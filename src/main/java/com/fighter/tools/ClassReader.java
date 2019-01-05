package com.fighter.tools;

import com.fighter.tools.types.AttributeInfo;
import com.fighter.tools.types.FieldInfo;
import com.fighter.tools.types.MethodInfo;
import com.fighter.tools.types.Utils;
import com.fighter.tools.types.attribute.AttributeFactory;
import com.fighter.tools.types.cpinfo.CpInfo;
import com.fighter.tools.types.cpinfo.DoubleInfo;
import com.fighter.tools.types.cpinfo.LongInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;


@Slf4j
public class ClassReader {

    public static ClassObject readClass(String className) throws IOException {
        log.info("read class "+ className);
        className = className.replace(".","/");
        log.info("start");
        DataInput input = new DataInputStream(ClassReader.class.getResourceAsStream("/"+className+".class"));

        ClassObject cls = new ClassObject();
        cls.magic = input.readInt();
        log.info("Magic {}",String.format("0x%08X",cls.magic));

        cls.mior_version = input.readUnsignedShort();
        cls.major_version = input.readUnsignedShort();
        cls.constant_pool_count = input.readUnsignedShort();
        log.info("major version:{} minor_version:{},constant_pool:{}",cls.major_version,cls.mior_version,cls.constant_pool_count);
        cls.constant_pool = new CpInfo[cls.constant_pool_count];
        for(int i=1;i<=cls.constant_pool_count-1;i++){
            cls.constant_pool[i] = CpInfo.read(input);
            //long，double  要跳一个下标,占用两个格子
            if( cls.constant_pool[i] instanceof LongInfo ||  cls.constant_pool[i] instanceof DoubleInfo) {
                i++;
            }
        }
        log.info("read constant pool complement");

        cls.access_flags = input.readUnsignedShort();

        log.info("access flags : {} ,{} ",cls.access_flags,String.format("0x%04x",cls.access_flags));
        log.info("access flags str: {}", Utils.classAccessFlags(cls.access_flags));
        cls.this_class = input.readUnsignedShort();
        cls.super_class = input.readUnsignedShort();


        cls.interfaces_count = input.readUnsignedShort();
        cls.interfaces = new int[cls.interfaces_count];
        for(int i=0;i<cls.interfaces_count;i++){
            cls.interfaces[i] = input.readUnsignedShort();
        }

        cls.fields_count = input.readUnsignedShort();
        cls.fields = new FieldInfo[cls.fields_count];
        for(int i=0;i<cls.fields_count;i++){
            cls.fields[i] = readField(input, cls);
        }

        cls.methods_count  = input.readUnsignedShort();
        cls.methods = new MethodInfo[cls.methods_count];
        for(int i=0;i<cls.methods_count;i++){
            cls.methods[i] = readMethod(input);
            for(int j=0;j<cls.methods[i].attributes.length;j++){
                cls.methods[i].attributes[j] = AttributeFactory.convertAttribute(cls.methods[i].attributes[j], cls);
            }
        }

        cls.attributes_count = input.readUnsignedShort();
        cls.attributes = new AttributeInfo[cls.attributes_count];
        for(int i=0;i<cls.attributes_count;i++){
            cls.attributes[i] = readAttribute(input);
            cls.attributes[i] = AttributeFactory.convertAttribute(cls.attributes[i],cls);
        }

        return cls;
    }

    public static FieldInfo readField(DataInput input , ClassObject cls){
        FieldInfo info = new FieldInfo();

        try {
            info.access_flags = input.readUnsignedShort();
            info.name_index = input.readUnsignedShort();
            info.descriptor_index = input.readUnsignedShort();
            info.attributes_count = input.readUnsignedShort();
            info.attributes = new AttributeInfo[info.attributes_count];

            for(int i=0;i< info.attributes_count;i++){
                info.attributes[i] = readAttribute(input);
                info.attributes[i] = AttributeFactory.convertAttribute(info.attributes[i],cls);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return info;
    }

    public static AttributeInfo readAttribute(DataInput input){
        AttributeInfo attributeInfo = new AttributeInfo();

        try {
            attributeInfo.attribute_name_index = input.readUnsignedShort();
            attributeInfo.attrubute_length = input.readInt();
            attributeInfo.bytes = new byte[attributeInfo.attrubute_length];
            input.readFully(attributeInfo.bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return attributeInfo;
    }

    public static MethodInfo readMethod(DataInput input){
        MethodInfo info = new MethodInfo();

        try {
            info.access_flags = input.readUnsignedShort();
            info.name_index = input.readUnsignedShort();
            info.descriptor_index = input.readUnsignedShort();
            info.attributes_count = input.readUnsignedShort();
            info.attributes = new AttributeInfo[info.attributes_count];

            for(int i=0;i< info.attributes_count;i++){
                info.attributes[i] = readAttribute(input);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return info;
    }

}
