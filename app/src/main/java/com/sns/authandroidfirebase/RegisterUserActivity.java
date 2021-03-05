package com.sns.authandroidfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText etName;
    private EditText etAge;
    private EditText etEmailAddress;
    private EditText etPassword;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        registerButton = findViewById(R.id.registerButton);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etEmailAddress = findViewById(R.id.etEmailAddress);
        etPassword = findViewById(R.id.etPassword);

    }

    public void onClickRegisterUser(View view) {

        Log.d("TAG", "50 public void onClickRegisterUser(View view) { ");

        final String name = etName.getText().toString();
        final String age = etAge.getText().toString();
        final String email = etEmailAddress.getText().toString();
        String password = etPassword.getText().toString();

        if (name.isEmpty()
                || age.isEmpty()
                || email.isEmpty()
                || password.isEmpty()
        ) {
            etName.setError("名前などを入力してください");
            etName.requestFocus();
            return;
        }

        // メールアドレスの型にハマっているか？
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmailAddress.setError("名前などを入力してください");
            etEmailAddress.requestFocus();
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // 成功
                        if (task.isSuccessful()) {
                            User user = new User(name, age, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterUserActivity.this,"ユーザー登録完了",Toast.LENGTH_LONG).show();
                                    } else {

                                        Log.d("Log" ,"上 ユーザー登録に失敗しました" );
                                        Toast.makeText(RegisterUserActivity.this,"ユーザー登録に失敗しました",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {

                            Log.d("Log" ,"下 ユーザー登録に失敗しました" );

                            Toast.makeText(RegisterUserActivity.this,"ユーザー登録に失敗しました",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}