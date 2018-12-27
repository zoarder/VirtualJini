package com.technologies.virtualjini.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nostra13.jaazmultimedia.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.jaazmultimedia.core.ImageLoader;
import com.nostra13.jaazmultimedia.core.ImageLoaderConfiguration;
import com.nostra13.jaazmultimedia.core.assist.QueueProcessingType;
import com.technologies.virtualjini.R;
import com.technologies.virtualjini.fragments.HomeFragment;
import com.technologies.virtualjini.fragments.MyPostedFragment;
import com.technologies.virtualjini.fragments.MyWishListFragment;
import com.technologies.virtualjini.utils.AFCHealthConstants;
import com.technologies.virtualjini.utils.SharedPreferencesManager;

public class MainActivity extends BaseActivity {

    // Remove the below line after defining your own ad unit ID.
    private static final String TOAST_TEXT = "Test ads are being shown. "
            + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.";
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_MY_POSTED = "my_posted";
    private static final String TAG_MY_WISH_LIST = "my_wish_list";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initImageLoader(getApplicationContext());
        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        activityTitles = getResources().getStringArray(R.array.drawer_menu_item_titles);

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.activity_main_app_bar_main_av);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    @Override
    void setContentView() {

    }

    @Override
    void setupActionBar() {

    }

    @Override
    void initializeEditTextComponents() {

    }

    @Override
    void initializeButtonComponents() {

    }

    @Override
    void initializeTextViewComponents() {

    }

    @Override
    void initializeImageViewComponents() {

    }

    @Override
    void initializeOtherViewComponents() {

    }

    @Override
    void initializeViewComponentsEventListeners() {

    }

    @Override
    void removeViewComponentsEventListeners() {

    }

    @Override
    void exitThisWithAnimation() {
        finish();
        overridePendingTransition(R.anim.trans_top_in,
                R.anim.trans_top_out);
    }

    @Override
    void startNextWithAnimation(Intent intent, boolean isFinish) {
        if (isFinish) {
            finish();
        }
        int adCounter = SharedPreferencesManager.getIntegerSetting(MainActivity.this, AFCHealthConstants.AD_COUNTER, 0);
        SharedPreferencesManager.setIntegerSetting(MainActivity.this, AFCHealthConstants.AD_COUNTER, ++adCounter);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_left_in,
                R.anim.trans_left_out);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main_menu content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        drawer.closeDrawers();

