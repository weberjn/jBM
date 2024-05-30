<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<%@include file="top.jsp"%>


<h2>My Profile</h2>

<c:if test="${saved}">
	<p class="success">Changes saved.</p>
</c:if>


<form action="profile" method="post">

	<h3>Account Details</h3>

	<table class="profile">
		<tr>
			<th align="left">Username</th>
			<td>${user.username}</td>
			<td></td>
		</tr>
		<tr>
			<th align="left">E-mail</th>
			<td><input type="text" name="pMail" size="75"
				value="${user.email}" /></td>
			<td>* Required</td>
		</tr>
		<tr>
			<th align="left">Private RSS Feed</th>
			<td><input type="checkbox" id="pEnablePrivateKey"
				name="pEnablePrivateKey" value="true" /> <label
				for="pEnablePrivateKey">Enable</label>&nbsp;&nbsp;&nbsp; <input
				type="text" id="pPrivateKey" name="pPrivateKey" size="40" value=""
				readonly="readonly" /> <a
				onclick="getNewPrivateKey(this); return false;"><button
						type="submit" name="submittedPK" value="1">Generate New
						Key</button></a></td>
		</tr>
	</table>


	<h3>Personal Details</h3>

	<table class="profile">
		<tr>
			<th align="left">Name</th>
			<td><input type="text" name="pName" size="75"
				value="${user.name}" /></td>
		</tr>
		<tr>
			<th align="left">Homepage</th>
			<td><input type="text" name="pPage" size="75"
				value="${user.homepage}" /></td>
		</tr>
		<tr>
			<th align="left">Description</th>
			<td><textarea name="pDesc" cols="75" rows="10">${user.content}</textarea></td>
		</tr>
		<tr>
			<th></th>
			<td><input type="submit" name="submitted" value="Save Changes" /></td>
		</tr>
	</table>

</form>

<%@include file="bottom.jsp"%>
