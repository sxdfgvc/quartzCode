package com.zhengqing.modules.quartz.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * <p>描述内容</br>订单信息PO</p>
 * @author zhangxXueyong Email:zhangxueyong@co-mall.com
 * @company 北京科码先锋软件技术有限公司@版权所有
 * @version  3.1.0
 * @since 2016年3月7日下午3:04:42
 */
public class OrderPO implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 订单标识
	 */
	private int id;

	/**
	 * 订单编号,规则为3位分站编号+8位日期+6位随机数字
	 */
	private String orderNo;
	
	/**
	 * 是否包含子订单
	 */
	private Boolean hasChildren;
	/**
	 * 父订单标识
	 */
	private int parentOrderId;
	/**
	 * 分站标识
	 */
	private int subsiteId;
	/**
	 * 订单来源,0=手机客户端,1=电脑
	 */
	private int orderOrigin;
	/**
	 * 下单ip
	 */
	private String originIP;
	/**
	 * 下单人
	 */
	private int memberId;
	/**
	 * 是否需要发票，0=不需要，1=需要
	 */
	private int invoiceStatus;
	/**
	 * 会员备注
	 */
	private String remark;
	/**
	 * 订单总额
	 */
	private BigDecimal goodsAmount;

	/**
	 * 积分价总额
	 */
	private int goodsPointAmount;

	/**
	 * 货品优惠总额
	 */
	private BigDecimal goodsDiscountAmount;
	/**
	 * 订单重量
	 */
	private BigDecimal orderWeight;
	/**
	 * 运费
	 */
	private BigDecimal freightFee;
	/**
	 * 运费优惠金额
	 */
	private BigDecimal freightFeeDiscount;
	/**
	 * 物流服务费
	 */
	private BigDecimal freightServiceFee;
	/**
	 * 应付运费
	 */
	private BigDecimal payableFreight;
	/**
	 * 手工调控金额
	 */
	private BigDecimal ajustAmount;
	/**
	 * 系统抹零,抹掉分以后的金额,保留小数点六位
	 */
	private BigDecimal systemAjustAmount;
	/**
	 * 订单生成时间
	 */
	private Date orderCreateTime;
	/**
	 * 审核人员标识
	 */
	private int reviewerId;
	/**
	 * 订单状态，0=待付款，1=待审核，2=支付未完成,3=待发货，4=已发货，5=已签收，6=取消申请中，7=已关闭,8=已取消
	 */
	private int orderStatus;
	/**
	 * 服务状态
	 */
	private int serviceStatus;
	
	/**
	 * 仓储状态，0=待拣货，1=待打包，2=待发货，3=已发货
	 */
	private int storageStatus;
	
	/**
	 * 复核状态，0=未复核，1=系统复核通过，2=系统复核未通过，3=手动复核通过
	 */
	private int checkStatus;
	
	/**
	 * 最后更新时间
	 */
	private Date lastModifiedTime;
	/**
	 * 建议标记
	 */
	private int orderAdvice;
	
	/**
	 * 准时达日期
	 */
	private String deliveryOntime;
	
	/**
	 * 准时达时间id
	 */
	private int deliveryOntimeDetailId;
	
	/**
	 * 积分抵扣金额
	 */
	private BigDecimal pointAmount;
	
	/**
	 * 优惠券支付总金额
	 */
	private BigDecimal couponAmount;
	
	/**
	 * 下单语言
	 */
	private int language;
	
	/**
	 * 店铺Id
	 */
	private int merchantId;

	/**
	 * 渠道
	 */
	private int channel;

	/**
	 * 渠道
	 */
	private int isMember;

	/**
	 * 订单类型
	 */
	private int orderType;

	/**
	 * 是否预订,0=否,1=是
	 */
	private int isPreordain;

	/**
	 * 会员等级  20191010 首页改版
	 */
	private String memberLevelId;
	/***
	 * 积分价值  20191015订单创建
	 */
	private BigDecimal pointRatio;
	/**
	 * 实际支付金额
	 */
	private BigDecimal afterFoldingPrice;
	/**
	 * 应付金额
	 */
	private BigDecimal  payableAmount;

	/**
	 * recordId
	 */
	private int recordId;

	public BigDecimal getPointRatio() {
		return pointRatio;
	}

	public void setPointRatio(BigDecimal pointRatio) {
		this.pointRatio = pointRatio;
	}

	/**
	 * 获取订单标识 
	 * @return id 订单标识 
	 */
	public int getId() {
		return id;
	}

	/**  
	 * 设置订单标识  
	 * @param id 订单标识  
	 */
	public void setId(int id) {
		this.id = id;
	}

	/** 
	 * 获取订单编号规则为3位分站编号+8位日期+6位随机数字 
	 * @return orderNumber 订单编号规则为3位分站编号+8位日期+6位随机数字 
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**  
	 * 设置订单编号规则为3位分站编号+8位日期+6位随机数字  
	 * @param orderNo 订单编号规则为3位分站编号+8位日期+6位随机数字  
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/** 
	 * 获取是否包含子订单 
	 * @return hasChildren 是否包含子订单 
	 */
	public Boolean getHasChildren() {
		return hasChildren;
	}

	/**  
	 * 设置是否包含子订单  
	 * @param hasChildren 是否包含子订单  
	 */
	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	/** 
	 * 获取父订单标识 
	 * @return parentOrderId 父订单标识 
	 */
	public int getParentOrderId() {
		return parentOrderId;
	}

	/**  
	 * 设置父订单标识  
	 * @param parentOrderId 父订单标识  
	 */
	public void setParentOrderId(int parentOrderId) {
		this.parentOrderId = parentOrderId;
	}

	/** 
	 * 获取分站标识 
	 * @return subsiteId 分站标识 
	 */
	public int getSubsiteId() {
		return subsiteId;
	}

	/**  
	 * 设置分站标识  
	 * @param subsiteId 分站标识  
	 */
	public void setSubsiteId(int subsiteId) {
		this.subsiteId = subsiteId;
	}

	/** 
	 * 获取订单来源0=手机客户端1=电脑 
	 * @return orderOrigin 订单来源0=手机客户端1=电脑 
	 */
	public int getOrderOrigin() {
		return orderOrigin;
	}

	/**  
	 * 设置订单来源0=手机客户端1=电脑  
	 * @param orderOrigin 订单来源0=手机客户端1=电脑  
	 */
	public void setOrderOrigin(int orderOrigin) {
		this.orderOrigin = orderOrigin;
	}

	/** 
	 * 获取下单ip 
	 * @return originIP 下单ip 
	 */
	public String getOriginIP() {
		return originIP;
	}

	/**  
	 * 设置下单ip  
	 * @param originIP 下单ip  
	 */
	public void setOriginIP(String originIP) {
		this.originIP = originIP;
	}

	/** 
	 * 获取下单人 
	 * @return memberId 下单人 
	 */
	public int getMemberId() {
		return memberId;
	}

	/**  
	 * 设置下单人  
	 * @param memberId 下单人  
	 */
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	/** 
	 * 获取是否需要发票，0=不需要，1=需要 
	 * @return invoiceStatus 是否需要发票，0=不需要，1=需要 
	 */
	public int getInvoiceStatus() {
		return invoiceStatus;
	}

	/**  
	 * 设置是否需要发票，0=不需要，1=需要  
	 * @param invoiceStatus 是否需要发票，0=不需要，1=需要  
	 */
	public void setInvoiceStatus(int invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	
	/** 
	 * 获取会员备注 
	 * @return remark 会员备注 
	 */
	public String getRemark() {
		return remark;
	}

	/**  
	 * 设置会员备注  
	 * @param remark 会员备注  
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/** 
	 * 获取订单总额 
	 * @return goodsAmount 订单总额 
	 */
	public BigDecimal getGoodsAmount() {
		return goodsAmount;
	}

	/**  
	 * 设置订单总额  
	 * @param goodsAmount 订单总额  
	 */
	public void setGoodsAmount(BigDecimal goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	/**
	 * 获取订单总额
	 * @return goodsPointAmount 订单总额
	 */
	public int getGoodsPointAmount() {
		return goodsPointAmount;
	}

	/**
	 * 设置订单总额
	 * @param goodsPointAmount 订单总额
	 */
	public void setGoodsPointAmount(int goodsPointAmount) {
		this.goodsPointAmount = goodsPointAmount;
	}

	/**
	 * 获取货品优惠总额 
	 * @return goodsDiscountAmount 货品优惠总额 
	 */
	public BigDecimal getGoodsDiscountAmount() {
		return goodsDiscountAmount;
	}

	/**  
	 * 设置货品优惠总额  
	 * @param goodsDiscountAmount 货品优惠总额  
	 */
	public void setGoodsDiscountAmount(BigDecimal goodsDiscountAmount) {
		this.goodsDiscountAmount = goodsDiscountAmount;
	}

	/** 
	 * 获取订单重量 
	 * @return orderWeight 订单重量 
	 */
	public BigDecimal getOrderWeight() {
		return orderWeight;
	}

	/**  
	 * 设置订单重量  
	 * @param orderWeight 订单重量  
	 */
	public void setOrderWeight(BigDecimal orderWeight) {
		this.orderWeight = orderWeight;
	}

	/** 
	 * 获取运费 
	 * @return freightFee 运费 
	 */
	public BigDecimal getFreightFee() {
		return freightFee;
	}

	/**  
	 * 设置运费  
	 * @param freightFee 运费  
	 */
	public void setFreightFee(BigDecimal freightFee) {
		this.freightFee = freightFee;
	}

	/** 
	 * 获取运费优惠金额 
	 * @return freightFeeDiscount 运费优惠金额 
	 */
	public BigDecimal getFreightFeeDiscount() {
		return freightFeeDiscount;
	}

	/**  
	 * 设置运费优惠金额  
	 * @param freightFeeDiscount 运费优惠金额  
	 */
	public void setFreightFeeDiscount(BigDecimal freightFeeDiscount) {
		this.freightFeeDiscount = freightFeeDiscount;
	}

	/** 
	 * 获取物流服务费 
	 * @return freightServiceFee 物流服务费 
	 */
	public BigDecimal getFreightServiceFee() {
		return freightServiceFee;
	}

	/**  
	 * 设置物流服务费  
	 * @param freightServiceFee 物流服务费  
	 */
	public void setFreightServiceFee(BigDecimal freightServiceFee) {
		this.freightServiceFee = freightServiceFee;
	}

	/** 
	 * 获取手工调控金额 
	 * @return ajustAmount 手工调控金额 
	 */
	public BigDecimal getAjustAmount() {
		return ajustAmount;
	}

	/**  
	 * 设置手工调控金额  
	 * @param ajustAmount 手工调控金额  
	 */
	public void setAjustAmount(BigDecimal ajustAmount) {
		this.ajustAmount = ajustAmount;
	}

	/** 
	 * 获取系统抹零抹掉分以后的金额保留小数点六位 
	 * @return systemAjustAmount 系统抹零抹掉分以后的金额保留小数点六位 
	 */
	public BigDecimal getSystemAjustAmount() {
		return systemAjustAmount;
	}

	/**  
	 * 设置系统抹零抹掉分以后的金额保留小数点六位  
	 * @param systemAjustAmount 系统抹零抹掉分以后的金额保留小数点六位  
	 */
	public void setSystemAjustAmount(BigDecimal systemAjustAmount) {
		this.systemAjustAmount = systemAjustAmount;
	}

	/** 
	 * 获取订单生成时间 
	 * @return orderCreateTime 订单生成时间 
	 */
	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	/**  
	 * 设置订单生成时间  
	 * @param orderCreateTime 订单生成时间  
	 */
	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	/** 
	 * 获取审核人员标识 
	 * @return reviewerId 审核人员标识 
	 */
	public int getReviewerId() {
		return reviewerId;
	}

	/**  
	 * 设置审核人员标识  
	 * @param reviewerId 审核人员标识  
	 */
	public void setReviewerId(int reviewerId) {
		this.reviewerId = reviewerId;
	}

	/** 
	 * 获取订单状态，0=待付款，1=待审核，2=支付未完成3=待发货，4=已发货，5=已签收，6=取消申请中，7=已关闭8=已取消 
	 * @return orderStatus 订单状态，0=待付款，1=待审核，2=支付未完成3=待发货，4=已发货，5=已签收，6=取消申请中，7=已关闭8=已取消 
	 */
	public int getOrderStatus() {
		return orderStatus;
	}

	/**  
	 * 设置订单状态，0=待付款，1=待审核，2=支付未完成3=待发货，4=已发货，5=已签收，6=取消申请中，7=已关闭8=已取消  
	 * @param orderStatus 订单状态，0=待付款，1=待审核，2=支付未完成3=待发货，4=已发货，5=已签收，6=取消申请中，7=已关闭8=已取消  
	 */
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	/** 
	 * 获取服务状态 
	 * @return serviceStatus 服务状态 
	 */
	public int getServiceStatus() {
		return serviceStatus;
	}

	/**  
	 * 设置服务状态  
	 * @param serviceStatus 服务状态  
	 */
	public void setServiceStatus(int serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	/** 
	 * 获取仓储状态，0=待拣货，1=待打包，2=待发货，3=已发货 
	 * @return storageStatus 仓储状态，0=待拣货，1=待打包，2=待发货，3=已发货 
	 */
	public int getStorageStatus() {
		return storageStatus;
	}

	/**  
	 * 设置仓储状态，0=待拣货，1=待打包，2=待发货，3=已发货  
	 * @param storageStatus 仓储状态，0=待拣货，1=待打包，2=待发货，3=已发货  
	 */
	public void setStorageStatus(int storageStatus) {
		this.storageStatus = storageStatus;
	}

	/** 
	 * 获取复核状态，0=未复核，1=系统复核通过，2=系统复核未通过，3=手动复核通过 
	 * @return checkStatus 复核状态，0=未复核，1=系统复核通过，2=系统复核未通过，3=手动复核通过 
	 */
	public int getCheckStatus() {
		return checkStatus;
	}

	/**  
	 * 设置复核状态，0=未复核，1=系统复核通过，2=系统复核未通过，3=手动复核通过  
	 * @param checkStatus 复核状态，0=未复核，1=系统复核通过，2=系统复核未通过，3=手动复核通过  
	 */
	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}

 
	/**
	 * 获取 最后更新时间 
	 * @return lastModifiedTime
	 */
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	/**
	 * 设置 最后更新时间 
	 * @param lastModifiedTime :  最后更新时间 
	 */
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	/**
	 * 获取建议
	 * @return 获取建议
	 */
	public int getOrderAdvice() {
		return orderAdvice;
	}
	/**
	 * 设置建议
	 * @param orderAdvice
	 */
	public void setOrderAdvice(int orderAdvice) {
		this.orderAdvice = orderAdvice;
	}

	/**
	 * 获取 应付运费 
	 * @return payableFreight
	 */
	public BigDecimal getPayableFreight() {
		return payableFreight;
	}

	/**
	 * 设置 应付运费 
	 * @param payableFreight :  应付运费 
	 */
	public void setPayableFreight(BigDecimal payableFreight) {
		this.payableFreight = payableFreight;
	}

	/**
	 * 获取 准时达日期 
	 * @return deliveryOntime
	 */
	public String getDeliveryOntime() {
		return deliveryOntime;
	}

	/**
	 * 设置 准时达日期 
	 * @param deliveryOntime :  准时达日期 
	 */
	public void setDeliveryOntime(String deliveryOntime) {
		this.deliveryOntime = deliveryOntime;
	}

	/**
	 * 获取 准时达时间id 
	 * @return deliveryOntimeDetailId
	 */
	public int getDeliveryOntimeDetailId() {
		return deliveryOntimeDetailId;
	}

	/**
	 * 设置 准时达时间id 
	 * @param deliveryOntimeDetailId :  准时达时间id 
	 */
	public void setDeliveryOntimeDetailId(int deliveryOntimeDetailId) {
		this.deliveryOntimeDetailId = deliveryOntimeDetailId;
	}

	/**
	 * 获取 积分抵扣金额 
	 * @return pointAmount
	 */
	public BigDecimal getPointAmount() {
		return pointAmount;
	}

	/**
	 * 设置 积分抵扣金额 
	 * @param pointAmount :  积分抵扣金额 
	 */
	public void setPointAmount(BigDecimal pointAmount) {
		this.pointAmount = pointAmount;
	}

	/**
	 * 获取 优惠券支付总金额 
	 * @return couponAmount
	 */
	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	/**
	 * 设置 优惠券支付总金额 
	 * @param couponAmount :  优惠券支付总金额 
	 */
	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	/**
	 * 获取 下单语言 
	 * @return language
	 */
	public int getLanguage() {
		return language;
	}

	/**
	 * 设置 下单语言 
	 * @param language :  下单语言 
	 */
	public void setLanguage(int language) {
		this.language = language;
	}

	/**
	 * 获取 店铺Id 
	 * @return merchantId
	 */
	public int getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置 店铺Id 
	 * @param merchantId :  店铺Id 
	 */
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 获取 渠道 
	 * @return channel
	 */
	public int getChannel() {
		return channel;
	}

	/**
	 * 设置 渠道 
	 * @param channel :  渠道 
	 */
	public void setChannel(int channel) {
		this.channel = channel;
	}


	public int getIsMember() {
		return isMember;
	}

	public void setIsMember(int isMember) {
		this.isMember = isMember;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public int getIsPreordain() {
		return isPreordain;
	}

	public void setIsPreordain(int isPreordain) {
		this.isPreordain = isPreordain;
	}

	public String getMemberLevelId() {
		return memberLevelId;
	}

	public void setMemberLevelId(String memberLevelId) {
		this.memberLevelId = memberLevelId;
	}

	public BigDecimal getAfterFoldingPrice() {
		return afterFoldingPrice;
	}

	public void setAfterFoldingPrice(BigDecimal afterFoldingPrice) {
		this.afterFoldingPrice = afterFoldingPrice;
	}

	public BigDecimal getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(BigDecimal payableAmount) {
		this.payableAmount = payableAmount;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
}