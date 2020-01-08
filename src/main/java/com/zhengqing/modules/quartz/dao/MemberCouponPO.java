package com.zhengqing.modules.quartz.dao;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 *<p>描述内容</br>会员优惠券PO</p>
 * @author lijianchang Email:lijianchang@co-mall.com
 * @company 北京科码先锋技术有限公司@版权所有
 * @version 3.1.0
 * @since 2016年3月9日上午11:00:12
 */
public class MemberCouponPO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 会员优惠券ID
	 */
	private int id;
	/**
	 * 会员ID
	 */
	private int memberId;
	/**
	 * 优惠券ID
	 */
	private int couponId;
	/**
	 * 优惠券批次ID
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
	 * 中文图片ID
	 */
	private int pictureId;
	/**
	 * 英文图片ID
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
	 * 优惠券券类型：0.外部优惠券、1.优惠券-单品满赠积分、2.折扣券-单品满减商品消费、3.折扣券-单品满减运费、4.折扣券-多品满赠积分、5. 折扣券-多品满减商品消费、6.折扣券-多品满减运费
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
	 * 获取会员优惠券ID
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * 设置 会员优惠券ID
	 * @param id ： 会员优惠券ID
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 获取会员ID
	 * @return memberId
	 */
	public int getMemberId() {
		return memberId;
	}
	/**
	 * 设置 会员ID
	 * @param memberId ： 会员ID
	 */
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	/**
	 * 获取优惠券ID
	 * @return couponId
	 */
	public int getCouponId() {
		return couponId;
	}
	/**
	 * 设置 优惠券ID
	 * @param couponId ： 优惠券ID
	 */
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	/**
	 * 获取优惠券批次ID
	 * @return couponBatchId
	 */
	public int getCouponBatchId() {
		return couponBatchId;
	}
	/**
	 * 设置 优惠券批次ID
	 * @param couponBatchId ： 优惠券批次ID
	 */
	public void setCouponBatchId(int couponBatchId) {
		this.couponBatchId = couponBatchId;
	}
	/**
	 * 获取继承的促销表Id
	 * @return promotionId
	 */
	public int getPromotionId() {
		return promotionId;
	}
	/**
	 * 设置 继承的促销表Id
	 * @param promotionId ： 继承的促销表Id
	 */
	public void setPromotionId(int promotionId) {
		this.promotionId = promotionId;
	}
	/**
	 * 获取券号
	 * @return couponNo
	 */
	public String getCouponNo() {
		return couponNo;
	}
	/**
	 * 设置 券号
	 * @param couponNo ： 券号
	 */
	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}
	/**
	 * 获取密码:加密的密码
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置 密码:加密的密码
	 * @param password ： 密码:加密的密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取中文图片ID
	 * @return pictureId
	 */
	public int getPictureId() {
		return pictureId;
	}
	/**
	 * 设置 中文图片ID
	 * @param pictureId ： 中文图片ID
	 */
	public void setPictureId(int pictureId) {
		this.pictureId = pictureId;
	}
	/**
	 * 获取英文图片ID
	 * @return pictureENId
	 */
	public int getPictureENId() {
		return pictureENId;
	}
	/**
	 * 设置 英文图片ID
	 * @param pictureENId ： 英文图片ID
	 */
	public void setPictureENId(int pictureENId) {
		this.pictureENId = pictureENId;
	}
	/**
	 * 获取有效期开始时间
	 * @return startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置 有效期开始时间
	 * @param startTime ： 有效期开始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取有效期结束时间
	 * @return endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置 有效期结束时间
	 * @param endTime ： 有效期结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取促销类型
	 * @return couponType
	 */
	public int getCouponType() {
		return couponType;
	}
	/**
	 * 设置 促销类型
	 * @param couponType ： 促销类型
	 */
	public void setCouponType(int couponType) {
		this.couponType = couponType;
	}
	/**
	 * 获取状态:0.删除1启用2停用
	 * @return status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * 设置 状态:0.删除1启用2停用
	 * @param status ： 状态:0.删除1启用2停用
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * 获取优惠券说明
	 * @return remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置 优惠券说明
	 * @param remark ： 优惠券说明
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取创建时间
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置 创建时间
	 * @param createTime ： 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取创建用户
	 * @return creator
	 */
	public int getCreator() {
		return creator;
	}
	/**
	 * 设置 创建用户
	 * @param creator ： 创建用户
	 */
	public void setCreator(int creator) {
		this.creator = creator;
	}
	/**
	 * 获取最后更新时间
	 * @return lastModifiedTime
	 */
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}
	/**
	 * 设置 最后更新时间
	 * @param lastModifiedTime ： 最后更新时间
	 */
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	/**
	 * 获取最后更新用户
	 * @return lastModifiedUser
	 */
	public int getLastModifiedUser() {
		return lastModifiedUser;
	}
	/**
	 * 设置 最后更新用户
	 * @param lastModifiedUser ： 最后更新用户
	 */
	public void setLastModifiedUser(int lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}
	/**
	 * 获取支付流水号
	 * @return mergePaymentNo
	 */
	public String getMergePaymentNo() {
		return mergePaymentNo;
	}
	/**
	 * 设置 支付流水号
	 * @param mergePaymentNo ： 支付流水号
	 */
	public void setMergePaymentNo(String mergePaymentNo) {
		this.mergePaymentNo = mergePaymentNo;
	}
	/**
	 * 获取业务状态：1.已创建、2.已绑定、3.已使用、4.已过期
	 * @return bizStatus
	 */
	public int getBizStatus() {
		return bizStatus;
	}
	/**
	 * 设置 业务状态：1.已创建、2.已绑定、3.已使用、4.已过期
	 * @param bizStatus ： 业务状态：1.已创建、2.已绑定、3.已使用、4.已过期
	 */
	public void setBizStatus(int bizStatus) {
		this.bizStatus = bizStatus;
	}
		
}
