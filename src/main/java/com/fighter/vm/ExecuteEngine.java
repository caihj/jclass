package com.fighter.vm;

import static com.fighter.constant.Instruct.*;

import java.util.Stack;

import com.fighter.model.Klass;
import com.fighter.model.Oop;
import com.fighter.model.oopimpl.ArrayOop;
import com.fighter.model.oopimpl.CharOop;
import com.fighter.model.oopimpl.DoubleOop;
import com.fighter.model.oopimpl.FloatOop;
import com.fighter.model.oopimpl.IntegerOop;
import com.fighter.model.oopimpl.LongOop;
import com.fighter.tools.types.cpinfo.ClassInfo;
import com.fighter.tools.types.cpinfo.CpInfo;
import com.fighter.tools.types.cpinfo.FieldRefInfo;
import com.fighter.tools.types.cpinfo.NameAndTypeInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by caihaijun@navercorp.com on 2019/1/3.
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
				}
				break;
				case arraylength: {
					ArrayOop arrayOop = (ArrayOop)frame.opStack.pop();
					IntegerOop integerOop = arrayOop.getSize();
					frame.opStack.push(integerOop);
				}
				break;
				case astore: {
					int index1 = codeArray[++frame.pc] & 0xff;
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
					frame.opStack.push(new DoubleOop(doubleOop1.getValue() % doubleOop2.getValue()));
				}
				break;
				case dreturn: {
					Oop returnValue = frame.opStack.pop();
					frameStack.pop();
					Frame currentFrame = frameStack.peek();
					currentFrame.opStack.push(returnValue);
				}
				break;
				case dstore: {
					int index1 = codeArray[++frame.pc] & 0xff;
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
				}
				break;
				case fastore: {
				}
				break;
				case fcmpg: {
				}
				break;
				case fcmpl: {
				}
				break;
				case fconst_0: {
				}
				break;
				case fconst_1: {
				}
				break;
				case fconst_2: {
				}
				break;
				case fdiv: {
				}
				break;
				case fload: {
				}
				break;
				case fload_0: {
				}
				break;
				case fload_1: {
				}
				break;
				case fload_2: {
				}
				break;
				case fload_3: {
				}
				break;
				case fmul: {
				}
				break;
				case fneg: {
				}
				break;
				case frem: {
				}
				break;
				case freturn: {
				}
				break;
				case fstore: {
				}
				break;

				case fstore_0: {
				}
				break;

				case fstore_1: {
				}
				break;

				case fstore_2: {
				}
				break;

				case fstore_3: {
				}
				break;

				case fsub: {
				}
				break;

				case getfield: {
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
				}
				break;
				case goto_w: {
				}
				break;
				case i2b: {
				}
				break;
				case i2c: {
				}
				break;
				case i2d: {
				}
				break;
				case i2f: {
				}
				break;
				case i2l: {
				}
				break;
				case i2s: {
				}
				break;
				case iadd: {
				}
				break;
				case iaload: {
				}
				break;
				case iand: {
				}
				break;
				case iastore: {
				}
				break;
				case iconst_m1: {
				}
				break;
				case iconst_0: {
				}
				break;
				case iconst_1: {
				}
				break;
				case iconst_2: {
				}
				break;
				case iconst_3: {
				}
				break;
				case iconst_4: {
				}
				break;
				case iconst_5: {
				}
				break;
				case idiv: {
				}
				break;
				case if_acmpeq: {
				}
				break;
				case if_acmpne: {
				}
				break;
				case if_icmpeq: {
				}
				break;
				case if_icmpge: {
				}
				break;
				case if_icmpgt: {
				}
				break;
				case if_icmple: {
				}
				break;
				case if_icmplt: {
				}
				break;
				case if_icmpne: {
				}
				break;
				case ifeq: {
				}
				break;
				case ifge: {
				}
				break;
				case ifgt: {
				}
				break;
				case ifle: {
				}
				break;
				case iflt: {
				}
				break;
				case ifne: {
				}
				break;
				case ifnonnull: {
				}
				break;
				case ifnull: {
				}
				break;
				case iinc: {
				}
				break;
				case iload: {
				}
				break;
				case iload_0: {
				}
				break;
				case iload_1: {
				}
				break;
				case iload_2: {
				}
				break;
				case iload_3: {
				}
				break;
				case impdep1: {
				}
				break;
				case impdep2: {
				}
				break;
				case imul: {
				}
				break;
				case ineg: {
				}
				break;
				case i_instanceof: {
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
				}
				break;
				case irem: {
				}
				break;
				case ireturn: {
				}
				break;
				case ishl: {
				}
				break;
				case ishr: {
				}
				break;
				case istore: {
				}
				break;
				case istore_0: {
				}
				break;
				case istore_1: {
				}
				break;
				case istore_2: {
				}
				break;
				case istore_3: {
				}
				break;
				case isub: {
				}
				break;
				case iushr: {
				}
				break;
				case ixor: {
				}
				break;
				case jsr: {
				}
				break;
				case jsr_w: {
				}
				break;
				case l2d: {
				}
				break;
				case l2f: {
				}
				break;
				case l2i: {
				}
				break;
				case ladd: {
				}
				break;
				case laload: {
				}
				break;
				case land: {
				}
				break;
				case lastore: {
				}
				break;
				case lcmp: {
				}
				break;
				case lconst_0: {
				}
				break;
				case lconst_1: {
				}
				break;
				case ldc: {
				}
				break;
				case ldc_w: {
				}
				break;
				case ldc2_w: {
				}
				break;
				case ldiv: {
				}
				break;
				case lload: {
				}
				break;
				case lload_0: {
				}
				break;
				case lload_1: {
				}
				break;
				case lload_2: {
				}
				break;
				case lload_3: {
				}
				break;
				case lmul: {
				}
				break;
				case lneg: {
				}
				break;
				case lookupswitch: {
				}
				break;
				case lor: {
				}
				break;
				case lrem: {
				}
				break;
				case lreturn: {
				}
				break;
				case lshl: {
				}
				break;
				case lshr: {
				}
				break;
				case lstore: {
				}
				break;
				case lstore_0: {
				}
				break;
				case lstore_1: {
				}
				break;
				case lstore_2: {
				}
				break;
				case lstore_3: {
				}
				break;
				case lsub: {
				}
				break;
				case lushr: {
				}
				break;
				case lxor: {
				}
				break;
				case monitorenter: {
				}
				break;
				case monitorexit: {
				}
				break;
				case multianewarray: {
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
				}
				break;
				case nop: {
				}
				break;
				case pop: {
				}
				break;
				case pop2: {
				}
				break;
				case putfield: {
				}
				break;
				case putstatic: {
				}
				break;
				case ret: {
				}
				break;
				case i_return: {
				}
				break;
				case saload: {
				}
				break;
				case sastore: {
				}
				break;
				case sipush: {
				}
				break;
				case swap: {
				}
				break;
				case tableswitch: {
				}
				break;
				case wide: {
				}
				break;

				default:
					log.info("bad instruct");
			}

			frame.pc++;
		}
	}
}
