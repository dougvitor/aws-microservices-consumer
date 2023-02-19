package br.com.home.awsmicroservices.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class SnsMessage(
    @JsonProperty("Message")
    private val message: String,
    @JsonProperty("Type")
    private val type: String,
    @JsonProperty("TopicArn")
    private val topicArn: String,
    @JsonProperty("Timestamp")
    private val timestamp: String,
    @JsonProperty("MessageId")
    private val messageId: String
)
