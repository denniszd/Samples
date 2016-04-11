package org.dennis.sample.btrace;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Export;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.OnTimer;
import com.sun.btrace.annotations.Self;

import static com.sun.btrace.BTraceUtils.concat;
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.str;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-08 15:51
 */
@BTrace
public class ThreadCounter {
    // create a jvmstat counter using @Export
    @Export
    private static long count;

    @OnMethod(
            clazz = "java.lang.Thread",
            method = "start"
    )
    public static void onNewThread(@Self Thread thread) {
        count++;
    }

    @OnTimer(3000)
    public static void onTimer() {
        println("============================================================");
        println(concat("thread count: ", str(count)));
    }
}
