package softices.com.traineeapp.Model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String name, email, mobilenumber,gender, password;
    private Bitmap photo;

    public UserModel(String name, String email, String mobilenumber,String gender, String password, Bitmap photo) {
        this.name = name;
        this.email = email;
        this.mobilenumber = mobilenumber;
        this.gender = gender;
        this.password = password;
        this.photo = photo;
    }
    public UserModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobile) {
        this.mobilenumber = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}