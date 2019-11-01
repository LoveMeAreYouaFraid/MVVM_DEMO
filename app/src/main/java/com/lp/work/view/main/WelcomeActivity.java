package com.lp.work.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.lp.work.R;
import com.lp.work.databinding.WelcomeActivityBinding;
import com.lp.work.view.diy.BaseActivity;

public class WelcomeActivity extends BaseActivity {
    private WelcomeActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.welcome_activity);
        handler.sendEmptyMessageDelayed(1, 1000);
        binding.splashBtn.setText("3");
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what) {
                case 1:
                    binding.splashBtn.setText("2");
                    handler.sendEmptyMessageDelayed(2, 1000);
                    break;
                case 2:
                    handler.sendEmptyMessageDelayed(3, 1000);
                    binding.splashBtn.setText("1");
                    break;
                case 3:
                    startActivity(new Intent(mContext, MainActivity.class));
                    break;
            }
            return false;
        }
    });
}
