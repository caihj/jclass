package com.fighter.model.oopimpl;

import com.fighter.model.Klass;
import com.fighter.model.Oop;

/**
 * Created by caihaijun@navercorp.com on 2019/1/7.
 */
public class CharOop  extends Oop {

	private int value;

	public int getValue() {
		return value;
	}

	@Override
	public void putField(String fieldName, Oop oop) {

	}

	@Override
	public Klass getKlass() {
		return null;
	}
}
