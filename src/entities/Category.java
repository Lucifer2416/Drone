package entities;

public class Category {
	private int id,orderOfAppearance;
	
	private String name,
		description;
	
	public Category() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderOfAppearance() {
		return orderOfAppearance;
	}

	public void setOrderOfAppearance(int orderOfAppearance) {
		this.orderOfAppearance = orderOfAppearance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category(int id, int orderOfAppearance, String name, String description) {
		super();
		this.id = id;
		this.orderOfAppearance = orderOfAppearance;
		this.name = name;
		this.description = description;
	}
	
	
}
