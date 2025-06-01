package fit.hcmuaf.edu.vn.foodmart.Constant;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

public class Iconstant {
    private static final Dotenv dotenv = Dotenv.load();

    // Google OAuth2 credentials
    public static final String GOOGLE_CLIENT_ID = dotenv.get("GOOGLE_CLIENT_ID");
    public static final String GOOGLE_CLIENT_SECRET = dotenv.get("GOOGLE_CLIENT_SECRET");
    public static final String GOOGLE_REDIRECT_URI = "http://localhost:8080/project/loginGoogle";
    public static final String GOOGLE_GRANT_TYPE = "authorization_code";
    public static final String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static final String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";

    // Facebook OAuth2 credentials
    public static final String FACEBOOK_CLIENT_ID = dotenv.get("FACEBOOK_CLIENT_ID");
    public static final String FACEBOOK_CLIENT_SECRET = dotenv.get("FACEBOOK_CLIENT_SECRET");
    public static final String FACEBOOK_REDIRECT_URI = "http://localhost:8080/project/loginFacebook";
    public static final String FACEBOOK_LINK_GET_TOKEN = "https://graph.facebook.com/v19.0/oauth/access_token";

    public static final String FACEBOOK_LINK_GET_USER_INFO = "https://graph.facebook.com/me?fields=id,name,email,picture&access_token=";
}