//        // refresh toolbar menu
//        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // Home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // My Posted
                MyPostedFragment myPostedFragment = new MyPostedFragment();
                return myPostedFragment;
            case 2:
                // My Wish List
                MyWishListFragment myWishListFragment = new MyWishListFragment();
                return myWishListFragment;

            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int adCounter = 0;
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main_menu content with ContentFragment Which is our Inbox View;
                    case R.id.drawer_menu_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.drawer_menu_my_posted:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_MY_POSTED;
                        break;
                    case R.id.drawer_menu_my_wish_list:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_MY_WISH_LIST;
                        break;

                    case R.id.drawer_menu_about_us:
                        // launch new intent instead of loading fragment
                        adCounter = SharedPreferencesManager.getIntegerSetting(MainActivity.this, AFCHealthConstants.AD_COUNTER, 0);
                        SharedPreferencesManager.setIntegerSetting(MainActivity.this, AFCHealthConstants.AD_COUNTER, ++adCounter);
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        overridePendingTransition(R.anim.trans_left_in,
                                R.anim.trans_left_out);
                        drawer.closeDrawers();
                        return true;
                    case R.id.drawer_menu_contact_us:
                        adCounter = SharedPreferencesManager.getIntegerSetting(MainActivity.this, AFCHealthConstants.AD_COUNTER, 0);
                        SharedPreferencesManager.setIntegerSetting(MainActivity.this, AFCHealthConstants.AD_COUNTER, ++adCounter);
                        startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
                        overridePendingTransition(R.anim.trans_left_in,
                                R.anim.trans_left_out);
                        drawer.closeDrawers();
                        return true;
                    case R.id.drawer_menu_faq:
                        adCounter = SharedPreferencesManager.getIntegerSetting(MainActivity.this, AFCHealthConstants.AD_COUNTER, 0);
                        SharedPreferencesManager.setIntegerSetting(MainActivity.this, AFCHealthConstants.AD_COUNTER, ++adCounter);
                        startActivity(new Intent(MainActivity.this, FAQActivity.class));
                        overridePendingTransition(R.anim.trans_left_in,
                                R.anim.trans_left_out);
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        exitFromApp();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//
//        MenuItem item = menu.findItem(R.id.action_search);
//        SearchView sv = new SearchView(this);
//        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
//        MenuItemCompat.setActionView(item, sv);
//        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if (!query.equals(null) && !query.isEmpty()) {
//                    try {
//                        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
//                        intent.putExtra("title", getResources().getString(R.string.main_menu_search_text));
//                        intent.putExtra("url", getResources().getString(R.string.main_menu_search_url));
//                        startActivity(intent);
//                        overridePendingTransition(R.anim.trans_left_in,
//                                R.anim.trans_left_out);
//                    } catch (NullPointerException ex) {
//                    }
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (!newText.equals(null) && !newText.isEmpty()) {
//                    try {
//
//                    } catch (NullPointerException ex) {
//                    }
//                }
//                return false;
//            }
//        });
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        if (id == R.id.main_menu_web_mail) {
//            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
//            intent.putExtra("title", getResources().getString(R.string.main_menu_web_mail_text));
//            intent.putExtra("url", getResources().getString(R.string.main_menu_web_mail_url));
//            startActivity(intent);
//            overridePendingTransition(R.anim.trans_left_in,
//                    R.anim.trans_left_out);
//            return true;
//        } else if (id == R.id.main_menu_about_developer) {
//            Intent intent = new Intent(MainActivity.this, ListActivity.class);
//            intent.putExtra("title", getResources().getString(R.string.main_menu_about_developer_text));
//            intent.putExtra("content", getResources().getStringArray(R.array.about_developer_content));
//            startActivity(intent);
//            overridePendingTransition(R.anim.trans_left_in,
//                    R.anim.trans_left_out);
//            return true;
//        } else if (id == R.id.main_menu_exit_app) {
//            exitFromApp();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public void exitFromApp() {
//        final SweetAlertDialog sDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_MODIFIED_TYPE);
//        sDialog.setTitleText(getResources().getString(R.string.title_exit_dialog));
//        sDialog.setTitle("sDialog");
//        sDialog.setConfirmText(getResources().getString(R.string.dialog_confirm_text));
//        sDialog.setCancelText(getResources().getString(R.string.dialog_cancel_text));
//        sDialog.setCancelable(false);
//        sDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//            public void onClick(SweetAlertDialog mDialog) {
//                mDialog.dismiss();
        exitThisWithAnimation();
//            }
//        });
//        sDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//            public void onClick(SweetAlertDialog mDialog) {
//                mDialog.cancel();
//            }
//        });
//        sDialog.show();
        /*//SharedPreferencesManager.setBooleanSetting(activity, PolyLifeConstants.KEY_IS_IMAGE_REQUESTED_FOR_PROFILE_PICTURE, false);
            ImageChooseOptionDialog imageChooseOptionDialog = new ImageChooseOptionDialog(activity);
            imageChooseOptionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            imageChooseOptionDialog.setCanceledOnTouchOutside(true);
            imageChooseOptionDialog.show();
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(imageChooseOptionDialog.getWindow().getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            imageChooseOptionDialog.getWindow().setAttributes(layoutParams);

            imageChooseOptionDialog
                    .setImageChooseOptionDialogInterfaceListener(new ImageChooseOptionDialogInterface() {

                        @Override
                        public void onDefaultOptionClicked() {
                            // TODO Auto-generated method stub
                            //SharedPreferencesManager.setStringSetting(activity, PolyLifeConstants.KEY_WALLPAPER_IMAGE_PATH, "");
                        }
                    });*/
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }
}
