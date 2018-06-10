package com.fighter.tools;

import com.fighter.tools.types.AttributeInfo;
import com.fighter.tools.types.CpInfo;
import com.fighter.tools.types.FieldInfo;
import com.fighter.tools.types.MethodInfo;

public class ClassObject {
    //u4
    public int magic;
    //u2
    public int mior_version;
    //u2
    public int major_version;
    //u2
    public int constant_pool_count;
    //array constant_pool_count-1
    public CpInfo[] constant_pool;
    //u2
    public int access_flags;
    //u2
    public int this_class;
    //u2
    public int super_class;
    //u2
    public int interfaces_count;
    //array interfaces_count
    public int [] interfaces;
    //u2
    public int fields_count;
    //array fields_count
    public FieldInfo []  fields;
    //u2
    public int methods_count;
    //array methods_count
    public MethodInfo [] methods;
    //u2
    public int attributes_count;
    //array attributes_count
    public AttributeInfo[]  attributes;


}
