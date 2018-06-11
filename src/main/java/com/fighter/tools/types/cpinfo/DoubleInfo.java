package com.fighter.tools.types.cpinfo;

import java.io.DataInput;
import java.io.IOException;

/**
 * Created by caihaijun@navercorp.com on 2018/6/11.
 */
public class DoubleInfo extends CpInfo {
    //u4+u4
    public double bytes;
    public DoubleInfo(int tag, DataInput in) {
        super(tag);
        try {
            bytes = in.readDouble();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
