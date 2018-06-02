package com.hudezhi.freedom.homeapp.more.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by boy on 2017/8/22.
 */

public class NetBean implements Serializable {
    private int Code;
    private String fileName;
    private String filePath;
    private List<ChildBean> data;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<ChildBean> getChildBeanList() {
        return data;
    }

    public void setChildBeanList(List<ChildBean> data) {
        this.data = data;
    }


    public static class ChildBean implements Serializable{
        private int age;
        private String name;

        public ChildBean() {
        }

        public ChildBean(int age, String name) {
            this.age = age;
            this.name = name;
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
}
