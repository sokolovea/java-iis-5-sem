<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Cache-Control" content="no-cache">
<html><head><title>Error Page</title></head>
<body>
 Request from ${pageContext.errorData.requestURI} is failed
 <br/>
 Servlet name or type: ${pageContext.errorData.servletName}
 <br/>
 Status code: ${pageContext.errorData.statusCode}
 <br/>
 Exception: ${pageContext.errorData.throwable}
</body></html>