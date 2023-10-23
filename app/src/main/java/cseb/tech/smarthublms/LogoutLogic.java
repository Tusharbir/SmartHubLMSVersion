package cseb.tech.smarthublms;

import android.app.Activity;
import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class LogoutLogic {



    public static void logoutLogic(Activity activity){

        FirebaseAuth mAuth =FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear all back stack
        activity.startActivity(intent);
        activity.finish();

    }
}
