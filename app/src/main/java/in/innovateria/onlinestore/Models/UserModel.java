package in.innovateria.onlinestore.Models;

public class UserModel {
    private String id;
    private String name;
    private String email;
    private String photo;
    private String mobile;

    public UserModel(String id, String name, String email, String photo, String mobile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
