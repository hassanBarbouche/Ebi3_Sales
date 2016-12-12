package com.esprit.android.ebi3;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.esprit.android.ebi3.Utils.ImageUtil;
import com.esprit.android.ebi3.Utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.samsung.android.sdk.iap.lib.helper.SamsungIapHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hassan on 02/02/16.
 */
public class Welcome2 extends Fragment {
    RequestQueue requestQueue1;
    String showUrl = "http://www.e-bi3.com/server/getpromo.php";
    protected ImageLoader imageLoader;
    private static final int RightToLeft = 1;
    private static final int LeftToRight = 2;
    private static final int DURATION = 30000;
    private RectF mDisplayRect = new RectF();
    private final Matrix mMatrix = new Matrix();
    private int mDirection = RightToLeft;
    private ValueAnimator mCurrentAnimator;
    private ImageView profileImg;
    private float mScaleFactor;
    protected ImageView mBackground;
    private TextView titre,desc;
    private Button Skip;
    private static final int IAP_MODE = SamsungIapHelper.IAP_MODE_TEST_SUCCESS;
    // ========================================================================

    // Item ID for test button of purchase one item
    // ========================================================================
    private static final String ITEM_ID          = "PromoteEbi3";
    // ========================================================================

    // Item ID for test button of cached items inbox list
    // ========================================================================
    private static final String ITEM_IDS         = "Nuclear, Claymore, " +
            "Blockbuster";

    @Bind(R.id.image2)
    ImageView mImageView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NotifService.passenow=false;
        System.out.println(NotifService.passenow + " this is pasinng");
        View view = inflater.inflate(R.layout.welcome_2,container,false);
        ButterKnife.bind(this, view);
        Skip = (Button) view.findViewById(R.id.skip2);
        titre = (TextView) view.findViewById(R.id.titre);
        desc = (TextView) view.findViewById(R.id.ProductDesc);
        imageLoader = ImageLoader.getInstance();
        profileImg = (ImageView) view.findViewById(R.id.Profile1);
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity().getApplicationContext()));



        Map<String, String> params = new HashMap<String, String>();
        params = new HashMap<String, String>();
        params.put("pager_number", "2");

        requestQueue1 = Volley.newRequestQueue(getActivity().getApplicationContext());
        //System.out.println("ww");
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Chargement...","Un petit moment svp...",false,false);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, showUrl, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                try {

                    JSONArray listproduit = response.getJSONArray("product");
                    if(listproduit.length()!=0) {
                        for (int i = 0; i < 1; i++) {

                            JSONObject nourriture = listproduit.getJSONObject(i);


                            ImageUtil.displayImage(mImageView, nourriture.getString("promo_photo"), null);
                            titre.setText(nourriture.getString("name_promo"));
                            desc.setText(nourriture.getString("description_promo"));
                            ImageUtil.displayRoundImage(profileImg, nourriture.getString("ImagePath"), null);

                        }


                    }









                    loading.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.append(error.getMessage());
                loading.dismiss();
            }
        });

        requestQueue1.add(jsObjRequest);


        Skip.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                Intent intent = new Intent(
                        getActivity(),
                        Main.class);
                intent.putExtra("from", "one");

                startActivity(intent);
                getActivity().finish();


                return false;
            }
        });
        mBackground = mImageView;
        return  view;


    }


    protected void moveBackground() {
        if (Utils.hasHoneycomb()) {
            mBackground.post(new Runnable() {
                @Override
                public void run() {
                    mScaleFactor = (float) mBackground.getHeight() / (float) mBackground.getDrawable().getIntrinsicHeight();
                    mMatrix.postScale(mScaleFactor, mScaleFactor);
                    mBackground.setImageMatrix(mMatrix);
                    animate();
                }
            });
        }
    }
    protected void moveBackground2() {
        if (Utils.hasHoneycomb()) {
            mBackground.post(new Runnable() {
                @Override
                public void run() {
                    mScaleFactor = (float) mBackground.getHeight() / (float) mBackground.getDrawable().getIntrinsicHeight();
                    mMatrix.postScale(mScaleFactor, mScaleFactor);
                    mBackground.setImageMatrix(mMatrix);
                    animate2();
                }
            });
        }
    }


    private void animate() {
        updateDisplayRect();
        if (mDirection == RightToLeft) {
            animate(mDisplayRect.left, mDisplayRect.left - (mDisplayRect.right - mBackground.getWidth()));
        } else {
            animate(mDisplayRect.left, 0.0f);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        NotifService.passenow=false;
        System.out.println(NotifService.passenow + " this is pasinng");
    }

    private void animate2() {
        updateDisplayRect();
        if (mDirection == RightToLeft) {
            animate(mDisplayRect.left, mDisplayRect.left - (mDisplayRect.right - (mBackground.getWidth()+800)));
        } else {
            animate(mDisplayRect.left, 0.0f);
        }
    }


    private void updateDisplayRect() {
        mDisplayRect.set(0, 0, mBackground.getDrawable().getIntrinsicWidth(), mBackground.getDrawable().getIntrinsicHeight());
        mMatrix.mapRect(mDisplayRect);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void animate(float from, float to) {
        mCurrentAnimator = ValueAnimator.ofFloat(from, to);
        mCurrentAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();

                mMatrix.reset();
                mMatrix.postScale(mScaleFactor, mScaleFactor);
                mMatrix.postTranslate(value, 0);

                mBackground.setImageMatrix(mMatrix);

            }
        });
        mCurrentAnimator.setDuration(DURATION);
        mCurrentAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mDirection == RightToLeft)
                    mDirection = LeftToRight;
                else
                    mDirection = RightToLeft;

                animate();
            }
        });
        mCurrentAnimator.start();
    }
}
