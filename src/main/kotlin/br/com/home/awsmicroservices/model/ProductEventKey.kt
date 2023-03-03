package br.com.home.awsmicroservices.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey

class ProductEventKey{

    private var pk: String? = null

    private var sk: String? = null

    @DynamoDBHashKey(attributeName = "pk")
    fun getPk(): String? = this.pk

    fun setPk(pk: String){
        this.pk = pk
    }

    @DynamoDBRangeKey(attributeName = "sk")
    fun getSk(): String? = this.sk

    fun setSk(sk: String){
        this.sk = sk
    }


}
