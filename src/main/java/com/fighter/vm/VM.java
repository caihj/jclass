package com.fighter.vm;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import com.fighter.classloader.BootStrapClassLoader;
import com.fighter.model.Klass;
import com.fighter.model.Method;
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

	}

	public void execute(String mainClass){

		Klass klass = bootStrapClassLoader.loadClass(mainClass);
		if (klass == null ){
			log.error("class not found " + mainClass);
			return;
		}



		//find main method
		Method method = klass.getMethod("main", "([Ljava/lang/String;)V");

		if( method == null ){
			log.error("main method not found " + mainClass);
			return;
		}

		//create a frame
		Frame frame = new Frame();

		frame.localVariable = new ArrayList<>(method.codeAttribute.max_locals);
		frame.opStack = new Stack<>();
		frame.currentMethod = method;
		frame.pc = 0;

		//execute it
		executeEngine.execute(frame);

	}

}
