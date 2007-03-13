<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<HTML>
<HEAD>
    <TITLE>JSP invoke ejb</TITLE>
    <%@ page import="java.util.Date" %>
    <%@ page import="javax.naming.Context" %>
    <%@ page import="net.sourceforge.jfox.ejb3.naming.JNDIContextHelper" %>
    <%@ page import="org.jfox.example.ejb3.stateless.Calculator" %>
</HEAD>
<BODY>

<H1>JSP invoke stateless ejb</H1>
<p>

<%
    try {
        int p1 = Integer.parseInt(request.getParameter("p1"));
        int p2 = Integer.parseInt(request.getParameter("p2"));
        Context context = JNDIContextHelper.getInitalContext();
        Calculator calculator = (Calculator)context.lookup("stateless.CalculatorBean/remote");
        int r1 = calculator.add(p1, p2);
        int r2 = calculator.subtract(p1, p2);
        out.println(p1 + " + " + p2 + " = " + r1);
        out.println("<br>");
        out.println(p1 + " - " + p2 + " = " + r2);
    }
    catch (Exception e) {
        out.println(e.getMessage());
    }
%>
</BODY>
</HTML>
