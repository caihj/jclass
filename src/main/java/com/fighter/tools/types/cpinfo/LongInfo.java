package com.fighter.tools.types.cpinfo;

import java.io.DataInput;
import java.io.IOException;

/**
 * Created by caihaijun@navercorp.com on 2018/6/11.
 */
public class LongInfo extends CpInfo {
    //u4+u4
    public long bytes;
    public LongInfo(int tag, DataInput in) {
        super(tag);
        try {
            bytes = in.readLong();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
