package com.fighter.model.oopimpl;

import com.fighter.model.Klass;
import com.fighter.model.Oop;

/**
 * Created by caihaijun@navercorp.com on 2019/1/7.
 */
public class CharOop  extends BasicOop  {

	private int value;

	public CharOop(int value) {
		this.value = (char)value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public void putField(String className,String fieldName,String description, Oop oop) {

	}

	@Override
	public Klass getKlass() {
		return null;
	}
}
