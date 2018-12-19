package net.openobject.tmmm.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * @작성자 : 장국영 (잘쓸게 국영아 ^^) (https://github.com/HYNIX-Jang)
 * @작성일 : 2018. 10. 25
 * @개요 : 네이버 소셜로그인을 하기 위한 Util
 */
public class NaverLoginUtil {
    private HttpUtil httpUtil = new HttpUtil();

    public String getNaverEmail(String code, String state) {
        String tokenJson = getTokenJson(code, state);
        String userInfoJson = getUserInfoJson(tokenJson);
        String email = null;
        try {
            JSONObject jsonObject = new JSONObject(userInfoJson);
            email = jsonObject.getJSONObject("response").getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return email;
    }

    private String getTokenJson(String code, String state) {
        Map<String, String> properties = new HashMap<>();
        properties.put("X-Naver-Client-Id", Constants.SNS_NAVER_CLIENT_ID);
        properties.put("X-Naver-Client-Secret", Constants.SNS_NAVER_CLIENT_SECRET);

        Map<String, String> params = new HashMap<>();
        params.put("client_id", Constants.SNS_NAVER_CLIENT_ID);
        params.put("client_secret", Constants.SNS_NAVER_CLIENT_SECRET);
        params.put("redirect_uri", Constants.SNS_NAVER_CALLBACK_URL);
        params.put("code", code);
        params.put("state", state);
        return httpUtil.sendRequest(Constants.SNS_NAVER_TOKEN_URL, properties, params,
                HttpUtil.RequestMethod.GET);
    }

    public String getLoginURL() {
        String state = new BigInteger(130, new SecureRandom()).toString();
        return Constants.SNS_NAVER_LOGIN_URL +
                "&client_id=" + Constants.SNS_NAVER_CLIENT_ID +
                "&redirect_uri=" + Constants.SNS_NAVER_CALLBACK_URL +
                "&state=" + state;
    }

    private String getUserInfoJson(String token) {
        String accessToken = null;
        try {
            JSONObject jsonObject = new JSONObject(token);
            accessToken = jsonObject.get("access_token").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> properties = new HashMap<>();
        properties.put("Authorization", "Bearer " + accessToken);
        return httpUtil.sendRequest("https://openapi.naver.com/v1/nid/me", properties, null, HttpUtil.RequestMethod.GET);
    }
}