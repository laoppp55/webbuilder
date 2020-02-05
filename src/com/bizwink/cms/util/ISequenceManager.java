package com.bizwink.cms.util;

public interface ISequenceManager
{
    int nextID(String counterName);

    int getSequenceNum(String seqFlag);

    long generateOrderID();

    int getLSH_Num();
}