package mobi.lab.sample

import org.junit.Test
import kotlin.test.assertEquals

class SampleClassTest {
    @Test
    fun testSampleClass() {
        assertEquals("Foo", SampleClass().foo())
    }
}
