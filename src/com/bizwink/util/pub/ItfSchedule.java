package com.bizwink.util.pub;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.edarong.butt.service.impl.SuppServiceImpl;
//import com.edarong.persistence.hibernate.JDBCHelper;
//import com.edarong.sys.MD5Util;

/**
 * 定时任务调度程序
 */
public class ItfSchedule extends HttpServlet {
    private static final long serialVersionUID = 1L;

    //一个定时任务一个Timer
    private static Map<String, Timer> mapTimer = new HashMap<String, Timer>();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //是否要求用户登录校验(no 不需要；空或yes 需要)
/*
        final String needLogin =  ItfConfigUtil.getProperty("login.need");
        if(needLogin == null || "yes".equals(needLogin.toLowerCase())) {
            //1：来自用户校验页面、2：来自通过校验后的接口调度页面
            final String fromLogin =  request.getParameter("fromLogin");
            if(fromLogin == null || (!"1".equals(fromLogin) && !"2".equals(fromLogin))) {
                request.getRequestDispatcher("/itfScheduleLogin.jsp").forward(request, response);
                return;
            } else if(!"2".equals(fromLogin)) {
                final String userName =  request.getParameter("userName");
                String pwd =  request.getParameter("pwd");
                String roles =  ItfConfigUtil.getProperty("user.roles");
                String msg = null;
                String sql = null;

                if(roles == null || roles.trim().length() == 0) {
                    msg = "用户名或密码错误！";
                    sql = "select to_char(u.c_ifallow) from t_user u where u.c_username='"+ userName
                            + "' and u.c_password='"+ MD5Util.edarongPass(pwd) +"'";
                } else {
                    msg = "用户名或密码错误，或者用户角色不是指定的角色（"+ roles +"）！";
                    if(!roles.endsWith(","))
                        roles += ",";
                    sql = "select to_char(u.c_ifallow) from t_user u where u.c_username='"+ userName
                            + "' and u.c_password='"+ MD5Util.edarongPass(pwd)
                            + "' and exists(select 1 from ralasafe_user_role r where r.user_id=u.c_id and '"+ roles +"' like r.role_code||',%')";
                }
                //是否允许登录(1: 允许 0：不允许)
                Object ifAllow = JDBCHelper.queryReturnFristValue(sql);
                if(!"1".equals(ifAllow)) {
                    request.setAttribute("userName", userName);
                    request.setAttribute("pwd", pwd);
                    request.setAttribute("msg", msg);
                    request.getRequestDispatcher("/itfScheduleLogin.jsp").forward(request, response);
                    return;
                }
            }
        }
*/
        String itfType  = request.getParameter("itfType");   //接口类型
        String actType = request.getParameter("actType"); //操作类型
        if(actType == null || actType.length() == 0)
            actType = "show";

        //TODO 新加接口，别忘了加到此数组内
        String[] itfTypeAll = new String[] {
                ItfConsts.TYPE_FP,
                ItfConsts.TYPE_FK,
                ItfConsts.TYPE_SP,
                ItfConsts.TYPE_HT,
                ItfConsts.TYPE_HTGB,
                ItfConsts.TYPE_JHD,
                ItfConsts.TYPE_PR,
                ItfConsts.TYPE_PO,
                ItfConsts.TYPE_JS,
                ItfConsts.TYPE_GYSPG,
                ItfConsts.TYPE_PR_Imps,
                ItfConsts.TYPE_Grade,
                ItfConsts.TYPE_PurchaseOrder,
                ItfConsts.TYPE_PRODCLASS,
                ItfConsts.TYPE_PROD,
                ItfConsts.TYPE_PRODFEATURE,
                ItfConsts.TYPE_SUPPMAIN,
                ItfConsts.TYPE_SUPPPRODCLASS,
                ItfConsts.TYPE_SUPP_YNWJY,
                ItfConsts.TYPE_SUPP_ZHPF,
                ItfConsts.TYPE_EXCHRATE
        };

