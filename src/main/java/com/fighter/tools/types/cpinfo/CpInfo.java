package com.fighter.tools.types.cpinfo;

import lombok.extern.slf4j.Slf4j;

import java.io.DataInput;
import java.io.IOException;

@Slf4j
public class CpInfo {
    //u1
    public int tag;

    public static final int constant_class = 7;

    public static final int const_filedref = 9;

    public static final int const_methodref = 10;

    public static final int const_interfaceMethodref = 11;

    public static final int const_string = 8;

    public static final int const_integer = 3;

    public static final int const_float = 4;

    public static final int const_long = 5;

    public static final int const_double = 6;

    public static final int const_nameAndType = 12;

    public static final int const_utf8  = 1;

    public static final int const_methodhandle = 15;

    public static final int const_methodtype= 16;

    public static final int const_invokedynamic = 18;

    public CpInfo(int tag){
        this.tag = tag;
    }

    public static CpInfo read(DataInput in) throws IOException {
        int tag = in.readUnsignedByte();
        switch (tag){
            case constant_class: return new ClassInfo(tag,in);
            case const_filedref: return new FieldRefInfo(tag,in);
            case const_methodref: return new MethodRefInfo(tag,in);
            case const_interfaceMethodref: return new InterfaceMethodInfo(tag,in);
            case const_string: return new StringInfo(tag,in);
            case const_integer: return new IntegerInfo(tag,in);
            case const_float: return new FloatInfo(tag,in);
            case const_long: return new LongInfo(tag,in);
            case const_double: return new DoubleInfo(tag,in);
            case const_nameAndType: return new NameAndTypeInfo(tag,in);
            case const_utf8: return new Utf8Info(tag,in);
            case const_methodhandle: return new MethodHandleInfo(tag,in);
            case const_methodtype: return new MethodTypeInfo(tag,in);
            case const_invokedynamic: return new InvokDynamicINfo(tag,in);

            default: log.info("tag error,tag:{}",tag);return null;
        }
    }


}
