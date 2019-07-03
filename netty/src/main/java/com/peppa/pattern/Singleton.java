package com.peppa.pattern;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 恶汉式单例模式
 */
class Singleton {
    private static final Singleton INSTANCE = new Singleton();

    private String name;

    public String getName() {
        return this.name;
    }

    private Singleton() {
        this.name = "Neo";
    }

    public static Singleton getInstance() {
        return INSTANCE;
    }
}

/**
 * 通过反射破坏单例
 */
class SingletonTest {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Singleton instance = Singleton.getInstance();
        System.out.println(instance);

        Constructor constructor = Singleton.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Singleton instance2 = (Singleton) constructor.newInstance();
        System.out.println(instance2);

        System.out.println(instance == instance2);
    }
}
