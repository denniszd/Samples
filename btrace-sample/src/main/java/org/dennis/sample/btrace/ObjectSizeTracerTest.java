package org.dennis.sample.btrace;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-08 15:39
 */
public class ObjectSizeTracerTest {
    private static List<String> list = new ArrayList<String>();
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        while (true) {
            list.addAll(new ArrayList<String>(random.nextInt(10)));
            Thread.sleep(5000);
        }
    }
}
