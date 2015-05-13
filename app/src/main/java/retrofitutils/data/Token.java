package retrofitutils.data;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

public final class Token {

    public static final String USER_SHARED_TOKEN = "user_access_token";
    public static final String SQ_SHARED_TOKEN = "sq_access_token";
    public static final String WY_SHARED_TOKEN = "wy_access_token";
    public static final String OAUTH_GRANT_TYPE = "client_credentials";

    //Callback<Token> callback
    public interface GetToken {
        @FormUrlEncoded
        @POST("/OAuth/Token")
        Token getToken(@Header("Authorization") String auth, @Field("grant_type") String granttype);
    }


    public String access_token;
    public String token_type;
    public int expires_in;

    public static String getHeaderProperty(String property) {
        return "Basic " + property;
    }

    @Override
    public String toString() {
        return "Token{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in=" + expires_in +
                '}';
    }
}
