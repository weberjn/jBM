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

<%@include file="pagination.jsp"%>

<%@include file="navigator.jsp"%>

<ol id="bookmarks">

	<c:forEach items="${bookmarks}" var="bookmark">

		<fmt:formatDate value="${bookmark.modified}" var="bmodified"
			type="date" pattern="yyyy-MM-dd HH:mm" />

		<fmt:formatDate value="${bookmark.datetime}" var="bcreated"
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
						
						<small title="Last update">${bmodified} (${bcreated}) by ${bookmark.user.username}</small>
						<a href="${context}/bookmark/edit/${bookmark.id}">Edit</a>
					
				</div>
			</div>
		</li>
	</c:forEach>
</ol>

<%@include file="navigator.jsp"%>

<%@include file="pagination.jsp"%>



<%@include file="bottom.jsp"%>

