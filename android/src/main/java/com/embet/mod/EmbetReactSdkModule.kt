package com.embet.mod

import com.embet.ReactEmbetConfigurator
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.livelike.mobile.embet.data.EmBetSDKConfig
import com.livelike.mobile.embet.data.internal.EmBetSDK

class EmbetReactSdkModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext){

  override fun getName() : String {
    return "EmbetModuleSDK"
  }


  @ReactMethod
  fun configure(promise : Promise){

   // EmBetSDK(EmBetSDKConfig(reactApplicationContext, ReactEmbetConfigurator))
  }

  @ReactMethod
  fun setAuthToken(token:String, promise: Promise){
    ReactEmbetConfigurator.authToken = token
  }

  @ReactMethod
  fun updateCustomData(data:String , promise: Promise){
    ReactEmbetConfigurator.updateSDK()
  }

}
