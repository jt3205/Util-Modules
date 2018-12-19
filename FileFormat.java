package net.openobject.tmmm.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @작성자 : 박형진
 * @작성일 : 2018. 10. 15.
 * @개요 : FileUploadUtil에서 사용할 데이터 전달용 객체 
 * 		  (사실 DTO에 더 가까우나 util의 독립성을 위해 util패키지 안에 존재)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileFormat {
	private String name;
	
	private String originalName;
}
