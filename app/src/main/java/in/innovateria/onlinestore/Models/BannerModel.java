package in.innovateria.onlinestore.Models;

import com.google.gson.annotations.SerializedName;

public class BannerModel {
    @SerializedName("url")
    String url;

    public BannerModel() {
        // Default constructor required for Firebase
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
}


