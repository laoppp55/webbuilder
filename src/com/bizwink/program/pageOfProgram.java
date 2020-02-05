package com.bizwink.program;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-3-7
 * Time: 16:49:11
 * To change this template use File | Settings | File Templates.
 */
public class pageOfProgram {
    private String header;
    private String display;
    private String tail;
    private String script;

    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public void setDisplay(String disp) {
        this.display = disp;
    }

    public String getDisplay() {
        return display;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public String getTail() {
        return tail;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getScript() {
        return script;
    }
}
