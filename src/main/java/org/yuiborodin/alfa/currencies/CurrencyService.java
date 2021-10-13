package org.yuiborodin.alfa.currencies;

import org.yuiborodin.alfa.utils.TypeUtils.ImageType;
import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.yuiborodin.alfa.utils.DateUtils.getPreviousDate;


@Service
public class CurrencyService{


    @Value("${currency.base_url}")
    private String base_url;

    @Value("${currency.base_currency}")
    private String base_currency;

    @Value("${currency.api_key}")
    private String api_key;
    CurrencyInterface newCurrencyInterface;

    private Double latest_rate;
    private Double previous_rate;

    private Double getLatestRate(String currency) {
        this.newCurrencyInterface = Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .contract(new SpringMvcContract())
                .target(CurrencyInterface.class, base_url);

        Currency currencyClient = newCurrencyInterface.getLatestRecord(
                api_key, base_currency, currency
        );
        return currencyClient.getRates().get(currency);



    }
    private Double getPreviousRate(String currency) {
        this.newCurrencyInterface = Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .contract(new SpringMvcContract())
                .target(CurrencyInterface.class, base_url);

        Currency currencyClient = newCurrencyInterface.getPreviousRecord(
                getPreviousDate(),
                api_key, base_currency, currency
        );
        return currencyClient.getRates().get(currency);
    }
    public ImageType compareRates(String currency){
        try {
            previous_rate = getPreviousRate(currency);
            latest_rate = getLatestRate(currency);
            //System.out.println(previous_rate);
            //System.out.println(latest_rate);
        }
        catch (FeignException exception){

            return ImageType.error;
        }
        if (latest_rate == null || previous_rate == null){
            return ImageType.error;
        }
        if (latest_rate >= 1 || previous_rate >= 1){
            if (latest_rate >= previous_rate){
                return ImageType.rich;
            }
            else return ImageType.broke;
        }
        if (latest_rate <= previous_rate){
            return ImageType.broke;
        }
        else return ImageType.rich;
    }


    public Double getLatestRate() {
        return latest_rate;
    }

    public Double getPreviousRate() {
        return previous_rate;
    }
}
