<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

[<c:forEach var="cidade" items="${cidades}" varStatus="contador">
	{"label":"${cidade.nome}","value":${cidade.id}}
<c:if test="${fn:length(cidades) > contador.count }">,</c:if></c:forEach>]