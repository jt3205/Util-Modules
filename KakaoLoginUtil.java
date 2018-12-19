package net.openobject.tmmm.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @작성자 : 박형진
 * @작성일 : 2018. 10. 22
 * @개요 : 카카오 소셜로그인을 하기 위한 Util
 */
public class KakaoLoginUtil {
	private HttpUtil httpUtil = new HttpUtil();

	public String getLoginURL() {
		return Constants.SNS_KAKAO_LOGIN_URL;
	}
	
	public String getKakaoEmail(String code) {
		String tokenJson = getTokenJson(code);
		String userInfoJson = getUserInfoJson(tokenJson);
		String email = null;
		try {
			JSONObject jsonObject = new JSONObject(userInfoJson);
			email = jsonObject.getJSONObject("kakao_account").getString("email").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return email;
	}

	private String getTokenJson(String code) {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("Authorization", Constants.SNS_KAKAO_ADMIN_ID);

		Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "authorization_code");
		params.put("client_id", Constants.SNS_KAKAO_CLIENT_ID);
		params.put("redirect_uri", Constants.SNS_KAKAO_CALLBACK_URL);
		params.put("code", code);

		return httpUtil.sendRequest("https://kauth.kakao.com/oauth/token", properties, params,
				HttpUtil.RequestMethod.POST);
	}

	private String getUserInfoJson(String tokenJson) {
		String accessToken = null;
		try {
			JSONObject jsonObject = new JSONObject(tokenJson);
			accessToken = jsonObject.get("access_token").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("Authorization", "Bearer " + accessToken);
		return httpUtil.sendRequest("https://kapi.kakao.com/v2/user/me", properties, null, HttpUtil.RequestMethod.POST);
	}
}

// 09fb9ec7e11dc4e2e34b74bcfaa1d290