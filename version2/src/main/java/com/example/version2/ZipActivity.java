package com.example.version2;

import android.os.Bundle;

import com.example.version2.data.IpLookup;
import com.example.version2.utils.RxApis;
import com.example.version2.utils.RetrofitUtils;
import com.example.version2.utils.RxRetrofitUtils;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;

public class ZipActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip);
        Observable.zip(RxRetrofitUtils.getInterface(RxApis.class).ipLookup(), RxRetrofitUtils.getInterface(RxApis.class).ipLookup(), new Func2<IpLookup, IpLookup, IpLookup>() {
            @Override
            public IpLookup call(IpLookup ipLookup, IpLookup ipLookup2) {
                return ipLookup;
            }
        }).compose(RetrofitUtils.<IpLookup>applySchedulers()).subscribe(new Action1<IpLookup>() {
            @Override
            public void call(IpLookup ipLookup) {
                showToast(ipLookup.city + " " + ipLookup.country);
            }
        });
    }
}
