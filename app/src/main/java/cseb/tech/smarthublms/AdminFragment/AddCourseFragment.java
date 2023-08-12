package cseb.tech.smarthublms.AdminFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import cseb.tech.smarthublms.R;


//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AddCourseFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class AddCourseFragment extends Fragment {
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
//    public AddCourseFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AddCourseFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AddCourseFragment newInstance(String param1, String param2) {
//        AddCourseFragment fragment = new AddCourseFragment();
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
//        return inflater.inflate(R.layout.fragment_add_course, container, false);
//    }
//}




public class AddCourseFragment extends Fragment {

    private Spinner courseSpinner;
    private Spinner branchSpinner;
    private String course;
    private String branchname;
    private FirebaseFirestore db;
    private Button addButton;
    private String nothing;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_course, container, false);

        db=FirebaseFirestore.getInstance();

        courseSpinner=view.findViewById(R.id.courseSpinnerAdmin);
        branchSpinner=view.findViewById(R.id.branchSpinnerAdmin);

        addButton=view.findViewById(R.id.adminAddCourseButton);


//        CourseADD Lofgic
        ArrayList<String> courseNames = new ArrayList<>();
        courseNames.add("No Selection");
        courseNames.add("Bachelors of Technology(B.Tech)");
        courseNames.add("Masters of Technology(M.Tech)");
        courseNames.add("Bachelors of Computer Applications(BCA)");
        courseNames.add("Masters of Computer Applications(MSA)");




        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, courseNames);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        courseSpinner.setAdapter(adapter);

        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selected = parentView.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(), "Selected: " + selected, Toast.LENGTH_SHORT).show();
                course = selected;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });





//        Branches Logic

        ArrayList<String> branchesNames = new ArrayList<>();
        branchesNames.add("No Selection");
        branchesNames.add("Computer Science and Technology");
        branchesNames.add("Masters of Technology(M.Tech)");
        branchesNames.add("Bachelors of Computer Applications(BCA)");
        branchesNames.add("Masters of Computer Applications(MSA)");


        ArrayAdapter<String> branchAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, branchesNames);

        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        branchSpinner.setAdapter(branchAdapter);

        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selected = parentView.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(), "Selected: " + selected, Toast.LENGTH_SHORT).show();
                branchname = selected;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here


            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(branchname.equals("No Selection") || course.equals("No Selection"))
//                {
//                    Toast.makeText(getActivity(), "Make Selection", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//
//                    HashMap<String,String> v = new HashMap<>();
//                    v.put("Course",course);
//                    v.put("Branch",branchname);
//
//                    db.collection("Course").add(v).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            Toast.makeText(getActivity(), "data Added", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
                if (branchname.equals("No Selection") || course.equals("No Selection")) {
                    Toast.makeText(getActivity(), "Make Selection", Toast.LENGTH_SHORT).show();
                } else {
                    final String docId = course + "_" + branchname; // Create a unique ID based on course and branchname

                    db.collection("Course").document(docId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Toast.makeText(getActivity(), "Entry already exists", Toast.LENGTH_SHORT).show();
                                } else {
                                    HashMap<String, String> v = new HashMap<>();
                                    v.put("Course", course);
                                    v.put("Branch", branchname);

                                    db.collection("Course").document(docId).set(v).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity(), "Data Added", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Error adding data", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            } else {
                                Toast.makeText(getActivity(), "Error checking for duplicates", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });





        return view;
    }


}




