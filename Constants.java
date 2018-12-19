package net.openobject.tmmm.util;

/**
 * @작성자 : 박형진
 * @작성일 : 2018. 10. 11.
 * @개요 : 상수들을 담고있는 클래스
 */
public class Constants {
    public final static String SALT_KEY = "";

    public final static String MAIL_SENDER = "";

    public final static int PAGE_LIMIT = 15;

    // KAKAO *********************************************************************************
    final static String SNS_KAKAO_CLIENT_ID = "";
    
    final static String SNS_KAKAO_ADMIN_ID = "";

    final static String SNS_KAKAO_CALLBACK_URL = "http://127.0.0.1:5060/api/test/kakao";

    final static String SNS_KAKAO_LOGIN_URL = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + SNS_KAKAO_CLIENT_ID + "&redirect_uri=" + SNS_KAKAO_CALLBACK_URL;
    // KAKAO *********************************************************************************

    // NAVER *********************************************************************************
    final static String SNS_NAVER_CLIENT_ID = "";

    final static String SNS_NAVER_CLIENT_SECRET = "";

    final static String SNS_NAVER_CALLBACK_URL = "http://192.168.0.18:5060/api/test/login/naver/callback";

    final static String SNS_NAVER_LOGIN_URL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";

    final static String SNS_NAVER_TOKEN_URL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
    // NAVER *********************************************************************************

    // Google ********************************************************************************

	public static final String SNS_GOOGLE_CLIENT_ID = "";
	
	public static final String SNS_GOOGLE_CLIENT_SECRET = "";

	public static final String SNS_GOOGLE_SCOPE_URL = "https://www.googleapis.com/auth/plus.profile.emails.read";
	
	public static final String SNS_GOOGLE_CALLBACK_URL = "http://localhost:5060/api/test/login/google/callback";
	
    // Google ********************************************************************************

    // 이미지 업로드 폴더
    public final static String UPLOADED_FOLDER = "/var/lib/jenkins/workspace/tmmm/images/";
}
