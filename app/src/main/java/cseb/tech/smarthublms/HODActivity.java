package cseb.tech.smarthublms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import cseb.tech.smarthublms.HODFragment.HODAddStudentFragment;
import cseb.tech.smarthublms.HODFragment.HODAddSubject;
import cseb.tech.smarthublms.HODFragment.HODAddTeacherFragment;
import cseb.tech.smarthublms.HODFragment.HODAssignClassIncharge;
import cseb.tech.smarthublms.HODFragment.HodHomeFragment;

public class HODActivity extends AppCompatActivity {


    private String fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hodactivity);

        getSupportFragmentManager().beginTransaction().replace(R.id.containerHOD, new HodHomeFragment()).commit();

        BottomNavigationView bottomNavigation;
        bottomNavigation = findViewById(R.id.bottom_navigationHOD);


        fragment = "home";

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemid = item.getItemId();

                if(itemid==R.id.HODhome){
                    replaceFragment(new HodHomeFragment());
                    return true;
                } else if (itemid==R.id.addTeacher) {
                    replaceFragment(new HODAddTeacherFragment());
                    return true;
                }
                else if(itemid==R.id.addStudents){
                    replaceFragment(new HODAddStudentFragment());
                    return true;
                } else if (itemid==R.id.assignIncharge) {
                    replaceFragment(new HODAddSubject());
                    return true;
                } else if (itemid==R.id.logout) {
                    LogoutLogic.logoutLogic(HODActivity.this);
                    return true;
                }




                return false;
            }
        });




    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerHOD, fragment)
                .commit();
    }
}