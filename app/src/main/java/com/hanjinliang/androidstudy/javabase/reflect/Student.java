package com.hanjinliang.androidstudy.javabase.reflect;

/**
 * @Description: java类作用描述
 * @Author: Monkey
 * @CreateDate: 2020/8/18 11:07
 */
public class Student {
    private String name="张三";
    private int age=23;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void sayHello(){
        System.out.println("你好，我的名字叫"+name+"，我的年龄是 "+age);
    }

    private void privateSayHello(){
        System.out.println("私有方法你好，我的名字叫"+name+"，我的年龄是 "+age);
    }
}
