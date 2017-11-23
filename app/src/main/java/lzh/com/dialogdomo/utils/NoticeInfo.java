package lzh.com.dialogdomo.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/11/17.
 */

public class NoticeInfo {
    private String[] message;
    private String realMsg;

    private String police;
    private String client;
    private String ip;
    private int port = 9031;
    private String date;
    private int type;
    private String code;
    private String sessionId;
    private boolean status = true;

    private String[] getMessage(String msg) {
        return  msg.split("#")[0].split(",", 2)[1].split(",");
    }

    private String[] getMessage() {
        return message;
    }
    public NoticeInfo(String msg) {
        realMsg = msg;
        Log.e("lzh",""+msg);
        message = getMessage(msg);
        police = message[0];
        code = message[3];
        ip = message[5];
        port =  Integer.valueOf(message[6]);
        sessionId = message[7];
        client = message[8];
        date = new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date());

    }
    public NoticeInfo(String[] msg) {
        message = msg;
        police = message[0];
        code = message[2];
        ip = message[4];
        port =  Integer.valueOf(message[5]);
        sessionId = message[6];
        client = message[7];
        date = new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date());

    }

    public String getPolice() {
        return police;
    }

    public void setPolice(String police) {
        this.police = police;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRealMsg() {
        return realMsg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
