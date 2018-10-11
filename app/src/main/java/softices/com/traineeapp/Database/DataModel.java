package softices.com.traineeapp.Database;


import softices.com.traineeapp.activity.ForgotPasswordActivity;

public class DataModel {


    String name;
    String password;
    int id_;
    int image;

    public DataModel(String name, String password, int id_, int image) {
        this.name = name;
        this.password = password;
        this.id_ = id_;
        this.image=image;
    }

    public DataModel(ForgotPasswordActivity forgotPasswordActivity) {

    }


    public String getName() {
        return name;
    }


    public String getPassword() {
        return password;
    }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id_;
    }

    public int getVersion() {
        return 0;
    }
}