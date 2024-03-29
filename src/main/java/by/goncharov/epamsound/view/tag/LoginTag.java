package by.goncharov.epamsound.view.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * The type Login tag.
 * @author Goncharov Daniil
 */
public class LoginTag extends TagSupport {
    private static final String IS_LOGIN = "is_login";
    @Override
    public int doStartTag() throws JspException {
        Object isLogin = pageContext.getSession().getAttribute(IS_LOGIN);
        if (isLogin != null && Boolean.parseBoolean(isLogin.toString())) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}
