package com.bizwink.service;

import com.bizwink.persistence.CmClassMapper;
import com.bizwink.persistence.CmClassapplyMapper;
import com.bizwink.persistence.MaterialcodeMapper;
import com.bizwink.persistence.WzClassMapper;
import com.bizwink.po.CmClass;
import com.bizwink.po.Materialcode;
import com.bizwink.po.WzClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by petersong on 16-8-17.
 */
@Service
public class MaterialcodeService {
    @Autowired
    private MaterialcodeMapper materialcodeMapper;

    @Autowired
    private CmClassMapper cmClassMapper;

    @Autowired
    private CmClassapplyMapper cmClassapplyMapper;

    @Autowired
    private WzClassMapper wzClassMapper;

    public List<Materialcode> getMaterialcodesBySiteid(BigDecimal siteid) {
        return materialcodeMapper.getMaterialcodesBySiteid(siteid);
    }

    public String getMaterialcodeJsonsBySiteid(BigDecimal siteid) {
        List<Materialcode> materialcodes = materialcodeMapper.getMaterialcodesBySiteid(siteid);
        String buf = null;
        if (materialcodes.size() > 0) {
            buf = "[\r\n";
            for(int ii=0;ii<materialcodes.size();ii++){
                Materialcode materialcode = (Materialcode)materialcodes.get(ii);
                if (ii<materialcodes.size()-1) {
                    if (materialcode.getPID().intValue()==0)
                        buf = buf + "{id:" +materialcode.getID().intValue() + ",pId:"+ materialcode.getPID().intValue() + ",name:\"" + materialcode.getNAME() +"\",url:\"\"" + ",open:\"true\"" +"},\r\n";
                    else
                        buf = buf + "{id:" +materialcode.getID().intValue() + ",pId:"+ materialcode.getPID().intValue() + ",name:\"" + materialcode.getNAME() +"\"},\r\n";
                }else{
                    if (materialcode.getPID().intValue()==0)
                        buf = buf + "{id:" +materialcode.getID().intValue() + ",pId:"+ materialcode.getPID().intValue() + ",name:\"" + materialcode.getNAME() +"\",url:\"#\"" + ",open:\"true\"" +"}\r\n";
                    else
                        buf = buf + "{id:" +materialcode.getID().intValue() + ",pId:"+ materialcode.getPID().intValue() + ",name:\"" + materialcode.getNAME() +"\"}\r\n";
                }
            }
            buf = buf + "]";
        }
        return buf;
    }

    public int insertWzClassInfo(WzClass classinfo) {
        return wzClassMapper.insert(classinfo);
    }
}
