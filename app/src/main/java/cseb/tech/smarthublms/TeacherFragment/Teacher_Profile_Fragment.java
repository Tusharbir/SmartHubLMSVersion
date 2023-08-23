package cseb.tech.smarthublms.TeacherFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.internal.ViewUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import cseb.tech.smarthublms.R;


public class Teacher_Profile_Fragment extends Fragment {


    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_teacher__profile_, container, false);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        TextView name , city , email ,fathername , mothername, pincode , phonenumber, state , uniqueid;
        ProgressBar progressBar;
        name = view.findViewById(R.id.tname);
        city = view.findViewById(R.id.tcity);
        email = view.findViewById(R.id.temail);
        fathername = view.findViewById(R.id.tfathername);
        mothername = view.findViewById(R.id.tmothername);
        pincode = view.findViewById(R.id.tpincode);
        phonenumber = view.findViewById(R.id.tphonenumber);
        state = view.findViewById(R.id.tstate);
        uniqueid = view.findViewById(R.id.tuniqueid);
        progressBar = view.findViewById(R.id.tprogress_bar);


        name.setVisibility(View.GONE);
        city.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        fathername.setVisibility(View.GONE);
        mothername.setVisibility(View.GONE);
        pincode.setVisibility(View.GONE);
        phonenumber.setVisibility(View.GONE);
        state.setVisibility(View.GONE);
        uniqueid.setVisibility(View.GONE);



                firestore.collection("Teacher").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task)
            {

                if (task.getResult().exists())
                {
                    progressBar.setVisibility(View.GONE);
                    name.setText(task.getResult().getString("Name"));
                    email.setText(task.getResult().getString("Email"));
                    city.setText(task.getResult().getString("City"));
                    fathername.setText(task.getResult().getString("Fname"));
                    mothername.setText(task.getResult().getString("Mname"));
                    pincode.setText(task.getResult().getString("Pincode"));
                    phonenumber.setText(task.getResult().getString("Pnumber"));
                    state.setText(task.getResult().getString("State"));
                    // unique id need to set
                    uniqueid.setText(task.getResult().getString("uid"));

                    name.setVisibility(View.VISIBLE);
                    city.setVisibility(View.VISIBLE);
                    email.setVisibility(View.VISIBLE);
                    fathername.setVisibility(View.VISIBLE);
                    mothername.setVisibility(View.VISIBLE);
                    pincode.setVisibility(View.VISIBLE);
                    phonenumber.setVisibility(View.VISIBLE);
                    state.setVisibility(View.VISIBLE);
                    uniqueid.setVisibility(View.VISIBLE);
                }

            }
        });



        return  view;

    }
}