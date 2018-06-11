package com.fighter.tools.types.cpinfo;

import java.io.DataInput;
import java.io.IOException;

/**
 * Created by caihaijun@navercorp.com on 2018/6/11.
 */
public class FloatInfo extends CpInfo {
    //u4
    float bytes;
    public FloatInfo(int tag, DataInput in) {
        super(tag);
        try {
            bytes = in.readFloat();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
