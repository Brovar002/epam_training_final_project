package by.goncharov.epamsound.view.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * The type Not deleted tag.
 * @author Goncharov Daniil
 */
public class NotDeletedTag extends TagSupport {
    private static final String IS_DELETED = "is_deleted";
    @Override
    public int doStartTag() throws JspException {
        Object isDeleted = pageContext.getSession().getAttribute(IS_DELETED);
        if (isDeleted == null || !Boolean.parseBoolean(isDeleted.toString())) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}
