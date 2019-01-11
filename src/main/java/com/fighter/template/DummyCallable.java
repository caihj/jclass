package com.fighter.template;

import java.util.concurrent.Callable;

/**
 * Created by caihaijun@navercorp.com on 2019/1/4.
 */
public class DummyCallable implements MyCallable {

	@Override
	public Object call(int a, int b, long c) throws Exception {
		return null;
	}
}
