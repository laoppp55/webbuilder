package com.bizwink.dubboservice.serviceimpl;

import com.bizwink.dubboservice.service.MessageService;
import com.bizwink.persistence.MessageMapper;
import com.bizwink.po.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by petersong on 16-12-17.
 */
@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private MessageMapper messageMapper;

    public int insertMessage(Message message) {
        return messageMapper.insert(message);
    }
}
