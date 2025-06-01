package fit.hcmuaf.edu.vn.foodmart.Constant;

import fit.hcmuaf.edu.vn.foodmart.Constant.IconstantLocal;

public class Iconstant {
    public static final String GOOGLE_CLIENT_ID = IconstantLocal.GOOGLE_CLIENT_ID;
    public static final String GOOGLE_CLIENT_SECRET = IconstantLocal.GOOGLE_CLIENT_SECRET;
    public static final String GOOGLE_REDIRECT_URI ="http://localhost:8080/project/loginGoogle";
    public static final String GOOGLE_GRANT_TYPE ="authorization_code";
    public static final String GOOGLE_LINK_GET_TOKEN ="https://accounts.google.com/o/oauth2/token";
    public static final String GOOGLE_LINK_GET_USER_INFO ="https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static final String FACEBOOK_CLIENT_ID = IconstantLocal.FACEBOOK_CLIENT_ID;
    public static final String FACEBOOK_CLIENT_SECRET = IconstantLocal.FACEBOOK_CLIENT_SECRET;
    public static final String FACEBOOK_REDIRECT_URI = "http://localhost:8080/project/loginFacebook";
    public static final String FACEBOOK_LINK_GET_TOKEN = "https://graph.facebook.com/v19.0/oauth/access_token";
    public static final String FACEBOOK_LINK_GET_USER_INFO = "https://graph.facebook.com/me?fields=id,name,email,picture&access_token=";
}
