package mobi.lab.sample.debug

import android.content.Context
import mobi.lab.sample.common.debug.DebugActions

class ReleaseDebugActions : DebugActions {
    override fun launchDebugActivity(context: Context) {
        // No-op
    }
}
