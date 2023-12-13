package mobi.lab.sample.demo.prototype

import android.content.Context
import android.content.Intent
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import mobi.lab.sample.common.BaseActivity
import mobi.lab.sample.common.assistedViewModels
import mobi.lab.sample.common.util.NavUtil

@AndroidEntryPoint
class PrototypeActivity : BaseActivity() {

    /**
     * Use the assistedViewModels extension function for convenience to just invoke the AssistedInject factory.
     */
    private val viewModel: PrototypeViewModel by assistedViewModels(PrototypeViewModel.Factory::class) {
        it.create(intent.getStringExtra(EXTRA_PROTOTYPE_URL) ?: "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.action.onEachEvent { action ->
            when (action) {
                is PrototypeViewModel.Action.OpenWebLinkAndClose -> openBrowserAndClose(action.url)
                PrototypeViewModel.Action.Close -> close()
            }
            return@onEachEvent true
        }
    }

    private fun openBrowserAndClose(url: String) {
        NavUtil.openBrowser(this, url)
        close()
    }

    private fun close() {
        finish()
    }

    companion object {
        private const val EXTRA_PROTOTYPE_URL = "prototype_url"

        fun getIntent(context: Context, prototypeUrl: String): Intent {
            return Intent(context, PrototypeActivity::class.java).apply {
                putExtra(EXTRA_PROTOTYPE_URL, prototypeUrl)
            }
        }
    }
}
