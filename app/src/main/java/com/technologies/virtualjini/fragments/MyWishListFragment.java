package com.technologies.virtualjini.fragments;

/**
 * Created by ZOARDER AL MUKTADIR on 01/23/3017.
 */

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nostra13.jaazmultimedia.core.DisplayImageOptions;
import com.nostra13.jaazmultimedia.core.ImageLoader;
import com.nostra13.jaazmultimedia.core.assist.FailReason;
import com.nostra13.jaazmultimedia.core.listener.ImageLoadingProgressListener;
import com.nostra13.jaazmultimedia.core.listener.SimpleImageLoadingListener;
import com.technologies.virtualjini.R;
import com.technologies.virtualjini.activities.AddEditPostActivity;
import com.technologies.virtualjini.activities.LoginActivity;
import com.technologies.virtualjini.activities.PostDetailsActivity;
import com.technologies.virtualjini.models.GetAllPostResponse;
import com.technologies.virtualjini.models.GetAllPostResponseData;
import com.technologies.virtualjini.models.PostData;
import com.technologies.virtualjini.utils.AFCHealthConstants;
import com.technologies.virtualjini.utils.AppConfig;
import com.technologies.virtualjini.utils.AppController;
import com.technologies.virtualjini.utils.SharedPreferencesManager;
import com.technologies.virtualjini.utils.ShowLog;
import com.technologies.virtualjini.volley.ObjectRequest;
import com.technologies.virtualjini.volley.VolleyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.technologies.virtualjini.utils.AFCHealthConstants.USER_IS_LOGIN;

public class MyWishListFragment extends Fragment {

    private static final String TAG = MyWishListFragment.class.getSimpleName();
    private RecyclerView rv;
    private MyWishListAdapter adapter;
    private int counter = 0;
    private int total_tolet = 1;

    private ObjectRequest<GetAllPostResponse> mPostObjectRequest;
    ArrayList<PostData> mPostItems = new ArrayList<>();
    private GetAllPostResponse mGetAllPostResponse;
    private GetAllPostResponseData mGetAllPostResponseData;

    //    ProgressDialog pDialog;
    Activity activity;
    private TextView ev;
    private boolean mark = false;

    public MyWishListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        // Progress dialog
//        pDialog = new ProgressDialog(activity);
//        pDialog.setCancelable(false);
//        pDialog.setCanceledOnTouchOutside(false);
//        pDialog.setMessage("Loading My Wish List...");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_wish_list, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.fragment_my_wish_list_parent_rv);
        ev = (TextView) rootView.findViewById(R.id.fragment_my_wish_list_empty_list_tv);
        rv.setHasFixedSize(true);
//        preparePostItems();

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        mPostItems.add(null);
        adapter = new MyWishListFragment.MyWishListAdapter();
        rv.setAdapter(adapter);

