package org.dennis.sample.btrace;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.OnTimer;
import com.sun.btrace.annotations.Self;

import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.strcat;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-06 19:26
 */
@BTrace
public class FieldTracer {
    private static Object totalCount = 0;

    @OnMethod(
            clazz = "org.dennis.sample.btrace.Counter",
            method = "add",
            location = @Location(Kind.RETURN)
    )
    public static void traceExecute(@Self Object counter) {
        totalCount = BTraceUtils.get(BTraceUtils.field("org.dennis.sample.btrace.Counter", "totalCount"), counter);
    }

    @OnTimer(1000)
    public static void print() {
        println("============================================================");
        println(strcat("totalCount: ", str(totalCount)));
    }
}
