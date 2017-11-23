package lzh.com.dialogdomo.utils;

/**
 * Created by Administrator on 2017/11/16.
 */

public class UserBean {
    private String terminalId;
    private String name;

    public UserBean(String terminalId,String name){
        setTerminalId(terminalId);
        setName(name);
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
