package net.openobject.tmmm.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.util.FileCopyUtils;

/**
 * @작성자 : 박형진
 * @작성일 : 2018. 10. 23
 * @개요 : HTTP 요청을 보내기 위한 HTTP 관련 Util 클래스
 */
public class HttpUtil {
	public enum RequestMethod {
		GET, POST, DELETE, PUT
	}

	public String sendRequest(String apiURL, Map<String, String> properties, Map<String, String> params,
			RequestMethod method) {
		String queryString = mapToQueryString(params);
		if (method == RequestMethod.GET || method == RequestMethod.DELETE) {
			apiURL += queryString;
			System.out.println(apiURL);
		}
		try {
			HttpURLConnection conn = getHttpUrlConn(apiURL);
			conn.setRequestMethod(method.toString());
			conn.setDoOutput(true);
			conn.setDoInput(true);
			setProperties(conn, properties);
			if (method == RequestMethod.POST || method == RequestMethod.PUT) {
				byte[] out = queryString.getBytes();
				conn.setFixedLengthStreamingMode(out.length);
				try {
					OutputStream os = conn.getOutputStream();
					os.write(out);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (method == RequestMethod.GET || method == RequestMethod.DELETE) {
				int code = conn.getResponseCode();
				if (code != 200) {
					return null;
				}
			}
			return inputStreamToData(conn.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private HttpURLConnection getHttpUrlConn(String apiURL) throws IOException {
		URL url = new URL(apiURL);
		return (HttpURLConnection) url.openConnection();
	}

	private String mapToQueryString(Map<String, String> params) {
		StringBuilder data = new StringBuilder();
		if (params != null) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				try {
					data.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString())).append("=")
							.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString())).append("&");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return data.toString();
	}

	private void setProperties(HttpURLConnection conn, Map<String, String> properties) {
		if (properties != null) {
			for (Map.Entry<String, String> entry : properties.entrySet()) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
		}
	}

	private String inputStreamToData(InputStream is) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			FileCopyUtils.copy(is, bos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(bos.toByteArray());
	}
}
