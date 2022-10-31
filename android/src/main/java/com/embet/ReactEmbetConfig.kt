package com.embet

import com.facebook.react.bridge.ReactApplicationContext
import com.livelike.mobile.embet.data.EmBetSDKConfig

object ReactEmbetConfigurator {

  var clientID : String? = null

  var programID : String? = null

  var authToken : String? = null

  var enableSegmentation : Boolean = false


  fun getSDKConfig(context : ReactApplicationContext) : EmBetSDKConfig? {
    return if (clientID == null) {
        null
    }
    else {
      EmBetSDKConfig(context, requireNotNull(clientID), authToken, null, enableSegmentation = false)
    }
  }
}
