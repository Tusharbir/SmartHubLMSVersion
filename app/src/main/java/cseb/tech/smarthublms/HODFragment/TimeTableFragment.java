package cseb.tech.smarthublms.HODFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cseb.tech.smarthublms.Adapters.Time_Table_Adapter;
import cseb.tech.smarthublms.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimeTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeTableFragment extends Fragment {


    public TimeTableFragment() {
        // Required empty public constructor
    }

    Map<String, Object> v = new HashMap<>();

    RecyclerView recyclerView;
    Time_Table_Adapter adapter;
    Button btn;
    FirebaseAuth mAuth;


    TimeTableFragment(Map<String, Object> v )
    {
        this.v = v;
    }




    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_time_table, container, false);



        ArrayList  s = (ArrayList) v.get("Day");
        ArrayList room = new ArrayList();
        ArrayList time = new ArrayList();
        ArrayList Group = new ArrayList();



        btn = view.findViewById(R.id.sumbittime);
        recyclerView = view.findViewById(R.id.recytime);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext() ));


        String group = (String) v.get("Type");



        adapter= new Time_Table_Adapter(s,room,time,Group,group);
        recyclerView.setAdapter(adapter);



        mAuth = FirebaseAuth.getInstance();



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                // Collections.reverse(q);
                //  Collections.reverse(time);
                v.put("Room_no",room);
                // System.out.println(q);
                //System.out.println(s);
                //System.out.println(time);
                v.put("Time",time);
                v.put("Group",Group);

                String document = (String) v.get("Batche")+v.get("Subject")+v.get("Section");

                FirebaseFirestore.getInstance().collection("Subject").document(document).set(v).addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        Toast.makeText(getActivity(), "Data Send", Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });



        return  view;
    }
}