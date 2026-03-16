package mobi.lab.sample.debug

import android.content.Context
import mobi.lab.sample.common.debug.DebugActions

class DevDebugActions : DebugActions {
    override fun launchDebugActivity(context: Context) = context.startActivity(DebugActivity.getIntent(context))
}
