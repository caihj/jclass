package com.fighter.tools.types.cpinfo;

import java.io.DataInput;
import java.io.IOException;

/**
 * Created by caihaijun@navercorp.com on 2018/6/11.
 */
public class ClassInfo extends CpInfo {

    //u2
    public int name_index;

    public ClassInfo(int tag, DataInput in) {
        super(tag);
        try {
            name_index = in.readUnsignedShort();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
