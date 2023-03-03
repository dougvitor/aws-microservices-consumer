package br.com.home.awsmicroservices.model

import br.com.home.awsmicroservices.enums.EventType
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum
import org.springframework.data.annotation.Id

@DynamoDBTable(tableName = "product-events")
class ProductEventLog {

    @Id
    private val productEventKey: ProductEventKey? = null
        get() = when (field) {
            null -> ProductEventKey()
            else -> field
        }

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "eventType")
    var eventType: EventType? = null

    @DynamoDBAttribute(attributeName = "productId")
    var productId: Long? = null

    @DynamoDBAttribute(attributeName = "username")
    var username: String? = null

    @DynamoDBAttribute(attributeName = "timestamp")
    var timestamp: Long? = null

    @DynamoDBAttribute(attributeName = "ttl")
    var ttl: Long? = null

    @DynamoDBAttribute(attributeName = "pk")
    fun getPk(): String? = this.productEventKey?.getPk()

    fun setPk(pk: String) {
        this.productEventKey?.setPk(pk)
    }

    @DynamoDBAttribute(attributeName = "sk")
    fun getSk(): String? = productEventKey?.getSk()

    fun setSk(sk: String) {
        this.productEventKey?.setSk(sk)
    }

}
