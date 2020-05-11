<%@page import="entities.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entities.Item"%>
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
	
	ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
	
	int action=Integer.parseInt(request.getParameter("a"));
	Item item=null;
	System.out.println(this.getServletContext().getContextPath()+" "+this.getServletContext().getRealPath("/"));
	if(action==1){
		item = (Item)request.getAttribute("item");
		
	}
	
		
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
			 if (error.equals("DBProblem")) {
	%>
	<div class="alert alert-danger">A connection error is produced.
		Please wait a while before making a new one attempt. If you see this
		message for the second time, please contact the site administrator to
		inform them of the problem.</div>
	<%
		}
		}
	%>

	<form action="add-product" method="post"
		class="panel panel-primary form-horizontal"
		style="float: unset; margin: auto;">
		<div class="panel-heading">
			<h3 class="panel-title">Add New Product to site</h3>
		</div>
		<div class="panel-body">
			<fieldset class="col-sm-6 col-lg-6 col-md-6">

				<legend>New Product Information</legend>
				<input type="hidden" id="id"
					value=<%= item!=null?item.getName():"" %> class="form-control"
					name="id"
					}
							value="<%=Misc.getOrDefault(hm_formParamValue, "Name", "")%>" />
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
							type="text" id="Name" value="<%= item!=null?item.getName():"" %>"
							class="form-control" name="Name"
							}
							value="<%=Misc.getOrDefault(hm_formParamValue, "Name", "")%>"
							required />
					</div>
				</div>
				<%
					if (hm_fieldErrorMsg != null && hm_fieldErrorMsg.containsKey("firstName")) {
				%>
				<div class="alert alert-warning"
					style="margin-bottom: 0px; white-space: pre-line;"><%=hm_fieldErrorMsg.get("firstName")%></div>
				<%
					}
				%>
				<div class="form-group">
					<div class="col-sm-10">
						<label for="category" class="col-sm-2 control-label">Category</label>
						<!-- <input type="text" id="category" class="form-control"
							name="category"
							value="<%=Misc.getOrDefault(hm_formParamValue, "Category", "")%>"
							required />-->
						<select id="Category" class="form-control" name="Category">
							<option value="">select category</option>
							<%
								if (categories.size() > 0) {
									for (Category category : categories) {
							%>

							<option value=<%=category.getId()%>
								<%=item!=null?(category.getId() == item.getCategory() ? "selected" : ""):""%>><%=category.getName()%></option>
							<%
								}
								}
							%>
						</select>
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
						<label for="desc" class="col-sm-2 control-label">Description</label>
						<textarea id="desc" class="form-control" name="desc" rows="7"
							required><%=item != null ? item.getDescription() : ""%></textarea>
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
						<label for="price" class="col-sm-2 control-label"
							style="padding-top: 0px;">price </label> <input type="number"
							step="0.01" id="price" class="form-control" name="price"
							placeholder="" value="<%=item != null ? item.getPrice() : ""%>"
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
						<label for="serial" class="col-sm-2 control-label"
							style="padding-top: 0px;">serial number </label> <input
							type="text" id="serial" class="form-control" name="serial"
							placeholder="" value="<%=item != null ? item.getSerial() : ""%>"
							required />
					</div>
				</div>

			</fieldset>
			<fieldset class="col-sm-6 col-lg-6 col-md-6">
				<legend>Product Details</legend>
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
						<label for="stock" class="col-sm-2 control-label"
							style="padding-top: 0px;">Stock </label> <input type="number"
							id="stock" class="form-control" name="stock" step="1"
							value="<%=item != null ? item.getStock() : ""%>" required />
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
				<div class="form-group login-group-checkbox">
					<input id="isactive" name="isactive" type="checkbox"
						<%=item != null ? (item.isActive() ? "checked" : "") : " "%>
						id="isactive" name="isactive"> <label for="isactive">do
						you want to make it live?</label>
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
