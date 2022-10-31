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
import com.livelike.mobile.embet.publicapi.EmBetContentSession
import com.livelike.mobile.embet.publicapi.EmBetInstance
import com.livelike.mobile.embet.publicapi.ProgramSession
import java.util.logging.Handler

class TestFragmentManager(
  private val reactContext : ReactApplicationContext
) : ViewGroupManager<FrameLayout>() {
  private var propWidth : Int? = null
  private var propHeight : Int? = null
  private val embetFragment by lazy { EmBetReactWidgetFragment() }
  private var embetSDK : EmBetSDK? = null


  override fun getName() = "TestFragmentManager"

  /**
   * Return a FrameLayout which will later hold the Fragment
   */
  override fun createViewInstance(reactContext : ThemedReactContext) =
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
    root : FrameLayout,
    commandId : String,
    args : ReadableArray?
  ) {
    super.receiveCommand(root, commandId, args)
    val reactNativeViewId = requireNotNull(args).getInt(0)

    when (commandId.toInt()) {
      COMMAND_CREATE -> createFragment(root, reactNativeViewId)
    }
  }

  @ReactProp(name = "clientId")
  fun setClientID(view : FrameLayout, clientID : String) {
   // ReactEmbetConfigurator.clientID = clientID
  }

  @ReactProp(name = "programId")
  fun setProgramID(view : FrameLayout, programID : String) {
    //ReactEmbetConfigurator.programID = programID
  }


