package uk.co.quidos.gdsap.content.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapConArticleDetail extends AbstractDO{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7035344187659792833L;

	private Long articleId;

    private String content;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}