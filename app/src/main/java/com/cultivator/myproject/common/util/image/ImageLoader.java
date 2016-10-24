package com.cultivator.myproject.common.util.image;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.cultivator.myproject.R;
import com.cultivator.myproject.common.log.MyLog;

/**
 * @author: xin.wu
 * @create time: 2016/5/18 10:51
 */
public final class ImageLoader {

    /**
     * TODO 图片下载
     * @param context
     * @param url
     * @param imageView
     */
    public static void displayImage(Context context, String url,final ImageView imageView){
        MyLog.d(ImageLoader.class, "url:" + url);
        Glide.with(context.getApplicationContext())
                .load(url)
                .dontAnimate()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.default_image)
                .animate(new ViewPropertyAnimation.Animator() {
                    @Override
                    public void animate (View view) {
                        view.setAlpha( 0f );
                        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat( view, "alpha", 0f, 1f );
                        fadeAnim.setDuration(800);
                        fadeAnim.start();
                    }
                })
                .into(imageView);
    }

    public static void displayImage(Context context, String url,final ImageView imageView,boolean istransparent) {
        MyLog.d(ImageLoader.class, "url:" + url);
        if (istransparent) {
            Glide.with(context.getApplicationContext())
                    .load(url)
                    .dontAnimate()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.default_image)
                    .animate(new ViewPropertyAnimation.Animator() {
                        @Override
                        public void animate(View view) {
                            view.setAlpha(0f);
                            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                            fadeAnim.setDuration(800);
                            fadeAnim.start();
                        }
                    })
                    .into(imageView);
        }
    }

}
