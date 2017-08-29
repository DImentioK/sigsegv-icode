package com.firebase.uidemo.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.uidemo.R;
import com.firebase.uidemo.icode2017.Preset;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class presets extends AppCompatActivity {

    @BindView(R.id.heritage_checkBox)
    CheckBox hBox;
    @BindView(R.id.nature_checkBox)
    CheckBox nBox;
    @BindView(R.id.tourism_checkBox)
    CheckBox tBox;

    @BindView(R.id.distance_spinner)
    Spinner distanceSpinner;

    @BindView(R.id.preset_button)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presets);
        ButterKnife.bind(this);
        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("5km");
        spinnerArray.add("10km");
        spinnerArray.add("15km");
        spinnerArray.add("Any");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        distanceSpinner.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePreset();
            }
        });


    }

    private void updatePreset() {
        if (!hBox.isChecked() && !nBox.isChecked() && !tBox.isChecked()) {
            Toast toast = Toast.makeText(this, "You must select at least one location type!", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            Preset preset = new Preset(tBox.isChecked(), hBox.isChecked(), nBox.isChecked(), distanceSpinner.getSelectedItem().toString());

            Log.d("category", preset.getCategory().toString());
            mDatabase.child("users").child(uid).child("preset").child("category").setValue(preset.getCategory());
            Log.d("preset", preset.getDistance());
            mDatabase.child("users").child(uid).child("preset").child("distance").setValue(preset.getDistance());
            Log.d("presets Activity", "Wrote to database");
            Toast toast = Toast.makeText(this, "Updated Preset Successfully (View in Settings)", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
