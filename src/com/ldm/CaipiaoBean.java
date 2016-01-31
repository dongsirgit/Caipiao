package com.ldm;

import java.util.List;

public class CaipiaoBean {
	
	private String expect;
	private String openCode;
	private String openTime;
	private String openTimeStamp;
	private MethodBean methodBean;
	private String ONElist;
	public String getONElist() {
		return ONElist;
	}
	public void setONElist(String oNElist) {
		ONElist = oNElist;
	}
	public MethodBean getMethodBean() {
		return methodBean;
	}
	public void setMethodBean(MethodBean methodBean) {
		this.methodBean = methodBean;
	}
	public String getExpect() {
		return expect;
	}
	public void setExpect(String expect) {
		this.expect = expect;
	}
	public String getOpenCode() {
		return openCode;
	}
	public void setOpenCode(String openCode) {
		this.openCode = openCode;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getOpenTimeStamp() {
		return openTimeStamp;
	}
	public void setOpenTimeStamp(String openTimeStamp) {
		this.openTimeStamp = openTimeStamp;
	}

}
