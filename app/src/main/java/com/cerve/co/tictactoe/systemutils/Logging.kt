package com.cerve.co.tictactoe.systemutils

import timber.log.Timber

object Logging {

    fun String.logIt(parentName: String) {
        Timber.d("${Thread.currentThread().name}| $parentName | $this")
    }
}