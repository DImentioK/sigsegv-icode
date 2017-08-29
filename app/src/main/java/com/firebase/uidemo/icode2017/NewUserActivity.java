package com.firebase.uidemo.icode2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.firebase.uidemo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewUserActivity extends AppCompatActivity {
    @BindView(R.id.residency_ToggleButton)
    ToggleButton toggle;
    @BindView(R.id.newUser_ContinueButton)
    Button btn;
    private String residency = "Tourist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        ButterKnife.bind(this);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    residency = "Resident";
                } else {
                    residency = "Tourist";
                }
                Log.d("NewUserActivity", residency);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setEnabled(false);
                createUser(residency);

            }
        });

    }

    private void createUser(String residency) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(uid).child("residency").setValue(residency);
        Log.d("NewUserActivity", "Created User!");
        startActivity(new Intent(NewUserActivity.this, MainActivity.class));
    }
}
