package com.fighter.model.oopimpl;

import com.fighter.model.Klass;
import com.fighter.model.Oop;

/**
 * Created by caihaijun@navercorp.com on 2019/1/4.
 */
public class IntegerOop extends BasicOop  {

	private int value;

	public IntegerOop(int value) {
		this.value = value;
	}

	@Override
	public void putField(String className,String fieldName,String description, Oop oop) {

	}

	public int getValue() {
		return value;
	}

	@Override
	public Klass getKlass() {
		return null;
	}
}