        rv.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
//                    Snackbar.make(child, "Process Under Development", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
                    Intent intent = new Intent(getActivity(), PostDetailsActivity.class);
                    intent.putExtra("post", mPostItems.get(position));
                    intent.putExtra("from", "my_wish_list");
                    intent.putExtra("position", position);
                    int adCounter = SharedPreferencesManager.getIntegerSetting(activity, AFCHealthConstants.AD_COUNTER, 0);
                    SharedPreferencesManager.setIntegerSetting(activity, AFCHealthConstants.AD_COUNTER, ++adCounter);
                    startActivityForResult(intent, 303);
                    getActivity().overridePendingTransition(R.anim.trans_left_in,
                            R.anim.trans_left_out);
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fragment_my_wish_list_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPreferencesManager.getBooleanSetting(getActivity(), USER_IS_LOGIN, false)) {
                    Intent intent = new Intent(activity, AddEditPostActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("position", -1);
                    intent.putExtra("data", new PostData());
                    int adCounter = SharedPreferencesManager.getIntegerSetting(activity, AFCHealthConstants.AD_COUNTER, 0);
                    SharedPreferencesManager.setIntegerSetting(activity, AFCHealthConstants.AD_COUNTER, ++adCounter);
                    startActivityForResult(intent, 301);
                    activity.overridePendingTransition(R.anim.trans_left_in,
                            R.anim.trans_left_out);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
                    builder.setTitle("Log In Required!!!");
                    builder.setMessage("You Have to Log In First before creating new Post. Do you want to go Log In Now?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(activity, LoginActivity.class);
                            int adCounter = SharedPreferencesManager.getIntegerSetting(activity, AFCHealthConstants.AD_COUNTER, 0);
                            SharedPreferencesManager.setIntegerSetting(activity, AFCHealthConstants.AD_COUNTER, ++adCounter);
                            startActivityForResult(intent, 302);
                            activity.overridePendingTransition(R.anim.trans_left_in,
                                    R.anim.trans_left_out);
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });

        if (SharedPreferencesManager.getBooleanSetting(getActivity(), USER_IS_LOGIN, false)) {
//            pDialog.show();
            counter++;
            getAllMyWishList();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
            builder.setTitle("Log In Required!!!");
            builder.setMessage("You Have to Log In First before getting your Post. Do you want to go Log In Now?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(activity, LoginActivity.class);
                    int adCounter = SharedPreferencesManager.getIntegerSetting(activity, AFCHealthConstants.AD_COUNTER, 0);
                    SharedPreferencesManager.setIntegerSetting(activity, AFCHealthConstants.AD_COUNTER, ++adCounter);
                    startActivityForResult(intent, 304);
                    activity.overridePendingTransition(R.anim.trans_left_in,
                            R.anim.trans_left_out);
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        return rootView;
    }

    private void updateView() {
        if (mPostItems.size() == 0) {
            rv.setVisibility(View.GONE);
            ev.setVisibility(View.VISIBLE);
        } else {
            rv.setVisibility(View.VISIBLE);
            ev.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        GetAllProfessionalResponseData data = null;
        PostData item;
        switch (resultCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case Activity.RESULT_OK:
                switch (requestCode) {
                    case 301:
                        ShowLog.e(TAG, "added");
//                        item = data.getExtras().getParcelable("data");
//                        mPostItems.add(item);
//                        updateView();
//                        adapter.notifyDataSetChanged();
//                        mListener.onNewProfessionalDataAdded(data);
                        break;
                    case 302:
//                        pDialog.show();
                        counter++;
                        getAllMyWishList();
                        mark = true;
                        break;

                    case 303:
                        item = data.getExtras().getParcelable("data");
                        String type = data.getExtras().getString("type");
                        int pos = data.getExtras().getInt("position");
                        if (type.equals("delete")) {
                            mPostItems.remove(pos);
                        }
                        updateView();
                        adapter.notifyDataSetChanged();
                        break;
                    case 304:
//                        pDialog.show();
                        counter++;
                        getAllMyWishList();
                        break;
                }
                break;
        }
    }

    class MyWishListAdapter extends RecyclerView.Adapter {
        private final int VIEW_ITEM = 1;
        private final int VIEW_PROG = 0;

        // The minimum amount of items to have below your current scroll position
        // before loading more.
        private int visibleMinThreshold = 1;
        private int visibleMaxThreshold = 5;
        private int lastVisibleItem, totalItemCount;
        private boolean loading;

        private DisplayImageOptions options;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class MyWishListViewHolder extends RecyclerView.ViewHolder {
            public CardView mParentCardView;
            public ImageView my_wish_list_item_layout_main_iv;
            public ProgressBar my_wish_list_item_layout_image_pb;
            public TextView my_wish_list_item_layout_title_tv;
            public TextView my_wish_list_item_layout_type_tv;
            public TextView my_wish_list_item_layout_bed_room_tv;
            public TextView my_wish_list_item_layout_bath_room_tv;
            public TextView my_wish_list_item_layout_car_parking_tv;
            public TextView my_wish_list_item_layout_living_room_tv;
            public TextView my_wish_list_item_layout_dyning_room_tv;
            public TextView my_wish_list_item_layout_address_tv;
            public TextView my_wish_list_item_layout_size_tv;
            public TextView my_wish_list_item_layout_rent_tv;
            public TextView my_wish_list_item_layout_available_from_tv;
//            public Button my_wish_list_item_layout_add_wishlist_bt;

            public MyWishListViewHolder(View v) {
                super(v);

                mParentCardView = (CardView) v.findViewById(R.id.my_wish_list_item_layout_parent_cv);
                my_wish_list_item_layout_main_iv = (ImageView) v.findViewById(R.id.my_wish_list_item_layout_main_iv);
                my_wish_list_item_layout_image_pb = (ProgressBar) v.findViewById(R.id.my_wish_list_item_layout_image_pb);
                my_wish_list_item_layout_title_tv = (TextView) v.findViewById(R.id.my_wish_list_item_layout_title_tv);
                my_wish_list_item_layout_type_tv = (TextView) v.findViewById(R.id.my_wish_list_item_layout_type_tv);
                my_wish_list_item_layout_bed_room_tv = (TextView) v.findViewById(R.id.my_wish_list_item_layout_bed_room_tv);
                my_wish_list_item_layout_bath_room_tv = (TextView) v.findViewById(R.id.my_wish_list_item_layout_bath_room_tv);
                my_wish_list_item_layout_car_parking_tv = (TextView) v.findViewById(R.id.my_wish_list_item_layout_car_parking_tv);
                my_wish_list_item_layout_living_room_tv = (TextView) v.findViewById(R.id.my_wish_list_item_layout_living_room_tv);
                my_wish_list_item_layout_dyning_room_tv = (TextView) v.findViewById(R.id.my_wish_list_item_layout_dyning_room_tv);
                my_wish_list_item_layout_address_tv = (TextView) v.findViewById(R.id.my_wish_list_item_layout_address_tv);
                my_wish_list_item_layout_size_tv = (TextView) v.findViewById(R.id.my_wish_list_item_layout_size_tv);
                my_wish_list_item_layout_rent_tv = (TextView) v.findViewById(R.id.my_wish_list_item_layout_rent_tv);
                my_wish_list_item_layout_available_from_tv = (TextView) v.findViewById(R.id.my_wish_list_item_layout_available_from_tv);
//                my_wish_list_item_layout_add_wishlist_bt = (Button) v.findViewById(R.id.my_wish_list_item_layout_add_wishlist_bt);
            }
        }

        public class ProgressViewHolder extends RecyclerView.ViewHolder {
            public ProgressBar progressBar;

            public ProgressViewHolder(View v) {
                super(v);
                progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyWishListAdapter() {
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.logo)
                    .showImageForEmptyUri(R.drawable.ic_empty)
                    .showImageOnFail(R.drawable.ic_error)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
            loading = true;
            if (rv.getLayoutManager() instanceof LinearLayoutManager) {

                final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rv
                        .getLayoutManager();


                rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        totalItemCount = linearLayoutManager.getItemCount();
                        lastVisibleItem = linearLayoutManager
                                .findLastVisibleItemPosition();
                        if (!loading
                                && totalItemCount <= (lastVisibleItem + visibleMinThreshold)) {
                            ShowLog.e(TAG, "counter: " + counter);
                            ShowLog.e(TAG, "total_tolet: " + total_tolet);
                            if (counter * visibleMaxThreshold < total_tolet) {
                                mPostItems.add(null);
                                notifyItemInserted(mPostItems.size() - 1);
                                counter++;
//                            pDialog.show();
                                getAllMyWishList();
                                loading = true;
                            }
                        }
                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            return mPostItems.get(position) != null ? VIEW_ITEM : VIEW_PROG;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
            RecyclerView.ViewHolder vh;
            if (viewType == VIEW_ITEM) {// create a new view
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.my_wish_list_item_layout, parent, false);
                // set the view's size, margins, paddings and layout parameters
                vh = new MyWishListViewHolder(v);
            } else {
                View v = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.progress_item, parent, false);

                vh = new ProgressViewHolder(v);
            }
            return vh;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyWishListViewHolder) {
                ((MyWishListViewHolder) holder).my_wish_list_item_layout_title_tv.setText(mPostItems.get(position).getToletTitle());
                ((MyWishListViewHolder) holder).my_wish_list_item_layout_type_tv.setText(mPostItems.get(position).getToletType());
                ((MyWishListViewHolder) holder).my_wish_list_item_layout_address_tv.setText(mPostItems.get(position).getToletAddress());
                ((MyWishListViewHolder) holder).my_wish_list_item_layout_available_from_tv.setText(mPostItems.get(position).getToletFrom());
                ((MyWishListViewHolder) holder).my_wish_list_item_layout_size_tv.setText(mPostItems.get(position).getToletSqfeet() + " SQFT");
                ((MyWishListViewHolder) holder).my_wish_list_item_layout_rent_tv.setText("BDT " + mPostItems.get(position).getToletRent() + "/Month");
//                ((MyWishListViewHolder) holder).my_wish_list_item_layout_add_wishlist_bt.setTag(position);

                if (Integer.parseInt(mPostItems.get(position).getToletBedRoom()) > 0) {
                    ((MyWishListViewHolder) holder).my_wish_list_item_layout_bed_room_tv.setText(mPostItems.get(position).getToletBedRoom() + "");
                } else {
                    ((MyWishListViewHolder) holder).my_wish_list_item_layout_bed_room_tv.setVisibility(View.GONE);
                }

                if (Integer.parseInt(mPostItems.get(position).getToletBathRoom()) > 0) {
                    ((MyWishListViewHolder) holder).my_wish_list_item_layout_bath_room_tv.setText(mPostItems.get(position).getToletBathRoom() + "");
                } else {
                    ((MyWishListViewHolder) holder).my_wish_list_item_layout_bath_room_tv.setVisibility(View.GONE);
                }

                if (Integer.parseInt(mPostItems.get(position).getToletCarParking()) > 0) {
                    ((MyWishListViewHolder) holder).my_wish_list_item_layout_car_parking_tv.setText(mPostItems.get(position).getToletCarParking() + "");
                } else {
                    ((MyWishListViewHolder) holder).my_wish_list_item_layout_car_parking_tv.setVisibility(View.GONE);
                }

                if (Integer.parseInt(mPostItems.get(position).getToletLivingRoom()) > 0) {
                    ((MyWishListViewHolder) holder).my_wish_list_item_layout_living_room_tv.setText(mPostItems.get(position).getToletLivingRoom() + "");
                } else {
                    ((MyWishListViewHolder) holder).my_wish_list_item_layout_living_room_tv.setVisibility(View.GONE);
                }

                if (Integer.parseInt(mPostItems.get(position).getToletDyningRoom()) > 0) {
                    ((MyWishListViewHolder) holder).my_wish_list_item_layout_dyning_room_tv.setText(mPostItems.get(position).getToletDyningRoom() + "");
                } else {
                    ((MyWishListViewHolder) holder).my_wish_list_item_layout_dyning_room_tv.setVisibility(View.GONE);
                }

//                ((MyWishListViewHolder) holder).my_wish_list_item_layout_add_wishlist_bt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        final int pos = (int) v.getTag();
//                        if (SharedPreferencesManager.getBooleanSetting(getActivity(), USER_IS_LOGIN, false)) {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
//                            builder.setTitle("Add To Wish List!!!");
//                            builder.setMessage("Are you sure that want to Add this post to your wish list?");
//                            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    addToWishList(mPostItems.get(pos).getId());
//                                    dialog.dismiss();
//                                }
//                            });
//                            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                            builder.show();
//                        } else {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
//                            builder.setTitle("Log In Required!!!");
//                            builder.setMessage("You Have to Log In First before Add this post to your wish list. Do you want to go Log In Now?");
//
//                            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent intent = new Intent(activity, LoginActivity.class);
//                                    startActivityForResult(intent, 301);
//                                    activity.overridePendingTransition(R.anim.trans_left_in,
//                                            R.anim.trans_left_out);
//                                }
//                            });
//                            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                            builder.show();
//                        }
//
//                    }
//                });

                ImageLoader.getInstance()
                        .displayImage(mPostItems.get(position).getTitleImage(), ((MyWishListViewHolder) holder).my_wish_list_item_layout_main_iv, options, new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String imageUri, View view) {
                                ((MyWishListViewHolder) holder).my_wish_list_item_layout_image_pb.setProgress(0);
                                ((MyWishListViewHolder) holder).my_wish_list_item_layout_image_pb.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                                ((MyWishListViewHolder) holder).my_wish_list_item_layout_image_pb.setVisibility(View.GONE);
                            }

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                ((MyWishListViewHolder) holder).my_wish_list_item_layout_image_pb.setVisibility(View.GONE);
                            }
                        }, new ImageLoadingProgressListener() {
                            @Override
                            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                                ((MyWishListViewHolder) holder).my_wish_list_item_layout_image_pb.setProgress(Math.round(100.0f * current / total));
                            }
                        });
            } else {
                ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            }
        }

        public void setLoaded() {
            loading = false;
        }

        @Override
        public int getItemCount() {
            return mPostItems.size();
        }
    }

