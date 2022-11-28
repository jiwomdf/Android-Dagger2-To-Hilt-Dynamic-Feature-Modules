package com.programmergabut.movieapp.util

import android.animation.ObjectAnimator
import android.app.Activity
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.Window
import android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.programmergabut.movieapp.R
import com.programmergabut.movieapp.databinding.LayoutErrorBottomSheetBinding


fun View.fadeInAndOut() {
    val fade = ObjectAnimator.ofFloat(this, "alpha", 0.8f,0.15f)
    fade.duration = 500
    fade.repeatMode = ObjectAnimator.REVERSE
    fade.repeatCount = ObjectAnimator.INFINITE
    fade.start()
    tag = fade
}

fun View.stopFadeInAndOut() {
    try {
        val fade = (tag as ObjectAnimator)
        fade.removeAllListeners()
        fade.end()
        fade.cancel()
    } catch (e: Exception) { }
}

fun Activity.showBottomSheet(
    title : String = resources.getString(R.string.text_error_title),
    description : String = resources.getString(R.string.text_error_dsc),
    isCancelable : Boolean = true,
    isFinish : Boolean = false,
    callback: (() -> Unit)? = null) {

    val dialog =  BottomSheetDialog(this)
    val dialogBinding = LayoutErrorBottomSheetBinding.inflate(layoutInflater)

    dialog.apply{
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setCancelable(isCancelable)
        setContentView(dialogBinding.root)
    }
    dialogBinding.apply {
        tvTitle.text = title
        tvDesc.text = description
    }
    dialog.show()
    dialogBinding.btnOk.setOnClickListener {
        dialog.hide()
        callback?.invoke()
        if(isFinish)
            finish()
    }
}

fun showFadeLoading(loadingView: View, invisibleView: View? = null, isLoading: Boolean){
    loadingView.isVisible = isLoading
    if(isLoading){
        invisibleView?.visibility = View.INVISIBLE
        loadingView.fadeInAndOut()
    } else {
        invisibleView?.visibility = View.VISIBLE
        loadingView.stopFadeInAndOut()
    }
}

fun Activity.setStatusBarThemeMode(isDarkMode: Boolean){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val window: Window = window
        val decorView = window.decorView
        val wic = WindowInsetsControllerCompat(window, decorView)
        wic.isAppearanceLightStatusBars = isDarkMode
    }
}

fun Fragment.setStatusBarThemeMode(isDarkMode: Boolean){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val window: Window = requireActivity().window
        val decorView = window.decorView
        val wic = WindowInsetsControllerCompat(window, decorView)
        wic.isAppearanceLightStatusBars = isDarkMode
    }
}

fun Activity.setTransparentStatusBar(view: View? = null, isMarginTop: Boolean = true){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.setFlags(FLAG_TRANSLUCENT_STATUS, FLAG_TRANSLUCENT_STATUS)
        if (isMarginTop) {
            view?.let {
                setViewPadding(it,0, getStatusBarHeight(), 0, 0)
            }
        }
    }
}

fun Activity.getStatusBarHeight(): Int {
    var result = 0
    val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun setViewPadding(view: View, left: Int, top: Int, right: Int, bottom: Int) {
    view.setPadding(left, top, right, bottom)
}

fun setViewMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
    if (view.layoutParams is MarginLayoutParams) {
        val p = view.layoutParams as MarginLayoutParams
        p.setMargins(left, top, right, bottom)
        view.requestLayout()
    }
}