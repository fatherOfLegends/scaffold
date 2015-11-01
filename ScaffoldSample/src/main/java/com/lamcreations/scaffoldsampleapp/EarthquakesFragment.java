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
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.lamcreations.scaffold.common.adapters.BasicRecyclerViewAdapter;
import com.lamcreations.scaffold.common.fragments.RecyclerViewFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class EarthquakesFragment extends RecyclerViewFragment<EarthquakesFragment.EarthquakesRecyclerViewAdapter> {

    private static final String TAG = "EarthquakesFragment";
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
    protected EarthquakesRecyclerViewAdapter getRecyclerViewAdapter() {
        return new EarthquakesRecyclerViewAdapter(getActionMode());
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

    public class EarthquakesRecyclerViewAdapter
            extends BasicRecyclerViewAdapter<EarthquakeViewHolder, Earthquake>
            implements ChildEventListener {

        private Firebase mFirebase;
        private List<Earthquake> mEarthQuakes = new ArrayList<>();

        public EarthquakesRecyclerViewAdapter(ActivationMode mode) {
            super(mode);
            mFirebase = new Firebase("https://publicdata-earthquakes.firebaseio.com/by_continent/" + mContinent + "/" + mMagnitude + "/");
            mFirebase.orderByChild("updated").limitToFirst(50).addChildEventListener(this);
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
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            mEarthQuakes.add(0, dataSnapshot.getValue(Earthquake.class));
            notifyItemInserted(0);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Earthquake earthquake = dataSnapshot.getValue(Earthquake.class);
            int index = mEarthQuakes.indexOf(earthquake);
            if (index > -1) {
                mEarthQuakes.remove(index);
                mEarthQuakes.add(index, earthquake);
                notifyItemChanged(index);
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Earthquake earthquake = dataSnapshot.getValue(Earthquake.class);
            int index = mEarthQuakes.indexOf(earthquake);
            if (index > -1) {
                mEarthQuakes.remove(index);
                notifyItemRemoved(index);
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

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

        private String getMapUrl(Earthquake earthquake) {
            HashMap<String, Object> location = earthquake.getLocation();
            return new StringBuilder()
                    .append("http://maps.googleapis.com/maps/api/staticmap?")
                    .append("center=")
                    .append(location.get("lat"))
                    .append(",")
                    .append(location.get("lng"))
                    .append("&zoom=" + 8)
                    .append("&size=")
                    .append(getResources().getDimensionPixelSize(R.dimen.static_map_width))
                    .append("x")
                    .append(getResources().getDimensionPixelSize(R.dimen.static_map_height))
                    .append("&maptype=roadmap")
                    .append("&format=jpg")
                    .append("&sensor=false")
                    .append("&key=AIzaSyCcc5UuW7qa2o_Ui-3eUNsgpFADUz5jswg")
                    .toString();
        }
    }
}
