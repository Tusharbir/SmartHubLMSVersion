package cseb.tech.smarthublms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class AdminHomePage extends AppCompatActivity {
    private FirebaseFirestore db;

    private Toolbar appbar;
    private TextView welcomeAdminName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
        appbar=findViewById(R.id.AppBar);

        db=FirebaseFirestore.getInstance();

        setSupportActionBar(appbar);
        welcomeAdminName=findViewById(R.id.welcomeAdminText);

        String username = getIntent().getStringExtra("USER_NAME");
//        welcomeAdminName.setText(username);
        fetchNameFromFirestore(username);
    }

    private void fetchNameFromFirestore(String username) {
        db.collection("users")
                .whereEqualTo("username", username) // Assuming the field is named "username"
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("Name");
                            welcomeAdminName.setText("WELCOME "+name.toUpperCase());
                            Log.i("username",name);
                        }
                    } else {
                        Log.d("NextActivity", "Error getting documents: ", task.getException());
                    }
                });

    }

}