package com.deveem.core.extentions

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.deveem.R
import com.deveem.core.utils.Log
import com.deveem.utils.PhoneTextFormatter
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso

fun View.showPopup(
    @MenuRes menu: Int,
    onOptionPick: (Int) -> Unit
) {
    PopupMenu(context, this).apply {
        inflate(menu)
        setOnMenuItemClickListener { item ->
            onOptionPick(item.itemId)
            return@setOnMenuItemClickListener true
        }
        show()
    }
}

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

var View.invisible: Boolean
    get() = visibility == View.INVISIBLE
    set(value) {
        visibility = if (value) View.INVISIBLE else View.VISIBLE
    }

fun View?.removeFocus() = try {
    val imm = this?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
    imm?.hideSoftInputFromWindow(this?.windowToken, 0)
} catch (e: Exception) {
    Log.e(e)
}

fun View.showKeyboard(toggleKeyboard: Boolean = true) {
    try {
        if (context != null && context is Activity) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE)
            if (imm is InputMethodManager) {
                this.requestFocus()
                if (toggleKeyboard) {
                    imm.toggleSoftInput(
                        InputMethodManager.SHOW_IMPLICIT,
                        InputMethodManager.HIDE_IMPLICIT_ONLY
                    )
                } else {
                    imm.showSoftInput(this, 0)
                }
            }
        }
    } catch (e: Exception) {
        Log.e(e)
    }
}

fun View.below(view: View) {
    (this.layoutParams as? RelativeLayout.LayoutParams)?.addRule(RelativeLayout.BELOW, view.id)
}

fun View.setMargins(newLeft: Int, newTop: Int, newRight: Int, newBottom: Int) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.let {
        it.setMargins(newLeft, newTop, newRight, newBottom)
        this.layoutParams = it
    }
}

fun View.setTopMargin(top: Int) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.let {
        it.topMargin = top
        this.layoutParams = it
    }
}

fun ViewGroup.inflate(@LayoutRes layoutId: Int, attach: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutId, this, attach)

fun TextView.setTextColorRes(@ColorRes colorRes: Int) {
    setTextColor(ContextCompat.getColor(this.context, colorRes))
}

fun TextInputLayout.showError(@StringRes errorRes: Int, vibrate: Boolean = true) {
    val message = context.getString(errorRes)
    error = message

    if (vibrate) {
        val animVibrate = AnimationUtils.loadAnimation(context, R.anim.vibrate)
        this.startAnimation(animVibrate)
    }
}

fun TextInputLayout.showError(message: String, vibrate: Boolean = true) {
    error = message

    if (vibrate) {
        val animVibrate = AnimationUtils.loadAnimation(context, R.anim.vibrate)
        this.startAnimation(animVibrate)
    }
}

fun TextInputLayout.hideError() {
    error = null
}

fun EditText.setEditable(value: Boolean) {
    this.isFocusable = value
    this.isFocusableInTouchMode = value
    this.isClickable = value
}

fun EditText.enablePhoneFormatter() {
    this.addTextChangedListener(
        PhoneTextFormatter(
            this,
            "## (###) ###-####"
        )
    )
}

fun ImageView.loadUrl(url: String) {
    Picasso.get()
        .load(url)
        .into(this)
}
fun ImageView.loadUrl(url: String, placeholderResId: Int) {
    Picasso.get()
        .load(url)
        .placeholder(placeholderResId)
        .into(this)
}

