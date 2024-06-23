import java.util.concurrent.*;

public class ThreadTest {
    public static void main2(String[] args) throws ExecutionException, InterruptedException {
        Callable<Object> callable = new Callable<>() {
            @Override
            public Object call() throws Exception {
                System.out.println("callable线程执行开始");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("callable线程执行结束");
                return "callable返回结果";
            }
        };
        FutureTask<Object> objectFutureTask = new FutureTask<>(callable);
        Thread thread = new Thread(objectFutureTask);
        thread.start();
        Object o = objectFutureTask.get();
        System.out.println(o);
        System.out.println("主线程结束");
    }
    public static void main1(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程执行开始");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("线程执行结束");
            }
        });
        thread.start();
    }
}
