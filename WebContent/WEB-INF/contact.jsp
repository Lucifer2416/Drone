<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="util.Const"%>
        <jsp:include page="<%=Const.PATH_HEAD_JSP%>" />
        <jsp:include page="<%=Const.PATH_MENU_JSP%>" />
        <!-- /.container -->
        <!-- Page Content -->
        <div class="container">
            <div class="col-md-5">
                <div class="form-area">
                    <form role="form" action="contact" method="post">
                        <h3 style="margin-bottom: 25px; text-align: center;">Contact Form</h3>
                        <div class="form-group">
                            <input type="text" class="form-control" id="name" name="name" placeholder="Name" required>
                        </div>
                        <div class="form-group">
                            <input type="email" class="form-control" id="email" name="email" placeholder="Email" required>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" id="subject" name="subject" placeholder="Subject" required>
                        </div>
                        <div class="form-group">
                            <textarea class="form-control" id="message" placeholder="Message" rows="7"></textarea>
                        </div>
                        <button type="submit" id="submit" class="btn btn-default pull-right">Send</button>
                    </form>
                </div>
            </div>
            <div class="col-md-5">
                <div class="span4">
                    <h2>ISI Drone</h2>
                    <address>
				2100 Boulevard Maisonneuve Est<br>
				1er étage<br>
				Montréal (Québec)<br>
				H2K 4S1<br>
				Email:info@isi-mtl.com<br>
				<abbr title="phone">Tel:</abbr> (514) 842-2426
			</address>
                </div>
                <div class="span8">
                    <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2795.232056829237!2d-73.55346228471987!3d45.52553557910173!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x4cc91ba4da67d76d%3A0xd4542d4fe9cc1aca!2s2100%20Boulevard%20de%20Maisonneuve%20East%201er%20%C3%A9tage%2C%20Montr%C3%A9al%2C%20QC%20H2K%204S1!5e0!3m2!1sen!2sca!4v1580757500968!5m2!1sen!2sca"
                        width="600" height="450" frameborder="0" style="border:0" allowfullscreen></iframe>
                       
                </div>
            </div>
        </div>
        <jsp:include page="<%=Const.PATH_FOOTER_JSP%>" />