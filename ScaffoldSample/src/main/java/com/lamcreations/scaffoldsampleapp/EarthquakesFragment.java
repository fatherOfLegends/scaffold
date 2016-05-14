/*
 * Copyright (C) 2015 LAM Creations
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lamcreations.scaffoldsampleapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.lamcreations.scaffold.common.adapters.BasicRecyclerViewAdapter;
import com.lamcreations.scaffold.common.fragments.RecyclerViewFragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EarthquakesFragment extends RecyclerViewFragment<EarthquakesFragment.EarthquakesRecyclerViewAdapter> {

    public static final String MAGNITUDE = "magnitude";
    public static final String CONTINENT = "continent";
    private int mMagnitude = 5;
    private String mContinent = "north_america";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mMagnitude = args.getInt(MAGNITUDE, 5);
            mContinent = args.getString(CONTINENT, "north_america");
        }
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showProgressBar(getRecyclerViewAdapter().isLoading());
    }

    @Override
    protected EarthquakesRecyclerViewAdapter getRecyclerViewAdapter() {
        if (mRecyclerView.getAdapter() == null) {
            return new EarthquakesRecyclerViewAdapter(getActionMode());
        }
        return (EarthquakesRecyclerViewAdapter) mRecyclerView.getAdapter();
    }

    @Override
    protected RecyclerView.ItemAnimator getItemAnimator() {
        return new DefaultItemAnimator();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected BasicRecyclerViewAdapter.ActivationMode getActionMode() {
        return BasicRecyclerViewAdapter.ActivationMode.NONE;
    }

    @Override
    protected boolean isSwipeRefreshEnabled() {
        return false;
    }

    private void showProgressBar(final boolean show) {
        if ((show && !mSwipeRefreshLayout.isRefreshing()) ||
                (!show && mSwipeRefreshLayout.isRefreshing())) {
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(show);
                }
            });
        }
    }

    public class EarthquakesRecyclerViewAdapter
            extends BasicRecyclerViewAdapter<EarthquakeViewHolder, Earthquake>
            implements ValueEventListener {

        private boolean mLoading = true;
        private Firebase mFirebase;
        private List<Earthquake> mEarthQuakes = new ArrayList<>();

        public EarthquakesRecyclerViewAdapter(ActivationMode mode) {
            super(mode);
            mFirebase = new Firebase("https://publicdata-earthquakes.firebaseio.com/by_continent/" + mContinent + "/" + mMagnitude + "/");
            mFirebase.orderByChild("updated").limitToFirst(50).addListenerForSingleValueEvent(this);
        }

        @Override
        protected List<Earthquake> getDataList() {
            return mEarthQuakes;
        }

        @Override
        public EarthquakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new EarthquakeViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.earthquake_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(EarthquakeViewHolder holder, int position) {
            holder.bind(getDataList().get(position));
        }

        @Override
        public void onDataChange(final DataSnapshot dataSnapshot) {
            mLoading = false;
            showProgressBar(false);
            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                mEarthQuakes.add(0, snapshot.getValue(Earthquake.class));
                notifyItemInserted(0);
            }
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            mLoading = false;
            showProgressBar(false);
        }

        public boolean isLoading(){
            return mLoading;
        }
    }

    public class EarthquakeViewHolder extends RecyclerView.ViewHolder {

        private ImageView mMapImageView;
        private TextView mPlaceTextView;
        private TextView mMagTextView;

        public EarthquakeViewHolder(View itemView) {
            super(itemView);

            mMapImageView = (ImageView) itemView.findViewById(R.id.map);
            mPlaceTextView = (TextView) itemView.findViewById(R.id.place);
            mMagTextView = (TextView) itemView.findViewById(R.id.magnitude);
        }

        public void bind(Earthquake earthquake) {
            Glide.with(itemView.getContext()).load(getMapUrl(earthquake)).into(mMapImageView);
            mPlaceTextView.setText(earthquake.getPlace());
            mMagTextView.setText(String.format("Magnitude %s", earthquake.getMag()));
        }

        private Uri getMapUrl(Earthquake earthquake) {
            HashMap<String, Object> location = earthquake.getLocation();
            return Uri.parse("http://maps.googleapis.com/maps/api/staticmap")
                .buildUpon()
                .appendQueryParameter("center", location.get("lat") + "," +
                    location.get("lng"))
                .appendQueryParameter("zoom", "8")
                .appendQueryParameter("size",
                    getResources().getDimensionPixelSize(R.dimen.static_map_width) + "x"
                        + getResources().getDimensionPixelSize(R.dimen.static_map_height))
                .appendQueryParameter("maptype", "roadmap")
                .appendQueryParameter("format", "jpg")
                .appendQueryParameter("sensor", "false")
                .appendQueryParameter("key", "AIzaSyCcc5UuW7qa2o_Ui-3eUNsgpFADUz5jswg")
                .build();
        }
    }
}
