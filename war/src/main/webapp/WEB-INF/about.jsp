<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@include file="top.jsp"%>


<h2>About</h2>

<ul>
<li><strong>Store</strong> all your favourite links in one place, accessible from anywhere.</li>
</ul>

<h3>Bookmarklet</h3>
<p id="bookmarklet">

<ul>
<li>
<a class="bookmarklet" href="javascript:location.href='${baseurl}${context}/bookmark/add?addBookmark=get it&address='+encodeURIComponent(location.href)">Send to jBM</a>
</li>
<li>
<a class="bookmarklet" href="javascript:(function(){window.open('${baseurl}${context}/bookmark/add?addBookmark=get it&address='+encodeURIComponent(location.href),'jBM','modal=1,status=0,scrollbars=1,toolbar=0,resizable=1,width='+(screen.width/2)+',height='+(screen.height/2));})();">Send to jBM in Popup</a>
</li>
</ul>

</p>

<h3>Tips</h3>

<ul>
<li>Add search plugin into your browser: <a href="#" onclick="window.external.AddSearchProvider('//localhost/SemanticScuttle-0.98.5/www/api/opensearch.php');">opensearch</a></li>
<li>The secret tag "system:unfiled" allows you to find bookmarks without tags.</li>
<li>The secret tag "system:imported" allows you to find imported bookmarks.</li>
</ul>


<%@include file="bottom.jsp"%>
