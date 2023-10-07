package cseb.tech.smarthublms.HODFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cseb.tech.smarthublms.R;


public class HODAddSubject extends Fragment {
    View view;
    Button subject_add;
    EditText subname , subteach , subsem  , sub_sbatch,sub_branch,sub_section,sub_code,sub_link,sub_Month,sub_ebatch;

    CheckBox monday,tues,wed,thus,fri;


    ArrayList list = new ArrayList();
    FirebaseAuth mAuth;
    String a ;
    Map<String, Object> v = new HashMap<>();

    ArrayAdapter<String> sadapter;
    String []types = {"Lecture","Tute","Lab"};
    String type;
    Spinner spinner;

    public HODAddSubject(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hod_add_subject, container, false);
        subname = view.findViewById(R.id.subname_name);
        subteach = view.findViewById(R.id.sub_teach_id);
        subsem = view.findViewById(R.id.sub_sem);
        sub_sbatch = view.findViewById(R.id.sub_sbatch);
        sub_branch = view.findViewById(R.id.sub_branch);
        sub_section = view.findViewById(R.id.sub_section);
        sub_code =  view.findViewById(R.id.sub_code);
        sub_link =  view.findViewById(R.id.sub_Year);
        sub_Month =  view.findViewById(R.id.sub_Month);
        subject_add = view.findViewById(R.id.sub_add);
        sub_ebatch  = view.findViewById(R.id.sub_ebatch);
        monday = view.findViewById(R.id.checkBox1);
        tues = view.findViewById(R.id.checkBox2);
        wed = view.findViewById(R.id.checkBox3);
        thus = view.findViewById(R.id.checkBox4);
        fri = view.findViewById(R.id.checkBox5);
        spinner = view.findViewById(R.id.select_subject_spinner);


        sadapter = new ArrayAdapter<String>(getActivity(), android.R.layout
                .simple_spinner_dropdown_item,types);
        spinner.setAdapter(sadapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                type = (String) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });






        subject_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                String subnamef , subteachf ,subsemf , sub_sbatchf , sub_ebatchf , sub_branchf, sub_sectionf,sub_codef,sublinkf,submonth;
                subnamef = subname.getText().toString();
                subteachf = subteach.getText().toString();
                subsemf = subsem.getText().toString();
                sub_sbatchf = sub_sbatch.getText().toString();
                sub_branchf = sub_branch.getText().toString();
                sub_sectionf = sub_section.getText().toString();
                sub_codef = sub_code.getText().toString();
                sublinkf =  sub_link.getText().toString();
                submonth =  sub_Month.getText().toString();
                sub_ebatchf =  sub_ebatch.getText().toString();

                if (monday.isChecked())
                {
                    list.add("Monday");

                }

                if (tues.isChecked())
                {
                    list.add("Tuesday");

                }
                if (wed.isChecked())
                {
                    list.add("Wednesday");

                }
                if (thus.isChecked())
                {
                    list.add("Thursday");

                }
                if (fri.isChecked())
                {
                    list.add("Friday");

                }


                //  System.out.println(list);







                subadd(subnamef,subteachf,subsemf,sub_sbatchf,sub_ebatchf,sub_branchf,sub_sectionf,sub_codef,sublinkf,submonth);

            }
        });

    return  view;
    }

    public  void subadd(String fsubname , String fsubteach,String fsubsem,String fsub_sbatch , String fsub_ebatch, String fsub_branch, String fsub_group, String fsub_code,String fsub_link,String fsub_Month)
    {

        int intyear = Calendar.getInstance().get(Calendar.YEAR);

        String year1 = Integer.toString(intyear);


        v.put("Subject",fsubname);
        v.put("Subject_Code",fsub_code);
        v.put("Teach_id",fsubteach);
        v.put("Semester",fsubsem);
        v.put("Batchs",fsub_sbatch);
        v.put("Batche",fsub_ebatch);
        v.put("Branch",fsub_branch);
        v.put("Section",fsub_group);
        v.put("Year",year1);
        v.put("Month",fsub_Month);
        v.put("Link",fsub_link);
        v.put("Type",type);
        v.put("Day",list);

        TimeTableFragment timeTable_fragment = new TimeTableFragment(v);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerHOD,timeTable_fragment).commit();






//     FirebaseFirestore.getInstance().collection("Subject").document(fsub_ebatch+fsubname + fsub_group ).set(v).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task)
//                                {
//                              //      Toast.makeText(getActivity(), "Gya data firestore de vich", Toast.LENGTH_SHORT).show();
//
//                                }
//                            });



    }

}