package com.fighter.tools.types.cpinfo;

import java.io.DataInput;
import java.io.IOException;

/**
 * Created by caihaijun@navercorp.com on 2018/6/11.
 */
public class InvokDynamicINfo extends CpInfo {
    //u2
    public int bootstrap_method_attr_idex;
    //u2
    public int name_and_type_index;

    public InvokDynamicINfo(int tag, DataInput in) {
        super(tag);
        try {
            bootstrap_method_attr_idex = in.readUnsignedShort();
            name_and_type_index = in.readUnsignedShort();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
