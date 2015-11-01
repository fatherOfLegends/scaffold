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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Earthquake {

    private String mAlert;
    private String mCode;
    private HashMap<String, Object> mLocation = new HashMap<>();
    private double mMagnitude;
    private String mPlace;
    private String mUrl;
    private long mTime;
    private long mUpdated;

    public Earthquake() {
    }

    public void setAlert(String alert) {
        mAlert = alert;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public void setLocation(HashMap<String, Object> location) {
        mLocation = location;
    }

    public void setMag(int magnitude) {
        mMagnitude = magnitude;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public void setUpdated(long updated) {
        mUpdated = updated;
    }

    public String getAlert() {
        return mAlert;
    }

    public String getCode() {
        return mCode;
    }

    public HashMap<String, Object> getLocation() {
        return mLocation;
    }

    public double getMag() {
        return mMagnitude;
    }

    public String getPlace() {
        return mPlace;
    }

    public String getUrl() {
        return mUrl;
    }

    public long getTime() {
        return mTime;
    }

    public long getUpdated() {
        return mUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Earthquake)) {
            return false;
        }

        Earthquake that = (Earthquake) o;

        return !(getCode() != null ? !getCode().equals(that.getCode()) : that.getCode() != null);

    }

    @Override
    public int hashCode() {
        return getCode() != null ? getCode().hashCode() : 0;
    }
}
