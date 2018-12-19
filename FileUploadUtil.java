package net.openobject.tmmm.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import javassist.NotFoundException;
import net.openobject.tmmm.exception.SuspiciousFileException;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

/**
 * @작성자 : 박형진
 * @작성일 : 2018. 10. 11.
 * @개요 : 파일업로드를 수행하기 위한 Util 클래스
 */
public class FileUploadUtil {
	private RandomStringUtil randomStringUtil = new RandomStringUtil(5);

	private String[] allowMineType = {"image/jpeg","image/png", "image/gif"};
	
	public FileFormat saveFile(MultipartFile file) throws IOException, SuspiciousFileException, NotFoundException {
		if (file == null || file.isEmpty()) {
			throw new NotFoundException("파일을 찾을 수 없습니다.");
		}
		
		byte[] bytes = file.getBytes();
		MagicMatch match = null;

		try {
			match = Magic.getMagicMatch(bytes);
		} catch (MagicParseException | MagicMatchNotFoundException | MagicException e) {			//Magic 라이브러리에 포함되어 있지 않은 파일 형식일 경우
			throw new SuspiciousFileException("파일의 형식이 올바르지 않습니다.");
		}
		if (match != null) {
			String mimeType = match.getMimeType();
			if(!Arrays.asList(allowMineType).contains(mimeType)) {					//허용된 형식이 아닐 경우
				throw new SuspiciousFileException("파일의 형식이 올바르지 않습니다.");
			}
		}

		String fileName = randomStringUtil.nextString();
		String fileNameArr[] = file.getOriginalFilename().split("\\.");
		String extension = fileNameArr[fileNameArr.length - 1];			//확장자 EX) jpg, png, gif

		fileName = new Date().getTime() + fileName + "." + extension;
		Path path = Paths.get(Constants.UPLOADED_FOLDER + fileName);
		Files.write(path, bytes);

		return FileFormat.builder().name(fileName).originalName(file.getOriginalFilename()).build();
	}
}
