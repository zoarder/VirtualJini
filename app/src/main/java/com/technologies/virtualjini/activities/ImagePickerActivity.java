/**
 * BJIT CONFIDENTIAL                                                 *
 * Copyright 2014,2015 BJIT Group                                *
 *
 * @file ImagePickerActivity.java
 * <p>
 * This is the activity to show gallery image.
 * @file ImagePickerActivity.java
 * <p>
 * This is the activity to show gallery image.
 * @file ImagePickerActivity.java
 * <p>
 * This is the activity to show gallery image.
 * @file ImagePickerActivity.java
 * <p>
 * This is the activity to show gallery image.
 * @file ImagePickerActivity.java
 * <p>
 * This is the activity to show gallery image.
 * @file ImagePickerActivity.java
 * <p>
 * This is the activity to show gallery image.
 * @file ImagePickerActivity.java
 * <p>
 * This is the activity to show gallery image.
 * @file ImagePickerActivity.java
 * <p>
 * This is the activity to show gallery image.
 * @file ImagePickerActivity.java
 * <p>
 * This is the activity to show gallery image.
 * @file ImagePickerActivity.java
 * <p>
 * This is the activity to show gallery image.
 * @file ImagePickerActivity.java
 * <p>
 * This is the activity to show gallery image.
 * @file ImagePickerActivity.java
 * <p>
 * This is the activity to show gallery image.
 * @file ImagePickerActivity.java
 * <p>
 * This is the activity to show gallery image.
 * @file ImagePickerActivity.java
 * <p>
 * This is the activity to show gallery image.
 * @file ImagePickerActivity.java
 * <p>
 * This is the activity to show gallery image.
 * @file ImagePickerActivity.java
 * <p>
 * This is the activity to show gallery image.
 * @file ImagePickerActivity.java
 * <p>
 * This is the activity to show gallery image.
 * @file ImagePickerActivity.java
 * <p>
 * This is the activity to show gallery image.
 */
/**
 * @file ImagePickerActivity.java
 *
 *       This is the activity to show gallery image.
 */
package com.technologies.virtualjini.activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v4.util.LruCache;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.technologies.virtualjini.R;
import com.technologies.virtualjini.customviews.RecyclingImageView;
import com.technologies.virtualjini.dialog.CustomProgressDialog;
import com.technologies.virtualjini.dialog.NotificationDialog;
import com.technologies.virtualjini.imagepicker.MediaObject;
import com.technologies.virtualjini.imagepicker.Utils;
import com.technologies.virtualjini.utils.ImagePickerConstants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class ImagePickerActivity.
 *
 * @author Chinmoy Debnath
 */
public class ImagePickerActivity extends ParentActivity {
    public static final String TAG = ImagePickerActivity.class.getSimpleName();
    private static final int CONST_REQUIRED_SIZE = 85;

    Bitmap placeHolderBitmap;
    CustomProgressDialog mCustomProgressDialog = null;
    /** The m grid view. */
    private GridView mGridView;
    private Cursor mPhotoCursor = null;
    private List<MediaObject> cursorData;
    private ImagePickerAdapter mImagePickerAdapter;
    private LruCache<String, Bitmap> mMemoryCache;
    private String image_for;

