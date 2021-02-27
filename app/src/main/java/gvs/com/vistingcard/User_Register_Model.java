package gvs.com.vistingcard;

public class User_Register_Model {
    String id;
    String name;
    String email;
    String phone;
    String password;
    String username;
    String otp;
    String username_otp;
    String username_password;
    String username_phone;

    public User_Register_Model() {
    }

    public User_Register_Model(String id, String name, String email, String phone, String password, String username, String otp, String username_otp, String username_password,String username_phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.username = username;
        this.otp = otp;
        this.username_otp = username_otp;
        this.username_password = username_password;
        this.username_phone=username_phone;
    }

    public String getUsername_phone() {
        return username_phone;
    }

    public void setUsername_phone(String username_phone) {
        this.username_phone = username_phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getUsername_otp() {
        return username_otp;
    }

    public void setUsername_otp(String username_otp) {
        this.username_otp = username_otp;
    }

    public String getUsername_password() {
        return username_password;
    }

    public void setUsername_password(String username_password) {
        this.username_password = username_password;
    }
}
