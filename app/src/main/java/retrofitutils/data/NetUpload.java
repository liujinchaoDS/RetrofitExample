package retrofitutils.data;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

public class NetUpload {

    public interface Upload {
        @Multipart
        @POST("/Common/SaveImageFile.ashx")
        void upload(@Part("useryid") String userYid, @Part("uploadtype") String type, @Part("file") TypedFile typedFile, Callback<String> cb);

        @Multipart
        @POST("/Common/SaveImageFile.ashx")
        void upload(@Part("useryid") String userYid, @Part("uploadtype") int type, @Part("file") TypedFile typedFile, Callback<String> cb);
    }

}
