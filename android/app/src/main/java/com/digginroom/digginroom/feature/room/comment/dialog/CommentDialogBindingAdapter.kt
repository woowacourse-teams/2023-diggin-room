package com.digginroom.digginroom.feature.room.comment.dialog

import android.text.Editable
import android.text.Selection
import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("app:cursorIndex")
fun EditText.cursorIndex(comments: String) {
    val editable: Editable = this.text
    Selection.setSelection(editable, editable.length)
}
