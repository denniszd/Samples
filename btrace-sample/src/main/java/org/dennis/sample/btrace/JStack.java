package org.dennis.sample.btrace;

import com.sun.btrace.annotations.BTrace;
import static com.sun.btrace.BTraceUtils.*;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-08 15:32
 */
@BTrace
public class JStack {
    static {
        deadlocks(false);
        jstackAll();
        exit(0);
    }
}
