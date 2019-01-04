package com.fighter.model;

/**
 * Created by caihaijun@navercorp.com on 2019/1/3.
 * 表示所有jvm里面的实例对象
 */
public abstract class Oop {


	public abstract void putField(String fieldName, Oop oop);

	public abstract  Klass getKlass();
}
