package cseb.tech.smarthublms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Toolbar appbar;

    private EditText userText, passText;
    private FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    private TextView resetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appbar=findViewById(R.id.AppBar);
//        getSupportActionBar().setTitle(R.string.app_name);
        setSupportActionBar(appbar);
        firestore = FirebaseFirestore.getInstance();

        userText=findViewById(R.id.userNameTv);
        passText=findViewById(R.id.passwordTv);
        mAuth=FirebaseAuth.getInstance();


        resetView=findViewById(R.id.resetPassWordTV);

        if (mAuth.getCurrentUser() != null) {
            firestore.collection("Users").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.getResult().exists()) {
                        String a = task.getResult().getString("Type");
                        Toast.makeText(MainActivity.this, a + "", Toast.LENGTH_SHORT).show();

                        if (Objects.equals(a, "Admin")) {
                            Intent i = new Intent(MainActivity.this, AdminHomePage.class);
                            startActivity(i);
                            finish();
                        } else if (Objects.equals(a, "Student")) {
                            Intent i = new Intent(MainActivity.this, StudentActivity.class);
                            startActivity(i);
                            finish();
                        } else if (Objects.equals(a, "Teacher")) {
                            Intent i = new Intent(MainActivity.this, TeacherActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else if (Objects.equals(a, "HOD")) {
                            Intent i = new Intent(MainActivity.this, HODActivity.class);
                            startActivity(i);
                            finish();

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        resetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }

    public void logicButtonLogin(View view){
        String username = userText.getText().toString();
        String password = passText.getText().toString();

        if(!username.isEmpty() && !password.isEmpty()) {
            loginUser(username, password);
        }
        else {
            Toast.makeText(this, "Empty Field Exists!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginUser(String username, String password) {

            mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    firestore.collection("Users").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task)
                        {
                            if (task.getResult().exists()) {
                                String a = task.getResult().getString("Type");
                                Toast.makeText(MainActivity.this, a + "", Toast.LENGTH_SHORT).show();

                                if (Objects.equals(a, "Admin")) {
                                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                } else if (Objects.equals(a, "Student")) {
                                    Intent i = new Intent(MainActivity.this, StudentActivity.class);
                                    startActivity(i);
                                    finish();
                                } else if (Objects.equals(a, "Teacher")) {
                                    Intent i = new Intent(MainActivity.this, TeacherActivity.class);
                                    startActivity(i);
                                    finish();
                                } else if (Objects.equals(a, "HOD")) {
                                    Intent i = new Intent(MainActivity.this, HODActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(MainActivity.this, "Incorrect Details, Try Resetting Your Password", Toast.LENGTH_SHORT).show();
                }

            }
        });


        }



    public void resetPassword(){
        String emailId=userText.getText().toString();

        if(!emailId.isEmpty()){
            mAuth.sendPasswordResetEmail(emailId).addOnCompleteListener(
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }
        else {
            Toast.makeText(this, "Enter Email ID", Toast.LENGTH_SHORT).show();
        }

    }


}