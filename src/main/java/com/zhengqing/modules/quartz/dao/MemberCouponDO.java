package com.zhengqing.modules.quartz.dao;

import java.io.Serializable;
import java.util.Date;


public class MemberCouponDO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 会员优惠券标识
	 */
	private int id;

	/**
	 * 会员标识
	 */
	private int memberId;
	/**
	 * 优惠券标识
	 */
	private int couponId;
	/**
	 * 优惠券批次标识
	 */
	private int couponBatchId;
	/**
	 * 继承的促销表Id
	 */
	private int promotionId;
	/**
	 * 券号
	 */
	private String couponNo;
	/**
	 * 密码:加密的密码
	 */
	private String password;
	/**
	 * 中文图片标识
	 */
	private int pictureId;
	/**
	 * 英文图片标识
	 */
	private int pictureENId;
	/**
	 * 有效期开始时间
	 */
	private Date startTime;
	/**
	 * 有效期结束时间
	 */
	private Date endTime;
	/**
	 * 促销类型
	 */
	private int couponType;
	
	/**
	 * 状态:0. 删除 1 启用 2 停用
	 */
	private int status;
	/**
	 * 优惠券说明
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建用户
	 */
	private int creator;
	/**
	 * 最后更新时间
	 */
	private Date lastModifiedTime;
	/**
	 * 最后更新用户
	 */
	private int lastModifiedUser;
	/**
	 * 支付流水号
	 */
	private String mergePaymentNo;
	/**
	 * 业务状态：1.已创建、2.已绑定、3.已使用、4.已过期
	 */
	private int bizStatus;
	/**
	 * 中文图片地址
	 */
	private String pictureUrl;
	/**
	 * 英文图片地址
	 */
	private String pictureENUrl;
	/**
	 * 促销中文名称
	 */
	private String promotionName;
	/**
	 * 促销英文名称
	 */
	private String promotionNameEN;
	/**
	 * 优惠券中文名称
	 */
	private String couponName;
	/**
	 * 优惠券英文名称
	 */
	private String couponNameEN;
	
	public MemberCouponDO() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getCouponId() {
		return couponId;
	}
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	
	public int getCouponBatchId() {
		return couponBatchId;
	}

	public void setCouponBatchId(int couponBatchId) {
		this.couponBatchId = couponBatchId;
	}
	
	public int getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(int promotionId) {
		this.promotionId = promotionId;
	}

	public String getCouponNo() {
		return couponNo;
	}
	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPictureId() {
		return pictureId;
	}
	public void setPictureId(int pictureId) {
		this.pictureId = pictureId;
	}
	public int getPictureENId() {
		return pictureENId;
	}
	public void setPictureENId(int pictureENId) {
		this.pictureENId = pictureENId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getCouponType() {
		return couponType;
	}
	public void setCouponType(int couponType) {
		this.couponType = couponType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getCreator() {
		return creator;
	}
	public void setCreator(int creator) {
		this.creator = creator;
	}
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public int getLastModifiedUser() {
		return lastModifiedUser;
	}
	public void setLastModifiedUser(int lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}

	public String getMergePaymentNo() {
		return mergePaymentNo;
	}

	public void setMergePaymentNo(String mergePaymentNo) {
		this.mergePaymentNo = mergePaymentNo;
	}

	public int getBizStatus() {
		return bizStatus;
	}

	public void setBizStatus(int bizStatus) {
		this.bizStatus = bizStatus;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getPictureENUrl() {
		return pictureENUrl;
	}

	public void setPictureENUrl(String pictureENUrl) {
		this.pictureENUrl = pictureENUrl;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public String getPromotionNameEN() {
		return promotionNameEN;
	}

	public void setPromotionNameEN(String promotionNameEN) {
		this.promotionNameEN = promotionNameEN;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getCouponNameEN() {
		return couponNameEN;
	}

	public void setCouponNameEN(String couponNameEN) {
		this.couponNameEN = couponNameEN;
	}
	
	
}
