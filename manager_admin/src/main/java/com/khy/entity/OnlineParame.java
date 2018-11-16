package com.khy.entity;

import java.io.Serializable;
import java.util.Date;

import com.khy.utils.Utils;

public class OnlineParame extends BaseEntity implements Serializable {
    private Long id;

    private String title;

    private String content;

    private String description;
    
    private Date createTime;
    
    private String createTimeStr;
    
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
}