package com.huyingbao.module.github.ui.login.view

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by liujunfeng on 2019/3/28.
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<LoginActivity> = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun onRxChanged() {
        activityRule.scenario.moveToState(Lifecycle.State.CREATED)
    }
}