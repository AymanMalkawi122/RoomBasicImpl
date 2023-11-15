package com.example.roombasicimpl.common

import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackBbarMaker {

    fun makeSnackBar(layout: View, message:String = "Snack Bar") {
        val snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_LONG)
            .setAction("OK",{})
        snackbar.show();
    }
}
