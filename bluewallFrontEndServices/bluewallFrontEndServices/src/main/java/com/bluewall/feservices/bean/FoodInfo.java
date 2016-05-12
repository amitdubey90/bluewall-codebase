package com.bluewall.feservices.bean;


import lombok.Data;

@Data
public class FoodInfo {

	private int foodId;
	private String foodName;
	private double foodCalorie;
	private String imageUrl;
}
