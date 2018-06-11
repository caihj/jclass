package com.fighter.tools.types;

public class MethodInfo {
    public int access_flags;

    public int name_index;

    public int descriptor_index;

    public int attributes_count;
    //array attributes_count
    public AttributeInfo []  attributes;
}
