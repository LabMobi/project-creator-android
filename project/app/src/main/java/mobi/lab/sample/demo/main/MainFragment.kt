package mobi.lab.sample.demo.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mobi.lab.sample.common.BaseFragment
import mobi.lab.sample.common.debug.DebugActions
import mobi.lab.sample.common.platform.LogoutMonitor
import mobi.lab.sample.common.util.NavUtil
import mobi.lab.sample.demo.prototype.PrototypeActivity
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    @Inject lateinit var debugActions: DebugActions

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogoutMonitor.reset() // Reset logout monitor. If we can see this screen, then we have a valid session
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MainScreen(
                    onOpenPrototypeClicked = { viewModel.onOpenPrototypeClicked() },
                    onLogoutClicked = { viewModel.onLogoutClicked() },
                    onDebugClicked = { viewModel.onDebugClicked() }
                )
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**
         * Init ViewModel in onViewCreated as they are connected to View's lifecycle.
         */
        viewModel.action.onEachEvent { event ->
            val context = context
            if (context == null) {
                return@onEachEvent false
            }

            when (event) {
                is MainViewModel.Action.OpenWebLink -> openPrototype(context, event.url)
                is MainViewModel.Action.RestartApplication -> restartApplication(context)
                is MainViewModel.Action.OpenDebug -> openDebug(context)
            }
            return@onEachEvent true
        }
    }

    private fun openPrototype(context: Context, url: String) {
        // Open PrototypeActivity to demonstrate assisted injection with runtime arguments
        startActivity(PrototypeActivity.getIntent(context, url))
    }

    private fun restartApplication(context: Context) {
        NavUtil.restartApplication(context)
    }

    private fun openDebug(context: Context) {
        // Open DebugActivity
        debugActions.launchDebugActivity(context)
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}
