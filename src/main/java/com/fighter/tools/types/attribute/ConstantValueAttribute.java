package com.fighter.tools.types.attribute;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

import com.fighter.tools.ClassObject;
import com.fighter.tools.types.AttributeInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by caihaijun@navercorp.com on 2019/1/4.
 */

@Slf4j
public class ConstantValueAttribute extends AttributeInfo {

	public  Object value;

	public ConstantValueAttribute(AttributeInfo attributeInfo, ClassObject cls) {

		assert ( attributeInfo.attrubute_length == 2);

		DataInput input = new DataInputStream(new ByteArrayInputStream(attributeInfo.bytes));
		try {
			value = cls.constant_pool[input.readShort()];
		} catch (IOException e) {
			log.info(e.getMessage(), e);
		}
	}

}
