package net.openobject.tmmm.util;

/**
 * @작성자 : 박형진
 * @작성일 : 2018. 10. 11.
 * @개요 : ResponseFormat의 상태코드를 정의하기위한 Enum
 */
public enum CMD {
	SUCCESS(200),
	BAD_REQUEST(400),
	TOKEN_ERROR(401),
	FORBIDDEN(403),
	NOT_FOUND(404),
	FAIL(500);

	private int code;

	private CMD(int code) {
		this.setCode(code);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
