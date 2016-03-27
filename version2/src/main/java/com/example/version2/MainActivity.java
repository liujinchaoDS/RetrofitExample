package com.example.version2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void simple(View view) {
        startActivity(new Intent(this, SimpleUseActivity.class));
    }

    public void flatMap(View view) {
        startActivity(new Intent(this, FlatMapActivity.class));
    }

    public void zip(View view) {
        startActivity(new Intent(this, ZipActivity.class));
    }

}
