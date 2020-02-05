/**
 * UserServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.bjca.uumsinterface.services.User;

public class UserServiceTestCase extends junit.framework.TestCase {
    public UserServiceTestCase(String name) {
        super(name);
    }
    public void test1UserFindDepartByDepartID() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.bjca.uums.client.bean.DepartmentInformation value = null;
        value = binding.findDepartByDepartID(new String());
        // TBD - validate results
    }

    public void test2UserFindDepartsByUserID() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        java.util.Collection value = null;
        value = binding.findDepartsByUserID(new String());
        // TBD - validate results
    }

    public void test3UserFindSystemInfoBySystemId() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.bjca.uums.client.bean.SystemInformation value = null;
        value = binding.findSystemInfoBySystemId(new String());
        // TBD - validate results
    }

    public void test4UserGetLoginInformationByUserID() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.bjca.uums.client.bean.LoginInformation value = null;
        value = binding.getLoginInformationByUserID(new String());
        // TBD - validate results
    }

    public void test5UserUpdateUserpwByUserNameAndPwd() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        int value = -3;
        value = binding.updateUserpwByUserNameAndPwd(new String(), new String());
        // TBD - validate results
    }

    public void test6UserFindAllSystemInfos() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        java.util.Collection value = null;
        value = binding.findAllSystemInfos();
        // TBD - validate results
    }

    public void test7UserFindSystemInfosAccessedByUserID() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        java.util.Collection value = null;
        value = binding.findSystemInfosAccessedByUserID(new String());
        // TBD - validate results
    }

    public void test8UserFindCustomContentInfoByCustomID() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.bjca.uums.client.bean.CustomContentInfo value = null;
        value = binding.findCustomContentInfoByCustomID(new String());
        // TBD - validate results
    }

    public void test9UserFindRoleInfosBySystemCodeAndUserID() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        java.util.Collection value = null;
        value = binding.findRoleInfosBySystemCodeAndUserID(new String(), new String());
        // TBD - validate results
    }

    public void test10UserFindCredenceInfoByUserID() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.bjca.uums.client.bean.UserCredenceInformation value = null;
        value = binding.findCredenceInfoByUserID(new String());
        // TBD - validate results
    }

    public void test11UserFindCustomContentInfosBySystemCodeAndUserType() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        java.util.Collection value = null;
        value = binding.findCustomContentInfosBySystemCodeAndUserType(new String(), new String());
        // TBD - validate results
    }

    public void test12UserFindSystemInfoBySystemCode() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.bjca.uums.client.bean.SystemInformation value = null;
        value = binding.findSystemInfoBySystemCode(new String());
        // TBD - validate results
    }

    public void test13UserGetAuthorityAndSystemIDByUsernameAndPw() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        String value = null;
        value = binding.getAuthorityAndSystemIDByUsernameAndPw(new String(), new String());
        // TBD - validate results
    }

    public void test14UserFindWholeUserInfosByUserIDForDC() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.bjca.uums.client.bean.UserInformation value = null;
        value = binding.findWholeUserInfosByUserIDForDC(new String());
        // TBD - validate results
    }

    public void test15UserFindUserInfosByUserSIDForDC() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.bjca.uums.client.bean.UserInformation value = null;
        value = binding.findUserInfosByUserSIDForDC(new String());
        // TBD - validate results
    }

    public void test16UserFindUserInfosByUserIDForDC() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.bjca.uums.client.bean.UserInformation value = null;
        value = binding.findUserInfosByUserIDForDC(new String());
        // TBD - validate results
    }

    public void test17UserFindUserInfosBySystemIDForDC() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        java.util.Collection value = null;
        value = binding.findUserInfosBySystemIDForDC(new String());
        // TBD - validate results
    }

    public void test18UserFindUnitInfosByUserIDFroDC() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.bjca.uums.client.bean.UnitInformation value = null;
        value = binding.findUnitInfosByUserIDFroDC(new String());
        // TBD - validate results
    }

    public void test19UserFindWholePersonInfosByUserIDForDC() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.bjca.uums.client.bean.PersonInformation value = null;
        value = binding.findWholePersonInfosByUserIDForDC(new String());
        // TBD - validate results
    }

    public void test20UserFindPersonInfosByUserIDForDC() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.bjca.uums.client.bean.PersonInformation value = null;
        value = binding.findPersonInfosByUserIDForDC(new String());
        // TBD - validate results
    }

    public void test21UserGetAuthorityByUserIDAndPw() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        String value = null;
        value = binding.getAuthorityByUserIDAndPw(new String(), new String());
        // TBD - validate results
    }

    public void test22UserFindRoleInfosByUserIDForStrType() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        String value = null;
        value = binding.findRoleInfosByUserIDForStrType(new String());
        // TBD - validate results
    }

    public void test23UserFindRoleInfoByRoleId() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.bjca.uums.client.bean.RoleInformation value = null;
        value = binding.findRoleInfoByRoleId(new String());
        // TBD - validate results
    }

    public void test24UserFindRoleInfoByRoleCode() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.bjca.uums.client.bean.RoleInformation value = null;
        value = binding.findRoleInfoByRoleCode(new String());
        // TBD - validate results
    }

    public void test25UserFindUnitInfosByUserID() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.bjca.uums.client.bean.UnitInformation value = null;
        value = binding.findUnitInfosByUserID(new String());
        // TBD - validate results
    }

    public void test26UserFindPersonInfosByUserID() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.bjca.uums.client.bean.PersonInformation value = null;
        value = binding.findPersonInfosByUserID(new String());
        // TBD - validate results
    }

    public void test27UserUpdateUserpw() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        int value = -3;
        value = binding.updateUserpw(new String(), new String());
        // TBD - validate results
    }

    public void test28UserGetAuthorityByUsernameAndPw() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        String value = null;
        value = binding.getAuthorityByUsernameAndPw(new String(), new String());
        // TBD - validate results
    }

    public void test29UserFindUserInfosBySystemID() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        java.util.Collection value = null;
        value = binding.findUserInfosBySystemID(new String());
        // TBD - validate results
    }

    public void test30UserFindRoleInfosBySystemID() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        java.util.Collection value = null;
        value = binding.findRoleInfosBySystemID(new String());
        // TBD - validate results
    }

    public void test31UserFindRoleInfosByUserID() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        java.util.Collection value = null;
        value = binding.findRoleInfosByUserID(new String());
        // TBD - validate results
    }

    public void test32UserFindUserInfosByUserID() throws Exception {
        com.bjca.uumsinterface.services.User.UserSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.User.UserSoapBindingStub)
                          new UserServiceLocator().getUser();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.bjca.uums.client.bean.UserInformation value = null;
        value = binding.findUserInfosByUserID(new String());
        // TBD - validate results
    }

}
