package br.com.home.awsmicroservices.config

import br.com.home.awsmicroservices.repository.ProductEventLogRepository
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
@EnableDynamoDBRepositories(basePackageClasses = [ProductEventLogRepository::class])
class DynamoDBConfig(
    @Value("\${aws.region}")
    private val awsRegion: String
) {

    @Bean
    @Primary
    fun dynamoDBMapperConfig(): DynamoDBMapperConfig = DynamoDBMapperConfig.DEFAULT

    @Bean
    @Primary
    fun dynamoDBMapper(
        amazonDynamoDB: AmazonDynamoDB,
        mapperConfig: DynamoDBMapperConfig
    ): DynamoDBMapper = DynamoDBMapper(amazonDynamoDB, mapperConfig)

    @Bean
    @Primary
    fun amazonDynamoDB(): AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
        .withCredentials(DefaultAWSCredentialsProviderChain())
        .withRegion(Regions.fromName(awsRegion)).build()
}
