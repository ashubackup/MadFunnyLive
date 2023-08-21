<%@page import="gen.DataCollector"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@page import="gen.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%
	try {
		String data  = request.getQueryString();
		System.out.println("query string " + data);
		String ani = "";
		ani = request.getParameter("msisdn");

		DataCollector obj = new DataCollector();

		String countyCode = "27";
		int len = countyCode.length();
		if (ani.substring(0, len).equals(countyCode))
			ani = ani.substring(len);
		if (ani.startsWith("0"))
			ani = ani.substring(1);

		System.out.println("This is form query paramete === " + ani);

		String status = obj.getStatus(ani);
		if (status.equalsIgnoreCase("1")) {
			session.setAttribute("ani", ani);
			String reDirectURL = "home";
			response.sendRedirect(reDirectURL);
			System.out.println("Status for sub user: " + status);
		} else {
			out.println("<script type=\"text/javascript\">");
			out.println("location='http://optin.telkomsdp.co.za/service/248';");
			out.println("</script>");
			System.out.println("User not Sub");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
%>

<html>
	<head>
		<base href="<%=basePath%>">

		<title>Redirection</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
		<br>
	</body>
</html>
