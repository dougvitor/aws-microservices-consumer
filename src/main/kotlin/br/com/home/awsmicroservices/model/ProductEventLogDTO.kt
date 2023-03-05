package br.com.home.awsmicroservices.model

import br.com.home.awsmicroservices.enums.EventType

data class ProductEventLogDTO(
    private val code: String?,
    private val eventType: EventType,
    private val productId: Long,
    private val username: String,
    private val timestamp: Long
)

fun ProductEventLog.toDto() = ProductEventLogDTO(
    code = this.getPk(),
    eventType = this.eventType,
    productId = this.productId,
    username = this.username,
    timestamp = this.timestamp
)

