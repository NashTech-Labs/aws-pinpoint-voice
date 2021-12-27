package org.knoldus

import scala.concurrent.Future
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.pinpoint.model.PinpointException
import software.amazon.awssdk.services.pinpointsmsvoice.PinpointSmsVoiceClient
import software.amazon.awssdk.services.pinpointsmsvoice.model.{BadRequestException, SSMLMessageType, SendVoiceMessageRequest, SendVoiceMessageResponse, VoiceMessageContent}

import java.util
import scala.concurrent.ExecutionContext.Implicits.global

class Pinpoint {


  def makeCall(body: String, toNumber: String): Future[Response] = {

    val languageCode: String = "en-US"
    val fromNumber = "1234567890" // Get originationNumber from aws pinpoint project setting
    val client: PinpointSmsVoiceClient = createPinpointClient
    val ssmlTypeBody = s"<speak>$body</speak>"


    try {
      val ssmlMessageType: SSMLMessageType = createMessageType(
        languageCode,
        ssmlTypeBody
      )
      val content: VoiceMessageContent = createContent(ssmlMessageType)
      val voiceMessageRequest: SendVoiceMessageRequest = createVoiceMessageRequest(
        toNumber,
        fromNumber,
        content
      )

      sendVoiceMessageRequest(client, voiceMessageRequest)
      Future(Successful)
    }
    catch {
      case e: BadRequestException =>
        Future(Failed)
      case ex: PinpointException =>
        Future(Failed)
      case exception: Exception =>
        Future(Failed)
    }
    finally {
      client.close()
    }

  }


  def createPinpointClient: PinpointSmsVoiceClient = {
    val list: util.List[String] = new util.ArrayList[String]()
    list.add("application/json")
    val values: util.Map[String, util.List[String]] = new util.HashMap[String, util.List[String]]()
    values.put("Content-Type", list)
    val clientConfig: ClientOverrideConfiguration = ClientOverrideConfiguration.builder()
      .headers(values)
      .build()

    PinpointSmsVoiceClient.builder()
      .overrideConfiguration(clientConfig)
      .region(Region.US_EAST_1)
      .build()
  }

  protected def createMessageType(
                                   languageCode: String,
                                   ssmlMessage: String
                                 ): SSMLMessageType = {
    SSMLMessageType.builder()
      .languageCode(languageCode)
      .text(ssmlMessage)
      .build()
  }

  protected def createContent(ssmlMessageType: SSMLMessageType): VoiceMessageContent = {
    VoiceMessageContent.builder()
      .ssmlMessage(ssmlMessageType)
      .build()
  }

  protected def createVoiceMessageRequest(
                                           destinationNumber: String,
                                           originationNumber: String,
                                           content: VoiceMessageContent
                                         ): SendVoiceMessageRequest = {
    SendVoiceMessageRequest.builder()
      .destinationPhoneNumber(destinationNumber)
      .originationPhoneNumber(originationNumber)
      .content(content)
      .build()
  }

  def sendVoiceMessageRequest(
                               client: PinpointSmsVoiceClient,
                               voiceMessageRequest: SendVoiceMessageRequest
                             ): SendVoiceMessageResponse = {
    client.sendVoiceMessage(voiceMessageRequest)
  }


}
