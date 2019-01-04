package com.fighter.tools.types.attribute;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

import com.fighter.tools.ClassObject;
import com.fighter.tools.types.AttributeInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by caihaijun@navercorp.com on 2019/1/3.
 */
@Slf4j
public class SourceFileAttribute extends AttributeInfo {

	public String sourceFileName;

	public SourceFileAttribute(AttributeInfo info, ClassObject cls) {
		assert ( info.attrubute_length == 2);
		DataInput input = new DataInputStream(new ByteArrayInputStream(info.bytes));
		try {
			sourceFileName = cls.constant_pool[input.readShort()].toString();
		} catch (IOException e) {
			log.info(e.getMessage(), e);
		}
	}

	@Override
	public String toString() {
		return "SourceFileAttribute{" +
			"sourceFileName='" + sourceFileName + '\'' +
			'}';
	}
}
