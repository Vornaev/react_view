package com.embet.mod

import com.embet.ReactEmbetConfigurator
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.module.annotations.ReactModule

class EmbetReactSdkModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext){

  override fun getName() : String {
    return "EmbetModuleSDK"
  }


  @ReactMethod
  fun setAuthToken(token:String){
    ReactEmbetConfigurator.authToken = token
  }

  @ReactMethod
  fun updateCustomData(data:String ,Promise()){

  }

}