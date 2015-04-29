package uk.co.quidos.gdsap.framework.log.persistence.object;

import java.util.Date;

import uk.co.quidos.dal.object.AbstractDO;

/**
 * @author peng.shi
 */
public class GdsapAltLog extends AbstractDO {

	private static final long serialVersionUID = 3538472990523394243L;
	private String id;
    private Date insertTime;
    private Integer level;
    private String ipAddress;
    private Integer userType;
    private Long userId;
    private String typeId;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}