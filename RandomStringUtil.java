package net.openobject.tmmm.util;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 * @작성자 : 박형진
 * @작성일 : 2018. 10. 11.
 * @개요 : 비밀번호 찾기 기능의 인증키 String 난수를 만들기 위한 클래스
 */
public class RandomStringUtil {

    /**
     * 랜덤 String 생성
     */
    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase(Locale.ROOT);

    public static final String digits = "0123456789";

    public static final String alphanum = upper + lower + digits;

    private final Random random;

    private final char[] symbols;

    private char[] buf;

    public RandomStringUtil(int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }
            
    public RandomStringUtil(int length, Random random) {
        this(length, random, alphanum);
    }

    public RandomStringUtil(int length) {
        this(length, new SecureRandom());
    }

    public RandomStringUtil() {
        this(50);
    }
    
    public void setLength(int length) {
        if (length < 1) throw new IllegalArgumentException();
        this.buf = new char[length];
    }
}