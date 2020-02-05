package com.extractText;

import org.htmlparser.Node;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-12-6
 * Time: 22:33:29
 * To change this template use File | Settings | File Templates.
 */
public class PageContext {
    private StringBuffer textBuffer;
    private int number;
    private Node node;

    public StringBuffer getTextBuffer() {
        return textBuffer;
    }

    public void setTextBuffer(StringBuffer textBuffer) {
        this.textBuffer = textBuffer;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
