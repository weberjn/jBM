
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<p class="paging">


	<c:choose> 
		<c:when test="${!empty navigator.first}">
			<a href="${context}/bookmarks/${navigator.first}">First</a> 
		</c:when> 
		<c:otherwise> 
			<span class="disable">First</span><span> / </span>
		</c:otherwise> 
	</c:choose> 


	<c:choose> 
		<c:when test="${!empty navigator.previous}">
			<a href="${context}/bookmarks/${navigator.previous}">Previous</a> 
		</c:when> 
		<c:otherwise> 
			<span class="disable">Previous</span><span> / </span>
		</c:otherwise> 
	</c:choose> 


	<c:choose> 
		<c:when test="${!empty navigator.next}">
			<a href="${context}/bookmarks/${navigator.next}">Next</a> 
		</c:when> 
		<c:otherwise> 
			<span class="disable">Next</span><span> / </span>
		</c:otherwise> 
	</c:choose> 

	<c:choose> 
		<c:when test="${!empty navigator.last}">
			<a href="${context}/bookmarks/${navigator.last}">Last</a> 
		</c:when> 
		<c:otherwise> 
			<span class="disable">Last</span><span> / </span>
		</c:otherwise> 
	</c:choose> 
	Page ${navigator.current} of ${navigator.pageCount}
</p>

