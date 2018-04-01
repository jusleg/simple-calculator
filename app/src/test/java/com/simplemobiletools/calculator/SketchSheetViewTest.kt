package com.simplemobiletools.calculator

import com.simplemobiletools.calculator.activities.DrawActivity
import com.simplemobiletools.calculator.views.SketchSheetView
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class SketchSheetViewTest {
    private lateinit var activity: DrawActivity
    private lateinit var sketchView: SketchSheetView

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(DrawActivity::class.java)
        sketchView = SketchSheetView(activity.applicationContext)
    }

    @Test
    fun clearPointsTest() {
        sketchView.startNewStroke()
        sketchView.addPoint(1, 2)
        sketchView.startNewStroke()
        sketchView.addPoint(23, 324)
        sketchView.clearSketch()
        Assert.assertEquals(0, sketchView.points.size)
    }

    @Test
    fun AddNegativePointTest() {
        sketchView.startNewStroke()
        sketchView.addPoint(-1, 2)
        Assert.assertArrayEquals(intArrayOf(0, 2), sketchView.points[0][0])
    }

    @Test
    fun strokesProperlyAddedTest() {
        sketchView.startNewStroke()
        sketchView.addPoint(1, 2)
        sketchView.addPoint(3, 4)
        sketchView.startNewStroke()
        sketchView.addPoint(5, 6)
        Assert.assertArrayEquals(intArrayOf(1, 2), sketchView.points[0][0])
        Assert.assertArrayEquals(intArrayOf(3, 4), sketchView.points[0][1])
        Assert.assertArrayEquals(intArrayOf(5, 6), sketchView.points[1][0])
    }

    @Test
    fun emptyExportScginkTest() {
        Assert.assertEquals("SCG_INK\n0\n", sketchView.exportScgink())
    }

    @Test
    fun completeExportScginkTest() {
        sketchView.startNewStroke()
        sketchView.addPoint(1, 2)
        sketchView.addPoint(3, 4)
        sketchView.startNewStroke()
        sketchView.addPoint(5, 6)
        sketchView.addPoint(7, 8)
        sketchView.startNewStroke()
        sketchView.addPoint(9, 10)
        Assert.assertEquals("SCG_INK\n3\n2\n1 2\n3 4\n2\n5 6\n7 8\n1\n9 10\n",
                sketchView.exportScgink())
    }
}
