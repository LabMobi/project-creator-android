package mobi.lab.sample.debug

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import mobi.lab.components.compose.widget.button.LabButton
import mobi.lab.components.compose.widget.topappbar.LabTopAppBar
import mobi.lab.sample.R
import mobi.lab.sample.common.ui.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugScreen(
    onLogoutClicked: () -> Unit,
    onCrashClicked: () -> Unit,
    onScrollsClicked: () -> Unit,
) {
    AppTheme {
        Scaffold(
            topBar = {
                LabTopAppBar(
                    title = stringResource(R.string.title_debug),
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = dimensionResource(R.dimen.size_48))
                ) {
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_48)))

                    Text(
                        text = stringResource(R.string.text_debug_options),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 48.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_24)))

                    Text(
                        text = stringResource(R.string.text_debug_menu_description),
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_48)))

                    LabButton(
                        onClick = onScrollsClicked,
                        text = stringResource(R.string.button_view_logs),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_48)))

                    LabButton(
                        onClick = onCrashClicked,
                        text = stringResource(R.string.button_crash),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_48)))

                    LabButton(
                        onClick = onLogoutClicked,
                        text = stringResource(R.string.button_invalidate),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_16)))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDebugScreen() {
    DebugScreen(
        onLogoutClicked = {},
        onCrashClicked = {},
        onScrollsClicked = {}
    )
}
