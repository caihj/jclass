package com.fighter.model.oopimpl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.fighter.model.Klass;
import com.fighter.model.Oop;

/**
 * Created by caihaijun@navercorp.com on 2019/1/7.
 */
public class BasicOop extends Oop {

	private Lock monitorLock = new ReentrantLock();

	@Override
	public void putField(String fieldName, Oop oop) {

	}

	@Override
	public Klass getKlass() {
		return null;
	}

	@Override
	public Oop getField(String className, String fieldName, String description) {
		return null;
	}

	@Override
	public void lock() {
		monitorLock.lock();
	}

	@Override
	public void unlock() {
		monitorLock.unlock();
	}

}
