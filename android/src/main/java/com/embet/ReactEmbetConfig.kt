package com.embet

import android.content.Context
import com.facebook.react.bridge.ReactApplicationContext
import com.livelike.mobile.embet.data.EmBetSDKConfig
import com.livelike.mobile.embet.data.internal.EmBetSDK

object ReactEmbetConfigurator {

  var clientID : String? = null

  var programID : String? = null

  var authToken : String? = null

  var enableSegmentation : Boolean = false

  var SDK:EmBetSDK? = null




  fun getSDKConfig(context : Context) : EmBetSDKConfig? {
    return if (clientID == null) {
        null
    }
    else {
      EmBetSDKConfig(context, requireNotNull(clientID), authToken, null, enableSegmentation = false)
    }
  }

  fun canCreateSession():Boolean{
    return  false
  }

  fun getReason() : String {
    if(clientID == null) return "Missing client iD"
    else return "Missing config ids"
  }
}

class EmbetAdmin(){

  var sdk : EmBetSDK? =null


}
