package org.yuiborodin.alfa.images;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "imageInterface")
public interface ImageInterface {

    @RequestMapping(method = RequestMethod.GET, value = "/v1/gifs/search")
    ImageClientList getLatestRecord(
            @RequestParam String api_key,
            @RequestParam String q,
            @RequestParam String offset,
            @RequestParam(required = false, defaultValue = "1") String limit,
            @RequestParam(required = false, defaultValue = "r") String rating,
            @RequestParam(required = false, defaultValue = "en") String lang
    );

}