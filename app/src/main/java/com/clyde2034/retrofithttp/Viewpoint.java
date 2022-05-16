package com.clyde2034.retrofithttp;

public class Viewpoint {
    private String ScenicSpotID;
    private String ScenicSpotName;
    private String DescriptionDetail;

    public Viewpoint(String scenicSpotID, String scenicSpotName, String descriptionDetail) {
        ScenicSpotID = scenicSpotID;
        ScenicSpotName = scenicSpotName;
        DescriptionDetail = descriptionDetail;
    }

    public String getScenicSpotID() {
        return ScenicSpotID;
    }

    public void setScenicSpotID(String scenicSpotID) {
        ScenicSpotID = scenicSpotID;
    }

    public String getScenicSpotName() {
        return ScenicSpotName;
    }

    public void setScenicSpotName(String scenicSpotName) {
        ScenicSpotName = scenicSpotName;
    }

    public String getDescriptionDetail() {
        return DescriptionDetail;
    }

    public void setDescriptionDetail(String descriptionDetail) {
        DescriptionDetail = descriptionDetail;
    }
}
