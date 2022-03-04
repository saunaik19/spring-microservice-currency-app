package com.saurabh.microservices.currencyexchangeservice.dao;


import com.saurabh.microservices.currencyexchangeservice.dto.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange,Long> {
    CurrencyExchange findByFromAndTo(String from,String to);
}
