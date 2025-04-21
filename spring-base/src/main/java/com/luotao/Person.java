package com.luotao;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Classname Person
 * @Description TODO
 * @Version 1.0.0
 * @Date 2025/4/17 17:16
 * @Author LuoTao
 */
public class Person {
    private String name;
    private int age;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void sayHello(){
        System.out.println(String.format("你好,我是%s,今年%d岁！",this.name,this.age));
    }

    /**
    * @Description: 将 Person 类配置为 Spring 管理的 Bean后通过 Spring 容器获取和使用该 Bean
    * @Author: LuoTao
    * @Date: 2025-04-17 17:23:27
    **/
    public static void main(String[] args) {
        // 创建IOC容器，从类路径加载 XML 配置文件
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("spring.xml");
        // 从IOC容器获取 Person Bean实例
        Person person = beanFactory.getBean("person",Person.class);
        person.sayHello();
    }
}
