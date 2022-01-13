package com.example.weatherlogger

import android.Manifest
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.GrantPermissionRule
import com.example.weatherlogger.view.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * WeatherDataFragmentTest is used to test WeatherDataFragment UI
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class WeatherDataFragmentTest {

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.INTERNET
    )

    @Test
    fun testAllViewsIsDisplayed() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)

        waitTillLaunch()

        testIsCompletelyDisplay(R.id.weatherView)
        testIsCompletelyDisplay(R.id.txtTemperature)
        testIsCompletelyDisplay(R.id.txtDescription)
        testIsCompletelyDisplay(R.id.txtHumidity)
        testIsCompletelyDisplay(R.id.txtWind)
        testIsCompletelyDisplay(R.id.weatherViewMax)
        testIsCompletelyDisplay(R.id.txtTempMax)
        testIsCompletelyDisplay(R.id.txtTempMin)
        testIsCompletelyDisplay(R.id.weatherViewMor)
        testIsCompletelyDisplay(R.id.txtTempMorn)
        testIsCompletelyDisplay(R.id.weatherViewEve)
        testIsCompletelyDisplay(R.id.txtTempEve)
        testIsCompletelyDisplay(R.id.weatherViewNight)
        testIsCompletelyDisplay(R.id.txtTempNight)
        testIsCompletelyDisplay(R.id.btnViewData)
        testIsCompletelyDisplay(R.id.btnSave)

    }

    @Test
    fun testSaveButtonText() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)

        waitTillLaunch()

        Espresso.onView(ViewMatchers.withId(R.id.btnSave)).check(
            ViewAssertions.matches(
                ViewMatchers.withText("Save")
            )
        )
    }

    @Test
    fun testViewDataButtonText() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)

        waitTillLaunch()

        Espresso.onView(ViewMatchers.withId(R.id.btnViewData)).check(
            ViewAssertions.matches(
                ViewMatchers.withText("View Data")
            )
        )
    }

    private fun testIsCompletelyDisplay(id: Int) {
        Espresso.onView(ViewMatchers.withId(id)).check(
            ViewAssertions.matches(
                ViewMatchers.isCompletelyDisplayed()
            )
        )

    }

    private fun waitTillLaunch() {
        Thread.sleep(5000)
    }
}