package com.fighter.classloader;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.fighter.model.Klass;
import com.fighter.tools.ClassObject;
import com.fighter.tools.ClassReader;
import com.fighter.vm.VM;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by caihaijun@navercorp.com on 2019/1/3.
 */

@Slf4j
public class BootStrapClassLoader implements ClassLoader{

	public ConcurrentHashMap<String, Klass> loadClass = new ConcurrentHashMap<>();


	public  Klass loadClass(String className) {

		Klass klass = loadClass.get(className);
		if( klass!=null ){
			return klass;
		}

		klass =  doLoadClass(className);
		loadClass.put(className, klass);
		return klass;
	}


	private Klass doLoadClass(String className)  {

		try {
			ClassObject obj = ClassReader.readClass(className);
			Klass klass = new Klass(obj,className, this);

			LinkedList superInterfaceList = new LinkedList();


			String superClassName = klass.getSuperClassName();
			log.info("super class Name {}", superClassName );

			//load super class
			Klass superClass = null;
			if( superClassName !=null ){
				//load super class
				superClass = loadClass(superClassName);
			}

			// load supper interface
			List<String> interfaceNames = klass.getInterfaceNames();
			if (!interfaceNames.isEmpty()) {
				for (String interfaceName : interfaceNames) {
					Klass interfaceClass = loadClass(interfaceName);
					superInterfaceList.add(interfaceClass);
				}
			}

			klass.link(superClass , superInterfaceList);

			return klass;
		} catch (IOException e) {
			log.info(e.getMessage(), e);
		}
		return null;
	}
}
