/**
 * DepartmentServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.bjca.uumsinterface.services.Department;

public class DepartmentServiceTestCase extends junit.framework.TestCase {
    public DepartmentServiceTestCase(String name) {
        super(name);
    }
    public void test1DepartmentFindDepartByDepartID() throws Exception {
        com.bjca.uumsinterface.services.Department.DepartmentSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.Department.DepartmentSoapBindingStub)
                          new DepartmentServiceLocator().getDepartment();
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

    public void test2DepartmentGetAllDepart() throws Exception {
        com.bjca.uumsinterface.services.Department.DepartmentSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.Department.DepartmentSoapBindingStub)
                          new DepartmentServiceLocator().getDepartment();
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
        value = binding.getAllDepart();
        // TBD - validate results
    }

    public void test3DepartmentFindDepartByDepartCode() throws Exception {
        com.bjca.uumsinterface.services.Department.DepartmentSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.Department.DepartmentSoapBindingStub)
                          new DepartmentServiceLocator().getDepartment();
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
        value = binding.findDepartByDepartCode(new String());
        // TBD - validate results
    }

    public void test4DepartmentFindDepartByDepartCodeForDC() throws Exception {
        com.bjca.uumsinterface.services.Department.DepartmentSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.Department.DepartmentSoapBindingStub)
                          new DepartmentServiceLocator().getDepartment();
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
        value = binding.findDepartByDepartCodeForDC(new String());
        // TBD - validate results
    }

    public void test5DepartmentGetAllDepartForDC() throws Exception {
        com.bjca.uumsinterface.services.Department.DepartmentSoapBindingStub binding;
        try {
            binding = (com.bjca.uumsinterface.services.Department.DepartmentSoapBindingStub)
                          new DepartmentServiceLocator().getDepartment();
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
        value = binding.getAllDepartForDC();
        // TBD - validate results
    }

}
