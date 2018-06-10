package com.fighter;


import com.fighter.tools.ClassReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

@RunWith(JUnit4.class)
public class test {

    @Test
    public void testOne() throws IOException {

        ClassReader.readClass("com.fighter.template.Basic".replace(".","/"));
    }
}
