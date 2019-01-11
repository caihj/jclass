package com.fighter.vm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import com.fighter.classloader.BootStrapClassLoader;
import com.fighter.constant.ClassName;
import com.fighter.constant.Constant;
import com.fighter.model.Klass;
import com.fighter.model.Method;
import com.fighter.model.Oop;
import com.fighter.model.oopimpl.ArrayOop;
import com.fighter.model.oopimpl.DoubleOop;
import com.fighter.model.oopimpl.IntegerOop;
import com.fighter.model.oopimpl.LongOop;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by caihaijun@navercorp.com on 2019/1/3.
 * execute the byte code.
 */

@Slf4j
public class VM {

	public BootStrapClassLoader bootStrapClassLoader = new BootStrapClassLoader();

	public  ExecuteEngine executeEngine = new ExecuteEngine();

	public void loadSomeUsefulClass() {
		executeMethod(ClassName.SYSTEM, "initializeSystemClass", "()V");
	}

	public void execute(String mainClass){

		loadSomeUsefulClass();
		executeMethod(mainClass, "main", "([Ljava/lang/String;)V");

	}

	private void executeMethod(String className, String methodName, String description){

		Klass klass = bootStrapClassLoader.loadClass(className);
		if (klass == null ){
			log.error("class not found " + className);
			return;
		}

		Method method = klass.getMethod(className, methodName, description);

		if( method == null ){
			log.error(" method not found " + className);
			return;
		}

		//create a frame
		Frame frame = new Frame();

		frame.localVariable = new ArrayList<>(method.codeAttribute.max_locals);
		frame.opStack = new Stack<>();
		frame.currentMethod = method;
		frame.pc = 0;
		frame.currentKlass = klass;

		//execute it
		executeEngine.execute(frame, this);
	}


	public Oop getStaticFiled(String className, String fieldName, String description) {
		Klass klass = bootStrapClassLoader.loadClass(className);
		return 	klass.getStaticField(fieldName, description);
	}

	public Oop newInstance(String className) {
		Klass klass = bootStrapClassLoader.loadClass(className);

		Oop oop = klass.newInstance();
		//call clinit
		executeMethod(className, Constant.CINIT, Constant.descripton);
		//call init
		//executeMethod(oop, Constant.INIT, Constant.descripton);

		return oop;
	}

	public ArrayOop newArrayInstance(String className, IntegerOop count) {
		return null;
	}

	public void checkCast(Oop obj, String className) {

	}

	public int checkInstanceOf(Oop oop, String className) {
		return 0;
	}

	/**
	 * new int[3][2], dimArray is [2,3];
	 * @param className
	 * @param dimArray
	 * @return
	 */
	public Oop createMultiArray(String className, int[] dimArray) {
		return null;
	}

	/**
	 *
	 Array Type
	 Value
	 boolean 	 4
	 char 	 5
	 float 	 6
	 double  7
	 byte 	 8
	 short 	 9
	 int 	 10
	 long 	 11
	 * @param type
	 * @param size
	 * @return
	 */

	public Oop createArray(int type, int size) {
		return new ArrayOop(size);
	}

	public void putStatic(String className, String fieldName, String description, Oop value) {
	}

	/**
	 *
	 * @param className
	 * @param methodName
	 * @param description
	 * @param arg arg array, foo.call(1,2,3,4) arg is [4,3,2,1,foo]
	 * @return
	 */
	public Oop invokeInterface(String className, String methodName, String description, Oop[] arg) {
		Oop thisObj =  arg[arg.length-1];

		Klass klass = thisObj.getKlass();
		Method method = klass.getMethod(className, methodName, description);

		Frame frame = new Frame();
		frame.localVariable = new ArrayList<>(method.codeAttribute.max_locals);
		for(int i=0;i <method.codeAttribute.max_locals;i++){
			frame.localVariable.add(null);
		}

		//init localVariable
		//this
		frame.localVariable.set(0,thisObj);

		int i=1;
		for(int j = arg.length -1; j>=0; j--){
			Oop oop = arg[j];
			frame.localVariable.set(i++, oop);
			if( oop instanceof LongOop || oop instanceof DoubleOop){
				i++;
			}
		}

		frame.opStack = new Stack<>();
		frame.currentMethod = method;
		frame.pc = 0;
		frame.currentKlass = klass;

		return executeEngine.execute(frame, this);
	}

