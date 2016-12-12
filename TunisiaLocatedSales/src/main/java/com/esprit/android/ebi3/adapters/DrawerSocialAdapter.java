package com.esprit.android.ebi3.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esprit.android.ebi3.Utils.DummyContent;
import com.esprit.android.ebi3.models.DummyModel;
import com.esprit.android.ebi3.R;

import java.util.List;

public class DrawerSocialAdapter extends BaseAdapter {

	private List<DummyModel> mDrawerItems;
	//private List<DummyModel> mDrawerItemsImage;
	private LayoutInflater mInflater;

	public DrawerSocialAdapter(Context context) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDrawerItems = DummyContent.getSocialDummyListImage();
	//	mDrawerItemsImage = DummyContent.getSocialDummyListImage();
	}

	@Override
	public int getCount() {
		return mDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mDrawerItems.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.list_view_item_navigation_drawer_social, parent,
					false);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView
					.findViewById(R.id.Icone_drawer);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.Desc = (TextView) convertView.findViewById(R.id.ListDesc);
			holder.line = (LinearLayout) convertView.findViewById(R.id.listbackground);
			convertView.setTag(holder);
		//	holder.back = (ImageView) convertView.findViewById(R.id.background_social_navigation_item);
		//	convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		DummyModel item = mDrawerItems.get(position);
		//DummyModel itemimage = mDrawerItemsImage.get(position);
       // holder.back.setImageResource(itemimage.getDd());
		holder.icon.setImageResource(item.getDraw());
		holder.title.setText(item.getText());
		holder.Desc.setText(item.getDescription());
		if(position==6)
		{
        holder.line.setBackgroundColor(Color.parseColor("#ffc000"));

		}
		if(position==7)
		{
			holder.line.setBackgroundColor(Color.parseColor("#d44a8d"));

		}
		if(position==8)
		{
			holder.line.setBackgroundColor(Color.parseColor("#1487b1"));

		}


		return convertView;
	}

	private static class ViewHolder {
		public ImageView icon;
		public/* Roboto */TextView title;
		public TextView Desc;
		public ImageView back;
		public View mv;
		public LinearLayout line;
	}
}
