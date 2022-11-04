package com.embet

import android.content.Context
import com.facebook.react.bridge.ReactApplicationContext
import com.livelike.mobile.embet.data.EmBetSDKConfig
import com.livelike.mobile.embet.data.internal.EmBetSDK
import com.livelike.mobile.embet.publicapi.EmBetContentSession
import com.livelike.mobile.embet.publicapi.ProgramSession

object ReactEmbetConfigurator {

  var clientID : String? = null

  var programID : String? = null

  var authToken : String? = null

  var enableSegmentation : Boolean = false

  var SDK : EmBetSDK? = null

  var session : EmBetContentSession? = null


  fun getSDKConfig(context : Context) : EmBetSDKConfig? {
      return  clientID?.let { EmBetSDKConfig(context, requireNotNull(clientID), authToken, null, enableSegmentation = false)}
  }

  fun createSession(programID : String) {
    session = SDK?.createNewSession(ProgramSession(programID))
  }

  fun canCreateSession() : Boolean {
    return false
  }

  fun getReason() : String {
    if (clientID == null) return "Missing client iD"
    else return "Missing config ids"
  }
}

class EmbetAdmin() {

  var sdk : EmBetSDK? = null


}




interface ReactEmbetAccessTokenDelegate{

  fun getToken() :String

  fun setToken(token:String)

}



}


























