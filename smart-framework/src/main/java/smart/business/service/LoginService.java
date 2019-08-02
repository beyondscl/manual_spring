package smart.business.service;

import lombok.extern.slf4j.Slf4j;
import smart.annotations.Aautowire;
import smart.annotations.Sservice;
import smart.aop.annotaions.Transaction;
import smart.business.db.MysqlConnPool;

import java.util.Date;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-07-31
 */
@Slf4j
@Sservice
public class LoginService {
    /**
     * todo :属性注入接口还需要改造
     * todo :代理后，属性需要再次注入，否则都是空
     */
    @Aautowire
    private MysqlConnPool mysqlConnPool;

    private String name = "这个属性会丢失吗？";
    private Date now = new Date();

    @Transaction
    public void doLogin() {
        log.info("doLogin get conn{}", mysqlConnPool.getConnection());
        System.out.println("LoginService" + "哈哈");
    }
}
