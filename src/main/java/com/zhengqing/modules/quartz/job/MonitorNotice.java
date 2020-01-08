package com.zhengqing.modules.quartz.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.mail.MailUtil;
import com.zhengqing.modules.quartz.annotion.QuartzJob;
import com.zhengqing.modules.quartz.service.MonitorNoticeService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

@QuartzJob(name = "monitorNotice", cronExp = "0 23 7/6 * * ?", description = "数据异常预警定时任务", group = "MONITOR_GROUP")
public class MonitorNotice implements Job {
    private Logger logger = LoggerFactory.getLogger(MonitorNotice.class);
    @Autowired
    private MonitorNoticeService monitorNoticeService;
    @Value("${emailTitle}")
    private String emailTitle;
    @Value("${excelUrl}")
    private String excelUrl;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("预警定时任务开始：" + DateUtil.now());
        String excel = excelUrl + "NoExpressNumOrder" + DateUtil.format(new Date(), "yyyy-MM-dd") + ".xlsx";
        long start = System.currentTimeMillis();
        try {
            //邮箱内容
            StringBuffer content = new StringBuffer();
            //1、内部会员错误支付金额监控
            String content1 = monitorNoticeService.innerMemberPayAmoutAlarm();
            //2、没有自动过审监控
            String content2 = monitorNoticeService.autoReviewAlarm();
            //3、多人领取同一张券的数据
            String content3 = monitorNoticeService.manyMemberReceiveOneCoupon();
            //4、一券多订单
            String content4 = monitorNoticeService.couponUsedByManyOrder();
            //5、一人多鹤礼卷监控
            String content5 = monitorNoticeService.multipleMemberCoupon();
            //6、优惠券已使用优惠券状态未改变
            String content6 = monitorNoticeService.getCouponStatusWrongInfo();
            //7、订单状态为已付款但支付表为未支付
            String content7 = monitorNoticeService.getOrderStatusWrongInfo();
            //8、订单已经取消，但是自动过审了
            String content8 = monitorNoticeService.queryErrorOrder();
            //9.超过20天没有自动签收的订单
            String content9 = monitorNoticeService.getNotAutomaticOrderReceive();
            //10.订单状态为已复核(3,未发货)却有运单号
            String content10 = monitorNoticeService.getOrderStatusByNotYetShipped();
            //11、被自动过审定时任务过审的订单总数
            String content11 = monitorNoticeService.getAutoReviewOrderQuantity();
            //12、未复审的订单变成已发货
            String content12 = monitorNoticeService.queryOrderTwoTFour();
            //13、已取消变成已发货
            String content13 = monitorNoticeService.queryOrderSixToFour();
            //14、已取消变成已支付
            String content14 = monitorNoticeService.queryOrderSixToTwo();
            //15、订单状态正常，没有包裹
            String content15 = monitorNoticeService.queryOrderNoPackage();
            /*
             *  16、查询异常订单——梁立平
             * 1.订单取消未退还积分
             * 2.订单关闭未退还积分
             * 3.未扣积分，订单关闭退还积分
             * 4.未扣积分，订单取消退还积分
             * 5.订单复核未扣减积分
             * 6.订单发货未扣减积分
             * 7.订单签收未扣减积分
             * @return
             */
            List<String> contentList = monitorNoticeService.queryExecptionOrder();
            //17、查询电商修改的快递单号没有同步到星妈优选的数目——周树誉
            String content23 = monitorNoticeService.queryOrderExpressUnSync();
            //查询父子单异常订单
            String content24 = monitorNoticeService.queryFatherSonList();
            //库存可卖数变成负数
            String content29 = monitorNoticeService.queryErrorGoodsStock();
            //异常状态定时任务
            String content30 = monitorNoticeService.queryErrorQuartz();
            String content31 = monitorNoticeService.queryUnBindCoupon();
            String content32=monitorNoticeService.queryNoExpressNumOrder();
            content.append("<!DOCTYPE>" + "<div bgcolor='#f1fcfa' style='border:1px solid #d9f4ee; font-size:14px; color:#005aa0;padding-left:1px;padding-top:5px;padding-bottom:5px;'><span style='font-weight:bold;'>温馨提醒：</span>"
                    + "<div style='width:950px;font-family:arial;'>异常监控信息如下：<br/>");
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content1 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:应付与实付不一致</p><br/><br/><br/>");
            content.append("<h2 style='color:green;padding:0; margin:0;'>" + content2 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:订单审核的时候没有自动过审的点单</p><br/><br/><br/>");
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content3 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:一券多绑</p><br/><br/><br/>");
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content4 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:一券多订单</p><br/><br/><br/>");
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content5 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:一人多鹤礼券</p><br/><br/><br/>");
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content6 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:优惠券可以重复多次使用</p><br/><br/><br/>");
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content7 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:同一订单,订单状态（订单表cs_order）和支付状态（支付表cs_payment_record）不一致</p><br/><br/><br/>");
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content8 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:订单日志中,已取消,但是还是自动过审</p><br/><br/><br/>");
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content9 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:默认一个订单下的全部包裹发货,则超20天自动签收</p><br/><br/><br/>");
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content10 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:有快递单号的订单默认是已经发货了的</p><br/><br/><br/>");
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content11 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:过审补偿任务过审的订单数量</p><br/><br/><br/>");
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content12 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:默认先复核，然后再发货</p><br/><br/><br/>");
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content13 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:默认取消后订单状态终止</p><br/><br/><br/>");
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content14 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:默认取消后订单状态终止</p><br/><br/><br/>");
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content15 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:订单状态(cs_order表中orderStatus、checkStatus)正常,但是没有拆包裹</p><br/><br/><br/>");
            content.append(contentList.get(0));
            content.append(content24);
            content.append(contentList.get(1));
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content23 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:星妈优选传给上海电商的SKUId,上海电商返回时没有原样返回,会导致订单发货了但是没有快递单号</p><br/><br/><br/>");
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content29 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:库存可卖数变成负数</p><br/><br/><br/>");
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content30 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:未正常执行的定时任务</p><br/><br/><br/>");
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content31 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:抽奖抽中的优惠券未成功绑定到用户</p><br/><br/><br/>");
            content.append("<h2 style='color:green;margin:0;padding:0;'>" + content32 + "</h2><br/>");
            content.append("<p style='text-indent: 2em;margin:0;padding:0;'>&nbsp;&nbsp;&nbsp;&nbsp;Tips:订单状态为已发货但是超过36小时还没有快递单号的订单</p><br/><br/><br/>");
            content.append("本邮件由系统自动发出，请勿回复。</div></div>");
            logger.info("预警定时任务邮件内容为:" + content);
            MailUtil.send(monitorNoticeService.getEmailAddress(), emailTitle, content.toString(), true, FileUtil.file(excel));
        } catch (Exception e) {
            logger.error("定时预警发生异常", e);
        }
        long end = System.currentTimeMillis();
        logger.info("预警定时任务结束:" + DateUtil.now(), "耗时:" + (end - start) + "ms");
    }
}
