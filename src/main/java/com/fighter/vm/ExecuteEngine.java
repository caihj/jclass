package com.fighter.vm;

import static com.fighter.constant.Instruct.*;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Stack;

import com.fighter.model.Klass;
import com.fighter.model.Oop;
import com.fighter.model.oopimpl.ArrayOop;
import com.fighter.model.oopimpl.ByteOop;
import com.fighter.model.oopimpl.CharOop;
import com.fighter.model.oopimpl.DoubleOop;
import com.fighter.model.oopimpl.FloatOop;
import com.fighter.model.oopimpl.IntegerOop;
import com.fighter.model.oopimpl.LongOop;
import com.fighter.model.oopimpl.ShortOop;
import com.fighter.tools.types.cpinfo.ClassInfo;
import com.fighter.tools.types.cpinfo.CpInfo;
import com.fighter.tools.types.cpinfo.FieldRefInfo;
import com.fighter.tools.types.cpinfo.NameAndTypeInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by caihaijun@navercorp.com on 2019/1/3.
 * https://cs.au.dk/~mis/dOvs/jvmspec/ref--41.html
 */
@Slf4j
public class ExecuteEngine {
	//top frame is current running frame
	private Stack<Frame> frameStack = new Stack<>();

	public void execute(Frame frame, VM vm) {
		//
		frameStack.push(frame);

		byte[] codeArray = frame.currentMethod.codeAttribute.code;
		Klass currentKlass = frame.currentKlass;
		CpInfo [] constant_pool = currentKlass.getReadClass().constant_pool;

		boolean wideFlag = true;

		while ( frame.pc < codeArray.length ) {
			int code = codeArray[frame.pc] & 0xff;
			switch (code) {

				case aaload: {
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					ArrayOop arrayOop = (ArrayOop)frame.opStack.pop();
					frame.opStack.push(arrayOop.getValue(integerOop));
				}
				break;
				case aastore: {
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					ArrayOop arrayOop = (ArrayOop)frame.opStack.pop();
					Oop value = frame.opStack.pop();
					arrayOop.setValue(integerOop, value);
				}
				break;
				case aconst_null: {
					frame.opStack.push(null);
				}
				break;
				case aload: {
					int index1 = codeArray[++frame.pc] & 0xff;
					if ( wideFlag ) {
						index1 = (index1 <<8 ) + ( codeArray[++frame.pc] & 0xff);
					}
					frame.opStack.push(frame.localVariable.get(index1));
				}
				break;
				case aload_0: {
					frame.opStack.push(frame.localVariable.get(0));
				}
				break;
				case aload_1: {
					frame.opStack.push(frame.localVariable.get(1));
				}
				break;
				case aload_2: {
					frame.opStack.push(frame.localVariable.get(2));
				}
				break;
				case aload_3: {
					frame.opStack.push(frame.localVariable.get(3));
				}
				break;
				case anewarray: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;

					IntegerOop count = (IntegerOop)frame.opStack.pop();

					int index = ( index1 << 8 ) + index2;
					String className = constant_pool[((ClassInfo)constant_pool[index]).name_index].toString();

					ArrayOop arrayOop = vm.newArrayInstance(className, count);
					frame.opStack.push(arrayOop);
				}
				break;
				case areturn: {
					Oop returnValue = frame.opStack.pop();
					frameStack.pop();
					Frame currentFrame = frameStack.peek();
					currentFrame.opStack.push(returnValue);
					return;
				}
				case arraylength: {
					ArrayOop arrayOop = (ArrayOop)frame.opStack.pop();
					IntegerOop integerOop = arrayOop.getSize();
					frame.opStack.push(integerOop);
				}
				break;
				case astore: {
					int index1 = codeArray[++frame.pc] & 0xff;
					if ( wideFlag ) {
						index1 = (index1 <<8 ) + ( codeArray[++frame.pc] & 0xff);
					}
					Oop oop = frame.opStack.pop();
					frame.localVariable.set(index1, oop);
				}
				break;
				case astore_0: {
					Oop oop = frame.opStack.pop();
					frame.localVariable.set(0, oop);
				}
				break;
				case astore_1: {
					Oop oop = frame.opStack.pop();
					frame.localVariable.set(1, oop);
				}
				break;
				case astore_2: {
					Oop oop = frame.opStack.pop();
					frame.localVariable.set(2, oop);
				}
				break;
				case astore_3: {
					Oop oop = frame.opStack.pop();
					frame.localVariable.set(3, oop);
				}
				break;
				case athrow: {
					Oop oop = frame.opStack.pop();
					frame.opStack.clear();
					frame.opStack.push(oop);
				}
				break;
				case baload: {
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					ArrayOop arrayOop = (ArrayOop)frame.opStack.pop();
					Oop value = arrayOop.getValue(integerOop);
					frame.opStack.push(value);
				}
				break;
				case bastore: {
					IntegerOop value = (IntegerOop)frame.opStack.pop();
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					ArrayOop arrayOop = (ArrayOop)frame.opStack.pop();
					arrayOop.setValue(integerOop, value);
				}
				break;
				case bipush: {
					int index1 = codeArray[++frame.pc] & 0xff;
					frame.opStack.push(new IntegerOop(index1));
				}
				break;
				case breakpoint: {
				}
				break;
				case caload: {
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					ArrayOop arrayOop = (ArrayOop)frame.opStack.pop();
					Oop value = arrayOop.getValue(integerOop);
					frame.opStack.push(value);
				}
				break;
				case castore: {
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					ArrayOop arrayOop = (ArrayOop)frame.opStack.pop();
					CharOop value = (CharOop)frame.opStack.pop();
					arrayOop.setValue(integerOop, value);
				}
				break;
				case checkcast: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;

					int index = ( index1 << 8 ) + index2;
					String className = constant_pool[((ClassInfo)constant_pool[index]).name_index].toString();

					Oop obj = frame.opStack.peek();
					vm.checkCast(obj, className);
				}
				break;
				case d2f: {
					DoubleOop doubleOop = (DoubleOop)frame.opStack.pop();
					FloatOop floatOop = new FloatOop((float)doubleOop.getValue());
					frame.opStack.push(floatOop);
				}
				break;
				case d2i: {
					DoubleOop doubleOop = (DoubleOop)frame.opStack.pop();
					IntegerOop integerOop = new IntegerOop((int)doubleOop.getValue());
					frame.opStack.push(integerOop);
				}
				break;
				case d2l: {
					DoubleOop doubleOop = (DoubleOop)frame.opStack.pop();
					LongOop longOop = new LongOop((long)doubleOop.getValue());
					frame.opStack.push(longOop);
				}
				break;
				case dadd: {
					DoubleOop doubleOop1= (DoubleOop)frame.opStack.pop();
					DoubleOop doubleOop2 = (DoubleOop)frame.opStack.pop();
					frame.opStack.push(new DoubleOop(doubleOop1.getValue() + doubleOop2.getValue()));
				}
				break;
				case daload: {
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					ArrayOop arrayOop = (ArrayOop)frame.opStack.pop();
					Oop value = arrayOop.getValue(integerOop);
					frame.opStack.push((DoubleOop)value);
				}
				break;
				case dastore: {
					DoubleOop doubleOop = (DoubleOop)frame.opStack.pop();
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					ArrayOop arrayOop = (ArrayOop)frame.opStack.pop();
					arrayOop.setValue(integerOop, doubleOop);
				}
				break;
				case dcmpg: {
					DoubleOop doubleOop1= (DoubleOop)frame.opStack.pop();

					DoubleOop doubleOop2 = (DoubleOop)frame.opStack.pop();
					if(Double.isNaN(doubleOop2.getValue()) || Double.isNaN(doubleOop1.getValue()) ) {
						frame.opStack.push(new IntegerOop(1));
					}  else if(doubleOop2.getValue() > doubleOop1.getValue()) {
						frame.opStack.push(new IntegerOop(1));
					} else if(doubleOop2.getValue() == doubleOop1.getValue()) {
						frame.opStack.push(new IntegerOop(0));
					} else {
						frame.opStack.push(new IntegerOop(-1));
					}
				}
				break;
				case dcmpl: {
					DoubleOop doubleOop1= (DoubleOop)frame.opStack.pop();
					DoubleOop doubleOop2 = (DoubleOop)frame.opStack.pop();
					if(Double.isNaN(doubleOop2.getValue()) || Double.isNaN(doubleOop1.getValue())) {
						frame.opStack.push(new IntegerOop(1));
					} else if(doubleOop2.getValue() > doubleOop1.getValue()) {
						frame.opStack.push(new IntegerOop(1));
					} else if(doubleOop2.getValue() == doubleOop1.getValue()) {
						frame.opStack.push(new IntegerOop(0));
					} else {
						frame.opStack.push(new IntegerOop(-1));
					}
				}
				break;
				case dconst_0: {
					frame.opStack.push(new DoubleOop(0.0));
				}
				break;
				case dconst_1: {
					frame.opStack.push(new DoubleOop(1.0));
				}
				break;
				case ddiv: {
					DoubleOop doubleOop1= (DoubleOop)frame.opStack.pop();
					DoubleOop doubleOop2 = (DoubleOop)frame.opStack.pop();
					frame.opStack.push(new DoubleOop(doubleOop1.getValue()/doubleOop2.getValue()));
				}
				break;
				case dload: {
					int index1 = codeArray[++frame.pc] & 0xff;
					if ( wideFlag ) {
						index1 = (index1 <<8 ) + ( codeArray[++frame.pc] & 0xff);
					}
					frame.opStack.push(frame.localVariable.get(index1));
				}
				break;
				case dload_0: {
					frame.opStack.push(frame.localVariable.get(0));
				}
				break;
				case dload_1: {
					frame.opStack.push(frame.localVariable.get(1));
				}
				break;
				case dload_2: {
					frame.opStack.push(frame.localVariable.get(2));
				}
				break;
				case dload_3: {
					frame.opStack.push(frame.localVariable.get(3));
				}
				break;
				case dmul: {
					DoubleOop doubleOop1= (DoubleOop)frame.opStack.pop();
					DoubleOop doubleOop2 = (DoubleOop)frame.opStack.pop();
					frame.opStack.push(new DoubleOop(doubleOop1.getValue()*doubleOop2.getValue()));
				}
				break;
				case dneg: {
					DoubleOop doubleOop1= (DoubleOop)frame.opStack.pop();
					frame.opStack.push(new DoubleOop(0 - doubleOop1.getValue()));
				}
				break;
				case drem: {
					DoubleOop doubleOop1= (DoubleOop)frame.opStack.pop();
					DoubleOop doubleOop2 = (DoubleOop)frame.opStack.pop();
					double value1 = doubleOop1.getValue();
					double value2 = doubleOop2.getValue();
					double ret = value2 - ((int)( value2 / value1 ) * value1);
					frame.opStack.push(new DoubleOop(doubleOop1.getValue() % doubleOop2.getValue()));
				}
				break;
				case dreturn: {
					Oop returnValue = frame.opStack.pop();
					frameStack.pop();
					Frame currentFrame = frameStack.peek();
					currentFrame.opStack.push(returnValue);
					return;
				}
				case dstore: {
					int index1 = codeArray[++frame.pc] & 0xff;
					if ( wideFlag ) {
						index1 = (index1 <<8 ) + ( codeArray[++frame.pc] & 0xff);
					}
					DoubleOop doubleOop = (DoubleOop)frame.opStack.pop();
					frame.localVariable.set(index1, doubleOop);
				}
				break;
				case dstore_0: {
					DoubleOop doubleOop = (DoubleOop)frame.opStack.pop();
					frame.localVariable.set(0, doubleOop);
				}
				break;
				case dstore_1: {
					DoubleOop doubleOop = (DoubleOop)frame.opStack.pop();
					frame.localVariable.set(1, doubleOop);
				}
				break;
				case dstore_2: {
					DoubleOop doubleOop = (DoubleOop)frame.opStack.pop();
					frame.localVariable.set(2, doubleOop);
				}
				break;
				case dstore_3: {
					DoubleOop doubleOop = (DoubleOop)frame.opStack.pop();
					frame.localVariable.set(3, doubleOop);
				}
				break;
				case dsub: {
					DoubleOop doubleOop1= (DoubleOop)frame.opStack.pop();
					DoubleOop doubleOop2 = (DoubleOop)frame.opStack.pop();
					frame.opStack.push(new DoubleOop(doubleOop1.getValue()  - doubleOop2.getValue()));
				}
				break;
				case dup: {
					frame.opStack.push(frame.opStack.peek());
				}
				break;
				case dup_x1: {
					Oop oop1 = frame.opStack.pop();
					Oop oop2 = frame.opStack.pop();
					frame.opStack.push(oop1);
					frame.opStack.push(oop2);
					frame.opStack.push(oop1);
				}
				break;
				case dup_x2: {
					Oop oop1 = frame.opStack.pop();
					Oop oop2 = frame.opStack.pop();
					Oop oop3 = frame.opStack.pop();
					frame.opStack.push(oop1);
					frame.opStack.push(oop3);
					frame.opStack.push(oop2);
					frame.opStack.push(oop1);
				}
				break;
				case dup2: {
					Oop oop1 = frame.opStack.pop();
					Oop oop2 = frame.opStack.pop();

					frame.opStack.push(oop2);
					frame.opStack.push(oop1);

					frame.opStack.push(oop2);
					frame.opStack.push(oop1);
				}
				break;
				case dup2_x1: {

					Oop oop1 = frame.opStack.pop();
					Oop oop2 = frame.opStack.pop();
					Oop oop3 = frame.opStack.pop();

					frame.opStack.push(oop2);
					frame.opStack.push(oop1);

					frame.opStack.push(oop3);
					frame.opStack.push(oop2);
					frame.opStack.push(oop1);

				}
				break;
				case dup2_x2: {

					Oop oop1 = frame.opStack.pop();
					Oop oop2 = frame.opStack.pop();
					Oop oop3 = frame.opStack.pop();
					Oop oop4 = frame.opStack.pop();

					frame.opStack.push(oop2);
					frame.opStack.push(oop1);

					frame.opStack.push(oop4);
					frame.opStack.push(oop3);
					frame.opStack.push(oop2);
					frame.opStack.push(oop1);
				}
				break;
				case f2d: {
					FloatOop floatOop = (FloatOop)frame.opStack.pop();
					frame.opStack.push(new DoubleOop(floatOop.getValue()));
				}
				break;
				case f2i: {
					FloatOop floatOop = (FloatOop)frame.opStack.pop();
					frame.opStack.push(new IntegerOop((int)floatOop.getValue()));
				}
				break;
				case f2l: {
					FloatOop floatOop = (FloatOop)frame.opStack.pop();
					frame.opStack.push(new LongOop((long)floatOop.getValue()));
				}
				break;
				case fadd: {
					FloatOop floatOop1 = (FloatOop)frame.opStack.pop();
					FloatOop floatOop2 = (FloatOop)frame.opStack.pop();
					frame.opStack.push(new FloatOop(floatOop1.getValue() + floatOop2.getValue()));
				}
				break;
				case faload: {
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					ArrayOop arrayOop = (ArrayOop)frame.opStack.pop();
					Oop value = arrayOop.getValue(integerOop);
					frame.opStack.push(value);
				}
				break;
				case fastore: {
					Oop value = frame.opStack.pop();
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					ArrayOop arrayOop = (ArrayOop)frame.opStack.pop();
					arrayOop.setValue(integerOop, value);
				}
				break;
				case fcmpg: {
					FloatOop floatOop1= (FloatOop)frame.opStack.pop();
					FloatOop floatOop2 = (FloatOop)frame.opStack.pop();
					if(Double.isNaN(floatOop1.getValue()) || Double.isNaN(floatOop2.getValue()) ) {
						frame.opStack.push(new IntegerOop(1));
					}  else if(floatOop2.getValue() > floatOop1.getValue()) {
						frame.opStack.push(new IntegerOop(1));
					} else if(floatOop2.getValue() == floatOop1.getValue()) {
						frame.opStack.push(new IntegerOop(0));
					} else {
						frame.opStack.push(new IntegerOop(-1));
					}
				}
				break;
				case fcmpl: {
					FloatOop floatOop1= (FloatOop)frame.opStack.pop();
					FloatOop floatOop2 = (FloatOop)frame.opStack.pop();
					if(Double.isNaN(floatOop1.getValue()) || Double.isNaN(floatOop2.getValue()) ) {
						frame.opStack.push(new IntegerOop(1));
					}  else if(floatOop2.getValue() > floatOop1.getValue()) {
						frame.opStack.push(new IntegerOop(1));
					} else if(floatOop2.getValue() == floatOop1.getValue()) {
						frame.opStack.push(new IntegerOop(0));
					} else {
						frame.opStack.push(new IntegerOop(-1));
					}
				}
				break;
				case fconst_0: {
					frame.opStack.push(new FloatOop(0));
				}
				break;
				case fconst_1: {
					frame.opStack.push(new FloatOop(1));
				}
				break;
				case fconst_2: {
					frame.opStack.push(new FloatOop(2));
				}
				break;
				case fdiv: {
					FloatOop floatOop1= (FloatOop)frame.opStack.pop();
					FloatOop floatOop2 = (FloatOop)frame.opStack.pop();
					frame.opStack.push(new FloatOop(floatOop1.getValue() / floatOop2.getValue()));
				}
				break;
				case fload: {
					int index1 = codeArray[++frame.pc] & 0xff;
					if ( wideFlag ) {
						index1 = (index1 <<8 ) + ( codeArray[++frame.pc] & 0xff);
					}
					frame.opStack.push(frame.opStack.push(frame.localVariable.get(index1)));
				}
				break;
				case fload_0: {
					frame.opStack.push(frame.opStack.push(frame.localVariable.get(0)));
				}
				break;
				case fload_1: {
					frame.opStack.push(frame.opStack.push(frame.localVariable.get(1)));
				}
				break;
				case fload_2: {
					frame.opStack.push(frame.opStack.push(frame.localVariable.get(2)));
				}
				break;
				case fload_3: {
					frame.opStack.push(frame.localVariable.get(3));
				}
				break;
				case fmul: {
					FloatOop floatOop1= (FloatOop)frame.opStack.pop();
					FloatOop floatOop2 = (FloatOop)frame.opStack.pop();
					frame.opStack.push(new FloatOop(floatOop1.getValue() * floatOop2.getValue()));
				}
				break;
				case fneg: {
					FloatOop floatOop1= (FloatOop)frame.opStack.pop();
					frame.opStack.push(new FloatOop(0- floatOop1.getValue() ));
				}
				break;
				case frem: {
					FloatOop floatOop1= (FloatOop)frame.opStack.pop();
					FloatOop floatOop2 = (FloatOop)frame.opStack.pop();

					float value1 = floatOop1.getValue();
					float value2 = floatOop2.getValue();
					float ret = value2 - ((int)( value2 / value1 ) * value1);
					frame.opStack.push(new FloatOop(ret));
				}
				break;
				case freturn: {
					Oop returnValue = frame.opStack.pop();
					frameStack.pop();
					Frame currentFrame = frameStack.peek();
					currentFrame.opStack.push(returnValue);
					return;
				}
				case fstore: {
					int index1 = codeArray[++frame.pc] & 0xff;
					if ( wideFlag ) {
						index1 = (index1 <<8 ) + ( codeArray[++frame.pc] & 0xff);
					}
					FloatOop floatOop1= (FloatOop)frame.opStack.pop();
					frame.localVariable.set(index1, floatOop1);
				}
				break;

				case fstore_0: {
					FloatOop floatOop1= (FloatOop)frame.opStack.pop();
					frame.localVariable.set(0, floatOop1);
				}
				break;

				case fstore_1: {
					FloatOop floatOop1= (FloatOop)frame.opStack.pop();
					frame.localVariable.set(1, floatOop1);
				}
				break;

				case fstore_2: {
					FloatOop floatOop1= (FloatOop)frame.opStack.pop();
					frame.localVariable.set(2, floatOop1);
				}
				break;

				case fstore_3: {
					FloatOop floatOop1= (FloatOop)frame.opStack.pop();
					frame.localVariable.set(3, floatOop1);
				}
				break;

				case fsub: {
					FloatOop floatOop1= (FloatOop)frame.opStack.pop();
					FloatOop floatOop2 = (FloatOop)frame.opStack.pop();
					frame.opStack.push(new FloatOop(floatOop1.getValue() - floatOop2.getValue()));
				}
				break;

				case getfield: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					CpInfo fieldRef = constant_pool[index];
					assert fieldRef instanceof FieldRefInfo;
					FieldRefInfo fieldRefInfo = (FieldRefInfo)fieldRef;

					String className = constant_pool[((ClassInfo)constant_pool[fieldRefInfo.class_index]).name_index].toString();
					String fieldName =  constant_pool[((NameAndTypeInfo)constant_pool[fieldRefInfo.name_and_type_index]).name_index].toString();
					String description =  constant_pool[((NameAndTypeInfo)constant_pool[fieldRefInfo.name_and_type_index]).descriptor_index].toString();

					Oop oop = frame.opStack.pop();
					Oop value = oop.getField(className, fieldName, description);
					frame.opStack.push(value);
				}
				break;

