package uk.co.quidos.gdsap.framework.log.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

/**
 * @author peng.shi
 */
public class GdsapAltType extends AbstractDO 
{	
	private static final long serialVersionUID = -2671371118193256078L;
	private String id;
    private String title;
    private String defContent;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDefContent() {
        return defContent;
    }

    public void setDefContent(String defContent) {
        this.defContent = defContent;
    }
}