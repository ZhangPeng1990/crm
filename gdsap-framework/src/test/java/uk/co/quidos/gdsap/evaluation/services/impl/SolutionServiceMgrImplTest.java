/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;

import java.util.HashMap;
import java.util.Map;

import uk.co.quidos.common.util.TemplateUtil;

/**
 * @author shipeng
 */
public class SolutionServiceMgrImplTest {
	public static void main(String[] args) {
		String pattern = "${item?if_exists?xml}";
		Map<String,String> map = new HashMap<String,String>();
		map.put("item", "&sdfsdfsd?");
		String temp = TemplateUtil.template2String(pattern, map);
		System.out.println(temp);
	}
}
