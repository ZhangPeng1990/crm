/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import uk.co.quidos.gdsap.evaluation.Recommendation;
import uk.co.quidos.gdsap.evaluation.RelatedPartyDisclosure;
import uk.co.quidos.gdsap.evaluation.enums.Country;
import uk.co.quidos.gdsap.evaluation.enums.Language;
import uk.co.quidos.gdsap.evaluation.services.LandmarkDefineServiceMgr;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;
import uk.co.quidos.gdsap.framework.sys.lang.enums.EnumUtils;

/**
 * @author peng.shi
 *
 */
@Service("landmarkDefineServiceMgr")
public class LandmarkDefineServiceMgrImpl extends AbsBusinessObjectServiceMgr implements LandmarkDefineServiceMgr
{

	/**
	 * Recommendations 缓冲器
	 */
	private static Map<Country,Map<Language,List<Recommendation>>> recommendationCache = new LinkedHashMap<Country, Map<Language,List<Recommendation>>>();
	
	/**
	 * RelatedPartyDisclosure 缓冲器
	 */
	private static Map<Language,List<RelatedPartyDisclosure>> relatedPartyDisclosureCache = new LinkedHashMap<Language, List<RelatedPartyDisclosure>>();
	
	private static Object lock = new Object();
	
	private static String Landmark_Define_File = "/ExternalDefinitions.xml";
	
	public LandmarkDefineServiceMgrImpl()
	{
		synchronized (lock)
		{
			this._init();
		}
	}
	
	/**
	 * 初始化
	 */
	private void _init()
	{
		InputStream is = this.getClass().getResourceAsStream(Landmark_Define_File);
		SAXReader reader = new SAXReader();
		try
		{
			Document doc = reader.read(is);
			Element rootElement = doc.getRootElement();
			/*
			 * RelatedPartyDisclosure 初始化
			 */
			List<Element> relatedPartyDisclosureElements = rootElement.elements("Related-Party-Disclosure");
			if (CollectionUtils.isEmpty(relatedPartyDisclosureElements))
			{
				log.error("relatedPartyDisclosureElements is null. ");
				throw new ExceptionInInitializerError();
			}
			
			List<RelatedPartyDisclosure> enList = new ArrayList<RelatedPartyDisclosure>();
			List<RelatedPartyDisclosure> cyList = new ArrayList<RelatedPartyDisclosure>();
			
			for (Element rpdeElement : relatedPartyDisclosureElements)
			{
				Element code = rpdeElement.element("Disclosure-Code");
				List<Element> textElements = rpdeElement.elements("Disclosure-Text");
				if (CollectionUtils.isEmpty(textElements))
				{
					log.error("Disclosure-Code : " + code.getTextTrim() + " Disclosure-Text is null ");
					throw new ExceptionInInitializerError();
				}
				for (Element textElement : textElements)
				{
					if (textElement.attributeValue("language").equals(String.valueOf(Language.EN.getCode())))
					{
						RelatedPartyDisclosure rpd = new RelatedPartyDisclosure();
						rpd.setId(Integer.parseInt(code.getTextTrim()));
						rpd.setDesc(textElement.getTextTrim());
						rpd.setLanguage(Language.EN);
						enList.add(rpd);
					}
					if (textElement.attributeValue("language").equals(String.valueOf(Language.CY.getCode())))
					{
						RelatedPartyDisclosure rpd = new RelatedPartyDisclosure();
						rpd.setId(Integer.parseInt(code.getTextTrim()));
						rpd.setDesc(textElement.getTextTrim());
						rpd.setLanguage(Language.CY);
						cyList.add(rpd);
					}
				}
			}
			
			relatedPartyDisclosureCache.put(Language.CY, cyList);
			relatedPartyDisclosureCache.put(Language.EN, enList);
			
			/*
			 * Recommendations 初始化
			 */
			List<Element> countryElements = rootElement.elements("Country");
			if (CollectionUtils.isEmpty(countryElements))
			{
				log.error("Country null.");
				throw new ExceptionInInitializerError();
			}
			
			for (Element countryElement : countryElements)
			{
				Element countryCode = countryElement.element("Country-Code");
				Country country = (Country)EnumUtils.getByCode(countryCode.getTextTrim(),Country.class);
				
				List<Recommendation> enRecommendations = new ArrayList<Recommendation>();
				List<Recommendation> cyRecommendations = new ArrayList<Recommendation>();
				
				List<Element> recommendationElements = countryElement.elements("Recommendation");
				
				if (CollectionUtils.isEmpty(recommendationElements))
				{
					throw new ExceptionInInitializerError();
				}
				
				for (Element recommendationElement : recommendationElements)
				{
					Recommendation enr = this._getRecommendation(recommendationElement, Language.EN);
					enr.setCountry(country);
					Recommendation cyr = this._getRecommendation(recommendationElement, Language.CY);
					cyr.setCountry(country);
					enRecommendations.add(enr);
					cyRecommendations.add(cyr);
				}
				Map<Language,List<Recommendation>> recommendationsMap = new LinkedHashMap<Language, List<Recommendation>>();
				recommendationsMap.put(Language.CY, cyRecommendations);
				recommendationsMap.put(Language.EN, enRecommendations);
				
				recommendationCache.put(country, recommendationsMap);
			}
		} 
		catch (DocumentException e)
		{
			log.error("Maybe SAXReader inputstream is error.");
			e.printStackTrace();
			throw new ExceptionInInitializerError();
		}
	}
	
