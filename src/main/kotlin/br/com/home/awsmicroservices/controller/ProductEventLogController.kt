package br.com.home.awsmicroservices.controller

import br.com.home.awsmicroservices.repository.ProductEventLogRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ProductEventLogController(private val productEventLogRepository: ProductEventLogRepository) {


}