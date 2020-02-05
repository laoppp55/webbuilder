package com.bizwink.log;

public class LogColumnTree {
    private int treeRoot;
    private int nodeNum;
    private PublishArticleCountOfColumn[] publishArticleCountOfColumns;

    public int getTreeRoot() {
        return treeRoot;
    }

    public void setTreeRoot(int treeRoot) {
        this.treeRoot = treeRoot;
    }

    public int getNodeNum() {
        return nodeNum;
    }

    public void setNodeNum(int nodeNum) {
        this.nodeNum = nodeNum;
    }

    public PublishArticleCountOfColumn[] getPublishArticleCountOfColumns() {
        return publishArticleCountOfColumns;
    }

    public void setPublishArticleCountOfColumns(PublishArticleCountOfColumn[] publishArticleCountOfColumns) {
        this.publishArticleCountOfColumns = publishArticleCountOfColumns;
    }
}
