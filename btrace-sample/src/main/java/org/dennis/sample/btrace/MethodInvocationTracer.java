package org.dennis.sample.btrace;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnEvent;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.TargetInstance;
import com.sun.btrace.annotations.TargetMethodOrField;
import com.sun.org.apache.xpath.internal.operations.String;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-06 17:21
 */
@BTrace
public class MethodInvocationTracer {
    private static long count;

    @OnMethod(
            clazz = "org.dennis.sample.btrace.Counter",
            method = "add",
            location = @Location(value = Kind.CALL, clazz = "/.*/", method = "sleep")
    )
    public static void traceExecute(
            @ProbeClassName String probeClassName,
            @ProbeMethodName String probeMethodName,
            @TargetInstance Object targetInstance,
            @TargetMethodOrField String targetMethodOrField) {
        BTraceUtils.println("============================================================");
        BTraceUtils.println(BTraceUtils.strcat("Probe Class Name: ", BTraceUtils.str(probeClassName)));
        BTraceUtils.println(BTraceUtils.strcat("Probe Method Name: ", BTraceUtils.str(probeMethodName)));
        BTraceUtils.println(BTraceUtils.strcat("Target Instance: ", BTraceUtils.str(BTraceUtils.classOf(targetInstance))));
        BTraceUtils.println(BTraceUtils.strcat("Target method or field: ", BTraceUtils.str(targetMethodOrField)));
        count++;
    }

    @OnEvent
    public static void getCount() {
        BTraceUtils.println(BTraceUtils.strcat("Count: ", BTraceUtils.str(count)));
    }
}
