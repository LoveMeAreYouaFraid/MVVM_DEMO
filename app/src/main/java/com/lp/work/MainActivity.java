package com.lp.work;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lp.work.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TestMode testmodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        testmodes = new TestMode();
        testmodes.setAge("1");
        testmodes.setName("2");
        testmodes.setUrl("https://img-blog.csdn.net/20170817144529837?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemhhbmdwaGls/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center");
        testmodes.setCor(R.color.colorPrimaryDark);
        binding.setTests(testmodes);
        binding.setOnc(new UserPresenter());

    }

    int anInt = 0;


    public class UserPresenter {

        public void onUserNameClick(TestMode a) {
            Log.e("11", "onUserNameClick: ");
            Toast.makeText(MainActivity.this, "asda", Toast.LENGTH_LONG).show();
            testmodes.setName("code" + anInt++ + "");
            testmodes.setAge("1");
        }

        public void onUserNameClick2(TestMode a) {

            Toast.makeText(MainActivity.this, testmodes.getAge(), Toast.LENGTH_LONG).show();
            if (testmodes.getAge().equals("0")) {
                testmodes.setAge("1");
                testmodes.setCor(R.color.colorPrimaryDark);
                testmodes.setUrl("https://csdnimg.cn/pubfooter/images/edu-QR.png");
            } else {
                testmodes.setAge("0");
                testmodes.setCor(R.color.w);
                testmodes.setUrl("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3877020000,3234931958&fm=58");
            }
        }

    }


}