        if("show".equals(actType)) {
            Timer timer = null;
            long period = 0L;
            //在指定的时间开始按设定频率定时执行的接口
            boolean isFixType = false;
            for (String type : itfTypeAll) {
                //在指定的时间开始按设定频率定时执行的接口
                if(type.startsWith("#")) {
                    type = type.substring(1);
                    isFixType = true;
                } else {
                    isFixType = false;
                }
                timer = mapTimer.get(type);
                if(timer == null) {
                    request.setAttribute("info"+type, "定时器未启动！");
                } else {
                    if(isFixType) {
                        try {
                            Object[] ret = getTimePoint(type);
                            request.setAttribute("info"+type, "定时器已启动！"+ ret[2]);
                        } catch (Exception e) {
                            request.setAttribute("info"+type, e.getMessage());
                        }
                    } else {
                        period = getPeriod(type);
                        if(period >= 3600000)     //1小时：60*60*1000
                            request.setAttribute("info"+type, "定时器已启动！运行频次："+ period/3600000 +"小时一次。");
                        else if(period >= 60000) //1分钟：60*1000
                            request.setAttribute("info"+type, "定时器已启动！运行频次："+ period/60000 +"分钟一次。");
                        else
                            request.setAttribute("info"+type, "定时器已启动！运行频次："+ period/1000 +"秒一次。");
                    }
                }
            }
        } else if(ItfConsts.TYPE_ALL.equals(itfType)) { //全部
            for (String type : itfTypeAll) {
                actTimer(request, type, actType);
            }
            if("start".equals(actType))
                request.setAttribute("info"+ItfConsts.TYPE_ALL, "启动完成！");
            else if("restart".equals(actType))
                request.setAttribute("info"+ItfConsts.TYPE_ALL, "重启完成！");
            else if("stop".equals(actType))
                request.setAttribute("info"+ItfConsts.TYPE_ALL, "停止完成！");
            else
                request.setAttribute("info"+ItfConsts.TYPE_ALL, "未知操作（"+ actType +"）！");
        } else {
            for (String type : itfTypeAll) {
                if(type.startsWith("#"))
                    type = type.substring(1);
                request.setAttribute("info"+type, new String(request.getParameter("info"+type).getBytes("iso-8859-1"), "utf-8"));
            }
            actTimer(request, itfType, actType);
            request.setAttribute("info"+ItfConsts.TYPE_ALL, "");
        }
        //1：来自用户校验页面、2：来自通过校验后的接口调度页面
        request.setAttribute("fromLogin", "2");
        request.getRequestDispatcher("/itfSchedule.jsp").forward(request, response);
    }

    public static void resetMonthTimer(String itfType, Date d) throws Exception {
        Timer timer = mapTimer.get(itfType);
        if(timer == null) {
            timer = new Timer();
        } else {
            timer.cancel();
            timer = null;
            timer = new Timer();
        }
        timer.scheduleAtFixedRate((TimerTask)Class.forName(ItfConsts.PATH_TASK+"ItfTimerTask"+itfType).newInstance(),
                d, 2419200000L); //28天：28 * 24 * 60*60*1000
        mapTimer.put(itfType, timer);
    }

    /**
     * 设定定时器
     *
     * @param request
     * @param itfType
     * @param actType
     */
    private void actTimer(HttpServletRequest request, String itfType, String actType) {
        try {
            //在指定的时间开始按设定频率定时执行的接口
            boolean isFixType = false;
            if(itfType.startsWith("#")) {
                itfType = itfType.substring(1);
                isFixType = true;
            }
            Timer timer = mapTimer.get(itfType);
            if("start".equals(actType)) {
                if(isFixType) {
                    try {
                        Object[] ret = getTimePoint(itfType);
                        if (timer == null) {
                            timer = new Timer();
                           // timer.scheduleAtFixedRate((TimerTask)Class.forName(ItfConsts.PATH_TASK+"ItfTimerTask"+itfType).newInstance(),
                           //         (Date) ret[0], (long) ret[1]);
                            mapTimer.put(itfType, timer);
                        }
                        request.setAttribute("info"+itfType, "定时器已启动！"+ ret[2]);
                    } catch (Exception e) {
                        request.setAttribute("info"+itfType, e.getMessage());
                    }
                } else {
                    long period = getPeriod(itfType);
                    if (timer == null) {
                        timer = new Timer();
                        timer.schedule((TimerTask)Class.forName(ItfConsts.PATH_TASK+"ItfTimerTask"+itfType).newInstance(), 0, period);
                        mapTimer.put(itfType, timer);
                    }
                    if(period >= 3600000)     //1小时：60*60*1000
                        request.setAttribute("info"+itfType, "定时器已启动！运行频次："+ period/3600000 +"小时一次。");
                    else if(period >= 60000) //1分钟：60*1000
                        request.setAttribute("info"+itfType, "定时器已启动！运行频次："+ period/60000 +"分钟一次。");
                    else
                        request.setAttribute("info"+itfType, "定时器已启动！运行频次："+ period/1000 +"秒一次。");
                }
            } else if ("restart".equals(actType)) {
                long period = getPeriod(itfType);
                if (timer == null) {
                    timer = new Timer();
                    timer.schedule((TimerTask)Class.forName(ItfConsts.PATH_TASK+"ItfTimerTask"+itfType).newInstance(), 0, period);
                    if(period >= 3600000)     //1小时：60*60*1000
                        request.setAttribute("info"+itfType, "定时器已启动！运行频次："+ period/3600000 +"小时一次。");
                    else if(period >= 60000) //1分钟：60*1000
                        request.setAttribute("info"+itfType, "定时器已启动！运行频次："+ period/60000 +"分钟一次。");
                    else
                        request.setAttribute("info"+itfType, "定时器已启动！运行频次："+ period/1000 +"秒一次。");
                } else {
                    timer.cancel();
                    timer = null;
                    timer = new Timer();
                    timer.schedule((TimerTask)Class.forName(ItfConsts.PATH_TASK+"ItfTimerTask"+itfType).newInstance(), 0, period);
                    if(period >= 3600000)     //1小时：60*60*1000
                        request.setAttribute("info"+itfType, "定时器已重启！运行频次："+ period/3600000 +"小时一次。");
                    else if(period >= 60000) //1分钟：60*1000
                        request.setAttribute("info"+itfType, "定时器已重启！运行频次："+ period/60000 +"分钟一次。");
                    else
                        request.setAttribute("info"+itfType, "定时器已重启！运行频次："+ period/1000 +"秒一次。");
                }
                mapTimer.put(itfType, timer);
            } else if ("stop".equals(actType)) {
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                    mapTimer.remove(itfType);
                }
                request.setAttribute("info"+itfType, "定时器已停止！");
            }/* else if ("reread".equals(actType)) {
                if(ItfConsts.TYPE_SUPP_YNWJY_INFO.equals(itfType)) {
                    new SuppServiceImpl().syncSuppYnwjy();
                    request.setAttribute("info" +itfType, request.getAttribute("info" +itfType)+"已重新读取！");
                } else if (ItfConsts.TYPE_SUPP_ZHPF_INFO.equals(itfType)) {
                    new SuppServiceImpl().syncSuppZhpf();
                    request.setAttribute("info" +itfType, request.getAttribute("info" +itfType)+"已重新读取！");
                }
            }*/ else {
                request.setAttribute("info" +itfType, "未知操作（"+ actType +"）！");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取得接口定时频率
     *
     * @param itfType
     * @return long
     */
    private long getPeriod(String itfType) {
        long period = 0L;
        try {
            period = ItfConfigUtil.getPeriod(itfType);
        } catch(Exception e) {
            period = 10*60*1000; //默认10分钟
        }
        return period;
    }

    /**
     * 取得指定时间点定时执行接口的指定时间点
     *
     * @param itfType
     * @return Object[]{timePoint, period, msg}
     */
    private Object[] getTimePoint(String itfType) throws Exception {
        //格式：MM-dd HH:mm:ss
        String timePoint =  ItfConfigUtil.getProperty(itfType+ ".timepoint");
        if(timePoint == null || timePoint.length() != 14)
            throw new Exception("请在配置文件中正确设定"+ itfType +".timepoint参数的值。");

        //返回值{timePoint, period, msg}
        Object[] ret = new Object[3];
        //TODO 暂时支持每月某日某个时间 和 每日某个时间两种情况
        if(timePoint.startsWith("MM-dd ")) {
            //例如每天凌晨3点开始执行 -> MM-dd 03:00:00
            try {
                Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date())+timePoint.substring(5));
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                //如果当天时间点已在系统时间之前，则指定为下一天执行
                if(d.before(new Date())) {
                    c.add(Calendar.DATE, 1);
                }
                ret[0] = c.getTime();
                ret[1] = 2419200000L; //28天：28 * 24 * 60*60*1000
                ret[2] = "每天的"+ timePoint.substring(6) +"执行。";
            } catch(ParseException e) {
                throw new Exception("参数"+ itfType +".timepoint的值（"+ timePoint +"）格式不正确。");
            }
        } else if(timePoint.startsWith("MM-")) {
            //例如每月17号凌晨4点开始执行 -> MM-17 04:00:00
            try {
                Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                        new SimpleDateFormat("yyyy-MM").format(new Date())+timePoint.substring(2));
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                //如果当月时间点已在系统时间之前，则指定为下个月执行
                if(d.before(new Date())) {
                    c.add(Calendar.MONTH, 1);
                }
                ret[0] = c.getTime();
                ret[1] = 86400000L; //24小时：24 * 60*60*1000
                ret[2] = "每月"+ timePoint.substring(3, 5) +"日的"+ timePoint.substring(6) +"执行。";
            } catch(ParseException e) {
                throw new Exception("参数"+ itfType +".timepoint的值（"+ timePoint +"）格式不正确。");
            }
        }
        return ret;
    }

    /**
     * 取得每月某日某个时间定时执行的时间点
     *
     * @param itfType
     * @return Date
     * @throws Exception
     */
    public static Date getTimePointPerMonth(String itfType) throws Exception {
        //格式：MM-dd HH:mm:ss
        String timePoint =  ItfConfigUtil.getProperty(itfType+ ".timepoint");
        if(timePoint == null || timePoint.length() != 14)
            throw new Exception("请在配置文件中正确设定"+ itfType +".timepoint参数的值。");

        //例如每月17号凌晨4点开始执行 -> MM-17 04:00:00
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    new SimpleDateFormat("yyyy-MM").format(new Date())+timePoint.substring(2));
            return d;
        } catch(ParseException e) {
            throw new Exception("参数"+ itfType +".timepoint的值（"+ timePoint +"）格式不正确。");
        }
    }
}
