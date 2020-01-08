package com.zhengqing.modules.quartz.mapper;

import com.zhengqing.modules.quartz.dao.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MonitorNoticeMapper {
    /**
     * 内部会员错误支付金额监控
     *
     * @return
     */
    public List<OrderPO> getInnerMemberPayAmoutAlarm();

    /**
     * 2、没有自动过审的订单监控
     *
     * @return 没有自动过审的订单的id，逗号隔开
     */
    public List<OrderPO> autoReviewAlarm();

    /**
     * 3、多人领取同一张优惠券监控
     *
     * @return
     */
    public List<MemberCouponDO> manyMemberReceiveOneCoupon();

    /**
     * 查询已支付的订单
     *
     * @return
     */
    public List<OrderPO> queryPayOrder();

    /**
     * 4、贺礼一券多订单
     *
     * @return
     */
    public List<PaymentRecord> couponUsedByManyOrder();

    /**
     * 4、贺礼一券多订单——前8小时的增量
     *
     * @return
     */
    public List<PaymentRecord> couponUsedByManyOrderIncrement();

    /**
     * 5、一人多鹤礼券的监控
     *
     * @return
     */
    public List<MemberCouponPO> multipleMemberCoupon();

    /**
     * 超过20天已全部发货但未自动签收的订单总数为
     *
     * @return
     */
    public List<String> getNotAutomaticReceiveDeliveryOrder();

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
     * 8、已取消订单，但是自动过审的
     *
     * @return
     */
    public List<OrderPO> queryErrorOrder();

    /**
     * 9、超过20天没有自动签收的订单
     *
     * @return
     */
    public String getNotAutomaticOrderReceive();

    /**
     * 10、订单状态为已复核未发货但有运单号的总数为
     *
     * @return
     */
    public List<String> getOrderStatusByNotYetShipped();

    /**
     * 11、被自动过审定时任务过审的订单总数
     *
     * @return
     */
    public String getAutoReviewOrderQuantity();

    /**
     * 12、-- 查询order表2-4的数据状态，未复审的订单变成已发货
     *
     * @return
     */
    List<String> queryOrderTwoTFour();

    /**
     * 13、已取消变成已发货
     *
     * @return
     */
    List<String> queryOrderSixToFour();

    /**
     * 14、已取消变成已支付
     *
     * @return
     */
    List<String> queryOrderSixToTwo();

    /**
     * 15、订单状态正常，没有包裹
     *
     * @return
     */
    List<Map<String, Object>> queryOrderNoPackage();

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
    List<ExceptionOrderPO> queryExecptionOrder();

    /**
     * 17、查询电商修改的快递单号没有同步到星妈优选的数目
     *
     * @param thirdOrderCode
     * @param skuIds
     * @return
     */
    List<String> queryOrderExpressUnSync(@Param("thirdOrderCode") String thirdOrderCode, @Param("skuIds") String[] skuIds);

    List<OrderPO> getAllParentSonOrder();

    List<String> queryErrorQuartz();

    List<String> getEmailAddress(int emailStatus);

    List<Map<String, String>> queryNoExpressNumOrder();
}
