package org.dennis.sample.btrace;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.OnTimer;
import com.sun.btrace.annotations.TLS;

import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.strcat;
import static com.sun.btrace.BTraceUtils.timeNanos;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-06 16:58
 */
@BTrace
public class ExecutionTimeTracer {
    @TLS
    private static long startTime = 0;

    @OnMethod(
            clazz = "org.dennis.sample.btrace.Counter",
            method = "add"
    )
    public static void startExecute() {
        startTime = timeNanos();
    }

    @OnTimer(2000)
    public static void endExecute() {
        println(strcat("Execution time(nanos): ", strcat(str(timeNanos()), str(startTime))));
    }
}
