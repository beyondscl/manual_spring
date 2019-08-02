package smart.qeue;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-07-30
 */
public interface MyBlockQueue<E> {
    /**
     * 非阻塞 add = offer
     *
     * @param e
     * @return
     */
    boolean add(E e);

    /**
     * 阻塞
     *
     * @param e
     * @return
     */
    boolean put(E e);

    /**
     * 非阻塞
     *
     * @return
     */
    E poll();

    /**
     * 阻塞
     *
     * @return
     */
    E take();

    /**
     * 当前队列元素长度
     *
     * @return
     */
    int size();
}
