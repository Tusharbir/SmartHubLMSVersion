package cseb.tech.smarthublms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Toolbar appbar;

    private EditText userText, passText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appbar=findViewById(R.id.AppBar);
//        getSupportActionBar().setTitle(R.string.app_name);
        setSupportActionBar(appbar);


        userText=findViewById(R.id.userNameTv);
        passText=findViewById(R.id.passwordTv);
        mAuth=FirebaseAuth.getInstance();

    }

    public void logicButtonLogin(View view){
        String username = userText.getText().toString();
        String password = passText.getText().toString();
        loginUser(username, password);
    }

    private void loginUser(String username, String password){
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        FirebaseUser user = mAuth.getCurrentUser();
                        openNextActivity(username);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void openNextActivity(String username){
        Intent intent = new Intent(this, AdminHomePage.class);
        intent.putExtra("USER_NAME", username);
        startActivity(intent);

    }
}