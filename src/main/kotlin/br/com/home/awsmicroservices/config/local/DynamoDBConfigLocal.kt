package br.com.home.awsmicroservices.config.local

import br.com.home.awsmicroservices.repository.ProductEventLogRepository
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.model.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile

@Configuration
@EnableDynamoDBRepositories(basePackageClasses = [ProductEventLogRepository::class])
@Profile("local")
class DynamoDBConfigLocal {

    private val logger: Logger = LoggerFactory.getLogger(DynamoDBConfigLocal::class.java)

    private val amazonDynamoDB: AmazonDynamoDB = AmazonDynamoDBClient.builder()
        .withEndpointConfiguration(
            AwsClientBuilder.EndpointConfiguration(
                "http://localhost:4566",
                Regions.US_EAST_1.getName()
            )
        ).withCredentials(DefaultAWSCredentialsProviderChain()).build()

    init {
        val dynamoDB: DynamoDB = DynamoDB(amazonDynamoDB)

        val attributeDefinitions: List<AttributeDefinition> = listOf(
            AttributeDefinition().withAttributeName("pk").withAttributeType(ScalarAttributeType.S),
            AttributeDefinition().withAttributeName("sk").withAttributeType(ScalarAttributeType.S)
        )

        val keySchemas: List<KeySchemaElement> = listOf(
            KeySchemaElement().withAttributeName("pk").withKeyType(KeyType.HASH),
            KeySchemaElement().withAttributeName("sk").withKeyType(KeyType.RANGE)
        )

        val createTableRequest = CreateTableRequest().apply {
            withTableName("product-events")
            withKeySchema(keySchemas)
            withAttributeDefinitions(attributeDefinitions)
            withBillingMode(BillingMode.PAY_PER_REQUEST)
        }

        val table = dynamoDB.createTable(createTableRequest)

        try {
            table.waitForActive()
        }catch (exception: InterruptedException){
            logger.error(exception.message)
        }

    }

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
    fun amazonDynamoDB(): AmazonDynamoDB = this.amazonDynamoDB
}


