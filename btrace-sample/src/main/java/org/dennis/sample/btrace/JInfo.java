package org.dennis.sample.btrace;

import com.sun.btrace.annotations.BTrace;
import static com.sun.btrace.BTraceUtils.*;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-08 16:22
 */
@BTrace
public class JInfo {
    static {
        println("System Properties:");
        printProperties();
        println("VM Flags:");
        printVmArguments();
        println("OS Enviroment");
        printEnv();
        exit(0);
    }
}
