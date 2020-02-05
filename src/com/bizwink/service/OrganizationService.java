package com.bizwink.service;

import com.bizwink.cms.util.FileUtil;
import com.bizwink.error.ErrorMessage;
import com.bizwink.persistence.CompanyinfoMapper;
import com.bizwink.persistence.DepartmentMapper;
import com.bizwink.persistence.OrganizationMapper;
import com.bizwink.persistence.UsersMapper;
import com.bizwink.po.Companyinfo;
import com.bizwink.po.Department;
import com.bizwink.po.Organization;
import com.bizwink.vo.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 17-11-1.
 */
@Service
public class OrganizationService {
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private CompanyinfoMapper companyinfoMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private DepartmentMapper departmentMapper;

    @Transactional
    public Map saveOrganizationAndCompany(List<Companyinfo> companyinfos,int siteid,String username,int usertype,int orgpid) {
        int org_error = 0;
        int comp_error = 0;
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrcode(0);

        Timestamp thedatetime = new Timestamp(System.currentTimeMillis());
        Companyinfo companyinfo = null;
        Organization organization = new Organization();
        if (companyinfos.size()>0) {
            //获取组织架构当前层新增加节点的序号
            Map params1 = new HashMap();
            params1.put("CUSTOMERID",siteid);
            params1.put("PARENT",orgpid);
            BigDecimal orderid = organizationMapper.getOrderNumByParentID(params1);
            if (orderid==null)
                orderid = BigDecimal.valueOf(1);
            else
                orderid = orderid.add(BigDecimal.valueOf(1));
            organization.setPARENT(BigDecimal.valueOf(orgpid));
            organization.setNAME(companyinfos.get(0).getCOMPANYNAME());
            organization.setENAME(companyinfos.get(0).getCOMPSHORTNAME());
            organization.setORGCODE(companyinfos.get(0).getCOMPCODE());
            organization.setCUSTOMERID(BigDecimal.valueOf(siteid));
            organization.setCOTYPE(BigDecimal.valueOf(1));
            organization.setORDERID(orderid);
            organization.setSTATUS(BigDecimal.valueOf(1));
            organization.setISLEAF((short) 0);
            organization.setLLEVEL(BigDecimal.valueOf(1));
            organization.setCREATEUSER(username);
            organization.setUPDATEUSER(username);
            organization.setCREATEDATE(thedatetime);
            organization.setLASTUPDATE(thedatetime);
            int errorcode = organizationMapper.insert(organization);
            //组织架构中当前节点的父节点修改为非叶节点
            if (errorcode>0) {
                Map params = new HashMap();
                params.put("ISLEAF",1);
                params.put("ID",orgpid);
                int retcode= organizationMapper.updateIsleafByID(params);
            }

            if (errorcode > 0) {
                for(int ii=0; ii<companyinfos.size(); ii++) {
                    companyinfo = companyinfos.get(ii);
                    companyinfo.setORGID(organization.getID());
                    companyinfo.setSITEID(BigDecimal.valueOf(siteid));
                    companyinfo.setCREATEDATE(thedatetime);
                    companyinfo.setUPDATEDATE(thedatetime);
                    companyinfo.setCREATEUSER(username);
                    companyinfo.setSTATUS(BigDecimal.valueOf(1));
                    if (ii==0)
                        companyinfo.setALIASFLAG(BigDecimal.valueOf(1));
                    else
                        companyinfo.setALIASFLAG(BigDecimal.valueOf(0));
                    comp_error = companyinfoMapper.insert(companyinfo);
                }
                if (comp_error<=0) {
                    errorMessage.setErrcode(-2);
                    errorMessage.setErrmsg("创建组织架构增加公司记录出现错误");
                    errorMessage.setModelname("创建组织架构");
                }
            } else {
                errorMessage.setErrcode(-3);
                errorMessage.setErrmsg("创建组织架构记录出现错误");
                errorMessage.setModelname("创建组织架构");
            }
        } else {
            errorMessage.setErrcode(-1);
            errorMessage.setErrmsg("创建组织架构,但是组织架构的公司信息为空");
            errorMessage.setModelname("创建组织架构");
        }

        Map result = new HashMap();
        result.put("organization",organization);
        result.put("error",errorMessage);

        return result;
    }

