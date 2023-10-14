package cseb.tech.smarthublms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import cseb.tech.smarthublms.AdminFragment.AddBranchesFragment;
import cseb.tech.smarthublms.AdminFragment.AddCourseFragment;
import cseb.tech.smarthublms.AdminFragment.AddHodsFragment;
import cseb.tech.smarthublms.AdminFragment.HomeFragment;



public class AdminHomePage extends AppCompatActivity {
    private FirebaseFirestore db;

    //    private Toolbar appbar;
    private TextView welcomeAdminName;


    private Toolbar appbar;

    private String Frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
        appbar=findViewById(R.id.AppBar);
//        String username = getIntent().getStringExtra("USER_NAME");
//        db = FirebaseFirestore.getInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new HomeFragment())
                .commit();


        BottomNavigationView bottomNavigation;
        bottomNavigation = findViewById(R.id.bottom_navigation);


        Frag = "home";


        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.home) {
                    if (Frag != "home") {
                        replacefragment(new HomeFragment());
                        Frag = "home";
                    } else {
                        Toast.makeText(AdminHomePage.this, "khulya va pra", Toast.LENGTH_SHORT).show();
                    }
                    //  frag(homeFragment);
                    return true;

                } else if (itemId == R.id.addCourse) {
                    if (Frag != "student") {
                        replacefragment(new AddCourseFragment());
                        Frag = "student";
                    } else {
                        Toast.makeText(AdminHomePage.this, "khulya va pra", Toast.LENGTH_SHORT).show();
                    }
                    // frag(searchFragment);
                    return true;

                }
                else if (itemId == R.id.addHods) {
                    if (Frag != "abc") {
                        replacefragment(new AddHodsFragment());
                        Frag = "abc";
                    } else {
                        Toast.makeText(AdminHomePage.this, "khulya va pra", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }

                else if (itemId==R.id.logout) {

                    LogoutLogic.logoutLogic( AdminHomePage.this);


                }

                return false;
            }
        });

    }
    public void replacefragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}