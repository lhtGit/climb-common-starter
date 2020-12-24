package com.climb.common.util;

import com.climb.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lht
 * @since 2020/10/9 12:13
 */
@Slf4j
@SuppressWarnings("all")
public class OrderSequence {
    /**
     * 上次生产 ID 时间戳
     */
    private long lastTimestamp = -1L;
    /**
     * 并发控制
     */
    private long sequence = 0L;
    /**
     * 一秒内最大生成数量
     */
    private final long SEQUENCE_MASK = 8191;
    /**
     * 最大数据中心id
     */
    private final long MAX_DATACENTER_ID = 31;
    /**
     * 数据中心id
     */
    private final String datacenterId;

    public OrderSequence() {
        datacenterId = String.format("%02d", getDatacenterId(MAX_DATACENTER_ID));
    }




    /**
     * 获取下一个 ID
     * @author lht
     * @since  2020/10/9 15:01
     * @param
     */
    public synchronized String nextId() {
        long timestamp = SystemClock.now();

        if(isSameSecond(timestamp,lastTimestamp)){
            sequence = (sequence + 1) & SEQUENCE_MASK;

            if(sequence == 0){
                timestamp = tilNextMillis(timestamp);
            }
        }else{
            // 不同毫秒内，序列号置为 1 - 3 随机数
            sequence = ThreadLocalRandom.current().nextLong(1, 3);
        }

        lastTimestamp = timestamp;
        return formatDate(timestamp)+datacenterId+String.format("%04d", sequence);
    }

    /**
     * 数据标识id部分
     */
    private static long getDatacenterId(long maxDatacenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                if (null != mac) {
                    id = ((0x000000FF & (long) mac[mac.length - 2]) | (0x0000FF00 & (((long) mac[mac.length - 1]) << 8))) >> 6;
                    id = id % (maxDatacenterId + 1);
                }
            }
        } catch (Exception e) {
            log.warn(" getDatacenterId: " + e.getMessage());
        }
        return id;
    }

    /**
     * 循环获取下一秒
     * @author lht
     * @since  2020/10/9 14:55
     * @param lastTimestamp
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = SystemClock.now();
        while (isSameSecond(timestamp,lastTimestamp)) {
            timestamp = SystemClock.now();
            try{
                Thread.sleep(100L);
            }catch (Exception e){
            }
        }
        return timestamp;
    }

    /**
     * 时间戳是否秒相等
     * @author lht
     * @since  2020/10/9 14:56
     * @param timestamp1
     * @param timestamp2
     */
    private boolean isSameSecond(long timestamp1,long timestamp2){
        //时间回流
        if(timestamp1<timestamp2){
            throw new GlobalException("服务器时间变换，可能导致生成同一编号");
        }
        //
        return timestamp1/1000==timestamp2/1000;
    }

    /**
     * 将日期转为 年月日时分秒 201003121212
     * @author lht
     * @since  2020/10/9 15:00
     * @param time
     */
    private String formatDate(long time){
        LocalDateTime date = new Date(time).toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        int hour = date.getHour();
        int minute = date.getMinute();
        int second = date.getSecond();
        String yearString;
        String monthString;
        String dayString;
        String hourString;
        String minuteString;
        String secondString;
        String yearZeros = "0000";
        StringBuffer timestampBuf;

        if (year < 1000) {
            // Add leading zeros
            yearString = "" + year;
            yearString = yearZeros.substring(0, (4-yearString.length())) +
                    yearString;
        } else {
            yearString = ("" + year).substring(2);
        }
        if (month < 10) {
            monthString = "0" + month;
        } else {
            monthString = Integer.toString(month);
        }
        if (day < 10) {
            dayString = "0" + day;
        } else {
            dayString = Integer.toString(day);
        }
        if (hour < 10) {
            hourString = "0" + hour;
        } else {
            hourString = Integer.toString(hour);
        }
        if (minute < 10) {
            minuteString = "0" + minute;
        } else {
            minuteString = Integer.toString(minute);
        }
        if (second < 10) {
            secondString = "0" + second;
        } else {
            secondString = Integer.toString(second);
        }
        // do a string buffer here instead.
        timestampBuf = new StringBuffer(12);
        timestampBuf.append(yearString);
        timestampBuf.append(monthString);
        timestampBuf.append(dayString);
        timestampBuf.append(hourString);
        timestampBuf.append(minuteString);
        timestampBuf.append(secondString);
        return (timestampBuf.toString());
    }

}
