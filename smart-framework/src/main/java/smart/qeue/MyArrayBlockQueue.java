package smart.qeue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * 这里我就写简单的方法
 * @date : 2019-07-30
 */
public class MyArrayBlockQueue<E> implements MyBlockQueue {
    private Object[] items;
    private int putIndex;
    private int getIndex;
    private int capacity;
    private int size;

    // 重入锁
    private final ReentrantLock lock = new ReentrantLock();
    //这里是是空和是满，和源码返过来的
    private Condition isEmpty = lock.newCondition();
    private Condition isFull = lock.newCondition();

    public MyArrayBlockQueue() {
        this(16);
    }

    public MyArrayBlockQueue(int capacity) {
        this.capacity = capacity;
        items = new Object[capacity];
    }

    @Override
    public boolean add(Object o) {
        try {
            lock.lock();
            if (this.size == capacity)
                return false;
            this.items[putIndex] = o;
            putIndex++;
            if (putIndex == capacity) putIndex = 0;
            size++;
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean put(Object o) {
        try {
            lock.lock();
            while (this.size == capacity) {
                isFull.await();
            }
            isEmpty.signalAll();
            this.items[putIndex] = o;
            putIndex++;
            if (putIndex == capacity) putIndex = 0;
            size++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return true;
    }

    @Override
    public E poll() {
        try {
            lock.lock();
            if (this.size == 0) return null;
            E e = (E) this.items[this.getIndex];
            getIndex++;
            if (getIndex == capacity) getIndex = 0;
            size--;
            return e;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public E take() {
        try {
            lock.lock();
            while (this.size == 0) {
                try {
                    isEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isFull.signalAll();
            if (this.size == 0) return null;
            E e = (E) this.items[this.getIndex];
            getIndex++;
            size--;
            if (getIndex == capacity) getIndex = 0;
            return e;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int size() {
        return 0;
    }
}
