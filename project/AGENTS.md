# ProjectCodeName Android Architecture Guide

This document describes the architecture, conventions, and verification workflow for `ProjectCodeName` (`mobi.lab.sample`).

## Module Structure

The project uses a 4-module Clean Architecture layout:

- `:app` — Presentation layer and Android entry point. Single `MainActivity`, Jetpack Compose UI (`*Screen.kt`), Jetpack Navigation 3 destinations (`*Route.kt`, `navigation/`), ViewModels, DI wiring, and build-variant modules (`src/debug`, `src/release`).
- `:app-domain` — Business logic: Use Cases, domain entities, `DomainException`/`ErrorCode`, and `Gateway`/`Storage` interfaces. Pure domain boundaries with no UI or Retrofit/storage implementation details.
- `:app-infrastructure` — Implementations of domain `Gateway` and `Storage` interfaces (Retrofit, OkHttp, Moshi + Kotshi, SharedPreferences, Glide `ImageLoader`, error mappers). Annotate DTO classes with `@Keep` for R8 full mode.
- `:app-common` — Small utilities and extensions shared across modules.

Keep dependencies flowing inward (`:app` → `:app-domain`, `:app-infrastructure` → `:app-domain`). Avoid depending on `:app-infrastructure` implementation classes directly from UI/ViewModels.

## Single-Activity & Jetpack Navigation 3

The app uses a **single Activity** (`mobi.lab.sample.MainActivity`) and **Jetpack Navigation 3** (`androidx.navigation3`) with no screen Fragments:

- **`MainActivity`** (`AppCompatActivity`):
  - Installs AndroidX SplashScreen (`installSplashScreen()`) and keeps it on screen via `setKeepOnScreenCondition` until `SplashViewModel` resolves the initial session check (`AppRoute.Main` vs `AppRoute.Login`).
  - Distinguishes a fresh session check (`event.getContentIfNotHandled()`) from a configuration-change replay (`event.peekContent()`) so process-death restoration reconciles any restored stack against fresh session validation while configuration changes preserve the active stack.
  - Calls `enableEdgeToEdge()` once at Activity startup; Compose `Scaffold` / `LabTopAppBar` handle system insets.
  - Extends `AppCompatActivity` directly (no `BaseActivity` inheritance) so `supportFragmentManager` remains available for `DialogFragment` utilities.
- **Type-Safe Routes (`navigation/AppRoute.kt`)**:
  - Define destinations as `@Serializable` types implementing `NavKey` inside `sealed interface AppRoute : NavKey`.
  - Routes are protected by default (`requiresAuth = true`); explicitly override `override val requiresAuth: Boolean get() = false` on unauthenticated destinations (e.g., `AppRoute.Login`).
  - `rememberNavBackStack(startDestination)` persists the back stack across configuration changes and process death via Kotlin Serialization.
- **Navigation Controller (`navigation/Navigator.kt`)**:
  - Wraps `MutableList<NavKey>` with `navigateTo(route)`, `goBack()`, `replaceAll(route)` (used for auth transitions such as Login → Main and Logout → Login), and `reconcileAuth(isAuthenticated)` (strips protected entries on invalid fresh session checks).
  - Keep navigation logic in `Navigator` so it remains straightforward to unit test on the JVM (`NavigatorTest`).
- **Navigation Host (`navigation/AppNavigation.kt`)**:
  - Uses `NavDisplay` with both `rememberSaveableStateHolderNavEntryDecorator()` and `rememberViewModelStoreNavEntryDecorator()` so every back-stack entry gets its own scoped `ViewModelStoreOwner`.
  - Reconciles fresh auth state and any pending `LogoutMonitor` logout synchronously before `NavDisplay` composes, and collects `LogoutMonitor.logoutPending` (acknowledged via `consumePendingLogout()`) so logouts triggered while no collector is active (e.g., during Activity recreation) are never lost.
- **Route + Screen Split**:
  - `*Route.kt` registers the `EntryProviderScope<NavKey>` extension (`loginEntry`, `mainEntry`), acquires the Hilt ViewModel via `hiltViewModel()`, observes one-off actions via `ObserveSingleEvent`, and handles platform/navigation side effects.
  - `*Screen.kt` stays a pure, previewable `@Composable` taking state and callbacks.
- **Passing Route Arguments to ViewModels**:
  - When a destination needs typed route arguments in its ViewModel, annotate the ViewModel with `@HiltViewModel(assistedFactory = MyViewModel.Factory::class)` and `@AssistedInject`, then instantiate it inside the route composable using:
    ```kotlin
    val viewModel = hiltViewModel<MyViewModel, MyViewModel.Factory>(
        creationCallback = { factory -> factory.create(route.arg) }
    )
    ```
- **Build-Variant Destinations (`common/debug/DebugNavigation.kt`, `di/BuildVariantModule.kt`)**:
  - Debug-only routes are registered via `DebugNavigation` bound in `src/debug/.../BuildVariantModule.kt` (`DevDebugNavigation`) and stubbed out in `src/release/.../BuildVariantModule.kt` (`ReleaseDebugNavigation`).

## MVVM, RxJava, Events & Dialogs

- **ViewModels & RxJava**: ViewModels expose `LiveData` state (using `SavedStateHandle` where state should survive process death) and `LiveData<SingleEvent<Action>>` (from `mobi.lab.mvvm`). Use `SchedulerProvider` transformers so unauthorized errors automatically trigger `LogoutMonitor.logout()`.
- **Observing `SingleEvent` in Compose**: Use `ObserveSingleEvent(viewModel.action) { ... }` (`common/ui/ObserveSingleEvent.kt`) rather than `observeAsState()` so rapid one-off events are consumed reliably and scoped to `LocalLifecycleOwner`.
- **Dialogs & EventBus**: The template currently retains `DialogFragment` (`ConfirmationDialogFragment`, `ProgressDialogFragment`), `DialogUtil` (`FragmentActivity` overloads via `LocalActivity.current as? FragmentActivity`), and `EventBus` (`common/eventbus/Bus.kt`) for compatibility with existing team patterns.
- **UI & Theming**: Wrap UI in `AppTheme` (`common/ui/AppTheme.kt`), which configures `LabTheme` (`mobi.lab.components.compose`) and Material 3 color schemes.

## Verification & Code Quality

Always run static analysis and tests after making changes:

- **Code style & static analysis (ktlint + detekt):**
  ```bash
  ./gradlew checkCode
  ```
  Note detekt rules in `config/detekt/detekt-config.yaml` (e.g., `BracesOnWhenStatements` requires consistent braces across all branches of a `when`, `BracesOnIfStatements` requires braces on multi-line `if`, `MaxLineLength = 150`).
- **Unit tests:**
  ```bash
  ./gradlew :app:testDebugUnitTest :app-domain:testDebugUnitTest :app-infrastructure:testDebugUnitTest
  ```
- **Debug & release verification:**
  ```bash
  ./gradlew :app:assembleDebug :app:minifyReleaseWithR8 :app:lint
  ```
  (Or `./gradlew :app:buildAllDebug` / `./gradlew :app:buildAllRelease` when release signing keystore is configured.)
