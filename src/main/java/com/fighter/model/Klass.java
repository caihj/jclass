package com.fighter.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fighter.classloader.ClassLoader;
import com.fighter.model.oopimpl.IntegerOop;
import com.fighter.tools.ClassObject;
import com.fighter.tools.types.AttributeInfo;
import com.fighter.tools.types.FieldInfo;
import com.fighter.tools.types.MethodInfo;
import com.fighter.tools.types.Utils;
import com.fighter.tools.types.attribute.ConstantValueAttribute;
import com.fighter.tools.types.cpinfo.ClassInfo;
import com.fighter.tools.types.cpinfo.IntegerInfo;

/**
 * Created by caihaijun@navercorp.com on 2019/1/3.
 *表示所有已经加载的class。
 */
public class Klass {

	private ClassObject readClass;

	//class loader load this class.
	private ClassLoader classloader ;

	private String className;

	private Map<String, Integer> OopfieldOffsetMap;

	//private Map<String, Integer > staticField;

	private Map<String, Oop> staticFieldvalue;

	private Map<String, Method> vTableMap;

	private Klass superClass ;

	private List<Klass> superInterface;

	public Klass(ClassObject obj, String className, ClassLoader classloader) {
		readClass = obj;
		this.className = className;
		this.classloader = classloader;
	}

	public String getSuperClassName() {
		ClassInfo classInfo = (ClassInfo)readClass.constant_pool[readClass.super_class];
		if(classInfo!=null) {
			String superClassName =  readClass.constant_pool[classInfo.name_index].toString();
			return superClassName;
		} else {
			return null;
		}
	}

	public List<String> getInterfaceNames(){

		List<String> interfaces = new ArrayList<>(readClass.interfaces.length);

		for (int anInterface : readClass.interfaces) {
			ClassInfo classInfo = (ClassInfo)readClass.constant_pool[anInterface];
			String className = readClass.constant_pool[classInfo.name_index].toString();
			interfaces.add(className);
		}

		return interfaces;
	}

	public void link(Klass superClass, List<Klass> superInterface){

		int fieldIndex = 0;
		int statiFieldIndex = 0;
		this.superClass = superClass;
		this.superInterface = superInterface;

		if( this.superClass != null){
			vTableMap = this.superClass.vTableMap;
			OopfieldOffsetMap = this.superClass.OopfieldOffsetMap;
			//staticField =  this.superClass.staticField;
			staticFieldvalue = this.superClass.staticFieldvalue;
		} else {
			vTableMap = new HashMap<>();
			OopfieldOffsetMap = new HashMap<>();
			//staticField = new HashMap<>();
			staticFieldvalue =  new HashMap<>();
		}

		for (FieldInfo field : readClass.fields) {
			String m_name = readClass.constant_pool[field.name_index].toString();
			String m_desc = readClass.constant_pool[field.descriptor_index].toString();

			if(Utils.fieldAccessFlags(field.access_flags).contains("static")){
				staticFieldvalue.put(m_name + m_desc, initConstantValue(field));
			} else {
				OopfieldOffsetMap.put(m_name + m_desc, fieldIndex ++ );
			}

		}

		for (MethodInfo method : readClass.methods) {
			String m_name = readClass.constant_pool[method.name_index].toString();
			String m_desc = readClass.constant_pool[method.descriptor_index].toString();
			Method m = new Method(method, readClass);
			m.accessFlags = method.access_flags;
			vTableMap.put(m_name + m_desc, m);
		}
	}

	public Method getMethod(String name, String description) {
		return vTableMap.get( name + description );
	}

	private Oop initConstantValue(FieldInfo field) {

		for (AttributeInfo attribute : field.attributes) {
			if( attribute instanceof ConstantValueAttribute){
				ConstantValueAttribute constantValueAttribute = (ConstantValueAttribute)attribute;

				if( constantValueAttribute.value instanceof IntegerInfo) {
					//construct a integer and return
					IntegerInfo info = (IntegerInfo)constantValueAttribute.value;
					return new IntegerOop(info.value);
				}
			}
		}
		return null;
	}


	public Oop newInstance(){

		return null;
	}

	public ClassObject getReadClass() {
		return readClass;
	}

	public Oop getStaticField(String fieldName, String description) {
		return staticFieldvalue.get(fieldName + description);
	}
}
