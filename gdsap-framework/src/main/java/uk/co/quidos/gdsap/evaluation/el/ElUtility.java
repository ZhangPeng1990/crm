package uk.co.quidos.gdsap.evaluation.el;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.util.Assert;

import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.evaluation.SolutionIssue;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationCalResult;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationWrap;
import uk.co.quidos.gdsap.evaluation.enums.ReportStatus;
import uk.co.quidos.gdsap.framework.enums.YesNo;

public class ElUtility {

	public static boolean contains(Object o, Collection<?> c) {
		if (c == null) {
			return false;
		}

		return c.contains(o);
	}
	
	public static StandardRecommendationWrap containSRs(StandardRecommendationWrap wrap,List<StandardRecommendationCalResult> calResults)
	{
		Assert.notNull(wrap);
		Assert.notEmpty(calResults);
		for (StandardRecommendationCalResult calResult : calResults)
		{
			if (wrap.getId() == calResult.getStandardRecommendationWrap().getId())
			{
				return calResult.getStandardRecommendationWrap();
			}
		}
		return null;
	}
	
	public static StandardRecommendationCalResult containCurrentCalResult(StandardRecommendationWrap wrap,List<StandardRecommendationCalResult> calResults)
	{
		Assert.notNull(wrap);
		Assert.notEmpty(calResults);
		for (StandardRecommendationCalResult calResult : calResults)
		{
			if (wrap.getId() == calResult.getStandardRecommendationWrap().getId())
			{
				return calResult;
			}
		}
		return null;
	}
	/**
	 * 获取Conditions
	 * @param item
	 * @param solutionIssue
	 * @return
	 */
	public static List<String> getConditions(String item,SolutionIssue solutionIssue) {
		System.out.println("el utils getConditions ");
		Assert.hasText(item);
		Assert.notNull(solutionIssue);
		List<String> strs = new ArrayList<String>();
		if (item.equalsIgnoreCase("A")) {
			if (solutionIssue.getNoLoftAccess().equals(YesNo.Yes)) {
				strs.add("Access to the loft is needed to establish existing loft insulation.");
			}
		}
		if (item.equalsIgnoreCase("A2")) {
			if (solutionIssue.getFlatRoofInsulationUnknown().equals(YesNo.Yes)) {
				strs.add("Flat roof insulation improvement requires that the existing insulation is ascertained.");
			}
		}
		if (item.equalsIgnoreCase("A3")) {
			if (solutionIssue.getRoofRoomInsulationUnknown().equals(YesNo.Yes)) {
				strs.add("Roof room insulation improvement requires that the existing insulation is ascertained.");
			}
		}
		if (item.equalsIgnoreCase("B")) {
			if (solutionIssue.getCavityFillUnknown().equals(YesNo.Yes)) {
				strs.add("A borescope examination is needed to establish whether cavity insulation is already present.");
			}
			if (solutionIssue.getNarrowCavities().equals(YesNo.Yes)) {
				strs.add("The walls may have narrow cavities. This should be checked to establish a suitable type of cavity wall insulation.");
			}
			if (solutionIssue.getAccessIssues().equals(YesNo.Yes)) {
				strs.add("Establish whether access to applicable walls can be arranged.");
			}
			if (solutionIssue.getHighExposure().equals(YesNo.Yes)) {
				strs.add("Suitability for cavity fill should be checked in relation to local exposure conditions and wall construction");
			}
			if (solutionIssue.getSystemBuild().equals(YesNo.Yes)) {
				strs.add("Some of the walls are system built or of non-conventional construction. It needs to be established that these walls are suitable for cavity insulation");
			}
			if (solutionIssue.getStoneWalls().equals(YesNo.Yes)) {
				strs.add("It should be established whether stone walls are of cavity construction and suitable for cavity fill.");
			}
		}
		if (item.equalsIgnoreCase("Q2")) {
			if (solutionIssue.getCavityFillUnknown().equals(YesNo.Yes)) {
				strs.add("A borescope examination is needed to establish whether cavity insulation is already present.");
			}
		}
		if (item.equalsIgnoreCase("Q")) {
			if (solutionIssue.getSolidWallInsulationUnknown().equals(YesNo.Yes)) {
				strs.add("It needs to be established whether the solid walls are insulated.");
			}
		}
		if (item.equalsIgnoreCase("C")) {
			if (solutionIssue.getNoCylinderAccess().equals(YesNo.Yes)) {
				strs.add("The existing cylinder insulation is not known. Access to the cylinder is needed to establish this.");
			}
		}
		if (item.equalsIgnoreCase("F")) {
			if (solutionIssue.getNoCylinderAccess().equals(YesNo.Yes)) {
				strs.add("It is not known whether a cylinder thermostat is present. Access to the cylinder is needed to establish this.");
			}
		}
		if (item.equalsIgnoreCase("W")) {
			if (solutionIssue.getFloorInsulationUnknown().equals(YesNo.Yes)) {
				strs.add("Floor insulation improvement requires that the existing insulation is ascertained.");
			}
		}
		if (item.equals("T")) {
			if (solutionIssue.getMainsGasNeeded().equals(YesNo.Yes)) {
				strs.add("Assumes that mains gas can be made available to the property. The cost of providing the gas supply needs to be ascertained.");
			}
		}
		if (item.equalsIgnoreCase("T+T2")) {
			if (solutionIssue.getMainsGasNeeded().equals(YesNo.Yes)) {
				strs.add("Assumes that mains gas can be made available to the property. The cost of providing the gas supply needs to be ascertained.");
			}
		}
		if (item.equalsIgnoreCase("Z3")) {
			if (solutionIssue.getMainsGasNeeded().equals(YesNo.Yes)) {
				strs.add("Assumes that mains gas can be made available to the property. The cost of providing the gas supply needs to be ascertained.");
			}
		}
		return strs;
	}
	
	public static boolean editable(Report report) 
	{
		Assert.notNull(report);
		if (report.getReportStatus().equals(ReportStatus.In_Process)) {
			return true;
		}
		return false;
	}
	
	public static boolean gdipEditable(Report report) 
	{
		Assert.notNull(report);
		if (report.getReportStatus().getCode() == ReportStatus.GDIP_In_Process.getCode()) {
			return true;
		}
		return false;
	}
	
	public static boolean isLodged(Report report)
	{
		Assert.notNull(report);
		if (report.getReportStatus().equals(ReportStatus.Lodged) || report.getReportStatus().getCode() > ReportStatus.Lodged.getCode()) {
			return true;
		}
		return false;
	}
	
	public static boolean isGdipLodged(Report report)
	{
		Assert.notNull(report);
		if (report.getReportStatus().getCode() == ReportStatus.GDIP_Lodged_By_Assessor.getCode() || report.getReportStatus().getCode() == ReportStatus.GDIP_Lodged_By_Provider.getCode() ||
				report.getReportStatus().getCode() == ReportStatus.GDIP_reLodged_By_Assessor.getCode() || report.getReportStatus().getCode() == ReportStatus.GDIP_reLodged_By_Provider.getCode()) 
		{
			return true;
		}
		return false;
	}
	
	public static boolean isDoneGdip(Report report)
	{
		Assert.notNull(report);
		if (report.getReportStatus().getCode() >= ReportStatus.GDIP_In_Process.getCode()) {
			return true;
		}
		return false;
	}
}
