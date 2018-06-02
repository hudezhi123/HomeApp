package com.hudezhi.freedom.homeapp.more.bean;

/**
 * Created by boy on 2017/8/22.
 */

public class ChildBeanNew {
    private int age;
    private String name;

    public ChildBeanNew(){}

    public ChildBeanNew(int age, String name){
        this.age=age;
        this.name=name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
