package com.firebase.uidemo.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.firebase.uidemo.R;

public class notif extends AppCompatActivity {

    Switch notif;
    ///TODO notif in firebase
    boolean fireBaseNotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        notif = (Switch) findViewById(R.id.notifSwitch);


        notif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (notif.isChecked() != fireBaseNotif) {
                    //todo update firebase
                    fireBaseNotif = notif.isChecked();
                }

                updated();
            }
        });
    }

    public void updated() {
        Toast toast = Toast.makeText(this, R.string.notifToast, Toast.LENGTH_SHORT);
        toast.show();
    }
}
