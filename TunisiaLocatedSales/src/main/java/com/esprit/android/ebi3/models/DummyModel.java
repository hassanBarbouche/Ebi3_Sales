package com.esprit.android.ebi3.models;

public class DummyModel {
	
	private long mId;
	private String mImageURL;
	private String mText;
	private String Description;
	private int mIconRes;
	private int draw;
	private int nbr;

	public DummyModel() {
	}

	public DummyModel(long id, String imageURL, String text, int iconRes) {
		mId = id;
		mImageURL = imageURL;
		mText = text;
		mIconRes = iconRes;
	}

	public DummyModel(long id, int img , String text, String desc, int nomb) {

		mId = id;
		mText = text;
		Description = desc;
		draw = img;
		nbr = nomb;
	}
	public DummyModel(long id, int map) {

		mId = id;
		draw = map;
	}

	public long getId() {
		return mId;
	}

	public void setId(long id) {
		mId = id;
	}

	public String getImageURL() {
		return mImageURL;
	}

	public void setImageURL(String imageURL) {
		mImageURL = imageURL;
	}

	public String getText() {
		return mText;
	}

	public void setText(String text) {
		mText = text;
	}

	public int getIconRes() {
		return mIconRes;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int dra) {
		draw = dra;
	}
	public String getDescription() {
		return Description;
	}

	public void setIconRes(int iconRes) {
		mIconRes = iconRes;
	}

	@Override
	public String toString() {
		return mText;
	}
}
