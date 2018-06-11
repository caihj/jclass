package com.fighter.tools.types.cpinfo;

import java.io.DataInput;
import java.io.IOException;

/**
 * Created by caihaijun@navercorp.com on 2018/6/11.
 */
public class InterfaceMethodInfo extends CpInfo {
    //u2
    public int class_index;
    //u2
    public int name_and_type_index;

    public InterfaceMethodInfo(int tag, DataInput in) {
        super(tag);
        try {
            class_index = in.readUnsignedShort();
            name_and_type_index = in.readUnsignedShort();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
