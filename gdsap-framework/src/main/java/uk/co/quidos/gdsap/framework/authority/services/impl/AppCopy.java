package uk.co.quidos.gdsap.framework.authority.services.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author peng.shi
 * 
 */
public class AppCopy
{
	public static void main(String[] args) throws SecurityException, ClassNotFoundException
	{
		String class2 = "uk.co.quidos.gdsap.framework.authority.persistence.object.AdminDO";// 输出类路径，返回值
		String class1 = "uk.co.quidos.gdsap.framework.authority.Admin";// 读取类路径,参数

		String name1[] = class1.split("\\.");
		String cname1 = name1[name1.length - 1];
		cname1 = cname1.substring(0,1).toLowerCase() + cname1.substring(1);
		String name2[] = class2.split("\\.");
		String cname2 = name2[name2.length - 1];
		cname2 = cname2.substring(0,1).toLowerCase() + cname2.substring(1);
		Field[] fds1 = Class.forName(class1).getDeclaredFields();
		Field[] fds2 = Class.forName(class2).getDeclaredFields();
		ArrayList<String> a = new ArrayList<String>();
		for (int i = 0; i < fds2.length; i++)
		{
			a.add(fds2[i].getName());
		}
		for (int i = 0; i < fds1.length; i++)
		{
			String flname = fds1[i].getName();
			String fname = toFirstUpper(fds1[i].getName());
			if (a.contains(flname))
			{
				if (fname.indexOf("SerialVersionUID") == -1)
				{
					System.out.println(cname1 + ".set" + fname + "(" + cname2 + ".get" + fname + "());");
				}
			}
		}
	}

	private static String toFirstUpper(String s)
	{
		String s1 = s.substring(0, 1).toUpperCase() + s.substring(1);
		return s1;
	}
	
}
