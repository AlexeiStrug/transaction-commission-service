package com.example.transaction.commision.service.exchange;

import com.example.transaction.commision.common.dto.ExchangeRateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "jplaceholder", url = "https://api.exchangerate.host/")
public interface ExchangeRateService {

    @RequestMapping(method = RequestMethod.GET, value = "/{date}", produces = "application/json")
    ExchangeRateDto getExchangeRate(@PathVariable("date") String date);
}
