package com.embet


import android.content.Context
import android.graphics.Color
import android.widget.FrameLayout
import android.widget.TextView

class TestFragmentView(context: Context) : FrameLayout(context) {

  init {
    // set padding and background color
    setPadding(16,16,16,16)
    setBackgroundColor(Color.BLUE)


    // add default text view
    addView(TextView(context).apply {
      width = android.view.ViewGroup.LayoutParams.MATCH_PARENT
      height = android.view.ViewGroup.LayoutParams.MATCH_PARENT
      text = "Welcome to Android Fragments with React Native."
      setBackgroundColor(Color.RED)
    })

  }


}
