package com.lp.work;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class DBU {
    @BindingAdapter({"imageUrl"})
    public static void loadimage(ImageView imageView, String url) {
        Log.e("TAG", url + "   -走到这里了");
        Glide.with(imageView.getContext()).load(url)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }

    @BindingAdapter({"setColor"})
    public static void setColor(Button textView, int c) {
        Log.e("111", "setColor: "+" -走到这里了" );
        textView.setTextColor(textView.getContext().getResources().getColor(c));
    }
}
