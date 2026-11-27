package mobi.lab.sample.demo.login

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import mobi.lab.sample.R
import mobi.lab.sample.common.dialog.ConfirmationDialogFragment
import mobi.lab.sample.common.dialog.ProgressDialogFragment
import mobi.lab.sample.common.ui.ObserveSingleEvent
import mobi.lab.sample.common.util.DialogUtil
import mobi.lab.sample.common.util.formatErrorCode
import mobi.lab.sample.domain.entities.ErrorCode
import mobi.lab.sample.navigation.AppRoute
import mobi.lab.sample.navigation.Navigator

private const val TAG_DIALOG_PROGRESS = "login.TAG_DIALOG_PROGRESS"
private const val TAG_DIALOG_ERROR = "login.TAG_DIALOG_ERROR"

fun EntryProviderScope<NavKey>.loginEntry(navigator: Navigator) {
    entry<AppRoute.Login> {
        LoginRoute(
            onOpenApplication = { navigator.replaceAll(AppRoute.Main) },
        )
    }
}

@Composable
fun LoginRoute(
    onOpenApplication: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.state.observeAsState(LoginViewModel.State.Default)
    val activity = LocalActivity.current as? FragmentActivity

    ObserveSingleEvent(viewModel.action) { action ->
        when (action) {
            LoginViewModel.Action.OpenApplication -> {
                if (activity != null) {
                    hideProgress(activity)
                }
                onOpenApplication()
            }
        }
    }

    LaunchedEffect(state, activity) {
        if (activity == null) {
            return@LaunchedEffect
        }
        when (val currentState = state) {
            LoginViewModel.State.Default -> {
                hideProgress(activity)
            }
            LoginViewModel.State.Progress -> {
                showProgress(activity)
            }
            is LoginViewModel.State.Error -> {
                hideProgress(activity)
                if (currentState.errorCode != ErrorCode.LOCAL_INVALID_CREDENTIALS) {
                    showLoginError(activity, currentState.errorCode)
                }
            }
        }
    }

    DisposableEffect(activity) {
        onDispose {
            if (activity != null) {
                hideProgress(activity)
            }
        }
    }

    LoginScreen(
        state = state,
        onLoginClicked = viewModel::onLoginClicked,
    )
}

private fun showLoginError(activity: FragmentActivity, error: ErrorCode) {
    DialogUtil.show(
        activity = activity,
        dialog = ConfirmationDialogFragment.newInstance(
            title = activity.getString(R.string.demo_title_error),
            message = formatErrorCode(activity, error),
            positiveButton = activity.getString(android.R.string.ok),
        ),
        tag = TAG_DIALOG_ERROR,
    )
}

private fun showProgress(activity: FragmentActivity) {
    if (DialogUtil.isShowing(activity, TAG_DIALOG_PROGRESS)) {
        return
    }
    DialogUtil.show(activity, ProgressDialogFragment.newInstance(), TAG_DIALOG_PROGRESS)
}

private fun hideProgress(activity: FragmentActivity) {
    if (!DialogUtil.isShowing(activity, TAG_DIALOG_PROGRESS)) {
        return
    }
    DialogUtil.dismiss(activity, TAG_DIALOG_PROGRESS)
}
