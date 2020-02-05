package com.bizwink.task;

import java.util.Set;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

//import com.edarong.butt.domain.PurchaseOrder;
//import com.edarong.butt.service.ContractService;
//import com.edarong.butt.service.PurchaseOrderService;
//import com.edarong.butt.tools.PropertiesUtil;
//import com.edarong.butt.tools.SpringContextUtil;

@Component
public class ItfTimerTaskProcurementPurchaseOrder extends TimerTask {
	private static final Logger logger = Logger.getLogger(ItfTimerTaskProcurementPurchaseOrder.class);
	@Override
	public void run() {
/*
		Set<PurchaseOrder> result = null;
		try {
			PurchaseOrderService purchaseOrderService=(PurchaseOrderService)SpringContextUtil.getBean("purchaseOrderService");
			ContractService contractService=(ContractService)SpringContextUtil.getBean("contractService");
			PropertiesUtil propertiesUtil=(PropertiesUtil)SpringContextUtil.getBean("propertiesUtil");
			Integer pageCount = purchaseOrderService
					.getTotalPurchaseOrderService(propertiesUtil.getPageSize());// ��ҳ��
			if (null != pageCount) {
				for (int pageindex = 1; pageindex <= pageCount; pageindex++) {// ��ҳȡ���
					logger.info("IPMS��ͬ��Ϣ�ϴ�---------��"+pageCount+"ҳ,��"+pageindex+"ҳ");
					result = purchaseOrderService.getPurchaseOrderService(pageindex, propertiesUtil.getPageSize());
					contractService.upContractInfo(result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("IPMS��ͬ��Ϣ�ϴ�--------�쳣:"+e.getMessage());
		}
*/
	}

}
