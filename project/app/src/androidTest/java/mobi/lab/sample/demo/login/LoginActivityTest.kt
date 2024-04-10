package mobi.lab.sample.demo.login

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import mobi.lab.sample.R
import mobi.lab.sample.common.rx.SchedulerProvider
import mobi.lab.sample.demo.main.MainActivity
import mobi.lab.sample.util.hasNoTextInputLayoutError
import mobi.lab.sample.util.hasTextInputLayoutError
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class LoginActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    /**
     * Use [androidx.test.ext.junit.rules.ActivityScenarioRule] to create and launch the activity under test before each test,
     * and close it after each test. This is a replacement for
     * [androidx.test.rule.ActivityTestRule].
     */
    @get:Rule
    val activityScenarioRule = activityScenarioRule<LoginActivity>()

    @Inject
    lateinit var schedulers: SchedulerProvider

    private lateinit var activity: LoginActivity

    @Before
    fun setup() {
        hiltRule.inject()
        Intents.init()
        activityScenarioRule.scenario.onActivity {
            activity = it
        }
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun show_input_error_when_fields_are_empty_rxidler() {
        onView(withId(R.id.button_login)).perform(click())

        onView(withId(R.id.input_layout_email)).check(matches(hasTextInputLayoutError(TEXT_ID_REQUIRED)))
        onView(withId(R.id.input_layout_password)).check(matches(hasTextInputLayoutError(TEXT_ID_REQUIRED)))

        Intents.assertNoUnverifiedIntents()
    }

    @Test
    fun show_input_error_when_only_username_is_filled_rxidler() {
        onView(withId(R.id.edit_text_email)).perform(typeText("asd"), closeSoftKeyboard())
        onView(withId(R.id.button_login)).perform(click())

        onView(withId(R.id.input_layout_email)).check(matches(hasNoTextInputLayoutError()))
        onView(withId(R.id.input_layout_password)).check(matches(hasTextInputLayoutError(TEXT_ID_REQUIRED)))

        Intents.assertNoUnverifiedIntents()
    }

    @Test
    fun show_input_error_when_only_password_is_filled_rxidler() {
        onView(withId(R.id.edit_text_password)).perform(typeText("asd"), closeSoftKeyboard())
        onView(withId(R.id.button_login)).perform(click())

        onView(withId(R.id.input_layout_email)).check(matches(hasTextInputLayoutError(TEXT_ID_REQUIRED)))
        onView(withId(R.id.input_layout_password)).check(matches(hasNoTextInputLayoutError()))

        Intents.assertNoUnverifiedIntents()
    }

    @Test
    fun login_success_when_fields_are_filled_rxidler() {
        onView(withId(R.id.edit_text_email)).perform(typeText("asd"), closeSoftKeyboard())
        onView(withId(R.id.edit_text_password)).perform(typeText("asd"), closeSoftKeyboard())
        onView(withId(R.id.button_login)).perform(click())

        Intents.intended(IntentMatchers.hasComponent(MainActivity::class.java.name))
    }

    @Test
    fun show_error_dialog_when_login_fails_rxidler() {
        // "test" is a keyword to trigger an error response. See LoginUseCase implementation
        onView(withId(R.id.edit_text_email)).perform(typeText("test"), closeSoftKeyboard())
        onView(withId(R.id.edit_text_password)).perform(typeText("asd"), closeSoftKeyboard())
        onView(withId(R.id.button_login)).perform(click())

        // Validate the dialog and close it
        onView(withText(R.string.demo_error_generic)).check(matches(isDisplayed()))
        Espresso.pressBack()

        onView(withId(R.id.input_layout_email)).check(matches(hasNoTextInputLayoutError()))
        onView(withId(R.id.input_layout_password)).check(matches(hasNoTextInputLayoutError()))

        Intents.assertNoUnverifiedIntents()
    }

    companion object {
        private val TEXT_ID_REQUIRED = R.string.demo_text_required
    }
}
