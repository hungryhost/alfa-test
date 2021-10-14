package org.yuiborodin.alfa.currencies;

import feign.AsyncFeign;
import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Service;
import org.yuiborodin.alfa.utils.TypeUtils.ImageType;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.yuiborodin.alfa.utils.DateUtils.getPreviousDate;


@Service
public class CurrencyService{

    public static CurrencyInterface getCurrencyFeignInterface(String base_url){
        return AsyncFeign.asyncBuilder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .contract(new SpringMvcContract())
                .target(CurrencyInterface.class, base_url);
    }
    @Value("${currency.base_url}")
    private String base_url;

    @Value("${currency.base_currency}")
    private String base_currency;

    @Value("${currency.api_key}")
    private String api_key;
    CurrencyInterface newCurrencyInterface;

    private Double latest_rate;
    private Double previous_rate;

    private Double getLatestRate(String currency) throws ExecutionException, InterruptedException {
        this.newCurrencyInterface = getCurrencyFeignInterface(this.base_url);

        CompletableFuture<Currency> currencyRecord = newCurrencyInterface.getLatestRecord(
                api_key, base_currency, currency
        );
        Currency currencyInfo = currencyRecord.get();
        return currencyInfo.getRates().get(currency);



    }
    private Double getPreviousRate(String currency) throws ExecutionException, InterruptedException {
        this.newCurrencyInterface = getCurrencyFeignInterface(this.base_url);

        CompletableFuture<Currency> currencyRecord = newCurrencyInterface.getPreviousRecord(
                getPreviousDate(),
                api_key, base_currency, currency
        );
        Currency currencyInfo = currencyRecord.get();
        return currencyInfo.getRates().get(currency);
    }
    public ImageType compareRates(String currency){
        try {
            latest_rate = getLatestRate(currency);
            previous_rate = getPreviousRate(currency);

        }
        catch (FeignException | ExecutionException | InterruptedException exception){
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
        if (latest_rate >= previous_rate){
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

