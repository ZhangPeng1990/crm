/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.evaluation.StandardOption;
import uk.co.quidos.gdsap.evaluation.StandardRecommendation;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationWrap;
import uk.co.quidos.gdsap.evaluation.StandardValue;
import uk.co.quidos.gdsap.evaluation.ValueAttributes;
import uk.co.quidos.gdsap.evaluation.ValueAttributes.InputType;
import uk.co.quidos.gdsap.evaluation.ValueAttributes.ValueType;
import uk.co.quidos.gdsap.evaluation.enums.SolutionType;
import uk.co.quidos.gdsap.evaluation.enums.StandardRecommendationGroup;
import uk.co.quidos.gdsap.evaluation.enums.StandardRecommendationType;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalRecommendationMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalRecommendation;
import uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.StandardRecommendationServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.object.ReportConfig;
import uk.co.quidos.gdsap.evaluation.utils.DocumentUtil;
import uk.co.quidos.gdsap.framework.enums.EpcVersion;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;
import uk.co.quidos.gdsap.framework.sys.lang.enums.EnumUtils;
import uk.co.quidos.gdsap.framework.sysconf.GlobalConfig;

/**
 * @author peng.shi
 *
 */
@Service("standardRecommendationServiceMgr")
@Transactional
public class StandardRecommendationServiceMgrImpl extends AbsBusinessObjectServiceMgr implements StandardRecommendationServiceMgr
{
	@Autowired
	private GdsapEvalRecommendationMapper gdsapEvalRecommendationMapper;
	
	@Autowired
	private ReportServiceMgr reportServiceMgr;
	
	public ReportServiceMgr getReportServiceMgr()
	{
		return reportServiceMgr;
	}
	public void setReportServiceMgr(ReportServiceMgr reportServiceMgr)
	{
		this.reportServiceMgr = reportServiceMgr;
	}
	public GdsapEvalRecommendationMapper getGdsapEvalRecommendationMapper()
	{
		return gdsapEvalRecommendationMapper;
	}
	public void setGdsapEvalRecommendationMapper(GdsapEvalRecommendationMapper gdsapEvalRecommendationMapper)
	{
		this.gdsapEvalRecommendationMapper = gdsapEvalRecommendationMapper;
	}
	public static final String STANDARD_RECOMMENDATION_PATH = "/standard_recommendation.xml";
	public static final String STANDARD_RECOMMENDATION_PATH_V992 = "/standard_recommendation_v992.xml";
	
	public Map<Integer,StandardRecommendation> srMapHolder_v991;
	public Map<Integer,StandardRecommendation> srMapHolder_v992;
	
