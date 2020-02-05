package com.bizwink.ItfSchedule;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接口调度
 * 
 * @author huasen.xu 2013-11-16 create
 */
public class ItfSchedule extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//一个接口一个Timer
	private Map<String, Timer> mapTimer = new HashMap<String, Timer>();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String itfType  = request.getParameter("itfType");   //接口类型
		String actType = request.getParameter("actType"); //操作类型
		//TODO 新加接口，别忘了发到此数组内
		String[] itfTypeAll = new String[]{ItfConsts.TYPE_FP, ItfConsts.TYPE_FK, ItfConsts.TYPE_SP,
				ItfConsts.TYPE_HT, ItfConsts.TYPE_HTGB, ItfConsts.TYPE_JHD,
				ItfConsts.TYPE_PR, ItfConsts.TYPE_PO, ItfConsts.TYPE_JS, ItfConsts.TYPE_GYSPG,
				ItfConsts.TYPE_PRODCLASS, ItfConsts.TYPE_PROD, ItfConsts.TYPE_PRODFEATURE,
				ItfConsts.TYPE_INTSUPPMAIN, ItfConsts.TYPE_EXTSUPPMAIN, ItfConsts.TYPE_SUPPPRODCLASS};

		if(ItfConsts.TYPE_ALL.equals(itfType)) { //全部
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
				request.setAttribute("info"+type, new String(request.getParameter("info"+type).getBytes("iso-8859-1"), "utf-8"));
			}
			actTimer(request, itfType, actType);
			request.setAttribute("info"+ItfConsts.TYPE_ALL, "");
		}
		request.getRequestDispatcher("/itfSchedule.jsp").forward(request, response);
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
			Timer timer = mapTimer.get(itfType);
			if("start".equals(actType)) {
				long period = getPeriod(itfType);
				if (timer == null) {
					timer = new Timer();
					timer.schedule((TimerTask)Class.forName(ItfConsts.PATH_TASK+"ItfTimerTask"+itfType).newInstance(), 0, period);
					mapTimer.put(itfType, timer);
				}
				//1分钟：60*1000
				if(period >= 60000)
					request.setAttribute("info"+itfType, "定时器已启动！运行频次："+ period/60000 +"分钟一次。");
				else
					request.setAttribute("info"+itfType, "定时器已启动！运行频次："+ period/1000 +"秒一次。");
			} else if ("restart".equals(actType)) {
				long period = getPeriod(itfType);
				if (timer == null) {
					timer = new Timer();
					timer.schedule((TimerTask)Class.forName(ItfConsts.PATH_TASK+itfType+"Tasker").newInstance(), 0, period);
					//1分钟：60*1000
					if(period >= 60000)
						request.setAttribute("info"+itfType, "定时器已启动！运行频次："+ period/60000 +"分钟一次。");
					else
						request.setAttribute("info"+itfType, "定时器已启动！运行频次："+ period/1000 +"秒一次。");
				} else {
					timer.cancel();
					timer = null;
					timer = new Timer();
					timer.schedule((TimerTask)Class.forName(ItfConsts.PATH_TASK+itfType+"Tasker").newInstance(), 0, period);
					//1分钟：60*1000
					if(period >= 60000)
						request.setAttribute("info"+itfType, "定时器已重启！运行频次："+ period/60000 +"分钟一次。");
					else
						request.setAttribute("info"+itfType, "定时器已重启！运行频次："+ period/1000 +"秒一次。");
				}
			} else if ("stop".equals(actType)) {
				if (timer != null) {
					timer.cancel();
					timer = null;
				}
				request.setAttribute("info"+itfType, "定时器已停止！");
			} else {
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
			period = ConfigUtil.getPeriod(itfType);
		} catch(Exception e) {
			period = 10*60*1000; //默认10分钟
		}
		return period;
	}

}
