# spring-microservice-currency-app
Spring boot sample currency convertor

http://localhost:8080/limitsC

http://localhost:8000/currency-exchange/from/USD/to/inr
http://localhost:8001/currency-exchange/from/USD/to/inr

http://localhost:8000/h2-console


currency-exchange
http://localhost:8000/currency-exchange/from/USD/to/INR
http://localhost:8000/currency-exchange/from/EUR/to/INR
http://localhost:8000/currency-exchange/from/AUD/to/INR
http://localhost:8000/currency-exchange/from/AUD/to/INR
currency-conversion
http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/100
http://localhost:8100/currency-conversion/from/AUD/to/INR/quantity/500
http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/700

/feign/
http://localhost:8100/currency-conversion-feign/from/USD/to/INR/quantity/100
http://localhost:8100/currency-conversion/feign/from/USD/to/INR/quantity/54


EUREKA DISCOVERY SERVER
http://localhost:8761/

API gateway
http://localhost:8765/
url pattern
Initial
 



only change we did is added this property in api gateway  
spring.cloud.gateway.discovery.locator.enabled=true

currency exchange from api gateway
http://localhost:8765/CURRENCY-EXCHANGE/currency-exchange/db/from/USD/to/INR

currency Conversion  from api gateway
http://localhost:8765/CURRENCY-CONVERSION/currency-conversion/feign/from/USD/to/INR/quantity/10

to make it lower case
spring.cloud.gateway.discovery.locator.lowerCaseServiceId=true
http://localhost:8765/currency-conversion/currency-conversion/feign/from/USD/to/INR/quantity/10

http://localhost:8765/currency-exchange/currency-exchange/db/from/USD/to/INR
http://localhost:8765/currency-conversion/feign/from/USD/to/INR/quantity/55
routes in api gateway

http://localhost:8765/currency-conversion/feign/from/USD/to/INR/quantity/50

disable 
spring.cloud.gateway.discovery.locator.lowerCaseServiceId=true & spring.cloud.gateway.discovery.locator.lowerCaseServiceId=true
define routes like this
 @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        Function<PredicateSpec, Buildable<Route>> routeFunction =
                p -> p.path("get")
                        .uri("http://httpbin.org:80")
                ;
        return builder.routes()
                .route(routeFunction)
                   .route(p->p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p->p.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion"))
                .route(p->p.path("/currency-exchange/**")
                        .uri("lb://currency-exchange"))
                .route(p->p.path("/currency-exchange2/**")
                        .uri("lb://currency-exchange2/sample-api"))
                .build();


http://localhost:8765/currency-conversion/feign/from/USD/to/INR/quantity/786