    public static boolean cancelPotentialWork(String data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final String bitmapData = bitmapWorkerTask.data;
            if (bitmapData.length() == 0 || !bitmapData.equals(data)) {
                bitmapWorkerTask.cancel(true);
            } else {
                return false;
            }
        }
        return true;
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.zappalas.kyunkuru.activity.KyunkuruBaseActivity#onCreate(android.
     * os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_image_picker_parent_tb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            image_for = b.getString("image_for");
        }
        initializeView();
    }

    /**
     * Initialize button and set event listener on them.
     */
    private void initializeView() {
        mGridView = (GridView) findViewById(R.id.gridView);

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.dummy_gray,
                options);
        options.inSampleSize = calculateInSampleSize(options,
                CONST_REQUIRED_SIZE, CONST_REQUIRED_SIZE);
        options.inJustDecodeBounds = false;
        placeHolderBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.dummy_gray, options);

        new GetAllImageFromMediaStore().execute();
    }

    /**
     * Checks if is decoded perfectly.
     *
     * @param imgPath
     *            the img path
     * @return true, if is decoded perfectly
     */
    private boolean isDecodedPerfectly(String imgPath) {
        BitmapFactory.Options optionsBitmap = new BitmapFactory.Options();
        optionsBitmap.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, optionsBitmap);
        if (optionsBitmap.outWidth != 0 && optionsBitmap.outHeight != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if is image ok.
     *
     * @param imagePath
     *            the image path
     * @return true, if is image ok
     */
    private boolean isImageOk(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int minHeight = (200 * size.x) / 592;
        int actualImageHeight = (options.outHeight * size.x) / options.outWidth;
        if (actualImageHeight >= minHeight) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Populate grid view.
     */
    private void populateGridView() {
        if (!cursorData.isEmpty()) {
            mImagePickerAdapter = new ImagePickerAdapter(
                    ImagePickerActivity.this);
            mGridView.setAdapter(mImagePickerAdapter);

            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView,
                                        View view, int i, long l) {
                    Intent intentImagePath;

                    String imagePath = cursorData.get(i).getPath();
                    if (isImageOk(imagePath)) {
                        /////OrderMedicineActivity.action = 3;
                        intentImagePath = new Intent();
                        if (image_for.equals(ImagePickerConstants.KEY_MAIN_IMAGE)) {
                            intentImagePath.putExtra(
                                    ImagePickerConstants.KEY_MAIN_IMAGE_PATH,
                                    cursorData.get(i).getPath());
                        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_1)) {
                            intentImagePath.putExtra(
                                    ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_1,
                                    cursorData.get(i).getPath());
                        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_2)) {
                            intentImagePath.putExtra(
                                    ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_2,
                                    cursorData.get(i).getPath());
                        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_3)) {
                            intentImagePath.putExtra(
                                    ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_3,
                                    cursorData.get(i).getPath());
                        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_4)) {
                            intentImagePath.putExtra(
                                    ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_4,
                                    cursorData.get(i).getPath());
                        } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_5)) {
                            intentImagePath.putExtra(
                                    ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_5,
                                    cursorData.get(i).getPath());
                        } else {
                            intentImagePath.putExtra(
                                    ImagePickerConstants.KEY_DEFAULT_IMAGE_PATH,
                                    cursorData.get(i).getPath());
                        }

                        setResult(RESULT_OK, intentImagePath);
                        finish();
                        overridePendingTransition(R.anim.trans_left_in,
                                R.anim.trans_left_out);
                    } else {
                        NotificationDialog notificationDialog = new NotificationDialog(
                                ImagePickerActivity.this, "Error",
                                "Image Not Found", "OK");
                        notificationDialog.show();
                    }
                }

            });
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        placeHolderBitmap = null;

        mMemoryCache.evictAll();
        super.onDestroy();
        System.gc();
    }

    /**
     * Gets the thumbnail.
     *
     * @param path
     *            the path
     * @return the thumbnail
     */
    public String getThumbnail(String path) {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.MediaColumns._ID},
                MediaStore.MediaColumns.DATA + "=?", new String[]{path},
                null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            Cursor cursor2 = MediaStore.Images.Thumbnails.queryMiniThumbnail(
                    contentResolver, id,
                    MediaStore.Images.Thumbnails.MINI_KIND, null);
            if (cursor2 != null && cursor2.getCount() > 0) {
                cursor2.moveToFirst();
                String turi = cursor2.getString(cursor2
                        .getColumnIndex(MediaStore.Images.Thumbnails.DATA));
                cursor2.close();
                return turi;
            }
            cursor2.close();
        }
        cursor.close();
        return null;
    }

    public void loadBitmap(String imagePath, ImageView imageView) {
        final String imageKey = String.valueOf(imagePath);
        Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            bitmap = null;
        } else {
            if (cancelPotentialWork(imagePath, imageView)) {
                final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
                final AsyncDrawable asyncDrawable = new AsyncDrawable(
                        getResources(), placeHolderBitmap, task);
                imageView.setImageDrawable(asyncDrawable);
                task.execute(imagePath);
            } else {
                imageView.setImageResource(R.drawable.dummy_gray);
                BitmapWorkerTask task = new BitmapWorkerTask(imageView);
                task.execute(imagePath);
            }
        }
    }

    public Bitmap decodeSampledBitmapFromSD(String imagePath, int reqWidth,
                                            int reqHeight) {
        String thumbPath = getThumbnail(imagePath);
        if (thumbPath != null) {
            imagePath = thumbPath;
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath, options);
    }

    public int calculateInSampleSize(BitmapFactory.Options options,
                                     int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
        bitmap = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                exitThisWithAnimation();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    void exitThisWithAnimation() {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        super.exitThisWithAnimation();
    }

    @Override
    public void onBackPressed() {
        exitThisWithAnimation();
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(
                    bitmapWorkerTask);
            bitmap = null;
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    /**
     * The Class GetImageFromMediaStore.
     */
    public class GetAllImageFromMediaStore extends
            AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mCustomProgressDialog = new CustomProgressDialog(
                    ImagePickerActivity.this, "Loading...");
            mCustomProgressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
                // String[] projection = { MediaStore.Images.Media._ID,
                // MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                // MediaStore.Images.Media.DISPLAY_NAME };
                // String selection =
                // MediaStore.Images.Media.BUCKET_DISPLAY_NAME
                // + " = ?";
                // String[] selectionArgs = new String[] { "DCIM" };
                // mPhotoCursor = getContentResolver()
                // .query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                // projection, selection, selectionArgs,
                // orderBy + " DESC");
                mPhotoCursor = getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                        null, null, orderBy + " DESC");
                if (mPhotoCursor.getCount() > 0) {
                    cursorData = new ArrayList<MediaObject>();
                    ArrayList<MediaObject> tempArr = new ArrayList<MediaObject>();
                    tempArr.addAll(Utils.extractMediaList(mPhotoCursor, "PHOTO"));
                    for (int i = 0; i < tempArr.size(); i++) {
                        if (isDecodedPerfectly(tempArr.get(i).getPath())) {
                            cursorData.add(tempArr.get(i));
                        }
                    }
                    tempArr.clear();
                    return "1";
                } else {
                    return "0";
                }
            } catch (Exception e) {
                return "0";
            }
        }

        @Override
        protected void onPostExecute(final String result) {
            try {
                new CountDownTimer(1000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if (result.equals("1")) {
                            populateGridView();
                        }
                        mCustomProgressDialog.dismiss();
                    }
                }.start();
            } catch (Exception e) {
            }
            super.onPostExecute(result);
        }
    }

    private class ImagePickerAdapter extends BaseAdapter {

        private final Context mContext;
        private GridView.LayoutParams mImageViewLayoutParams;

        public ImagePickerAdapter(Context context) {
            super();
            mContext = context;
            mImageViewLayoutParams = new GridView.LayoutParams(
                    LayoutParams.MATCH_PARENT, 200);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new RecyclingImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(mImageViewLayoutParams);
            } else {
                imageView = (ImageView) convertView;
            }
            loadBitmap(cursorData.get(position).getPath(), imageView);
            return imageView;
        }

        /*
         * (non-Javadoc)
         *
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return cursorData.size();
        }

        /*
         * (non-Javadoc)
         *
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        /*
         * (non-Javadoc)
         *
         * @see android.widget.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

    }

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private String data = "";

        public BitmapWorkerTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(String... params) {
            final Bitmap bitmap = decodeSampledBitmapFromSD(params[0],
                    CONST_REQUIRED_SIZE, CONST_REQUIRED_SIZE);
            if (bitmap != null) {
                addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
            }
            return bitmap;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageBitmap(bitmap);
                    bitmap = null;
                }
            }
        }
    }
}
