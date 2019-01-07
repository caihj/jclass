package com.fighter.model.oopimpl;

import java.util.LinkedList;

import com.fighter.model.Klass;
import com.fighter.model.Oop;

/**
 * Created by caihaijun@navercorp.com on 2019/1/7.
 */
public class ArrayOop extends Oop {

	private LinkedList<Oop> values;

	@Override
	public void putField(String fieldName, Oop oop) {

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
