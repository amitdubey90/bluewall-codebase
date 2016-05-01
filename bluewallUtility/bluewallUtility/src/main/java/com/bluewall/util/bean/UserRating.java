package com.bluewall.util.bean;

/*
 * POJO representing user ratings on food items.s
 */

public class UserRating {
	
	private int foodID;
	private int rating;
	
	public int getFoodID() {
		return foodID;
	}
	public void setFoodID(int foodID) {
		this.foodID = foodID;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}

}
