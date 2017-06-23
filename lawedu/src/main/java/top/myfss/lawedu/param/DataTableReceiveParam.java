package top.myfss.lawedu.param;


import javax.servlet.http.HttpServletRequest;

import top.myfss.lawedu.util.JsonUtil;

import java.util.Hashtable;
import java.util.Map;

/**
 * dataTables表格数据接收
 */
public class DataTableReceiveParam {
	/**
	 * 开始条数
	 */
	private String startNum = null;
	/**
	 * 每页条数
	 */
	private String pageLength = null;

	/**
	 * 构造函数
	 * @param request
	 */
	public DataTableReceiveParam(HttpServletRequest request) {
		super();
		if(request != null) {
			this.startNum = request.getParameter("start");
			this.pageLength = request.getParameter("length");
		}
	}

	/**
	 * 构造函数
	 * @param startPage
	 * @param pageLength
	 */
	public DataTableReceiveParam(String startPage,String pageLength) {
		super();
		this.startNum = startPage;
		this.pageLength = pageLength;
	}

	public void setDatabaseQuery(Map<String, Object> param) {
		param.put("startNum",startNum);
		param.put("pageLength",pageLength);
	}

	public String getStartNum() {
		return startNum;
	}

	public void setStartNum(String startNum) {
		this.startNum = startNum;
	}

	public String getPageLength() {
		return pageLength;
	}

	public void setPageLength(String pageLength) {
		this.pageLength = pageLength;
	}
}
