package br.com.home.awsmicroservices.repository

import br.com.home.awsmicroservices.model.ProductEventKey
import br.com.home.awsmicroservices.model.ProductEventLog
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface ProductEventLogRepository : CrudRepository<ProductEventLog, ProductEventKey> {

    fun findAllByPk(code: String): List<ProductEventLog>

    fun findAllByPkAndSkStartsWith(code: String, eventType: String)

}