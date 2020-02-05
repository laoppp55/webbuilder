package com.bizwink.persistence;

import com.bizwink.po.AttachType;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-6-6
 * Time: ����5:39
 * To change this template use File | Settings | File Templates.
 */
public interface AttachTypeMapper {
    public int createAttachType(AttachType attach);
    public int createAttachTypePart(AttachType attach);
}
