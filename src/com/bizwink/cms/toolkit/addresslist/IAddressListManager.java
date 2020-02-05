package com.bizwink.cms.toolkit.addresslist;

import java.util.*;
import java.sql.Timestamp;

public interface IAddressListManager {

  public List getAddressList(String memberid) throws AddressListException;

  public List getCurrentAddressList(String memberid, int startrow, int range) throws AddressListException;

  public AddressList getA_AddressList(int id) throws AddressListException;

  public void insertAddressList(AddressList addresslist) throws AddressListException;

  public void updateAddressList(AddressList addresslist, int id) throws AddressListException;

  public void deleteAddressList(int id) throws AddressListException;

  public List getSearchAddressList(String sqlstr) throws AddressListException;

  public List getCurrentSearchAddressList(String sqlstr, int startrow, int range) throws AddressListException;

  public List getSysAddressList(int siteid) throws AddressListException;

  public List getCurrentSysAddressList(int startrow, int range, int siteid) throws AddressListException;

  public void insertMessage(AddressList addresslist) throws AddressListException;

  public Timestamp getNewReadDate(String receiver) throws AddressListException;

  public List getMessageList(String receiver) throws AddressListException;

  public List getCurrentMessageList(String receiver, int startrow, int range) throws AddressListException;

  public void updateMessageDate(String receiver) throws AddressListException;

  public Timestamp getNewMessageDate(String receiver) throws AddressListException;

  public List getMessageList1(String sender) throws AddressListException;

  public List getCurrentMessageList1(String sender, int startrow, int range) throws AddressListException;

  public void deleteMessage(int id) throws AddressListException;

}