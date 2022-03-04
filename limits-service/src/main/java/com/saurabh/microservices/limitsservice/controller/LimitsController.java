package com.saurabh.microservices.limitsservice.controller;

import com.saurabh.microservices.limitsservice.configuration.Configuration;
import com.saurabh.microservices.limitsservice.dto.Limits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    @Autowired
    private Configuration configuration;
    @GetMapping("/limits")
    public Limits getLimits(){
    return new Limits(1,1000);
    }

    @GetMapping("/limitsC")
    public Limits getLimitsFromConfig(){
        return new Limits(configuration.getMinimum(),configuration.getMaximum());
    }

}
