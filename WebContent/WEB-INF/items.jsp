<%@page import="java.text.DecimalFormat"%>
<%@page import="entities.Item"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entities.Category"%>
<%@page import="entities.Cart"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.Const"%>
<%
@SuppressWarnings("unchecked")
	ArrayList<Item> items = (ArrayList<Item>) request.getAttribute("items");
%>

<jsp:include page="<%=Const.PATH_HEAD_JSP%>"/>
<jsp:include page="<%=Const.PATH_MENU_JSP%>"/>
	<!-- /.container -->
    <!-- Page Content -->
    <div class="container">

        <div class="row">
        

        

            <div class="col-md-3">
            	<div class="panel panel-default">
                	<div class="panel-heading">
                		<p class="lead">category</p>
                	</div>
                	<div class="list-group">
<jsp:include page="<%=Const.PATH_CATEGORY_JSP%>"/>
                	</div>
                </div>
            </div>
            <div class="col-md-9">
            	<div class="row">
<%
	//Format a deux decimal
	DecimalFormat df = new DecimalFormat("####0.00");
	
	if(items.size() > 0) {
		for(Item item : items) {
%>
		            <div class="col-sm-4 col-lg-4 col-md-4">
					    <div class="thumbnail">
					    	<div style="max-width: 250px; max-height: 250px; text-aling: center; line-height: 250px;">
					        	<img src="<%=Const.PATH_IMG_PRODUCT+item.getImage()%>" alt="">
					        </div>
					        <div class="caption">
					            <h4><a href="item?item=<%=item.getId()%>"><%=item.getName()%></a></h4>
					            <h4 class="pull-right"><%=df.format(item.getPrice()) + "$"%></h4>
					            <p><%=item.getDescription()%></p>
					        </div>
					        <div class="ratings">
					            <p class="pull-right">0 reviews</p>
					            <p>
					                <span class="glyphicon glyphicon-star"></span>
					                <span class="glyphicon glyphicon-star"></span>
					                <span class="glyphicon glyphicon-star"></span>
					                <span class="glyphicon glyphicon-star"></span>
					                <span class="glyphicon glyphicon-star"></span>
					            </p>
					        </div>
					        <div>
					        	<form method="post" id="form-add-item">
					        		<input type="hidden" name="itemId" value="<%=item.getId()%>">
							        <button type="submit" class="btn btn-info">
										<span class="glyphicon glyphicon-shopping-cart"></span> Add</button>
										<input type="number" value="1" min="1" max="999" name="qty" class="item-qty form-control">
								</form>
							</div>
					    </div>
					</div>
<%
		}
	}
	else {
%>
		<div class="alert alert-info">
			No product corresponds to your request.
		</div>
<%
	}
%>
				</div>
            </div>
		</div>
    </div>
<jsp:include page="<%=Const.PATH_FOOTER_JSP%>"/>