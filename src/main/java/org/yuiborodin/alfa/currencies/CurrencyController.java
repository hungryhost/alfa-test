package org.yuiborodin.alfa.currencies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yuiborodin.alfa.images.ImageService;
import org.yuiborodin.alfa.utils.ImageResponse;
import org.yuiborodin.alfa.utils.TypeUtils;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(path="api/v1/currencies")
public class CurrencyController {
    private final CurrencyService currencyService;
    private final ImageService imageService;
    @Autowired
    public CurrencyController(CurrencyService currencyService, ImageService imageService){
        this.currencyService = currencyService;
        this.imageService = imageService;
    }

    @GetMapping("{ticker}")
    @ResponseBody
    ImageResponse getInfoByCurrency(@PathVariable String ticker) throws ExecutionException, InterruptedException {
        TypeUtils.ImageType imageType = currencyService.compareRates(ticker);
        String image_result = imageService.getImage(imageType);
        if (image_result.equals("fail")){
            return new ImageResponse("fail", false, imageType);
        }
        else {
            return new ImageResponse(image_result, true, imageType);
        }
    }
}
