package com.example.capstone.util

import android.animation.ObjectAnimator
import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.example.capstone.R
import com.example.capstone.databinding.LayoutErrorBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


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
