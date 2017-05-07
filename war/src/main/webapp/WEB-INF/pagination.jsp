
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<p class="paging">

<c:forEach items="${navigator.paginationPoints}" var="point">
	
	<c:choose>
	 	<c:when test="${empty point.href}">
			${point.a}
		</c:when>
		<c:otherwise> 
			<a href="${context}/bookmarks/${point.href}">${point.a}</a> 
		</c:otherwise> 
	</c:choose> 
	
</c:forEach>
</p>
	