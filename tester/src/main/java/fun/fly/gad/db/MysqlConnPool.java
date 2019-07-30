package fun.fly.gad.db;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : pettygadfly@gmail.com
 * @description : 尝试写一个简单的线程池
 * @date : 2019-07-30
 */
@Slf4j
public class MysqlConnPool implements ConnPool {
    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/yuese?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "Cqyh178178.";
    // =======================================================================
    private static int maxPoolSize = 10;
    private static int corePoolSize = 1;
    private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    private static final BlockingQueue<Connection> connQuene = new ArrayBlockingQueue<>(maxPoolSize);
    private static final AtomicInteger counter = new AtomicInteger();
    private Object lock = new Object();
    // =======================================================================

    private static MysqlConnPool instance;


    static {
        try {
            Class.forName(JDBC_DRIVER);
            instance = new MysqlConnPool();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private MysqlConnPool() {
    }

    public static MysqlConnPool getInstance() {
        return instance;
    }

    public static MysqlConnPool getInstance(int maxPoolSize) {
        assert (maxPoolSize > 0);
        MysqlConnPool.maxPoolSize = maxPoolSize;
        for (int i = 0; i < corePoolSize; i++) {
            connQuene.offer(instance.getInnerConn());
            counter.incrementAndGet();
        }
        return instance;
    }

    /**
     * 从连接池中获取连接，没有就等待
     *
     * @return
     */
    @Override
    public Connection getConnection() {
//        synchronized (lock) {
        Connection conn = threadLocal.get();
        if (null == conn) {
            try {
                conn = connQuene.size() > 0 ? connQuene.take() : null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (conn == null) {
                if (maxPoolSize > counter.intValue()) {
                    conn = instance.getInnerConn();
                    counter.incrementAndGet();
                    threadLocal.set(conn);
                    return conn;
                } else {
                    try {
                        log.info("线程:{},当前已经创建的链接数:{},队列中可用链接数:{}"
                                , Thread.currentThread()
                                , counter.intValue()
                                , connQuene.size() - connQuene.remainingCapacity());
                        // 防止虚假唤醒
                        while (maxPoolSize <= counter.intValue()) {
                            lock.wait();
                        }
                        return getConnection();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                threadLocal.set(conn);
            }
        }
        return conn;
//        }
    }

    /**
     * 获取一个链接
     *
     * @return
     */
    public Connection getInnerConn() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            log.info("线程:{},当前已经创建的链接数:{}", Thread.currentThread(), counter.intValue());
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean releaseConn() {
        synchronized (lock) {
            Connection conn = threadLocal.get();
            log.info("链接被归还:{}" + conn);
            if (conn != null) {
                connQuene.offer(conn);
                threadLocal.remove();
                lock.notifyAll();
            }

        }
        return true;
    }

    @Override
    public boolean closeConn() {
        synchronized (lock) {
            Connection conn = threadLocal.get();
            if (conn != null) {
                log.info("链接被关闭:{}" + conn);
                threadLocal.remove();
                counter.decrementAndGet();
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                lock.notifyAll();
            }
        }
        return true;
    }
}
