package com.example.demo.dto;

public class RelatedColorDTO {
	private String color;
	

	private boolean isRemained = false;
	
	public RelatedColorDTO() {
		// TODO Auto-generated constructor stub
	}


	public RelatedColorDTO(String color, boolean isRemained) {
		this.color = color;
		this.isRemained = isRemained;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isRemained() {
		return isRemained;
	}

	public void setRemained(boolean isRemained) {
		this.isRemained = isRemained;
	}
	
}
