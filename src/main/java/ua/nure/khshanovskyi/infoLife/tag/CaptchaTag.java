package ua.nure.khshanovskyi.infoLife.tag;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class CaptchaTag extends SimpleTagSupport {

    @Override
    public void doTag() throws IOException {
        JspContext jspContext = getJspContext();

        JspWriter out = jspContext.getOut();

        out.println("<div class=\"form-group\" id=\"captcha\">");
        out.println("<img src=\"http://localhost:8880/captcha\" class=\"mx-auto d-block\" alt=\"captcha\">");
        out.println(String.format("<input value=\"%s\"type=\"hidden\" name=\"captchaKey\">",
                jspContext.findAttribute("captchaKey")));
        out.println("</div>");
        out.println("<input class=\"form-control\" placeholder=\"Captcha\" type=\"text\" name=\"captcha\">");
    }
}