<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<%@include file="top.jsp"%>


<h2>All Tags: ${user.username}</h2>


<p class="tags">

	<c:forEach items="${tags}" var="tag">
	
		<a href="${context}/bookmarks/list/1/${tag.id}" style="font-size:90%">${tag.tag}</a>

	</c:forEach>
</p>

<%@include file="bottom.jsp"%>
