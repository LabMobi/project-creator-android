package mobi.lab.sample.demo.login

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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import mobi.lab.components.compose.theme.LabTheme
import mobi.lab.components.compose.widget.button.LabButton
import mobi.lab.components.compose.widget.textfield.LabTextField
import mobi.lab.components.compose.widget.topappbar.LabTopAppBar
import mobi.lab.sample.R
import mobi.lab.sample.common.ui.AppTheme
import mobi.lab.sample.domain.entities.ErrorCode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    state: LoginViewModel.State,
    onLoginClicked: (String, String) -> Unit
) {
    AppTheme {
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }

        val isInvalidCredentials = state is LoginViewModel.State.Error && state.errorCode == ErrorCode.LOCAL_INVALID_CREDENTIALS
        val emailError = if (isInvalidCredentials && email.isEmpty()) stringResource(R.string.demo_text_required) else null
        val passwordError = if (isInvalidCredentials && password.isEmpty()) stringResource(R.string.demo_text_required) else null

        Scaffold(
            topBar = {
                LabTopAppBar(
                    title = stringResource(R.string.demo_title_login),
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(LabTheme.colors.background)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = dimensionResource(R.dimen.size_48))
                ) {
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_48)))

                    LabTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = stringResource(R.string.demo_title_email),
                        errorValue = emailError,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_8)))

                    LabTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = stringResource(R.string.demo_title_password),
                        errorValue = passwordError,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_32)))

                    LabButton(
                        onClick = { onLoginClicked(email, password) },
                        text = stringResource(R.string.demo_title_login),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = state !is LoginViewModel.State.Progress
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoginScreen() {
    LoginScreen(
        state = LoginViewModel.State.Default,
        onLoginClicked = { _, _ -> }
    )
}
