
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
	ArrayList<Item> items = (ArrayList<Item>) request.getAttribute("items");
%>


<jsp:include page="<%=Const.PATH_HEAD_JSP%>"/>
<jsp:include page="<%=Const.PATH_MENU_JSP%>"/>
   <div class="container">
  
	 <div class="row">
         <div class="col-md-12">
         <a type="button" class="btn btn-primary" href="add-product?a=3">Add Product</a>
         </div>
         </div>
         
        <div class="row">
         <div class="col-md-12">
         <div class="table-responsive">
  <table class="table table-striped">
  	<thead class="thead-dark">
    <tr>
      <th scope="col">Name</th>
      <th scope="col">Category</th>
      <th scope="col">Description</th>
      <th scope="col">Price</th>
      <!-- <th scope="col">Serial number</th>-->
      <th scope="col">Items in stock</th>
      <th scope="col">Product is active</th>
      <th scope="col">Action</th>
    </tr>
  </thead>
<%
DecimalFormat df = new DecimalFormat("####0.00");
	
	if(items.size() > 0) {
		for(Item item : items) {
%>
		<tr>
		<td><%=item.getName()%></td>
		<td><%=item.getCategory()%></td>
		<td><%=item.getDescription().substring(0,20)+" ..."%></td>
		<td><%=item.getPrice()%></td>
		<!-- <td><%=item.getSerial()%></td> -->
		<td><%=item.getStock()%></td>
		<td><%=item.isActive()%></td>
		<td>
		<a class="btn btn-primary" href="add-product?a=1&product_id=<%= item.getId() %>" role="button" >Modify</a>
		<a class="btn btn-outline-danger" href="add-product?a=2&product_id=<%= item.getId() %>" onclick="return confirm('Are you sure you want to delete this item?');" role="button">Remove</a>
		</td>
		</tr>
<%
		}
	}
	else {
%>
		<tr>
		<td colspan="8" class="text-center">No Products Found</td>
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