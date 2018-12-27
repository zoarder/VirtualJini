package com.technologies.virtualjini.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class ImageUtils {
    /**
     * @param context
     * @param imageFilePath
     * @return bitmap image file with proper orientation
     */
    public static Bitmap RotateBitmap(Context context, String imageFilePath)
            throws Exception {
        Bitmap bitmapImageFile = decodeFile(context, imageFilePath);
        Matrix matrix = new Matrix();
        matrix.postRotate(getImageOrientation(context,
                Uri.parse(imageFilePath), imageFilePath));
        try {
            Bitmap btmp = Bitmap.createBitmap(bitmapImageFile, 0, 0,
                    bitmapImageFile.getWidth(), bitmapImageFile.getHeight(),
                    matrix, true);
            if (btmp != bitmapImageFile) {
                bitmapImageFile.recycle();
            }
            return btmp;
        } catch (Exception e) {
            return bitmapImageFile;
        } finally {
            bitmapImageFile = null;
        }
    }

    /**
     * @param imagePath File
     * @return decoded file of image
     */
    public static Bitmap decodeFile(Context context, final String imagePath) {
        try {
            // decode image size
            final BitmapFactory.Options optionsBitmap = new BitmapFactory.Options();
            optionsBitmap.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, optionsBitmap);
            Display display = ((Activity) context).getWindowManager()
                    .getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            if (width <= optionsBitmap.outWidth
                    || optionsBitmap.outHeight > size.y) {
                // Find the correct scale value. It should be the power of 2.
                int tempWidth = optionsBitmap.outWidth;
                int tempHeight = optionsBitmap.outHeight;
                int scale = 1;
                final int devider = 2;

                while (true) {
                    if (tempWidth / devider < 500 || tempHeight / devider < 500) {
                        break;
                    }
                    tempWidth /= devider;
                    tempHeight /= devider;
                    scale++;
                }
                // decode with inSampleSize
                final BitmapFactory.Options optionsBitmap2 = new BitmapFactory.Options();
                optionsBitmap2.inSampleSize = scale;
                return BitmapFactory.decodeFile(imagePath, optionsBitmap2);
            } else {
                return BitmapFactory.decodeFile(imagePath,
                        new BitmapFactory.Options());
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static int getImageOrientation(Context context, Uri imageUri,
                                          String imagePath) {
        int orientation = getOrientationFromExif(context, imageUri, imagePath);
        if (orientation < 0) {
            orientation = getOrientationFromMediaStore(context, imagePath,
                    imageUri);
            if (orientation < 0) {
                orientation = 0;
            }
        }

        return orientation;
    }

    /**
     * @param context
     * @param imageUri
     * @param imagePath
     * @return image rotation angle
     */
    public static int getOrientationFromExif(Context context, Uri imageUri,
                                             String imagePath) {
        int orientation = -1;
        try {
            ExifInterface exif = new ExifInterface(imagePath);
            int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    orientation = 270;

                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    orientation = 180;

                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    orientation = 90;

                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                    orientation = 0;

                    break;
                default:
                    break;
            }
        } catch (IOException e) {
        }

        return orientation;
    }

    public static int getOrientationFromMediaStore(Context context,
                                                   String imagePath, Uri imageUri) {
        imageUri = getImageContentUri(context, imagePath, imageUri);
        if (imageUri == null) {
            return -1;
        }

        String[] projection = {MediaStore.Images.ImageColumns.ORIENTATION};
        Cursor cursor = context.getContentResolver().query(imageUri,
                projection, null, null, null);

        int orientation = -1;
        if (cursor != null && cursor.moveToFirst()) {
            orientation = cursor.getInt(0);
            cursor.close();
        }

        return orientation;
    }

    public static Uri getImageContentUri(Context context, String imagePath,
                                         Uri imageUri) {
        String[] projection = new String[]{MediaStore.Images.Media._ID};
        String selection = MediaStore.Images.Media.DATA + "=? ";
        String[] selectionArgs = new String[]{imagePath};
        Cursor cursor = context.getContentResolver().query(imageUri,
                projection, selection, selectionArgs, null);

        if (cursor != null && cursor.moveToFirst()) {
            int imageId = cursor.getInt(0);
            cursor.close();

            return Uri.withAppendedPath(imageUri, Integer.toString(imageId));
        }

        if (new File(imagePath).exists()) {
            return Uri.fromFile(new File(imagePath));
        }

        return null;
    }

    @SuppressLint("NewApi")
    public static Bitmap getScaledImage(Activity activity, Bitmap srcBitmap,
                                        int negetiveWidth) throws Exception {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x - convertDpToPixel(negetiveWidth, activity);
        int dstHeight = width * srcBitmap.getHeight() / srcBitmap.getWidth();
        Bitmap bmp = null;
        try {
            bmp = Bitmap.createScaledBitmap(srcBitmap, width, dstHeight, false);
            return bmp;
        } catch (Exception e) {
            return null;
        } finally {
            bmp = null;
        }
    }

    public static int convertDpToPixel(int dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int px = (int) (dp * (metrics.densityDpi / 160f));
        return px;
    }

    /**
     * Load bitmap from view.
     *
     * @param v the v
     * @param w the w
     * @param h the h
     * @return the bitmap
     */
    public static Bitmap loadBitmapFromView(View v, int w, int h) {
        Bitmap b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }

    /**
     * Check duplecate image.
     */
    public static void checkDuplecateImage() {
        if (AFCHealthDataHolder.getInstance().getLastIamgeId() != -1) {
            final String[] imageColumns = {MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DATE_TAKEN,
                    MediaStore.Images.Media.SIZE, MediaStore.Images.Media._ID};
            final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
            final String imageWhere = MediaStore.Images.Media._ID + ">?";
            final String[] imageArguments = {Integer
                    .toString(AFCHealthDataHolder.getInstance()
                    .getLastIamgeId())};
            Cursor imageCursor = AFCHealthDataHolder
                    .getInstance()
                    .getProfileImageContext()
                    .getContentResolver()
                    .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            imageColumns, imageWhere, imageArguments,
                            imageOrderBy);
            if (imageCursor != null && imageCursor.moveToFirst()) {
                int id = imageCursor.getInt(imageCursor
                        .getColumnIndex(MediaStore.Images.Media._ID));
                ContentResolver cr = AFCHealthDataHolder.getInstance()
                        .getProfileImageContext().getContentResolver();
                cr.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        MediaStore.Images.Media._ID + "=?",
                        new String[]{Long.toString(id)});
                AFCHealthDataHolder.getInstance().setLastIamgeId(-1);
            }
            imageCursor.close();
        }
    }

    /**
     * Gets the last image id from the media store
     *
     * @return
     */
    public static int getLastImageId() {
        final String[] imageColumns = {MediaStore.Images.Media._ID};
        final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
        final String imageWhere = null;
        final String[] imageArguments = null;
        Cursor imageCursor = AFCHealthDataHolder
                .getInstance()
                .getProfileImageContext()
                .getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        imageColumns, imageWhere, imageArguments, imageOrderBy);
        if (imageCursor.moveToFirst()) {
            int id = imageCursor.getInt(imageCursor
                    .getColumnIndex(MediaStore.Images.Media._ID));
            imageCursor.close();
            return id;
        } else {
            return 0;
        }
    }

    public static void displayOrClearAnimation(ImageView imageview,
                                               boolean isShow) {
        try {
            if (imageview != null) {
                imageview.clearAnimation();
                imageview.setAnimation(null);
                if (isShow) {
                    RotateAnimation rotate = new RotateAnimation(0.0F, 359.0F,
                            Animation.RELATIVE_TO_SELF, 0.5F,
                            Animation.RELATIVE_TO_SELF, 0.5F);
                    rotate.setInterpolator(AFCHealthDataHolder
                                    .getInstance().getProfileImageContext(),
                            android.R.anim.linear_interpolator);
                    rotate.setRepeatCount(Animation.INFINITE);
                    rotate.setDuration(1000);
                    imageview.setAnimation(rotate);
                }
            }
        } catch (Exception e) {
        }
    }
}
