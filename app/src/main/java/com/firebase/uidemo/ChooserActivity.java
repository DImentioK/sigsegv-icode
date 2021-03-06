/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.firebase.uidemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.firebase.uidemo.auth.AuthUiActivity;
import com.firebase.uidemo.auth.SignedInActivity;
import com.firebase.uidemo.database.ChatActivity;
import com.firebase.uidemo.storage.ImageActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooserActivity extends AppCompatActivity {
    @BindView(R.id.activities)
    RecyclerView mActivities;
    private static final int RC_SIGN_IN = 100;

    View mRootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);
        ButterKnife.bind(this);

        mActivities.setLayoutManager(new LinearLayoutManager(this));
        mActivities.setAdapter(new ActivityChooserAdapter());
        mActivities.setHasFixedSize(true);
        Log.d("New activity", getApplicationContext().toString());
        getApplicationContext().startActivity(new Intent(getApplicationContext(), AuthUiActivity.class));

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(SignedInActivity.createIntent(this, null));
            finish();
        }
        else{
            signIn();
        }
    }

    public void signIn() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setTheme(R.style.GreenTheme)
                        .setLogo(R.drawable.firebase_auth_120dp)
                        .setProviders(getSelectedProviders())
                        .setTosUrl("https://firebase.google.com/terms/")
                        .setIsSmartLockEnabled(true)
                        .setAllowNewEmailAccounts(true)
                        .build(),
                RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            handleSignInResponse(resultCode, data);
            return;
        }

        showSnackbar(R.string.unknown_response);
    }


    @MainThread
    private void handleSignInResponse(int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);

        // Successfully signed in
        if (resultCode == ResultCodes.OK) {
            startActivity(SignedInActivity.createIntent(this, response));
            finish();
            return;
        } else {
            // Sign in failed
            if (response == null) {
                // User pressed back button
                showSnackbar(R.string.sign_in_cancelled);
                return;
            }

            if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                showSnackbar(R.string.no_internet_connection);
                return;
            }

            if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                showSnackbar(R.string.unknown_error);
                return;
            }
        }

        showSnackbar(R.string.unknown_sign_in_response);
    }
    @MainThread
    private void showSnackbar(@StringRes int errorMessageRes) {
        Snackbar.make(mRootView, errorMessageRes, Snackbar.LENGTH_LONG).show();
    }
    private List<AuthUI.IdpConfig> getSelectedProviders() {
        List<AuthUI.IdpConfig> selectedProviders = new ArrayList<>();
        selectedProviders.add(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());

        return selectedProviders;
    }
    private static class ActivityChooserAdapter extends RecyclerView.Adapter<ActivityStarterHolder> {
        private static final Class[] CLASSES = new Class[]{
                ChatActivity.class,
                AuthUiActivity.class,
                ImageActivity.class,
        };

        private static final int[] DESCRIPTION_NAMES = new int[]{
                R.string.name_chat,
                R.string.name_auth_ui,
                R.string.name_image
        };

        private static final int[] DESCRIPTION_IDS = new int[]{
                R.string.desc_chat,
                R.string.desc_auth_ui,
                R.string.desc_image
        };

        @Override
        public ActivityStarterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ActivityStarterHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.activity_chooser_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ActivityStarterHolder holder, int position) {
            holder.bind(CLASSES[position], DESCRIPTION_NAMES[position], DESCRIPTION_IDS[position]);
        }

        @Override
        public int getItemCount() {
            return CLASSES.length;
        }
    }

    private static class ActivityStarterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle;
        private TextView mDescription;

        private Class mStarterClass;

        public ActivityStarterHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.text1);
            mDescription = (TextView) itemView.findViewById(R.id.text2);
        }
        private void bind(Class aClass, @StringRes int name, @StringRes int description) {
            mStarterClass = aClass;

            mTitle.setText(name);
            mDescription.setText(description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("New activity", itemView.getContext().toString());
            itemView.getContext().startActivity(new Intent(itemView.getContext(), mStarterClass));
        }
    }
}
