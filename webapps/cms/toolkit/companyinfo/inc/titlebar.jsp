<%
if( titlebars != null ) {
    out.println("<table class=line width=100% border=0 cellspacing=0 cellpadding=0>");
    out.println("<tr bgcolor=#003366><td height=2 colspan=2></td></tr>");
    out.println("<tr><td width=50% class=line>");
    int i=0;
    while( titlebars.length > 1 && i < (titlebars.length-1) ) {
	     out.println("<a href=\"javascript:parent.document.location='index.jsp'\" target=_blank>");
	     out.println(titlebars[i][0] +"</a>&gt;");
	     i++;
    }
    if( titlebars.length >= 1 ) {
        out.println(titlebars[i][0]);
    }
    out.println("</td><td width=50% align=right class=line>");
    if (operations != null) {
        i=0;
        while( operations.length > 0 && i < operations.length ) {
            if(operations[i][1] != "")
                out.println("<a href="+ operations[i][1] +">"+ operations[i][0] +"</a>&nbsp;");
            else
                out.println(operations[i][0]);
	    i++;
        }
    }
    out.println("</td></tr>");
    out.println("<tr bgcolor=#003366><td colspan=2 height=2></td>");
    out.println("</tr></table>");
}
%>
