package com.xjc.pojo.use;


/**
 * @Author : XJC
 * @Description :消息状态
 * @create : 2022/1/19 9:06
 */
public class MailConstants {

    //发送中
    public static final Integer DELIVERING=0;

    //发送成功
    public static final Integer SUCCESS=1;

    //发送失败
    public static final Integer FAILURE=2;

    public static final Integer MAX_TRY_COUNT=3;

    public static final Integer MAS_TIMEOUT=0;

    public static final String MAIL_QUEUE_NAME="mail.queue";

    public static final String MAIL_EXCHANGE_NAME="mail.exchange";

    public static final String MAIL_ROUTING_KEY_NAME="mail.routing.key";


}
