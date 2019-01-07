package com.fighter.model.oopimpl;

import com.fighter.model.Klass;
import com.fighter.model.Oop;

/**
 * Created by caihaijun@navercorp.com on 2019/1/7.
 */
public class DoubleOop extends BasicOop  {

	private double value;

	public DoubleOop( double value){
		this.value = value;
	}

	public double getValue() {
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
