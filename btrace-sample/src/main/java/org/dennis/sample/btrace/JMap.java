package org.dennis.sample.btrace;

import static  com.sun.btrace.BTraceUtils.*;
import com.sun.btrace.annotations.BTrace;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-08 16:29
 */
@BTrace
public class JMap {
    static {
        String name;
        if (Sys.$length() == 3) {
            name = Sys.$(2);
        } else {
            name = "heap.bin";
        }
        Sys.Memory.dumpHeap(name);
        println("heap dumped!");
        exit(0);
    }
}
