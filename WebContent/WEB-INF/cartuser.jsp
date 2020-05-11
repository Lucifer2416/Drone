<%@page import="entities.Address"%>
<%@page import="entities.User"%>
<%@page import="util.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// Recuperer l'utilisateur
	User user = (User)session.getAttribute("user");
	Address address = user.getShipAddress();
%>
<jsp:include page="<%=Const.PATH_HEAD_JSP%>"/>
<jsp:include page="<%=Const.PATH_MENU_JSP%>"/>
<div class="container">
	<div class="progress-cart">
		<span class="label label-success">Panier</span>
		<span class="label label-success">Sommaire</span>
		<span class="label label-success">Compte</span>
		<span class="label label-warning">Facture</span>
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">
	    	<h3 class="panel-title">Compte</h3>
	  	</div>
	  	<div class="panel-body">
			<fieldset class="col-sm-6 col-lg-6 col-md-6">
			  <div class="panel">
			  	<legend>Information Utilisateur</legend>
			    <p>Last Name: <%=user.getLastName()%></p>
			    <p>First Name: <%=user.getFirstName()%></p>
			    <p>Email: <%=user.getEmail()%></p>
			  </div>
			</fieldset>
			<fieldset class="col-sm-6 col-lg-6 col-md-6">
			  	<legend>Delivery Adresse</legend>
			    <p>Civic Number: <%=address.getNo()%></p>
			    <%=((address.getAppt() == null)? "<p>Appartement: " + address.getAppt() + "</p>" : "")%>
			    <p>Street: <%=address.getStreet()%></p>
			    <p> Postal Code: <%=address.getZip()%></p>
			    <p>City: <%=address.getCity()%></p>
			    <p>State: <%=address.getState()%></p>
			    <p>Country: <%=address.getCountry()%></p>
			</fieldset>
		</div>
	</div>
	<form action="cartuser" method="post" style="float:left;margin-right:10px;">
	<button type="submit" name="toInvoice" class="btn btn-success">
		Confirm <span class="glyphicon glyphicon-play"></span>
	</button>
	</form>
	<button type="submit" name="modify" class="btn btn-default">
		Modify
	</button>
</div>
<!-- Footer -->
<jsp:include page="<%=Const.PATH_FOOTER_JSP%>"/>