package com.fighter.vm;

import static com.fighter.constant.Instruct.*;

import java.util.Stack;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by caihaijun@navercorp.com on 2019/1/3.
 */
@Slf4j
public class ExecuteEngine {
	//top frame is current running frame
	private Stack<Frame> frameStack = new Stack<>();

	public void execute(Frame frame) {
		//
		frameStack.push(frame);

		byte[] codeArray = frame.currentMethod.codeAttribute.code;

		while ( frame.pc < codeArray.length ) {
			int code = codeArray[frame.pc] & 0xff;
			switch (code) {

				case aaload: {
				}
				break;
				case aastore: {
				}
				break;
				case aconst_null: {
				}
				break;
				case aload: {
				}
				break;
				case aload_0: {
				}
				break;
				case aload_1: {
				}
				break;
				case aload_2: {
				}
				break;
				case aload_3: {
				}
				break;
				case anewarray: {
				}
				break;
				case areturn: {
				}
				break;
				case arraylength: {
				}
				break;
				case astore: {
				}
				break;
				case astore_0: {
				}
				break;
				case astore_1: {
				}
				break;
				case astore_2: {
				}
				break;
				case astore_3: {
				}
				break;
				case athrow: {
				}
				break;
				case baload: {
				}
				break;
				case bastore: {
				}
				break;
				case bipush: {
				}
				break;
				case breakpoint: {
				}
				break;
				case caload: {
				}
				break;
				case castore: {
				}
				break;
				case checkcast: {
				}
				break;
				case d2f: {
				}
				break;
				case d2i: {
				}
				break;
				case d2l: {
				}
				break;
				case dadd: {
				}
				break;
				case daload: {
				}
				break;
				case dastore: {
				}
				break;
				case dcmpg: {
				}
				break;
				case dcmpl: {
				}
				break;
				case dconst_0: {
				}
				break;
				case dconst_1: {
				}
				break;
				case ddiv: {
				}
				break;
				case dload: {
				}
				break;
				case dload_0: {
				}
				break;
				case dload_1: {
				}
				break;
				case dload_2: {
				}
				break;
				case dload_3: {
				}
				break;
				case dmul: {
				}
				break;
				case dneg: {
				}
				break;
				case drem: {
				}
				break;
				case dreturn: {
				}
				break;
				case dstore: {
				}
				break;
				case dstore_0: {
				}
				break;
				case dstore_1: {
				}
				break;
				case dstore_2: {
				}
				break;
				case dstore_3: {
				}
				break;
				case dsub: {
				}
				break;
				case dup: {
				}
				break;
				case dup_x1: {
				}
				break;
				case dup_x2: {
				}
				break;
				case dup2: {
				}
				break;
				case dup2_x1: {
				}
				break;
				case dup2_x2: {
				}
				break;
				case f2d: {
				}
				break;
				case f2i: {
				}
				break;
				case f2l: {
				}
				break;
				case fadd: {
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
