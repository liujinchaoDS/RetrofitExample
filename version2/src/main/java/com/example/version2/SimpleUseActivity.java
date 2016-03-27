package com.example.version2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.version2.data.Sug;
import com.example.version2.utils.CallApis;
import com.example.version2.utils.CallRetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimpleUseActivity extends BaseActivity {

    EditText input;
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_use);
        input = (EditText) findViewById(R.id.input);
        content = (TextView) findViewById(R.id.content);
        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputString = input.getText().toString().trim();
                if (inputString.length() > 0) {
                    Call<Sug> sug = CallRetrofitUtils.getInterface(CallApis.class).sug(inputString);
                    sug.enqueue(callback);
                }
            }
        });
    }

    Callback<Sug> callback = new Callback<Sug>() {
        @Override
        public void onResponse(Call<Sug> call, Response<Sug> response) {
            content.setText(response.body().toString());
        }

        @Override
        public void onFailure(Call<Sug> call, Throwable t) {
            content.setText(t.getMessage());
        }
    };
}
