package com.ebits.restro.activities.utils;
/***
 * Created by Mr. Satyaki Mukherjee  on 14/07/16
 */

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.ebits.restro.R;

public class CustomDialogClass {

	public static TransparentProgressDialog pd;
	/***
	 * Show Progress Dialog
	 * */
	public static void showProgressDialog(Context context,boolean flag)
	{
		if(flag)
		{
			pd = new TransparentProgressDialog(context, R.mipmap.ic_launcher);
			pd.show();
		}
		else
		{			
			pd.dismiss();			
		}
	}

	public static boolean isShowProgress(){
		if(pd.isShowing())
			return true;
		else
			return false;
	}
	
	public static class TransparentProgressDialog extends Dialog {
		
		private ImageView iv;
			
		public TransparentProgressDialog(Context context, int resourceIdOfImage) {
			super(context, R.style.TransparentProgressDialog);
			
	        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
	        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
	        getWindow().setAttributes(wlmp);
			setTitle(null);
			setCancelable(false);
			setOnCancelListener(null);
			LinearLayout layout = new LinearLayout(context);
			layout.setOrientation(LinearLayout.VERTICAL);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			iv = new ImageView(context);
			iv.setImageResource(resourceIdOfImage);
			layout.addView(iv, params);
			addContentView(layout, params);
		}			
		

		@Override
		public void show() {
			super.show();
			RotateAnimation anim = new RotateAnimation(0.0f, 360.0f , Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
			anim.setInterpolator(new LinearInterpolator());
			anim.setRepeatCount(Animation.INFINITE);
			anim.setDuration(3000);
			iv.setAnimation(anim);
			iv.startAnimation(anim);
		}
	}
}
