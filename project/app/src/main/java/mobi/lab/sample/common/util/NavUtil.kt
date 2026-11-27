package mobi.lab.sample.common.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import mobi.lab.sample.MainActivity
import timber.log.Timber

object NavUtil {

    fun openBrowser(context: Context, url: String) {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
        } catch (error: ActivityNotFoundException) {
            Timber.e(error, "openBrowser")
        }
    }

    @Suppress("unused")
    fun restartApplication(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        val restartIntent = Intent.makeRestartActivityTask(intent.component)
        context.startActivity(restartIntent)
    }
}
