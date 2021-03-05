package com.sns.authandroidfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText mEmailEt, mPasswordEt;
    private Button mRegisterButton, mLoginButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mEmailEt = findViewById(R.id.emailEt);
        mPasswordEt = findViewById(R.id.passwordEt);
        mRegisterButton = findViewById(R.id.toRegister);
        mLoginButton = findViewById(R.id.loginButton);
    }

    public void onClickLoginButton(View view) {
        loginUser();
    }

    /**
     * ログインボタン押下時の処理
     */
    private void loginUser() {
        // パスワード、メールが空の時の処理を書く
        // https://www.youtube.com/watch?v=KB2BIm_m1Os&list=PL65Ccv9j4eZJ_bg0TlmxA7ZNbS8IMyl5i&index=3

        String email = mEmailEt.getText().toString();
        String password = mPasswordEt.getText().toString();

        Log.d("Log" ,"email =" +email +
                " password =" +password
        );

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(MainActivity.this,"ログインしました = " + task.isSuccessful(),Toast.LENGTH_LONG).show();

                    Log.d("Log" ," task.isSuccessful() =" +task.isSuccessful() ); /
                    Intent intent = new Intent(MainActivity.this, profileActivity.class);
                    MainActivity.this.startActivity(intent);

                } else {
                    Log.d("Log" ," ログイン失敗" +task.isSuccessful() ); // OK
                    Toast.makeText(MainActivity.this,"ログインに失敗しました" ,Toast.LENGTH_LONG).show();
                }


            }
        });
    }


    /**
     * 登録画面へ押下時の処理
     */
    public void onClickToRegister(View view) {
        Log.d("Log" ," 登録画面へ押下時の処理 " );
    }
}