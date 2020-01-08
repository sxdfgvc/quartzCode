package com.zhengqing.modules.quartz.dao;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付记录
 * 
 * @author XYC
 */
public class PaymentRecordDTO implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 支付记录标识
	 */
	private int id;

	/**
	 * 合并支付时多条共用
	 */
	private String mergePaymentNo;

	/**
	 * 支付类型
	 */
	private int paymentModeType;

	/**
	 * 支付方式标识
	 */
	private int paymentModeId;

	/**
	 * 支付方式名称
	 */
	private String paymentModeName;

	/**
	 * 支付方式编码
	 */
	private String payModeCode;

	/**
	 * 在线支付回传的流水编码，合并支付多条共用
	 */
	private String tradeNo;

	/**
	 * 支付金额
	 */
	private BigDecimal amount;

	/**
	 * 积分支付值
	 */
	private int pointPayValue;

	/**
	 * 优惠券标识
	 */
	private String couponIds;

	/**
	 * 支付时间
	 */
	private String paidTimeStr;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMergePaymentNo() {
		return mergePaymentNo;
	}

	public void setMergePaymentNo(String mergePaymentNo) {
		this.mergePaymentNo = mergePaymentNo;
	}

	public int getPaymentModeType() {
		return paymentModeType;
	}

	public void setPaymentModeType(int paymentModeType) {
		this.paymentModeType = paymentModeType;
	}

	public int getPaymentModeId() {
		return paymentModeId;
	}

	public void setPaymentModeId(int paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	public String getPaymentModeName() {
		return paymentModeName;
	}

	public void setPaymentModeName(String paymentModeName) {
		this.paymentModeName = paymentModeName;
	}

	public String getPayModeCode() {
		return payModeCode;
	}

	public void setPayModeCode(String payModeCode) {
		this.payModeCode = payModeCode;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getPointPayValue() {
		return pointPayValue;
	}

	public void setPointPayValue(int pointPayValue) {
		this.pointPayValue = pointPayValue;
	}

	public String getCouponIds() {
		return couponIds;
	}

	public void setCouponIds(String couponIds) {
		this.couponIds = couponIds;
	}

	public String getPaidTimeStr() {
		return paidTimeStr;
	}

	public void setPaidTimeStr(String paidTimeStr) {
		this.paidTimeStr = paidTimeStr;
	}

}
