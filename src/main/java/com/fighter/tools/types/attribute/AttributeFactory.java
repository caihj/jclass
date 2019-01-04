package com.fighter.tools.types.attribute;

import com.fighter.tools.ClassObject;
import com.fighter.tools.types.AttributeInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by caihaijun@navercorp.com on 2018/6/12.
 */
@Slf4j
public class AttributeFactory {
    public static AttributeInfo convertAttribute(AttributeInfo attributeInfo, ClassObject cls){

        String attributeName  = cls.constant_pool[attributeInfo.attribute_name_index].toString();

        switch (attributeName){
            case "Code":return new  CodeAttribute(attributeInfo, cls);
			case "SourceFile":return new SourceFileAttribute(attributeInfo, cls);
            case "ConstantValue":return new ConstantValueAttribute(attributeInfo, cls);
            default:
                log.info("unrecognized attribute:"+attributeName);
                return attributeInfo;
        }
    }
}
