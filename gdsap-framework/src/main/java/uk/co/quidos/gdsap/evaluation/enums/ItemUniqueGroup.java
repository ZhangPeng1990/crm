/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.enums;

/**
 * @author peng.shi
 *
 */
public enum ItemUniqueGroup
{
	Group1("G1",new String[]{"B","Q2"}),
	Group2("G2",new String[]{"I", "J", "K", "L", "M", "R", "S", "T", "Z1", "Z4", "Z2", "Z5", "Z3", "T+T2"}),
	Group3("G3",new String[]{"O","O2","P"}),
	Group4("G4",new String[]{"V","V2"}),
	Group5("G5",new String[]{"N","Y2"}),
	
	// TODO G6和G5冲突，目前用js控制了,想办法看看能不能统一成一种方式
//	Group6("G6",new String[]{"Y2","Y"}),
	;
		
	private String[] items;
	private String groupName;
	
	private ItemUniqueGroup(String groupName,String[] items)
	{
		this.groupName = groupName;
		this.items = items;
	}

	public String[] getItems()
	{
		return items;
	}
	
	public String getGroupName()
	{
		return groupName;
	}
	
}
