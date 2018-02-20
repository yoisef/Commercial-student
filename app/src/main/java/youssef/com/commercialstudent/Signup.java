package youssef.com.commercialstudent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URL;

public class Signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    ProgressBar progressBar,proimagee;
    TextView uploadphoto;
    ImageView profileimage;
    private static final int image=101;

    EditText emailedit,passwordedit,Retypingpass,Fname,Lname;
    Button signupp;
    Uri uriprofileimage;
     String downloadedurl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        emailedit=(EditText)findViewById(R.id.useremail);
        passwordedit=(EditText)findViewById(R.id.password);
        Retypingpass=(EditText)findViewById(R.id.retypingpassword);
        signupp=(Button)findViewById(R.id.regist);
        mAuth=FirebaseAuth.getInstance();
        progressBar=(ProgressBar)findViewById(R.id.probar);
        Fname=(EditText)findViewById(R.id.Firstname);
        Lname=(EditText)findViewById(R.id.Lastname);
        uploadphoto=(TextView)findViewById(R.id.addphoto);
        profileimage=(ImageView)findViewById(R.id.profileimage);
        proimagee=(ProgressBar)findViewById(R.id.proimage);


        signupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveuserinfo();
                registeruser();


            }
        });

        uploadphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                Intent chooser=Intent.createChooser(intent,"choose Image");
                startActivityForResult(chooser,image);
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==image&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
            uriprofileimage=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uriprofileimage);

                profileimage.setBackground(null);
                profileimage.setImageBitmap(bitmap);

                uploadimagetofirebase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadimagetofirebase() {

        StorageReference profileimageref= FirebaseStorage.getInstance().getReference("profilepics/"+System.currentTimeMillis()+".jpg");
        if (uriprofileimage!=null)
        {
            proimagee.setVisibility(View.VISIBLE);
            profileimageref.putFile(uriprofileimage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            proimagee.setVisibility(View.GONE);
                            @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            downloadedurl=downloadUrl.toString();
                            //downloadedurl= taskSnapshot.getDownloadUrl().toString();
                            Toast.makeText(Signup.this,"photo updated",Toast.LENGTH_LONG).show();


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            proimagee.setVisibility(View.GONE);
                            Toast.makeText(Signup.this,e.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    });

        }
    }

   public void saveuserinfo()
    {
       String username= Fname.getText().toString()+ Lname.getText().toString();
        if (username.isEmpty())
        {
            Fname.setError("name required");
            Fname.requestFocus();
            return;
        }
        FirebaseUser user=mAuth.getCurrentUser();
        if (user!=null&&downloadedurl!=null)
        {
            UserProfileChangeRequest profile=new UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .setPhotoUri(Uri.parse(downloadedurl))
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful())
                           {
                               Toast.makeText(Signup.this,"profile updated",Toast.LENGTH_LONG).show();

                           }
                           if(!task.isSuccessful())
                           {
                               Toast.makeText(Signup.this,"profile not updated",Toast.LENGTH_LONG).show();


                           }
                        }

                    });
        }

    }

    private  void registeruser()
    {
        String email=emailedit.getText().toString();
        String passwordd=passwordedit.getText().toString().trim();
        String retpingpass=Retypingpass.getText().toString().trim();
        String firstname=Fname.getText().toString();
        String lastnamee=Lname.getText().toString();
        if (firstname.isEmpty())
        {
            Fname.setError("First name is required");
            Fname.requestFocus();
            return;
        }
        if(lastnamee.isEmpty())
        {
            Lname.setError("Last name is required");
            Lname.requestFocus();
            return;
        }
        if (email.isEmpty())
        {
            emailedit.setError("email is required");
            emailedit.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailedit.setError("please Enter a valid email");
            emailedit.requestFocus();
            return;
        }
        if (passwordd.isEmpty())
        {
            passwordedit.setError("Password is required");
            passwordedit.requestFocus();
            return;
        }
        if (passwordd.length()<6)
        {
            passwordedit.setError("Minimum Length of password should be 6");
            passwordedit.requestFocus();
            return;
        }
        if (retpingpass.isEmpty())
        {
            Retypingpass.setError("Password is required");
            Retypingpass.requestFocus();
            return;
        }
        if (retpingpass.length()<6) {
            Retypingpass.setError("Minimum Length of password should be 6");
            Retypingpass.requestFocus();
            return;
        }

        if (!retpingpass.equals(passwordd))
        {
            Retypingpass.setError("Not match password");
            Retypingpass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        signupp.setClickable(false);
        signupp.setEnabled(false);
        signupp.setBackgroundColor(Color.WHITE);
        mAuth.createUserWithEmailAndPassword(email,passwordd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        signupp.setClickable(true);
                        signupp.setEnabled(true);
                        signupp.setBackgroundColor(Color.BLACK);
                        if (task.isSuccessful()) {
                            Toast.makeText(Signup.this,"Successful registration",Toast.LENGTH_LONG).show();
                            finish();
                            Intent o=new Intent(Signup.this,Main2Activity.class);
                            o.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(o);
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
