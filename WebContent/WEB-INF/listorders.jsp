
<%@page import="entities.Order"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="entities.Item"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entities.Category"%>
<%@page import="entities.Cart"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="util.Const"%>
<%
@SuppressWarnings("unchecked")
	ArrayList<Order> orders = (ArrayList<Order>) request.getAttribute("orders");

%>


<jsp:include page="<%=Const.PATH_HEAD_JSP%>"/>
<jsp:include page="<%=Const.PATH_MENU_JSP%>"/>
   <div class="container">
  
	 
         
        <div class="row">
         <div class="col-md-12">
         <div class="table-responsive">
  <table class="table table-striped">
  	<thead class="thead-dark">
    <tr>
      <th scope="col">Date</th>
      <th scope="col">Client Name</th>
      <th scope="col">Order is shipped</th>
      <th scope="col">Action</th>
    </tr>
  </thead>
<%
DecimalFormat df = new DecimalFormat("####0.00");
	
	if(orders.size() > 0) {
		for(Order order : orders) {
%>
		<tr>
		<td><%=order.getDate()%></td>
		<td><%=order.getUser().getFirstName()+" "+order.getUser().getLastName()%></td>
		<td><%=order.isShipped()%></td>
		
		<a class="btn btn-primary" href="modify-order?order_id=<%= order.getId() %>" role="button" >Modify</a>
		<a class="btn btn-outline-danger" href="" onclick="myFunction()" role="button">Remove</a>
		</td>
		</tr>
<%
		}
	}
	else {
%>
		<tr>
		<td colspan="8" class="text-center">No Orders Found</td>
		</tr>
<%
	}
%>
            </table>
            </div>
		</div>
    </div>
    </div>
<jsp:include page="<%=Const.PATH_FOOTER_JSP%>"/>