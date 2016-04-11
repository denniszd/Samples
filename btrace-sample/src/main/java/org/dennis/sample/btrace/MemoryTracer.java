package org.dennis.sample.btrace;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnTimer;
import static com.sun.btrace.BTraceUtils.*;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-08 14:14
 */
@BTrace
public class MemoryTracer {
    @OnTimer(5000)
    public static void printMemory() {
        println("============================================================");
        println("Heap: ");
        println(Sys.Memory.heapUsage());
        println("Non-Heap: ");
        println(Sys.Memory.nonHeapUsage());
    }
}
