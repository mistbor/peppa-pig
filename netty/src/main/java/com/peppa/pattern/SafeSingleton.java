package com.peppa.pattern;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum SafeSingleton {
    INSTANCE;

    private String name;

    SafeSingleton() {
        this.name = "Noe";
    }

    public static SafeSingleton getInstance() {
        return INSTANCE;
    }

    public String getName() {
        return this.name;
    }
}

class SafeSingletonTest {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor constructor = SafeSingleton.class.getDeclaredConstructor();

        // 以上这句会报错
      /*  Exception in thread "main" java.lang.NoSuchMethodException: com.peppa.pattern.SafeSingleton.<init>()
        at java.lang.Class.getConstructor0(Class.java:3082)
        at java.lang.Class.getDeclaredConstructor(Class.java:2178)
        at com.peppa.pattern.SafeSingletonTest.main(SafeSingleton.java:26)*/


        constructor.setAccessible(true);
        SafeSingleton safeSingleton = (SafeSingleton) constructor.newInstance();

        System.out.println(safeSingleton);
    }
}
