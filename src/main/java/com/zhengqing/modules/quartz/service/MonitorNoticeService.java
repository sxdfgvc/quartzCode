package com.zhengqing.modules.quartz.service;

import java.util.List;

public interface MonitorNoticeService {
    /**
     * 内部会员错误支付金额监控
     *
     * @return
     */
    public String innerMemberPayAmoutAlarm();

    /**
     * 多人领取同一张优惠券监控
     *
     * @return
     */
    public String manyMemberReceiveOneCoupon();

    /**
     * 贺礼一券多订单
     *
     * @return
     */
    public String couponUsedByManyOrder();

    /**
     * 没有自动过审的订单监控
     *
     * @return 没有自动过审的订单的id，逗号隔开
     */
    public String autoReviewAlarm();

    /**
     * 一人多鹤礼券的监控
     *
     * @return
     */
    public String multipleMemberCoupon();

    /**
     * 超过20天没有自动签收的订单
     *
     * @return
     */
    public String getNotAutomaticOrderReceive();

    /**
     * 6.优惠券已使用优惠券状态未改变
     *
     * @return
     */
    public String getCouponStatusWrongInfo();

    /**
     * 7.订单状态为已付款但支付表为未支付
     *
     * @return
     */
    public String getOrderStatusWrongInfo();

    /**
     * 已取消订单，但是自动过审的
     *
     * @return
     */
    public String queryErrorOrder();

    /**
     * 订单状态为已复核未发货但有运单号的总数为
     *
     * @return
     */
    public String getOrderStatusByNotYetShipped();

    /**
     * 查询被自动定时任务过审的订单总数量
     *
     * @return
     */
    public String getAutoReviewOrderQuantity();

    /**
     * 12、-- 查询order表2-4的数据状态
     *
     * @return
     */
    String queryOrderTwoTFour();

    /**
     * 13、已取消变成已发货
     *
     * @return
     */
    String queryOrderSixToFour();

    /**
     * 14、已取消变成已支付
     *
     * @return
     */
    String queryOrderSixToTwo();

    /**
     * 15、订单状态正常，没有包裹
     *
     * @return
     */
    String queryOrderNoPackage();

    /**
     * 16、查询异常订单
     * 1.订单取消未退还积分
     * 2.订单关闭未退还积分
     * 3.未扣积分，订单关闭退还积分
     * 4.未扣积分，订单取消退还积分
     * 5.订单复核未扣减积分
     * 6.订单发货未扣减积分
     * 7.订单签收未扣减积分
     *
     * @return
     */
    List<String> queryExecptionOrder();

    /**
     * 17、查询电商修改的快递单号没有同步到星妈优选的数目
     *
     * @return
     */
    String queryOrderExpressUnSync();

    /**
     * 异常父子单
     *
     * @return
     */
    String queryFatherSonList();

    /**
     * 库存可卖数变成负数
     *
     * @return
     */
    String queryErrorGoodsStock();

    /**
     * 异常状态定时任务
     *
     * @return
     */
    String queryErrorQuartz();

    String queryUnBindCoupon();

    List<String> getEmailAddress(int emailStatus);

    String queryNoExpressNumOrder();
}