    @Transactional
    public Map updateOrganizationAndCompany(List<Companyinfo> companyinfos,int siteid,String username,int usertype,int orgid) {
        int org_error = 0;
        int comp_error = 0;
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrcode(0);

        Timestamp thedatetime = new Timestamp(System.currentTimeMillis());
        Companyinfo companyinfo = null;
        Organization organization = new Organization();
        if (companyinfos.size()>0) {
            organization.setNAME(companyinfos.get(0).getCOMPANYNAME());
            organization.setENAME(companyinfos.get(0).getCOMPSHORTNAME());
            organization.setORGCODE(companyinfos.get(0).getCOMPCODE());
            organization.setORDERID(organization.getORDERID());
            organization.setUPDATEUSER(username);
            organization.setLASTUPDATE(thedatetime);
            organization.setID(BigDecimal.valueOf(orgid));
            org_error = organizationMapper.updateByPrimaryKeySelective(organization);

            if (org_error > 0) {
                for(int ii=0; ii<companyinfos.size(); ii++) {
                    companyinfo = companyinfos.get(ii);
                    companyinfo.setUPDATEDATE(thedatetime);
                    comp_error = companyinfoMapper.updateByPrimaryKeySelective(companyinfo);
                }
                if (comp_error<=0) {
                    errorMessage.setErrcode(-2);
                    errorMessage.setErrmsg("修改组织架构增加公司记录出现错误");
                    errorMessage.setModelname("修改组织架构");
                }
            } else {
                errorMessage.setErrcode(-3);
                errorMessage.setErrmsg("修改组织架构记录出现错误");
                errorMessage.setModelname("修改组织架构");
            }
        } else {
            errorMessage.setErrcode(-1);
            errorMessage.setErrmsg("修改组织架构,但是组织架构的公司信息为空");
            errorMessage.setModelname("修改组织架构");
        }

        Map result = new HashMap();
        result.put("organization",organization);
        result.put("error",errorMessage);

        return result;
    }

    @Transactional
    public Map saveOrganizationAndDept(Department department,int siteid,String username,int usertype,int orgpid) {
        int org_error = 0;
        int dept_error = 0;
        ErrorMessage errorMessage = new ErrorMessage();
        Timestamp thedatetime = new Timestamp(System.currentTimeMillis());

        Organization organization = new Organization();
        //获取当前父ID下一层子ID的序号
        Map params1 = new HashMap();
        params1.put("CUSTOMERID",siteid);
        params1.put("PARENT",orgpid);
        BigDecimal orderid = organizationMapper.getOrderNumByParentID(params1);
        if (orderid==null)
            orderid = BigDecimal.valueOf(1);
        else
            orderid = orderid.add(BigDecimal.valueOf(1));
        //获取主键信息
        organization.setPARENT(BigDecimal.valueOf(orgpid));
        organization.setNAME(department.getCNAME());
        organization.setENAME(department.getENAME());
        organization.setORGCODE(department.getDepartcode());
        organization.setCUSTOMERID(BigDecimal.valueOf(siteid));
        organization.setCOTYPE(BigDecimal.valueOf(0));
        organization.setORDERID(orderid);
        organization.setSTATUS(BigDecimal.valueOf(1));
        organization.setISLEAF((short) 0);
        organization.setLLEVEL(BigDecimal.valueOf(1));
        organization.setCREATEUSER(username);
        organization.setUPDATEUSER(username);
        organization.setCREATEDATE(thedatetime);
        organization.setLASTUPDATE(thedatetime);
        org_error = organizationMapper.insert(organization);

        //组织架构中当前节点的父节点修改为非叶节点
        if (org_error>0) {
            Map params = new HashMap();
            params.put("ISLEAF",1);
            params.put("ID",orgpid);
            int retcode= organizationMapper.updateIsleafByID(params);
        }

        if (org_error>0) {
            List<BigDecimal> compids = getCompanylistByOrgid(BigDecimal.valueOf(siteid), BigDecimal.valueOf(orgpid));
            BigDecimal compid = null;
            if (compids.size()>0) compid = compids.get(0);
            department.setORGID(organization.getID());
            department.setCOMPANYID(compid);

            /*System.out.println("deptid==" + deptid);
            System.out.println("orgid==" + orgid);
            System.out.println("compid==" + compid);
            System.out.println("name==" + department.getCNAME());
            System.out.println("shortname==" + department.getSHORTNAME());
            System.out.println("ename==" + department.getENAME());
            System.out.println("email==" + department.getEMAIL());
            System.out.println("telephone==" + department.getTELEPHONE());
            System.out.println("createuser==" + department.getCREATEUSER());
            System.out.println("siteid==" + department.getSITEID());
            System.out.println("createdate==" + department.getCREATEDATE());
            System.out.println("lastupdate==" + department.getLASTUPDATE());
             */

            dept_error = departmentMapper.insert(department);
            if (dept_error==0) {
                errorMessage.setErrcode(-2);
                errorMessage.setErrmsg("创建组织架构记录部门信息出现错误");
                errorMessage.setModelname("创建组织架构");
            } else {
                errorMessage.setErrcode(0);
                errorMessage.setErrmsg("创建组织架构记录部门信息成功");
                errorMessage.setModelname("创建组织架构");
            }
        } else {
            errorMessage.setErrcode(-3);
            errorMessage.setErrmsg("创建组织架构记录出现错误");
            errorMessage.setModelname("创建组织架构");
        }

        Map result = new HashMap();
        result.put("organization",organization);
        result.put("error",errorMessage);

        return result;
    }

