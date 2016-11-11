package org.dennis.sample.gc;

/**
 * @author deng.zhang
 * @since 1.0.0
 * Created on 2016-11-11 15:54
 */
public class YoungGenGC {
    /**
     * 1 MB
     */
    private final static int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        testSpaceHandlePromotion();
    }

    /**
     * 在Minor GC触发时，会检测之前每次晋升到老年代的平均大小是否大于老年代的剩余空间，如果大于，改为直接进行一次Full GC，
     * 如果小于则查看HandlePromotionFailure设置是否允许担保失败，如果允许，那仍然进行Minor GC，如果不允许，则也要改为进行一次Full GC。
     * 上述规则适用于JDK 6 Update 24之之前的版本。
     * 在JDK 6 Update 24之后，HandlePromotionFailure参数不会再影响到虚拟机的空间分配担保策略，规则变为只要老年代的连续
     * 空间大于新生代对象总大小或者历次晋升的平均大小就会进行Minor GC，否则将进行Full GC。
     * -XX:-HandlePromotionFailure不允许担保失败
     * -XX:+HandlePromotionFailure允许担保失败
     * VM Options:  -server -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:-HandlePromotionFailure
     * -XX:+UseConcMarkSweepGC -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80
     * -XX:+CMSPermGenSweepingEnabled -XX:+CMSClassUnloadingEnabled
     * -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
     */
    @SuppressWarnings("unused")
    private static void testSpaceHandlePromotion() {
        byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6, allocation7;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        // 程序运行到下面的语句时将发生第一次Minor GC，GC完之后allocation1、allocation2和allocation3被迁移至Old Gen
        // 截止目前，历次晋升的平均大小为6M
        allocation4 = new byte[2 * _1MB];

        allocation5 = new byte[2 * _1MB];
        allocation6 = new byte[2 * _1MB];
        allocation4 = null;
        allocation5 = null;
        allocation6 = null;
        allocation7 = new byte[2 * _1MB];
    }

    @SuppressWarnings("unused")
    private static void testBigObjToOldGen() {
        //
    }

    @SuppressWarnings("unused")
    private static void testLongSurvivorToOldGen() {
        //
    }
}
