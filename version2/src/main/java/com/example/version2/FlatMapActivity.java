package com.example.version2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.version2.data.GeoCoding;
import com.example.version2.data.Regeo;
import com.example.version2.utils.RxApis;
import com.example.version2.utils.RxRetrofitUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class FlatMapActivity extends BaseActivity {

    EditText input;
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_map);
        input = (EditText) findViewById(R.id.input);
        content = (TextView) findViewById(R.id.content);
        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputString = input.getText().toString().trim();
                if (inputString.length() > 0) {
                    RxRetrofitUtils.getInterface(RxApis.class)
                            .geocoding(inputString)
                            .subscribeOn(Schedulers.io())
                            //此处必须使用IO调度，可以不使用主线程 因为下一步还是在IO上
//                            .observeOn(AndroidSchedulers.mainThread())
                            .flatMap(new Func1<GeoCoding, Observable<Regeo>>() {
                                @Override
                                public Observable<Regeo> call(GeoCoding geoCoding) {
                                    if (geoCoding != null) {
                                        return RxRetrofitUtils.getInterface(RxApis.class).regeo(geoCoding.lon, geoCoding.lat)
                                                //如果上一个是在IO，这个可以省略IO，但必须有主线程  如果上一个带主线程 这个必须带IO
//                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread());
                                    } else {
                                        content.setText("未找到该地址");
                                        Throwable throwable = new Throwable("Input Error");
                                        return Observable.error(throwable);
                                    }
                                }
                            }).subscribe(new Action1<Regeo>() {
                        @Override
                        public void call(Regeo regeo) {
                            if (regeo != null) {
                                content.setText(regeo.toString());
                            }
                        }
                    });
                }
            }
        });
    }
}
