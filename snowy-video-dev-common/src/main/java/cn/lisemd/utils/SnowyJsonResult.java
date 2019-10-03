package cn.lisemd.utils;


/**
 *
 */
public class SnowyJsonResult {

	// 响应业务状态
	private Integer status;

	// 响应消息
	private String msg;

	// 响应中的数据
	private Object data;

	private String ok; // 不使用

	public static SnowyJsonResult build(Integer status, String msg, Object data) {
		return new SnowyJsonResult(status, msg, data);
	}

	public static SnowyJsonResult ok(Object data) {
		return new SnowyJsonResult(data);
	}

	public static SnowyJsonResult ok() {
		return new SnowyJsonResult(null);
	}

	public static SnowyJsonResult errorMsg(String msg) {
		return new SnowyJsonResult(500, msg, null);
	}

	public static SnowyJsonResult errorMap(Object data) {
		return new SnowyJsonResult(501, "error", data);
	}

	public static SnowyJsonResult errorTokenMsg(String msg) {
		return new SnowyJsonResult(502, msg, null);
	}

	public static SnowyJsonResult errorException(String msg) {
		return new SnowyJsonResult(555, msg, null);
	}

	public SnowyJsonResult() {

	}

	public SnowyJsonResult(Integer status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public SnowyJsonResult(Object data) {
		this.status = 200;
		this.msg = "OK";
		this.data = data;
	}

	public Boolean isOK() {
		return this.status == 200;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

}
