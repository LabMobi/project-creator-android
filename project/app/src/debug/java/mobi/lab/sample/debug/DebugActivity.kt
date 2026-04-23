package mobi.lab.sample.debug

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import mobi.lab.sample.common.BaseActivity
import mobi.lab.sample.common.platform.LogoutMonitor
import mobi.lab.sample.common.util.EdgeToEdgeUtil
import mobi.lab.scrolls.LogImplFile
import mobi.lab.scrolls.LogViewBuilder

class DebugActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EdgeToEdgeUtil.setLightStatusBarIcons(window = window)
        setContent {
            DebugScreen(
                onLogoutClicked = { LogoutMonitor.logout() },
                onCrashClicked = { throw RuntimeException() },
                onScrollsClicked = {
                    val builder = LogViewBuilder()
                    builder.setDirectory(LogImplFile.getLogDir())
                        .addTags("manual_post")
                        .launchActivity(this@DebugActivity)
                }
            )
        }
    }

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, DebugActivity::class.java)
    }
}
