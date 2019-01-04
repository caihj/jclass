package com.fighter.tools;

import java.util.Arrays;
import java.util.Optional;

import com.fighter.tools.types.AttributeInfo;
import com.fighter.tools.types.Utils;
import com.fighter.tools.types.attribute.SourceFileAttribute;
import com.fighter.tools.types.cpinfo.ClassInfo;
import com.fighter.tools.types.cpinfo.CpInfo;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        ClassInfo classInfo = null;
        classInfo = (ClassInfo) constant_pool[this_class];

        sb.append("class Name:"+ Utils.classAccessFlags(access_flags)+constant_pool[classInfo.name_index].toString().replace("/","."));
        sb.append("\n");

        for (AttributeInfo attribute : attributes) {
            sb.append("attr :" + attribute.toString() + "\n");
        }

        sb.append("field:\n");
        for(int i=0;i<fields_count;i++){
            sb.append("\t");
            sb.append(Utils.fieldAccessFlags(fields[i].access_flags) + constant_pool[fields[i].descriptor_index] +" " +constant_pool[fields[i].name_index].toString());

            for(int j=0;j<fields[i].attributes_count;j++){
                AttributeInfo attr=methods[i].attributes[j];
                String attrName = constant_pool[attr.attribute_name_index].toString();
                sb.append("\t\t"+attrName+",");
            }

            sb.append("\n");
        }

        sb.append("method:\n");
        for(int i=0;i<methods_count;i++){
            sb.append("\t");
            sb.append(Utils.fieldAccessFlags(methods[i].access_flags) + constant_pool[methods[i].descriptor_index] + constant_pool[methods[i].name_index].toString());
            sb.append("\t\t");
            for(int j=0;j<methods[i].attributes_count;j++){
                AttributeInfo attr=methods[i].attributes[j];
                String attrName = constant_pool[attr.attribute_name_index].toString();
                sb.append("\t\t"+attrName+",");
                sb.append("\t\tlength:"+attr.attrubute_length);
                sb.append("\t\t"+attr.toString());

            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
