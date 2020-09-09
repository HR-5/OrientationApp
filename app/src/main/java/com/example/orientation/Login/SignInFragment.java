package com.example.orientation.Login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionInflater;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orientation.R;
import com.example.orientation.Schedule.Sched_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class SignInFragment extends Fragment {

    EditText email, password;
    String mailid, pass;
    Button signin;
    TextView forgot, create;
    FirebaseAuth firebaseAuth;

    public SignInFragment() {
        // Required empty public constructor
    }


    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long l) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Signing In...");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                @Override
                public void onFinish() {
                    Intent i = new Intent(getContext(), Sched_Activity.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                }
            }.start();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);
        email = (EditText) v.findViewById(R.id.email);
        password = (EditText) v.findViewById(R.id.password);
        signin = (Button) v.findViewById(R.id.signup);
        forgot = (TextView) v.findViewById(R.id.forgot);
        create = (TextView) v.findViewById(R.id.create);
        firebaseAuth = FirebaseAuth.getInstance();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(INPUT_METHOD_SERVICE);
                assert inputMethodManager != null;
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
                sign();
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotpass();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createuser();
            }
        });
        return v;
    }

    private void sign() {
        if (TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(password.getText()))
            Toast.makeText(getContext(), "Enter All Fields", Toast.LENGTH_SHORT).show();
        else {
            mailid = email.getText().toString();
            pass = password.getText().toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Signing In...");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            if (isValid(mailid) && pass.length() >= 6) {
                firebaseAuth.signInWithEmailAndPassword(mailid, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isComplete())
                                {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    if(user != null) {
                                        Intent i = new Intent(getContext(), Sched_Activity.class);
                                        startActivity(i);
                                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                                    }
                                    else
                                        Toast.makeText(getContext(), "Sign In Failed", Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(getContext(), "Sign In Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else if (!isValid(mailid))
                Toast.makeText(getContext(), "Enter Valid Email Id", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), "Password should be minimum 6 characters", Toast.LENGTH_SHORT).show();
        }

    }

    private static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    private void forgotpass() {
        if (TextUtils.isEmpty(email.getText()))
            Toast.makeText(getContext(), "Enter Email Id", Toast.LENGTH_SHORT).show();
        else {
            mailid = email.getText().toString();
            firebaseAuth.sendPasswordResetEmail(mailid)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Email has been sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    private void createuser(){
        SignUpFragment fragment = new SignUpFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_rightin, R.animator.card_flip_rightout,
                        R.animator.card_flip_leftin, R.animator.card_flip_leftout)
                .replace(R.id.conta,fragment)
                .commit();
    }
}
