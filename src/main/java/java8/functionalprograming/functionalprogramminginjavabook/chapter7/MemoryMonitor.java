package java8.functionalprograming.functionalprogramminginjavabook.chapter7;

/**
 * @author Tushar Chokshi @ 11/7/16.
 */
// pg 204 ????????????
public class MemoryMonitor {
/*    public static void monitorMemory(double threshold) {
        findPSOldGenPool().forEachOrThrow(poolMxBean ->
                poolMxBean.setCollectionUsageThreshold((int) Math.floor(poolMxBean
                        .getUsage().getMax() * threshold)));
        NotificationEmitter emitter = (NotificationEmitter) ManagementFactory.getMemoryMXBean();
        emitter.addNotificationListener(notificationListener, null, null);
    }

    private static NotificationListener notificationListener =
            (Notification notification, Object handBack) -> {
                if (notification.getType().equals(MemoryNotificationInfo
                        .MEMORY_COLLECTION_THRESHOLD_EXCEEDED)) {
// cleanly shutdown the application;
                }
            };

    private static Result<MemoryPoolMXBean> findPSOldGenPool() {
        return List.fromCollection(ManagementFactory.getMemoryPoolMXBeans())
                .first(x -> x.getName().equals("PS Old Gen"));
    }*/
}