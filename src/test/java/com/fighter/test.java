package com.fighter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.NumberFormat;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.fighter.classloader.BootStrapClassLoader;
import com.fighter.model.Klass;
import com.fighter.template.BasicFoo;
import com.fighter.tools.ClassObject;
import com.fighter.tools.ClassReader;
import com.fighter.vm.VM;

@RunWith(JUnit4.class)
public class test {

    @Test
    public void testOne() throws IOException {

        ClassObject object = ClassReader.readClass("com.fighter.template.Basic");

        System.out.println(object);
    }

    @Test
    public void testMain() throws IOException {

        ClassObject object = ClassReader.readClass("com.fighter.template.Main");

        System.out.println(object);
    }

    @Test
    public void testInterface() throws IOException, ClassNotFoundException {

        BootStrapClassLoader bootStrapClassLoader = new BootStrapClassLoader();
        Klass klass = bootStrapClassLoader.loadClass("com.fighter.template.MyCallable");
        System.out.println(klass);
    }

    @Test
    public void readObject() throws IOException {

        ClassObject object = ClassReader.readClass("java/lang/Object");

        System.out.println(object);
    }

    @Test
    public void readProperties() throws IOException {

        ClassObject object = ClassReader.readClass("java/util/Properties");

        System.out.println(object);
    }

    @Test
    public void testStatic(){

        BasicFoo foo = new BasicFoo();
        BasicFoo foo2 = new BasicFoo();
    }

    @Test
    public void testVm() {
        VM vm = new VM();
        vm.execute("com.fighter.template.Main");
    }

    @Test
    public void testShort() {
        short a = -1;
        int byteOne = (a & 0xff00) >> 8;
        int byteTwo = a & 0xff;
        System.out.println(byteTwo);

        short c = (short)((byteOne <<8) | (byteTwo & 0xff));
        System.out.println(c);

        Object str= "";

        String ccc= (String)str;
    }
}
