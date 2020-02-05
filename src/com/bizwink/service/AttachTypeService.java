package com.bizwink.service;

import com.bizwink.po.AttachType;
import com.bizwink.persistence.AttachTypeMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-6-6
 * Time: 下午6:27
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AttachTypeService {
    @Autowired
    private AttachTypeMapper attachTypeMapper;
    private static Logger logger = Logger.getLogger(AttachTypeService.class.getName());

    @Transactional
    public int createAttachType(AttachType attachType,List attachTypePartList) {
        int code = attachTypeMapper.createAttachType(attachType);

        //插入附表信息
        AttachType a = null;
        for (int i=0; i<attachTypePartList.size(); i++) {
            a = (AttachType)attachTypePartList.get(i);
            System.out.println("cname=" + a.getCname() + "===" + a.getEname() + "===" + a.getCltype() + "=="+a.getEditor() + "==" + a.getSiteid());
            a.setClassid(attachType.getId());
            code = attachTypeMapper.createAttachTypePart(a);
            System.out.println("part code=" + code);
        }

        return code;
    }
}
