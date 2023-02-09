package com.example.demo.dto;

import java.util.List;

public class SizeDTO {
	private String size;
	
	private List<RelatedColorDTO> relatedColors;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public List<RelatedColorDTO> getRelatedColors() {
		return relatedColors;
	}

	public void setRelatedColors(List<RelatedColorDTO> relatedColors) {
		this.relatedColors = relatedColors;
	}
	

}
