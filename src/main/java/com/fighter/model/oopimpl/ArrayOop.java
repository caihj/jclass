package com.fighter.model.oopimpl;

import java.util.ArrayList;
import java.util.LinkedList;

import com.fighter.model.Klass;
import com.fighter.model.Oop;

/**
 * Created by caihaijun@navercorp.com on 2019/1/7.
 */
public class ArrayOop extends BasicOop  {

	private ArrayList<Oop> values;

	public ArrayOop(int size ) {
		values = new ArrayList(size);
		for (int i=0; i< size; i++) {
			values.add(null);
		}
	}

	@Override
	public void putField(String className,String fieldName,String description, Oop oop) {

	}

	public Oop getValue(IntegerOop index) {

		return values.get(index.getValue());
	}

	public void setValue(IntegerOop index,Oop oop) {
		values.set(index.getValue(), oop);
	}

	public IntegerOop getSize() {
		return new IntegerOop(values.size());
	}

	@Override
	public Klass getKlass() {
		return null;
	}
}
