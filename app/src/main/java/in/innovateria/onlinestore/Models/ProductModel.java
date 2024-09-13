package in.innovateria.onlinestore.Models;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ProductModel implements Serializable {
    @SerializedName("categoryId")
    int categoryId;

    @SerializedName("description")
    String description;

    @SerializedName("model")
    List<String> model;

    @SerializedName("picUrl")
    List<String> picUrl;

    @SerializedName("price")
    int price;

    @SerializedName("rating")
    double rating;

    @SerializedName("showRecommended")
    boolean showRecommended;

    @SerializedName("title")
    String title;

    public ProductModel (){
        // Firebase required default Constructor
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public int getCategoryId() {
        return categoryId;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setModel(List<String> model) {
        this.model = model;
    }
    public List<String> getModel() {
        return model;
    }

    public void setPicUrl(List<String> picUrl) {
        this.picUrl = picUrl;
    }
    public List<String> getPicUrl() {
        return picUrl;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int getPrice() {
        return price;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
    public double getRating() {
        return rating;
    }

    public void setShowRecommended(boolean showRecommended) {
        this.showRecommended = showRecommended;
    }
    public boolean getShowRecommended() {
        return showRecommended;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

}
