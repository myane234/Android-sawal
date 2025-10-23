package com.example.blog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button btnLogin;

    TextView SignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SignUp = findViewById(R.id.txtSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPass);

        //klik
        SignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        //klik login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ApiHelper helper = new ApiHelper(  "http://10.0.2.2:5000",  "GET",  null);
                    JSONArray arr = new JSONArray(helper.execute().get());
                    if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                        Toast.makeText( LoginActivity.this, "Usernamae / Password harus diisi", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    boolean valid = false;
                    for (int i =0; i <arr.length(); i++){
                        if (arr.getJSONObject(i).getString( "username").equals(username.getText().toString()) && arr.getJSONObject(i).getString("Password").equals(password.getText().toString())){
                            valid = true;
                        }
                        if (valid){

                        }else {
                            Toast.makeText( LoginActivity.this,  "Username / Password salah", Toast.LENGTH_SHORT).show();
                        }
                    }


                }catch (ExecutionException e){
                        throw new ExecutionException(e)
                }catch (JSONException e){
                    throw new JSONException(e)
                }catch (RuntimeException e){
                    throw new RuntimeException(e)
                }catch (InterruptedException e){
                    throw new InterruptedException(e)
                }

            }
        });
    }
}