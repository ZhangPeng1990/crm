package uk.co.quidos.gdsap.evaluation.services.impl;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import org.junit.Test;
import org.springframework.util.Assert;

import uk.co.quidos.gdsap.framework.charge.CreditPrice;
import uk.co.quidos.gdsap.framework.charge.TransactionRecord;
import uk.co.quidos.gdsap.framework.charge.persistence.object.GdsapChargeCreditPrice;
import uk.co.quidos.gdsap.framework.charge.persistence.object.GdsapChargeTransactionRecord;

public class AppCopy2 {

	/**
	 * 用于t,e两个对象的所有属性值互转
	 * @param t 
	 * @param e
	 * @throws IntrospectionException
	 */
	public <T,E> void printOut(T t,E e) throws IntrospectionException{
		Assert.notNull(t);
		Assert.notNull(e);
		String[] tnames = t.getClass().getName().split("\\.");
		String tempClassName = tnames[tnames.length - 1];
		String tClassName = tempClassName.substring(0,1).toLowerCase() + tempClassName.substring(1);
		
		String[] enames = e.getClass().getName().split("\\.");
		String tempeClassName = enames[enames.length - 1];
		String eClassName = tempeClassName.substring(0, 1).toLowerCase() + tempeClassName.substring(1);
		
		
		BeanInfo tinfo = Introspector.getBeanInfo(t.getClass(), Object.class);//不得到父类Object的BeanInfo
		PropertyDescriptor[] tproperties = tinfo.getPropertyDescriptors();
		for(PropertyDescriptor p : tproperties){
			String name = p.getName().substring(0,1).toUpperCase() + p.getName().substring(1);
			String out = tClassName + ".set" + name + "(" + eClassName + ".get" + name + "());";
			System.out.println(out);
		}
		System.out.println("-------------------------------------------------------------------------------------------");
		for(PropertyDescriptor p : tproperties){
			String name = p.getName().substring(0,1).toUpperCase() + p.getName().substring(1);
			String out =  eClassName + ".set" + name + "(" + tClassName + ".get" + name + "());";
			System.out.println(out);
		}
	}
	
	@Test
	public void test() throws IntrospectionException{
		CreditPrice creditPrice = new CreditPrice();
		GdsapChargeCreditPrice gdsapChargeCreditPrice = new GdsapChargeCreditPrice();
		this.printOut(creditPrice,gdsapChargeCreditPrice);
	}
}
