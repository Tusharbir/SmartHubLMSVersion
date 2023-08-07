package cseb.tech.smarthublms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class AdminHomePage extends AppCompatActivity {

    private Toolbar appbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
        appbar=findViewById(R.id.AppBar);
        setSupportActionBar(appbar);
    }
}