package com.zhengqing.modules.quartz.dao;

/**
 * Description: IntergraDetails <br>
 *
 * @author Liang lp
 * Date: 2019/11/20 10:57 <br>
 */
public class IntergraDetailsPO {


   private String orderId;
   private String changeMode;
   private String remark;
   private String record_type;
   private String record_source;
   private String effectiveDate;
   private String point;
   private String memberId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getChangeMode() {
        return changeMode;
    }

    public void setChangeMode(String changeMode) {
        this.changeMode = changeMode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRecord_type() {
        return record_type;
    }

    public void setRecord_type(String record_type) {
        this.record_type = record_type;
    }

    public String getRecord_source() {
        return record_source;
    }

    public void setRecord_source(String record_source) {
        this.record_source = record_source;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
