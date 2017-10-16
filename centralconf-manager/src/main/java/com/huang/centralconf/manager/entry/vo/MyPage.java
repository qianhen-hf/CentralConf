package com.huang.centralconf.manager.entry.vo;

public class MyPage {

	private Integer offset = 0;
	private Integer limit = 10;
	private Integer results = 10;
	private Integer page = 1;

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPageNo() {
		return offset / limit + 1;
	}

	public Integer getResults() {
		return results;
	}

	public void setResults(Integer results) {
		this.results = results;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

}
