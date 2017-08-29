package com.firebase.uidemo.icode2017;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.uidemo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileEditorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileEditorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileEditorFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @BindView(R.id.profile_name_display)
    TextView name;
    @BindView(R.id.profile_email_display)
    TextView email;
    @BindView(R.id.profile_residency_display)
    TextView residency;
    @BindView(R.id.profile_points_display)
    TextView points;
    @BindView(R.id.profile_preset_display)
    TextView preset;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        View rootView= inflater.inflate(R.layout.fragment_profile_editor, container, false);
        ButterKnife.bind(this, rootView);

        /* TODO
        1. Search database for user points/exp  AND residency
        2. display preset identifier
         */
        if (mAuth.getCurrentUser() != null) {
            Log.d("Hello", "Current user loaded");
            Log.d("Text", mAuth.getCurrentUser().getDisplayName());
            name.setText(mAuth.getCurrentUser().getDisplayName());
            email.setText(mAuth.getCurrentUser().getEmail());
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String currentPreset = dataSnapshot.child("users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("preset").child("category").getValue().toString();
                        currentPreset = currentPreset.replaceFirst("_", " + ");
                        String d = dataSnapshot.child("users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("preset").child("distance").getValue().toString();
                        if (d.equals("50")) d = "∞";
                        currentPreset = currentPreset.concat(" | " + d + " km");
                        String r = dataSnapshot.child("users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("residency").getValue().toString();

                        preset.setText(currentPreset);
                        residency.setText(r);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        return rootView;

    }
}
