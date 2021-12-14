package by.goncharov.epamsound.view.tag;

import by.goncharov.epamsound.beans.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * The type Admin tag.
 * @author Goncharov Daniil
 * @see User
 */
public class AdminTag extends TagSupport {
    private static final String USER_ATTR = "user";
    @Override
    public int doStartTag() throws JspException {
        User user = (User) pageContext.getSession().getAttribute(USER_ATTR);
        if (user != null) {
            if (user.getRole() == 1) {
                return EVAL_BODY_INCLUDE;
            } else {
                return SKIP_BODY;
            }
        } else {
            return SKIP_BODY;
        }
    }
}
