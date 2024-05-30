
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>


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
	