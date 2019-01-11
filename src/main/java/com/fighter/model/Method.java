package com.fighter.model;

import com.fighter.tools.ClassObject;
import com.fighter.tools.types.AttributeInfo;
import com.fighter.tools.types.MethodInfo;
import com.fighter.tools.types.attribute.CodeAttribute;

/**
 * Created by caihaijun@navercorp.com on 2019/1/3.
 */
public class Method {

	public String name;

	public String descriptor;

	public CodeAttribute codeAttribute;

	public int accessFlags;

	public Klass klass;

	public Method(MethodInfo method, ClassObject readClass, Klass klass) {
		name = readClass.constant_pool[method.name_index].toString();
		descriptor = readClass.constant_pool[method.descriptor_index].toString();
		accessFlags = method.access_flags;
		this.klass = klass;

		for(int j=0;j<method.attributes_count;j++){
			AttributeInfo attr = method.attributes[j];
			String attrName = readClass.constant_pool[attr.attribute_name_index].toString();
			if( attrName.equals("Code")) {
				codeAttribute = (CodeAttribute)attr;
			}
		}
	}
}
