package com.weatherapplication.core.extension

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

fun TextView.onTextChange() = callbackFlow {
    val listener = addTextChangedListener { text ->
        if (text != null) {
            this.trySend(text.toString()).isSuccess
        }
    }
    awaitClose { removeTextChangedListener(listener) }
}.conflate()

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}