package mobi.lab.sample.sample

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import mobi.lab.sample.SampleClass

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.content)) { view, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                    or WindowInsetsCompat.Type.displayCutout()
            )
            view.updatePadding(
                view.paddingLeft.coerceAtLeast(bars.left),
                view.paddingTop.coerceAtLeast(bars.top),
                view.paddingRight.coerceAtLeast(bars.right),
                view.paddingBottom.coerceAtLeast(bars.bottom)
            )
            WindowInsetsCompat.CONSUMED
        }

        findViewById<Button>(R.id.button).setOnClickListener { useLibrary() }
    }

    private fun useLibrary() {
        // Use our library here
        Toast.makeText(this, SampleClass().foo(), Toast.LENGTH_LONG).show()
    }
}
