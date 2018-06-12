package com.fighter.tools.types.cpinfo;

import java.io.DataInput;
import java.io.IOException;

/**
 * Created by caihaijun@navercorp.com on 2018/6/11.
 */
public class StringInfo extends CpInfo {
    //u2
    public int string_index;
    public StringInfo(int tag, DataInput in) {
        super(tag);
        try {
            string_index = in.readUnsignedShort();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
