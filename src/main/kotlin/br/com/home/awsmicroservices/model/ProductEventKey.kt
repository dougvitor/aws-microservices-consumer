package br.com.home.awsmicroservices.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey

@DynamoDBDocument
data class ProductEventKey (

    @field:DynamoDBHashKey(attributeName = "pk")
    var pk: String? = null,

    @field:DynamoDBRangeKey(attributeName = "sk")
    var sk: String? = null
)
