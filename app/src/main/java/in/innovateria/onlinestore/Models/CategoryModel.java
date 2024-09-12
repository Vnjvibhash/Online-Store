package in.innovateria.onlinestore.Models;

import com.google.gson.annotations.SerializedName;

public class CategoryModel {
    @SerializedName("id")
    int id;

    @SerializedName("picUrl")
    String picUrl;

    @SerializedName("title")
    String title;

    public CategoryModel() {
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
    public String getPicUrl() {
        return picUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
}
