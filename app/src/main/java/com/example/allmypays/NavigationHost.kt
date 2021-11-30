package com.example.allmypays

import android.app.Activity
import androidx.fragment.app.Fragment


interface NavigationHost {
    fun navigateTo(fragment: Fragment, addToBackstack: Boolean)
}
