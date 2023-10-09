package cseb.tech.smarthublms.StudentFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import cseb.tech.smarthublms.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubjectFragment extends Fragment {


    public SubjectFragment() {
        // Required empty public constructor
    }

   FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_subject, container, false);


        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        firestore.collection("Student").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task)
            {
                if (task.getResult().exists())
                {
                    Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
                    String batche = task.getResult().getString("Batche");
                    String Group = task.getResult().getString("Group");
                    String Section = task.getResult().getString("Section");

                    Log.d("indd", "onComplete: "+batche+Group+Section);

                    firestore.collection("Subject")
                            .whereEqualTo("Batche",batche)

                            .whereEqualTo("Section",Section).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                                {
                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                    for (DocumentSnapshot d :list)
                                    {
                                        Toast.makeText(getContext(), "vhjjhj", Toast.LENGTH_SHORT).show();


                                    }
                                }
                            });



                }
            }
        });


        return view;

    }
}