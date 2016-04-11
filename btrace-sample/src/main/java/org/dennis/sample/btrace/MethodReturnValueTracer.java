package org.dennis.sample.btrace;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Return;

import static com.sun.btrace.BTraceUtils.Threads.jstack;
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.strcat;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-06 16:13
 */
@BTrace
public class MethodReturnValueTracer {
    /**
     * @param num    parameter
     * @param result return value
     */
    @OnMethod(
            clazz = "org.dennis.sample.btrace.Counter",
            method = "add",
            location = @Location(Kind.RETURN)
    )
    public static void traceExecute(int num, @Return int result) {
        println("============================================================");
        println(strcat("Parameter num: ", str(num)));
        println(strcat("Return result: ", str(result)));
        jstack();
    }
}
