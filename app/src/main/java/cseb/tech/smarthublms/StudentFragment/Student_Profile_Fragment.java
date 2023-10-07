package cseb.tech.smarthublms.StudentFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import cseb.tech.smarthublms.R;


public class Student_Profile_Fragment extends Fragment {

    // Firestore instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    // UI elements
    private TextView tvBatchStart,tvBatchEnd, tvBranch, tvEmail, tvGroup, tvName, tvPassword, tvPhoneNumber, tvRoll, tvSection, tvType;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student__profile_, container, false);

        // Initialize views
        tvBatchEnd = view.findViewById(R.id.tv_batch_end);
        tvBatchStart = view.findViewById(R.id.tv_batch_start);
        tvBranch = view.findViewById(R.id.tv_branch);
        tvEmail = view.findViewById(R.id.tv_email);
        tvGroup = view.findViewById(R.id.tv_group);
        tvName = view.findViewById(R.id.tv_name);
        tvPassword = view.findViewById(R.id.tv_password);
        tvPhoneNumber = view.findViewById(R.id.tv_phonenumber);
        tvRoll = view.findViewById(R.id.tv_roll);
        tvSection = view.findViewById(R.id.tv_section);
        tvType = view.findViewById(R.id.tv_type);
        fetchStudentDetails();
        Log.i("PEhla AA chlya","Chlpya");

        return view;
    }

    private void fetchStudentDetails() {
        String userId = mAuth.getCurrentUser().getUid();
        Log.i("fetch Details Chlya", userId);


        // Get the current logged-in user ID
        Log.i("user iud", userId);

        db.collection("Student").document(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Map<String, Object> data = document.getData();

                    tvBatchStart.setText(data.get("Batchs").toString());
                    tvBatchEnd.setText("Batch End: " + data.get("Batche").toString());
                    tvBranch.setText("Branch: " + data.get("Branch").toString());
                    tvEmail.setText("Email: " + data.get("Email").toString());
                    tvGroup.setText("Group: " + data.get("Group").toString());
                    tvName.setText("Name: " + data.get("Name").toString());
                    tvPassword.setText("Password: " + data.get("Password").toString()); // Remember the note on security!
                    tvPhoneNumber.setText("Phone Number: " + data.get("Phone").toString());
                    tvRoll.setText("Roll: " + data.get("Rollno").toString());
                    tvSection.setText("Section: " + data.get("Section").toString());
                    tvType.setText("Type: " + data.get("Type").toString());

                } else {
                    Toast.makeText(getContext(), "No such document", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Failed to fetch details.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}