    @Transactional
    public Map updateOrganizationAndDept(Department department,int siteid,String username,int usertype,int deptid) {
        int org_error = 0;
        int dept_error = 0;
        ErrorMessage errorMessage = new ErrorMessage();
        Timestamp thedatetime = new Timestamp(System.currentTimeMillis());

        Organization organization = new Organization();
        organization.setID(department.getORGID());
        organization.setNAME(department.getCNAME());
        organization.setENAME(department.getENAME());
        organization.setORGCODE(department.getDepartcode());
        organization.setUPDATEUSER(username);
        organization.setLASTUPDATE(thedatetime);
        org_error = organizationMapper.updateByPrimaryKeySelective(organization);

        if (org_error>0) {
            dept_error = departmentMapper.updateDeptinfo(department);
            if (dept_error==0) {
                errorMessage.setErrcode(-2);
                errorMessage.setErrmsg("创建组织架构记录部门信息出现错误");
                errorMessage.setModelname("创建组织架构");
            } else {
                errorMessage.setErrcode(0);
                errorMessage.setErrmsg("创建组织架构记录部门信息成功");
                errorMessage.setModelname("创建组织架构");
            }
        } else {
            errorMessage.setErrcode(-3);
            errorMessage.setErrmsg("创建组织架构记录出现错误");
            errorMessage.setModelname("创建组织架构");
        }

        Map result = new HashMap();
        result.put("organization",organization);
        result.put("error",errorMessage);

        return result;
    }

    private List<BigDecimal> getCompanylistByOrgid(BigDecimal siteid,BigDecimal orgid) {
        List<Companyinfo> companyinfos = null;
        List<Department> departments = null;
        List<BigDecimal> result = null;
        Map params = new HashMap();
        BigDecimal t_orgid = orgid;
        while (t_orgid.intValue()!=-1) {
            BigDecimal compid = null;
            params.put("SITEID",siteid);
            params.put("ORGID",t_orgid);
            companyinfos=companyinfoMapper.getCompanyInfosByOrgid(params);
            System.out.println("company size==" + companyinfos.size());
            //如果当前ORGID可以找到公司的COMPID则退出该循环，返回找到的公司列表
            if (companyinfos.size()>0)
                compid = companyinfos.get(0).getID();
            else {
                departments=departmentMapper.getDepartmentsByOrgid(params);
                if (departments!=null) compid = departments.get(0).getCOMPANYID();
            }

            if (compid!=null) {
                result = new ArrayList();
                result.add(compid);
                break;
            } else {
                //如果返回的公司列表为空或者返回的公司列表不为空但是COMPID为空，则找到ORGID的父ID，再次查找公司信息，看能否找到COMPID
                Organization organization = organizationMapper.selectByPrimaryKey(orgid);
                t_orgid = organization.getPARENT();
            }
        }

        return result;
    }

