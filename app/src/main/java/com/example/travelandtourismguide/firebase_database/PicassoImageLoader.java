package com.example.travelandtourismguide.firebase_database;

import android.content.Context;
import android.widget.ImageView;

import com.example.travelandtourismguide.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.veinhorn.scrollgalleryview.loader.MediaLoader;


public class PicassoImageLoader implements MediaLoader {
    private String url;
    private Integer thumbnailWidth;
    private Integer thumbnailHeight;

    public PicassoImageLoader(String url) {
        this.url = url;
    }

    public PicassoImageLoader(String url, Integer thumbnailWidth, Integer thumbnailHeight) {
        this.url = url;
        this.thumbnailWidth = thumbnailWidth;
        this.thumbnailHeight = thumbnailHeight;
    }

    @Override
    public boolean isImage() {
        return true;
    }

    @Override
    public void loadMedia(Context context, final ImageView imageView, final MediaLoader.SuccessCallback callback) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.black_background)
                .into(imageView, new ImageCallback(callback));
    }

    @Override
    public void loadThumbnail(Context context, final ImageView thumbnailView, final MediaLoader.SuccessCallback callback) {
        Picasso.get()
                .load(url)
                .resize(thumbnailWidth == null ? 100 : thumbnailWidth,
                        thumbnailHeight == null ? 100 : thumbnailHeight)
                .placeholder(R.drawable.aeroplane)
                .centerInside()
                .into(thumbnailView, new ImageCallback(callback));
    }

    private static class ImageCallback implements Callback {
        private final MediaLoader.SuccessCallback callback;

        public ImageCallback(SuccessCallback callback) {
            this.callback = callback;
        }

        @Override public void onSuccess() {
            callback.onSuccess();
        }

        @Override public void onError(Exception e) {
        }
    }
}