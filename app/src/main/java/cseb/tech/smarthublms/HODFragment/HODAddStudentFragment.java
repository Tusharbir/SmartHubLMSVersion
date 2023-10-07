package cseb.tech.smarthublms.HODFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cseb.tech.smarthublms.R;
import cseb.tech.smarthublms.OtpGenerator;
import cseb.tech.smarthublms.R;
import cseb.tech.smarthublms.SMTPMailSender;


public class HODAddStudentFragment extends Fragment {


    public HODAddStudentFragment() {
        // Required empty public constructor
    }

    Button signup;
    EditText email  , name , roll , mobile,branch,section,sbatch,ebatch,group;




    FirebaseAuth mAuth;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_hod_add_student, container, false);

        mAuth = FirebaseAuth.getInstance();
        email = view.findViewById(R.id.signup_email);
//        pass = view.findViewById(R.id.signup_password);
        name = view.findViewById(R.id.signup_name);
        roll = view.findViewById(R.id.signup_rollno);
        mobile = view.findViewById(R.id.signup_mobile);
//        branch = view.findViewById(R.id.signup_branch);
        section = view.findViewById(R.id.signup_leet);
//        sbatch = view.findViewById(R.id.signup_sbatch);
        signup = view.findViewById(R.id.ssignup_btn);
        group = view.findViewById(R.id.signup_group);
//        ebatch  = view.findViewById(R.id.signup_ebatch);
        //Leet Checkbox
        CheckBox checkBoxLeet = view.findViewById(R.id.checkbox_leet);
////        Spinner
//        Spinner sbatchSpinner = view.findViewById(R.id.spinner_sbatch);
//        ArrayList<String> years = new ArrayList<>();
//        for (int i = 2020; i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
//            years.add(String.valueOf(i));
//        }

//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, years);
//        sbatchSpinner.setAdapter(spinnerAdapter);



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {



                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String currentUserUID = mAuth.getCurrentUser().getUid();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("HODS").document(currentUserUID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {


                                // Now you have the branch of the currently logged-in HOD.

                                String emails ,names , rolls , mobiles, branchs , leets, batchs , groups , ebatchs;
                                emails = email.getText().toString();
                                branchs = document.getString("Branch");
                                names = name.getText().toString();
                                rolls = roll.getText().toString();
                                mobiles = mobile.getText().toString();
                                boolean isLeet = checkBoxLeet.isChecked();
                                String leetStatus = isLeet ? "Yes" : "No";

                                //Checking thee leet status and setting the batch start and end
                                batchs = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

                                int endYear;
                                if(isLeet) {
                                    endYear = Integer.parseInt(batchs) + 3;
                                } else {
                                    endYear = Integer.parseInt(batchs) + 4;
                                }
                                ebatchs = String.valueOf(endYear);


                                groups = group.getText().toString();


                                // If you're calling a method after getting branchs, you'd have to call it here, inside this callback.
                                signup(emails,names,rolls,mobiles,branchs,leetStatus,batchs, ebatchs ,groups);

                            } else {
                                Toast.makeText(getActivity(), "Record already exists!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error fetching document", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return  view;

    }

    public  void signup(String email ,String names,String rolls , String mobiles, String branches   ,String sections ,String batchs  ,String batchse ,String fgroups )
    {


        Map<String,String> v = new HashMap<>();


        v.put("Email", email);
        v.put("Name", names);
        v.put("Rollno", rolls);
        v.put("Phone", mobiles);
        v.put("Branch", branches);
        v.put("Section", sections);
        v.put("Batchs", batchs);
        v.put("Batche", batchse);
        v.put("Type", "Student");
        v.put("Group", fgroups);

        // Generating a simple OTP. You can improve upon this method.
        String nameS1 = names;
        String branchS1 = branches;
        String otp1 = String.format("%06d", (int) (Math.random() * 1000000));
        String type ="a Student";
        String emailIdS = email;

        String subject = "Enrollment and Credentials as HOD";
        String context = "Dear " + nameS1 + "! \nNow enrolled as "+ type + "for Department " + branchS1 + "\nLogin with this number and OTP: " + otp1 + ". Please Change your password after Login";

        SMTPMailSender.smtpMailSender(emailIdS, subject, nameS1, branchS1, otp1,type);
        Toast.makeText(getActivity(), "Email Sent", Toast.LENGTH_SHORT).show();




        mAuth.createUserWithEmailAndPassword(email,otp1)
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
                                            db.collection("Users").document(uid).set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        // Data added successfully
                                                        Toast.makeText(getActivity(), "Type Created", Toast.LENGTH_SHORT).show();


                                                        db.collection("Student").document(uid).set(v).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {

                                                                    Toast.makeText(getActivity(), "User registered successfully!", Toast.LENGTH_SHORT).show();

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