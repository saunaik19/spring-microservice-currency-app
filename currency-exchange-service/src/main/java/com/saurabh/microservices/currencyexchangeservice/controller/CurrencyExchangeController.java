package com.saurabh.microservices.currencyexchangeservice.controller;

import com.saurabh.microservices.currencyexchangeservice.dao.CurrencyExchangeRepository;
import com.saurabh.microservices.currencyexchangeservice.dto.CurrencyExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequestMapping("/currency-exchange")
@RestController
public class CurrencyExchangeController {

    @Autowired
    Environment environment;

    @Autowired
    CurrencyExchangeRepository currencyExchangeRepository;
/*
    @GetMapping("/from/{from}/to/{to}")
    public CurrencyExchange retriveExchange(@PathVariable("from") String from,
                                            @PathVariable("to") String to){
        CurrencyExchange currencyExchange=new CurrencyExchange(1000L,"USD","INR", BigDecimal.valueOf(75));
        String port=getInstancePort();
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
*/
    @GetMapping("/from/{from}/to/{to}")
    public CurrencyExchange retriveExchangeFromDb(@PathVariable("from") String from,
                                            @PathVariable("to") String to){
        CurrencyExchange currencyExchange=currencyExchangeRepository.findByFromAndTo(from,to);
        String port= getInstancePort();
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }

    private String getInstancePort() {
        return environment.getProperty("local.server.port");
    }
}
