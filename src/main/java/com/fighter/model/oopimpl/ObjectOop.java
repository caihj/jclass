package com.fighter.model.oopimpl;

import com.fighter.model.Klass;
import com.fighter.model.Oop;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by caihaijun@navercorp.com on 2019/1/4.
 */

@Slf4j
public class ObjectOop extends Oop {

	private Klass klass;

	//instance field.
	private Oop [] field;

	@Override
	public void putField(String fieldName, Oop oop) {

	}

	@Override
	public Klass getKlass() {
		return klass;
	}
}
