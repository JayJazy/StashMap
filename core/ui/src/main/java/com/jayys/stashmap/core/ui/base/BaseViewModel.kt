package com.jayys.stashmap.core.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(),  CoroutineScope {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("jayjazy", "Error : $throwable")
    }

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext + exceptionHandler
}