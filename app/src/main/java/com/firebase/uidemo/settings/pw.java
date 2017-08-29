package com.firebase.uidemo.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.uidemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class pw extends AppCompatActivity {

    EditText oldPW, newPW;

    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw);

        oldPW = (EditText) findViewById(R.id.old);
        newPW = (EditText) findViewById(R.id.newpw);

        error = (TextView) findViewById(R.id.error);
    }

    public void change(View v) {


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), oldPW.getText().toString());

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPW.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("pw", "Password updated");
                                        error.setVisibility(View.INVISIBLE);
                                        password_success();

                                        oldPW = newPW;
                                        newPW.setText("");
                                    } else {
                                        Log.d("pw", "Error password not updated");
                                    }
                                }
                            });
                        } else {
                            error.setText(getText(R.string.mismatchError).toString());
                            error.setVisibility(View.VISIBLE);
                            Log.d("pw", "Error auth failed");
                        }
                    }
                });

    }

    private void password_success() {
        Toast toast = Toast.makeText(this, R.string.pwSet, Toast.LENGTH_SHORT);
        toast.show();
    }
}