    public List<Organization> getFirstLevelOrg(BigDecimal customer,BigDecimal rootOrgid) {
        Map params = new HashMap();
        params.put("CUSTOMERID",customer);
        params.put("PARENT",rootOrgid);
        return organizationMapper.getFirstLevelOrgnizations(params);
    }

    public Organization getRootOrganization(BigDecimal siteid) {
        return organizationMapper.getRootOrgnization(siteid);
    }

    public List<Organization> getOrgByParant(BigDecimal customer,BigDecimal parentid) {
        Map params = new HashMap();
        params.put("CUSTOMERID",customer);
        params.put("PARENT",parentid);
        return organizationMapper.getOrgByParant(params);
    }

    public List<Organization> getUpOrgsByID(BigDecimal customer,BigDecimal parentid) {
        Organization organization = new Organization();
        List<Organization> organizations = new ArrayList();

        do{
            organization = organizationMapper.selectByPrimaryKey(parentid);
            if (organization!=null && parentid.intValue()>0) {
                organizations.add(organization);
                parentid = organization.getPARENT();
            }
        }while (parentid.intValue() > 0);

        return organizations;
    }

    public String getTreeDataForOrg(BigDecimal customerid) {
        List<Organization> organizations = organizationMapper.getAllOrgnizations(customerid);
        int subnodenum = 1;                                              //判断当前节点是否有子节点
        int subnodenumOfParentNode = 0;
        StringBuffer buf = new StringBuffer();                        //存储生成的菜单树
        if (organizations.size()>0) {
            buf.append("[");
            List<Node> nodes = new ArrayList();
            //System.out.println("size==" + organizations.size());
            nodes.add(new Node(organizations.get(organizations.size() - 1).getID().intValue(), 0));
            while (nodes.size() > 0) {
                Node node = nodes.get(0);
                //System.out.println(node.getId() + "==" + node.getPid() + "==" + node.getSubnodenum());
                //从待处理节点队列中移走被处理的节点
                nodes.remove(0);

                int level = node.getLevel();
                int currentNodeIndex = 0;
                int currentNode_ParentNodeIndex = 0;

                //获取当前正在处理的节点
                for (int ii = 0; ii < organizations.size(); ii++) {
                    if (organizations.get(ii).getID().intValue() == node.getPid()) {
                        currentNodeIndex = ii;
                        break;
                    }
                }

                //获取当前节点的父节点
                for (int ii = 0; ii < organizations.size(); ii++) {
                    if (organizations.get(ii).getID().intValue() == organizations.get(currentNodeIndex).getPARENT().intValue()) {
                        currentNode_ParentNodeIndex = ii;
                        break;
                    }
                }

                //寻找该节点的子节点
                boolean exist_subnode_flag = false;
                subnodenum = 0;
                for (int ii = 0; ii < organizations.size(); ii++) {
                    Organization organization = organizations.get(ii);
                    if (organization.getPARENT().intValue() == node.getPid()) {
                        if (exist_subnode_flag == false) {
                            exist_subnode_flag = true;
                            level = level + 1;
                        }
                        subnodenum = subnodenum + 1;
                        nodes.add(0, new Node(organization.getID().intValue(), level));
                    }
                }

                //设置当前节点子节点的数量
                organizations.get(currentNodeIndex).setSubnodenum(BigDecimal.valueOf(subnodenum));

                //当前节点的父节点的子节点数量减1
                if (currentNode_ParentNodeIndex > 0) {
                    subnodenumOfParentNode = organizations.get(currentNode_ParentNodeIndex).getSubnodenum().intValue() - 1;
                    //修改当前节点父节点未处理的子节点的数量
                    organizations.get(currentNode_ParentNodeIndex).setSubnodenum(BigDecimal.valueOf(subnodenumOfParentNode));
                    //处理当前节点没有子节点的情况
                    if (subnodenum == 0) {
                        if (subnodenumOfParentNode == 0) {
                            buf.append("{title:'" + organizations.get(currentNodeIndex).getNAME() + "',id:" + organizations.get(currentNodeIndex).getID() + "}\r");
                            //寻找当前节点向上的路径上子节点数不是零的节点
                            int tnum = 0;
                            int tlevel = 0;
                            while (tnum == 0 && organizations.get(currentNodeIndex).getPARENT().intValue() > 0) {
                                for (int i = 0; i < organizations.size(); i++) {
                                    if (organizations.get(i).getID().intValue() == organizations.get(currentNodeIndex).getPARENT().intValue()) {
                                        tnum = organizations.get(i).getSubnodenum().intValue();
                                        if (tnum == 0) {
                                            tlevel = tlevel + 1;
                                            currentNodeIndex = i;
                                            break;
                                        }
                                    }
                                }
                            }

                            //从当前节点到根节点的路径上查找到第一个未处理子节点数不为零的节点，输出相应深度个数的“]}”字符串
                            for (int i = 0; i < tlevel - 1; i++) buf.append("]}\r");
                            buf.append("]},\r");
                        } else
                            buf.append("{title:'" + organizations.get(currentNodeIndex).getNAME() + "',id:" + organizations.get(currentNodeIndex).getID() + "},\r");
                    } else
                        buf.append("{title:'" + organizations.get(currentNodeIndex).getNAME() + "',id:" + organizations.get(currentNodeIndex).getID() + ",children:[\r");
                } else
                    buf.append("{title:'" + organizations.get(currentNodeIndex).getNAME() + "',id:" + organizations.get(currentNodeIndex).getID() + ",spread: true," + "children:[\r");
            }

            //去掉字符串最后一个多余的“,”字符，增加json的关闭字符“];”
            int posi = buf.lastIndexOf(",");
            if (posi > -1) {
                buf.delete(posi, buf.length());
                buf.append("\r]");
            }
        } else {
            buf.append("[]");
        }

        //FileUtil.writeFile(buf,"c:\\data\\11111.txt");

        return buf.toString();
    }

