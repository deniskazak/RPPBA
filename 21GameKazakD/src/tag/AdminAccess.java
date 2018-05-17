package tag;

import bean.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Custom jstl tag for admin access.
 */
public class AdminAccess extends BodyTagSupport {
    /**
     * Evaluate tag body.
     * @return Status.
     */
    @Override
    public int doStartTag() throws JspException {
        if (pageContext.getSession().getAttribute("user") == null){
            return SKIP_PAGE;
        }
        if (!((User)pageContext.getSession().getAttribute("user")).isAdmin()){
            return SKIP_BODY;
        } else return EVAL_BODY_INCLUDE;
    }
}
