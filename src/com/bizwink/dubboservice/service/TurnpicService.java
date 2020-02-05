package com.bizwink.dubboservice.service;

import com.bizwink.po.Turpic;

import java.util.List;

/**
 * Created by petersong on 16-12-5.
 */
public interface TurnpicService {
    int CreateTurnpics(List<Turpic> turnpicList);

    int CreateTurnpic(Turpic turnpic);
}
