package com.bizwink.vo;

/**
 * Created by Administrator on 18-6-25.
 */
public class Node {
    private int id;                    //当前节点ID
    private int pid;                   //树形节点的ID
    private int subnodenum;           //当前节点子节点的数量
    private int level;                 //树形节点所在的层级

    public Node(int id,int pid,int subnode,int level) {
        this.id = id;
        this.pid = pid;
        this.subnodenum = subnode;
        this.level = level;
    }

    public Node(int pid,int level) {
        this.pid = pid;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSubnodenum() {
        return subnodenum;
    }

    public void setSubnodenum(int subnodenum) {
        this.subnodenum = subnodenum;
    }
}
