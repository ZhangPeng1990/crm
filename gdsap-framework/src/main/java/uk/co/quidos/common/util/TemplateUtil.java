package uk.co.quidos.common.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateUtil {

	@SuppressWarnings("rawtypes")
	public static String template2String(String templateContent, Map map) {
		Assert.hasText(templateContent);
		if (map == null) {
			map = new HashMap();
		}

		Template t = null;
		try {
			Configuration config = new Configuration();
			t = new Template("", new StringReader(templateContent), config);
			StringWriter writer = new StringWriter();
			t.process(map, writer);
			return writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (TemplateException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		String template = "<#if solutionIssue?exists && (solutionIssue?size >0) >sdfsdfsdfsdf</#if>";
		List<String> solutionIssue = new ArrayList<String>();
		
		Map<String, List<String>> maps = new HashMap<String, List<String>>();
		maps.put("solutionIssue", solutionIssue);
		System.out.println(template2String(template, maps));
	}
}
