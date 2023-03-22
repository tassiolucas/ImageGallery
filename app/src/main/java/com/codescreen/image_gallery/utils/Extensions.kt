package com.codescreen.image_gallery.utils

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

fun Lifecycle.addObserverOnce(observer: LifecycleEventObserver) {
    addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            observer.onStateChanged(source, event)
            removeObserver(this)
        }
    })
}

@Composable
fun LifecycleOwner.onBackPressed(action: () -> Unit) {
    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val backPressedCallback = remember(action) {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                action.invoke()
            }
        }
    }
    DisposableEffect(dispatcher) {
        dispatcher?.addCallback(backPressedCallback)
        onDispose {
            backPressedCallback.remove()
        }
    }
    LocalLifecycleOwner.current.lifecycle.addObserverOnce(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                backPressedCallback.remove()
            }
        }
    })
}