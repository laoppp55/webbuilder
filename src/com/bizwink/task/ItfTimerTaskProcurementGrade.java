package com.bizwink.task;

import java.util.Set;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

//import com.edarong.butt.domain.PurchaseOrderGrade;
//import com.edarong.butt.service.GradeUploadService;
//import com.edarong.butt.service.PurchaseOrderGradeService;
//import com.edarong.butt.tools.PropertiesUtil;
//import com.edarong.butt.tools.SpringContextUtil;

@Component
public class ItfTimerTaskProcurementGrade extends TimerTask {
	private static final Logger logger = Logger.getLogger(ItfTimerTaskProcurementGrade.class);
	@Override
	public void run() {
/*
		Set<PurchaseOrderGrade> result = null;
		try {
			PurchaseOrderGradeService purchaseOrderGradeService=(PurchaseOrderGradeService)SpringContextUtil.getBean("purchaseOrderGradeService");
			GradeUploadService gradeUploadService=(GradeUploadService)SpringContextUtil.getBean("gradeUploadService");
			PropertiesUtil propertiesUtil=(PropertiesUtil)SpringContextUtil.getBean("propertiesUtil");
			Integer pageCount = purchaseOrderGradeService
					.getTotalPurchaseOrderGradeService(propertiesUtil
							.getPageSize());// ?????
			if (null != pageCount) {
				for (int pageindex = 1; pageindex <= pageCount; pageindex++) {// ???????
					logger.info("IPMS??????????---------??"+pageCount+"?,??"+pageindex+"?");
					result = purchaseOrderGradeService
							.getPlaPurchaseOrderGradeService(pageindex,
									propertiesUtil.getPageSize());
					gradeUploadService.generateGrade(result);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("IPMS??????????--------??:"+e.getMessage());
		}
*/
	}

}
