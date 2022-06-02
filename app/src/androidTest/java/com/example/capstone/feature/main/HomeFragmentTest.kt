package com.example.capstone.feature.main

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @Test
    fun testEventFragment() {
        // The "fragmentArgs" argument is optional.
        val fragmentArgs = bundleOf()
        val scenario = launchFragmentInContainer<HomeFragment>(fragmentArgs)
    }

}