    //获取某个客户的所有组织架构节点信息
    public List<Organization> getOrganizationByCustomerid(BigDecimal customerid) {
        List<Organization> organizations = organizationMapper.getAllOrgnizations(customerid);
        List<Integer> nodes = new ArrayList();
        nodes.add(organizations.get(0).getID().intValue());
        int level = 0;
        while (nodes.size()>0) {
            int pid = nodes.get(nodes.size()-1);
            nodes.remove(nodes.size()-1);
            boolean add_level_val_flag = false;
            for(int ii=0;ii<organizations.size();ii++) {
                Organization organization = organizations.get(ii);
                if (organization.getPARENT().intValue()==pid) {
                    nodes.add(organization.getID().intValue());

                    //判断层级增加的标识位是否为真，为真表示增加过了层级值。
                    if (add_level_val_flag == false) {
                        level = level + 1;
                        add_level_val_flag = true;
                    }

                    //打印前置的空格
                    for(int jj=0;jj<level;jj++) {
                        System.out.print("--");
                    }
                    System.out.println(organization.getNAME());
                }
            }
        }

        return organizations;
    }

    //获取某个客户的所有组织架构节点信息，形成下拉列表的选项，以字符串的形式返回
    public String getOrgOptionsByCustomerid(BigDecimal customerid,int selectedID) {
        List<Organization> organizations = organizationMapper.getAllOrgnizations(customerid);
        List<Node> nodes = new ArrayList();
        StringBuffer buf = new StringBuffer();

        if (organizations.size()>0) {
            nodes.add(new Node(organizations.get(organizations.size() - 1).getID().intValue(), 0));

            while (nodes.size() > 0) {
                Node node = nodes.get(0);

                //设置前置空格的数量
                String whitespace = "";
                int level = node.getLevel();
                for (int ii = 0; ii < level; ii++) {
                    whitespace = whitespace + "&nbsp;&nbsp;";
                }

                //处理当前节点，建立option的输出项
                for (int ii = 0; ii < organizations.size(); ii++) {
                    if (organizations.get(ii).getID().intValue() == node.getPid()) {
                        if (organizations.get(ii).getID().intValue() == selectedID)
                            buf.append("<option value=" + organizations.get(ii).getID() + " selected>" + whitespace + organizations.get(ii).getNAME() + "</option>\r\n");
                        else
                            buf.append("<option value=" + organizations.get(ii).getID() + ">" + whitespace + organizations.get(ii).getNAME() + "</option>\r\n");
                        break;
                    }
                }

                //从待处理节点列表中删除该节点
                nodes.remove(0);

                //寻找该节点的子节点
                boolean exist_subnode_flag = false;
                for (int ii = 0; ii < organizations.size(); ii++) {
                    Organization organization = organizations.get(ii);
                    if (organization.getPARENT().intValue() == node.getPid()) {
                        if (exist_subnode_flag == false) {
                            exist_subnode_flag = true;
                            level = level + 1;
                        }
                        nodes.add(0, new Node(organization.getID().intValue(), level));
                    }
                }
            }
        }

        return buf.toString();
    }

