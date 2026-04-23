package mobi.lab.sample.demo.main

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import mobi.lab.components.compose.theme.LabTheme
import mobi.lab.components.compose.widget.button.LabButton
import mobi.lab.components.compose.widget.topappbar.LabTopAppBar
import mobi.lab.sample.BuildConfig
import mobi.lab.sample.R
import mobi.lab.sample.common.ui.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onOpenPrototypeClicked: () -> Unit,
    onLogoutClicked: () -> Unit,
    onDebugClicked: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    AppTheme {
        Scaffold(
            topBar = {
                LabTopAppBar(
                    title = stringResource(R.string.demo_title_prototype),
                    actions = {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = stringResource(R.string.menu_more_options),
                                tint = LabTheme.colors.onSurface
                            )
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            if (BuildConfig.DEBUG) {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = stringResource(R.string.title_debug),
                                            style = LabTheme.typography.bodyMedium
                                        )
                                    },
                                    onClick = {
                                        showMenu = false
                                        onDebugClicked()
                                    }
                                )
                            }
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = stringResource(R.string.title_log_out),
                                        style = LabTheme.typography.bodyMedium
                                    )
                                },
                                onClick = {
                                    showMenu = false
                                    onLogoutClicked()
                                }
                            )
                        }
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(LabTheme.colors.background)
            ) {
                Image(
                    painter = painterResource(R.drawable.demo_img_brush),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(LabTheme.colors.primary),
                    modifier = Modifier.align(Alignment.BottomEnd)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = dimensionResource(R.dimen.size_48))
                ) {
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_48)))

                    Text(
                        text = stringResource(R.string.demo_text_experience_prototype),
                        style = LabTheme.typography.headlineLarge,
                        color = LabTheme.colors.primary
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_24)))

                    Text(
                        text = stringResource(R.string.demo_text_experience_prototype_description),
                        style = LabTheme.typography.bodyLarge,
                        color = LabTheme.colors.primary
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_48)))

                    LabButton(
                        onClick = onOpenPrototypeClicked,
                        text = stringResource(R.string.demo_title_open_prototype),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMainScreen() {
    MainScreen(
        onOpenPrototypeClicked = {},
        onLogoutClicked = {},
        onDebugClicked = {}
    )
}
