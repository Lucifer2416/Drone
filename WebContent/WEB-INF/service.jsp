<%@page import="util.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="<%=Const.PATH_HEAD_JSP%>"/>
<jsp:include page="<%=Const.PATH_MENU_JSP%>"/>
<div class="container">
	<h1>Repair service</h1>
	<div class="row">
		<div class="col-sm-4 col-lg-4 col-md-4">
            <div class="well">
        		<h2 class="muted">Money</h2>
        		<p><span class="label label-default">POPULAR</span></p>
        		<ul>
        			<li>Problem starting up?</li>
        			<li>Antenna problem?</li>
        			<li>Dusting</li>
        			<li>Support E-mail</li>
        		</ul>          
        		<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta.</p>
        		<hr>
        		<h3>15.99$ / hour</h3>
        		<hr>
        		<p><a class="btn btn-primary" href="#"><i class="icon-ok"></i> Select plan</a></p>
        	</div>
        </div>
        <div class="col-sm-4 col-lg-4 col-md-4">
        	<div class="well">
        		<h2 class="text-warning">Bronze</h2>
        		<p><span class="label label-success">POPULAR</span></p>
        		<ul>
        			<li>Problem starting up ?</li>
        			<li>Antenna problem ?</li>
        			<li>Dusting</li>
        			<li style="visibility: hidden;"></li>
        		</ul>          
        		<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta.</p>
        		<hr>
        		<h3>10.99$ / hour</h3>
        		<hr>
        		<p><a class="btn btn-success btn-large" href="#"><i class="icon-ok"></i> Select plan</a></p>
        	</div>
        </div>
        <div class="col-sm-4 col-lg-4 col-md-4">
        	<div class="well">
        		<h2 class="text-info">Economy</h2>
        		<p><span class="label label-info">BUDGET</span></p>
        		<ul>
        			<li>Problem starting up ?</li>
        			<li>Antenna problem</li>
        			<li style="visibility: hidden;"></li>
        			<li style="visibility: hidden;"></li>
        		</ul>          
        		<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta.</p>
        		<hr>
        		<h3>8.99$ / hour</h3>
        		<hr>
              <p><a class="btn btn-primary" href="#"><i class="icon-ok"></i> Select plan</a></p>
        	</div>
        </div>
	</div>
</div>
<jsp:include page="<%=Const.PATH_FOOTER_JSP%>"/>