package cseb.tech.smarthublms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import cseb.tech.smarthublms.AdminFragment.HomeFragment;

public class StudentActivity extends AppCompatActivity {

    String Frag;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Toast.makeText(this, "Student", Toast.LENGTH_SHORT).show();

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, new HomeFragment()).commit();
        BottomNavigationView bottomNavigations;


        bottomNavigations = findViewById(R.id.bottom_navigation_s);

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, new HomeFragment()).commit();

        Frag = "Subject";



        bottomNavigations.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int itemId = item.getItemId();


                if (itemId == R.id.subject_s)
                {
                    if (Frag != "Subject")
                    {
                        // replacefragment(new HomeFragment());
                        Frag = "Subject";
                    }
                    else {
                        Toast.makeText(StudentActivity.this, "khulya va pra", Toast.LENGTH_SHORT).show();
                    }
                    //  frag(homeFragment);
                    return true;


                } else if (itemId == R.id.campusVibess) {
                    if (Frag != "CampusVibe")
                    {
                        //   replacefragment(new HomeFragment());
                        Frag = "CampusVibe";
                    }
                    else {
                        Toast.makeText(StudentActivity.this, "khulya va pra", Toast.LENGTH_SHORT).show();
                    }
                    // frag(searchFragment);
                    return true;


                } else if (itemId == R.id.notification_s) {
                    if (Frag != "Notification")
                    {
                        //   replacefragment(new HomeFragment());
                        Frag = "Notification";
                    }
                    else {
                        Toast.makeText(StudentActivity.this, "khulya va pra", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }

                else if (itemId == R.id.profile_s) {
                    if (Frag != "Profile")
                    {
                        //   replacefragment(new HomeFragment());
                        Frag = "Profile";
                    }
                    else {
                        Toast.makeText(StudentActivity.this, "khulya va pra", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }else if (itemId == R.id.abc) {
                    if (Frag != "abc")
                    {
                        //   replacefragment(new HomeFragment());
                        Frag = "abc";
                    }
                    else {
                        Toast.makeText(StudentActivity.this, "khulya va pra", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }


                return false;




            }

        });


    }

    void  replacefragment(Fragment fragment)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containers,  fragment)
                .commit();
    }
}