	@SuppressWarnings("unchecked")
	public StandardRecommendationServiceMgrImpl()
	{
		// for version 991
		if (srMapHolder_v991 == null)
		{
			srMapHolder_v991 = new LinkedHashMap<Integer, StandardRecommendation>();
			SAXReader reader = new SAXReader();
			try
			{
				Document doc = reader.read(this.getClass().getResourceAsStream(STANDARD_RECOMMENDATION_PATH));
				Element rootElement = doc.getRootElement();
				
				List<Element> recElements = rootElement.elements("Recommendation");
				for (Element recElement : recElements)
				{
					//初始化StandardRecommendation
					int code = Integer.parseInt(recElement.element("Improvement-Number").getTextTrim());
					String item = recElement.elementText("Improvement-Item");
					String summary = recElement.elementText("Improvement-Summary");
					String heading = recElement.elementText("Improvement-Heading");
					String description = recElement.elementText("Improvement-Description");
					String situation = recElement.element("Improvement-Situation") != null ? recElement.elementText("Improvement-Situation"):null;
					String conditions = recElement.element("Improvement-Conditions") != null ? recElement.elementText("Improvement-Conditions"):null;
					String group = recElement.elementText("Improvement-Group");
					StandardRecommendation sr = new StandardRecommendation();
					sr.setCode(code);
					sr.setItem(item);
					sr.setConditions(conditions);
					sr.setDescription(description);
					sr.setHeading(heading);
					sr.setSituation(situation);
					sr.setStandardRecommendationType(StandardRecommendationType.No_Option);
					sr.setSummary(summary);
					sr.setStandardRecommendationGroup((StandardRecommendationGroup)EnumUtils.getByCode(group, StandardRecommendationGroup.class));
					
					Element optionElement = recElement.element("Improvement-Options");
					if (optionElement != null)
					{
						sr.setStandardRecommendationType(StandardRecommendationType.Option);
						
						sr.setStandardOptions(new ArrayList<StandardOption>());
						List<Element> uls = optionElement.elements("ul");
						for (Element ulElement : uls)
						{
							StandardOption so = new StandardOption();
							so.setCode(ulElement.attributeValue("code"));
							so.setName(ulElement.attributeValue("name"));
							List<Element> liElements = ulElement.elements("li");
							Map<Integer,String> lis = new LinkedHashMap<Integer, String>();
							Map<Integer, String> descs = new LinkedHashMap<Integer, String>();
							for (Element liElement : liElements)
							{
								lis.put(Integer.parseInt(liElement.attributeValue("code")), liElement.attributeValue("name"));
								descs.put(Integer.parseInt(liElement.attributeValue("code")), liElement.attributeValue("desc"));
							}
							so.setLis(lis);
							so.setDescs(descs);
							sr.getStandardOptions().add(so);
						}
					}
					
					srMapHolder_v991.put(sr.getCode(), sr);
				}
			} catch (DocumentException e)
			{
				e.printStackTrace();
				throw new ExceptionInInitializerError(e);
			}
		}
		
		// for version 992 
		if (srMapHolder_v992 == null)
		{
			srMapHolder_v992 = new LinkedHashMap<Integer, StandardRecommendation>();
			SAXReader reader = new SAXReader();
			try
			{
				Document doc = reader.read(this.getClass().getResourceAsStream(STANDARD_RECOMMENDATION_PATH_V992));
				Element rootElement = doc.getRootElement();
				
				//用于GDIP页面输入的U-value高于默认值时提示信息
				String recommendationDeclarationEAW_windows = null;
				String recommendationDeclarationEAW_wall_floor_roof = null;
				String recommendationDeclarationSCT_windows = null;
				String recommendationDeclarationSCT_wall_floor_roof = null;
				String recommendationDeclarationSCT_partWall = null;
				String recommendationDeclarationEAW_partWall = null;
				recommendationDeclarationEAW_windows = rootElement.element("Recommendation-Declarations").element("Recommendation-Declaration-EAW").element("for_windows").getText();
				recommendationDeclarationEAW_wall_floor_roof = rootElement.element("Recommendation-Declarations").element("Recommendation-Declaration-EAW").element("for_wall_floor_roof").getText();
				recommendationDeclarationSCT_windows = rootElement.element("Recommendation-Declarations").element("Recommendation-Declaration-SCT").element("for_windows").getText();
				recommendationDeclarationSCT_wall_floor_roof = rootElement.element("Recommendation-Declarations").element("Recommendation-Declaration-SCT").element("for_wall_floor_roof").getText();
				recommendationDeclarationEAW_partWall = rootElement.element("Recommendation-Declarations").element("Recommendation-Declaration-EAW").element("for_partWall").getText();
				recommendationDeclarationSCT_partWall = rootElement.element("Recommendation-Declarations").element("Recommendation-Declaration-SCT").element("for_partWall").getText();
				
				
				List<Element> recElements = rootElement.elements("Recommendation");
				for (Element recElement : recElements)
				{
					//初始化StandardRecommendation
					int code = Integer.parseInt(recElement.element("Improvement-Number").getTextTrim());
					String item = recElement.elementText("Improvement-Item");
					String summary = recElement.elementText("Improvement-Summary");
					String heading = recElement.elementText("Improvement-Heading");
					String description = recElement.elementText("Improvement-Description");
					String situation = recElement.element("Improvement-Situation-EAW") != null ? recElement.elementText("Improvement-Situation-EAW"):null;
					String conditions = recElement.element("Improvement-Conditions-EAW") != null ? recElement.elementText("Improvement-Conditions-EAW"):null;
					String situationSCT = recElement.element("Improvement-Situation-SCT") != null ? recElement.elementText("Improvement-Situation-SCT"):null;
					String conditionsSCT = recElement.element("Improvement-Conditions-SCT") != null ? recElement.elementText("Improvement-Conditions-SCT"):null;
					String group = recElement.elementText("Improvement-Group");
					StandardRecommendation sr = new StandardRecommendation();
					sr.setCode(code);
					sr.setItem(item);
					sr.setConditions(conditions);
					sr.setConditions_SCT(conditionsSCT);
					sr.setDescription(description);
					sr.setHeading(heading);
					sr.setSituation(situation);
					sr.setSituation_SCT(situationSCT);
					sr.setStandardRecommendationType(StandardRecommendationType.No_Option);
					sr.setGdip_standardRecommendationType(StandardRecommendationType.No_Option);
					sr.setSummary(summary);
					sr.setStandardRecommendationGroup((StandardRecommendationGroup)EnumUtils.getByCode(group, StandardRecommendationGroup.class));
					
					if(sr.getStandardRecommendationGroup().equals(StandardRecommendationGroup.Windows_Doors))
					{
						sr.setMeasureDeclarationEAW(recommendationDeclarationEAW_windows);
						sr.setMeasureDeclarationSCT(recommendationDeclarationSCT_windows);
					}
					else if(sr.getStandardRecommendationGroup().equals(StandardRecommendationGroup.Insulation_Measures) && !sr.getItem().equals("B4"))
					{
						sr.setMeasureDeclarationEAW(recommendationDeclarationEAW_wall_floor_roof);
						sr.setMeasureDeclarationSCT(recommendationDeclarationSCT_wall_floor_roof);
					}
					else if(sr.getItem().equals("B4"))
					{
						sr.setMeasureDeclarationEAW(recommendationDeclarationEAW_partWall);
						sr.setMeasureDeclarationSCT(recommendationDeclarationSCT_partWall);
					}
					
					//GDAR 改进输入项
					Element valueElements = recElement.element("Improvement-Values");
					if(valueElements != null)
					{
						List<Element> inputValues = valueElements.elements("Improvement-Value");
						List<StandardValue> svs = new ArrayList<StandardValue>();
						for(Element inputE : inputValues)
						{
							StandardValue sv = new StandardValue();
							ValueAttributes attribute = new ValueAttributes();
							attribute.setReadonly((inputE.attributeValue("readonly") != null && inputE.attributeValue("readonly").equals("true")) ? true : false);
							attribute.setValueType((inputE.attributeValue("valueType") != null) ? ValueType.valueOf(inputE.attributeValue("valueType")) : null);
							attribute.setHaveDefaultValue((inputE.attributeValue("DefaultValue") != null && inputE.attributeValue("DefaultValue").equals("true"))? true : false);
							attribute.setRequired((inputE.attributeValue("required") != null && inputE.attributeValue("required").equals("true")));
							attribute.setInputType(inputE.attributeValue("inputType") != null ? InputType.valueOf(inputE.attributeValue("inputType")) : null);
							
							sv.setDesc(inputE.element("desc").getText());
							sv.setName(inputE.element("name").getText());
							sv.setEawValue(inputE.element("EAW-value") != null ? inputE.element("EAW-value").getText() : null);
							sv.setEawValueDesc(inputE.element("EAW-value") != null ? inputE.element("EAW-value").attributeValue("desc") : null);
							sv.setSctValue(inputE.element("SCT-value") != null ? inputE.element("SCT-value").getText() : null);
							sv.setSctValueDesc(inputE.element("SCT-value") != null ? inputE.element("SCT-value").attributeValue("desc") : null);
							if(sv.getEawValue() != null && sv.getSctValue() != null && sv.getEawValue().equals(sv.getSctValue()))
							{
								sv.setShowValue(sv.getEawValue());
							}
							sv.setAttribute(attribute);
							svs.add(sv);
						}
						sr.setStandardValues(svs);
					}
					
					//GDip 改进输入项
					Element gdipVlueElements = recElement.element("GDIP-Improvement-Values");
					if(gdipVlueElements != null)
					{
						List<Element> inputValues = gdipVlueElements.elements("Improvement-Value");
						List<StandardValue> svs = new ArrayList<StandardValue>();
						for(Element inputE : inputValues)
						{
							StandardValue sv = new StandardValue();
							ValueAttributes attribute = new ValueAttributes();
							attribute.setReadonly((inputE.attributeValue("readonly") != null && inputE.attributeValue("readonly").equals("true")));
							attribute.setValueType((inputE.attributeValue("valueType") != null) ? ValueType.valueOf(inputE.attributeValue("valueType")) : null);
							attribute.setHaveDefaultValue((inputE.attributeValue("DefaultValue") != null && inputE.attributeValue("DefaultValue").equals("true")));
							attribute.setRequired((inputE.attributeValue("required") != null && inputE.attributeValue("required").equals("true")));
							attribute.setInputType(inputE.attributeValue("inputType") != null ? InputType.valueOf(inputE.attributeValue("inputType")) : null);
							attribute.setNeedConfirm((inputE.attributeValue("needConfirm") != null && inputE.attributeValue("needConfirm").equals("true")));
							
							sv.setDesc(inputE.element("desc").getText());
							sv.setName(inputE.element("name").getText());
							sv.setEawValue(inputE.element("EAW-value") != null ? inputE.element("EAW-value").getText() : null);
							sv.setEawValueDesc(inputE.element("EAW-value") != null ? inputE.element("EAW-value").attributeValue("desc") : null);
							sv.setEawConfirmValue((inputE.element("EAW-value") != null && inputE.element("EAW-value").attributeValue("confirmValue") != null) ? inputE.element("EAW-value").attributeValue("confirmValue") : sv.getEawValue());
							sv.setSctValue(inputE.element("SCT-value") != null ? inputE.element("SCT-value").getText() : null);
							sv.setSctValueDesc(inputE.element("SCT-value") != null ? inputE.element("SCT-value").attributeValue("desc") : null);
							sv.setSctConfirmValue((inputE.element("SCT-value") != null && inputE.element("SCT-value").attributeValue("confirmValue") != null) ? inputE.element("SCT-value").attributeValue("confirmValue") : sv.getSctValue());
							if(sv.getEawValue() != null && sv.getSctValue() != null && sv.getEawValue().equals(sv.getSctValue()))
							{
								sv.setShowValue(sv.getEawValue());
							}
							sv.setAttribute(attribute);
							svs.add(sv);
						}
						sr.setGdipInputValues(svs);
					}
					
					Element optionElement = recElement.element("Improvement-Options");
					if (optionElement != null)
					{
						sr.setStandardRecommendationType(StandardRecommendationType.Option);
						sr.setStandardOptions(new ArrayList<StandardOption>());
						List<Element> uls = optionElement.elements("ul");
						for (Element ulElement : uls)
						{
							StandardOption so = new StandardOption();
							so.setCode(ulElement.attributeValue("code"));
							so.setName(ulElement.attributeValue("name"));
							List<Element> liElements = ulElement.elements("li");
							Map<Integer,String> lis = new LinkedHashMap<Integer, String>();
							Map<Integer, String> descs = new LinkedHashMap<Integer, String>();
							for (Element liElement : liElements)
							{
								lis.put(Integer.parseInt(liElement.attributeValue("code")), liElement.attributeValue("name"));
								descs.put(Integer.parseInt(liElement.attributeValue("code")), liElement.attributeValue("desc"));
								
								if(liElement.attributeValue("defaultOption") != null && liElement.attributeValue("defaultOption").equals("YES"))
								{
									so.setDefaultValue(liElement.attributeValue("desc"));
									so.setDefaultValueCode(Integer.parseInt(liElement.attributeValue("code")));
									if(liElement.attributeValue("uValue") != null && liElement.attributeValue("uValue").length() > 0)
									{
										so.setuValue(liElement.attributeValue("uValue"));
									}
								}
							}
							so.setLis(lis);
							so.setDescs(descs);
							sr.getStandardOptions().add(so);
						}
					}
					
					Element gdip_optionElement = recElement.element("GDIP-Improvement-Options");
					if (gdip_optionElement != null)
					{
						sr.setGdip_standardRecommendationType(StandardRecommendationType.Option);
						
						sr.setGdip_standardOptions(new ArrayList<StandardOption>());
						List<Element> uls = gdip_optionElement.elements("ul");
						for (Element ulElement : uls)
						{
							StandardOption so = new StandardOption();
							so.setCode(ulElement.attributeValue("code"));
							so.setName(ulElement.attributeValue("name"));
							List<Element> liElements = ulElement.elements("li");
							Map<Integer,String> lis = new LinkedHashMap<Integer, String>();
							Map<Integer, String> descs = new LinkedHashMap<Integer, String>();
							for (Element liElement : liElements)
							{
								lis.put(Integer.parseInt(liElement.attributeValue("code")), liElement.attributeValue("name"));
								descs.put(Integer.parseInt(liElement.attributeValue("code")), liElement.attributeValue("desc"));
								
								if(liElement.attributeValue("defaultOption") != null && liElement.attributeValue("defaultOption").equals("YES"))
								{
									so.setDefaultValue(liElement.attributeValue("desc"));
									so.setDefaultValueCode(Integer.parseInt(liElement.attributeValue("code")));
									if(liElement.attributeValue("uValue") != null && liElement.attributeValue("uValue").length() > 0)
									{
										so.setuValue(liElement.attributeValue("uValue"));
									}
								}
							}
							so.setLis(lis);
							so.setDescs(descs);
							sr.getGdip_standardOptions().add(so);
						}
					}
					
					srMapHolder_v992.put(sr.getCode(), sr);
				}
			} catch (DocumentException e)
			{
				e.printStackTrace();
				throw new ExceptionInInitializerError(e);
			}
		}
	}
	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.StandardRecommendationServiceMgr#getStandardRecommendation(int)
	 */
	@Override
	public StandardRecommendation getStandardRecommendation(int code, Report report)
	{
		EpcVersion version = report.getEpcVersion();
		
		StandardRecommendation sr = null;
		
		if(EpcVersion.EPC_NEW.equals(version))
		{
			sr = this.srMapHolder_v992.get(code);
		}
		else
		{
			// TODO 2014.12.06 周六 部署到 Live 和 Uat (qube , qube2), 为了兼容老数据
			if(this.srMapHolder_v992.get(code) == null)
			{
				sr = this.srMapHolder_v991.get(code);
			}
			else
			{
				sr = this.srMapHolder_v992.get(code);
			}
		}
		
		//针对GDAR页面B改进时显示警告信息的特殊性做处理
		if(sr != null && sr.getItem().equals("B"))
		{
			if(sr.getCode() == 4 || sr.getCode() == 106)
			{
				boolean accessIssues = false;
				boolean highExposure = false;
				boolean narrowCavities = false;
				
				StringBuffer situation = new StringBuffer("");
				StringBuffer conditions = new StringBuffer("");
				
				String path = GlobalConfig.getInstance().getFSDir() + report.getReportXmlFile();
				String epcXmlContent = null;
				Document doc = null;
				try {
					epcXmlContent = FileUtils.readFileToString(new File(path));
					doc = DocumentUtil.readDocument(epcXmlContent);
				} catch (Exception e) {
					try {
						epcXmlContent = FileUtils.readFileToString(new File(path),"utf-16");
						doc = DocumentUtil.readDocument(epcXmlContent);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
				
				Element rootElt = doc.getRootElement();
			    if(ReportConfig.isExist(ReportConfig.EAWRootElements, rootElt.getName()))//英格兰 991
			    {
			    	String defNamespace = rootElt.getNamespaceURI();
					XPath xpathSelector;
					Map<String, String> nameSpaceMap = new HashMap<String, String>();
					//处理名称空间
					if(defNamespace != null)
					{
						nameSpaceMap.put("defu", defNamespace);
					}
					for(String prefix : ReportConfig.prefixs){
						String namespace = null;
						namespace = rootElt.getNamespaceForPrefix(prefix) != null ? rootElt.getNamespaceForPrefix(prefix).getURI() : null;
						if(namespace != null){
							nameSpaceMap.put(prefix, namespace);
						}
					}
					//针对前缀为SAP和HIP的名称空间定义到子节点上的情况处理
					if(nameSpaceMap.get("SAP") == null || nameSpaceMap.get("HIP") == null){
						nameSpaceMap = null;
						nameSpaceMap = getNameSpaceMap(doc);
					}
					//处理名称空间
					Element selectElement = null; 
					
					xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Addendum/HIP:Access-Issues");
					xpathSelector.setNamespaceURIs(nameSpaceMap);
					selectElement = (Element) xpathSelector.selectSingleNode(doc);
					if(selectElement != null && selectElement.getTextTrim().equals("true"))
					{
						accessIssues = true;
					}
					
					xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Addendum/HIP:High-Exposure");
					xpathSelector.setNamespaceURIs(nameSpaceMap);
					selectElement = (Element) xpathSelector.selectSingleNode(doc);
					if(selectElement != null && selectElement.getTextTrim().equals("true"))
					{
						highExposure = true;
					}
					
					xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Addendum/HIP:Narrow-Cavities");
					xpathSelector.setNamespaceURIs(nameSpaceMap);
					selectElement = (Element) xpathSelector.selectSingleNode(doc);
					if(selectElement != null && selectElement.getTextTrim().equals("true"))
					{
						narrowCavities = true;
					}
			    }
			    else if(ReportConfig.isExist(ReportConfig.SCTRootElements, rootElt.getName()))//苏格兰 991
			    {
			    	Element selectElement = null; 
			    	String defNamespace = rootElt.getNamespaceURI();
					String xsiNamespace = rootElt.getNamespaceForPrefix("xsi") != null ? rootElt.getNamespaceForPrefix("xsi").getURI() : null;
					String hipNamespace = rootElt.getNamespaceForPrefix("HIP") != null ? rootElt.getNamespaceForPrefix("HIP").getURI() : null;
					String sapNamespace = rootElt.getNamespaceForPrefix("SAP") != null ? rootElt.getNamespaceForPrefix("SAP").getURI() : null;
					String csNamespace = rootElt.getNamespaceForPrefix("CS") != null ? rootElt.getNamespaceForPrefix("CS").getURI() : null;
					
					XPath xpathSelector;
					Map<String, String> nameSpaceMap = new HashMap<String, String>();
					
					if(xsiNamespace != null)
					{
						nameSpaceMap.put("xsi", xsiNamespace);
					}
					if(defNamespace != null)
					{
						nameSpaceMap.put("defu", defNamespace);
					}
					if(sapNamespace != null)
					{
						nameSpaceMap.put("SAP", sapNamespace);
					}
					if(hipNamespace != null)
					{
						nameSpaceMap.put("HIP", hipNamespace);
					}
					if(csNamespace != null)
					{
						nameSpaceMap.put("CS", csNamespace);
					}
					
					if(sapNamespace != null && hipNamespace != null){
						xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Addendum/HIP:Access-Issues");
					}else if(sapNamespace != null && hipNamespace == null){
						xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Addendum/defu:Access-Issues");
					}else if(sapNamespace == null && hipNamespace != null){
						xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Addendum/HIP:Access-Issues");
					}else{
						xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Addendum/defu:Access-Issues");
					}
					xpathSelector.setNamespaceURIs(nameSpaceMap);
					selectElement = (Element) xpathSelector.selectSingleNode(doc);
					if(selectElement != null && selectElement.getTextTrim().equals("true"))
					{
						accessIssues = true;
					}
					
					if(sapNamespace != null && hipNamespace != null){
						xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Addendum/HIP:High-Exposure");
					}else if(sapNamespace != null && hipNamespace == null){
						xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Addendum/defu:High-Exposure");
					}else if(sapNamespace == null && hipNamespace != null){
						xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Addendum/HIP:High-Exposure");
					}else{
						xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Addendum/defu:High-Exposure");
					}
					xpathSelector.setNamespaceURIs(nameSpaceMap);
					selectElement = (Element) xpathSelector.selectSingleNode(doc);
					if(selectElement != null && selectElement.getTextTrim().equals("true"))
					{
						highExposure = true;
					}
					
					if(sapNamespace != null && hipNamespace != null){
						xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Addendum/HIP:Narrow-Cavities");
					}else if(sapNamespace != null && hipNamespace == null){
						xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Addendum/defu:Narrow-Cavities");
					}else if(sapNamespace == null && hipNamespace != null){
						xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Addendum/HIP:Narrow-Cavities");
					}else{
						xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Addendum/defu:Narrow-Cavities");
					}
					xpathSelector.setNamespaceURIs(nameSpaceMap);
					selectElement = (Element) xpathSelector.selectSingleNode(doc);
					if(selectElement != null && selectElement.getTextTrim().equals("true"))
					{
						narrowCavities = true;
					}
					
			    }
			    else if(ReportConfig.isExist(ReportConfig.NewEpcRootElements, rootElt.getName()))//针对EPC 17.0新格式解析
			    {
			    	Element selectElement = null; 
			    	String defNamespace = rootElt.getNamespaceURI();
					XPath xpathSelector;
					Map<String, String> nameSpaceMap = new HashMap<String, String>();
					//处理名称空间
					if(defNamespace != null)
					{
						nameSpaceMap.put("defu", defNamespace);
					}
					for(String prefix : ReportConfig.prefixs){
						String namespace = null;
						namespace = rootElt.getNamespaceForPrefix(prefix) != null ? rootElt.getNamespaceForPrefix(prefix).getURI() : null;
						if(namespace != null){
							nameSpaceMap.put(prefix, namespace);
						}
					}
					//处理名称空间
					
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Addendum/defu:Access-Issues");
					xpathSelector.setNamespaceURIs(nameSpaceMap);
					selectElement = (Element)xpathSelector.selectSingleNode(doc);
					if(selectElement != null && selectElement.getTextTrim().equals("true"))
					{
						accessIssues = true;
					}
					
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Addendum/defu:High-Exposure");
					xpathSelector.setNamespaceURIs(nameSpaceMap);
					selectElement = (Element)xpathSelector.selectSingleNode(doc);
					if(selectElement != null && selectElement.getTextTrim().equals("true"))
					{
						highExposure = true;
					}
					
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Addendum/defu:Narrow-Cavities");
					xpathSelector.setNamespaceURIs(nameSpaceMap);
					selectElement = (Element)xpathSelector.selectSingleNode(doc);
					if(selectElement != null && selectElement.getTextTrim().equals("true"))
					{
						narrowCavities = true;
					}
			    }
			    
			    if(narrowCavities)
				{
					situation.append("Cavity wall, fill selected but narrow cavities indicated in EPC addendum, ");
					conditions.append("Narrow cavities: The walls may have narrow cavities. This should be checked to establish a suitable type of cavity wall insulation. ");
				}
				if(accessIssues)
				{
					situation.append("Cavity wall, fill selected but access issues indicated in EPC addendum, ");
					conditions.append("Access to be determined: Establish whether access to applicable walls can be arranged. ");
				}
				if(highExposure)
				{
					situation.append("Cavity wall, fill selected but possible high exposure indicated in EPC addendum, ");
					conditions.append("Possible high exposure: Suitability for cavity fill should be checked in relation to local exposure conditions and wall construction. ");
				}
				
				
				if(situation.length() > 1)
				{
					sr.setConditions(conditions.toString());
					sr.setConditions_SCT(conditions.toString());
					sr.setSituation(situation.toString());
					sr.setSituation_SCT(situation.toString());
				}
			}
		}
		//针对GDAR页面B改进时显示警告信息的特殊性做处理
		
		return sr;
	}
	
	Map<String, String> nameSpacesMap = null;
	private Map<String, String> getNameSpaceMap(Document doc){
		nameSpacesMap = new HashMap<String, String>();
		Element rootElt = doc.getRootElement();
		for(String prefix : ReportConfig.prefixs){
			String namespace = null;
			namespace = rootElt.getNamespaceForPrefix(prefix) != null ? rootElt.getNamespaceForPrefix(prefix).getURI() : null;
			if(namespace != null){
				nameSpacesMap.put(prefix, namespace);
			}
		}
		getNameSpaceMap(rootElt);
		return nameSpacesMap;
	}
	
	private void getNameSpaceMap(Element e){
		for(Iterator<Element> it = e.elementIterator(); it.hasNext();){
			Element element = (Element) it.next();
			Namespace namespace = element.getNamespace();
			if(isValidNamespace(namespace)){
				if(!namespace.getPrefix().trim().equals("")){
					nameSpacesMap.put(namespace.getPrefix(),namespace.getURI());
				}else{
					nameSpacesMap.put("defu",namespace.getURI());
				}
			}
			getNameSpaceMap(element);
		}
	}
	
	private boolean isValidNamespace(Namespace namespace){
		Assert.notNull(namespace);
		boolean valid = false;
		if(!namespace.getPrefix().trim().equals("") || !namespace.getURI().trim().equals("")){
			valid = true;
		}
		return valid;
	}
	
	@Override
	public StandardValue getStandardValue(StandardRecommendation sr, String name)
	{
		Assert.notNull(sr);
		List<StandardValue> sos = null;
		if(SolutionType.GDAR.equals(sr.getSolutionType()))
		{
			sos = sr.getStandardValues();
		}
		else if(SolutionType.GDIP.equals(sr.getSolutionType()))
		{
			sos = sr.getGdipInputValues();
		}
		
		if (!CollectionUtils.isEmpty(sos))
		{
			for (StandardValue so : sos)
			{
				if (so.getName().equals(name))
				{
					return so;
				}
			}
		}
		return null;
	}
	
	@Override
	public StandardOption getStandardOption(StandardRecommendation sr, String standardOptionCode)
	{
		Assert.notNull(sr);
		List<StandardOption> sos = null;
		if(SolutionType.GDAR.equals(sr.getSolutionType()))
		{
			sos = sr.getStandardOptions();
		}
		else if(SolutionType.GDIP.equals(sr.getSolutionType()))
		{
			sos = sr.getGdip_standardOptions();
		}
		
		if (!CollectionUtils.isEmpty(sos))
		{
			for (StandardOption so : sos)
			{
				if (so.getCode().equals(standardOptionCode))
				{
					return so;
				}
			}
		}
		return null;
	}
	
	@Override
	public List<StandardRecommendationWrap> addStandardRecommendation(long reportId ,List<StandardRecommendation> recommendations)
	{
		Assert.notEmpty(recommendations);
		Report report = this.getReportServiceMgr().getReport(reportId);
		List<StandardRecommendationWrap> tmpSrs = new ArrayList<StandardRecommendationWrap>();
		for (StandardRecommendation r : recommendations)
		{
			Assert.notNull(r.getSolutionType());
			GdsapEvalRecommendation rmodel = new GdsapEvalRecommendation();
			rmodel.setRecommendationCode(r.getCode());
			rmodel.setRecommendationType(r.getSolutionType().getCode());
			rmodel.setReportId(reportId);
			this.getGdsapEvalRecommendationMapper().insert(rmodel);
			
			StandardRecommendationWrap wrap = new StandardRecommendationWrap();
			wrap.setId(rmodel.getId());
			wrap.setReport(report);
			wrap.setSolutionType(r.getSolutionType());
			wrap.setStandardRecommendation(r);
			tmpSrs.add(wrap);
		}
		
		
		
		//排序遍历
		
		List<StandardRecommendationWrap> tmpWraps = new ArrayList<StandardRecommendationWrap>();
		
		//Insulation_Measures("Insulation Measures"),
		List<StandardRecommendationWrap> imWraps = new ArrayList<StandardRecommendationWrap>();
		//Heating_Hot_Water("Heating and Hot Water"),
		List<StandardRecommendationWrap> hhwWraps = new ArrayList<StandardRecommendationWrap>();
		//Windows_Doors("Windows and Doors"),
		List<StandardRecommendationWrap> wdWraps = new ArrayList<StandardRecommendationWrap>();
		//Electricity_Generation("Electricity Generation")
		List<StandardRecommendationWrap> egWraps = new ArrayList<StandardRecommendationWrap>();
		//Lighting
		List<StandardRecommendationWrap> ltWraps = new ArrayList<StandardRecommendationWrap>();
		
		
		for (StandardRecommendationWrap wrap : tmpSrs)
		{
			if (wrap.getStandardRecommendation().getStandardRecommendationGroup().equals(StandardRecommendationGroup.Insulation_Measures))
			{
				imWraps.add(wrap);
				continue;
			}
			if (wrap.getStandardRecommendation().getStandardRecommendationGroup().equals(StandardRecommendationGroup.Heating_Hot_Water))
			{
				hhwWraps.add(wrap);
				continue;
			}
			if (wrap.getStandardRecommendation().getStandardRecommendationGroup().equals(StandardRecommendationGroup.Windows_Doors))
			{
				wdWraps.add(wrap);
				continue;
			}
			if (wrap.getStandardRecommendation().getStandardRecommendationGroup().equals(StandardRecommendationGroup.Electricity_Generation))
			{
				egWraps.add(wrap);
				continue;
			}
			if (wrap.getStandardRecommendation().getStandardRecommendationGroup().equals(StandardRecommendationGroup.Lighting))
			{
				ltWraps.add(wrap);
				continue;
			}
		}
		if (!CollectionUtils.isEmpty(imWraps))
		{
			tmpWraps.addAll(imWraps);
		}
		if (!CollectionUtils.isEmpty(hhwWraps))
		{
			tmpWraps.addAll(hhwWraps);
		}
		if (!CollectionUtils.isEmpty(wdWraps))
		{
			tmpWraps.addAll(wdWraps);
		}
		if (!CollectionUtils.isEmpty(egWraps))
		{
			tmpWraps.addAll(egWraps);
		}
		if (!CollectionUtils.isEmpty(ltWraps))
		{
			tmpWraps.addAll(ltWraps);
		}
		return tmpWraps;
		
	}
	
	@Override
	public List<StandardRecommendationWrap> getStandardRecommendationWraps(long reportId, SolutionType recommendationType)
	{
		Report report = this.getReportServiceMgr().getReport(reportId);
		List<GdsapEvalRecommendation> models = this.getGdsapEvalRecommendationMapper().findByReportAndType(reportId, recommendationType.getCode());
		if (!CollectionUtils.isEmpty(models))
		{
			List<StandardRecommendationWrap> wraps = new ArrayList<StandardRecommendationWrap>();
			for (GdsapEvalRecommendation r : models)
			{
				StandardRecommendationWrap wrap = new StandardRecommendationWrap();
				wrap.setId(r.getId());
				wrap.setReport(report);
				wrap.setStandardRecommendation(this.getStandardRecommendation(r.getRecommendationCode(), report));
				wraps.add(wrap);
			}
			
			List<StandardRecommendationWrap> tmpWraps = new ArrayList<StandardRecommendationWrap>();
			
			//Insulation_Measures("Insulation Measures"),
			List<StandardRecommendationWrap> imWraps = new ArrayList<StandardRecommendationWrap>();
			//Heating_Hot_Water("Heating and Hot Water"),
			List<StandardRecommendationWrap> hhwWraps = new ArrayList<StandardRecommendationWrap>();
			//Windows_Doors("Windows and Doors"),
			List<StandardRecommendationWrap> wdWraps = new ArrayList<StandardRecommendationWrap>();
			//Electricity_Generation("Electricity Generation")
			List<StandardRecommendationWrap> egWraps = new ArrayList<StandardRecommendationWrap>();
			//Lighting("Lighting"),
			List<StandardRecommendationWrap> ltWraps = new ArrayList<StandardRecommendationWrap>();
			
			//排序遍历
			for (StandardRecommendationWrap wrap : wraps)
			{
				if (wrap.getStandardRecommendation().getStandardRecommendationGroup().equals(StandardRecommendationGroup.Insulation_Measures))
				{
					imWraps.add(wrap);
				}
				if (wrap.getStandardRecommendation().getStandardRecommendationGroup().equals(StandardRecommendationGroup.Heating_Hot_Water))
				{
					hhwWraps.add(wrap);
				}
				if (wrap.getStandardRecommendation().getStandardRecommendationGroup().equals(StandardRecommendationGroup.Windows_Doors))
				{
					wdWraps.add(wrap);
				}
				if (wrap.getStandardRecommendation().getStandardRecommendationGroup().equals(StandardRecommendationGroup.Electricity_Generation))
				{
					egWraps.add(wrap);
				}
				if (wrap.getStandardRecommendation().getStandardRecommendationGroup().equals(StandardRecommendationGroup.Lighting))
				{
					ltWraps.add(wrap);
				}
			}
			if (!CollectionUtils.isEmpty(imWraps))
			{
				tmpWraps.addAll(imWraps);
			}
			if (!CollectionUtils.isEmpty(hhwWraps))
			{
				tmpWraps.addAll(hhwWraps);
			}
			if (!CollectionUtils.isEmpty(wdWraps))
			{
				tmpWraps.addAll(wdWraps);
			}
			if (!CollectionUtils.isEmpty(egWraps))
			{
				tmpWraps.addAll(egWraps);
			}
			if (!CollectionUtils.isEmpty(ltWraps))
			{
				tmpWraps.addAll(ltWraps);
			}
			return tmpWraps;
		}
		return null;
	}
	
	@Override
	public StandardRecommendationWrap getStandardRecommendationWrap(long id)
	{
		GdsapEvalRecommendation model = this.getGdsapEvalRecommendationMapper().load(id);
		if (model != null)
		{
			Report report = this.getReportServiceMgr().getReport(model.getReportId());
			StandardRecommendationWrap wrap = new StandardRecommendationWrap();
			wrap.setId(model.getId());
			wrap.setReport(this.getReportServiceMgr().getReport(model.getReportId()));
			wrap.setStandardRecommendation(this.getStandardRecommendation(model.getRecommendationCode(), report));
			return wrap;
		}
		return null;
	}
	
	@Override
	public List<StandardRecommendationWrap> processNodataWraps(Report report, List<StandardRecommendationWrap> nodataWraps)
	{
		Assert.notEmpty(nodataWraps);
		List<StandardRecommendationWrap> newNodataWraps = new ArrayList<StandardRecommendationWrap>();
		String path = GlobalConfig.getInstance().getFSDir() + report.getLigXmlFile();
		String xmlContent = null;
		try {
			xmlContent = FileUtils.readFileToString(new File(path),"utf-8");
		} catch (IOException e) {
			e.printStackTrace();
			try {
				xmlContent = FileUtils.readFileToString(new File(path),"utf-16");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if(xmlContent != null)
		{
			Document gdarLigDoc = null;
			try {
				gdarLigDoc = DocumentUtil.stringToDocument(xmlContent);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			
			if(gdarLigDoc != null)
			{
				Element rootElt = gdarLigDoc.getRootElement();
				String defNamespace = rootElt.getNamespaceURI();
				XPath xpathSelector;
				Map<String, String> nameSpaceMap = new HashMap<String, String>();
				if(defNamespace != null)
				{
					nameSpaceMap.put("defu", defNamespace);
				}
				
				xpathSelector = DocumentHelper.createXPath("//defu:Occupancy-Assessment-Results/defu:Selected-Improvements/defu:Improvement");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				@SuppressWarnings("unchecked")
				List<Element> selectedImp = xpathSelector.selectNodes(gdarLigDoc);
				if(!CollectionUtils.isEmpty(selectedImp))
				{
					for(int i = 1; i <= selectedImp.size(); i++)
					{
						xpathSelector = DocumentHelper.createXPath("//defu:Occupancy-Assessment-Results/defu:Selected-Improvements/defu:Improvement" + "["+i+"]" + "/defu:Improvement-Type");
						xpathSelector.setNamespaceURIs(nameSpaceMap);
						Element improvementType = (Element)xpathSelector.selectSingleNode(gdarLigDoc);
						if(improvementType != null)
						{
							for(StandardRecommendationWrap srw : nodataWraps)
							{
								if(srw.getStandardRecommendation().getItem().trim().equalsIgnoreCase(improvementType.getTextTrim()) || 
										(improvementType.getTextTrim().equalsIgnoreCase("L") && srw.getStandardRecommendation().getItem().trim().equalsIgnoreCase("L2")))// 因为 L2改进 只有GDIP页面会出现
								{
									srw.getStandardRecommendation().setSelected(true);
									newNodataWraps.add(srw);
									if(!improvementType.getTextTrim().equalsIgnoreCase("L"))
									{
										break;
									}
								}
							}
						}
					}
					return newNodataWraps;
				}
				
			}
		}
		return nodataWraps;
	}
}
