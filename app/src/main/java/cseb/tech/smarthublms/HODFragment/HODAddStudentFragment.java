package cseb.tech.smarthublms.HODFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import cseb.tech.smarthublms.R;


public class HODAddStudentFragment extends Fragment {


    public HODAddStudentFragment() {
        // Required empty public constructor
    }

    Button signup;
    EditText email , pass , name , roll , mobile,branch,section,sbatch,ebatch,group;



    FirebaseAuth mAuth;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_hod_add_student, container, false);

        mAuth = FirebaseAuth.getInstance();
        email = view.findViewById(R.id.signup_email);
        pass = view.findViewById(R.id.signup_password);
        name = view.findViewById(R.id.signup_name);
        roll = view.findViewById(R.id.signup_rollno);
        mobile = view.findViewById(R.id.signup_mobile);
        branch = view.findViewById(R.id.signup_branch);
        section = view.findViewById(R.id.signup_leet);
        sbatch = view.findViewById(R.id.signup_sbatch);
        signup = view.findViewById(R.id.ssignup_btn);
        group = view.findViewById(R.id.signup_group);
        ebatch  = view.findViewById(R.id.signup_ebatch);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String emails , passs ,names , rolls , mobiles, branchs , leets, batchs , groups , ebatchs;
                emails = email.getText().toString();
                passs = pass.getText().toString();
                names = name.getText().toString();
                rolls = roll.getText().toString();
                mobiles = mobile.getText().toString();
                branchs = branch.getText().toString();
                leets =  section.getText().toString();
                batchs = sbatch.getText().toString();
                groups = group.getText().toString();
                ebatchs =  ebatch.getText().toString();


                signup(emails,passs,names,rolls,mobiles,branchs,leets,batchs, ebatchs ,groups);

            }
        });



        return  view;

    }

    public  void signup(String email , String pass,String names,String rolls , String mobiles, String branches   ,String sections ,String batchs  ,String batchse ,String fgroups )
    {


        Map<String,String> v = new HashMap<>();

        v.put("Email",email);
        v.put("Pass",pass);
        v.put("Name",names);
        v.put("Rollno",rolls);
        v.put("Phone",mobiles);
        v.put("Branch",branches);
        v.put("Section",sections);
        v.put("Batchs",batchs);
        v.put("Batche",batchse);
        v.put("Type","Student");
        v.put("Group",fgroups);
       // v.put("Link","https://firebasestorage.googleapis.com/v0/b/agcd-5e575.appspot.com/o/Demo%2Fdp.jpg?alt=media&token=8c87322d-481e-437b-a18c-8933727ffec3");




        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final String uid = mAuth.getUid();
                            final FirebaseFirestore db = FirebaseFirestore.getInstance();

                            // Check if the user document exists in the "Student" collection
                            db.collection("Student").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            // User document exists, show a toast indicating that the user already exists
                                            Toast.makeText(getActivity(), "User already exists", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // User document doesn't exist, add data to Firestore
                                            HashMap<String, String> userData = new HashMap<>();
                                            userData.put("Type", "Student");

                                            // Add data to the "Student" collection
                                            db.collection("Student").document(uid).set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        // Data added successfully
                                                        Toast.makeText(getActivity(), "Student Data has been added", Toast.LENGTH_SHORT).show();

                                                        // Add user type data to the "Users" collection
//                                                        HashMap<String, String> userTypeData = new HashMap<>();
//                                                        userTypeData.put("Type", "Student");

                                                        db.collection("Users").document(uid).set(v).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    // User type data added successfully
                                                                    Toast.makeText(getActivity(), "Type Created", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    // Handle errors for adding user type data
                                                                    Toast.makeText(getActivity(),"An unexpected error occured ",Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        // errors
                                                        Toast.makeText(getActivity(),"An unexpected error occured",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                    } else {
                                        Toast.makeText(getActivity(),"An unexpected error occured",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }

                });



    }
}