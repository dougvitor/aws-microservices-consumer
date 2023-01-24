package br.com.home.awsmicroservices

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AwsMicroservicesConsumerApplication

fun main(args: Array<String>) {
	runApplication<AwsMicroservicesConsumerApplication>(*args)
}
