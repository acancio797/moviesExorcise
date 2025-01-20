package com.exorcise.movie.ui

import com.exorcise.movie.rules.MainDispatcherRule
import org.junit.Rule

open class BaseViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
}
