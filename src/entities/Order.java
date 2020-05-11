package entities;

public class Order extends Cart{
	private static final long serialVersionUID = 1L;
	
	int id;
	User user;
	int userId;
	String date;
	boolean isShipped;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	public Order(int id, User user, String date, boolean isShipped) {
		super();
		this.id = id;
		this.user = user;
		this.date = date;
		this.isShipped = isShipped;
	}
	public boolean isShipped() {
		return isShipped;
	}
	public void setShipped(boolean isShipped) {
		this.isShipped = isShipped;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Order() {
		super();
	}
	
	
}
