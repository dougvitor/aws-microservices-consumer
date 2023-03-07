package br.com.home.awsmicroservices.model

import br.com.home.awsmicroservices.enums.EventType

data class ProductEventLogDTO(
    val code: String?,
    val eventType: EventType?,
    val productId: Long?,
    val username: String?,
    val timestamp: Long?,
    val messageId: String?
)

fun ProductEventLog.toDto() = ProductEventLogDTO(
    code = this.getPk(),
    eventType = this.eventType,
    productId = this.productId,
    username = this.username,
    timestamp = this.timestamp,
    messageId = this.messageId
)

