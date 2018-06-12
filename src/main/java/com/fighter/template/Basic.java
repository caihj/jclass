package com.fighter.template;

public abstract class Basic {


    private static volatile String attr1=System.getProperty("system.os");

    private static int attr2=2333;

    public Basic(){

        System.out.println("hhhh");
    }

    static{
        System.out.println("hello world");
    }


    public void print(){
        int a=-2;
        System.out.println(attr1);
        System.out.println(attr2);
    }

}
