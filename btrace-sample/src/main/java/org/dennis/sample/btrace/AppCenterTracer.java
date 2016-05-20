package org.dennis.sample.btrace;

import com.sun.btrace.AnyType;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Duration;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Return;
import com.sun.btrace.annotations.TLS;

import java.util.List;

import static com.sun.btrace.BTraceUtils.Threads.jstack;
import static com.sun.btrace.BTraceUtils.compare;
import static com.sun.btrace.BTraceUtils.field;
import static com.sun.btrace.BTraceUtils.get;
import static com.sun.btrace.BTraceUtils.heapUsage;
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.size;
import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.strcat;
import static com.sun.btrace.BTraceUtils.threadCount;
import static com.sun.btrace.BTraceUtils.timeNanos;
import static com.sun.btrace.BTraceUtils.timestamp;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-18 13:52
 */
@BTrace
public class AppCenterTracer {
    @TLS
    private static long startTime = 0;

    @OnMethod(
            clazz = "com.tcl.mie.appcenter.server.manager.FeatureManager",
            method = "findFeatures"
    )
    public static void startExecute() {
        startTime = timeNanos();
    }

    @OnMethod(
            clazz = "com.tcl.mie.appcenter.server.manager.FeatureManager",
            method = "findFeatures",
            location = @Location(Kind.RETURN)
    )
    public static void endExecute(AnyType filterParam, AnyType offset, AnyType pageSize, @Duration long duration, @Return List<AnyType> result) {
        if (compare("appcentermonitor", str(get(field("com.tcl.mie.appcenter.server.model.app.filter.AppFilterParam", "imei"), filterParam)))) {
            long time = timeNanos() - startTime;
            println("====================================================================================================");
            println(timestamp("yyyy-MM-dd HH:mm:ss SSS"));
            println(strcat("Execute time(millis): ", str(time / 1000 / 1000)));
            println(strcat("Duration(millis): ", str(duration / 1000 / 1000)));
            println(strcat("Result size: ", str(size(result))));
            println(strcat("Heap usage: ", str(heapUsage())));
            println(strcat("Thread count: ", str(threadCount())));
        }
    }

    @OnMethod(
            clazz = "com.tcl.mie.appcenter.osapp.api.OsAppService",
            method = "findAppsByIds",
            location = @Location(Kind.RETURN)
    )
    public static void traceExecute(List<AnyType> var1, AnyType var2, AnyType var3, @Return List<AnyType> result) {
        println("====================================================================================================");
        println(strcat("Parameter var1: ", str(var1)));
        println(strcat("Parameter var2: ", str(var2)));
        println(strcat("Parameter var3: ", str(var3)));
        println(strcat("Result size: ", str(size(result))));
        println(strcat("Result: ", str(result)));
        jstack();
    }
}
