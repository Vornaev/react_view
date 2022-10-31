package com.embet

import android.graphics.Color
import android.util.Log
import android.view.Choreographer
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.uimanager.annotations.ReactPropGroup
import com.facebook.react.views.image.ReactImageView
import com.livelike.mobile.embet.data.EmBetSDKConfig
import com.livelike.mobile.embet.data.internal.EmBetSDK
import com.livelike.mobile.embet.fragment.EmBetReactWidgetFragment
import com.livelike.mobile.embet.publicapi.EmBetAccessTokenDelegate
import com.livelike.mobile.embet.publicapi.EmBetInstance
import com.livelike.mobile.embet.publicapi.ProgramSession

class TestFragmentManager(
  private val reactContext: ReactApplicationContext
) : ViewGroupManager<FrameLayout>() {
  private var propWidth: Int? = null
  private var propHeight: Int? = null

  private var sdkConfig : EmBetSDKConfig? = null

  override fun getName() = "TestFragmentManager"

  /**
   * Return a FrameLayout which will later hold the Fragment
   */
  override fun createViewInstance(reactContext: ThemedReactContext) =
    FrameLayout(reactContext).apply {
    }

  /**
   * Map the "create" command to an integer
   */
  override fun getCommandsMap() = mapOf("create" to COMMAND_CREATE)

  /**
   * Handle "create" command (called from JS) and call createFragment method
   */
  override fun receiveCommand(
    root: FrameLayout,
    commandId: String,
    args: ReadableArray?
  ) {
    super.receiveCommand(root, commandId, args)
    val reactNativeViewId = requireNotNull(args).getInt(0)

    when (commandId.toInt()) {
      COMMAND_CREATE -> createFragment(root, reactNativeViewId)
    }
  }

  @ReactProp(name = "clientID")
  fun setClientID(view : FrameLayout, clientID:String){


  }

  @ReactProp(name = "programID")
  fun setProgramID(view : FrameLayout, programID:String){
    Log.i("hello", "$programID")
  }


//  @ReactProp(name = "segmentationFeature")
//  fun setSegmentation(view : FrameLayout, segmentation:String){
//
//    segmentation.toLowerCase()
//    //of or on
//
//  }

  @ReactPropGroup(names = ["width", "height"], customType = "Style")
  fun setStyle(view: FrameLayout, index: Int, value: Int) {
    if (index == 0) propWidth = value
    if (index == 1) propHeight = value
  }

  /**
   * Replace your React Native view with a custom fragment
   */
  fun createFragment(root: FrameLayout, reactNativeViewId: Int) {
    val parentView = root.findViewById<ViewGroup>(reactNativeViewId)
    setupLayout(parentView)

    val myFragment = EmBetReactWidgetFragment()


    val activity = reactContext.currentActivity as FragmentActivity
    activity.supportFragmentManager
      .beginTransaction()
      .replace(reactNativeViewId, myFragment, reactNativeViewId.toString())
      .commit()

    val clientid = "mOBYul18quffrBDuq2IACKtVuLbUzXIPye5S3bq5"
    val programID = "7b7aa007-4a9b-497a-9c55-ec819a91b475"


    sdkConfig = EmBetSDKConfig(reactContext, clientid, null, null, enableSegmentation = false)
    val embetSDk = EmBetSDK(sdkConfig = sdkConfig!!)
     myFragment.createNewSession(embetSDk, programSession = ProgramSession(programID))
  }

  fun setupLayout(view: View) {
    Choreographer.getInstance().postFrameCallback(object: Choreographer.FrameCallback {
      override fun doFrame(frameTimeNanos: Long) {
        manuallyLayoutChildren(view)
        view.viewTreeObserver.dispatchOnGlobalLayout()
        Choreographer.getInstance().postFrameCallback(this)
      }
    })
  }

  /**
   * Layout all children properly
   */
  private fun manuallyLayoutChildren(view: View) {
    // propWidth and propHeight coming from react-native props
    val width = requireNotNull(propWidth)
    val height = requireNotNull(propHeight)

    view.measure(
      View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
      View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY))

    view.layout(0, 0, width, height)
  }

  companion object {
    private const val REACT_CLASS = "TestFragmentManager"
    private const val COMMAND_CREATE = 1
  }
}
