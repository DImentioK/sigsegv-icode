package com.firebase.uidemo.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.firebase.uidemo.R;

public class sound extends AppCompatActivity {

    Switch sound;

    boolean fireBaseSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        sound = (Switch) findViewById(R.id.soundSwitch);

        preset();

        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sound.isChecked() != fireBaseSound) {
                    //todo update firebase
                    fireBaseSound = sound.isChecked();
                }
                updated();
            }
        });
    }

    public void updated() {
        Toast toast = Toast.makeText(this, R.string.soundToast, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void preset() {
        //todo get firebase info
    }
}
