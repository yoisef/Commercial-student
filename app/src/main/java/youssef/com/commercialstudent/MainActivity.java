package youssef.com.commercialstudent;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView title;
    TextView title2;
    TextView signup;
    EditText user,pass;
    Button go;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title=(TextView) findViewById(R.id.titlee);
        title2=(TextView)findViewById(R.id.student) ;
        signup=(TextView)findViewById(R.id.signupp);
        user=(EditText)findViewById(R.id.getuser) ;
        pass=(EditText)findViewById(R.id.getpass);
        go=(Button)findViewById(R.id.goo) ;
        progressBar=(ProgressBar)findViewById(R.id.probar2);
        mAuth=FirebaseAuth.getInstance();
        Typeface typeface=Typeface.createFromAsset(getAssets(), "Fonts/ss.ttf");
        title.setTypeface(typeface);
        title2.setTypeface(typeface);

        go.setEnabled(true);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginuser();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent u=new Intent(MainActivity.this,Signup.class);
                startActivity(u);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,Main2Activity.class));
        }

    }

    private void loginuser()
    {
        String email=user.getText().toString();
        String passwordd=pass.getText().toString();
        if (email.isEmpty())
        {
            user.setError("Username is required");
            user.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
           user.setError("please Enter a valid email");
            user.requestFocus();
            return;
        }
        if (passwordd.isEmpty())
        {
           pass.setError("Password is required");
            pass.requestFocus();
            return;
        }
        if (passwordd.length()<6)
        {
            pass.setError("Minimum Length of password should be 6");
            pass.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,passwordd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful())
                        {
                            finish();
                            Intent i=new Intent(MainActivity.this,Main2Activity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);

                        } else {

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
                }


}
