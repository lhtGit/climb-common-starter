package com.climb.common.util;

/**
 * 雪花算法 唯一id
 * @author lht
 * @since 2020/9/29 14:39
 */
public class IdUtils {
    private static final Sequence SEQUENCE = new Sequence();
    public static String nextId(){
        return String.valueOf(nextIdNumber());
    }

    public static long nextIdNumber(){
        return SEQUENCE.nextId();
    }

}
