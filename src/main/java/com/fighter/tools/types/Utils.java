package com.fighter.tools.types;

/**
 * Created by caihaijun@navercorp.com on 2018/6/11.
 */
public class Utils {

    public static final int ACC_PUBLIC = 0x0001;

    public static final int ACC_PRIVATE = 0x0002;

    public static final int ACC_PROTECTED = 0x0004;

    public static final int ACC_STATIC = 0x0008;

    public static final int ACC_FINAL = 0x0010;

    public static final int ACC_SUPER = 0x0020;

    public static final int ACC_INTERFACE = 0x0200;

    public static final int ACC_ABSTRACT = 0x0400;

    public static final int ACC_VOLATILE = 0x0040;

    public static final int ACC_TRANSIENT = 0x0080;


    public static final int ACC_SYNTHETIC = 0x1000;

    public static final int ACC_ANNOTATION = 0x2000;

    public static final int ACC_ENUM = 0x4000;

    public static String classAccessFlags(int accessFlags){
        StringBuilder sb = new StringBuilder();

        if((accessFlags & ACC_PUBLIC) == ACC_PUBLIC){
            sb.append("public ");
        }

        if((accessFlags & ACC_FINAL) == ACC_FINAL){
            sb.append("final ");
        }

        if((accessFlags & ACC_SUPER) == ACC_SUPER){
            sb.append("super ");
        }

        if((accessFlags & ACC_INTERFACE) == ACC_INTERFACE ){
            sb.append("interface ");
        }

        if((accessFlags & ACC_ABSTRACT) == ACC_ABSTRACT){
            sb.append("abstract ");
        }

        if((accessFlags & ACC_SYNTHETIC) == ACC_SYNTHETIC){
            sb.append("synthetic ");
        }

        if((accessFlags & ACC_ANNOTATION) == ACC_ANNOTATION){
            sb.append("annotation ");
        }

        if((accessFlags & ACC_ENUM) == ACC_ENUM){
            sb.append("enum ");
        }

        return sb.toString();

    }

    public static String fieldAccessFlags(int accessFlags){
        StringBuilder sb = new StringBuilder();

        if((accessFlags & ACC_PUBLIC) == ACC_PUBLIC){
            sb.append("public ");
        }

        if((accessFlags & ACC_PRIVATE) == ACC_PRIVATE){
            sb.append("private ");
        }

        if((accessFlags & ACC_PROTECTED) == ACC_PROTECTED){
            sb.append("protected ");
        }

        if((accessFlags & ACC_STATIC) == ACC_STATIC ){
            sb.append("static ");
        }

        if((accessFlags & ACC_FINAL) == ACC_FINAL){
            sb.append("final ");
        }

        if((accessFlags & ACC_VOLATILE) == ACC_VOLATILE){
            sb.append("volatile ");
        }

        if((accessFlags & ACC_TRANSIENT) == ACC_TRANSIENT){
            sb.append("transient ");
        }

        if((accessFlags & ACC_SYNTHETIC) == ACC_SYNTHETIC){
            sb.append("synthetic ");
        }

        if((accessFlags & ACC_ENUM) == ACC_ENUM){
            sb.append("enum ");
        }

        return sb.toString();
    }
}
