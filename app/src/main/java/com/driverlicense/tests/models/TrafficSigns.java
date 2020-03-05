package com.driverlicense.tests.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrafficSigns {

    @SerializedName("traffic_sign_name")
    @Expose
    public String trafficSignName;
    @SerializedName("traffic_sign_definition")
    @Expose
    public String trafficSignDefinition;
    @SerializedName("traffic_sign_image_url")
    @Expose
    public String trafficSignImageUrl;




    public String getTrafficSignName() {
        return trafficSignName;
    }

    public void setTrafficSignName(String trafficSignName) {
        this.trafficSignName = trafficSignName;
    }

    public String getTrafficSignDefinition() {
        return trafficSignDefinition;
    }

    public void setTrafficSignDefinition(String trafficSignDefinition) {
        this.trafficSignDefinition = trafficSignDefinition;
    }

    public String getTrafficSignImageUrl() {
        return trafficSignImageUrl;
    }

    public void setTrafficSignImageUrl(String trafficSignImageUrl) {
        this.trafficSignImageUrl = trafficSignImageUrl;
    }

}
