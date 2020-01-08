package com.zhengqing.modules.quartz.dao;

import java.io.Serializable;


public class ExpressCompanySH implements Serializable {

    private static final long serialVersionUID = 5500964831380994168L;
    /**
     * 星妈订单号
     */
    private String ThirdOrderCode;
    /**
     * 包裹号
     */
    private String ConsignCode;
    /**
     * 是否出库
     */
    private int IsOut;
    /**
     * 发货时间
     */
    private String SendTime;
    /**
     * 快递公司编号
     */
    private String TransferCode;
    /**
     * 快递公司名称
     */
    private String TransferName;
    /**
     * 快递单号,逗号串联
     */
    private String ExpressCodes;
    /**
     * SkuId，逗号串联
     */
    private String SkuIds;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getThirdOrderCode() {
        return ThirdOrderCode;
    }

    public void setThirdOrderCode(String thirdOrderCode) {
        ThirdOrderCode = thirdOrderCode;
    }

    public String getConsignCode() {
        return ConsignCode;
    }

    public void setConsignCode(String consignCode) {
        ConsignCode = consignCode;
    }

    public int getIsOut() {
        return IsOut;
    }

    public void setIsOut(int isOut) {
        IsOut = isOut;
    }

    public String getSendTime() {
        return SendTime;
    }

    public void setSendTime(String sendTime) {
        SendTime = sendTime;
    }

    public String getTransferCode() {
        return TransferCode;
    }

    public void setTransferCode(String transferCode) {
        TransferCode = transferCode;
    }

    public String getTransferName() {
        return TransferName;
    }

    public void setTransferName(String transferName) {
        TransferName = transferName;
    }

    public String getExpressCodes() {
        return ExpressCodes;
    }

    public void setExpressCodes(String expressCodes) {
        ExpressCodes = expressCodes;
    }

    public String getSkuIds() {
        return SkuIds;
    }

    public void setSkuIds(String skuIds) {
        SkuIds = skuIds;
    }

    @Override
    public String toString() {
        return "ExpressCompanySH{" +
                "ThirdOrderCode='" + ThirdOrderCode + '\'' +
                ", ConsignCode='" + ConsignCode + '\'' +
                ", IsOut=" + IsOut +
                ", SendTime='" + SendTime + '\'' +
                ", TransferCode='" + TransferCode + '\'' +
                ", TransferName='" + TransferName + '\'' +
                ", ExpressCodes='" + ExpressCodes + '\'' +
                ", SkuIds='" + SkuIds + '\'' +
                '}';
    }

}