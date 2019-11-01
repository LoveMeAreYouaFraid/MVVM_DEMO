package com.lp.work;

import androidx.databinding.BaseObservable;


public class TestMode extends BaseObservable {
    private String name;
    private String age, url;
    private int cor;


    public int getCor() {
        return cor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyChange();
    }

    public void setCor(int cor) {
        this.cor = cor;
        notifyChange();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyChange();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
        notifyChange();
    }


}
