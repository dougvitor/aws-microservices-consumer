package br.com.home.awsmicroservices.service

import br.com.home.awsmicroservices.model.Envelope
import br.com.home.awsmicroservices.model.ProductEvent
import br.com.home.awsmicroservices.model.SnsMessage
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service
import javax.jms.TextMessage

@Service
class ProductEventConsumer(private val objectMapper: ObjectMapper) {
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
    }
}