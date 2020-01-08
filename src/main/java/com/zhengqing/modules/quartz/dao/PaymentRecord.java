package com.zhengqing.modules.quartz.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付记录
 * 
 * @author XYC
 */
public class PaymentRecord implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 支付记录标识
	 */
	private int id;

	/**
	 * 合并支付流水号
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
	private Date paidTime;

	public PaymentRecord() {
	}

	public PaymentRecord(String mergePaymentNo, int paymentModeType,
                         int paymentModeId, String payModeCode, BigDecimal amount,
                         int pointPayValue, String couponIds, Date paidTime) {
		this.mergePaymentNo = mergePaymentNo;
		this.paymentModeType = paymentModeType;
		this.paymentModeId = paymentModeId;
		this.payModeCode = payModeCode;
		this.amount = amount;
		this.pointPayValue = pointPayValue;
		this.couponIds = couponIds;
		this.paidTime = paidTime;
	}

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

	public Date getPaidTime() {
		return paidTime;
	}

	public void setPaidTime(Date paidTime) {
		this.paidTime = paidTime;
	}

}
