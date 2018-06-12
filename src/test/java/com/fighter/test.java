package com.fighter;


import com.fighter.template.BasicFoo;
import com.fighter.tools.ClassObject;
import com.fighter.tools.ClassReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

@RunWith(JUnit4.class)
public class test {

    @Test
    public void testOne() throws IOException {

        ClassObject object = ClassReader.readClass("com.fighter.template.Basic".replace(".","/"));

        System.out.println(object);
    }

    @Test
    public void testStatic(){

        BasicFoo foo = new BasicFoo();
        BasicFoo foo2 = new BasicFoo();
    }
}
