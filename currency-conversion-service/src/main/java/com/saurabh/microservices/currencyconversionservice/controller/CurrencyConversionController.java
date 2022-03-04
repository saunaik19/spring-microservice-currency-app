package com.saurabh.microservices.currencyconversionservice.controller;

import com.saurabh.microservices.currencyconversionservice.dto.CurrencyConversion;
import com.saurabh.microservices.currencyconversionservice.fiegnproxies.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {
    /*
        @GetMapping("/from/{from}/to/{to}/quantity/{quantity}")
        public CurrencyConversion calculateCurrencyConverison(
                @PathVariable("from") String from,
                @PathVariable("to") String to,
                @PathVariable("quantity") BigDecimal quantity ) {
            return new CurrencyConversion(1000L,from,to,quantity,BigDecimal.ONE,BigDecimal.ONE,"");
        }
    */
    //CALLING IT HERE
    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConverisonUsingMS(
            @PathVariable("from") String from,
            @PathVariable("to") String to,
            @PathVariable("quantity") BigDecimal quantity) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversion> currencyConversionResponseEntity = new RestTemplate()
                .getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}"
                        , CurrencyConversion.class
                        , uriVariables);
        CurrencyConversion currencyConversion = currencyConversionResponseEntity.getBody();


        calculateonQuantity(quantity, currencyConversion);
        System.out.println(currencyConversion.toString());
        return currencyConversion;
    }

    private void calculateonQuantity(BigDecimal quantity, CurrencyConversion currencyConversion) {
        currencyConversion.setQuantity(quantity);
        currencyConversion.setTotalCalculatedAmount(quantity.multiply(currencyConversion.getConversionMultiple()));
    }

    //USING FEIGN CLEINT
    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;
    @GetMapping("/currency-conversion/feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConverisonUsingFEIGN(
            @PathVariable("from") String from,
            @PathVariable("to") String to,
            @PathVariable("quantity") BigDecimal quantity) {

        CurrencyConversion currencyConversion = currencyExchangeProxy.retriveExchangeFromDb(from, to);
        calculateonQuantity(quantity,currencyConversion);
        currencyConversion.setEnvironment(currencyConversion.getEnvironment().concat(" FEIGN CALL"));
        return currencyConversion;
    }

}
