package smart.qeue;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author : pettygadfly@gmail.com
 * @description :
 * @date : 2019-07-30
 */
@Slf4j
public class MyArrayBlockQueueTest {
    public static void main(String[] args) {
        Random r = new Random();
        MyArrayBlockQueue<Integer> mq = new MyArrayBlockQueue<>(1);
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000 * r.nextInt(5));
                    log.info("add return : {}", mq.add(1));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000 * r.nextInt(5));
                    log.info("take return : {}", mq.take());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000 * r.nextInt(5));
                    log.info("put return : {}", mq.put(1));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000 * r.nextInt(5));
                    log.info("poll return : {}", mq.poll());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