//    private void addToWishList(final String id) {
//        pDialog.setMessage("Adding to Wish List...");
//        pDialog.show();
//        String userId = SharedPreferencesManager.getStringSetting(activity, AFCHealthConstants.LOGIN_USER_PHONE_NO, "");
//        final Map<String, String> params = new HashMap<>();
//        params.put(getString(R.string.api_param_12), userId);
//        params.put(getString(R.string.api_param_13), id);
//        params.put(getString(R.string.api_param_62), AppConfig.ADD_TO_WISH_LIST);
//
//        final Map<String, String> headers = new HashMap<>();
////        headers.put(getString(R.string.api_param_1), apiToken);
//        StringRequest strReq = new StringRequest(Request.Method.POST,
//                AppConfig.MAIN_URL, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                ShowLog.e(TAG, response.toString());
//                pDialog.hide();
//                try {
//                    JSONObject jObj = new JSONObject(response.toString());
//                    boolean code = jObj.getBoolean(KEY_ERROR);
//                    String message = jObj.getString(KEY_MESSAGE);
////                    if (!code) {
////                        mDataArrayList.remove(pos);
////                        mAdapter.notifyDataSetChanged();
////                        mListener.onEducationDataDeleted(pos);
////                    } else {
////
////                    }
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
//                    builder.setTitle(message);
//                    builder.setMessage(message);
//
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    });
//                    builder.setIcon(android.R.drawable.ic_dialog_alert);
//                    builder.show();
//                } catch (JSONException e) {
//                    // JSON error
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                pDialog.hide();
//                VolleyUtils.showVolleyResponseError(activity, error, false);
//                return;
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() {
//                return headers;
//            }
//
//            @Override
//            public Map<String, String> getParams() {
//
//                return params;
//            }
//        };
//        strReq.setRetryPolicy(new DefaultRetryPolicy(
//                AFCHealthConstants.SOCKET_TIMEOUT_MS,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(strReq);
//    }

    private void getAllMyWishList() {

        Map<String, String> params = new HashMap<>();
        params.put(getString(R.string.api_param_74), counter + "");
        params.put(getString(R.string.api_param_12), SharedPreferencesManager.getStringSetting(getActivity(), AFCHealthConstants.LOGIN_USER_PHONE_NO, ""));
        params.put(getString(R.string.api_param_61), AppConfig.GET_MY_WISH_LIST);
        ShowLog.e(TAG, "Counter: " + counter);
        ShowLog.e(TAG, "User ID: " + SharedPreferencesManager.getStringSetting(getActivity(), AFCHealthConstants.LOGIN_USER_PHONE_NO, ""));
        ShowLog.e(TAG, "Tag: " + AppConfig.GET_MY_WISH_LIST);

        mPostObjectRequest = new ObjectRequest<>(Request.Method.POST, AppConfig.MAIN_URL, params,
                new Response.Listener<GetAllPostResponse>() {
                    @Override
                    public void onResponse(GetAllPostResponse response) {
                        ShowLog.e(TAG, response.toString());
//                        pDialog.hide();
                        if (!response.isError()) {
                            mGetAllPostResponse = response;
                            mGetAllPostResponseData = response.getData();
                            mPostItems.remove(mPostItems.size() - 1);
                            adapter.notifyItemRemoved(mPostItems.size());
                            if (mGetAllPostResponseData.getAllPosts().size() > 0) {
                                int size = mPostItems.size();
                                mPostItems.addAll(mGetAllPostResponseData.getAllPosts());
                                adapter.notifyItemRangeInserted(size, mGetAllPostResponseData.getAllPosts().size());
                            }
                            if (counter == 1) {
                                total_tolet = mGetAllPostResponseData.getTotalTolet();
                            }

                            adapter.setLoaded();
                            updateView();
                        } else {
                            mPostItems.remove(mPostItems.size() - 1);
                            adapter.notifyItemRemoved(mPostItems.size());
                            adapter.setLoaded();
                            counter--;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                pDialog.hide();
                VolleyUtils.showVolleyResponseError(getActivity(), error, true);
                return;
            }
        }, GetAllPostResponse.class);

        mPostObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                AFCHealthConstants.SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(mPostObjectRequest);
    }
}