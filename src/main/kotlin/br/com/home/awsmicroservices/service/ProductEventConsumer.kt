package br.com.home.awsmicroservices.service

import br.com.home.awsmicroservices.model.Envelope
import br.com.home.awsmicroservices.model.ProductEvent
import br.com.home.awsmicroservices.model.ProductEventLog
import br.com.home.awsmicroservices.model.SnsMessage
import br.com.home.awsmicroservices.repository.ProductEventLogRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import javax.jms.TextMessage

@Service
class ProductEventConsumer(
    private val objectMapper: ObjectMapper,
    private val productEventLogRepository: ProductEventLogRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(ProductEventConsumer::class.java)

    @JmsListener(destination = "\${aws.sqs.queue.product.events.name}")
    fun receiveProductEvent(textMessage: TextMessage) {

        val snsMessage = objectMapper.readValue(textMessage.text, SnsMessage::class.java)

        val envelope = objectMapper.readValue(snsMessage.message, Envelope::class.java)

        val productEvent = objectMapper.readValue(envelope.data, ProductEvent::class.java)

        logger.info(
            "Product event received - MessageId: {} - Event: {} - ProductId: {}",
            snsMessage.messageId, envelope.eventType, productEvent.productId
        )

        productEventLogRepository.save(buildProductEventLog(envelope, productEvent, snsMessage.messageId))
    }

    private fun buildProductEventLog(envelope: Envelope, productEvent: ProductEvent, messageId: String): ProductEventLog {
        val timestamp = Instant.now().toEpochMilli()

        return ProductEventLog().apply {
            eventType = envelope.eventType
            productId = productEvent.productId
            username = productEvent.username
            this.timestamp = timestamp
            ttl = Instant.now().plus(Duration.ofMinutes(10)).epochSecond
            this.messageId = messageId
            setPk(productEvent.code)
            setSk("${envelope.eventType}-$timestamp")
        }
    }
}