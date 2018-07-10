package com.hanjinliang.androidstudy.javabase;

/**
 * Created by HanJinLiang on 2018-07-10.
 */

public class StudentBean {
    private int age;
    private String name;
    private String sex;
    private String home;

    public StudentBean(int age, String name, String sex, String home) {
        this.age = age;
        this.name = name;
        this.sex = sex;
        this.home = home;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }
}
