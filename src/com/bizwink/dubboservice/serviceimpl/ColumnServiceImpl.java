package com.bizwink.dubboservice.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bizwink.ColumnTree.ITree;
import com.bizwink.ColumnTree.Tree;
import com.bizwink.ColumnTree.TreeManager;
import com.bizwink.ColumnTree.node;
import com.bizwink.dubboservice.service.ColumnService;
import com.bizwink.persistence.ColumnMapper;
import com.bizwink.persistence.CompanyinfoMapper;
import com.bizwink.po.Column;
import com.bizwink.po.Companyinfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by petersong on 16-4-5.
 */
@Service
public class ColumnServiceImpl implements ColumnService {
    @Autowired
    private ColumnMapper columnMapper;
    @Autowired
    private CompanyinfoMapper companyinfoMapper;

    public List<Column> getColumnsBySiteid(BigDecimal siteid) {
        return columnMapper.getColumnsBySiteid(siteid);
    }

    public List<Column> getSubColumnsByParentID(BigDecimal pid) {
        return columnMapper.getSubColumnsByParentID(pid);
    }

    public String getColumnJsonsBySiteid(BigDecimal siteid,BigDecimal samsiteid) {
        Companyinfo companyinfo = companyinfoMapper.getCompanyinfoBySiteid(siteid.intValue());
        List<Column> columns = columnMapper.getColumnsBySiteid(samsiteid);
        String buf = null;
        if (columns.size() > 0) {
            buf = "[\r\n";
            for(int ii=0;ii<columns.size();ii++){
                Column column = (Column)columns.get(ii);
                if (column.getCNAME().contains("gugulx.com")) {
                    column.setCNAME(companyinfo.getCOMPANYNAME());
                }
                if (ii<columns.size()-1) {
                    if (column.getPARENTID().intValue()==0)
                        buf = buf + "{id:" +column.getID().intValue() + ",pId:"+ column.getPARENTID().intValue() + ",name:\"" + column.getCNAME() +"\",url:\"\"" + ",open:\"true\"" +"},\r\n";
                    else
                        buf = buf + "{id:" +column.getID().intValue() + ",pId:"+ column.getPARENTID().intValue() + ",name:\"" + column.getCNAME() +"\"},\r\n";
                }else{
                    if (column.getPARENTID().intValue()==0)
                        buf = buf + "{id:" +column.getID().intValue() + ",pId:"+ column.getPARENTID().intValue() + ",name:\"" + column.getCNAME() +"\",url:\"#\"" + ",open:\"true\"" +"}\r\n";
                    else
                        buf = buf + "{id:" +column.getID().intValue() + ",pId:"+ column.getPARENTID().intValue() + ",name:\"" + column.getCNAME() +"\"}\r\n";
                }
            }
            buf = buf + "]";
        }
        return buf;
    }

    public Column getRootColumn(BigDecimal siteid) {
        return columnMapper.getRootColumnBySiteid(siteid);
    }

    public Column getColumnByID(BigDecimal columnid) {
        return columnMapper.selectByPrimaryKey(columnid);
    }

    public String getJsonDataOfColumnsBySiteid(BigDecimal siteid,BigDecimal samsiteid) {
        List<Column> Columns = columnMapper.getColumnsNotIncludeRootColumnBySiteidOrderByOrderID(samsiteid);
        Column rootcolumn = columnMapper.getRootColumnBySiteid(samsiteid);
        int column_count= columnMapper.getColumnCount(samsiteid);
        ITree treePeer = new TreeManager();
        Tree colTree = treePeer.getSiteTree(column_count,rootcolumn,Columns);
        StringBuffer buf = generateTreeData(colTree);

        return buf.toString();
    }

