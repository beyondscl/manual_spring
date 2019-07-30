package fun.fly.gad.db;


import java.sql.Connection;
import java.util.Random;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-07-30
 */
public class Test {

    public static void main(String[] args) {
        MysqlConnPool pool = MysqlConnPool.getInstance(10);

        ThreadLocal<Integer> threadLocal = new ThreadLocal();
        final int loopCount = 1; // 每个线程重复次数
        int count = 10; // 启动的线程数量
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                threadLocal.set(0);
                while (true) {
                    if (loopCount < threadLocal.get()) {
                        break;
                    }
                    threadLocal.set(threadLocal.get() + 1);
                    pool.getConnection();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int r = random.nextInt(2);
                    if (r == 0)
                        pool.releaseConn();
                    else
                        pool.closeConn();
                }

            }).start();
        }
    }
}
