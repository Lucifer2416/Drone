package manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionLogin;
import entities.Cart;
import entities.Category;
import entities.Item;
import entities.ItemCart;
import entities.Order;
import entities.User;

public class MOrder {
	public static int add(User user, Cart cart) {
		
		int orderId = 0;
		
		try {
			// TODO Faire une transaction
			MDB.connect();

			// Partie 1
			// Creer une commande et récupere le ID
			String query = "INSERT INTO `orders` (`user_id`, `date`, `isShipped`) VALUES (?, now(),false)";
			
			PreparedStatement ps = MDB.getPS(query, 1);

			ps.setInt(1, user.getId());

			ps.executeUpdate();
			
			ResultSet generatedKeys = ps.getGeneratedKeys();
            
			if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            }
			
			// Partie 2
			// Ajoute tout les items de la commande dans la table order_info
			
			for (ItemCart itemC : cart.getCart().values()) {
				
				query = "INSERT INTO `order_info` (`order_id`, `product_id`, `qty`, `price`) VALUES (?, ?, ?, ?)";
				ps = MDB.getPS(query);
				
				ps.setInt(1, orderId);
				ps.setInt(2, itemC.getId());
				ps.setInt(3, itemC.getQty());
				ps.setDouble(4, itemC.getPrice());
				
				ps.executeUpdate();
				
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MDB.disconnect();
		}

		return orderId;
		
	}
	public static List<Order> getAllOrdersByUserId(int userId){
		
		List<Order> orderList= new ArrayList<Order>();
		
		try {
			
			MDB.connect();

			String query = "SELECT `orders`.id, `orders`.date,`orders`.isShipped,"
					+ " `order_info`.order_id, `order_info`.product_id, `order_info`.qty, `order_info`.price "
					+ "FROM `orders`  INNER JOIN `order_info` ON `orders`.id = `order_info`.order_id WHERE `orders`.user_id = ?;";
			System.out.println(query);
			PreparedStatement ps = MDB.getPS(query);

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			int orderId = 0;
			
			Order order = new Order();
			
			// Une ligne = un item avec qte
			while(rs.next()) {
				
				// Si nouvel commande, creer une nouvelle commande
				if(orderId != rs.getInt("id")){
					
					// Ecraser le orderId de condition
					orderId = rs.getInt("id");
					
					// Nouvelle commande
					order = new Order();
					
					order.setId(orderId);
					order.setUserId(userId);
					order.setDate(rs.getString("orders.date"));
					order.setShipped(rs.getBoolean("orders.isShipped"));
					// Ajouter la commande a la liste	
					orderList.add(order);
				}
				
				// Recupérer l'item suivant
				int itemId = rs.getInt("order_info.product_id");
				
				// Recuperer l'item complet de la BD
				Item item = MItem.getItemById(itemId);
				
				// Ecraser le prix et ajouter la quantité
				ItemCart itemC = new ItemCart(item);
				itemC.setQty(rs.getInt("order_info.qty"));
				itemC.setPrice(rs.getDouble("order_info.price"));
				
				// Ajouter l'itemPanier au panier
				order.addItem(itemC.getSerial(), itemC);
			}
			
			//orderList.add(order);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MDB.disconnect();
		}
		
		return orderList;
	}
	public static ArrayList<Order> getOrders(HttpServletRequest request, HttpServletResponse response){
		ArrayList<Order> orders = new ArrayList<Order>();
		String query ;
		try {
			MDB.connect();
					
				query =  "SELECT * FROM orders";
			

				
			ResultSet rs = MDB.execQuery(query);
			while(rs.next()) {
				User user=ActionLogin.getUserById(rs.getInt("user_id"), request, response);
				orders.add(new Order(rs.getInt("id"),user,rs.getString("date"),rs.getBoolean("isShipped")));	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			MDB.disconnect();	
		}
		
		return orders;
	}
	
}