    private StringBuffer generateTreeData(Tree colTree) {  //(Tree colTree) {
        StringBuffer buf = new StringBuffer();                        //存储生成的菜单树

        if (colTree.getNodeNum() > 1) {
            node[] treeNodes = colTree.getAllNodes();                     //获取该树的所有节点
            int node[] = new int[colTree.getNodeNum()];                   //遍历树所需要的节点数组，存储当前未处理的节点
            int subnode_num[] = new int[colTree.getNodeNum()];            //存储每个节点子节点的个数
            int subnodenum = 0;                                           //记录当前节点的子节点数
            int currentID = 0;                                            //当前正在处理的节点,当前节点的ID
            int i = 0;                                                    //循环变量
            int nodenum = 1;                                              //当前被处理节点的初始值
            int process_node_total = 0;                                   //当前处理节点的总数量
            int[] ordernum = new int[colTree.getNodeNum()];               //当前节点所有子节点的顺序号
            int orderNumber = 0;                                          //当前节点在同级节点的顺序号
            int depth = 0;                                              //最深的节点层次

            buf.append("var zNodes =[\r\n");

            do {
                currentID = node[nodenum];
                process_node_total = process_node_total + 1;

                //从处理的节点数组中取出当前正在处理的元素，查找该元素下的子元素
                //设置所有子节点的父菜单名称，设置所有子节点的序列号，把所有的子节点存入pid数组中
                subnodenum = 0;
                nodenum = nodenum - 1;
                int current_nodeid = 0;
                for (i = 0; i < colTree.getNodeNum(); i++) {
                    if (treeNodes[i].getLinkPointer() == currentID) {
                        nodenum = nodenum + 1;
                        node[nodenum] = treeNodes[i].getId();
                        //统计当前节点的页节点数量
                        subnodenum = subnodenum + 1;
                    }

                    if (treeNodes[i].getId() == currentID) {
                        current_nodeid = i;
                    }
                }

                //记录当前节点的子节点数
                subnode_num[current_nodeid] = subnodenum;

                //寻找当前节点的父节点
                int parent_nodeid=0;
                for (i = 0; i < colTree.getNodeNum(); i++) {
                    if (treeNodes[i].getId() == treeNodes[current_nodeid].getLinkPointer()) {
                        parent_nodeid = i;
                        break;
                    }
                }

                //获取节点的深度，从根节点到该节点
                //int depth = Server.colTree.getNodeDepth(Server.colTree,treeNodes[current_nodeid].getId());
                //if (depth>zsdepth) zsdepth = depth;

                StringBuffer prefix_space = new StringBuffer();

                //for(i=0; i<depth; i++) {
                //   prefix_space.append("   ");
                //}

                //如果当前节点有子节点，生成当前节点的菜单
                if (treeNodes[current_nodeid].isLeaffalg()==false) {
                    subnode_num[parent_nodeid] = subnode_num[parent_nodeid] - 1;
                    buf.append(prefix_space).append("{ name:\"" + treeNodes[current_nodeid].getChName() + " - 展开\",id:" + treeNodes[current_nodeid].getId() + ",open:true,\r\n");
                    buf.append(prefix_space).append("   children: [\r\n");
                    depth = depth + 1;
                } else {
                    if (subnode_num[parent_nodeid] > 1) {
                        subnode_num[parent_nodeid] = subnode_num[parent_nodeid] - 1;
                        buf.append(prefix_space).append("{ name:\"" + treeNodes[current_nodeid].getChName() + "\",id:" + treeNodes[current_nodeid].getId() + "},\r\n");
                    } else if (subnode_num[parent_nodeid] == 1){
                        subnode_num[parent_nodeid] = subnode_num[parent_nodeid] - 1;
                        buf.append(prefix_space).append("{ name:\"" + treeNodes[current_nodeid].getChName() + "\",id:" + treeNodes[current_nodeid].getId() + "}\r\n");
                        //if (process_node_total < Server.colTree.getNodeNum())  {    //不是最后一个节点
                        if (nodenum>0) {
                            buf.append(prefix_space).append("]},\r\n");
                            depth = depth - 1;
                        } else {                                               //最后一个节点
                            buf.append(prefix_space).append("]}\r\n");
                            depth = depth - 1;
                        }
                    }
                }

                //处理完最后一个节点,写结尾标识
                //if (process_node_total == Server.colTree.getNodeNum()) {
                if (nodenum == 0) {
                    for(i=0; i<depth; i++)  buf.append("    ]}\r\n");
                    buf.append("];");
                }

            } while (nodenum >= 1);


            //直到pid数组中没有待处理的节点为止
        }

        return buf;
    }

    public List<Column> getMajors(Map<String, Object> param){
          return columnMapper.getMajors(param);
    }
}
