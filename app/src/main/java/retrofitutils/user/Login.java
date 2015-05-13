package retrofitutils.user;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofitutils.data.BaseRetrofit;

public class Login {

    public interface LoginService {

        /**
         * 可以直接在主线程调用
         * @param name 登录名
         * @param pwd 密码
         * @param cb 回调
         */
        @GET("/api/Login/LoginValidate")
        void login(@Query("loginname") String name,
                   @Query("password") String pwd, Callback<BaseRetrofit<User>> cb);

        /**
         * 不能在主线程调用
         * @param name 登录名
         * @param pwd 密码
         * @return BaseRetrofit<User>
         */
        @GET("/api/Login/LoginValidate")
        BaseRetrofit<User> login(@Query("loginname") String name,
                                 @Query("password") String pwd);

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
