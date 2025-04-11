package mobi.lab.sample.demo.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mobi.lab.sample.BuildConfig
import mobi.lab.sample.R
import mobi.lab.sample.common.BaseFragment
import mobi.lab.sample.common.FragmentBindingHolder
import mobi.lab.sample.common.ViewBindingHolder
import mobi.lab.sample.common.debug.DebugActions
import mobi.lab.sample.common.platform.LogoutMonitor
import mobi.lab.sample.common.util.EdgeToEdgeSpec
import mobi.lab.sample.common.util.EdgeToEdgeUtil
import mobi.lab.sample.common.util.EdgeToEdgeUtil.setInsetsForBarsAndCutout
import mobi.lab.sample.common.util.NavUtil
import mobi.lab.sample.databinding.DemoFragmentMainBinding
import mobi.lab.sample.demo.prototype.PrototypeActivity
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment(), ViewBindingHolder<DemoFragmentMainBinding> by FragmentBindingHolder() {

    @Inject lateinit var debugActions: DebugActions

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogoutMonitor.reset() // Reset logout monitor. If we can see this screen, then we have a valid session
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return createBinding(DemoFragmentMainBinding.inflate(inflater), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireBinding {
            initToolbar(this)
            buttonOpen.setOnClickListener { viewModel.onOpenPrototypeClicked() }
        }

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

    private fun initToolbar(binding: DemoFragmentMainBinding) {
        if (BuildConfig.DEBUG) {
            binding.toolbar.inflateMenu(R.menu.debug_toolbar)
            binding.toolbar.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.menu_logout) {
                    viewModel.onLogoutClicked()
                    return@setOnMenuItemClickListener true
                }
                if (item.itemId == R.id.button_debug) {
                    viewModel.onDebugClicked()
                    return@setOnMenuItemClickListener true
                }

                return@setOnMenuItemClickListener false
            }
        } else {
            binding.toolbar.inflateMenu(R.menu.demo_main_toolbar)
            binding.toolbar.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.menu_logout) {
                    viewModel.onLogoutClicked()
                    return@setOnMenuItemClickListener true
                }
                return@setOnMenuItemClickListener false
            }
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

    override fun setEdgeToEdgeInsets() {
        // Let the image go under the navigation bars
        setInsetsForBarsAndCutout(targetView = requireView(), spec = EdgeToEdgeSpec.AVOID_BAR_AND_CUTOUT_SET_LEFT_TOP_RIGHT)
        EdgeToEdgeUtil.setLightStatusBarIcons(window = requireActivity().window)
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}
