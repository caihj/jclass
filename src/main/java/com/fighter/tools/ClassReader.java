package com.fighter.tools;

import com.fighter.tools.types.CpInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;


@Slf4j
public class ClassReader {

    public static ClassObject readClass(String className) throws IOException {

        log.info("start");
        DataInput input = new DataInputStream(ClassReader.class.getResourceAsStream("/"+className+".class"));

        ClassObject cls = new ClassObject();
        cls.magic = input.readInt();
        log.info("Magic {}",String.format("0x%08X",cls.magic));

        cls.mior_version = input.readUnsignedShort();
        cls.major_version = input.readUnsignedShort();
        cls.constant_pool_count = input.readUnsignedShort();
        log.info("major version:{} mior_version:{},constant_pool:{}",cls.major_version,cls.mior_version,cls.constant_pool_count);
        cls.constant_pool = new CpInfo[cls.constant_pool_count-1];
        for(int i=0;i<cls.constant_pool_count-1;i++){

        }



        return null;
    }

}
