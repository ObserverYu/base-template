package org.test;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 *  
 * @author YuChen
 * @date 2020/9/16 10:45
 **/

public class TestDisruptor2 {

    public static class MyOrder{
        private long orderNum;

        private long paySum;

        private String fromProduce;

        public String getFromProduce() {
            return fromProduce;
        }

        public void setFromProduce(String fromProduce) {
            this.fromProduce = fromProduce;
        }

        public long getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(long orderNum) {
            this.orderNum = orderNum;
        }

        public long getPaySum() {
            return paySum;
        }

        public void setPaySum(long paySum) {
            this.paySum = paySum;
        }
    }

    public static class MyOrderProducer {

        private final RingBuffer<MyOrder> ringBuffer;

        public MyOrderProducer(RingBuffer<MyOrder> ringBuffer) {
            this.ringBuffer = ringBuffer;
        }


        /**
         * onData用来发布事件，每调用一次就发布一次事件
         * 它的参数会用过事件传递给消费者
         */
        public void onData(long orderNum, long paySum) {
            //可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
            long sequence = ringBuffer.next();
            try {
                //用上面的索引取出一个空的事件用于填充（获取该序号对应的事件对象）
                MyOrder order = ringBuffer.get(sequence);
                //获取要通过事件传递的业务数据
                order.setOrderNum(orderNum);
                order.setPaySum(paySum);
            } finally {
                //发布事件
                //注意，最后的 ringBuffer.publish 方法必须包含在 finally 中以确保必须得到调用；如果某个请求的 sequence 未被提交，将会堵塞后续的发布操作或者其它的 producer。
                ringBuffer.publish(sequence);
            }
        }
    }

    public static class MyConsumer implements WorkHandler<MyOrder>{
        private String consumerId;

        private static AtomicInteger count = new AtomicInteger(0);

        public int getCount(){
            return count.get();
        }

        public MyConsumer(String consumerId){
            this.consumerId = consumerId;
        }

        @Override
        public void onEvent(MyOrder myOrder) throws Exception {
            System.out.println("当前消费者: " + this.consumerId + "，消费信息：" + myOrder.getOrderNum());
            System.out.println("线程: " + Thread.currentThread().getName());
            count.incrementAndGet();
        }
    }

    public static class MyOrderInstanceFactory implements EventFactory<MyOrder> {

        @Override
        public MyOrder newInstance() {
            return new MyOrder();
        }
    }

    public static class MySimpleThreadFactory implements  ThreadFactory{

        private static AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("thread"+count);
            count.incrementAndGet();
            return thread;
        }
    }

    public static void main(String[] args) {
        WaitStrategy strategy = new BlockingWaitStrategy();
        Disruptor<MyOrder> disruptor = new Disruptor<>(
                new MyOrderInstanceFactory()
                , 64
                , new MySimpleThreadFactory()
                , ProducerType.MULTI
                , strategy);

        MyConsumer[] consumers = new MyConsumer[3];
        for(int i = 0; i < consumers.length; i++){
            consumers[i] = new MyConsumer("c" + i);
        }

        RingBuffer<MyOrder> ringBuffer = disruptor.getRingBuffer();
        SequenceBarrier barriers = ringBuffer.newBarrier();
        WorkerPool<MyOrder> workerPool =
                new WorkerPool<MyOrder>(ringBuffer,
                        barriers,
                        new ExceptionHandler() {
                            @Override
                            public void handleEventException(Throwable throwable, long l, Object o) {

                            }

                            @Override
                            public void handleOnStartException(Throwable throwable) {

                            }

                            @Override
                            public void handleOnShutdownException(Throwable throwable) {

                            }
                        },
                        consumers);

    }

}