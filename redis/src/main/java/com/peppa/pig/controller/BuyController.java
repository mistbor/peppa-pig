package com.peppa.pig.controller;

import com.peppa.pig.common.DealQueueThread;
import com.peppa.pig.common.RedisTool;
import com.peppa.pig.model.BuyRequest;
import com.peppa.pig.model.Result;
import com.peppa.pig.utils.BuyQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("buy")
@Slf4j
public class BuyController {
    /**
     * 线程安全的请求队列
     */
    private static BuyQueue<BuyRequest> buyQueue = null;

    @Autowired
    private RedisTool redisTool;

    @RequestMapping(value = "/addOrders", method = RequestMethod.POST)
    @ResponseBody
    public Object addOrders(BuyRequest buyRequest) {
        Result result;
        Jedis jedis = redisTool.getResources();
        try {
            // 下订单之前，先获取商品的剩余数量
            int residue = Integer.valueOf(jedis.get("residue" + buyRequest.getGoodId()));
            // 如果剩余数量不足，直接响应客户端“售罄”
            if (residue < 1) {
                result = new Result("售罄", false);
                return result;
            }
            // 如果还有剩余商品，就准备将请求放到请求队列中

            if (buyQueue == null) {
                // 第一次初始化请求队列，队列的容量为当前商品剩余数量
                buyQueue = new BuyQueue<BuyRequest>(residue);
            }
            if (buyQueue.remainingCapacity() > 0) {
                // 当队列的可用容量大于0时，将请求放到请求队列中
                buyQueue.put(buyRequest);
            } else {
                // 当请求队列已满，本次请求不能处理，直接响应客户端提示请求队列已满
                result = new Result("抢购队列已满，请稍后重试！", false);
                return result;
            }

            if (!DealQueueThread.excute) {
                DealQueueThread dealQueueThread = new DealQueueThread(buyQueue);
                // TODO: ThreadPoolUtil 待完善
                //ThreadPoolUtil.pool.execute(dealQueueThread);
                log.info("thread.activeCount = " + Thread.activeCount());
            }
            result = new Result("下单成功", true);
        } catch (InterruptedException e) {
            result = new Result("下单失败", false);
            log.error("");
        } finally {
            redisTool.returnResource(jedis);
        }
        return result;
    }

}
