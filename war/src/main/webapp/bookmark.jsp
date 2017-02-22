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


<p id="sort">
	${bookmarksCount} bookmark(s) - Sort by: <a href="?sort=date_asc">Date
		↓</a> <span>/</span> <a href="?sort=title_asc">Title</a> <span>/</span>

</p>



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
					Tags: <a
						href="//localhost/SemanticScuttle-0.98.5/www/bookmarks.php/intuser/online"
						rel="tag">online</a>, <a
						href="//localhost/SemanticScuttle-0.98.5/www/bookmarks.php/intuser/zeitung"
						rel="tag">zeitung</a> by you - <a
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
	<span class="disable">First</span><span>${context}/bookmark/list/${pagePosition.first}</span><span class="disable">Previous</span><span>
		${context}/bookmark/list/${pagePosition.previous} </span><span class="disable">Next</span><span> ${context}/bookmark/list/${pagePosition.next} </span><span
		class="disable">Last</span> <span> / </span>Page
	${pagePosition.current} of ${pagePosition.last} <a
		style="background: #FFFFFF"
		href="//localhost/SemanticScuttle-0.98.5/www/rss.php?sort=date_desc"
		title="SemanticScuttle: Recent bookmarks"><img
		src="//localhost/SemanticScuttle-0.98.5/www/themes/default/images/rss.gif"
		alt="SemanticScuttle: Recent bookmarks" width="16" height="16"></a>
</p>


<p class="paging">
	<a href="//localhost/SemanticScuttle-0.98.5/www/bookmarks.php/intuser/">First</a><span>
		/ </span><a
		href="//localhost/SemanticScuttle-0.98.5/www/bookmarks.php/intuser/?page=1">Previous</a><span>
		/ </span><a
		href="//localhost/SemanticScuttle-0.98.5/www/bookmarks.php/intuser/?page=3">Next</a><span>
		/ </span><a
		href="//localhost/SemanticScuttle-0.98.5/www/bookmarks.php/intuser/?page=3">Last</a>
	<span> / </span>Page 2 of 3 <a style="background: #FFFFFF"
		href="//localhost/SemanticScuttle-0.98.5/www/rss.php/intuser?sort=date_desc"
		title="SemanticScuttle: My Bookmarks"><img
		src="//localhost/SemanticScuttle-0.98.5/www/themes/default/images/rss.gif"
		width="16" height="16" alt="SemanticScuttle: My Bookmarks" /></a>
</p>
<div id="sidebar" class="adminBackground">




	</body>
	</html>