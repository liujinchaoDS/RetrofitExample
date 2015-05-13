package retrofitutils.bx;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.mime.TypedFile;
import retrofitutils.data.BaseJSPResult;

public class JSPUpload {

    public interface Upload {
        @Multipart
        @POST("/WYGL/client/complainEvent/uploadImage")
        void upload(@Part("file") TypedFile typedFile, Callback<UploadResult> cb);
    }


    public class UploadResult extends BaseJSPResult {
        public String path;
    }

}
