package by.goncharov.epamsound.view.tag;

import by.goncharov.epamsound.beans.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class NotAdminTag extends TagSupport {
    private static final String USER_ATTR = "user";
    @Override
    public int doStartTag() throws JspException {
        User user = (User) pageContext.getSession().getAttribute(USER_ATTR);
        if (user != null) {
            if (user.getRole() == 0) {
                return EVAL_BODY_INCLUDE;
            } else {
                return SKIP_BODY;
            }
        } else {
            return SKIP_BODY;
        }
    }
}
