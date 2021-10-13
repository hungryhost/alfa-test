package org.yuiborodin.alfa.images;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Service;
import org.yuiborodin.alfa.utils.TypeUtils;

import java.util.HashMap;
import java.util.Random;

@Service
public class ImageService {

    private final String limit ="1";
    private final String lang = "end";
    private final String rating = "r";
    @Value("${images.base_url}")
    private String base_url;
    private String type;
    @Value("${images.api_key}")
    private String api_key;

    ImageInterface newImageInterface;

    public String getImage(TypeUtils.ImageType imageType) {
        this.newImageInterface = Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .contract(new SpringMvcContract())
                .target(ImageInterface.class, base_url);

        Random rand = new Random();
        int offset = rand.nextInt(3000);
        if (imageType.equals(TypeUtils.ImageType.rich)) {
            //ImageClientList
            this.type = "rich";

        } else if (imageType.equals(TypeUtils.ImageType.broke)) {
            this.type = "broke";
        } else return "fail";
        ImageClientList imageClientList = newImageInterface.getLatestRecord(
                api_key, type, String.valueOf(offset), limit, rating, lang
        );
        HashMap<String, ImageClientData> data = imageClientList.getData().get(0).getImages();
        return data.get("original").getUrl();
    }
}
