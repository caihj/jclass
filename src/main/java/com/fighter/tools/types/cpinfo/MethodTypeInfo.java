package com.fighter.tools.types.cpinfo;

import java.io.DataInput;
import java.io.IOException;

/**
 * Created by caihaijun@navercorp.com on 2018/6/11.
 */
public class MethodTypeInfo extends CpInfo {
    //u2
    public int descriptor_index;
    public MethodTypeInfo(int tag, DataInput in) {
        super(tag);
        try {
            descriptor_index = in.readUnsignedShort();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
