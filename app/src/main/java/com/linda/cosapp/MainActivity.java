package com.linda.cosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SignInButton btnGoogleLogin = findViewById(R.id.btn_google_login);
        Button btnGoogleLogin = findViewById(R.id.btn_google_signin);
        Button btnGoogleLogout = findViewById(R.id.btn_google_logout);


        btnGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Choose authentication providers
                List<AuthUI.IdpConfig> providers = Arrays.asList(

                        new AuthUI.IdpConfig.GoogleBuilder().build());


                // Create and launch sign-in intent
                startActivityForResult( // 여기로 뭔가 리턴 값을 받는 거임.
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        3648);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3648) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.d(TAG, "onActivityResult:로그인 완료 : " + user.getEmail());
                // 로그인 성공 그럼 로그인 됐는지 확인해서 인텐트해서 메인페이지로 이동

            } else { //로그인 실패
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                Log.d(TAG, "onActivityResult : 로그인실패 : ");
                Log.d(TAG, "onActivityResult : 에러로그 : " + response.getError());
            }
        }
    }
}