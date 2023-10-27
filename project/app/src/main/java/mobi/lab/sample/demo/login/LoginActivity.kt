package mobi.lab.sample.demo.login

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import mobi.lab.sample.common.BaseFragmentActivity

class LoginActivity : BaseFragmentActivity() {

    override val tag: String
        get() = LoginFragment::class.java.name

    override fun createFragment(): Fragment {
        return LoginFragment.newInstance()
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}
