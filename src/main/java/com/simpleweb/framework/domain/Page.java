package com.simpleweb.framework.domain;

import java.util.List;

/**
 *  分页查询的对象
 * @author Administrator
 *
 */
public class Page<T> {
    
	/**
	 *  总数量
	 */
	 private long totalCount;
	 
	 /**
	  *  当前页面
	  */
	 private int curPageIndex;
	 
	 /**
	  * 总页面数
	  */
	 private double  totalPageIndex;
	 
	 /**
	  *  当前页的数据
	  */
	 private List<T> data;
	 
	 /**
	  *  单页面数量
	  */
	 private int offset;

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getCurPageIndex() {
		return curPageIndex;
	}

	public void setCurPageIndex(int curPageIndex) {
		this.curPageIndex = curPageIndex;
	}

	public double getTotalPageIndex() {
		return totalPageIndex;
	}

	public void setTotalPageIndex(Double totalPageIndex) {
		this.totalPageIndex = totalPageIndex;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	
	 
	
	
}
