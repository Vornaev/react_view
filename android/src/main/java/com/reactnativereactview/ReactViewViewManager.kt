package com.reactnativereactview
import android.graphics.Color
import android.view.View
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp

class ReactViewViewManager : SimpleViewManager<View>() {

  override fun getName() = "ReactViewView"

  override fun createViewInstance(reactContext: ThemedReactContext): View {
    return View(reactContext)
  }

  @ReactProp(name = "color")
  fun setColor(view: View, color: String) {
    view.setBackgroundColor(Color.parseColor(color))
  }

var user_clientID:String = "";


  @ReactProp(name = "clientId")
  fun setClientID(view: View, clientId: String) {
    user_clientID = clientId
  }

  var user_programID:String = "";
  @ReactProp(name = "programId")
  fun setProgramId(view: View, programId: String) {
    user_programID = programId
  }

}
