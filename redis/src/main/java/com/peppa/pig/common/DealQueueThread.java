package com.peppa.pig.common;

import com.peppa.pig.model.BuyOrders;
import com.peppa.pig.model.BuyRequest;
import com.peppa.pig.service.BuyGoodService;
import com.peppa.pig.service.BuyOrdersService;
import com.peppa.pig.utils.BuyQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Slf4j
public class DealQueueThread implements Runnable {

    private static DealQueueThread dealQueueThread;

    @Autowired
    private BuyGoodService buyGoodService;

    @Autowired
    private BuyOrdersService buyOrdersService;

    @Autowired
    private RedisTool redisTool;

    private Jedis jedis;

    private BuyQueue<BuyRequest> buyQueue;

    /**
     * 线程的默认执行标志为未执行，即空闲状态
     */
    public static boolean excute = false;

    public DealQueueThread() {
    }

    public DealQueueThread(BuyQueue<BuyRequest> buyQueue) {
        this.buyQueue = buyQueue;
        this.jedis = dealQueueThread.redisTool.getResources();
    }

    /**
     * @PostConstruct 和 @PreDestroy 用来修饰一个非静态的void方法，而且这个方法不能有抛出异常声明
     * @PostConstruct：被它修饰的方法会在服务器加载Servlet时运行，且只会被服务器调用一次，类似于Servlet的init方法，但是在init方法之前运行
     * @PreDestroy：被它修饰的方法会在服务器卸载Servlet时运行，且只会被服务器调用一次，类似于Servlet的destroy方法，但是在destroy方法之后运行
     */
    @PostConstruct
    public void init() {
        dealQueueThread = this;
        dealQueueThread.buyGoodService = this.buyGoodService;
        dealQueueThread.buyOrdersService = this.buyOrdersService;
        dealQueueThread.redisTool = this.redisTool;
    }

    @Override
    public void run() {
        try {
            // 修改线程的默认执行标志为执行状态
            excute = true;
            // 开始处理请求队列中的请求，按照队列FIFO的规则，先处理先放到队列中的请求
            while (buyQueue != null && buyQueue.size() > 0) {
                // 取出队列中的请求
                BuyRequest buyRequest = buyQueue.take();
                // 处理请求
                dealWithQueue(buyRequest);
            }
        } catch (InterruptedException e) {
            log.error("DealQueueThread: " + e);
        } finally {
            excute = false;
        }


    }

    /**
     * 轮询订单
     * 思路：查询订单和商品剩余数量，有以下三种情况：
     * 1. 查到订单，直接跳转到确认订单并支付的页面完成支付；
     * 2. 还没有查询到订单，但是剩余商品数量大于0，说明请求还在队列中，继续轮询；
     * 3. 没有查到订单，剩余商品数量等于或小于0，说明抢购失败了，直接响应客户抢购失败。
     */

    public synchronized void dealWithQueue(BuyRequest buyRequest) {
        // 为了尽量保证数据的一致性，处理之前先从redis中获取当前抢购商品的剩余数量
        int residue = Integer.valueOf(jedis.get("residue" + buyRequest.getGoodId()));
        if (residue < 1) {
            // 如果没有剩余商品，就直接返回
            buyRequest.setResponseStatus(3);
            return;
        }
        // 如果有剩余商品，先在redis中将剩余数量减一，再开始下订单
        jedis.decr("residue" + buyRequest.getGoodId());
        // 将数据库中剩余数量减一，这一步处理可以在队列处理完成之后一次性更新剩余数量
        dealQueueThread.buyOrdersService.minusResidue(buyRequest.getGoodId());

        // 请求处理，下订单
        BuyOrders bo = new BuyOrders();
        bo.setGoodId(buyRequest.getGoodId());
        bo.setUserId(buyRequest.getUserId());
        int orderId = dealQueueThread.buyOrdersService.insert(bo);
        BuyOrders orders = dealQueueThread.buyOrdersService.getById(orderId);
        // 订单Id
        buyRequest.setOrderId(orderId);
        // 订单信息
        buyRequest.setBuyOrders(orders);
        // 处理完成状态
        buyRequest.setResponseStatus(1);
    }
}
