package app.tags;

import java.util.Calendar;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Jsp tag support class
 */
public class MyNameSupport extends TagSupport {
    public int doStartTag() {
        JspWriter out = pageContext.getOut();
        try {
            out.print("Developed by Sitnikov Dmytro [" + Calendar.getInstance().getTime()+"]");
        } catch(Exception e) {
            System.out.println(e);
        }
        return SKIP_BODY;
    }
}