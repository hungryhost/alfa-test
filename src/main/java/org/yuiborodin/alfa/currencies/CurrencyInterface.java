package org.yuiborodin.alfa.currencies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "currencyInterface1")
public interface CurrencyInterface {
    @RequestMapping(method = RequestMethod.GET, value = "/api/latest.json")
    Currency getLatestRecord(
            @RequestParam String app_id,
            @RequestParam String base,
            @RequestParam String symbols
            );
    @RequestMapping(method = RequestMethod.GET, value = "api/historical/{date}.json/")
    Currency getPreviousRecord(
            @PathVariable(name="date") String date,
            @RequestParam String app_id,
            @RequestParam String base,
            @RequestParam String symbols
    );

}
