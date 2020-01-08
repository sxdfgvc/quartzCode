package com.zhengqing.modules.quartz.dao;

import java.util.HashMap;
import java.util.Map;

public class OrderState {
    /**
     * 已下单 1
     */
    public static final int CREATED = 1;
    /**
     * 已付款 2
     */
    public static final int PAYMENT = 2;
    /**
     * 已复核 3
     */
    public static final int REVIEWED = 3;
    /**
     * 已出库 4
     */
    public static final int STOCKOUT = 4;
    /**
     * 已签收 5
     */
    public static final int RECEIVE= 5;
    /**
     * 已取消 6
     */
    public static final int CANCELLED = 6;
    /**
     * 已关闭 7
     */
    public static final int CLOSED = 7;
    /**
     * 部分发货
     */
    public static final int PARTIAL_DELIVERY = 20;
    /**
     * 订单状态集合
     */
    public static final Map<Integer,String> map = new HashMap<>();
    static{
        map.put(CREATED, "已下单");
        map.put(PAYMENT, "已付款");
        map.put(REVIEWED, "已复核");
        map.put(STOCKOUT, "已发货");
        map.put(RECEIVE, "已签收");
        map.put(CANCELLED, "已取消");
        map.put(CLOSED, "已关闭");
        map.put(PARTIAL_DELIVERY, "部分发货");
    }
    /**
     * 订单状态集合
     */
    public static final Map<Integer,String> onTheAmountMap = new HashMap<>();
    static{
        onTheAmountMap.put(PAYMENT, "已付款");
        onTheAmountMap.put(REVIEWED, "已复核");
        onTheAmountMap.put(STOCKOUT, "已发货");
        onTheAmountMap.put(RECEIVE, "已签收");
    }

}
