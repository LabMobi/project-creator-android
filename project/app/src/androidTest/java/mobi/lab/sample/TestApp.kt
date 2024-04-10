package mobi.lab.sample

import dagger.hilt.android.testing.CustomTestApplication

// Hilt will generate an application class that extends TestAppBase
@CustomTestApplication(TestAppBase::class)
interface TestApp
