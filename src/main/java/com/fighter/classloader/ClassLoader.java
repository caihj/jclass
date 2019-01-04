package com.fighter.classloader;

import com.fighter.model.Klass;

/**
 * Created by caihaijun@navercorp.com on 2019/1/4.
 */
public interface ClassLoader {

	Klass loadClass(String className);
}
