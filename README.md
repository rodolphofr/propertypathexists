# Spring Conditional Property Path Exists Annotation

Annotation for initialization of classes and methods through the existence of a property path.

## Pre-requirements

1. Java 11+
2. Maven 3.8+
3. Spring Boot 3+

## Example

There may be cases where it is necessary to determine the initialization of a class by the property path:

```java
@Configuration
@ConditionalOnPropertyPathExist("spring.cloud.azure")
class AzureConfiguration {
    ...
}

@Configuration
@ConditionalOnPropertyPathExist("spring.cloud.stream.rabbit")
class RabbitMqConfiguration {
    ...
}
```