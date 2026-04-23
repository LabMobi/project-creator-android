package mobi.lab.sample.demo.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mobi.lab.sample.R
import mobi.lab.sample.common.BaseFragment
import mobi.lab.sample.common.dialog.ConfirmationDialogFragment
import mobi.lab.sample.common.dialog.ProgressDialogFragment
import mobi.lab.sample.common.util.DialogUtil
import mobi.lab.sample.common.util.formatErrorCode
import mobi.lab.sample.demo.main.MainActivity
import mobi.lab.sample.domain.entities.ErrorCode

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val state by viewModel.state.observeAsState(LoginViewModel.State.Default)
                LoginScreen(
                    state = state,
                    onLoginClicked = { email, password -> viewModel.onLoginClicked(email, password) }
                )
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.action.onEachEvent { action ->
            val activity = activity
            if (activity == null) {
                return@onEachEvent false
            }

            when (action) {
                LoginViewModel.Action.OpenApplication -> {
                    startActivity(MainActivity.getIntent(activity))
                    activity.finishAffinity()
                }
            }
            return@onEachEvent true
        }
        viewModel.state.onEachNotNull { state ->
            when (state) {
                LoginViewModel.State.Default -> {
                    hideProgress()
                }
                LoginViewModel.State.Progress -> {
                    showProgress()
                }
                is LoginViewModel.State.Error -> {
                    hideProgress()
                    if (state.errorCode != ErrorCode.LOCAL_INVALID_CREDENTIALS) {
                        showLoginError(state.errorCode)
                    }
                }
            }
        }
    }

    private fun showLoginError(error: ErrorCode) {
        DialogUtil.show(
            this,
            ConfirmationDialogFragment.newInstance(
                title = getString(R.string.demo_title_error),
                message = formatErrorCode(context, error),
                positiveButton = getString(android.R.string.ok)
            ),
            TAG_DIALOG_ERROR
        )
    }

    private fun showProgress() {
        if (isProgressShown()) {
            // Already there
            return
        }
        DialogUtil.show(this, ProgressDialogFragment.newInstance(), TAG_DIALOG_PROGRESS)
    }

    private fun hideProgress() {
        if (!isProgressShown()) {
            // Already there
            return
        }
        DialogUtil.dismiss(this, TAG_DIALOG_PROGRESS)
    }

    private fun isProgressShown(): Boolean = DialogUtil.isShowing(activity, TAG_DIALOG_PROGRESS)

    companion object {
        const val TAG_DIALOG_PROGRESS = "login.TAG_DIALOG_PROGRESS"
        const val TAG_DIALOG_ERROR = "login.TAG_DIALOG_ERROR"

        fun newInstance(): LoginFragment = LoginFragment()
    }
}
