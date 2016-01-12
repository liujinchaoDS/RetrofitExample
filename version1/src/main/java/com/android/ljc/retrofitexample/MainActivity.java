package com.android.ljc.retrofitexample;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.IdentityHashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import com.android.ljc.retrofitutils.RetrofitRestAdapterUtils;
import com.android.ljc.retrofitutils.bx.JSPUpload;
import com.android.ljc.retrofitutils.bx.TSCommit;
import com.android.ljc.retrofitutils.data.BaseJSPResult;
import com.android.ljc.retrofitutils.data.BaseRetrofit;
import com.android.ljc.retrofitutils.data.NetDownload;
import com.android.ljc.retrofitutils.data.NetUpload;
import com.android.ljc.retrofitutils.user.Login.LoginService;
import com.android.ljc.retrofitutils.user.Login.User;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_TEST_JSP = 1;
    private static final int REQUEST_TEST_NET = 2;
    private static final int REQUEST_TEST_NET_INT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void testLogin(final View view) {
        RestAdapter restAdapter = RetrofitRestAdapterUtils.getUserSystemInstance(MainActivity.this);
        LoginService loginService = restAdapter.create(LoginService.class);
        Callback<BaseRetrofit<User>> cb = new Callback<BaseRetrofit<User>>() {
            @Override
            public void success(BaseRetrofit<User> loginBaseRetrofit, Response response) {
                ((Button) view).setText(loginBaseRetrofit.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                ((Button) view).setText(error.toString());
            }
        };
        loginService.login("18636909627", "中文会不会编码", cb);// gson解析错误 得不到响应信息
//        loginService.login("18636909627", "111111");
    }

    private Intent getImageIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        return intent;
//        Intent i = new Intent(
//                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        return i;
    }

    public void testPostJSP(View view) {
        startActivityForResult(getImageIntent(), REQUEST_TEST_JSP);
    }

    public void testPostNet(View view) {
        startActivityForResult(getImageIntent(), REQUEST_TEST_NET);
    }

    public void testPostNetInt(View view) {
        startActivityForResult(getImageIntent(), REQUEST_TEST_NET_INT);
    }

    private void postJSP(final Button view, File file) {
        RestAdapter restAdapter = RetrofitRestAdapterUtils.getBXSystemInstance(MainActivity.this);
        JSPUpload.Upload uploadService = restAdapter.create(JSPUpload.Upload.class);
        Callback<JSPUpload.UploadResult> cb = new Callback<JSPUpload.UploadResult>() {
            @Override
            public void success(JSPUpload.UploadResult uploadResult, Response response) {
                view.setText(uploadResult.path);
            }

            @Override
            public void failure(RetrofitError error) {
                view.setText(error.toString());
            }
        };
        if (file.exists()) {
            String mimeType = "image/pjpeg";
            TypedFile fileToSend = new TypedFile(mimeType, file);
            uploadService.upload(fileToSend, cb);
        } else {
            view.setText("file not exists");
        }
    }

    private void postNet(final Button view, File file) {
        RestAdapter restAdapter = RetrofitRestAdapterUtils.getUserSystemInstance(MainActivity.this);
        NetUpload.Upload uploadService = restAdapter.create(NetUpload.Upload.class);
        Callback<String> cb = new Callback<String>() {
            @Override
            public void success(String loginBaseRetrofit, Response response) {
                view.setText(loginBaseRetrofit);
            }

            @Override
            public void failure(RetrofitError error) {
                view.setText(error.toString());
            }
        };

        if (file.exists()) {
            String mimeType = "image/pjpeg";
            TypedFile fileToSend = new TypedFile(mimeType, file);
            uploadService.upload("C6D88363175E7DE6B9", "1", fileToSend, cb);
        } else {
            view.setText("file not exists");
        }
    }


    private void postNetInt(final Button view, File file) {
        RestAdapter restAdapter = RetrofitRestAdapterUtils.getUserSystemInstance(MainActivity.this);
        NetUpload.Upload uploadService = restAdapter.create(NetUpload.Upload.class);
        Callback<String> cb = new Callback<String>() {
            @Override
            public void success(String loginBaseRetrofit, Response response) {
                view.setText(loginBaseRetrofit);
            }

            @Override
            public void failure(RetrofitError error) {
                view.setText(error.toString());
            }
        };
        if (file.exists()) {
            String mimeType = "image/pjpeg";
            TypedFile fileToSend = new TypedFile(mimeType, file);
            uploadService.upload("C6D88363175E7DE6B9", 1, fileToSend, cb);
        } else {
            view.setText("file not exists");
        }
    }

    public void testDownNet(final View view) {
        RestAdapter restAdapter = RetrofitRestAdapterUtils.getUserSystemInstance(MainActivity.this);
        NetDownload.NetDown uploadService = restAdapter.create(NetDownload.NetDown.class);
        Callback<Response> cb = new Callback<Response>() {
            @Override
            public void success(Response loginBaseRetrofit, Response response) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(response.getBody().in());
                    ((ImageView) findViewById(R.id.image)).setImageBitmap(bitmap);
                } catch (IOException e) {
                    ((Button) view).setText("exception");
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ((Button) view).setText(error.toString());
            }
        };
        uploadService.netDown(200, 200, 50, cb);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            File file = new File(getStringFromUri(uri));
            switch (requestCode) {
                case REQUEST_TEST_JSP:
                    postJSP((Button) findViewById(R.id.button2), file);
                    break;
                case REQUEST_TEST_NET:
                    postNet((Button) findViewById(R.id.button3), file);
                    break;
                case REQUEST_TEST_NET_INT:
                    postNetInt((Button) findViewById(R.id.button4), file);
                    break;
            }
        }
    }

    private String getStringFromUri(Uri uri) {
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor actualimagecursor = this.managedQuery(uri, proj, null, null, null);
//        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        actualimagecursor.moveToFirst();
//        String img_path = actualimagecursor.getString(actual_image_column_index);
//        return img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    /**
     * 测试投诉提交
     *
     */
    public void testTSCommit(final View view) {
        TSCommit.Commit commit = RetrofitRestAdapterUtils.getBXSystemInstance(this).create(TSCommit.Commit.class);
        IdentityHashMap<String, String> maps = new IdentityHashMap<>();
        maps.put(new String("imagePath"), "/upload/complainEvent/20150515/f6f18120-a43e-4831-bee3-07a9b094e884.jpg");
        maps.put(new String("imagePath"), "/upload/complainEvent/20150515/69601500-8bd6-4ae4-bf23-1882ef609a66.jpg");
        Callback<BaseJSPResult> cb = new Callback<BaseJSPResult>() {
            @Override
            public void success(BaseJSPResult s, Response response) {
                ((Button) view).setText(s.result + "");
            }

            @Override
            public void failure(RetrofitError error) {
                ((Button) view).setText(error.toString());
            }
        };
        commit.commit(123, "hhh", "dfsf", "123456789", maps, cb);
    }
}
