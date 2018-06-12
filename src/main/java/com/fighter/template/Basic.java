package com.fighter.template;

public final class Basic {

    private volatile String attr1="sss";

    private volatile String attr2;

    public Basic(){

    }

    public void print(){
        int a=3;
        System.out.println(attr1);
        System.out.println(attr2);
    }

}
