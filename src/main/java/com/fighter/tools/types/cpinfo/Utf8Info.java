package com.fighter.tools.types.cpinfo;

import java.io.DataInput;
import java.io.IOException;

/**
 * Created by caihaijun@navercorp.com on 2018/6/11.
 */
public class Utf8Info extends CpInfo {
    //u2
    public int length;
    //u1 array
    public byte [] bytes;
    public Utf8Info(int tag, DataInput in) {
        super(tag);
        try {
            length = in.readUnsignedShort();
            bytes = new byte[length];
            in.readFully(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        //may diff from gen utf-8
        return new String(bytes);
    }
}
