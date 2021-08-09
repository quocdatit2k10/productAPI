package au.xero.product.common;

public class InvalidLoginResponse {
    private String username;
    private String password;

    public InvalidLoginResponse() {
        this.username = PropertiesUtil.getProperty(Constant.user.AUTHEN_USERNAME);
        this.password = PropertiesUtil.getProperty(Constant.user.AUTHEN_PASSWORD);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}