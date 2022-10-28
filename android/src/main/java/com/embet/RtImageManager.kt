package com.embet

import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.views.image.ReactImageView

class ReactImageManager(
  private val callerContext: ReactApplicationContext
) : SimpleViewManager<ReactImageView>() {

  override fun getName() = "RCTImageView"

  companion object {
    const val REACT_CLASS = "RCTImageView"
  }

  override fun createViewInstance(p0 : ThemedReactContext) : ReactImageView {
    return   ReactImageView(p0, Fresco.newDraweeControllerBuilder(), null, callerContext)
  }
}
