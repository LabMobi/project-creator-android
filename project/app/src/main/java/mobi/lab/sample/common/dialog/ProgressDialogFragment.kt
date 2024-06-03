package mobi.lab.sample.common.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import mobi.lab.sample.R
import mobi.lab.sample.common.eventbus.Bus

class ProgressDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()

        val dialog = MaterialAlertDialogBuilder(context)
            .setView(R.layout.demo_dialog_progress)
            .create()

//        val dialog = MaterialDialog(context)
//        dialog.customView(R.layout.demo_dialog_progress)
        return dialog
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        sendEvent(DialogEvent.Action.CANCELLED)
    }

    @Suppress("SameParameterValue")
    private fun sendEvent(action: DialogEvent.Action) {
        Bus.post(DialogEvent(getSafeTag(), action))
    }

    private fun getSafeTag(): String {
        return tag ?: ""
    }

    companion object {
        fun newInstance(): ProgressDialogFragment {
            return ProgressDialogFragment()
        }
    }
}
