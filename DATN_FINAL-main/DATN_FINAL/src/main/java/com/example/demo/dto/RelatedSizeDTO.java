package com.example.demo.dto;

public class RelatedSizeDTO {
	private String size;
	

	private boolean isRemained =false;
	
	public RelatedSizeDTO() {
		// TODO Auto-generated constructor stub
	}

	public RelatedSizeDTO(String size, boolean isRemained) {
		this.size = size;
		this.isRemained = isRemained;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public boolean isRemained() {
		return isRemained;
	}

	public void setRemained(boolean isRemained) {
		this.isRemained = isRemained;
	}
	
	
}
