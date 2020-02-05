package com.bizwink.filter;

import com.bizwink.cms.security.Auth;
import com.bizwink.cms.security.AuthPeer;
import com.bizwink.cms.security.IAuthManager;
import com.bizwink.cms.security.UnauthedException;
import com.bizwink.cms.util.SessionUtil;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 15-4-1.
 */
public class ActionFilter implements Filter {
    private FilterConfig filterConfig;

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        Map paras = request.getParameterMap();
        IAuthManager authMgr = AuthPeer.getInstance();

        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        HttpSession session = httpServletRequest.getSession();
        Auth authToken = SessionUtil.getUserAuthorization(httpServletRequest, httpServletResponse, session);
        String userid = null;
        if (authToken != null) {
            userid = authToken.getUserID();
            try {
                authMgr.updateUserLoginInfo(userid,"");
            } catch (UnauthedException exp) {
                System.out.println(exp.getMessage());
            }
        }

        //String url1 = httpServletRequest.getRequestURI();

        //获得所有请求参数名
        Enumeration params = httpServletRequest.getParameterNames();
        String sql = "";
        while (params.hasMoreElements()) {
            sql = "";

            //得到参数名
            String name = params.nextElement().toString();

            //得到参数对应值
            if (!name.equals("content")) {
                String[] value = httpServletRequest.getParameterValues(name);
                for (int i = 0; i < value.length; i++) {
                    sql = sql + value[i];
                }

                //有sql关键字，跳转到error.html
                if (sqlValidate(sql)) {
                    throw new IOException("您发送请求中的参数中含有非法字符");
                }
            }
        }

        //Iterator it = paras.entrySet().iterator();
        //while(it.hasNext()){
        //    Map.Entry m=(Map.Entry)it.next();
        //    System.out.println(m.getKey() + ":" + m.getValue());
        //}

        //System.out.println("Demo1过滤前");
        //System.out.println(filterConfig.getInitParameter("param1"));
        //System.out.println("Demo1过滤后");

        chain.doFilter(request, response);//放行。让其走到下个链或目标资源中
    }

    //效验
    protected static boolean sqlValidate(String str) {
        str = str.toLowerCase();//统一转为小写
        String badStr = "exec|execute|insert|select|delete|update|count|drop|chr|mid|master|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|like|and|exec|execute|insert|create|drop|" +
                "table|from|grant|use|group_concat|column_name|information_schema.columns|table_schema|"+
                "union|where|select|delete|update|order|by|count|chr|mid|master|truncate|char|declare";
                //过滤掉的sql关键字，可以手动添加
        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
            if (str.equals(badStrs[i])) {
                return true;
            }
        }
        return false;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        //System.out.println("初始化了");
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        //System.out.println("销毁了");
    }
}
