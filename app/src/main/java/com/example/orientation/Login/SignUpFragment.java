package com.example.orientation.Login;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.transition.Fade;
import androidx.transition.TransitionInflater;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class SignUpFragment extends Fragment {
    EditText email, password;
    String mailid, pass;
    FirebaseAuth firebaseAuth;
    Button signup;
    TextView signin;
    CardView cardView;

    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        email = (EditText) v.findViewById(R.id.email);
        password = (EditText) v.findViewById(R.id.password);
        signup = (Button) v.findViewById(R.id.signup);
        signin = (TextView) v.findViewById(R.id.signin);
        cardView = (CardView) v.findViewById(R.id.signcard);
        firebaseAuth = FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(INPUT_METHOD_SERVICE);
                assert inputMethodManager != null;
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
                signupuser();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signinset();
            }
        });
        return v;
    }

    private void signupuser() {
        if (TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(password.getText()))
            Toast.makeText(getContext(), "Enter All Fields", Toast.LENGTH_SHORT).show();
        else {
            mailid = email.getText().toString();
            pass = password.getText().toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Signing Up...");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            if (isValid(mailid) && pass.length() >= 6) {
                firebaseAuth.createUserWithEmailAndPassword(mailid, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isComplete()) {
                                    Toast.makeText(getContext(), "Signed Up", Toast.LENGTH_SHORT).show();
                                    signinset();
                                }
                                else
                                    Toast.makeText(getContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else if (!isValid(mailid))
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

    @SuppressLint("NewApi")
    private void signinset(){
        SignInFragment fragment = new SignInFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_rightin, R.animator.card_flip_rightout,
                        R.animator.card_flip_leftin, R.animator.card_flip_leftout)
                .replace(R.id.conta,fragment)
                .addToBackStack(null)
                .commit();
    }
}
