package cseb.tech.smarthublms.AdminFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cseb.tech.smarthublms.OtpGenerator;
import cseb.tech.smarthublms.R;


//public class AddHodsFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public AddHodsFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AddHodsFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AddHodsFragment newInstance(String param1, String param2) {
//        AddHodsFragment fragment = new AddHodsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_add_hods, container, false);
//    }
//}
public class AddHodsFragment extends Fragment {

    private Spinner branchSpinner, courseSpinner;
    private FirebaseFirestore db;

    private EditText name, fName, mName, emailId, phoneNumber, state, city, address, pinCode;
    private Button adminaddHodButton;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_hods, container, false);

        db=FirebaseFirestore.getInstance();

        courseSpinner = view.findViewById(R.id.adminAddHODSpinner);
        branchSpinner = view.findViewById(R.id.adminAddHodDepartment);

        name=view.findViewById(R.id.adminAddHodET);
        fName=view.findViewById(R.id.adminAddHodFathersNameET);
        mName=view.findViewById(R.id.adminAddHodMothersNameET);
        emailId=view.findViewById(R.id.adminAddHodemailIDET);
        phoneNumber=view.findViewById(R.id.adminAddHODPhoneEt);
        state=view.findViewById(R.id.adminAddHodState);
        city=view.findViewById(R.id.adminAddHodCity);
        address = view.findViewById(R.id.adminAddHodAddressET);
        pinCode=view.findViewById(R.id.adminAddHodPinCodeET);
        adminaddHodButton=view.findViewById(R.id.adminaddHodButton);

        fetchBranchesAndCourses();

        adminaddHodButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                addData();
            }
        });






        return view;
    }

    private void fetchBranchesAndCourses() {
        db.collection("Course")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> branches = new ArrayList<>();
                        List<String> courses = new ArrayList<>();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String branch = document.getString("Branch");
                            String course = document.getString("Course");

                            if (branch != null && !branches.contains(branch)) {
                                branches.add("No Selection");
                                branches.add(branch);
                            }

                            if (course != null && !courses.contains(course)) {
                                courses.add("No Selection");
                                courses.add(course);
                            }
                        }

                        setupSpinner(branchSpinner, branches);
                        setupSpinner(courseSpinner, courses);
                    } else {
                        // Handle the error
                        Toast.makeText(getActivity(), "Unexpected Error Occured", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setupSpinner(Spinner spinner, List<String> data) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    private void addData(){

        String nameS, fNameS, mNameS, emailIdS, phoneNumberS, stateS, cityS, addressS, pinCodeS, courseS, branchS;
        nameS=name.getText().toString();
        fNameS=fName.getText().toString();
        mNameS=mName.getText().toString();
        emailIdS=emailId.getText().toString();
        phoneNumberS=phoneNumber.getText().toString();
        stateS=state.getText().toString();
        addressS=address.getText().toString();
        pinCodeS=pinCode.getText().toString();
        cityS=city.getText().toString();

        branchS=branchSpinner.getSelectedItem().toString();
        courseS=courseSpinner.getSelectedItem().toString();

        if (!nameS.isEmpty() &&
                !fNameS.isEmpty() &&
                !mNameS.isEmpty() &&
                !emailIdS.isEmpty() &&
                !phoneNumberS.isEmpty() &&
                !stateS.isEmpty() &&
                !cityS.isEmpty() &&
                !addressS.isEmpty() &&
                !pinCodeS.isEmpty() &&
                !courseS.equals("No Selection") &&
                !branchS.equals("No Selection"))
        {
            db.collection("HODS")
                    .whereEqualTo("Branch", branchS)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                Toast.makeText(getActivity(), "HOD Already Assigned! Try Deleting", Toast.LENGTH_SHORT).show();
                            } else {
                                // Branch is not assigned, proceed to save the details
                                Map<String, Object> hod = new HashMap<>();
                                hod.put("Name", nameS);
                                hod.put("Father's Name", fNameS);
                                hod.put("Mother's Name", mNameS);
                                hod.put("Email-ID", emailIdS);
                                hod.put("Phone Number", phoneNumberS);
                                hod.put("State", stateS);
                                hod.put("City", cityS);
                                hod.put("Address", addressS);
                                hod.put("Pin Code", pinCodeS);
                                hod.put("Course", courseS);
                                hod.put("Branch", branchS);

                                db.collection("HODS")
                                        .add(hod)
                                        .addOnSuccessListener(documentReference -> {
                                            // Handle success, e.g., show a success message or clear the fields
                                            OtpGenerator obj = new OtpGenerator();
                                            String otp = obj.OTPGenerator();


                                            Toast.makeText(getActivity(), otp, Toast.LENGTH_SHORT).show();
                                            String subject="Enrollment and Credentials as HOD";
                                            String context= "Dear "+nameS+"! \nNow enrolled as HOD for Department"+branchS+".\nLogin with this number and OTP: "+otp+"/Please Change your password after Login";

//                                            EmailSender.emailSender(emailIdS,subject,context);

                                        })
                                        .addOnFailureListener(e -> {
                                            // Handle the error
                                            Log.i("data addition", e.toString());
                                            Toast.makeText(getActivity(), "Error Occured", Toast.LENGTH_SHORT).show();
                                        });
                            }
                        }


                    });
        }

        else {
            Toast.makeText(getActivity(), "Empty Field Exists!", Toast.LENGTH_SHORT).show();
        }
    }
}










