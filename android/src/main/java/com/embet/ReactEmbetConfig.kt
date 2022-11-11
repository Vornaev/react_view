package com.embet

import android.content.Context
import com.facebook.react.bridge.ReactApplicationContext
import com.livelike.mobile.embet.data.EmBetErrorListener
import com.livelike.mobile.embet.data.EmBetSDKConfig
import com.livelike.mobile.embet.data.IEmBetAuthTokenDelegate
import com.livelike.mobile.embet.data.internal.EmBetSDK
import com.livelike.mobile.embet.publicapi.EmBetContentSession
import com.livelike.mobile.embet.publicapi.ProgramSession

object ReactEmbetConfigurator {

  var clientID : String? = null

  var programID : String? = null

  var authToken : String? = null

  var enableSegmentation : Boolean = true

  var SDK : EmBetSDK? = null

  var session : EmBetContentSession? = null


  fun getSDKConfig(context : Context) : EmBetSDKConfig? {
      return  clientID?.let { EmBetSDKConfig(context, requireNotNull(clientID), authToken, null, enableSegmentation = false,
      tokenDelegate, errDelegate)}
  }

  val tokenDelegate = object : IEmBetAuthTokenDelegate{
    override fun onGetToken() : String? {
      return authToken
    }

    override fun onSaveToken(token : String?) {
      println("token $token")
    }
  }

  val errDelegate = object : EmBetErrorListener{
    override fun onError(error : Error) {
      println(error)
    }
  }

  fun createSession(programID : String) {
    session = SDK?.createNewSession(ProgramSession(programID))
  }

  fun configure(clientID:String){

  }

  fun canCreateSession() : Boolean {
    return false
  }

  fun getReason() : String {
    if (clientID == null) return "Missing client iD"
    else return "Missing config ids"
  }

  fun updateSDK() {

  }
}

class EmbetAdmin() {

  var sdk : EmBetSDK? = null


}


































