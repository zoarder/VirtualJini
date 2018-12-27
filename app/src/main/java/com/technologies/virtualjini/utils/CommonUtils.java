package com.technologies.virtualjini.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.technologies.virtualjini.R;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonUtils {

//    public static void initializeCustomFonts(Context context) {
//
//        // Load helper with default custom typeface (single custom typeface)
//        TypefaceHelper.init(new TypefaceCollection.Builder()
//                .set(Typeface.NORMAL,
//                        Typeface.createFromAsset(
//                                context.getAssets(),
//                                context.getResources().getString(
//                                        R.string.app_typeface_museo_sans_100)))
//                .set(Typeface.BOLD,
//                        Typeface.createFromAsset(
//                                context.getAssets(),
//                                context.getResources().getString(
//                                        R.string.app_typeface_museo_sans_100)))
//                .set(Typeface.ITALIC,
//                        Typeface.createFromAsset(
//                                context.getAssets(),
//                                context.getResources().getString(
//                                        R.string.app_typeface_museo_sans_100)))
//                .set(Typeface.BOLD_ITALIC,
//                        Typeface.createFromAsset(
//                                context.getAssets(),
//                                context.getResources().getString(
//                                        R.string.app_typeface_museo_sans_100)))
//                .create());
//
//        // Multiple custom typefaces support
//        TypefaceCollection mRobotoTypeface = new TypefaceCollection.Builder()
//                .set(Typeface.NORMAL,
//                        Typeface.createFromAsset(
//                                context.getAssets(),
//                                context.getResources().getString(
//                                        R.string.app_typeface_museo_sans_100)))
//                .set(Typeface.BOLD,
//                        Typeface.createFromAsset(
//                                context.getAssets(),
//                                context.getResources().getString(
//                                        R.string.app_typeface_museo_sans_100)))
//                .set(Typeface.ITALIC,
//                        Typeface.createFromAsset(
//                                context.getAssets(),
//                                context.getResources().getString(
//                                        R.string.app_typeface_museo_sans_100)))
//                .set(Typeface.BOLD_ITALIC,
//                        Typeface.createFromAsset(
//                                context.getAssets(),
//                                context.getResources().getString(
//                                        R.string.app_typeface_museo_sans_100)))
//                .create();
//
//        // Multiple custom typefaces support
//        TypefaceCollection mSystemDefaultTypeface = TypefaceCollection
//                .createSystemDefault();
//    }

    public static void setLocale(Context context, String localeName) {
        Locale locale = new Locale(localeName);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }

    /**
     * Unbind drawables.
     *
     * @param view the view
     */
    public static void unbindDrawables(View view) {
        if (view == null) {
            return;
        }
        try {
            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    unbindDrawables(((ViewGroup) view).getChildAt(i));
                }
                ((ViewGroup) view).removeAllViews();
            } else {
                if (view.getBackground() != null) {
                    BitmapDrawable d = (BitmapDrawable) view.getBackground();
                    if (d != null) {
                        if (d.getBitmap() != null
                                && !d.getBitmap().isRecycled()) {
                            d.getBitmap().isRecycled();
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Unbind view.
     *
     * @param view the view
     */
    public static void unbindView(View view) {
        if (view == null) {
            return;
        }
        try {
            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    unbindDrawables(((ViewGroup) view).getChildAt(i));
                }
            } else {
                if (view.getBackground() != null) {
                    BitmapDrawable d = (BitmapDrawable) view.getBackground();
                    if (d != null) {
                        if (d.getBitmap() != null
                                && !d.getBitmap().isRecycled()) {
                            d.getBitmap().isRecycled();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.gc();
    }

    public static void showUnderDevelopmentToast(Context context) {
        Toast.makeText(context, "Under Development", Toast.LENGTH_SHORT).show();
    }

    public static void dispatchTakePictureIntent(Activity activity, String image_for) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {

                if (image_for.equals(ImagePickerConstants.KEY_MAIN_IMAGE)) {
                    photoFile = createImageFile(ImagePickerConstants.MAIN_IMAGE_NAME);
                } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_1)) {
                    photoFile = createImageFile(ImagePickerConstants.OPTIONAL_IMAGE_NAME_1);
                } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_2)) {
                    photoFile = createImageFile(ImagePickerConstants.OPTIONAL_IMAGE_NAME_2);
                } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_3)) {
                    photoFile = createImageFile(ImagePickerConstants.OPTIONAL_IMAGE_NAME_3);
                } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_4)) {
                    photoFile = createImageFile(ImagePickerConstants.OPTIONAL_IMAGE_NAME_4);
                } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_5)) {
                    photoFile = createImageFile(ImagePickerConstants.OPTIONAL_IMAGE_NAME_5);
                } else {
                    photoFile = createImageFile(ImagePickerConstants.DEFAULT_IMAGE_NAME);
                }
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                // Save a file: path for use with ACTION_VIEW intents
                if (image_for.equals(ImagePickerConstants.KEY_MAIN_IMAGE)) {
                    SharedPreferencesManager.setStringSetting(activity,
                            ImagePickerConstants.KEY_MAIN_IMAGE_PATH,
                            photoFile.getAbsolutePath());
                } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_1)) {
                    SharedPreferencesManager.setStringSetting(activity,
                            ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_1,
                            photoFile.getAbsolutePath());
                } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_2)) {
                    SharedPreferencesManager.setStringSetting(activity,
                            ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_2,
                            photoFile.getAbsolutePath());
                } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_3)) {
                    SharedPreferencesManager.setStringSetting(activity,
                            ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_3,
                            photoFile.getAbsolutePath());
                } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_4)) {
                    SharedPreferencesManager.setStringSetting(activity,
                            ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_4,
                            photoFile.getAbsolutePath());
                } else if (image_for.equals(ImagePickerConstants.KEY_OPTIONAL_IMAGE_5)) {
                    SharedPreferencesManager.setStringSetting(activity,
                            ImagePickerConstants.KEY_OPTIONAL_IMAGE_PATH_5,
                            photoFile.getAbsolutePath());
                } else {
                    SharedPreferencesManager.setStringSetting(activity,
                            ImagePickerConstants.KEY_DEFAULT_IMAGE_PATH,
                            photoFile.getAbsolutePath());
                }

                Uri photoURI = null;
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    photoURI = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", photoFile);
                } else {
                    photoURI = Uri.fromFile(photoFile);
                }
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                int adCounter = SharedPreferencesManager.getIntegerSetting(activity, AFCHealthConstants.AD_COUNTER, 0);
                SharedPreferencesManager.setIntegerSetting(activity, AFCHealthConstants.AD_COUNTER, ++adCounter);
                activity.startActivityForResult(takePictureIntent,
                        ImagePickerConstants.REQUEST_FOR_TAKE_PHOTO);
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static File createImageFile(String fileName) throws IOException {
        // Create an image file name
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                ImagePickerConstants.APP_IMAGE_FOLDER);

        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }

        File image = new File(mediaStorageDir.getPath() + File.separator
                + fileName);

        return image;
    }

    public static boolean validateEmail(final String text) {

        if (text.length() == 0) {
            return false;
        }

        final Pattern p = Pattern
                .compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9]+\\.+[a-z.]+");

        // Match the given string with the pattern
        final Matcher m = p.matcher(text);

        // check whether match is found
        final boolean matchFound = m.matches();

        final StringTokenizer st = new StringTokenizer(text, ".");
        String lastToken = null;
        while (st.hasMoreTokens()) {
            lastToken = st.nextToken();
        }

        if (matchFound && lastToken.length() >= 2
                && text.length() - 1 != lastToken.length()) {

            // validate the country code
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPhoneNoValid(String mob) {
        Pattern patt = Pattern.compile("^(?:\\+?88)?01[5-9]\\d{8}$");
        Matcher matcher = patt.matcher(mob);
        if (!matcher.matches()) {
            return false;
        } else {
            return true;
        }
    }

    public static String isLandPhoneNoValid(Activity activity, String mob) {
        if (TextUtils.isEmpty(mob)) {
            return activity.getResources().getString(R.string.error_field_required);
        }
        if (mob.length() < 7) {
            return activity.getResources().getString(R.string.error_invalid_phone);
        }
        if (mob.length() > 11) {
            return activity.getResources().getString(R.string.error_invalid_phone);
        }
        return "OK";
    }

    public static boolean isLandPhoneNoValid(String mob) {
        //Pattern patt = Pattern.compile("^(?:\\+?88)?0[15-8]\\d{8}$");
        //Matcher matcher = patt.matcher(mob);
        if (!TextUtils.isEmpty(mob)) {
            if (mob.length() < 7) {
                return false;
            }

            if (mob.length() > 11) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static boolean isAppEnabled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        boolean app_enabled = false;
        try {
            ApplicationInfo af = pm.getApplicationInfo(packageName, 0);
            if (af.enabled) {
                app_enabled = true;
            } else {
                app_enabled = false;
            }
        } catch (PackageManager.NameNotFoundException e) {
            app_enabled = false;
        }
        return app_enabled;
    }

    public static boolean isWifiLocationEnabled(Context context) {
        ContentResolver cr = context.getContentResolver();
        String enabledProviders = Settings.Secure.getString(cr,
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!TextUtils.isEmpty(enabledProviders)) {
            // not the fastest way to do that :)
            String[] providersList = TextUtils.split(enabledProviders, ",");
            for (String provider : providersList) {
                if (LocationManager.NETWORK_PROVIDER.equals(provider)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isGpsEnabled(Context ctx) {

        LocationManager locationManager = (LocationManager) ctx

                .getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))

            return true;

        else

            return false;

    }

    public static boolean deleteFile(String filePath) {
        File fileToDelete = new File(filePath);
        if (fileToDelete.exists()) {
            fileToDelete.delete();
            return true;
        }
        return false;
    }


//    public static Age calculateAge(Date birthDate) {
//        int years = 0;
//        int months = 0;
//        int days = 0;
//        Calendar birthDay = Calendar.getInstance();
//        birthDay.setTimeInMillis(birthDate.getTime());
//        long currentTime = System.currentTimeMillis();
//        Calendar now = Calendar.getInstance();
//        now.setTimeInMillis(currentTime);
//        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
//        int currMonth = now.get(Calendar.MONTH) + 1;
//        int birthMonth = birthDay.get(Calendar.MONTH) + 1;
//        months = currMonth - birthMonth;
//        if (months < 0) {
//            years--;
//            months = 12 - birthMonth + currMonth;
//            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
//                months--;
//        } else if (months == 0
//                && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
//            years--;
//            months = 11;
//        }
//        if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
//            days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
//        else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
//            int today = now.get(Calendar.DAY_OF_MONTH);
//            now.add(Calendar.MONTH, -1);
//            days = now.getActualMaximum(Calendar.DAY_OF_MONTH)
//                    - birthDay.get(Calendar.DAY_OF_MONTH) + today;
//        } else {
//            days = 0;
//            if (months == 12) {
//                years++;
//                months = 0;
//            }
//        }
//        return new Age(days, months, years);
//    }

    public static void requestFocus(Activity activity, View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            //activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public static void hideSoftInputMode(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    public static String removeLeadingZeroes(String value) {
        return new Integer(value).toString();
    }

    public static boolean validateUserFullName(Activity activity, String userFullName, EditText userFullNameEditText) {
        if (TextUtils.isEmpty(userFullName)) {
            userFullNameEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, userFullNameEditText);
            return false;
        }
        if (userFullName.length() < 3) {
            userFullNameEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_3));
            CommonUtils.requestFocus(activity, userFullNameEditText);
            return false;
        }

        return true;
    }

    public static boolean validateChamberName(Activity activity, String chamberName, EditText chamberNameNameEditText) {
        if (TextUtils.isEmpty(chamberName)) {
            chamberNameNameEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, chamberNameNameEditText);
            return false;
        }
        if (chamberName.length() < 3) {
            chamberNameNameEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_3));
            CommonUtils.requestFocus(activity, chamberNameNameEditText);
            return false;
        }
        if (chamberName.length() > 100) {
            chamberNameNameEditText.setError(activity.getResources().getString(R.string.error_maximum_length_100));
            CommonUtils.requestFocus(activity, chamberNameNameEditText);
            return false;
        }
        return true;
    }

    public static boolean validateDoctorBMDCNoOpt(Activity activity, String userBMDCNo, EditText userBMDCNoEditText) {

        if (!TextUtils.isEmpty(userBMDCNo)) {
            if (userBMDCNo.length() < 3) {
                userBMDCNoEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_3));
                CommonUtils.requestFocus(activity, userBMDCNoEditText);
                return false;
            }

            if (userBMDCNo.length() > 10) {
                userBMDCNoEditText.setError(activity.getResources().getString(R.string.error_maximum_length_10));
                CommonUtils.requestFocus(activity, userBMDCNoEditText);
                return false;
            }
        }

        return true;
    }

    public static boolean validateDoctorBMDCNoRequired(Activity activity, String userBMDCNo, EditText userBMDCNoEditText) {

        if (TextUtils.isEmpty(userBMDCNo)) {
            userBMDCNoEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, userBMDCNoEditText);
            return false;
        }
        if (userBMDCNo.length() < 3) {
            userBMDCNoEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_3));
            CommonUtils.requestFocus(activity, userBMDCNoEditText);
            return false;
        }

        if (userBMDCNo.length() > 10) {
            userBMDCNoEditText.setError(activity.getResources().getString(R.string.error_maximum_length_10));
            CommonUtils.requestFocus(activity, userBMDCNoEditText);
            return false;
        }

        return true;
    }

    public static boolean validateUserEmail(Activity activity, String userEmail, EditText userEmailEditText) {

        if (!TextUtils.isEmpty(userEmail) && userEmail.length() < 7) {
            userEmailEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_7));
            CommonUtils.requestFocus(activity, userEmailEditText);
            return false;
        }
        if (!TextUtils.isEmpty(userEmail) && !CommonUtils.validateEmail(userEmail)) {
            userEmailEditText.setError(activity.getResources().getString(R.string.error_invalid_email));
            CommonUtils.requestFocus(activity, userEmailEditText);
            return false;
        }

        return true;
    }

    public static boolean validateDateFormat(Activity activity, String userDate, EditText userBDate) {

        if (TextUtils.isEmpty(userDate)) {
            userBDate.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, userBDate);
            return false;
        }

        if (!DateTimeUtils.isValidDateTimeFormat(userDate, DateTimeUtils.DATE_FORMAT)) {
            userBDate.setError(activity.getResources().getString(R.string.error_date_format));
            CommonUtils.requestFocus(activity, userBDate);
            return false;
        }
        return true;
    }


    public static boolean validateUserPhoneNo(Activity activity, String userPhoneNo, String userPhoneNoWithCode, EditText userPhoneNoEditText) {
        if (TextUtils.isEmpty(userPhoneNo)) {
            userPhoneNoEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, userPhoneNoEditText);
            return false;
        }
        if (userPhoneNo.length() < 11) {
            userPhoneNoEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_11));
            CommonUtils.requestFocus(activity, userPhoneNoEditText);
            return false;
        }
        if (!CommonUtils.isPhoneNoValid(userPhoneNoWithCode)) {
            userPhoneNoEditText.setError(activity.getResources().getString(R.string.error_invalid_phone));
            CommonUtils.requestFocus(activity, userPhoneNoEditText);
            return false;
        }
        return true;
    }

    public static boolean validateChamberPhoneNo(Activity activity, String chamberPhoneNo, EditText chamberPhoneNoEditText) {
        if (TextUtils.isEmpty(chamberPhoneNo)) {
            chamberPhoneNoEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, chamberPhoneNoEditText);
            return false;
        }
        if (chamberPhoneNo.length() < 7) {
            chamberPhoneNoEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_7));
            CommonUtils.requestFocus(activity, chamberPhoneNoEditText);
            return false;
        }
        return true;
    }

    public static boolean validateUserPhoneNoForEditProfile(Activity activity, String userPhoneNo, String userPhoneNoWithCode, EditText userPhoneNoEditText) {
        if (!TextUtils.isEmpty(userPhoneNo) && userPhoneNo.length() < 11) {
            userPhoneNoEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_11));
            CommonUtils.requestFocus(activity, userPhoneNoEditText);
            return false;
        }
        if (!TextUtils.isEmpty(userPhoneNo) && !CommonUtils.isPhoneNoValid(userPhoneNoWithCode)) {
            userPhoneNoEditText.setError(activity.getResources().getString(R.string.error_invalid_phone));
            CommonUtils.requestFocus(activity, userPhoneNoEditText);
            return false;
        }

        return true;
    }

    public static boolean validateUserPin(Activity activity, String userPin, String userServerPin, EditText userPinEditText) {
        if (TextUtils.isEmpty(userPin)) {
            userPinEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, userPinEditText);
            return false;
        }
        if (userPin.length() < 4) {
            userPinEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_4));
            CommonUtils.requestFocus(activity, userPinEditText);
            return false;
        }
        if (!userPin.equals(userServerPin)) {
            userPinEditText.setError(activity.getResources().getString(R.string.error_pin));
            CommonUtils.requestFocus(activity, userPinEditText);
            return false;
        }
        return true;
    }


    public static boolean validateUserEmailRequired(Activity activity, String userEmail, EditText userEmailEditText) {
        if (TextUtils.isEmpty(userEmail)) {
            userEmailEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, userEmailEditText);
            return false;
        }
        if (userEmail.length() < 7) {
            userEmailEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_7));
            CommonUtils.requestFocus(activity, userEmailEditText);
            return false;
        }
        if (!CommonUtils.validateEmail(userEmail)) {
            userEmailEditText.setError(activity.getResources().getString(R.string.error_invalid_email));
            CommonUtils.requestFocus(activity, userEmailEditText);
            return false;
        }

        return true;
    }

    public static boolean validateReferralCode(Activity activity, String referralCode, EditText referralCodeEditText) {
        if (TextUtils.isEmpty(referralCode)) {
            referralCodeEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, referralCodeEditText);
            return false;
        }
        if (!TextUtils.isEmpty(referralCode) && referralCode.length() < 7) {
            referralCodeEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_7));
            CommonUtils.requestFocus(activity, referralCodeEditText);
            return false;
        }

        return true;
    }

    public static boolean validateUserAge(Activity activity, String userAge, EditText userAgeEditText) {
        if (TextUtils.isEmpty(userAge)) {
            userAgeEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, userAgeEditText);
            return false;
        }
        if (userAge.length() < 2) {
            userAgeEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_2));
            CommonUtils.requestFocus(activity, userAgeEditText);
            return false;
        }
