package com.ebits.restro.activities.fragments;

/**
 * Created by Mr. Satyaki Mukherjee on 15-07-2016
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.ebits.restro.R;
import com.ebits.restro.activities.application.RestroApplication;
import com.ebits.restro.activities.utils.constant.Consts;
import com.ebits.restro.activities.utils.tools.CircularImageView;


public class FragmentDrawer extends Fragment {

    private static String TAG = FragmentDrawer.class.getSimpleName();

    private TextView nameTextView,timeTextView,outletTextView,profileId, logoutId, slaesId;
    private CircularImageView   profileImageView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private FragmentDrawerListener drawerListener;
    private SharedPreferences   sp;

    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sp  =   this.getActivity().getSharedPreferences(Consts.SP_NAME, Context.MODE_PRIVATE);

        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        profileImageView=   (CircularImageView) layout.findViewById(R.id.profileImageView);

        nameTextView    =   (TextView) layout.findViewById(R.id.nameTextView);
        timeTextView    =   (TextView) layout.findViewById(R.id.timeTextView);
        outletTextView  =   (TextView) layout.findViewById(R.id.outletTextView);
        profileId       =   (TextView) layout.findViewById(R.id.profileId);
        logoutId        =   (TextView) layout.findViewById(R.id.logoutId);
        slaesId         =   (TextView) layout.findViewById(R.id.slaesId);



        String imageUrl =   sp.getString("USERPHOTO","http://gshandicraftfashion.com/wp-content/themes/sw_chamy/assets/img/no-thumbnail.png");
        System.out.println("Image Tag- "+imageUrl);

        ImageLoader imageLoader = RestroApplication.getInstance().getImageLoader();

        profileImageView.setImageUrl(imageUrl,imageLoader);

        /*// If you are using normal ImageView
        imageLoader.get(imageUrl, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Image Load Error: " + error.getMessage());
                profileImageView.setImageResource(R.drawable.ic_profile);
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    profileImageView.setImageBitmap(response.getBitmap());
                }
            }
        });*/

        nameTextView.setText(sp.getString("NAME","Name"));
        timeTextView.setText("For "+sp.getString("CUR_TIME","Time"));
        String[]    tokenSession    =   sp.getString("SESSION","Session # 0").split("#");
        outletTextView.setText("For "+tokenSession[0]);

        profileId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.PROFILE);
                mDrawerLayout.closeDrawer(containerView);
            }
        });

        logoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.LOGOUT);
                mDrawerLayout.closeDrawer(containerView);
            }
        });

        slaesId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerListener.onDrawerItemSelected(view, Consts.SALES);
                mDrawerLayout.closeDrawer(containerView);
            }
        });

        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }
}
