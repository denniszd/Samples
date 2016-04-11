package org.dennis.sample.btrace;

import java.util.Random;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-06 16:10
 */
public class BTraceTest {
    public static void main(String[] args) throws Exception {
        Random random = new Random();
        Counter counter = new Counter();
        while (true) {
            counter.add(random.nextInt(10));
            Thread.sleep(1000);
        }
    }
}
