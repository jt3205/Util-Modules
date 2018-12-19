package net.openobject.tmmm.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @작성자 : 박형진
 * @작성일 : 2018. 10. 15.
 * @개요 : 페이징 처리에 필요한 데이터들을 모아 보내기 위한 Format
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageFormat<E> {
	private E data;
	
	private int endPage;
}
