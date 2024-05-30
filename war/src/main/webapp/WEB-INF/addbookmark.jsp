<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<%@include file="top.jsp"%>

				<c:choose>
					<c:when test="${bmop=='add'}">
						<c:set var="btSubText" scope="request" value="Add Bookmark"/>
						<c:set var="h2" scope="request" value="Add a Bookmark"/>
					</c:when> 
					<c:otherwise> 
						<c:set var="btSubText" scope="request" value="Save Changes"/>
						<c:set var="h2" scope="request" value="Edit Bookmark"/>
					</c:otherwise> 
				</c:choose>

<h2>${h2}</h2>



<form action="${context}/bookmark/${cmd}" method="post">
	<input type="hidden" name="bmID" value="${bm.id}">
	<table>
		<tr>
			<th align="left">Address</th>
			<td><input type="text" id="address" name="address" size="75"
				maxlength="65535" value="${bm.address}"/>
				<input type="hidden" name="bmop" value="${bmop}">
				</td>
			<td><input type="submit" name="addBookmark" value="get it"/></td>
		</tr>
		<tr>
			<th align="left">Title</th>
			<td><input type="text" id="titleField" name="title" size="75"
				maxlength="255" value="${bm.title}" /></td>
			<td>* Required</td>
		</tr>
		<tr>
			<th align="left">Description</th>
			<td><textarea name="description" id="description" rows="5"
					cols="63">${bm.description}</textarea></td>
			<td></td>
		</tr>

		<tr>
			<th align="left">Tags</th>
			<td class="scuttletheme"><input type="text" id="tags"
				name="tags" size="75" value="${keywords}" /></td>
			<td>* Comma-separated</td>
		</tr>
		<tr>
			<th></th>
		</tr>
		<tr>
			<th align="left">Privacy</th>
			<td><select name="status">
					<option value="0">Public</option>
					<option value="1">Shared with Watch List</option>
					<option value="2" selected="selected">Private</option>
			</select></td>
			<td></td>
		</tr>

		
		<tr>
			<td></td>
			<td><input type="submit" name="addBookmark" 
						value="${btSubText}"	/>
 				<c:if test="${bmop=='edit'}"> 				
					<input	type="submit" name="deleteBookmark" value="Delete Bookmark" />
				</c:if> 
				</td>
			<td></td>
		</tr>
	</table>
</form>


<%@include file="bottom.jsp"%>
