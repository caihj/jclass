package com.fighter.tools.types;

public class FieldInfo {
    //u2
    public int access_flags;
    //u2
    public int name_index;
    //u2
    public int descriptor_index;
    //u2
    public int attributes_count;
    //array attributes_count
    public AttributeInfo []  attributes;
}