	/**
	 *
	 * @param className
	 * @param methodName
	 * @param description
	 * @param arg arg array, foo.call(1,2,3,4) arg is [1,2,3,4,foo]
	 * @return
	 */
	public Oop invokeVirtual(String className, String methodName, String description, Oop[] arg) {

		Oop thisObj =  arg[arg.length-1];

		Klass klass = thisObj.getKlass();
		Method method = klass.getMethod(className, methodName, description);

		Frame frame = new Frame();
		frame.localVariable = new ArrayList<>(method.codeAttribute.max_locals);
		for(int i=0;i <method.codeAttribute.max_locals;i++){
			frame.localVariable.add(null);
		}

		//init localVariable
		//this
		frame.localVariable.set(0,thisObj);

		int i=1;
		for(int j=0; j< arg.length -1; j++){
			Oop oop = arg[j];
			frame.localVariable.set(i++, oop);
			if( oop instanceof LongOop || oop instanceof DoubleOop){
				i++;
			}
		}

		frame.opStack = new Stack<>();
		frame.currentMethod = method;
		frame.pc = 0;
		frame.currentKlass = klass;

		return executeEngine.execute(frame, this);
	}
	/**
	 *
	 * @param className
	 * @param methodName
	 * @param description
	 * @param arg arg array, foo.call(1,2,3,4) arg is [4,3,2,1]
	 * @return
	 */
	public Oop invokeStatic(String className, String methodName, String description, Oop[] arg) {
		Klass klass = bootStrapClassLoader.loadClass(className);
		Method method = klass.getMethod(className, methodName, description);

		Frame frame = new Frame();
		frame.localVariable = new ArrayList<>(method.codeAttribute.max_locals);
		for(int i=0;i <method.codeAttribute.max_locals;i++){
			frame.localVariable.add(null);
		}

		int i=0;
		for(int j = arg.length -1; j>=0; j--){
			Oop oop = arg[j];
			frame.localVariable.set(i++, oop);
			if( oop instanceof LongOop || oop instanceof DoubleOop){
				i++;
			}
		}

		frame.opStack = new Stack<>();
		frame.currentMethod = method;
		frame.pc = 0;
		frame.currentKlass = klass;

		return executeEngine.execute(frame, this);

	}

	/**
	 *
	 * @param className
	 * @param methodName
	 * @param description
	 * @param arg arg arg array, foo.call(1,2,3,4) arg is [4,3,2,1,foo]
	 * @return
	 */
	public Oop invokeSpecial(String className, String methodName, String description, Oop[] arg) {

		Oop thisObj =  arg[arg.length-1];

		Klass klass = thisObj.getKlass();
		Method method = klass.getMethod(className, methodName, description);

		Frame frame = new Frame();
		frame.localVariable = new ArrayList<>(method.codeAttribute.max_locals);
		for(int i=0;i <method.codeAttribute.max_locals;i++){
			frame.localVariable.add(null);
		}

		//init localVariable
		//this
		frame.localVariable.set(0,thisObj);

		int i=1;
		for(int j = arg.length - 2; j>=0; j--){
			Oop oop = arg[j];
			frame.localVariable.set(i++, oop);
			if( oop instanceof LongOop || oop instanceof DoubleOop){
				i++;
			}
		}

		frame.opStack = new Stack<>();
		frame.currentMethod = method;
		frame.pc = 0;
		frame.currentKlass = method.klass;

		return executeEngine.execute(frame, this);
	}
}
