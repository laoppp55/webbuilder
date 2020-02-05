package com.bizwink.webtrend;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-12
 * Time: 下午9:39
 * To change this template use File | Settings | File Templates.
 */
public class UserSession {
    private Timestamp enter_time;
    private Timestamp leave_time;
    private String enter_url;
    private String leave_url;

    public Timestamp getEnter_time() {
        return enter_time;
    }

    public void setEnter_time(Timestamp enter_time) {
        this.enter_time = enter_time;
    }

    public Timestamp getLeave_time() {
        return leave_time;
    }

    public void setLeave_time(Timestamp leave_time) {
        this.leave_time = leave_time;
    }

    public String getEnter_url() {
        return enter_url;
    }

    public void setEnter_url(String enter_url) {
        this.enter_url = enter_url;
    }

    public String getLeave_url() {
        return leave_url;
    }

    public void setLeave_url(String leave_url) {
        this.leave_url = leave_url;
    }
}
