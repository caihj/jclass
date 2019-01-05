package com.fighter.vm;

import java.util.ArrayList;
import java.util.Stack;

import com.fighter.model.Klass;
import com.fighter.model.Method;
import com.fighter.model.Oop;

/**
 * Created by caihaijun@navercorp.com on 2019/1/3.
 */
public class Frame {

	//局部变量表,此处一个格子就可以存储所有的值，所以占用两个格子的元素(double ,long )，第二个格子不使用
	public ArrayList<Oop> localVariable;

	// 操作数栈，此处一个格子就可以存储所有的值，所以占用两个格子的元素(double ,long )，第二个格子不使用
	public Stack<Oop> opStack;

	//program counter,
	public int pc;

	public Method currentMethod;

	public Klass currentKlass;

}
