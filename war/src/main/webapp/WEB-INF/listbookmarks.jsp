<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@include file="top.jsp"%>

<h2>All Bookmarks</h2>


<form id="search"
	action="//localhost/SemanticScuttle-0.98.5/www/search.php"
	method="post">
	<table>
		<tbody>
			<tr>

				<td><input name="terms" size="30" value="Search..."
					onfocus="if (this.value == 'Search...') this.value = '';"
					onblur="if (this.value == '') this.value = 'Search...';"
					type="text"></td>
				<td>in</td>
				<td><select name="range">
						<option value="intuser">my bookmarks</option>
						<option value="watchlist">my watchlist</option>
						<option value="all" selected="selected">all bookmarks</option>
				</select></td>

				<td><input value="Search" type="submit"></td>
			</tr>
		</tbody>
	</table>
</form>


<p id="sort">${bookmarksCount} bookmark(s)</p>



<ol id="bookmarks">

	<c:forEach items="${bookmarks}" var="bookmark">

		<fmt:formatDate value="${bookmark.modified}" var="bmodified"
			type="date" pattern="yyyy-MM-dd" />

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
						    <a href="${context}/bookmarks/list/tag/${tag.id}">${tag.tag}</a>
						</c:forEach>
						
						by ${bookmark.user.username} - <a
						href="${context}/bookmark/edit/${bookmark.id}">Edit</a> <a
						href="#"
						onclick="deleteBookmark(this, ${bookmark.id}); return false;">Delete</a>
					<small title="Last update">${bmodified}</small>
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
		<c:when test="${!empty pagePosition.first}">
			<a href="${context}/bookmarks/list/${pagePosition.first}">First</a> 
		</c:when> 
		<c:otherwise> 
			<span class="disable">First</span><span> / </span>
		</c:otherwise> 
	</c:choose> 


	<c:choose> 
		<c:when test="${!empty pagePosition.previous}">
			<a href="${context}/bookmarks/list/${pagePosition.previous}">Previous</a> 
		</c:when> 
		<c:otherwise> 
			<span class="disable">Previous</span><span> / </span>
		</c:otherwise> 
	</c:choose> 


	<c:choose> 
		<c:when test="${!empty pagePosition.next}">
			<a href="${context}/bookmarks/list/${pagePosition.next}">Next</a> 
		</c:when> 
		<c:otherwise> 
			<span class="disable">Next</span><span> / </span>
		</c:otherwise> 
	</c:choose> 

	<c:choose> 
		<c:when test="${!empty pagePosition.last}">
			<a href="${context}/bookmarks/list/${pagePosition.last}">Last</a> 
		</c:when> 
		<c:otherwise> 
			<span class="disable">Last</span><span> / </span>
		</c:otherwise> 
	</c:choose> 

<%@include file="bottom.jsp"%>

