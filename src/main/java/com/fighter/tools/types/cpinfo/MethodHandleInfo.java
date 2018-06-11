package com.fighter.tools.types.cpinfo;

import java.io.DataInput;
import java.io.IOException;

/**
 * Created by caihaijun@navercorp.com on 2018/6/11.
 */
public class MethodHandleInfo extends CpInfo {
    //u2
    public int ref_kind;
    //u2
    public int ref_index;
    public MethodHandleInfo(int tag, DataInput in) {
        super(tag);
        try {
            ref_kind = in.readUnsignedShort();
            ref_index = in.readUnsignedShort();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