//        if (Integer.parseInt(userAge) < 18) {
//            userAgeEditText.setError(activity.getResources().getString(R.string.error_invalid_age));
//            CommonUtils.requestFocus(activity, userAgeEditText);
//            return false;
//        }
        if (Integer.parseInt(userAge) < 18) {
            userAgeEditText.setError(activity.getResources().getString(R.string.error_invalid_age_bellow_18));
            CommonUtils.requestFocus(activity, userAgeEditText);
            return false;
        }

        return true;
    }

    public static boolean validateUserPassword(Activity activity, String userPassword, EditText userPasswordEditText) {
        if (TextUtils.isEmpty(userPassword)) {
            userPasswordEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, userPasswordEditText);
            return false;
        }
        if (userPassword.length() < 6) {
            userPasswordEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_6));
            CommonUtils.requestFocus(activity, userPasswordEditText);
            return false;
        }

        if (userPassword.length() > 15) {
            userPasswordEditText.setError(activity.getResources().getString(R.string.error_maximum_length_15));
            CommonUtils.requestFocus(activity, userPasswordEditText);
            return false;
        }

        return true;
    }

    public static boolean validateMedicineName(Activity activity, String userFullName, EditText medicineNameEditText) {
        if (TextUtils.isEmpty(userFullName)) {
            medicineNameEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, medicineNameEditText);
            return false;
        }
        if (userFullName.length() < 3) {
            medicineNameEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_3));
            CommonUtils.requestFocus(activity, medicineNameEditText);
            return false;
        }

        return true;
    }


    public static boolean validateUserConfirmPassword(Activity activity, String userPassword, String userConfirmPassword, EditText userConfirmPasswordEditText) {
        if (TextUtils.isEmpty(userConfirmPassword)) {
            userConfirmPasswordEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, userConfirmPasswordEditText);
            return false;
        }
        if (userConfirmPassword.length() < 6) {
            userConfirmPasswordEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_6));
            CommonUtils.requestFocus(activity, userConfirmPasswordEditText);
            return false;
        }

        if (userPassword.length() > 15) {
            userConfirmPasswordEditText.setError(activity.getResources().getString(R.string.error_maximum_length_15));
            CommonUtils.requestFocus(activity, userConfirmPasswordEditText);
            return false;
        }

        if (!userPassword.equals(userConfirmPassword)) {
            userConfirmPasswordEditText.setError(activity.getResources().getString(R.string.error_miss_match_password));
            CommonUtils.requestFocus(activity, userConfirmPasswordEditText);
            return false;
        }

        return true;
    }

    public static boolean validateUserGender(Activity activity, String userGender, String[] genderList, EditText userGenderEditText) {
        if (TextUtils.isEmpty(userGender)) {
            userGenderEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, userGenderEditText);
            return false;
        }
        if (userGender.length() < 4) {
            userGenderEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_4));
            CommonUtils.requestFocus(activity, userGenderEditText);
            return false;
        }
        if (!userGender.equals(genderList[0]) && !userGender.equals(genderList[1])) {
            userGenderEditText.setError(activity.getResources().getString(R.string.invalid_gender));
            CommonUtils.requestFocus(activity, userGenderEditText);
            return false;
        }

        return true;
    }

    public static boolean validateUserType(Activity activity, String userType, String[] typeList, EditText userTypeEditText) {
        if (TextUtils.isEmpty(userType)) {
            userTypeEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, userTypeEditText);
            return false;
        }
        if (userType.length() < 4) {
            userTypeEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_4));
            CommonUtils.requestFocus(activity, userTypeEditText);
            return false;
        }
        if (!userType.equals(typeList[0]) && !userType.equals(typeList[1])) {
            userTypeEditText.setError(activity.getResources().getString(R.string.invalid_type));
            CommonUtils.requestFocus(activity, userTypeEditText);
            return false;
        }

        return true;
    }

    public static boolean validateUserBMDCGener(Activity activity, String userBMDCNo, String userBMDCType, String[] bmdcTypeList, EditText userBMDCTypeEditText) {

        if (!TextUtils.isEmpty(userBMDCNo)) {
            if (TextUtils.isEmpty(userBMDCType)) {
                userBMDCTypeEditText.setError(activity.getResources().getString(R.string.error_field_required));
                CommonUtils.requestFocus(activity, userBMDCTypeEditText);
                return false;
            }
            if (userBMDCType.length() < 4) {
                userBMDCTypeEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_4));
                CommonUtils.requestFocus(activity, userBMDCTypeEditText);
                return false;
            }
            if (!userBMDCType.equals(bmdcTypeList[0]) && !userBMDCType.equals(bmdcTypeList[1])) {
                userBMDCTypeEditText.setError(activity.getResources().getString(R.string.invalid_gener));
                CommonUtils.requestFocus(activity, userBMDCTypeEditText);
                return false;
            }
        }
        return true;
    }

    public static boolean validateUserAddress(Activity activity, String userAddress, EditText userAddressEditText) {
        if (TextUtils.isEmpty(userAddress)) {
            userAddressEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, userAddressEditText);
            return false;
        }
        if (userAddress.length() < 11) {
            userAddressEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_11));
            CommonUtils.requestFocus(activity, userAddressEditText);
            return false;
        }

        return true;
    }

    public static boolean validateChamberAddress(Activity activity, String chamberAddress, EditText chamberAddressEditText) {
        if (TextUtils.isEmpty(chamberAddress)) {
            chamberAddressEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, chamberAddressEditText);
            return false;
        }
        if (chamberAddress.length() < 11) {
            chamberAddressEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_11));
            CommonUtils.requestFocus(activity, chamberAddressEditText);
            return false;
        }

        return true;
    }

    public static boolean validateUserCity(Activity activity, String userCity, String[] cityList, EditText userCityEditText) {
        if (TextUtils.isEmpty(userCity)) {
            userCityEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, userCityEditText);
            return false;
        } else if (userCity.length() < 3) {
            userCityEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_3));
            CommonUtils.requestFocus(activity, userCityEditText);
            return false;
        } else {
            for (int i = 0; i < cityList.length; i++) {
                if (userCity.equals(cityList[i])) {
                    return true;
                }
            }
            userCityEditText.setError(activity.getResources().getString(R.string.invalid_city));
            CommonUtils.requestFocus(activity, userCityEditText);
            return false;
        }
    }

    public static boolean validateChamberCity(Activity activity, String chamberCity, String[] cityList, EditText chamberCityEditText) {
        if (TextUtils.isEmpty(chamberCity)) {
            chamberCityEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, chamberCityEditText);
            return false;
        } else if (chamberCity.length() < 3) {
            chamberCityEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_3));
            CommonUtils.requestFocus(activity, chamberCityEditText);
            return false;
        } else {
            for (int i = 0; i < cityList.length; i++) {
                if (chamberCity.equals(cityList[i])) {
                    return true;
                }
            }
            chamberCityEditText.setError(activity.getResources().getString(R.string.invalid_city));
            CommonUtils.requestFocus(activity, chamberCityEditText);
            return false;
        }
    }

    public static boolean validateUserArea(Activity activity, String userArea, EditText userAreaEditText) {
        if (TextUtils.isEmpty(userArea)) {
            userAreaEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, userAreaEditText);
            return false;
        }
        if (userArea.length() < 3) {
            userAreaEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_3));
            CommonUtils.requestFocus(activity, userAreaEditText);
            return false;
        }

        return true;
    }

    public static boolean validateChamberArea(Activity activity, String chamberArea, EditText chamberAreaEditText) {
        if (TextUtils.isEmpty(chamberArea)) {
            chamberAreaEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, chamberAreaEditText);
            return false;
        }
        if (chamberArea.length() < 3) {
            chamberAreaEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_3));
            CommonUtils.requestFocus(activity, chamberAreaEditText);
            return false;
        }
        if (chamberArea.length() > 50) {
            chamberAreaEditText.setError(activity.getResources().getString(R.string.error_maximum_length_50));
            CommonUtils.requestFocus(activity, chamberAreaEditText);
            return false;
        }
        return true;
    }

    public static boolean validateChamberLocation(Activity activity, String chamberLocation, EditText chamberLocationEditText) {
        if (TextUtils.isEmpty(chamberLocation)) {
            chamberLocationEditText.setError(activity.getResources().getString(R.string.error_field_required));
//            CommonUtils.requestFocus(activity, chamberLocationEditText);
            return false;
        }
//        if (chamberLocation.length() < 3) {
//            chamberLocationEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_3));
//            CommonUtils.requestFocus(activity, chamberLocationEditText);
//            return false;
//        }

        return true;
    }

    public static boolean validateUserBirthDate(Activity activity, String userDOB, EditText userAreaEditText) {
        if (TextUtils.isEmpty(userDOB)) {
            userAreaEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, userAreaEditText);
            return false;
        }
        if (!DateTimeUtils.isValidDateTimeFormat(userDOB, DateTimeUtils.DATE_FORMAT)) {
            userAreaEditText.setError(activity.getResources().getString(R.string.error_invalid_date_format));
            CommonUtils.requestFocus(activity, userAreaEditText);
            return false;
        }

        return true;
    }

    public static boolean validateCoupon(Activity activity, String coupon, EditText couponEditText) {
        if (TextUtils.isEmpty(coupon)) {
            couponEditText.setError(activity.getResources().getString(R.string.error_field_required));
            CommonUtils.requestFocus(activity, couponEditText);
            return false;
        }
        if (!TextUtils.isEmpty(coupon) && coupon.length() < 5) {
            couponEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_5));
            CommonUtils.requestFocus(activity, couponEditText);
            return false;
        }

        return true;
    }

//    public static boolean validateDonateAmount(Activity activity, String amount, EditText amountEditText) {
//        if (TextUtils.isEmpty(amount)) {
//            amountEditText.setError(activity.getResources().getString(R.string.error_field_required));
//            CommonUtils.requestFocus(activity, amountEditText);
//            return false;
//        }
//        double userRewardAmount = SharedPreferencesManager.getDoubleSetting(activity, AFCHealthConstants.DOCTOR_REWARD_AMOUNT, 0);
//        if (Double.parseDouble(amount) > userRewardAmount) {
//            amountEditText.setError(activity.getResources().getString(R.string.error_amount));
//            CommonUtils.requestFocus(activity, amountEditText);
//            return false;
//        }
//
//        if (Double.parseDouble(amount) < 1000) {
//            amountEditText.setError(activity.getResources().getString(R.string.error_minimum_amount_required_1000));
//            CommonUtils.requestFocus(activity, amountEditText);
//            return false;
//        }
//
//        if (!TextUtils.isEmpty(amount) && amount.length() < 4) {
//            amountEditText.setError(activity.getResources().getString(R.string.error_minimum_length_required_4));
//            CommonUtils.requestFocus(activity, amountEditText);
//            return false;
//        }
//
//        return true;
//    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
}
