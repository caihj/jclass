package com.fighter.template;

import java.util.concurrent.Callable;

/**
 * Created by caihaijun@navercorp.com on 2018/6/12.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("hello world");

        Callable c = new  DummyCallable();

        c.call();

        MyCallable m = new  DummyCallable();

        m.call();
    }
}