				case getstatic: {

					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					CpInfo fieldRef = constant_pool[index];
					assert fieldRef instanceof FieldRefInfo;
					FieldRefInfo fieldRefInfo = (FieldRefInfo)fieldRef;

					String className = constant_pool[((ClassInfo)constant_pool[fieldRefInfo.class_index]).name_index].toString();
					String fieldName =  constant_pool[((NameAndTypeInfo)constant_pool[fieldRefInfo.name_and_type_index]).name_index].toString();
					String description =  constant_pool[((NameAndTypeInfo)constant_pool[fieldRefInfo.name_and_type_index]).descriptor_index].toString();

					Oop value = vm.getStaticFiled(className, fieldName, description);
					frame.opStack.push(value);
				}
				break;
				case i_goto: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					frame.pc = index;
					continue;
				}
				case goto_w: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index3 = codeArray[++frame.pc] & 0xff;
					int index4 = codeArray[++frame.pc] & 0xff;
					int index = (index1 << 24 ) + (index2 <<16) + (index3 <<8 )+ index4;
					frame.pc = index;
					continue;
				}
				case i2b: {
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					frame.opStack.push(new ByteOop(integerOop.getValue()));
				}
				break;
				case i2c: {
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					frame.opStack.push(new CharOop(integerOop.getValue()));
				}
				break;
				case i2d: {
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					frame.opStack.push(new DoubleOop(integerOop.getValue()));
				}
				break;
				case i2f: {
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					frame.opStack.push(new FloatOop(integerOop.getValue()));
				}
				break;
				case i2l: {
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					frame.opStack.push(new LongOop(integerOop.getValue()));
				}
				break;
				case i2s: {
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					frame.opStack.push(new ShortOop(integerOop.getValue()));
				}
				break;
				case iadd: {
					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();
					frame.opStack.push(new IntegerOop(integerOop1.getValue() + integerOop2.getValue()));
				}
				break;
				case iaload: {
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					ArrayOop arrayOop = (ArrayOop)frame.opStack.pop();
					Oop value = arrayOop.getValue(integerOop);
					frame.opStack.push(value);
				}
				break;
				case iand: {
					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();
					frame.opStack.push(new IntegerOop(integerOop1.getValue() | integerOop2.getValue()));
				}
				break;
				case iastore: {
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					IntegerOop indexOop = (IntegerOop)frame.opStack.pop();
					ArrayOop arrayOop = (ArrayOop)frame.opStack.pop();
					arrayOop.setValue(indexOop, integerOop);
				}
				break;
				case iconst_m1: {
					frame.opStack.push(new IntegerOop(-1));
				}
				break;
				case iconst_0: {
					frame.opStack.push(new IntegerOop(0));
				}
				break;
				case iconst_1: {
					frame.opStack.push(new IntegerOop(1));
				}
				break;
				case iconst_2: {
					frame.opStack.push(new IntegerOop(2));
				}
				break;
				case iconst_3: {
					frame.opStack.push(new IntegerOop(3));
				}
				break;
				case iconst_4: {
					frame.opStack.push(new IntegerOop(4));
				}
				break;
				case iconst_5: {
					frame.opStack.push(new IntegerOop(5));
				}
				break;
				case idiv: {
					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();
					frame.opStack.push(new IntegerOop(integerOop1.getValue() / integerOop2.getValue()));
				}
				break;
				case if_acmpeq: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					Oop oop1 = frame.opStack.pop();
					Oop oop2 = frame.opStack.pop();

					if(oop1 == oop2){
						frame.pc = index;
						continue;
					}

				}
				break;
				case if_acmpne: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;
					Oop oop1 = frame.opStack.pop();
					Oop oop2 = frame.opStack.pop();

					if(oop1 != oop2){
						frame.pc = index;
						continue;
					}

				}
				break;
				case if_icmpeq: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;


					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();

					if(integerOop1.getValue() == integerOop2.getValue()){
						frame.pc = index;
						continue;
					}
				}
				break;
				case if_icmpge: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();

					if(integerOop1.getValue() >= integerOop2.getValue()){
						frame.pc = index;
						continue;
					}

				}
				break;
				case if_icmpgt: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();

					if(integerOop1.getValue() > integerOop2.getValue()){
						frame.pc = index;
						continue;
					}

				}
				break;
				case if_icmple: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();

					if(integerOop1.getValue() <= integerOop2.getValue()){
						frame.pc = index;
						continue;
					}
				}
				break;
				case if_icmplt: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();

					if(integerOop1.getValue() < integerOop2.getValue()){
						frame.pc = index;
						continue;
					}
				}
				break;
				case if_icmpne: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();

					if(integerOop1.getValue() != integerOop2.getValue()){
						frame.pc = index;
						continue;
					}

				}
				break;
				case ifeq: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					if(integerOop1.getValue() == 0) {
						frame.pc = index;
						continue;
					}
				}
				break;
				case ifge: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					if(integerOop1.getValue() >= 0) {
						frame.pc = index;
						continue;
					}
				}
				break;
				case ifgt: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					if(integerOop1.getValue() > 0) {
						frame.pc = index;
						continue;
					}
				}
				break;
				case ifle: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					if(integerOop1.getValue() <= 0) {
						frame.pc = index;
						continue;
					}
				}
				break;
				case iflt: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					if(integerOop1.getValue() < 0) {
						frame.pc = index;
						continue;
					}
				}
				break;
				case ifne: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					if(integerOop1.getValue() != 0) {
						frame.pc = index;
						continue;
					}

				}
				break;
				case ifnonnull: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					Oop oop = (Oop)frame.opStack.pop();
					if ( oop != null ) {
						frame.pc = index;
						continue;
					}

				}
				break;
				case ifnull: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					Oop oop = (Oop)frame.opStack.pop();
					if ( oop == null ) {
						frame.pc = index;
						continue;
					}
				}
				break;
				case iinc: {
					int index = 0;
					int const_value = 0;

					if(wideFlag){
						index = codeArray[++frame.pc] & 0xff;
						const_value = codeArray[++frame.pc];
					} else {
						int index1 = codeArray[++frame.pc] & 0xff;
						int index2 = codeArray[++frame.pc] & 0xff;
						index = ( index1 << 8 ) + index2;
						const_value = getOpCodeShort(codeArray, ++frame.pc);
						frame.pc +=2;
					}

					IntegerOop old = (IntegerOop)frame.localVariable.get(index);

					frame.localVariable.set(index, new IntegerOop(old.getValue()+ const_value));
					continue;
				}
				case iload: {
					int index1 = codeArray[++frame.pc] & 0xff;
					frame.opStack.push(frame.localVariable.get(index1));
				}
				break;
				case iload_0: {
					frame.opStack.push(new IntegerOop(0));
				}
				break;
				case iload_1: {
					frame.opStack.push(new IntegerOop(1));
				}
				break;
				case iload_2: {
					frame.opStack.push(new IntegerOop(2));
				}
				break;
				case iload_3: {
					frame.opStack.push(new IntegerOop(3));
				}
				break;
				case impdep1: {
					//reserved
				}
				break;
				case impdep2: {
					//reserved
				}
				break;
				case imul: {
					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();
					frame.opStack.push(new IntegerOop(integerOop1.getValue() * integerOop2.getValue()));
				}
				break;
				case ineg: {
					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					frame.opStack.push(new IntegerOop(0 -  integerOop1.getValue()));
				}
				break;
				case i_instanceof: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					String className = constant_pool[((ClassInfo)constant_pool[index]).name_index].toString();

					Oop oop = frame.opStack.pop();

					int ret = vm.checkInstanceOf(oop, className);

					frame.opStack.push(new IntegerOop(ret));
				}
				break;
				case invokedynamic: {

				}
				break;
				case invokeinterface: {
				}
				break;
				case invokespecial: {
				}
				break;
				case invokestatic: {
				}
				break;
				case invokevirtual: {
				}
				break;
				case ior: {
					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();
					frame.opStack.push(new IntegerOop(integerOop1.getValue() | integerOop2.getValue()));
				}
				break;
				case irem: {
					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();
					int value1 = integerOop1.getValue();
					int value2 = integerOop2.getValue();
					frame.opStack.push(new IntegerOop(value2- (value1/value2)* value2));
				}
				break;
				case ireturn: {
					Oop returnValue = frame.opStack.pop();
					frameStack.pop();
					Frame currentFrame = frameStack.peek();
					currentFrame.opStack.push(returnValue);
					return;
				}
				case ishl: {
					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();
					int value1 = integerOop1.getValue();
					int value2 = integerOop2.getValue();
					int ret = value2 << (value1 & 0x1f);
					frame.opStack.push(new IntegerOop(ret));
				}
				break;
				case ishr: {
					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();
					int value1 = integerOop1.getValue();
					int value2 = integerOop2.getValue();
					int ret = value2 >> (value1 & 0x1f);
					frame.opStack.push(new IntegerOop(ret));
				}
				break;
				case istore: {
					int index1 = codeArray[++frame.pc] & 0xff;
					if ( wideFlag ) {
						index1 = (index1 <<8 ) + ( codeArray[++frame.pc] & 0xff);
					}
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();
					frame.localVariable.set(index1, integerOop2);
				}
				break;
				case istore_0: {
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();
					frame.localVariable.set(0, integerOop2);
				}
				break;
				case istore_1: {
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();
					frame.localVariable.set(1, integerOop2);
				}
				break;
				case istore_2: {
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();
					frame.localVariable.set(2, integerOop2);
				}
				break;
				case istore_3: {
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();
					frame.localVariable.set(3, integerOop2);
				}
				break;
				case isub: {
					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();
					frame.opStack.push(new IntegerOop(integerOop1.getValue() - integerOop2.getValue()));
				}
				break;
				case iushr: {
					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();
					int value1 = integerOop1.getValue();
					int value2 = integerOop2.getValue();
					int ret = value2 >>> (value1 & 0x1f);
					frame.opStack.push(new IntegerOop(ret));
				}
				break;
				case ixor: {
					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					IntegerOop integerOop2 = (IntegerOop)frame.opStack.pop();
					frame.opStack.push(new IntegerOop(integerOop1.getValue() ^ integerOop2.getValue()));
				}
				break;
				case jsr: {
				}
				break;
				case jsr_w: {
				}
				break;
				case l2d: {
					LongOop longOop = (LongOop)frame.opStack.pop();
					frame.opStack.push(new DoubleOop(longOop.getValue()));
				}
				break;
				case l2f: {
					LongOop longOop = (LongOop)frame.opStack.pop();
					frame.opStack.push(new FloatOop(longOop.getValue()));
				}
				break;
				case l2i: {
					LongOop longOop = (LongOop)frame.opStack.pop();
					frame.opStack.push(new IntegerOop((int)longOop.getValue()));
				}
				break;
				case ladd: {
					LongOop longOop1 = (LongOop)frame.opStack.pop();
					LongOop longOop2 = (LongOop)frame.opStack.pop();
					frame.opStack.push(new LongOop(longOop1.getValue() + longOop2.getValue()));
				}
				break;
				case laload: {
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					ArrayOop arrayOop = (ArrayOop)frame.opStack.pop();
					Oop value = arrayOop.getValue(integerOop);
					frame.opStack.push(value);
				}
				break;
				case land: {
					LongOop longOop1 = (LongOop)frame.opStack.pop();
					LongOop longOop2 = (LongOop)frame.opStack.pop();
					frame.opStack.push(new LongOop(longOop1.getValue() & longOop2.getValue()));
				}
				break;
				case lastore: {
					int index1 = codeArray[++frame.pc] & 0xff;
					LongOop longOop = (LongOop)frame.opStack.pop();
					frame.localVariable.set(index1, longOop);
				}
				break;
				case lcmp: {
					LongOop longOop1 = (LongOop)frame.opStack.pop();
					LongOop longOop2 = (LongOop)frame.opStack.pop();

					long value1 = longOop1.getValue();
					long value2 = longOop2.getValue();
					if(value1 > value2 ){
						frame.opStack.push(new IntegerOop(-1));
					} else if( value1 < value2) {
						frame.opStack.push(new IntegerOop(1));
					} else {
						frame.opStack.push(new IntegerOop(0));
					}
				}
				break;
				case lconst_0: {
					frame.opStack.push(new LongOop(0));
				}
				break;
				case lconst_1: {
					frame.opStack.push(new LongOop(1));
				}
				break;
				case ldc: {
					int index1 = codeArray[++frame.pc] & 0xff;
					Oop oop = convertConstPoolToOop(constant_pool[index1]);
					frame.opStack.push(oop);
				}
				break;
				case ldc_w: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					Oop oop = convertConstPoolToOop(constant_pool[index]);
					frame.opStack.push(oop);
				}
				break;
				case ldc2_w: {
					//push double ,long to opStack.
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					Oop oop = convertConstPoolToOop(constant_pool[index]);
					frame.opStack.push(oop);
				}
				break;
				case ldiv: {
					LongOop longOop1 = (LongOop)frame.opStack.pop();
					LongOop longOop2 = (LongOop)frame.opStack.pop();
					frame.opStack.push(new LongOop(longOop1.getValue() / longOop2.getValue()));
				}
				break;
				case lload: {
					int index1 = codeArray[++frame.pc] & 0xff;
					if ( wideFlag ) {
						index1 = (index1 <<8 ) + ( codeArray[++frame.pc] & 0xff);
					}
					frame.opStack.push(frame.localVariable.get(index1));
				}
				break;
				case lload_0: {
					frame.opStack.push(new LongOop(0));
				}
				break;
				case lload_1: {
					frame.opStack.push(new LongOop(1));
				}
				break;
				case lload_2: {
					frame.opStack.push(new LongOop(2));
				}
				break;
				case lload_3: {
					frame.opStack.push(new LongOop(3));
				}
				break;
				case lmul: {
					LongOop longOop1 = (LongOop)frame.opStack.pop();
					LongOop longOop2 = (LongOop)frame.opStack.pop();
					frame.opStack.push(new LongOop(longOop1.getValue() * longOop2.getValue()));
				}
				break;
				case lneg: {
					LongOop longOop = (LongOop)frame.opStack.pop();
					frame.opStack.push(new LongOop(0 -  longOop.getValue()));
				}
				break;
				case lookupswitch: {
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					//need implement
					int nextInstructPos = frame.pc;
					//skip padding bytes
					frame.pc +=4;

					int defaultOffset = getOpCodeInt(codeArray, frame.pc);
					frame.pc +=4;

					int keySetCount = getOpCodeInt(codeArray, frame.pc);
					frame.pc +=4;
					int [] keyArray = new int[keySetCount];
					int [] offsetArray = new int[keySetCount];
					for(int i=0; i< keySetCount; i++ ) {
						keyArray[i] =  getOpCodeInt(codeArray, frame.pc);
						frame.pc +=4;
						offsetArray[i] =  getOpCodeInt(codeArray, frame.pc);
						frame.pc +=4;
					}

					int pos = Arrays.binarySearch(keyArray, integerOop.getValue());
					if(pos >=0 ){
						//found
						nextInstructPos = nextInstructPos + offsetArray[pos];
					} else {
						//not found
						nextInstructPos = nextInstructPos + defaultOffset;
					}

					frame.pc = nextInstructPos;

				}
				break;
				case lor: {
					LongOop longOop1 = (LongOop)frame.opStack.pop();
					LongOop longOop2 = (LongOop)frame.opStack.pop();
					frame.opStack.push(new LongOop(longOop1.getValue() | longOop2.getValue()));
				}
				break;
				case lrem: {
					LongOop integerOop1 = (LongOop)frame.opStack.pop();
					LongOop integerOop2 = (LongOop)frame.opStack.pop();
					long value1 = integerOop1.getValue();
					long value2 = integerOop2.getValue();
					frame.opStack.push(new LongOop(value2- (value1/value2)* value2));
				}
				break;
				case lreturn: {
					Oop returnValue = frame.opStack.pop();
					frameStack.pop();
					Frame currentFrame = frameStack.peek();
					currentFrame.opStack.push(returnValue);
					return;
				}
				case lshl: {
					IntegerOop longOop1 = (IntegerOop)frame.opStack.pop();
					LongOop longOop2 = (LongOop)frame.opStack.pop();
					long value1 = longOop1.getValue();
					long value2 = longOop2.getValue();
					long ret = value2 << (value1 & 0x3f);
					frame.opStack.push(new LongOop(ret));
				}
				break;
				case lshr: {
					IntegerOop integerOop1 = (IntegerOop)frame.opStack.pop();
					LongOop integerOop2 = (LongOop)frame.opStack.pop();
					long value1 = integerOop1.getValue();
					long value2 = integerOop2.getValue();
					long ret = value2 >> (value1 & 0x3f);
					frame.opStack.push(new LongOop(ret));
				}
				break;
				case lstore: {
					int index1 = codeArray[++frame.pc] & 0xff;
					if ( wideFlag ) {
						index1 = (index1 <<8 ) + ( codeArray[++frame.pc] & 0xff);
					}
					LongOop longOop = (LongOop)frame.opStack.pop();
					frame.localVariable.set(index1, longOop);
				}
				break;
				case lstore_0: {
					LongOop integerOop2 = (LongOop)frame.opStack.pop();
					frame.localVariable.set(0, integerOop2);
				}
				break;
				case lstore_1: {
					LongOop integerOop2 = (LongOop)frame.opStack.pop();
					frame.localVariable.set(1, integerOop2);
				}
				break;
				case lstore_2: {
					LongOop integerOop2 = (LongOop)frame.opStack.pop();
					frame.localVariable.set(2, integerOop2);
				}
				break;
				case lstore_3: {
					LongOop integerOop2 = (LongOop)frame.opStack.pop();
					frame.localVariable.set(3, integerOop2);
				}
				break;
				case lsub: {
					LongOop longOop1 = (LongOop)frame.opStack.pop();
					LongOop longOop2 = (LongOop)frame.opStack.pop();
					frame.opStack.push(new LongOop(longOop1.getValue() - longOop2.getValue()));
				}
				break;
				case lushr: {
					LongOop longOop1 = (LongOop)frame.opStack.pop();
					LongOop longOop2 = (LongOop)frame.opStack.pop();
					long op1 = longOop1.getValue();
					long op2 = longOop2.getValue() & 0x3f;
					long ret = op1>>>op2;

					frame.opStack.push(new LongOop(ret));
				}
				break;
				case lxor: {
					LongOop longOop1 = (LongOop)frame.opStack.pop();
					LongOop longOop2 = (LongOop)frame.opStack.pop();
					long op1 = longOop1.getValue();
					long op2 = longOop2.getValue();
					long ret = op1 ^ op2;

					frame.opStack.push(new LongOop(ret));
				}
				break;
				case monitorenter: {
					Oop oop = frame.opStack.pop();
					oop.lock();
				}
				break;
				case monitorexit: {
					Oop oop = frame.opStack.pop();
					oop.unlock();
				}
				break;
				case multianewarray: {
					//create a new array of dimensions dimensions

					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					String className = constant_pool[((ClassInfo)constant_pool[index]).name_index].toString();

					int dimensions = codeArray[++frame.pc] & 0xff;

					int[] dimArray = new int[dimensions];
					for ( int i=0; i< dimensions; i++) {
						IntegerOop sizeN = (IntegerOop)frame.opStack.pop();
						dimArray[i] = sizeN.getValue();
					}
					//new int[3][2], dimArray is [2,3];
					Oop array = vm.createMultiArray(className , dimArray);
					frame.opStack.push(array);
				}
				break;
				case i_new: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					String className = constant_pool[((ClassInfo)constant_pool[index]).name_index].toString();
					Oop oop = vm.newInstance(className);
					frame.opStack.push(oop);
				}
				break;
				case newarray: {
					int type = codeArray[++frame.pc] & 0xff;
					IntegerOop sizeN = (IntegerOop)frame.opStack.pop();

					Oop array = vm.createArray(type , sizeN.getValue());
					frame.opStack.push(array);
				}
				break;
				case nop: {
					//no operation
				}
				break;
				case pop: {
					frame.opStack.pop();
				}
				break;
				case pop2: {
					Oop oop = frame.opStack.pop();
					if( oop instanceof  DoubleOop || oop instanceof LongOop) {

					} else {
						frame.opStack.pop();
					}
				}
				break;
				case putfield: {
					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					CpInfo fieldRef = constant_pool[index];
					assert fieldRef instanceof FieldRefInfo;
					FieldRefInfo fieldRefInfo = (FieldRefInfo)fieldRef;

					String className = constant_pool[((ClassInfo)constant_pool[fieldRefInfo.class_index]).name_index].toString();
					String fieldName =  constant_pool[((NameAndTypeInfo)constant_pool[fieldRefInfo.name_and_type_index]).name_index].toString();
					String description =  constant_pool[((NameAndTypeInfo)constant_pool[fieldRefInfo.name_and_type_index]).descriptor_index].toString();

					Oop value = frame.opStack.pop();
					Oop objectRef = frame.opStack.pop();
					objectRef.putField(fieldName, value);
				}
				break;
				case putstatic: {

					int index1 = codeArray[++frame.pc] & 0xff;
					int index2 = codeArray[++frame.pc] & 0xff;
					int index = ( index1 << 8 ) + index2;

					CpInfo fieldRef = constant_pool[index];
					assert fieldRef instanceof FieldRefInfo;
					FieldRefInfo fieldRefInfo = (FieldRefInfo)fieldRef;

					String className = constant_pool[((ClassInfo)constant_pool[fieldRefInfo.class_index]).name_index].toString();
					String fieldName =  constant_pool[((NameAndTypeInfo)constant_pool[fieldRefInfo.name_and_type_index]).name_index].toString();
					String description =  constant_pool[((NameAndTypeInfo)constant_pool[fieldRefInfo.name_and_type_index]).descriptor_index].toString();
					Oop value = frame.opStack.pop();
					vm.putStatic(className, fieldName, description, value);
				}
				break;
				case ret: {
					int index1 = codeArray[++frame.pc] & 0xff;
					if ( wideFlag ) {
						index1 = (index1 <<8 ) + ( codeArray[++frame.pc] & 0xff);
					}

					IntegerOop address = (IntegerOop)frame.localVariable.get(index1);
					frame.pc = address.getValue();
					continue;
				}
				case i_return: {
					frameStack.pop();
					return;
				}
				case saload: {
					IntegerOop integerOop = (IntegerOop)frame.opStack.pop();
					ArrayOop arrayOop = (ArrayOop)frame.opStack.pop();
					Oop value = arrayOop.getValue(integerOop);
					frame.opStack.push(value);
				}
				break;
				case sastore: {
					ShortOop shortOop = (ShortOop)frame.opStack.pop();
					IntegerOop indexOop = (IntegerOop)frame.opStack.pop();
					ArrayOop arrayOop = (ArrayOop)frame.opStack.pop();
					arrayOop.setValue(indexOop, shortOop);
				}
				break;
				case sipush: {
					short value = ByteBuffer.wrap(new byte[]{codeArray[++frame.pc], codeArray[++frame.pc]}).getShort();
					frame.opStack.push(new IntegerOop(value));
				}
				break;
				case swap: {
					Oop oop1 = frame.opStack.pop();
					Oop oop2 = frame.opStack.pop();

					frame.opStack.push(oop1);
					frame.opStack.push(oop2);

				}
				break;
				case tableswitch: {
					IntegerOop index = (IntegerOop)frame.opStack.pop();
					frame.pc +=4;
					int defaultLabel = getOpCodeInt(codeArray, frame.pc);
					frame.pc +=4;
					int low = getOpCodeInt(codeArray, frame.pc);
					frame.pc +=4;
					int high = getOpCodeInt(codeArray, frame.pc);
					frame.pc +=4;
					int labelCount = high- low;
					int [] labelArray = new int[labelCount];
					for(int i=0; i< labelCount; i++){
						labelArray[i] = getOpCodeInt(codeArray, frame.pc);
						frame.pc +=4;
					}

					int i = index.getValue();
					if( i < low || i > high) {
						frame.pc += defaultLabel;
					} else {
						frame.pc += labelArray[i-low];
					}
					continue;
				}
				case wide: {
					wideFlag = true;

				}
				break;

				default:
					log.info("bad instruct");
			}

			frame.pc++;
		}
	}

	private Oop convertConstPoolToOop(CpInfo cpInfo) {
		return null;
	}

	private static int getOpCodeInt(byte []  opCode, int startIndex) {
		return ByteBuffer.wrap(new byte[]{opCode[startIndex],opCode[startIndex+1]
									,opCode[startIndex+2],opCode[startIndex+3]}).getInt();
	}

	private static short getOpCodeShort(byte []  opCode, int startIndex) {
		return ByteBuffer.wrap(new byte[]{opCode[startIndex],opCode[startIndex+1]}).getShort();
	}

}
