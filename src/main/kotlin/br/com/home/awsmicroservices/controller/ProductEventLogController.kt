package br.com.home.awsmicroservices.controller

import br.com.home.awsmicroservices.model.toDto
import br.com.home.awsmicroservices.repository.ProductEventLogRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ProductEventLogController(private val productEventLogRepository: ProductEventLogRepository) {

    @GetMapping("/events")
    fun getAllEvents() = productEventLogRepository.findAll().map {
        it.toDto()
    }

    @GetMapping("/events/{code}")
    fun findByCode(@PathVariable code: String) = productEventLogRepository.findAllByPk(code).map {
        it.toDto()
    }

    @GetMapping("/events/{code}/{event}")
    fun findByCode(@PathVariable code: String, @PathVariable event: String) =
        productEventLogRepository.findAllByPkAndSkStartsWith(code, event).map {
            it.toDto()
        }

}