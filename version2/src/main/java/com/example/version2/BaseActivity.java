package com.example.version2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.version2.utils.APIException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;


public class BaseActivity extends FragmentActivity {

    public static final String TAG = "BaseActivity";
    /**
     * 使用CompositeSubscription来持有所有的Subscriptions
     */
    protected CompositeSubscription mCompositeSubscription;
    private ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    protected void onDestroy() {
        //一旦调用了 CompositeSubscription.unsubscribe()，这个CompositeSubscription对象就不可用了,
        // 如果还想使用CompositeSubscription，就必须在创建一个新的对象了。
        super.onDestroy();
        mCompositeSubscription.unsubscribe();

    }


    /**
     * 创建观察者
     *
     * @param onNext
     * @param <T>
     * @return
     */
    protected <T> Subscriber newSubscriber(final Action1<? super T> onNext) {
        return new Subscriber<T>() {

            @Override
            public void onStart() {
                super.onStart();
                showLoadingDialog();
            }

            @Override
            public void onCompleted() {
                hideLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof APIException) {
                    APIException exception = (APIException) e;
                    showToast(exception.message);
                } else if (e instanceof SocketTimeoutException) {
                    showToast(e.getMessage());
//                    showToast("连接超时");
//                    Log.e(TAG, "onError " + e.getMessage());
                } else if (e instanceof ConnectException) {
                    showToast(e.getMessage());
                } else {
                    showToast(e.getMessage());
                }
                Log.e(TAG, "call " + e.toString());
                hideLoadingDialog();
            }

            @Override
            public void onNext(T t) {
                if (!mCompositeSubscription.isUnsubscribed()) {
                    onNext.call(t);
                }
            }
        };
    }


    public void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    protected void showLoadingDialog() {
        if (loading == null) {
            loading = new ProgressDialog(this);
        }
        loading.show();
    }

    protected void hideLoadingDialog() {
        if (loading != null) {
            loading.dismiss();
        }

    }

}
