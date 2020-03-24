package com.zhengqing.modules.quartz.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhengqing.modules.quartz.dao.*;
import com.zhengqing.modules.quartz.mapper.MonitorNoticeMapper;
import com.zhengqing.modules.quartz.service.MonitorNoticeService;
import com.zhengqing.utils.ERPUtil;
import net.rubyeye.xmemcached.MemcachedClient;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MonitorNoticeServiceImpl implements MonitorNoticeService {
    private static final Logger log = LoggerFactory.getLogger(MonitorNoticeService.class);
    private static final int SUCCESS_CODE = 200;
    private static final String SUB_POINT_SUFFIX = "XMYXDEDUCT";
    private static final String SEND_POINT_SUFFIX = "XMYXSEND";
    @Value("${crmUrl}")
    private String crmUrl;
    @Value("${excelUrl}")
    private String excelUrl;
    @Resource
    private MonitorNoticeMapper monitorNoticeMapper;
    @Resource
    private MemcachedClient memcachedClient;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String innerMemberPayAmoutAlarm() {
        //目前查询系统当前时间前四十八小时的订单
        List<String> list = new ArrayList<>();
        List<OrderPO> orderPOS = monitorNoticeMapper.getInnerMemberPayAmoutAlarm();
        Map<Integer, List<OrderPO>> map = new HashMap<>();
        if (orderPOS != null && orderPOS.size() > 0) {
            orderPOS.stream().forEach((ob) -> {
                if (ob.getHasChildren() || ob.getParentOrderId() > 0) {
                    int id = ob.getId();
                    if (ob.getParentOrderId() > 0) {
                        id = ob.getParentOrderId();
                    }
                    if (map.get(id) == null) {
                        List<OrderPO> ll = new ArrayList<>();
                        ll.add(ob);
                        map.put(ob.getId(), ll);
                    } else {
                        map.get(id).add(ob);
                    }
                }
            });
            orderPOS.stream().forEach((ob) -> {
                if (!ob.getHasChildren() && ob.getParentOrderId() == 0) {
                    if (ob.getPayableAmount().compareTo(ob.getAfterFoldingPrice()) != 0) {
                        list.add(String.valueOf(ob.getId()));
                    }
                } else {
                    if (ob.getHasChildren()) {
                        BigDecimal payableAmout = new BigDecimal(0);
                        BigDecimal afterFoldingPrice = new BigDecimal(0);
                        for (OrderPO op : map.get(ob.getId())) {
                            payableAmout.add(op.getPayableAmount());
                            afterFoldingPrice.add(op.getAfterFoldingPrice());
                        }
                        if (payableAmout.compareTo(afterFoldingPrice) != 0) {
                            list.add(String.valueOf(ob.getId()));
                        }
                    }
                }
            });
            log.info("内部会员支付金额错误订单Id:{}", StringUtils.join(list, ","));
        } else {
            log.info("暂无内购订单!");
        }
        return "1、内部会员支付金额错误订单总条数为:" + list.size();
    }

    @Override
    public String manyMemberReceiveOneCoupon() {
        long start = System.currentTimeMillis();
        StringBuffer content = new StringBuffer("3、多人领取同一张优惠券,总条数为:");
        List<MemberCouponDO> memberCouponDOS = monitorNoticeMapper.manyMemberReceiveOneCoupon();
        long end = System.currentTimeMillis();
        if (null != memberCouponDOS && memberCouponDOS.size() > 0) {
            content.append(memberCouponDOS.size());
        } else {
            content.append(0);
        }
        return content.toString();
    }

    @Override
    public String couponUsedByManyOrder() {
        long start = System.currentTimeMillis();
        StringBuffer content = new StringBuffer("4、一贺礼券多订单,总条数为:");
        List<PaymentRecord> paymentRecords = monitorNoticeMapper.couponUsedByManyOrder();
        long end = System.currentTimeMillis();
        if (null != paymentRecords && paymentRecords.size() > 0) {
            content.append(paymentRecords.size());
//            log.info(new StringBuffer(content).append(",paymentRecords:").append(JSONObject.toJSON(paymentRecords)).append(",总耗时:").append(end-start).append("ms").toString());
        } else {
            content.append(0);
//            log.info(new StringBuffer(content).append(",paymentRecords:null").append(",总耗时:").append(end-start).toString());
        }
        content.append("，前8小时的增量数据为:");
        List<PaymentRecord> paymentRecordsIncrement = monitorNoticeMapper.couponUsedByManyOrderIncrement();
        if (null != paymentRecordsIncrement && paymentRecordsIncrement.size() > 0) {
            content.append(paymentRecordsIncrement.size());
        } else {
            content.append(0);
        }
        return content.toString();
    }

    @Override
    public String autoReviewAlarm() {
        StringBuilder content = new StringBuilder("2、没有自动过审的订单,");
        StringBuilder logContent = new StringBuilder();
        String logStr = "";
        try {
            List<OrderPO> orderPOS = monitorNoticeMapper.autoReviewAlarm();
            if (CollectionUtil.isNotEmpty(orderPOS)) {
                content.append("总数:" + orderPOS.size());
                for (OrderPO orderPO : orderPOS) {
                    int id = orderPO.getId();
                    logContent.append(id + ",");
                }
                logStr = logContent.toString().substring(0, logContent.toString().length() - 1);
            } else {
                content.append("总数:0");
            }
        } catch (Exception e) {
            log.error("没自动过审的订单监控出现异常{}", e);
        }
        String contentStr = content.toString();
        log.info("{},orderID:{}", contentStr, logStr);
        return contentStr;
    }

    @Override
    public String multipleMemberCoupon() {
        StringBuilder content = new StringBuilder("5、一人多鹤礼券,");
        StringBuilder logContent = new StringBuilder();
        String logStr = "";
        try {
            List<MemberCouponPO> memberCouponPOS = monitorNoticeMapper.multipleMemberCoupon();
            if (CollectionUtil.isNotEmpty(memberCouponPOS)) {
                content.append("总数:" + memberCouponPOS.size());
                for (MemberCouponPO po : memberCouponPOS) {
                    int memberId = po.getMemberId();
                    logContent.append(memberId + ",");
                }
                logStr = logContent.toString().substring(0, logContent.toString().length() - 1);
            } else {
                content.append("总数:0");
            }
        } catch (Exception e) {
            log.error("一人多鹤礼券监控出现异常{}", e);
        }
        String contentStr = content.toString();
        log.info("{},memberID:{}", contentStr, logStr);
        return contentStr;
    }

    @Override
    public String getNotAutomaticOrderReceive() {
        StringBuilder content = new StringBuilder("9、超过20天没有自动签收的订单,");
        StringBuilder logStr = new StringBuilder("超过20天已全部发货但未自动签收的订单总数为:");
        try {
            String count = monitorNoticeMapper.getNotAutomaticOrderReceive();
            //超过20天(全部发货)没有自动签收的订单
            List<String> orderIds = monitorNoticeMapper.getNotAutomaticReceiveDeliveryOrder();
            if (StringUtils.isNotBlank(count)) {
                content.append("总条数:" + count);
            } else {
                content.append("总数:0;(本次没有排查到数据)");
            }
            content.append(",已全部发货但未自动签收的订单总数为:");
            if (null != orderIds) {
                content.append(orderIds.size());
                log.info(logStr.append("{},orderIds:{}").toString(), orderIds.size(), JSONObject.toJSONString(orderIds));
            } else {
                content.append("0");
                log.info(logStr.append("0").toString());
            }
        } catch (Exception e) {
            log.error("超过20天没有自动签收的订单异常", e);
        }
        String contentStr = content.toString();
        log.info("{}", contentStr);
        return contentStr;
    }

    @Override
    public String getCouponStatusWrongInfo() {
        StringBuffer stb = new StringBuffer("6、优惠券已使用但优惠券状态未改变的优惠券总条数为:  ");
        try {
            String wrongCouponNos = monitorNoticeMapper.getCouponStatusWrongInfo();
            if (StringUtils.isNotBlank(wrongCouponNos)) {
                stb.append(wrongCouponNos.split(",").length);
                log.info("优惠券已使用但优惠券状态未改变的优惠券为  " + wrongCouponNos);
            } else {
                stb.append("0");
            }
        } catch (Exception e) {
            log.error("查询优惠券已使用但优惠券状态未改变的优惠券出错,{}", e);
        }
        return stb.toString();
    }

    @Override
    public String getOrderStatusWrongInfo() {
        StringBuffer stb = new StringBuffer("7、订单状态为已付款但支付表为未支付orderId总数为:  ");
        try {
            String wrongOrderids = monitorNoticeMapper.getOrderStatusWrongInfo();
            if (StringUtils.isNotBlank(wrongOrderids)) {
                stb.append(wrongOrderids.split(",").length);
                log.info("订单状态为已付款但支付表为未支付订单为   " + wrongOrderids);
            } else {
                stb.append("0");
            }
        } catch (Exception e) {
            log.error("查询订单状态为已付款但支付表为未支付订单出错,{}", e);
        }

        return stb.toString();
    }

    @Override
    public String queryErrorOrder() {
        StringBuilder content = new StringBuilder("8、已取消订单,但是自动过审的,");
        StringBuilder logContent = new StringBuilder();
        String logStr = "";
        try {
            List<OrderPO> orderPOS = monitorNoticeMapper.queryErrorOrder();
            if (CollectionUtil.isNotEmpty(orderPOS)) {
                content.append("总数:" + orderPOS.size());
                for (OrderPO po : orderPOS) {
                    int id = po.getId();
                    logContent.append(id + ",");
                }
                logStr = logContent.toString().substring(0, logContent.toString().length() - 1);
            } else {
                content.append("总数:0");
            }
        } catch (Exception e) {
            log.error("已取消订单,但是自动过审的监控出现异常", e);
        }
        String contentStr = content.toString();
        log.info("{},orderID:{}", contentStr, logStr);
        return contentStr;
    }

    @Override
    public String getOrderStatusByNotYetShipped() {
        StringBuffer stb = new StringBuffer("10、订单状态为已复核未发货但有运单号的总数为:");
        try {
            List<String> notYetShippedOrderids = monitorNoticeMapper.getOrderStatusByNotYetShipped();
            if (notYetShippedOrderids.size() > 0) {
                stb.append(notYetShippedOrderids.size());
                log.info("订单状态为已复核未发货但有运单号的订单为:" + notYetShippedOrderids.toArray());
            } else {
                stb.append("0");
            }
        } catch (Exception e) {
            log.error("订单状态为已复核未发货但有运单号的总数出错,{}", e);
        }

        return stb.toString();
    }

    @Override
    public String getAutoReviewOrderQuantity() {
        StringBuffer stb = new StringBuffer("11、被自动定时任务过审的订单总数量:");
        try {
            Long quantity = memcachedClient.get("autoReviewOrderQuantity#", 3000);
            if (quantity != null) {
                stb.append(quantity.longValue());
            } else {
                stb.append("0");
            }
            log.info("从缓存查询被自动定时任务过审的订单总数量:{}", quantity == null ? "0" : quantity.longValue());
        } catch (Exception e) {
            log.error("从缓存查询被自动定时任务过审的订单总数量出错", e);
        }
        return stb.toString();
    }

    @Override
    public String queryOrderTwoTFour() {
        StringBuffer stb = new StringBuffer("12、未复核变为已发货的订单,");
        StringBuffer orderNoContent = new StringBuffer();
        try {
            List<String> orderPOS = monitorNoticeMapper.queryOrderTwoTFour();
            if (orderPOS.size() > 0) {
                stb.append("总数：" + orderPOS.size());
                log.info("未复核变为已发货的订单总数为:" + orderPOS.toArray());
                for (String po : orderPOS) {
                    orderNoContent.append(po + "，");
                }
                stb.append("，有问题的订单编号有：" + orderNoContent);
            } else {
                stb.append("0");
            }
        } catch (Exception e) {
            log.error("统计未复核变为已发货的订单总数出错,{}", e);
        }
        return stb.toString();
    }

    @Override
    public String queryOrderSixToFour() {
        StringBuilder content = new StringBuilder("13、已取消变成已发货,");
        StringBuilder logContent = new StringBuilder();
        String logStr = "";
        try {
            List<String> orderPOS = monitorNoticeMapper.queryOrderSixToFour();
            if (CollectionUtil.isNotEmpty(orderPOS)) {
                content.append("总数:" + orderPOS.size());
                for (String po : orderPOS) {
                    logContent.append(po + ",");
                }
                logStr = logContent.toString().substring(0, logContent.toString().length() - 1);
                content.append("，有问题的订单编号有：").append(logStr);
            } else {
                content.append("总数:0");
            }
        } catch (Exception e) {
            log.error("已取消变成已发货的订单,监控出现异常", e);
        }
        String contentStr = content.toString();
        log.info("{},orderID:{}", contentStr, logStr);
        return contentStr;
    }

    @Override
    public String queryOrderSixToTwo() {
        StringBuilder content = new StringBuilder("14、已取消变成已支付,");
        StringBuilder orderNoContent = new StringBuilder();
        String logStr = "";
        try {
            List<String> orderPOS = monitorNoticeMapper.queryOrderSixToTwo();
            if (CollectionUtil.isNotEmpty(orderPOS)) {
                content.append("总数:" + orderPOS.size());
                for (String po : orderPOS) {
                    orderNoContent.append(po + ",");
                }
                logStr = orderNoContent.toString().substring(0, orderNoContent.toString().length() - 1);
                content.append("，有问题的订单编号有：").append(logStr);
            } else {
                content.append("总数:0");
            }
        } catch (Exception e) {
            log.error("已取消变成已支付的订单,监控出现异常", e);
        }
        String contentStr = content.toString();
        log.info("{},orderID:{}", contentStr, logStr);
        return contentStr;
    }

    @Override
    public String queryOrderNoPackage() {
        StringBuilder content = new StringBuilder("15、订单状态正常，没有包裹,");
        List<String> allOrder = new ArrayList<>();
        int shippedCount = 0;
        int unShippedCount = 0;
        String logStr = "";
        try {
            List<Map<String, Object>> orderPOS = monitorNoticeMapper.queryOrderNoPackage();
            if (CollectionUtil.isNotEmpty(orderPOS)) {
                content.append("总数:" + orderPOS.size());
                for (Map<String, Object> orderPO : orderPOS) {
                    if ("4".equals(orderPO.get("orderStatus").toString())) {
                        shippedCount++;
                    } else if ("3".equals(orderPO.get("orderStatus").toString())) {
                        unShippedCount++;
                    }
                    allOrder.add(orderPO.get("orderNo").toString());
                }
                logStr = StringUtils.join(allOrder, ",");
                content.append("，有问题的订单总数为：" + orderPOS.size() + "，待发货没有包裹总数为:" + unShippedCount + "，已发货没有包裹总数为:" + shippedCount + "。");
            } else {
                content.append("总数:0，待发货没有包裹总数为:0，已发货没有包裹总数为:0。");
            }
        } catch (Exception e) {
            log.error("状态正常，但是没有包裹的订单,监控出现异常", e);
        }
        String contentStr = content.toString();
        log.info("{},orderID:{}", contentStr, logStr);
        return contentStr;
    }

    @Override
    public List<String> queryExecptionOrder() {
        List<String> strList = new ArrayList<>();
        List<ExceptionOrderPO> orderList = monitorNoticeMapper.queryExecptionOrder();
        int i = 1;
        int cnt1 = 0;//订单取消未退还积分
        int cnt2 = 0;//订单关闭未退还积分
        int cnt3 = 0;//未扣积分，订单关闭退还积分
        int cnt4 = 0;//未扣积分，订单取消退还积分
        int cnt5 = 0;//订单复核未扣减积分
        int cnt6 = 0;//订单发货未扣减积分
        int cnt7 = 0;//订单签收未扣减积分
        ArrayList<String> list = new ArrayList<>();
        for (ExceptionOrderPO exceptionOrderPO : orderList) {
            String openid = exceptionOrderPO.getLoginName();
            String orderNo = exceptionOrderPO.getOrderNo();
            int orderStatus = (Integer) exceptionOrderPO.getOrderStatus();
            Boolean xiaohao = false;
            Boolean fanhuan = false;
            int cntXh = 0;
            int cntFh = 0;
            List<IntergraDetailsPO> integralDetails = getIntegralDetails(openid); //通过openid查用户积分明细
            if (integralDetails != null) {
                for (IntergraDetailsPO integralDetail : integralDetails) {
                    String remark = integralDetail.getRemark();
                    if (remark.contains("|")) {
                        String[] split = remark.split("\\|");
                        if (split.length > 1) {
                            if (split[0].equals("订单消耗") && split[1].equals(orderNo)) {
                                //扣减的积分
                                xiaohao = true;
                                cntXh++;
                            }
                            if ("订单返还".equals(split[0]) && orderNo.equals(split[1])) {
                                //返回的积分的积分
                                fanhuan = true;
                                cntFh++;
                            }
                        }
                    }
                }
            }
            switch (orderStatus) {
                //订单已取消，
                case 6:
                    // 订单 有扣减 有返还
                    if (xiaohao && !fanhuan && cntXh > 0 && cntFh < cntXh) { // 订单取消 有扣减无返还
                        cnt4++;
                        list.add(orderNo);
                    } else if (!xiaohao && fanhuan && cntFh > 0) { //订单取消 未扣减有返还
                        cnt1++;
                        list.add(orderNo);
                    }
                    break;
                case 7://关闭
                    // 订单 有扣减 有返还
                    if (xiaohao && !fanhuan && cntXh > 0 && cntFh < cntXh) { // 订单关闭 有扣减无返还
                        cnt2++;
                        list.add(orderNo);
                    } else if (!xiaohao && fanhuan && cntFh > 0) { //订单关闭 未扣减有返还
                        cnt3++;
                        list.add(orderNo);
                    }
                    break;
                case 5://签收 有消耗 有返还
                    if (xiaohao && fanhuan && cntXh == cntFh) {
                        cnt7++;
                        list.add(orderNo);
                    }
                    break;
                case 4://发货 有消耗 有返还
                    if (xiaohao && fanhuan && cntXh == cntFh) {
                        cnt6++;
                        list.add(orderNo);
                    }
                    break;
                case 3://复核 有消耗 有返还
                    if (xiaohao && fanhuan && cntXh == cntFh) {
                        cnt5++;
                        list.add(orderNo);
                    }
                    break;
            }
        }
        log.info("异常订单为：{}", JSON.toJSONString(list));
        StringBuilder content = new StringBuilder();
        StringBuilder content1 = new StringBuilder();
        content.append("<h2 style='color:green;margin:0;padding:0;'>").append("16、[单订单]订单取消 未扣减有返还：").append(cnt1 + "</h2><br><p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:订单状态已付款，实际支付没有扣积分，取消后又给返还了积分</p><br/><br/><br/>");
        content.append("<h2 style='color:green;margin:0;padding:0;'>").append("17、[单订单]订单关闭 未扣减有返还：").append(cnt3 + "</h2><br><p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:订单状态已付款，实际支付没有扣积分，关闭后又给返还了积分</p><br/><br/><br/>");
        content.append("<h2 style='color:green;margin:0;padding:0;'>").append("18、[单订单]订单关闭 有扣减无返还：").append(cnt2 + "</h2><br><p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:订单支付时实际扣减了积分，但是关闭后没有给退还积分</p><br/><br/><br/>");
        content.append("<h2 style='color:green;margin:0;padding:0;'>").append("19、[单订单]订单取消 有扣减无返还：").append(cnt4 + "</h2><br><p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:订单支付时实际扣减了积分，但是取消后没有给退还积分</p><br/><br/><br/>");
        content1.append("<h2 style='color:green;margin:0;padding:0;'>").append("24、复核订单有消耗有返还：").append(cnt5 + "</h2><br><p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:默认复核后的订单不会给返还积分</p><br/><br/><br/>");
        content1.append("<h2 style='color:green;margin:0;padding:0;'>").append("25、发货订单有消耗有返还：").append(cnt6 + "</h2><br><p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:默认发货后的订单不会给返还积分</p><br/><br/><br/>");
        content1.append("<h2 style='color:green;margin:0;padding:0;'>").append("26、已签收订单有消耗有返还：").append(cnt7 + "</h2><br><p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:默认已签收的订单不会给返还积分</p><br/><br/><br/>");
        strList.add(content.toString());
        strList.add(content1.toString());
        return strList;
    }

    @Override
    public String queryOrderExpressUnSync() {
        StringBuilder content = new StringBuilder("27、电商修改快递单号未同步到星妈优选，");
        List<List<String>> ll = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -3);
        Date start = calendar.getTime();
        String startTime = df.format(start);
        calendar.add(Calendar.DATE, +1);
        Date today = calendar.getTime();
        String todayTime = df.format(today);
        List<ExpressCompanySH> newShExpressData;
        newShExpressData = getSHtodayExpressData(startTime, todayTime);
        int count = 0;
        if (newShExpressData.size() == 0) {
            return content.append("总数:0").toString();
        } else {
            log.info(startTime + "~~~" + todayTime + "的数据为" + newShExpressData.size());
            for (ExpressCompanySH expressCompanySH : newShExpressData) {
                count++;
                System.out.println(count + "/" + newShExpressData.size());
                String[] splitEX = expressCompanySH.getExpressCodes().split(",");
                List<String> stringList = new ArrayList<>();
                for (String s : splitEX) {
                    stringList.add(expressCompanySH.getThirdOrderCode() + "," + s);
                }
                String sql = "select a.id id,d.orderStatus status,d.orderno orderno,a.expressNum expressNum,c.goodsid goodsid,e.shanghai_sku shgoodsid,c.productName productName " +
                        " from cs_order_logistics a " +
                        " left join cs_order_logistics_item b on a.id =b.orderLogisticsId " +
                        " left join cs_order_item c on b.orderItemId=c.id " +
                        " left join cs_order d on c.orderId=d.id  " +
                        " left join cs_shanghai_sku e on c.goodsId=e.xmyx_sku " +
                        " where d.orderNo = '" + expressCompanySH.getThirdOrderCode() + "' and e.shanghai_sku in (" + expressCompanySH.getSkuIds() + ")";
                List<Map<String, Object>> test001s1 = jdbcTemplate.queryForList(sql);
                Map<String, Object> onemap = new HashMap<>();
                test001s1.stream().forEach(serialM -> {
                    onemap.put(expressCompanySH.getThirdOrderCode() + "," + serialM.get("expressNum"), 1);
                });
                Map<String, Object> twomap = new HashMap<>();
                for (String shex : stringList) {
                    Object o = onemap.get(shex);
                    if (o == null) {
                        twomap.put(shex.split(",")[0], 1);
                    }
                }

                for (String key : twomap.keySet()) {
                    String sql2 = "select a.id id,d.orderStatus status,d.orderno orderno,a.expressNum expressNum,c.goodsid goodsid,e.shanghai_sku shgoodsid,c.productName productName " +
                            " from cs_order_logistics a " +
                            " left join cs_order_logistics_item b on a.id =b.orderLogisticsId " +
                            " left join cs_order_item c on b.orderItemId=c.id " +
                            " left join cs_order d on c.orderId=d.id  " +
                            " left join cs_shanghai_sku e on c.goodsId=e.xmyx_sku " +
                            " where d.orderNo = '" + key + "' AND a.expressNum LIKE ''";
                    List<Map<String, Object>> test002s2 = jdbcTemplate.queryForList(sql2);
                    test002s2.stream().forEach(serialM -> {
                        if (StringUtils.isEmpty(String.valueOf(serialM.get("expressNum")))) {
                            List<String> list = new ArrayList<>();
                            list.add(String.valueOf(serialM.get("id")));
                            list.add(String.valueOf(serialM.get("orderno")));
                            list.add(String.valueOf(serialM.get("status")));
                            list.add(String.valueOf(serialM.get("goodsid")));
                            list.add(String.valueOf(serialM.get("shgoodsid")));
                            list.add(String.valueOf(serialM.get("productName")));
                            String[] splitgoods = expressCompanySH.getSkuIds().split(",");
                            for (String a : splitgoods) {
                                String sql3 = "select a.id id,d.orderStatus status,d.orderno orderno,a.expressNum expressNum,c.goodsid goodsid,e.shanghai_sku shgoodsid,c.productName productName " +
                                        " from cs_order_logistics a " +
                                        " left join cs_order_logistics_item b on a.id =b.orderLogisticsId " +
                                        " left join cs_order_item c on b.orderItemId=c.id " +
                                        " left join cs_order d on c.orderId=d.id  " +
                                        " left join cs_shanghai_sku e on c.goodsId=e.xmyx_sku " +
                                        " where d.orderNo = '" + expressCompanySH.getThirdOrderCode() + "' and e.shanghai_sku = " + a;
                                List<Map<String, Object>> test002 = jdbcTemplate.queryForList(sql3);
                                if (test002.size() == 0) {
                                    String sqlnew = "select b.shanghai_sku shskunew,a.id goodsidnew,c.materialName materialName from cs_goods a left join cs_shanghai_sku b on a.id=b.xmyx_sku left join cs_goods_material c on a.productStyleId=c.styleId where b.shanghai_sku =" + a;
                                    List<Map<String, Object>> test001 = jdbcTemplate.queryForList(sqlnew);
                                    test001.stream().forEach(serialMnew -> {
                                        List<String> listnew = new ArrayList<>();
                                        listnew.addAll(list);
                                        listnew.add(String.valueOf(serialMnew.get("shskunew")));
                                        listnew.add(String.valueOf(serialMnew.get("goodsidnew")));
                                        listnew.add(String.valueOf(serialMnew.get("materialName")));
                                        listnew.add(String.valueOf(serialM.get("expressNum")));
                                        listnew.add(expressCompanySH.getExpressCodes());
                                        ll.add(listnew);
                                    });
                                }
                            }
                        }
                    });
                }
            }
        }
        return content.append("总数:" + ll.size()).toString();
    }

    @Override
    public String queryFatherSonList() {
        //查询前十二小时所以父子单
        List<OrderPO> orderPOList = monitorNoticeMapper.getAllParentSonOrder();
        //订单已取消\已关闭积分未退还
        List<Integer> cancelRefundPoints = new ArrayList<>();
        //已发货\已签收\已付款退还积分
        List<Integer> signOrderRefundPoints = new ArrayList<>();
        //已发货\已签收\已付款未扣积分
        List<Integer> signOrderUnSubPoints = new ArrayList<>();
        //子订单多扣积分
        List<Integer> childrenManySubPoints = new ArrayList<>();
        if (orderPOList != null && orderPOList.size() > 0) {
            //查出所有父单
            //父子订单,父订单支付积分包含子订单
            Map<String, Integer> partentOrderIds = new HashMap<>();
            int orderId;
            //订单金额
            int goodsPointAmount = 0;
            for (OrderPO paymentOrder : orderPOList) {
                goodsPointAmount = paymentOrder.getGoodsPointAmount();
                if (paymentOrder.getHasChildren() || paymentOrder.getParentOrderId() > 0) {
                    if (paymentOrder.getHasChildren()) {
                        orderId = paymentOrder.getId();
                    } else {
                        orderId = paymentOrder.getParentOrderId();
                    }
                    int pointValue = getOrderPointSubtractDetail(orderId, SUB_POINT_SUFFIX);
                    if (pointValue > goodsPointAmount) {
                        partentOrderIds.put(String.valueOf(orderId), pointValue);
                    }
                }
            }
            Set<String> keySet = partentOrderIds.keySet();
            for (OrderPO paymentOrder : orderPOList) {
                int id = paymentOrder.getId();
                if (OrderState.CANCELLED == paymentOrder.getOrderStatus() || OrderState.CLOSED == paymentOrder.getOrderStatus()) {
                    if (paymentOrder.getHasChildren()) {
                        //包含则证明已扣减积分
                        if (keySet.contains(String.valueOf(id))) {
                            int sendPointValue = getOrderPointSubtractDetail(paymentOrder.getRecordId(), SEND_POINT_SUFFIX);
                            if (sendPointValue == 0 && paymentOrder.getGoodsPointAmount() != 0) {
                                cancelRefundPoints.add(id);
                            }
                        } else {
                            int pointValue = getOrderPointSubtractDetail(id, SUB_POINT_SUFFIX);
                            if (pointValue > 0) {
                                int sendValue = getOrderPointSubtractDetail(paymentOrder.getRecordId(), SEND_POINT_SUFFIX);
                                if (sendValue == 0 && paymentOrder.getGoodsPointAmount() != 0) {
                                    cancelRefundPoints.add(id);
                                }
                            }
                        }
                    } else {
                        int pointValue = getOrderPointSubtractDetail(id, SUB_POINT_SUFFIX);
                        if (keySet.contains(String.valueOf(paymentOrder.getParentOrderId())) || pointValue > 0) {
                            int sendValue = getOrderPointSubtractDetail(paymentOrder.getRecordId(), SEND_POINT_SUFFIX);
                            if (sendValue == 0 && paymentOrder.getGoodsPointAmount() != 0) {
                                cancelRefundPoints.add(id);
                            }
                        }
                    }
                } else if (paymentOrder.getOrderStatus() != OrderState.CREATED && paymentOrder.getOrderStatus() != OrderState.CANCELLED
                        && paymentOrder.getOrderStatus() != OrderState.CLOSED) {
                    int sendValue = getOrderPointSubtractDetail(paymentOrder.getRecordId(), SEND_POINT_SUFFIX);
                    if (sendValue != 0) {
                        signOrderRefundPoints.add(id);
                    }
                    int pointValue = getOrderPointSubtractDetail(id, SUB_POINT_SUFFIX);
                    if (pointValue == 0 && !keySet.contains(String.valueOf(paymentOrder.getParentOrderId())) && paymentOrder.getGoodsPointAmount() != 0) {
                        signOrderUnSubPoints.add(id);
                    }

                    if (keySet.contains(String.valueOf(paymentOrder.getParentOrderId())) && pointValue > 0) {
                        childrenManySubPoints.add(id);
                    }
                }
            }
            log.info("订单已取消、已关闭积分未退还订单ID:{}", StringUtils.join(cancelRefundPoints, ","));
            log.info("已发货、已签收、已付款退还积分订单ID:{}", StringUtils.join(signOrderRefundPoints, ","));
            log.info("已发货、已签收、已付款未扣积分订单ID:{}", StringUtils.join(signOrderUnSubPoints, ","));
            log.info("子订单多扣积分订单ID:{}", StringUtils.join(childrenManySubPoints, ","));

        } else {
            log.info("暂无父子单");
        }
        StringBuilder content = new StringBuilder();
        content.append("<h2 style='color:green;margin:0;padding:0;'>").append("20、[父子单]订单已取消、已关闭积分未退还订单总数：").append(cancelRefundPoints.size() + "</h2><br><p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:订单已经取消或关闭应该给用户退还积分,但是没有退</p><br/><br/><br/>");
        content.append("<h2 style='color:green;margin:0;padding:0;'>").append("21、[父子单]已发货、已签收、已付款退还积分订单总数:").append(signOrderRefundPoints.size() + "</h2><br><p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:订单已发货、已签收、已付款不应该给用户退换积分，但是退了</p><br/><br/><br/>");
        content.append("<h2 style='color:green;margin:0;padding:0;'>").append("22、[父子单]已发货、已签收、已付款未扣积分订单总数:").append(signOrderUnSubPoints.size() + "</h2><br><p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:订单已发货、已签收、已付款应该扣用户积分，但是没有扣</p><br/><br/><br/>");
        content.append("<h2 style='color:green;margin:0;padding:0;'>").append("23、[父子单]子订单多扣积分订单总数:").append(childrenManySubPoints.size() + "</h2><br><p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:父订单扣减积分已经包含子订单积分，子订单不应该再支付，但是子订单又单独支付了一次</p><br/><br/><br/>");
        return content.toString();
    }

    @Override
    public String queryErrorGoodsStock() {
        StringBuffer buffer = new StringBuffer("28、总库存小于0的货品总数为：");
        String sql = "SELECT c.`id` AS goodsId,c.`stock` FROM cs_goods c WHERE c.`stock`<0 ";
        String sql1 = "SELECT b.`goodsId`,a.`balance` FROM `cs_time_limit_goods_stock` a LEFT JOIN `cs_time_limit_goods` b ON a.`id`=b.`id` WHERE a.`balance`<0 ";
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        List<Map<String, Object>> mapList1 = jdbcTemplate.queryForList(sql1);
        if (mapList != null) {
            mapList.stream().forEach(map -> {
                log.info("总库存小于0的货品id:{},stock:{}", map.get("goodsId"), map.get("stock"));
            });
            buffer.append(mapList.size() + "，");
        } else {
            buffer.append("0，");
        }
        if (mapList1 != null) {
            mapList1.stream().forEach(map -> {
                log.info("秒杀可卖数小于0的货品id:{},stock:{}", map.get("goodsId"), map.get("balance"));
            });
            buffer.append("秒杀可卖数小于0的货品总数为：" + mapList1.size());
        } else {
            buffer.append("秒杀可卖数小于0的货品总数为：0");
        }
        return buffer.toString();
    }

    @Override
    public String queryErrorQuartz() {
        StringBuffer buffer = new StringBuffer("29、异常状态定时任务总数为:");
        List<String> errorQuartzList = monitorNoticeMapper.queryErrorQuartz();
        if (CollectionUtil.isNotEmpty(errorQuartzList)) {
            String str = StringUtils.join(errorQuartzList, ",");
            buffer.append(errorQuartzList.size() + "，jobName：" + str + " ");
            //执行定时任务补偿机制
            String sql = "UPDATE qrtz_triggers SET `NEXT_FIRE_TIME` = UNIX_TIMESTAMP(NOW()) * 1000 + 600000, `TRIGGER_STATE` = 'WAITING' WHERE `JOB_GROUP` != 'AxonFramework-Events' AND `TRIGGER_STATE` != 'COMPLETE' AND `NEXT_FIRE_TIME` < UNIX_TIMESTAMP(NOW()) * 1000 - 600000";
            try {
                int successCount = jdbcTemplate.update(sql);
                buffer.append("**********定时任务补偿修改状态条数 " + successCount);
            } catch (DataAccessException e) {
                buffer.append("**********定时任务补偿异常。");
            }
        } else {
            buffer.append("0。");
        }
        return buffer.toString();
    }

    @Override
    public String queryUnBindCoupon() {
        StringBuffer sbf = new StringBuffer("30、抽中的券未绑定成功的用户总数为:");
        Set<String> set = new HashSet<>();
        //这个时间段所有 中优惠券信息 总数
        String sql = "SELECT tp.memberId AS 'memberId', tp.couponId AS 'couponId'  FROM tb_prize tp  WHERE tp.createTime BETWEEN DATE_SUB( DATE_FORMAT( NOW( ), '%Y-%m-%d 23:59:59' ), INTERVAL 48 HOUR ) AND DATE_SUB( DATE_FORMAT( NOW( ), '%Y-%m-%d 23:59:59' ), INTERVAL 24 HOUR ) AND tp.prizeType = 2 AND tp.deleted != 1";
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> list : mapList) {
            String count1 = "SELECT tp.id FROM tb_prize tp  WHERE tp.createTime BETWEEN DATE_SUB( DATE_FORMAT( NOW( ), '%Y-%m-%d 23:59:59' ), INTERVAL 48 HOUR ) AND DATE_SUB( DATE_FORMAT( NOW( ), '%Y-%m-%d 23:59:59' ), INTERVAL 24 HOUR ) and tp.memberId = " + list.get("memberId") + " and tp.couponId =" + list.get("couponId") + " AND tp.prizeType = 2 AND tp.deleted != 1 ";
            String count2 = "SELECT id FROM cs_member_coupon  WHERE memberId  = " + list.get("memberId") + " and promotionId =(SELECT promotionId FROM cs_coupon_definition WHERE id  =" + list.get("couponId") + ")";
            if (jdbcTemplate.queryForList(count2).size() >= jdbcTemplate.queryForList(count1).size()) {
                continue;
            } else {
                set.add(list.get("memberID").toString() + "|" + list.get("couponId").toString());
            }
        }
        sbf.append(set.size());
        log.info("抽中的券未绑定成功的用户ID:{}", JSON.toJSONString(set));
        return sbf.toString();
    }


    private int getOrderPointSubtractDetail(int orderId, String suffix) {
        JSONObject json = new JSONObject();
        json.put("triggerId", orderId + suffix);
        String subJson = "{\"triggerId\":\"" + orderId + suffix + "\"}";
        int subPoint = 0;
        try {
            String devUrl = crmUrl + "/member/point/xmh/getRecordByTriggerId";
            Map<String, String> heads = getSign();
            String responseStr = HttpRequest.post(devUrl).header(Header.CONTENT_TYPE, "application/json")
                    .header("timestamp", heads.get("timestamp")).header("sign", heads.get("sign")).header("appkey", heads.get("appkey"))
                    .body(subJson).execute().body();
            log.info("幂等性ID：{}，result:{}", json.getString("triggerId"), responseStr);
            JSONObject jsonObject = JSONObject.parseObject(responseStr);
            int code = jsonObject.getIntValue("code");
            if (SUCCESS_CODE == code) {
                JSONArray jsonArray = jsonObject.getJSONArray("jsondata");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                subPoint = jsonObject1.getIntValue("point");
            }
        } catch (Exception e) {
            log.error("查询订单积分详情出错!triggerId:{}, e:{}", json, e);
        }
        return subPoint;

    }

    /**
     * 通过openid查用户积分明细
     *
     * @param openid
     * @return
     */
    public List<IntergraDetailsPO> getIntegralDetails(String openid) {
        String url = crmUrl + "/member/point/xmh/record";
        Map<String, String> heads = getSign();
        JSONObject json = new JSONObject();
        String contentType = "application/json";
        json.put("openId", openid);
        json.put("page", 1);
        json.put("pageSize", 1000);
        String result = HttpRequest.post(url).header(Header.CONTENT_TYPE, contentType)
                .header("timestamp", heads.get("timestamp")).header("sign", heads.get("sign")).header("appkey", heads.get("appkey"))
                .body(JSONObject.toJSONString(json)).execute().body();
        if (StringUtils.isNotBlank(result)) {
            JSONObject resultdata = JSONObject.parseObject(result);
            if (resultdata != null) {
                try {
                    String jsondata = resultdata.getString("jsondata");
                    JSONObject jsonData = JSONObject.parseObject(jsondata);
                    List<IntergraDetailsPO> list = JSONObject.parseArray(jsonData.getString("data"), IntergraDetailsPO.class);
                    return list;
                } catch (Exception e) {
                    log.error("对象转换异常{}", e);
                }
            }
        }
        return null;
    }

    private List<ExpressCompanySH> getSHtodayExpressData(String startTime, String endTime) {
        String responseBodyAsString = "";
        String url = "http://wmsapi.feihe.com/api/ERPServices/GetStageMothorExpressCode";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("StartTime", startTime);
        jsonObject.put("EndTime", endTime);
        try {
            responseBodyAsString = sendPost(url, jsonObject.toJSONString());
            if (StringUtils.isNotEmpty(responseBodyAsString)) {
                JSONObject dataObj = JSONObject.parseObject(responseBodyAsString);
                if (dataObj != null && StringUtils.isNotEmpty(dataObj.getString("Code")) && "2000".equals(dataObj.getString("Code"))) {
                    List<ExpressCompanySH> jsondata = setShanghaiData(dataObj.getJSONArray("Data"));
                    return jsondata;
                } else {
                    /**
                     * 					2000	查询成功,其余为失败
                     * 					1001	接口验证失败
                     * 					1002	非法的JSON格式
                     * 					1003	需传入加密验证码
                     */
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String sendPost(String url, String jsonData) {
        try {
            String partnerId = "TB74LCB572WS6W09CSL6YP41QKLO5D9N";
            String ClientId = "149542247";
            // 生成JsonStr
            String JsonStr = "";
            JsonStr = URLEncoder.encode(jsonData, "UTF-8");
//			log.info("JsonStr = " + JsonStr);
            // 数据MD5、base64加密
            String VerifyCode = jsonData + partnerId;
            VerifyCode = ERPUtil.md5EncryptBase64(VerifyCode);
            VerifyCode = URLEncoder.encode(VerifyCode, "utf-8");
//			log.info("VerifyCode = " + VerifyCode);
            // 数据URL 编码
            PostMethod postMethod = null;
            postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            //参数设置，需要注意的就是里边不能传NULL，要传空字符串
            NameValuePair[] data = {
                    new NameValuePair("JsonStr", JsonStr),
                    new NameValuePair("VerifyCode", VerifyCode),
                    new NameValuePair("ClientId", ClientId)
            };
            postMethod.setRequestBody(data);
            org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
            int response = httpClient.executeMethod(postMethod); // 执行POST方法
            log.info("response = " + response);
            String result = postMethod.getResponseBodyAsString();
//		    log.info("result = " + result);
            return result;
        } catch (Exception e) {
            log.error("sendPost" + e.getMessage());
            return null;
        }
    }

    private List<ExpressCompanySH> setShanghaiData(JSONArray jsonArray) {
        ArrayList<ExpressCompanySH> expressCompanySHArrayList = new ArrayList<>();
        if (jsonArray != null && jsonArray.size() > 0) {
            for (int index = 0; index < jsonArray.size(); index++) {
                JSONObject shanghaiData = (JSONObject) jsonArray.get(index);
                if (shanghaiData != null) {
                    expressCompanySHArrayList.add(setsetShanghaiDataDTO(shanghaiData));
                }
            }
        }
        return expressCompanySHArrayList;
    }

    private ExpressCompanySH setsetShanghaiDataDTO(JSONObject shanghaiData) {
        ExpressCompanySH expressCompanySH = new ExpressCompanySH();
        if (shanghaiData == null) {
            return null;
        }
        expressCompanySH.setThirdOrderCode(shanghaiData.getString("ThirdOrderCode"));
        expressCompanySH.setConsignCode(shanghaiData.getString("ConsignCode"));
        expressCompanySH.setIsOut(shanghaiData.getInteger("IsOut"));
        expressCompanySH.setSendTime(shanghaiData.getString("SendTime"));
        expressCompanySH.setTransferCode(shanghaiData.getString("TransferCode"));
        expressCompanySH.setTransferName(shanghaiData.getString("TransferName"));
        expressCompanySH.setSkuIds(shanghaiData.getString("SkuIds"));
        expressCompanySH.setExpressCodes(shanghaiData.getString("ExpressCodes"));
        return expressCompanySH;
    }

    public static Map<String, String> getSign() {

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String sign = "";
        String sbstr = getAppSecret() + "timestamp" + timestamp + getAppSecret();
        sign = DigestUtils.md5Hex(sbstr).toUpperCase();
        String signat = sign + "=" + timestamp;
        Map<String, String> paramMap = new HashMap<String, String>();//请求参数
        paramMap.put("appkey", "10001");//必填
        String[] split = signat.split("=");
        paramMap.put("timestamp", split[1]);//必填
        paramMap.put("sign", split[0]);//最后进行添加sign签名  必填
        paramMap.put("Content-Type", "application/json");
        return paramMap;
    }

    public static String getAppSecret() {
        return "7d61a8b8e05d4f864fa93f363b9ff09c";
    }

    @Override
    public List<String> getEmailAddress(int emailStatus) {
        return monitorNoticeMapper.getEmailAddress(emailStatus);
    }

    @Override
    public String queryNoExpressNumOrder() {
        //创建Excel表格
        List<Map<String, String>> mapList = monitorNoticeMapper.queryNoExpressNumOrder();
        String str;
        if (CollectionUtil.isNotEmpty(mapList)) {
            //生成Excle文件
            String excel = excelUrl + "NoExpressNumOrder" + DateUtil.format(new Date(), "yyyy-MM-dd") + ".xlsx";
            ExcelWriter writer = ExcelUtil.getWriter(excel);
            writer.addHeaderAlias("code", "店铺编码");
            writer.addHeaderAlias("name", "店铺名称");
            writer.addHeaderAlias("orderNo", "订单号");
            writer.addHeaderAlias("orderCreateTime", "下单时间");
            writer.addHeaderAlias("operateTime", "发货时间");
            writer.write(mapList, true);
            writer.close();
            str = "31、订单状态为已发货但是没有快递单号的订单总数为:" + mapList.size() + "。详情见附件";
        } else {
            str = "31、订单状态为已发货但是没有快递单号的订单总数为:0。";
        }
        return str;
    }
}
