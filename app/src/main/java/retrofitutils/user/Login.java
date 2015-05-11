package retrofitutils.user;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofitutils.data.BaseRetrofit;

public class Login {

    public interface LoginService {

        @GET("/api/Login/LoginValidate")
        void login(@Query("loginname") String name,
                   @Query("password") String pwd, Callback<BaseRetrofit<User>> cb);

    }

    public class User {

        long Id;
        String Ticket;
        String YId;
        String LoginName;
        int Avatar;
        String Timeout;
        String RefreshTicket;
        String RefreshTimeout;

        @Override
        public String toString() {
            return "User{" + "Id=" + Id + ", Ticket='" + Ticket + '\''
                    + ", YId='" + YId + '\'' + ", LoginName='" + LoginName
                    + '\'' + ", Avatar=" + Avatar + '}';
        }
    }


}
