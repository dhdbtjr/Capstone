package com.example.alban.capstone_camgal;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.content.ContentValues.TAG;

public class LoginActivity extends FragmentActivity{

    private static final int RESOLVE_CONNECTION_REQUEST_CODE=1;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(LoginActivity.this)
                .enableAutoManage(LoginActivity.this, new GoogleApiClient.OnConnectionFailedListener(){
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
                    {
                        //연결 실패시 실행되는 메소드
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        Button googleLoginButton = (Button) findViewById(R.id.button_googlelogin);
        googleLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //구글로그인 화면 출력. 화면이 닫힌 후 onActivityResult실행
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RESOLVE_CONNECTION_REQUEST_CODE);
            }
        });
        }

        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
            switch(requestCode){
                case RESOLVE_CONNECTION_REQUEST_CODE:
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

                    if(result.isSuccess()){
                        GoogleSignInAccount acct = result.getSignInAccount();
                        //계정 정보 얻어오기
                        Log.i(TAG, acct.getDisplayName());
                        Log.i(TAG, acct.getEmail());
                    }
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
            }
    }
}
