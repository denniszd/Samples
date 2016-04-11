package org.dennis.sample.btrace;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-06 15:20
 */
public class Counter {
    private static int totalCount = 0;

    public int add(int num) throws Exception {
        totalCount += num;
        sleep();
        return totalCount;
    }

    public void sleep() throws Exception {
        Thread.sleep(1000);
    }
}
