package mobi.lab.sample.util

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import mobi.lab.sample.TestApp_Application

/**
 * A runner to provide a different Application class from the regular application code.
 * Referenced from app/build.gradle.
 */
@Suppress("unused")
class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestApp_Application::class.java.name, context)
    }
}
