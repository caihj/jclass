package com.fighter.template;

import java.util.concurrent.Callable;

/**
 * Created by caihaijun@navercorp.com on 2019/1/4.
 */
public class DummyCallable implements Callable,MyCallable {
	@Override
	public Object call() throws Exception {
		return null;
	}
}
