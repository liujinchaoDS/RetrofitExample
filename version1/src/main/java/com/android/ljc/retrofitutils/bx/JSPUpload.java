package com.android.ljc.retrofitutils.bx;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
import com.android.ljc.retrofitutils.data.BaseJSPResult;

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
