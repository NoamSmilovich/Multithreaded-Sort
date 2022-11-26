import java.util.concurrent.TimeUnit;

public class Simple_Profiler {
    static private long max_active_threads;
    static private long max_memory_usage_by_jvm;
    private final long memory_before_program_starts;
    private static boolean flag;
    private static Thread t1;
    public Simple_Profiler() {
        memory_before_program_starts = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        t1 = new Thread(this::start);
        t1.start();
    }
    public void start() {
        long threads_active;
        long cur_memory_usage_by_jvm;
        flag = true;

        while (flag) {
            threads_active = Thread.activeCount();
            cur_memory_usage_by_jvm = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
            max_active_threads = Math.max(max_active_threads, threads_active);
            max_memory_usage_by_jvm = Math.max(max_memory_usage_by_jvm, cur_memory_usage_by_jvm);
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public long[] end() {
        flag = false;
        try {
            t1.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        long max_memory_usage_by_pro = (max_memory_usage_by_jvm-memory_before_program_starts)/1024;
        return new long[]{max_memory_usage_by_pro, max_active_threads};
    }
}
