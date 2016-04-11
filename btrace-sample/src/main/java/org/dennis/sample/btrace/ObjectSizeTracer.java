package org.dennis.sample.btrace;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Self;

import static com.sun.btrace.BTraceUtils.*;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-08 15:39
 */
@BTrace
public class ObjectSizeTracer {
    @OnMethod(
            clazz = "java.util.ArrayList",
            method = "<init>"
    )
    public static void onNew(@Self Object obj) {
        println(Strings.concat("object of: ", Reflective.name(Reflective.classOf(obj))));
        println(Strings.concat("size: ", Strings.str(sizeof(obj))));
    }
}
