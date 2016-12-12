package com.esprit.android.ebi3.models;

public class Produit {
	public String ProductImage;
	public String ProductImage2;
	public String ProductImage3;
	public String ProductImageTitle;
	public String ProductImage2Title;
	public String ProductImageT3itle;
	public String ProductImageDescription;
	public String ProductImage2Description;
	public String ProductImage3Description;

	public String UserName;
	public String Titre;
	public String Desc;
	public String Lieu;
	public String Quantite;
	public String Date;
	public float Prix;
    public User user;
	public Integer Id;



	public Produit(String productImage, String titre, String desc, String lieu, String quantite, String date, float prix, User user) {
		ProductImage = productImage;
		Titre = titre;
		Desc = desc;
		Lieu = lieu;
		Quantite = quantite;
		Date = date;
		Prix = prix;
		this.user = user;
	}

	public Produit (String productImage, String productImage2, String productImage3, String productImageTitle, String productImage2Title, String productImageT3itle, String productImageDescription, String productImage2Description, String productImage3Description, String titre, String desc, String lieu, String quantite, String date, float prix, User user) {

		ProductImage = productImage;
		ProductImage2 = productImage2;
		ProductImage3 = productImage3;
		ProductImageTitle = productImageTitle;
		ProductImage2Title = productImage2Title;
		ProductImageT3itle = productImageT3itle;
		ProductImageDescription = productImageDescription;
		ProductImage2Description = productImage2Description;
		ProductImage3Description = productImage3Description;

		Titre = titre;
		Desc = desc;
		Lieu = lieu;
		Quantite = quantite;
		Date = date;
		Prix = prix;
		this.user = user;
	}

	public Produit (String productImage, String productImage2, String productImage3, String productImageTitle, String productImage2Title, String productImageT3itle, String productImageDescription, String productImage2Description, String productImage3Description, String titre, String desc, String lieu, String quantite, String date, float prix) {

		ProductImage = productImage;
		ProductImage2 = productImage2;
		ProductImage3 = productImage3;
		ProductImageTitle = productImageTitle;
		ProductImage2Title = productImage2Title;
		ProductImageT3itle = productImageT3itle;
		ProductImageDescription = productImageDescription;
		ProductImage2Description = productImage2Description;
		ProductImage3Description = productImage3Description;

		Titre = titre;
		Desc = desc;
		Lieu = lieu;
		Quantite = quantite;
		Date = date;
		Prix = prix;

	}

	public Produit (String productImage, String productImage2, String productImage3, String productImageTitle, String productImage2Title, String productImageT3itle, String productImageDescription, String productImage2Description, String productImage3Description, String titre, String desc, String lieu, String quantite, String date, float prix,int id) {

		ProductImage = productImage;
		ProductImage2 = productImage2;
		ProductImage3 = productImage3;
		ProductImageTitle = productImageTitle;
		ProductImage2Title = productImage2Title;
		ProductImageT3itle = productImageT3itle;
		ProductImageDescription = productImageDescription;
		ProductImage2Description = productImage2Description;
		ProductImage3Description = productImage3Description;

		Titre = titre;
		Desc = desc;
		Lieu = lieu;
		Quantite = quantite;
		Date = date;
		Prix = prix;
		Id= id;

	}



	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



	public Produit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public Produit(String productImage, String titre, String desc, String lieu, String quantite, String date, float prix, String username) {
		ProductImage = productImage;

		Titre = titre;
		Desc = desc;
		Lieu = lieu;
		Quantite = quantite;
		Date = date;
		Prix = prix;
		UserName = username;
	}

	public void setProductImage(String productImage) {
		ProductImage = productImage;
	}



	public void setTitre(String titre) {
		Titre = titre;
	}

	public void setDesc(String desc) {
		Desc = desc;
	}

	public void setLieu(String lieu) {
		Lieu = lieu;
	}

	public void setQuantite(String quantite) {
		Quantite = quantite;
	}

	public void setDate(String date) {
		Date = date;
	}

	public void setPrix(float prix) {
		Prix = prix;
	}

	public String getProductImage() {
		return ProductImage;
	}



	public String getTitre() {
		return Titre;
	}

	public String getDesc() {
		return Desc;
	}

	public String getLieu() {
		return Lieu;
	}

	public String getQuantite() {
		return Quantite;
	}

	public String getDate() {
		return Date;
	}

	public float getPrix() {
		return Prix;
	}

	public String getProductImage2() {
		return ProductImage2;
	}

	public void setProductImage2(String productImage2) {
		ProductImage2 = productImage2;
	}

	public String getProductImage3() {
		return ProductImage3;
	}

	public void setProductImage3(String productImage3) {
		ProductImage3 = productImage3;
	}

	public String getProductImageTitle() {
		return ProductImageTitle;
	}

	public void setProductImageTitle(String productImageTitle) {
		ProductImageTitle = productImageTitle;
	}

	public String getProductImage2Title() {
		return ProductImage2Title;
	}

	public void setProductImage2Title(String productImage2Title) {
		ProductImage2Title = productImage2Title;
	}

	public String getProductImageT3itle() {
		return ProductImageT3itle;
	}

	public void setProductImageT3itle(String productImageT3itle) {
		ProductImageT3itle = productImageT3itle;
	}

	public String getProductImageDescription() {
		return ProductImageDescription;
	}

	public void setProductImageDescription(String productImageDescription) {
		ProductImageDescription = productImageDescription;
	}

	public String getProductImage2Description() {
		return ProductImage2Description;
	}

	public void setProductImage2Description(String productImage2Description) {
		ProductImage2Description = productImage2Description;
	}

	public String getProductImage3Description() {
		return ProductImage3Description;
	}

	public void setProductImage3Description(String productImage3Description) {
		ProductImage3Description = productImage3Description;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}
}
