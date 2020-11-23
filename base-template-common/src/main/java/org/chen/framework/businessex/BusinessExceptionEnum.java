package org.chen.framework.businessex;

/**
 * 业务异常
 *
 * @author YuChen
 * @date 2020/2/14 12:05
 **/

public enum BusinessExceptionEnum {

	NoCache("服务繁忙,请重新获取",500)

	,VALIDATE_NO("亲属关系验证不通过",202)

	,RANDOMSTR_EXPIRE("randomStr不存在或已过期",401)
	;

	private Integer code;

	private String message;

	BusinessExceptionEnum(String msg, Integer code){
		this.code = code;
		this.message = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
