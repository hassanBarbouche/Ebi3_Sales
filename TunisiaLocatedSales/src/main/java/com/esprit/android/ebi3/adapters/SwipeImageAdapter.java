package com.esprit.android.ebi3.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.esprit.android.ebi3.R;
import com.esprit.android.ebi3.Utils.ImageUtil;
import com.esprit.android.ebi3.models.Produit;

import java.util.List;

/**
 * Created by Hassan on 08/02/16.
 */


public class SwipeImageAdapter extends PagerAdapter {

    private Context ctx ;
    private LayoutInflater layoutInflater;
    public static boolean touched=false;
    public static String prodimg;
    public static String prodimg2;
    public static String prodimg3;
    public static String title1;
    public static String title2;
    public static String title3;
    public static String desc1;
    public static String desc2;
    public static String desc3;

    private List<Produit> imgList1,imgList2,imgList3;

    public SwipeImageAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater =(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.produc_image_pager,container,false);


        ImageView MyImage = (ImageView) item_view.findViewById(R.id.ProductImage);
        TextView Titre = (TextView) item_view.findViewById(R.id.ImageName);
        TextView Description = (TextView) item_view.findViewById(R.id.ImageDesc);

        if(position==0)

        {

        ImageUtil.displayImage(MyImage, prodimg, null);
        Titre.setText(title1.toString());
        Description.setText(desc1.toString());
        container.addView(item_view);


        }

        if(position==1)

        {   ImageUtil.displayImage(MyImage, prodimg2, null);
            Titre.setText(title2.toString());
            Description.setText(desc2.toString());
            container.addView(item_view);


        }

        if(position==2)

        {  ImageUtil.displayImage(MyImage, prodimg3, null);
            Titre.setText(title3.toString());
            Description.setText(desc3.toString());
            container.addView(item_view);


        }

//        container.addView(item_view);
        return item_view;







    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }

}
