package org.dennis.sample.btrace;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-08 09:44
 */
public class FinalizeTrackerTest {
    private static final String FILE_NAME = "testFinalizeTrackerFile";

    public static void main(String[] args) throws IOException, InterruptedException {
        String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + FILE_NAME;
        while (true) {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            byte[] bytes = new byte[1000];
            fileInputStream.read(bytes);
            System.out.println(new String(bytes));
            fileInputStream.close();
            Thread.sleep(10000);
        }
    }
}
