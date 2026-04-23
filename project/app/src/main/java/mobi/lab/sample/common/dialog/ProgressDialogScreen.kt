package mobi.lab.sample.common.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mobi.lab.components.compose.theme.LabTheme
import mobi.lab.components.compose.widget.progress.LabIndeterminateCircularIndicator
import mobi.lab.sample.R
import mobi.lab.sample.common.ui.AppTheme

@Composable
fun ProgressDialogScreen() {
    AppTheme {
        Row(
            modifier = Modifier
                .background(LabTheme.colors.surface)
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LabIndeterminateCircularIndicator(
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.demo_text_loading),
                style = LabTheme.typography.bodyMedium,
                color = LabTheme.colors.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProgressDialogScreen() {
    ProgressDialogScreen()
}
