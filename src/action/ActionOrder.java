package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Cart;
import entities.Order;
import entities.User;
import manager.MItem;
import manager.MOrder;
import manager.MSendMail;

public class ActionOrder {
	
	// Methode executé lorse qu'une commande est completé
	public static int process(User user, Cart cart){
		
		// Ajoute la commande a la base de donnée
		int orderId = MOrder.add(user, cart);
		
		// Envoie d'un email de confirmation
		String to = user.getEmail();
		sendEmail(to);
		
		return orderId;
	}
	
	private static void sendEmail(String to){
		
		// Envoie d'un email de confirmation
		String sujet = "Payment Complete";
		String message = "congratulations, Your order Placed Successfully";
		
//		MSendMail.sendEmail(message, to, sujet);
		
	}
	
	public static List<Order> getHistoryByUserID(int userId){
		List<Order> orderList = MOrder.getAllOrdersByUserId(userId);
		
		return orderList;
	}

	public static void getOrders(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("orders",MOrder.getOrders(request,response));
	}																										
}