	/**
	 * 根据Language 获取Recommendation
	 * @param recommendation
	 * @param language
	 * @return
	 */
	private Recommendation _getRecommendation(Element recommendationElement,Language language)
	{
		Element idElement = recommendationElement.element("Improvement-Number");
		List<Element> summaryElements = recommendationElement.elements("Improvement-Summary");
		List<Element> headingElements = recommendationElement.elements("Improvement-Heading");
		List<Element> descriptionElements = recommendationElement.elements("Improvement-Description");
		
		Recommendation r = new Recommendation();
		r.setId(Integer.parseInt(idElement.getTextTrim()));
		if (!CollectionUtils.isEmpty(summaryElements))
		{
			for (Element summaryElement : summaryElements)
			{
				if (summaryElement.attributeValue("language").equals(String.valueOf(language.getCode())))
				{
					r.setSummary(summaryElement.getTextTrim());
					break;
				}
			}
		}
		if (!CollectionUtils.isEmpty(headingElements))
		{
			for (Element headingElement : headingElements)
			{
				if (headingElement.attributeValue("language").equals(String.valueOf(language.getCode())))
				{
					r.setHeading(headingElement.getTextTrim());
					break;
				}
			}
		}
		if (!CollectionUtils.isEmpty(descriptionElements))
		{
			for (Element descriptionElement : descriptionElements)
			{
				if (descriptionElement.attributeValue("language").equals(String.valueOf(language.getCode())))
				{
					r.setDescription(descriptionElement.getTextTrim());
					break;
				}
			}
		}
		r.setLanguage(language);
		return r;
	}
	
	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.LandmarkDefineServiceMgr#getRecommentation(int, uk.co.quidos.gdsap.evaluation.enums.Country, uk.co.quidos.gdsap.evaluation.enums.Language)
	 */
	@Override
	public Recommendation getRecommentation(int id, Country country, Language language)
	{
		Assert.notNull(country);
		Assert.notNull(language);
		if (id <0)
		{
			throw new IllegalArgumentException();
		}
		
		List<Recommendation> recommendations = recommendationCache.get(country).get(language);
		if (!CollectionUtils.isEmpty(recommendations))
		{
			for (Recommendation r : recommendations)
			{
				if (r.getId().equals(new Integer(id)))
				{
					return r;
				}
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.LandmarkDefineServiceMgr#getRecommentations(int[], uk.co.quidos.gdsap.evaluation.enums.Country, uk.co.quidos.gdsap.evaluation.enums.Language)
	 */
	@Override
	public List<Recommendation> getRecommentations(int[] ids, Country country, Language language)
	{
		List<Recommendation> rs = new ArrayList<Recommendation>();
		for (int id : ids)
		{
			Recommendation r = this.getRecommentation(id, country, language);
			if (r != null)
			{
				rs.add(r);
			}
		}
		return rs;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.LandmarkDefineServiceMgr#getRelatedPartyDisclosure(int, uk.co.quidos.gdsap.evaluation.enums.Language)
	 */
	@Override
	public RelatedPartyDisclosure getRelatedPartyDisclosure(int id, Language language)
	{
		if (id <0)
		{
			throw new IllegalArgumentException();
		}
		Assert.notNull(language);
		List<RelatedPartyDisclosure> rpds = relatedPartyDisclosureCache.get(language);
		if (!CollectionUtils.isEmpty(rpds))
		{
			for (RelatedPartyDisclosure r : rpds)
			{
				if (r.getId().equals(new Integer(id)))
				{
					return r;
				}
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.LandmarkDefineServiceMgr#getRelatedPartyDisclosures(uk.co.quidos.gdsap.evaluation.enums.Language)
	 */
	@Override
	public List<RelatedPartyDisclosure> getRelatedPartyDisclosures(Language language)
	{
		return relatedPartyDisclosureCache.get(language);
	}
	
}
