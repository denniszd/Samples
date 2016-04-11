package org.dennis.sample.btrace;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-08 16:14
 */
public class ThreadCounterTest {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(60 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            Thread.sleep(3000);
        }
    }
}