//  @ReactProp(name = "segmentationFeature")
//  fun setSegmentation(view : FrameLayout, segmentation:String){
//
//    segmentation.toLowerCase()
//    //of or on
//
//  }

  @ReactPropGroup(names = ["width", "height"], customType = "Style")
  fun setStyle(view : FrameLayout, index : Int, value : Int) {
    if (index == 0) propWidth = value
    if (index == 1) propHeight = value
  }

  /**
   * Replace your React Native view with a custom fragment
   */
  fun createFragment(root : FrameLayout, reactNativeViewId : Int) {
    val parentView = root.findViewById<ViewGroup>(reactNativeViewId)
    setupLayout(parentView)


    val activity = reactContext.currentActivity as FragmentActivity
    activity.supportFragmentManager
      .beginTransaction()
      .replace(reactNativeViewId, embetFragment, reactNativeViewId.toString())
      .commit()

      testSDK()
      //connectSDK()
  }


  private fun connectSDK(){


    val sdkConfig = ReactEmbetConfigurator.getSDKConfig(reactContext)
    sdkConfig?.let { config ->

      embetSDK = EmBetSDK(config)
      ReactEmbetConfigurator.programID?.let {
        embetFragment.createNewSession(requireNotNull(embetSDK), programSession = ProgramSession(it))

      }
    }
  }

  private fun testSDK(){
    val clientid = "mOBYul18quffrBDuq2IACKtVuLbUzXIPye5S3bq5"
    val programID = "7b7aa007-4a9b-497a-9c55-ec819a91b475"

    ReactEmbetConfigurator.clientID = clientid
    ReactEmbetConfigurator.programID = programID

    connectSDK()

    //android.os.Handler().postDelayed(Runnable { embetFragment.displayWidget(demo_json)  }, 5000)


  }

  fun setupLayout(view : View) {
    Choreographer.getInstance().postFrameCallback(object : Choreographer.FrameCallback {
      override fun doFrame(frameTimeNanos : Long) {
        manuallyLayoutChildren(view)
        view.viewTreeObserver.dispatchOnGlobalLayout()
        Choreographer.getInstance().postFrameCallback(this)
      }
    })
  }

  /**
   * Layout all children properly
   */
  private fun manuallyLayoutChildren(view : View) {
    // propWidth and propHeight coming from react-native props
    val width = requireNotNull(view.layoutParams.width)
    val height = requireNotNull(view.layoutParams.height)

    view.measure(
      View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
      View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
    )

    view.layout(0, 0, width, height)
  }

  companion object {
    private const val REACT_CLASS = "TestFragmentManager"
    private const val COMMAND_CREATE = 1

    private val CLIENT_ID = "RuHpZaHvgP252o0oMw75cmaZAQ1pjG34UdyyqDF2"
    private val PROGRAM_ID = "4e9c93eb-8863-4df1-beea-e9ba10fe61aa"

    val demo_json = "{\n" +
      "  \"url\": \"https://cf-blast.livelikecdn.com/api/v1/text-predictions/74bca9b6-a507-4194-8229-4fdc7a8b3e6a/\",\n" +
      "  \"created_at\": \"2022-02-25T13:05:50.707Z\",\n" +
      "  \"widget_attributes\": [],\n" +
      "  \"kind\": \"image-prediction\",\n" +
      "  \"options\": [\n" +
      "    {\n" +
      "      \"translatable_fields\": [\n" +
      "        \"description\"\n" +
      "      ],\n" +
      "      \"image_url\": \"https://cf-blast-storage-qa.livelikecdn.com/assets/65c28c9a-e1fe-437c-add2-3d48be4a56e2.png\",\n" +
      "      \"is_correct\": false,\n" +
      "      \"earnable_rewards\": [],\n" +
      "      \"vote_url\": \"https://blastrt-prod-us-east-1.livelikecdn.com/api/v1/widget-interactions/text-prediction/74bca9b6-a507-4194-8229-4fdc7a8b3e6a/options/41da5f7c-c50a-4c0c-8830-596c5d8feb41/votes/\",\n" +
      "      \"description\": \"Astros\",\n" +
      "      \"vote_count\": 0,\n" +
      "      \"id\": \"1\",\n" +
      "      \"translations\": {}\n" +
      "    },\n" +
      "    {\n" +
      "      \"translatable_fields\": [\n" +
      "        \"description\"\n" +
      "      ],\n" +
      "      \"image_url\": \"https://cf-blast-storage.livelikecdn.com/assets/d6cd9a04-d4a5-49ca-92f5-581954e0533b.png\",\n" +
      "      \"is_correct\": false,\n" +
      "      \"earnable_rewards\": [],\n" +
      "      \"vote_url\": \"https://blastrt-prod-us-east-1.livelikecdn.com/api/v1/widget-interactions/text-prediction/74bca9b6-a507-4194-8229-4fdc7a8b3e6a/options/41da5f7c-c50a-4c0c-8830-596c5d8feb41/votes/\",\n" +
      "      \"description\": \"Draw\",\n" +
      "      \"vote_count\": 0,\n" +
      "      \"id\": \"2\",\n" +
      "      \"translations\": {}\n" +
      "    },\n" +
      "    {\n" +
      "      \"translatable_fields\": [\n" +
      "        \"description\"\n" +
      "      ],\n" +
      "      \"image_url\": \"https://cf-blast-storage-qa.livelikecdn.com/assets/85632f6e-55c7-4704-b5b8-59da18bc3c57.png\",\n" +
      "      \"is_correct\": false,\n" +
      "      \"earnable_rewards\": [],\n" +
      "      \"vote_url\": \"https://blastrt-prod-us-east-1.livelikecdn.com/api/v1/widget-interactions/text-prediction/74bca9b6-a507-4194-8229-4fdc7a8b3e6a/options/e0d944ca-5a9c-40a9-a40c-b42461eeefb8/votes/\",\n" +
      "      \"description\": \"Dodgers\",\n" +
      "      \"vote_count\": 0,\n" +
      "      \"id\": \"3\",\n" +
      "      \"translations\": {}\n" +
      "    }\n" +
      "  ],\n" +
      "  \"question\": \"Who will win tonight?\",\n" +
      "  \"timeout\": \"P0DT00H00M30S\",\n" +
      "  \"sponsors\": [],\n" +
      "  \"reactions\": [],\n" +
      "  \"impression_url\": \"https://blastrt-prod-us-east-1.livelikecdn.com/api/v1/widget-impressions/text-prediction/74bca9b6-a507-4194-8229-4fdc7a8b3e6a/\",\n" +
      "  \"client_id\": \"RuHpZaHvgP252o0oMw75cmaZAQ1pjG34UdyyqDF2\",\n" +
      "  \"widget_interactions_url_template\": \"https://cf-blast.livelikecdn.com/api/v1/profiles/{profile_id}/widget-interactions/?text_prediction_id=74bca9b6-a507-4194-8229-4fdc7a8b3e6a\",\n" +
      "  \"publish_delay\": \"P0DT00H00M00S\",\n" +
      "  \"published_at\": \"2022-02-25T13:05:50.960Z\",\n" +
      "  \"scheduled_at\": \"2022-02-25T13:05:50.823Z\",\n" +
      "  \"translations\": {},\n" +
      "  \"translatable_fields\": [\n" +
      "    \"question\",\n" +
      "    \"confirmation_message\"\n" +
      "  ],\n" +
      "  \"rewards_url\": null,\n" +
      "  \"engagement_count\": 0,\n" +
      "  \"unique_impression_count\": 0,\n" +
      "  \"engagement_percent\": \"0.000\",\n" +
      "  \"subscribe_channel\": \"program.widget.74bca9b6-a507-4194-8229-4fdc7a8b3e6a\",\n" +
      "  \"created_by\": {\n" +
      "    \"image_url\": null,\n" +
      "    \"id\": \"728b47c1-d2b9-4d8c-a823-3e3d92d40ffa\",\n" +
      "    \"name\": null\n" +
      "  },\n" +
      "  \"confirmation_message\": \"Alright! Stay tuned to see the result.\",\n" +
      "  \"program_id\": \"4e9c93eb-8863-4df1-beea-e9ba10fe61aa\",\n" +
      "  \"schedule_url\": \"https://cf-blast.livelikecdn.com/api/v1/text-predictions/74bca9b6-a507-4194-8229-4fdc7a8b3e6a/schedule/\",\n" +
      "  \"follow_up_url\": \"https://cf-blast.livelikecdn.com/api/v1/text-prediction-follow-ups/\",\n" +
      "  \"status\": \"published\",\n" +
      "  \"id\": \"74bca9b6-a507-4194-8229-4fdc7a8b3e6a\",\n" +
      "  \"follow_ups\": [\n" +
      "  ],\n" +
      "  \"custom_data\" : \"{ \\\"labels\\\": {\\n    \\\"sponsoredBy\\\": \\\"Sponsored by:\\\",\\n    \\\"numberOfVotes\\\": \\\"of votes\\\",\\n    \\\"widgetTitle\\\": \\\"\\\"\\n  },\\n  \\\"placeBetUrl\\\": \\\"https://sportradar.com\\\",\\n    \\\"widgetType\\\": \\\"live-moneyline\\\",\\n    \\\"widgetVariation\\\": \\\"bar\\\",\\n    \\\"timer\\\": \\\"round\\\",\\n    \\\"sponsors\\\": [{\\n        \\\"logo_url\\\": \\\"https://djvxfu5jaz3oj.cloudfront.net/images/sportradar-logo-white.png\\\",\\n        \\\"name\\\": \\\"Logo name\\\"\\n    }],\\n    \\\"theme\\\": {\\n        \\\"widgets\\\": {\\n            \\\"prediction\\\": {\\n                \\\"selectedOption\\\": {\\n                    \\\"borderColor\\\": \\\"#D65773\\\",\\n                    \\\"fontWeight\\\": \\\"bold\\\",\\n                    \\\"borderRadius\\\": [5, 5, 5, 5],\\n                    \\\"fontFamily\\\": \\\"Roboto\\\",\\n                    \\\"fontSize\\\": 14,\\n                    \\\"borderWidth\\\": 2,\\n                    \\\"background\\\": {\\n                        \\\"format\\\": \\\"fill\\\",\\n                        \\\"color\\\": \\\"#131338\\\"\\n                    },\\n                    \\\"fontColor\\\": \\\"#FFFFFF\\\"\\n                },\\n                \\\"selectedOptionBar\\\": {\\n                    \\\"borderColor\\\": \\\"#D65773\\\",\\n                    \\\"background\\\": {\\n                        \\\"format\\\": \\\"fill\\\",\\n                        \\\"color\\\": \\\"#C1022C7D\\\" }},\\\"selectedOptionPercentage\\\": {\\\"fontWeight\\\": \\\"normal\\\",\\\"fontFamily\\\": \\\"Roboto\\\",\\n                    \\\"fontSize\\\": 14,\\n                    \\\"fontColor\\\": \\\"#FFFFFF\\\"\\n                },\\n                \\\"unselectedOption\\\": {\\n                    \\\"borderColor\\\": \\\"#D9D9D9\\\",\\n                    \\\"fontWeight\\\": \\\"bold\\\",\\n                    \\\"borderRadius\\\": [5, 5, 5, 5],\\n                    \\\"fontFamily\\\": \\\"Roboto\\\",\\n                    \\\"fontSize\\\": 14,\\n                    \\\"borderWidth\\\": 2,\\n                    \\\"background\\\": {\\n                        \\\"format\\\": \\\"fill\\\",\\n                        \\\"color\\\": \\\"#131338\\\"\\n                    },\\n                    \\\"fontColor\\\": \\\"#FFFFFF\\\"\\n                },\\n                \\\"unselectedOptionBar\\\": {\\n                    \\\"borderColor\\\": \\\"#00FF00\\\",\\n                    \\\"background\\\": {\\n                        \\\"format\\\": \\\"fill\\\",\\n                        \\\"color\\\": \\\"#D9D9D97D\\\"\\n                    }\\n                },\\n                \\\"unselectedOptionPercentage\\\": {\\n                    \\\"fontWeight\\\": \\\"normal\\\",\\n                    \\\"fontFamily\\\": \\\"Roboto\\\",\\n                    \\\"fontSize\\\": 14,\\n                    \\\"fontColor\\\": \\\"#FF0000\\\"\\n                },\\n                \\\"header\\\": {\\n                    \\\"fontWeight\\\": \\\"bold\\\",\\n                    \\\"fontFamily\\\": \\\"Roboto\\\",\\n                    \\\"fontSize\\\": 24,\\n                    \\\"background\\\": {\\n                        \\\"format\\\": \\\"fill\\\",\\n                        \\\"color\\\": \\\"#26265B\\\"\\n                    },\\n                    \\\"fontColor\\\": \\\"#FFFFFF\\\"\\n                },\\n                \\\"title\\\": {\\n                    \\\"fontWeight\\\": \\\"normal\\\",\\n                    \\\"fontFamily\\\": \\\"Roboto\\\",\\n                    \\\"fontSize\\\": 18,\\n                    \\\"fontColor\\\": \\\"#FFFFFF\\\"\\n                },\\n                \\\"body\\\": {\\n                    \\\"fontWeight\\\": \\\"normal\\\",\\n                    \\\"fontFamily\\\": \\\"Roboto\\\",\\n                    \\\"fontSize\\\": 16,\\n                    \\\"background\\\": {\\n                        \\\"format\\\": \\\"fill\\\",\\n                        \\\"color\\\": \\\"#26265B\\\"\\n                    },\\n                    \\\"fontColor\\\": \\\"#FFFFFF\\\"\\n                },\\n                \\\"dismiss\\\": {\\n                    \\\"color\\\": \\\"#FFFFFF\\\"\\n                },\\n                \\\"footer\\\": {\\n                    \\\"fontWeight\\\": \\\"normal\\\",\\n                    \\\"fontFamily\\\": \\\"Roboto\\\",\\n                    \\\"fontSize\\\": 16,\\n                    \\\"background\\\": {\\n                        \\\"format\\\": \\\"fill\\\",\\n                        \\\"color\\\": \\\"#26265B\\\"\\n                    },\\n                    \\\"fontColor\\\": \\\"#FFFFFF\\\"\\n                },\\n                \\\"roundTimer\\\": {\\n                    \\\"height\\\": 30,\\n                    \\\"barWidth\\\": 3,\\n                    \\\"fontFamily\\\": \\\"Roboto\\\",\\n                    \\\"fontWeight\\\": \\\"bold\\\",\\n                    \\\"fontSize\\\": 14,\\n                    \\\"background\\\": {\\n                        \\\"format\\\": \\\"fill\\\",\\n                        \\\"color\\\": \\\"#C1022C\\\"\\n                    },\\n                    \\\"fontColor\\\": \\\"#FFFFFF\\\"\\n                },\\n                \\\"timer\\\": {\\n                    \\\"background\\\": {\\n                        \\\"format\\\": \\\"fill\\\",\\n                        \\\"color\\\": \\\"#C1022C\\\"\\n                    }\\n                },\\n                \\\"insights\\\": {\\n                    \\\"fontWeight\\\": \\\"bold\\\",\\n                    \\\"padding\\\": [0, 15, 10, 15],\\n                    \\\"fontFamily\\\": \\\"Roboto\\\",\\n                    \\\"fontSize\\\": 19,\\n                    \\\"background\\\": {\\n                        \\\"format\\\": \\\"fill\\\",\\n                        \\\"color\\\": \\\"#26265B\\\"\\n                    },\\n                    \\\"fontColor\\\": \\\"#FFFFFF\\\"\\n                },\\n                \\\"insightTable\\\": {\\n                    \\\"fontSize\\\": 14,\\n                    \\\"padding\\\": [0, 15, 0, 15]\\n                },\\n                \\\"insightTableCell\\\": {\\n                    \\\"fontWeight\\\": \\\"normal\\\",\\n                    \\\"fontFamily\\\": \\\"Roboto\\\",\\n                    \\\"fontSize\\\": 14,\\n                    \\\"fontColor\\\": \\\"#FFFFFF\\\"\\n                },\\n                \\\"insightTableCellHighlighted\\\": {\\n                    \\\"background\\\": {\\n                        \\\"format\\\": \\\"fill\\\",\\n                        \\\"color\\\": \\\"#D9D9D97D\\\"\\n                    }\\n                },\\n                \\\"sponsor\\\": {\\n                    \\\"borderRadius\\\": [5, 5, 5, 5],\\n                    \\\"borderColor\\\": \\\"#58587E\\\",\\n                    \\\"background\\\": {\\n                        \\\"format\\\": \\\"fill\\\",\\n                        \\\"color\\\": \\\"#090926\\\"\\n                    },\\n                    \\\"fontFamily\\\": \\\"Roboto\\\"\\n                },\\n                \\\"betButton\\\": {\\n                    \\\"borderColor\\\": \\\"#D65773\\\",\\n                    \\\"background\\\": {\\n                        \\\"format\\\": \\\"fill\\\",\\n                        \\\"color\\\": \\\"#C1022C\\\"\\n                    },\\n                    \\\"fontColor\\\": \\\"#FFFFFF\\\",\\n                    \\\"fontSize\\\": 16,\\n                    \\\"fontWeight\\\": \\\"bold\\\",\\n                    \\\"fontFamily\\\": \\\"Roboto\\\"\\n                }\\n            }\\n        }\\n    },\\n    \\\"betDetails\\\": [{\\n        \\\"bet\\\": \\\"+120\\\",\\n        \\\"description\\\": \\\"Astros\\\"\\n    }, {\\n        \\\"bet\\\": \\\"+360\\\",\\n        \\\"description\\\": \\\"Draw\\\"\\n    }, {\\n        \\\"bet\\\": \\\"+240\\\",\\n        \\\"description\\\": \\\"Dodgers\\\"\\n    }],\\n    \\\"animationDelay\\\": 3000,\\n    \\\"placeBetLabel\\\": \\\"PLACE BET\\\"\\n}\\n\"\n" +
      "}\n" +
      "\n" +
      "\n" +
      "\n"
  }
}
