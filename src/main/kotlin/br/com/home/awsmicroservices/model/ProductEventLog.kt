package br.com.home.awsmicroservices.model

import br.com.home.awsmicroservices.enums.EventType
import com.amazonaws.services.dynamodbv2.datamodeling.*
import org.springframework.data.annotation.Id

@DynamoDBTable(tableName = "product-events")
data class ProductEventLog(

    @field:Id
    @DynamoDBIgnore
    var productEventKey: ProductEventKey = ProductEventKey(),

    @DynamoDBTypeConvertedEnum
    @get:DynamoDBAttribute(attributeName = "eventType")
    var eventType: EventType,

    @get:DynamoDBAttribute(attributeName = "productId")
    var productId: Long,

    @get:DynamoDBAttribute(attributeName = "username")
    var username: String,

    @get:DynamoDBAttribute(attributeName = "timestamp")
    var timestamp: Long,

    @get:DynamoDBAttribute(attributeName = "ttl")
    var ttl: Long
) {
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
