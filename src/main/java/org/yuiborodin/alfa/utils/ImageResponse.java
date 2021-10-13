package org.yuiborodin.alfa.utils;

public class ImageResponse {
    private String result;
    private Boolean success;
    private TypeUtils.ImageType type;

    public ImageResponse(String result, Boolean success, TypeUtils.ImageType type) {
        this.result = result;
        this.success = success;
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public TypeUtils.ImageType getType() {
        return type;
    }

    public void setType(TypeUtils.ImageType type) {
        this.type = type;
    }
}
