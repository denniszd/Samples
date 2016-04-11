package org.dennis.sample.btrace;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.OnTimer;
import com.sun.btrace.annotations.Self;

import java.lang.reflect.Field;

import static com.sun.btrace.BTraceUtils.concat;
import static com.sun.btrace.BTraceUtils.currentThread;
import static com.sun.btrace.BTraceUtils.field;
import static com.sun.btrace.BTraceUtils.get;
import static com.sun.btrace.BTraceUtils.jstack;
import static com.sun.btrace.BTraceUtils.printFields;
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.runFinalization;
import static com.sun.btrace.BTraceUtils.str;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-08 09:36
 */
@BTrace
public class FinalizeTracer {
    private static Field fdField = field("java.io.FileInputStream", "fd");

    @OnTimer(5000)
    public static void onTimer() {
        runFinalization();
    }

    @OnMethod(
            clazz = "java.io.FileInputStream",
            method = "finalize"
    )
    public static void onFinalize(@Self Object me) {
        println(concat("finalizing ", str(me)));
        printFields(me);
        printFields(get(fdField, me));
        println("============================================================");
    }

    @OnMethod(
            clazz = "java.io.FileInputStream",
            method = "close"
    )
    public static void onClose(@Self Object me) {
        println(concat("closing ", str(me)));
        println(concat("thread ", str(currentThread())));
        printFields(me);
        printFields(get(fdField, me));
        jstack();
        println("============================================================");
    }
}
