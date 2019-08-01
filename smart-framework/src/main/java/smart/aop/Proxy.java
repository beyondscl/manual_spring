package smart.aop;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-08-01
 */
public interface Proxy {
    Object doProxy(ProxyChain proxyChain);
}
