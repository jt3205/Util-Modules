package net.openobject.tmmm.util;

import lombok.Data;

/**
 * @작성자 : 박형진
 * @작성일 : 2018. 10. 12.
 * @개요 : API 응답의 형식을 맞춰주기 위한 통신 규약
 */
@Data
public class ResponseFormat<T> {
    /**
     * CMD enum 값 넣어야함.
     */
	private CMD code;

    /**
     * 주로 요청 실패시에 이유를 설명. 성공시 null
     */
	private String description;

    /**
     * 제네릭으로써 요청에 대한 데이터. 실패, 요청 후 결과 없을 경우 null
     */
	private T data;

	/**
	 * @param data
	 * 요청 성공시 사용
	 */
    public ResponseFormat(T data) {
    		this.code = CMD.SUCCESS;
        this.data = data;
    }
    
    /**
     * @param code
     * @param description
     * 
     * 주로 요청 실패시 사용
     */
    public ResponseFormat(CMD code, String description) {
    		this.code = code;
    		this.description = description;
    }
}
