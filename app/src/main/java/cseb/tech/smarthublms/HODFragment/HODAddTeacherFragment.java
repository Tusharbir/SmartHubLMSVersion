package cseb.tech.smarthublms.HODFragment;

import android.annotation.SuppressLint;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import cseb.tech.smarthublms.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HODAddTeacherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//public class HODAddTeacherFragment extends Fragment {
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
//    public HODAddTeacherFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment HODAddTeacherFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static HODAddTeacherFragment newInstance(String param1, String param2) {
//        HODAddTeacherFragment fragment = new HODAddTeacherFragment();
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
//        return inflater.inflate(R.layout.fragment_hod_add_teacher, container, false);
//    }
//}


public class HODAddTeacherFragment extends Fragment{



    FirebaseFirestore firestore;

    private EditText name,fName,mName, emailId,phoneNumber,state,city,pinCode;
    Button btn;


    String  sname,sfName,smName, semailId,sphoneNumber,sstate,scity,spinCode;

    
    
    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        
        View view = inflater.inflate(R.layout.fragment_hod_add_teacher, container, false);

        firestore = FirebaseFirestore.getInstance();


        name = view.findViewById(R.id.hodAddTeacherNameET);//hodAddTeacherNameET
        fName = view.findViewById(R.id.hodAddTeacherFNameET);
        mName = view.findViewById(R.id.hodAddTeacherMNameET);
        emailId = view.findViewById(R.id.hodAddTeacherEmailET);
        phoneNumber = view.findViewById(R.id.hodAddTeacherPhNameET);
        state = view.findViewById(R.id.hodAddTeacherStateET);
        city = view.findViewById(R.id.hodAddTeacherCityET);
        pinCode = view.findViewById(R.id.hodAddTeacherPinCodeET);
        btn = view.findViewById(R.id.buttonts);








        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sname = name.getText().toString();
                sfName = fName.getText().toString();
                smName= mName.getText().toString();
                semailId= emailId.getText().toString();
                sphoneNumber = state.getText().toString();
                sstate = state.getText().toString();
                scity  = city.getText().toString();
                spinCode   = pinCode.getText().toString();
                HashMap<String,String> v = new HashMap<>();
                v.put("Type","Teacher");
                FirebaseAuth mauth;
                mauth = FirebaseAuth.getInstance();

                mauth.createUserWithEmailAndPassword(semailId,"1234567").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            String a = mauth.getUid();
                          //  Toast.makeText(getActivity(), ""+a+"s", Toast.LENGTH_SHORT).show();



                            firestore.collection("Users").document(mauth.getUid()).set(v).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {


                                    HashMap<String,String> u = new HashMap<>();
                                    u.put("Name",sname);
                                    u.put("Fname",sfName);
                                    u.put("Mname",smName);
                                    u.put("Email",semailId);
                                    u.put("Pnumber",sphoneNumber);
                                    u.put("State",sstate);
                                    u.put("City",scity);
                                    u.put("Pincode",spinCode);
                                    u.put("uid",mauth.getUid());
                                    u.put("Status","Active");

                                    firestore.collection("Teacher").document(mauth.getUid()).set(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            Toast.makeText(getActivity(), "Teacher Created", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }


                    }
                });


            }
        });


        return view;

    }
    
    
}