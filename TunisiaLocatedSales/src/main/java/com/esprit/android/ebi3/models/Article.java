package com.esprit.android.ebi3.models;

public class Article {
	public int Image;
	public String Titre;
	public String Desc;
	
	public Article() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Article(int image, String titre, String desc) {
		super();
		Image = image;
		Titre = titre;
		Desc = desc;
	}
	public int getImage() {
		return Image;
	}
	public void setImage(int image) {
		Image = image;
	}
	public String getTitre() {
		return Titre;
	}
	public void setTitre(String titre) {
		Titre = titre;
	}
	public String getDesc() {
		return Desc;
	}
	public void setDesc(String desc) {
		Desc = desc;
	}
	
	
}
