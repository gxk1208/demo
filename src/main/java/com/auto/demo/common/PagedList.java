package com.auto.demo.common;

import com.github.pagehelper.Page;

import java.util.List;

/**
 * 分页处理Response
 *
 * @author yuan'gui
 *
 * @param <T>
 */
public class PagedList<T> {
	private static final long serialVersionUID = -3398360572386723106L;

	/** 页码 */
	private int pageNum = 1;
	/** 每页的数量 */
	private int pageSize = 10;
	/** 总记录数 */
	private long total = 0;

	private List<T> data;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

    public long getPages() {
      if (pageSize == 0 || total == 0) {
          return 0;
      }
      long pageMod = total % pageSize;
      long pageCount = total / pageSize;
      return pageMod == 0 ? pageCount : pageCount + 1;
    }
	/**
	 * @param list
	 * @return
	 * @since 1.0
	 */
	public static <T> PagedList<T> parse(List<T> list) {
		if (list instanceof Page) {
			Page<T> page = (Page<T>) list;
			PagedList<T> pagedList = new PagedList<>();
			pagedList.pageNum = page.getPageNum();
			pagedList.pageSize = page.getPageSize();
			pagedList.total = page.getTotal();
			pagedList.data = page.getResult();

			return pagedList;
		} else {
			PagedList<T> pagedList = new PagedList<>();
			pagedList.pageNum = 1;
			pagedList.pageSize = list.size();
			pagedList.total = list.size();
			pagedList.data = list;

			return pagedList;
		}
	}
}
