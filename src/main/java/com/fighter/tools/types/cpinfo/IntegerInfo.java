package com.fighter.tools.types.cpinfo;

import java.io.DataInput;
import java.io.IOException;

/**
 * Created by caihaijun@navercorp.com on 2018/6/11.
 */
public class IntegerInfo extends CpInfo {
    //u4
    int bytes;
    public IntegerInfo(int tag, DataInput in) {
        super(tag);
        try {
            bytes = in.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
