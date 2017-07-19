package com.patriot.ur254.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.patriot.ur254.R;
import com.patriot.ur254.activities.TimelineActivity;
import com.patriot.ur254.utils.SharedPreference;
import com.patriot.ur254.utils.UniversalUtils;
import com.patriot.ur254.views.Btn;
import com.patriot.ur254.views.Edt;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    private Btn btnLogin;
    private Edt editTextNationalID, editTextPassword;
    private FirebaseAuth firebaseAuth;
    private UniversalUtils universalUtils;
    private SharedPreference sharedPreference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        universalUtils = new UniversalUtils(getActivity());
        sharedPreference = new SharedPreference(getActivity());
        editTextNationalID = (Edt) view.findViewById(R.id.editTextNationalID);
        editTextPassword = (Edt) view.findViewById(R.id.editTextPassword);
        btnLogin = (Btn) view.findViewById(R.id.buttonLogin);

        /** Firebase variables **/
        firebaseAuth = FirebaseAuth.getInstance();

        SetListeners();

        return view;
    }

    private void SetListeners() {
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                if (TextUtils.isEmpty(editTextNationalID.getText().toString())) {
                    editTextNationalID.setError("National ID is required!");
                } else if (TextUtils.isEmpty(editTextPassword.getText().toString())) {
                    editTextPassword.setError("Password is required!");
                } else {
                    SignIn(editTextNationalID.getText().toString() + "@gmail.com", editTextPassword.getText().toString());
                }
                break;
            default:
                break;
        }
    }

    private void SignIn(String national_id, String password) {
        universalUtils.ShowProgressDialog("Signing In...");

        firebaseAuth.signInWithEmailAndPassword(national_id, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        String name = user.getDisplayName();
                        sharedPreference.SaveUserName(name);
                        sharedPreference.putIsLoggedIn(true);
                        if (user.getPhotoUrl() != null) {
                            sharedPreference.SavePhotoUrl(String.valueOf(user.getPhotoUrl()));
                        } else {
                            sharedPreference.SavePhotoUrl("");
                        }

                        Intent intentTimeline = new Intent(getActivity(), TimelineActivity.class);
                        startActivity(intentTimeline);
                        getActivity().finish();

                        universalUtils.DismissProgressDialog();

                    }

                }
            }

        }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                universalUtils.DismissProgressDialog();
                universalUtils.ShowErrorDialog(e.getMessage());
            }
        });

    }

}
