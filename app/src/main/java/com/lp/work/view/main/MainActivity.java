package com.lp.work.view.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.lp.work.R;
import com.lp.work.databinding.ActivityMainBinding;
import com.lp.work.databinding.WelcomeActivityBinding;
import com.lp.work.view.diy.BaseActivity;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    }
}
