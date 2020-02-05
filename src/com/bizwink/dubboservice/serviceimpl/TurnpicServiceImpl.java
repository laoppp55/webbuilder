package com.bizwink.dubboservice.serviceimpl;

import com.bizwink.po.Turpic;
import com.bizwink.dubboservice.service.TurnpicService;
import com.bizwink.persistence.TurpicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by petersong on 16-12-5.
 */
@Service
public class TurnpicServiceImpl implements TurnpicService{
    @Autowired
    private TurpicMapper turpicMapper;

    public int CreateTurnpics(List<Turpic> turnpicList){

        return 0;
    }

    public int CreateTurnpic(Turpic turnpic) {
        return turpicMapper.insert(turnpic);
    }

}