    public Companyinfo getACompany(BigDecimal companyid) {
        return companyinfoMapper.selectByPrimaryKey(companyid);
    }

    public List<Companyinfo> getCompaniesByOrgid(BigDecimal siteid,BigDecimal orgid) {
        Map params = new HashMap();
        params.put("SITEID",siteid);
        params.put("ORGID",orgid);
        return companyinfoMapper.getCompanyInfosByOrgid(params);
    }

    public List<Companyinfo> getMainCompaniesByOrgid(BigDecimal siteid,BigDecimal orgid) {
        Map params = new HashMap();
        params.put("SITEID",siteid);
        params.put("ORGID",orgid);
        return companyinfoMapper.getMainCompanyInfosByOrgid(params);
    }

    public List<Companyinfo> getAliasCompaniesByOrgid(BigDecimal siteid,BigDecimal orgid) {
        Map params = new HashMap();
        params.put("SITEID",siteid);
        params.put("ORGID",orgid);
        return companyinfoMapper.getAliasCompanyInfosByOrgid(params);
    }

    public Department getADepartment(BigDecimal deptid) {
        return departmentMapper.selectByPrimaryKey(deptid);
    }

    public List<Department> getADepartmentByOrgid(BigDecimal siteid,BigDecimal orgid) {
        Map params = new HashMap();
        params.put("SITEID",siteid);
        params.put("ORGID",orgid);
        return departmentMapper.getDepartmentsByOrgid(params);
    }

    public Organization getAOrganization(BigDecimal orgid) {
        return organizationMapper.selectByPrimaryKey(orgid);
    }

    @Transactional
    public int deletOrganization(BigDecimal orgid) {
        int del_company_num = companyinfoMapper.deleteByOrgID(orgid);
        int del_dept_num = departmentMapper.deleteByOrgID(orgid);
        int del_org_num = organizationMapper.deleteByPrimaryKey(orgid);
        return del_company_num + del_dept_num + del_org_num;
    }

    //获取组织架构下某个节点的所有子节点
    public List<Organization> getSubOrgtreeByParant(BigDecimal customer,BigDecimal orgid) {
        Map params = new HashMap();
        params.put("CUSTOMERID",customer);
        params.put("ID",orgid);
        List<Organization> organizations = organizationMapper.getSubOrgtreeByParant(params);

        List<Organization> results = new ArrayList();
        List<Node> nodes = new ArrayList();
        nodes.add(new Node(orgid.intValue(),0));
        StringBuffer buf = new StringBuffer();
        while (nodes.size()>0) {
            Node node = nodes.get(0);

            //设置前置空格的数量
            String whitespace="";
            int level = node.getLevel();
            for(int ii=0; ii<level; ii++) {
                whitespace = whitespace + "&nbsp;&nbsp;";
            }

            //处理当前节点，建立option的输出项
            for(int ii=0; ii<organizations.size();ii++) {
                if (organizations.get(ii).getID().intValue() == node.getPid()) {
                    results.add(organizations.get(ii));
                    break;
                }
            }

            //从待处理节点列表中删除该节点
            nodes.remove(0);

            //寻找该节点的子节点
            boolean exist_subnode_flag = false;
            for(int ii=0;ii<organizations.size();ii++) {
                Organization organization = organizations.get(ii);
                if (organization.getPARENT().intValue()==node.getPid()) {
                    if (exist_subnode_flag == false){
                        exist_subnode_flag = true;
                        level = level + 1;
                    }
                    nodes.add(0,new Node(organization.getID().intValue(),level));
                }
            }
        }

        return results;
    }
}
