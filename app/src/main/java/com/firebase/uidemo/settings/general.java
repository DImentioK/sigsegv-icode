package com.firebase.uidemo.settings;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.firebase.uidemo.R;

public class general extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
    }

    public void abt(View v) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("About this Application");
        alertDialog.setMessage(getText(R.string.aboutInfo).toString());

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }

    public void software(View v) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Software Version");
        alertDialog.setMessage(getText(R.string.softwareInfo).toString());

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }
}
