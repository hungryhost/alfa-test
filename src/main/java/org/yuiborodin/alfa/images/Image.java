package org.yuiborodin.alfa.images;

import java.util.HashMap;


public class Image {
    private String id;
    private String url;
    private HashMap<String, ImageData> images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, ImageData> getImages() {
        return images;
    }
}
