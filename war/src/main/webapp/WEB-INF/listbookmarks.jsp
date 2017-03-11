<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@include file="top.jsp"%>

<h2>All Bookmarks</h2>


<form id="search"
	action="${context}/bookmarks/search" method="post">
	<table>
		<tbody>
			<tr>
				<td><input name="terms" size="30" value=""	type="text"></td>
				<td><input type="submit" name="search" value="Search"></td>
			</tr>
		</tbody>
	</table>
</form>


<p id="sort">${navigator.bookmarksCount} bookmark(s) 
	<c:if test="${not empty tag}">for tag ${tag.tag}</c:if>
	<c:if test="${not empty query}">for query ${query}</c:if>
</p>



<ol id="bookmarks">

	<c:forEach items="${bookmarks}" var="bookmark">

		<fmt:formatDate value="${bookmark.modified}" var="bmodified"
			type="date" pattern="yyyy-MM-dd HH:mm" />

		<li class="xfolkentry private">
			<div class="adminBackground">
				<div class="link">
					<a href="${bookmark.address}" rel="nofollow" class="taggedlink">${bookmark.title}</a>
				</div>
				<div class="${bookmark.description}"></div>
				<div class="address">
					<a href="${bookmark.address}">${bookmark.address}</a>
				</div>
				<div class="meta">
					Tags: 
					    <c:forEach items="${bookmark.tags}" var="tag">
						    <a href="${context}/bookmarks/list/1/${tag.id}">${tag.tag}</a>
						</c:forEach>
						
						by ${bookmark.user.username} <small title="Last update">${bmodified}</small>
						<a href="${context}/bookmark/edit/${bookmark.id}">Edit</a>
					
				</div>
			</div>
		</li>
	</c:forEach>
</ol>


<p class="backToTop">
	<a href="#header" title="Come back to the top of this page.">Top of
		the page</a>
</p>
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


<%@include file="bottom.jsp"%>

