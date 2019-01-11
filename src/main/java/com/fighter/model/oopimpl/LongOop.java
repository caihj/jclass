package com.fighter.model.oopimpl;

import com.fighter.model.Klass;
import com.fighter.model.Oop;

/**
 * Created by caihaijun@navercorp.com on 2019/1/7.
 */
public class LongOop extends BasicOop  {
	private long value;

	public LongOop(long value) {
		this.value = value;
	}

	@Override
	public void putField(String className,String fieldName,String description, Oop oop) {

	}

	@Override
	public Klass getKlass() {
		return null;
	}

	public long getValue() {
		return value;
	}
}
