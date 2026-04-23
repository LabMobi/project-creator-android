package mobi.lab.sample.common.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import mobi.lab.sample.common.eventbus.Bus

class ProgressDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()

        val composeView = ComposeView(context).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ProgressDialogScreen()
            }
        }

        return MaterialAlertDialogBuilder(context)
            .setView(composeView)
            .create()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        sendEvent(DialogEvent.Action.CANCELLED)
    }

    @Suppress("SameParameterValue")
    private fun sendEvent(action: DialogEvent.Action) {
        Bus.post(DialogEvent(getSafeTag(), action))
    }

    private fun getSafeTag(): String = tag ?: ""

    companion object {
        fun newInstance(): ProgressDialogFragment = ProgressDialogFragment()
    }
}
