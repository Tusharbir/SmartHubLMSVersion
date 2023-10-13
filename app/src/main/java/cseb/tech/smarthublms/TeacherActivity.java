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
import cseb.tech.smarthublms.TeacherFragment.Teacher_Profile_Fragment;

public class TeacherActivity extends AppCompatActivity {

    String Frag;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);



        BottomNavigationView bottomNavigations;

        bottomNavigations = findViewById(R.id.bottom_navigation_t);

        getSupportFragmentManager().beginTransaction().replace(R.id.containert, new HomeFragment()).commit();


        Frag = "Subject";
        Toast.makeText(this, "login", Toast.LENGTH_SHORT).show();


        bottomNavigations.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int itemId = item.getItemId();


                if (itemId == R.id.subject_t)
                {
                    if (Frag != "Subject")
                    {
                        replacefragment( new HomeFragment());
                        Frag = "Subject";
                    }
                    else {
                        Toast.makeText(TeacherActivity.this, "khulya va pra", Toast.LENGTH_SHORT).show();
                    }
                    //  frag(homeFragment);
                    return true;


                } else if (itemId == R.id.campusVibes) {
                    if (Frag != "CampusVibe")
                    {
                        //   replacefragment(new HomeFragment());
                        Frag = "CampusVibe";
                    }
                    else {
                        Toast.makeText(TeacherActivity.this, "khulya va pra", Toast.LENGTH_SHORT).show();
                    }
                    // frag(searchFragment);
                    return true;


                } else if (itemId == R.id.notification_t) {
                    if (Frag != "Notification")
                    {
                        //   replacefragment(new HomeFragment());
                        Frag = "Notification";
                    }
                    else {
                        Toast.makeText(TeacherActivity.this, "khulya va pra", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }

                else if (itemId == R.id.profile) {
                    if (Frag != "Profile")
                    {
                           replacefragment(new Teacher_Profile_Fragment());
                        Frag = "Profile";
                    }
                    else {
                        Toast.makeText(TeacherActivity.this, "khulya va pra", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }

                else if (itemId==R.id.logout_t) {

                    LogoutLogic.logoutLogic( TeacherActivity.this);
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
                .replace(R.id.containert,  fragment)
                .commit();
    }


}