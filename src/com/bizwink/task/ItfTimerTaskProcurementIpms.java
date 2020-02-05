package com.bizwink.task;

import java.util.Set;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

//import com.edarong.butt.domain.PlanIpms;
//import com.edarong.butt.service.PlanNewIpmsService;
//import com.edarong.butt.service.ProcurementIpmsService;
//import com.edarong.butt.tools.PropertiesUtil;
//import com.edarong.butt.tools.SpringContextUtil;

@Component
public class ItfTimerTaskProcurementIpms extends TimerTask {
	private static final Logger logger = Logger.getLogger(ItfTimerTaskProcurementIpms.class);
	@Override
	public void run() {
/*
		try {
			PlanNewIpmsService planNewIpmsService=(PlanNewIpmsService)SpringContextUtil.getBean("planNewIpmsService");
			ProcurementIpmsService procurementIpmsService=(ProcurementIpmsService)SpringContextUtil.getBean("procurementIpmsService");
			PropertiesUtil propertiesUtil=(PropertiesUtil)SpringContextUtil.getBean("propertiesUtil");
			Set<PlanIpms> result = null;
	 		Integer pageCount = planNewIpmsService.getTotalPageCountService(propertiesUtil.getPageSize());// ��ҳ��
			if (null != pageCount) {
	 			for (int pageindex = 1; pageindex <= pageCount; pageindex++) {// ��ҳȡ���
	 				    logger.info("IPMS�ɹ�����(PR)�ϴ�---------��"+pageCount+"ҳ,��"+pageindex+"ҳ");
						result = planNewIpmsService.getPlanIpmsService(pageindex,propertiesUtil.getPageSize());
						procurementIpmsService.generateStockOrder(result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("IPMS�ɹ�����(PR)�ϴ�---------�쳣:"+e.getMessage());
		}
*/
	}

}
