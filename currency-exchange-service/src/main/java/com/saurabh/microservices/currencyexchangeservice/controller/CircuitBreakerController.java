package com.saurabh.microservices.currencyexchangeservice.controller;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RequestMapping("/currency-exchange2")
@RestController
public class CircuitBreakerController {

    private Logger logger= LoggerFactory.getLogger(CircuitBreakerController.class);
    @GetMapping("/sample-api")
    //@Retry(name = "default",fallbackMethod = "hardCodedResponse")
    @CircuitBreaker(name = "default",fallbackMethod = "hardCodedResponse")
    public String getSample(){
        logger.info("Sample api call received....");
        ResponseEntity<String> responseEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummu-url",
                String.class);
        return responseEntity.getBody();
    }


    public String hardCodedResponse(Exception ex){
        return "This service is sleeping, try after sometime";
    }
}
