<%@page import="util.Misc"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="util.Const"%>
<jsp:include page="<%=Const.PATH_HEAD_JSP%>" />
<jsp:include page="<%=Const.PATH_MENU_JSP%>" />

<%
	@SuppressWarnings("unchecked")
	HashMap<String, String> hm_formParamValue = (HashMap<String, String>) request
			.getAttribute("hm_formParamValue");
	@SuppressWarnings("unchecked")
	HashMap<String, String> hm_fieldErrorMsg = (HashMap<String, String>) request
			.getAttribute("hm_fieldErrorMsg");
	String error = (String) request.getAttribute("error");
%>
<!-- Page Content -->
<div class="container">
	<%
		if (request.getParameter("fromCart") != null) {
	%>
	<div class="progress-cart">
		<span class="label label-success">Panier</span> <span
			class="label label-success">Sommaire</span> <span
			class="label label-warning">Compte</span> <span
			class="label label-warning">Facture</span>
	</div>
	<%
		}
	%>
	<%
		if (error != null) {
			if (error.equals("accountExisting")) {
	%>
	<div class="alert alert-info">An account already exists for this email address.</div>
	<%
		} else if (error.equals("DBProblem")) {
	%>
	<div class="alert alert-danger">A connection error is produced.
		Please wait a while before making a new one attempt. If you see this
		message for the second time, please contact the site administrator to
		inform them of the problem.</div>
	<%
		}
		}
	%>

	<form action="signup" method="post"
		class="panel panel-primary form-horizontal"
		style="float: unset; margin: auto;">
		<div class="panel-heading">
			<h3 class="panel-title">Add New category to site</h3>
		</div>
		<div class="panel-body">
			<fieldset class="col-sm-6 col-lg-6 col-md-6">
				<legend>New category Information</legend>
				<%
					if (hm_fieldErrorMsg != null && hm_fieldErrorMsg.containsKey("lastName")) {
				%>
				<div class="alert alert-warning"
					style="margin-bottom: 0px; white-space: pre-line;"><%=hm_fieldErrorMsg.get("lastName")%></div>
				<%
					}
				%>
				<div class="form-group">
					<div class="col-sm-10">
						<label for="Name" class="col-sm-2 control-label">Name</label> <input
							type="text" id="Name" class="form-control" name="Name"
							value="<%=Misc.getOrDefault(hm_formParamValue, "Name", "")%>"
							required />
					</div>
				</div>
				
				<%
					if (hm_fieldErrorMsg != null && hm_fieldErrorMsg.containsKey("email")) {
				%>
				<div class="alert alert-warning"
					style="margin-bottom: 0px; white-space: pre-line;"><%=hm_fieldErrorMsg.get("email")%></div>
				<%
					}
				%>
				<div class="form-group">
					<div class="col-sm-10">
						<label for="email" class="col-sm-2 control-label">Description</label>
						<input type="email" id="email" class="form-control" name="email"
							value="<%=Misc.getOrDefault(hm_formParamValue, "email", "")%>"
							required />
					</div>
				</div>
				
				<%
					if (hm_fieldErrorMsg != null && hm_fieldErrorMsg.containsKey("confirmEmail")) {
				%>
				<div class="alert alert-warning"
					style="margin-bottom: 0px; white-space: pre-line;"><%=hm_fieldErrorMsg.get("confirmEmail")%></div>
				<%
					}
				%>
				<div class="form-group">
					<div class="col-sm-10">
						<label for="confirmEmail" class="col-sm-2 control-label"
							style="padding-top: 0px;">order </label> <input
							type="email" id="confirmEmail" class="form-control"
							name="confirmEmail" placeholder=""
							value="<%=Misc.getOrDefault(hm_formParamValue, "confirmEmail", "")%>"
							required />
					</div>
				</div>

			</fieldset>
			<fieldset class="col-sm-6 col-lg-6 col-md-6">
				<legend>category Details</legend>
				
				<%
					if (hm_fieldErrorMsg != null && hm_fieldErrorMsg.containsKey("confirmEmail")) {
				%>
				<div class="alert alert-warning"
					style="margin-bottom: 0px; white-space: pre-line;"><%=hm_fieldErrorMsg.get("confirmEmail")%></div>
				<%
					}
				%>
				<div class="form-group login-group-checkbox">
					<input id="remember" name="remember" type="checkbox"> <label
						for="remember">do you want to make it live?</label>
				</div>
			</fieldset>
			<%
				if (request.getParameter("fromCart") != null) {
			%>
			<input type="hidden" name="fromCart" value="true">
			<%
				}
			%>
			<div class="form-group text-center "
				style="clear: left; top: 15px; margin-bottom: 15px;">
				<button type="submit" class="btn btn-default">Add..</button>
			</div>
		</div>
	</form>
</div>
<!-- Footer -->
<jsp:include page="<%=Const.PATH_FOOTER_JSP%>" />
