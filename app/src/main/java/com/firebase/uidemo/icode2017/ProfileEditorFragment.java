package com.firebase.uidemo.icode2017;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.uidemo.R;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_profile_editor, container, false);

        /* TODO
        Implement profile options
         */

        return rootView;

    }
}
