package com.ldm.bean;

import java.util.List;

public class CaipiaoResultBean2 {
	
	private String recordCnt;
	private String lotteryCode;
	private List<CaipiaoBean> data;
	public String getRecordCnt() {
		return recordCnt;
	}
	public void setRecordCnt(String recordCnt) {
		this.recordCnt = recordCnt;
	}
	public String getLotteryCode() {
		return lotteryCode;
	}
	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}
	public List<CaipiaoBean> getData() {
		return data;
	}
	public void setData(List<CaipiaoBean> data) {
		this.data = data;
	}
	

}
