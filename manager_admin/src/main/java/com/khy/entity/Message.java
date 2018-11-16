package com.khy.entity;

import java.util.Date;

import com.khy.utils.Utils;

public class Message extends BaseEntity{
	private Long id;
	private String title;
	private String content;
	private Date createTime;
    private String createTimeStr;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		this.createTimeStr = (null != createTime?Utils.formatDate(createTime, "yyyy-MM-dd HH:mm:ss"):"");
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
}
