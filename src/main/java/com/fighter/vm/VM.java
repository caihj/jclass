package com.fighter.vm;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import com.fighter.classloader.BootStrapClassLoader;
import com.fighter.constant.ClassName;
import com.fighter.constant.Constant;
import com.fighter.model.Klass;
import com.fighter.model.Method;
import com.fighter.model.Oop;
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

		Method method = klass.getMethod(methodName, description);

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

	void executeMethod(Oop oop, String methodName, String description) {

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
		executeMethod(oop, Constant.INIT, Constant.descripton);

		return oop;
	}
}
