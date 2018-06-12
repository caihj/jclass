package com.fighter.tools.types.attribute;

import com.fighter.tools.ClassReader;
import com.fighter.tools.types.AttributeInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by caihaijun@navercorp.com on 2018/6/12.
 */
@Slf4j
public class CodeAttribute extends AttributeInfo {

    public CodeAttribute(AttributeInfo info){
        this.attribute_name_index = info.attribute_name_index;
        this.attrubute_length = info.attrubute_length;

        DataInput input = new DataInputStream(new ByteArrayInputStream(info.bytes));

        try {
            max_stack = input.readUnsignedShort();
            max_locals = input.readUnsignedShort();
            code_length = input.readInt();
            code = new byte[code_length];
            input.readFully(code);
            exception_table_length = input.readUnsignedShort();
            exceptionTables = new ExceptionTable[exception_table_length];
            for(int i=0;i<exception_table_length;i++){
                ExceptionTable t = new ExceptionTable();

                t.start_pc = input.readUnsignedShort();
                t.end_pc = input.readUnsignedShort();
                t.handle_pc = input.readUnsignedShort();
                t.catch_type = input.readUnsignedShort();
                exceptionTables[i] = t;
            }

            attribute_count = input.readUnsignedShort();
            attributes = new AttributeInfo[attribute_count];
            for(int i=0;i<attribute_count;i++) {
                attributes[i] = ClassReader.readAttribute(input);
            }
        } catch (IOException e) {
            log.error("exception"+e);
        }

    }


    //u2
    public int max_stack;
    //u2
    public int max_locals;
    //u4
    public int code_length;
    //byte array code_length
    public byte[] code;
    //u2
    public int exception_table_length;

    public static class ExceptionTable{
        //u2
        public int start_pc;
        //u2
        public int end_pc;
        //u2
        public int handle_pc;
        //u2
        public int catch_type;

        @Override
        public String toString() {
            return "ExceptionTable{" +
                    "start_pc=" + start_pc +
                    ", end_pc=" + end_pc +
                    ", handle_pc=" + handle_pc +
                    ", catch_type=" + catch_type +
                    '}';
        }
    }

    public ExceptionTable []  exceptionTables;
    //u2
    public int attribute_count;
    //array attribute_count
    public AttributeInfo [] attributes;

    @Override
    public String toString() {
        return "CodeAttribute{" +
                "max_stack=" + max_stack +
                ", max_locals=" + max_locals +
                ", code_length=" + code_length +
                ", exception_table_length=" + exception_table_length +
                ", exceptionTables=" + Arrays.toString(exceptionTables) +
                '}';
    }
}
