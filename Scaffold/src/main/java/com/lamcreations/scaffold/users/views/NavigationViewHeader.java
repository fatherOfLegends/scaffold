package com.lamcreations.scaffold.users.views;
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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lamcreations.scaffold.R;
import com.lamcreations.scaffold.common.views.RoundedImageView;
import com.lamcreations.scaffold.users.models.UserAccount;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unused")
public class NavigationViewHeader extends FrameLayout {

    private ImageView mHeaderImage;
    private ImageView mHeaderImageOverlay;
    private RoundedImageView mCurrentAccountImageView;
    private RoundedImageView mAltAccountImageView1;
    private RoundedImageView mAltAccountImageView2;
    private RoundedImageView mAltAccountImageView3;
    private TextView mCurrentAccountName;
    private TextView mCurrentAccountEmail;
    private ImageButton mAccountDropDownButton;

    private int mCurrentAccountIndex = -1;
    private List<UserAccount> mUserAccounts = new ArrayList<>();

    public NavigationViewHeader(Context context) {
        this(context, null);
    }

    public NavigationViewHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationViewHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundResource(android.R.color.white);
        LayoutInflater.from(getContext()).inflate(getLayoutResId(), this, true);

        mHeaderImage = findViewById(R.id.scaffold_navigation_view_header_image);
        mHeaderImageOverlay = findViewById(R.id.scaffold_navigation_view_header_image_overlay);
        mCurrentAccountImageView = findViewById(R.id.scaffold_navigation_view_current_account_image);
        mAltAccountImageView1 = findViewById(R.id.scaffold_navigation_view_alt_account_image_1);
        mAltAccountImageView2 = findViewById(R.id.scaffold_navigation_view_alt_account_image_2);
        mAltAccountImageView3 = findViewById(R.id.scaffold_navigation_view_alt_account_image_3);
        mCurrentAccountName = findViewById(R.id.scaffold_navigation_view_current_account_name);
        mCurrentAccountEmail = findViewById(R.id.scaffold_navigation_view_current_account_email);
        mAccountDropDownButton = findViewById(R.id.scaffold_navigation_view_account_drop_down_button);
    }

    @LayoutRes
    public int getLayoutResId() {
        return R.layout.scaffold_navigation_drawer_header;
    }

    public void setHeaderImageBitmap(Bitmap bitmap) {
        if (mHeaderImage != null) {
            mHeaderImage.setImageBitmap(bitmap);
        }
    }

    public void setHeaderImageDrawable(Drawable drawable) {
        if (mHeaderImage != null) {
            mHeaderImage.setImageDrawable(drawable);
        }
    }

    public void setHeaderImageResource(@DrawableRes int resId) {
        if (mHeaderImage != null) {
            mHeaderImage.setImageResource(resId);
        }
    }

    public void setHeaderImageOverlayAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha) {
        mHeaderImageOverlay.setAlpha(alpha);
    }

    public Drawable getCurrentAccountImage() {
        if (mCurrentAccountImageView != null) {
            return mCurrentAccountImageView.getDrawable();
        }
        return null;
    }

    private void setCurrentAccountImage(Bitmap bitmap) {
        if (mCurrentAccountImageView != null) {
            mCurrentAccountImageView.setImageBitmap(bitmap);
        }
    }

    private void setFirstAltAccountImage(Bitmap bitmap) {
        if (mAltAccountImageView1 != null) {
            mAltAccountImageView1.setImageBitmap(bitmap);
        }
    }

    private void setSecondAltAccountImage(Bitmap bitmap) {
        if (mAltAccountImageView2 != null) {
            mAltAccountImageView2.setImageBitmap(bitmap);
        }
    }

    private void setThirdAltAccountImage(Bitmap bitmap) {
        if (mAltAccountImageView3 != null) {
            mAltAccountImageView3.setImageBitmap(bitmap);
        }
    }

    public CharSequence getCurrentAccountName() {
        if (mCurrentAccountName != null) {
            return mCurrentAccountName.getText();
        }
        return null;
    }

    private void setCurrentAccountName(CharSequence accountName) {
        if (mCurrentAccountName != null) {
            mCurrentAccountName.setText(accountName);
        }
    }

    public CharSequence getCurrentAccountEmail() {
        if (mCurrentAccountEmail != null) {
            return mCurrentAccountEmail.getText();
        }
        return null;
    }

    private void setCurrentAccountEmail(CharSequence accountEmail) {
        if (mCurrentAccountEmail != null) {
            mCurrentAccountEmail.setText(accountEmail);
        }
    }

    public void setAccountDropDownButtonClickListener(OnClickListener listener) {
        if (mAccountDropDownButton != null) {
            mAccountDropDownButton.setOnClickListener(listener);
        }
    }

    private void showDropDownMenu(boolean show) {
        if (mAccountDropDownButton != null) {
            mAccountDropDownButton.setVisibility(show ? VISIBLE : GONE);
        }
    }

    public boolean hasMultipleAccounts() {
        return mUserAccounts.size() > 1;
    }

    public void addUserAccount(UserAccount userAccount) {
        if (userAccount != null) {
            mUserAccounts.add(userAccount);
            if (mUserAccounts.size() == 1) {
                loadUserAccount(0);
            }
            showDropDownMenu(hasMultipleAccounts());
        }
    }

    public void addUserAccounts(List<UserAccount> userAccounts) {
        mUserAccounts.addAll(userAccounts);
        if (mUserAccounts.size() >= 1 && mCurrentAccountIndex == -1) {
            loadUserAccount(0);
        }
        showDropDownMenu(hasMultipleAccounts());
    }

    public void removeCurrentUserAccount() {
        mUserAccounts.remove(mCurrentAccountIndex);
        if (mUserAccounts.size() >= 1) {
            loadUserAccount(0);
        } else {
            loadUserAccount(-1);
        }
        showDropDownMenu(hasMultipleAccounts());
    }

    private void loadUserAccount(int index) {
        mCurrentAccountIndex = index;
        if (mCurrentAccountIndex >= 0 && mCurrentAccountIndex < mUserAccounts.size()) {
            UserAccount userAccount = mUserAccounts.get(mCurrentAccountIndex);
            setCurrentAccountName(userAccount.getName());
            setCurrentAccountEmail(userAccount.getEmail());
            setCurrentAccountImage(null);
            setHeaderImageDrawable(null);
            userAccount.getProfileImage(getContext(), new UserAccount.ImageCallback() {
                @Override
                public void onImageAvailable(Bitmap image) {
                    setCurrentAccountImage(image);
                }
            });
            userAccount.getHeaderImage(getContext(), new UserAccount.ImageCallback() {
                @Override
                public void onImageAvailable(Bitmap image) {
                    setHeaderImageBitmap(image);
                }
            });
            setAltAccountImages(mCurrentAccountIndex);
        } else {
            setCurrentAccountName(null);
            setCurrentAccountEmail(null);
            setCurrentAccountImage(null);
            setHeaderImageDrawable(null);
            setFirstAltAccountImage(null);
            setSecondAltAccountImage(null);
            setThirdAltAccountImage(null);
        }
    }

    private void setAltAccountImages(int currentAccountIndex) {
        ArrayList<UserAccount> otherAccounts = new ArrayList<>();
        int size = mUserAccounts.size();
        for (int i = 0; i < size; ++i) {
            if (i < currentAccountIndex) {
                otherAccounts.add(mUserAccounts.get(i));
            } else if (i > currentAccountIndex) {
                otherAccounts.add(i - currentAccountIndex, mUserAccounts.get(i));
            }
        }
        size = otherAccounts.size();
        for (int i = 0; i < 3 && i < size; ++i) {
            final int finalI = i;
            otherAccounts.get(i).getProfileImage(getContext(), new UserAccount.ImageCallback() {
                @Override
                public void onImageAvailable(Bitmap image) {
                    switch (finalI) {
                        case 0:
                            setFirstAltAccountImage(image);
                            break;
                        case 1:
                            setSecondAltAccountImage(image);
                            break;
                        case 2:
                            setThirdAltAccountImage(image);
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }
}
