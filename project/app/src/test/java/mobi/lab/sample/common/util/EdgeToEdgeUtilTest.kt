package mobi.lab.sample.common.util

import androidx.core.graphics.Insets
import androidx.core.view.WindowInsetsCompat
import org.junit.Test
import kotlin.test.assertEquals

class EdgeToEdgeUtilTest {

    @Test
    fun generateMaskFromSpec_AVOID_BARS_AND_CUTOUT() {
        val mask = EdgeToEdgeUtil.generateMaskFromSpec(
            spec = EdgeToEdgeSpec.AVOID_ALL_SET_ALL
        )
        assertEquals(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout(), mask)
    }

    @Test
    fun generateMaskFromSpec_AVOID_BARS() {
        val mask = EdgeToEdgeUtil.generateMaskFromSpec(
            spec = EdgeToEdgeSpec(
                avoidBars = true,
                avoidCutout = false,
                setLeft = true,
                setTop = true,
                setRight = true,
                setBottom = true,
            )
        )
        assertEquals(WindowInsetsCompat.Type.systemBars(), mask)
    }

    @Test
    fun generateMaskFromSpec_AVOID_CUTOUT() {
        val mask = EdgeToEdgeUtil.generateMaskFromSpec(
            spec = EdgeToEdgeSpec(
                avoidBars = false,
                avoidCutout = true,
                setLeft = true,
                setTop = true,
                setRight = true,
                setBottom = true,
            )
        )
        assertEquals(WindowInsetsCompat.Type.displayCutout(), mask)
    }

    @Test(expected = IllegalArgumentException::class)
    fun generateMaskFromSpec_AVOID_NOTHING() {
        EdgeToEdgeUtil.generateMaskFromSpec(
            spec = EdgeToEdgeSpec(
                avoidBars = false,
                avoidCutout = false,
                setLeft = true,
                setTop = true,
                setRight = true,
                setBottom = true,
            )
        )
    }

    @Test
    fun applySystemPaddingsBySpec_SET_ALL() {
        val currentViewPaddings = ViewPaddings(9, 9, 9, 9)
        val targetViewPaddings = ViewPaddings(1, 2, 3, 4)
        val systemPaddings = Insets.of(targetViewPaddings.left, targetViewPaddings.top, targetViewPaddings.right, targetViewPaddings.bottom)
        EdgeToEdgeUtil.applySystemPaddingsBySpec(currentViewPaddings, systemPaddings, EdgeToEdgeSpec.AVOID_ALL_SET_ALL) { paddings ->
            assertEquals(targetViewPaddings, paddings)
        }
    }

    @Test
    fun applySystemPaddingsBySpec_SET_LEFT_TOP_RIGHT() {
        val currentViewPaddings = ViewPaddings(9, 9, 9, 9)
        val targetViewPaddings = ViewPaddings(1, 2, 3, 9)
        val systemPaddings = Insets.of(targetViewPaddings.left, targetViewPaddings.top, targetViewPaddings.right, targetViewPaddings.bottom)
        EdgeToEdgeUtil.applySystemPaddingsBySpec(currentViewPaddings, systemPaddings, EdgeToEdgeSpec.AVOID_ALL_SET_LEFT_TOP_RIGHT) { paddings ->
            assertEquals(targetViewPaddings, paddings)
        }
    }

    @Test
    fun applySystemPaddingsBySpec_SET_LEFT_RIGHT() {
        val currentViewPaddings = ViewPaddings(9, 9, 9, 9)
        val targetViewPaddings = ViewPaddings(1, 9, 3, 9)
        val systemPaddings = Insets.of(targetViewPaddings.left, targetViewPaddings.top, targetViewPaddings.right, targetViewPaddings.bottom)
        EdgeToEdgeUtil.applySystemPaddingsBySpec(
            currentViewPaddings,
            systemPaddings,
            EdgeToEdgeSpec(
                avoidBars = true,
                avoidCutout = true,
                setLeft = true,
                setTop = false,
                setRight = true,
                setBottom = false
            )
        ) { paddings ->
            assertEquals(targetViewPaddings, paddings)
        }
    }

    @Test
    fun applySystemPaddingsBySpec_SET_LEFT() {
        val currentViewPaddings = ViewPaddings(9, 9, 9, 9)
        val targetViewPaddings = ViewPaddings(1, 9, 9, 9)
        val systemPaddings = Insets.of(targetViewPaddings.left, targetViewPaddings.top, targetViewPaddings.right, targetViewPaddings.bottom)
        EdgeToEdgeUtil.applySystemPaddingsBySpec(
            currentViewPaddings,
            systemPaddings,
            EdgeToEdgeSpec(
                avoidBars = true,
                avoidCutout = true,
                setLeft = true,
                setTop = false,
                setRight = false,
                setBottom = false
            )
        ) { paddings ->
            assertEquals(targetViewPaddings, paddings)
        }
    }

    @Test
    fun applySystemPaddingsBySpec_SET_RIGHT() {
        val currentViewPaddings = ViewPaddings(9, 9, 9, 9)
        val targetViewPaddings = ViewPaddings(9, 9, 3, 9)
        val systemPaddings = Insets.of(targetViewPaddings.left, targetViewPaddings.top, targetViewPaddings.right, targetViewPaddings.bottom)
        EdgeToEdgeUtil.applySystemPaddingsBySpec(
            currentViewPaddings,
            systemPaddings,
            EdgeToEdgeSpec(
                avoidBars = true,
                avoidCutout = true,
                setLeft = false,
                setTop = false,
                setRight = true,
                setBottom = false
            )
        ) { paddings ->
            assertEquals(targetViewPaddings, paddings)
        }
    }
}
