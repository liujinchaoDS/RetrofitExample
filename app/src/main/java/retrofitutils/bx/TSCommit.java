package retrofitutils.bx;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofitutils.data.BaseJSPResult;

public class TSCommit {
    public interface Commit {
        // 提交数据 FieldMap可传null或长度为0的Map 如果FieldMap里的name一致 必须使用IdentityHashMap  添加参数使用put(new String(key), "")
        @FormUrlEncoded
        @POST("/WYGL/client/complainEvent/save")
        void commit(@Field("complainEventTemp.userHouseId") int userHouseId, @Field("complainEventTemp.target") String target, @Field("complainEventTemp.username")
        String username, @Field("complainEventTemp.cellphone")
                           String cellphone,
                           @FieldMap
                           Map<String, String> maps, Callback<BaseJSPResult> cb
        );

    }

}
