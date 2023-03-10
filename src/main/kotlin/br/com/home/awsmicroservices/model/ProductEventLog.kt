package br.com.home.awsmicroservices.model

import br.com.home.awsmicroservices.enums.EventType
import com.amazonaws.services.dynamodbv2.datamodeling.*
import org.springframework.data.annotation.Id

@DynamoDBTable(tableName = "product-events")
class ProductEventLog {

    @field:Id
    @DynamoDBIgnore
    var productEventKey: ProductEventKey = ProductEventKey()

    @DynamoDBTypeConvertedEnum
    @get:DynamoDBAttribute(attributeName = "eventType")
    var eventType: EventType? = null

    @get:DynamoDBAttribute(attributeName = "productId")
    var productId: Long?  = null

    @get:DynamoDBAttribute(attributeName = "username")
    var username: String?  = null

    @get:DynamoDBAttribute(attributeName = "timestamp")
    var timestamp: Long?  = null

    @get:DynamoDBAttribute(attributeName = "ttl")
    var ttl: Long?  = null

    @get:DynamoDBAttribute(attributeName = "messageId")
    var messageId: String?  = null

    @DynamoDBHashKey(attributeName = "pk")
    fun getPk()= this.productEventKey.pk

    fun setPk(pk: String) {
        this.productEventKey.pk = pk
    }

    @DynamoDBRangeKey(attributeName = "sk")
    fun getSk() = productEventKey.sk

    fun setSk(sk: String) {
        this.productEventKey.sk = sk
    }
}
