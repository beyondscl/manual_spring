package fun.fly.gad.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-07-30
 */
public interface ConnPool {
    Connection getConnection() throws SQLException, ClassNotFoundException, InterruptedException;

    boolean releaseConn() throws SQLException;

    boolean closeConn() throws SQLException;
}
