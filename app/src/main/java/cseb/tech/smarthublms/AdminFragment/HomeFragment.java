package cseb.tech.smarthublms.AdminFragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import cseb.tech.smarthublms.Adapters.AdminHomeOptionsAdapter;
import cseb.tech.smarthublms.LogoutLogic;
import cseb.tech.smarthublms.Models.AdminHomeItemsModel;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import cseb.tech.smarthublms.GreetingLogic;
import cseb.tech.smarthublms.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//public class HomeFragment extends Fragment {
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
//    public HomeFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment HomeFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static HomeFragment newInstance(String param1, String param2) {
//        HomeFragment fragment = new HomeFragment();
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
//        return inflater.inflate(R.layout.fragment_home, container, false);
//    }
//}


public class HomeFragment extends Fragment{

    private TextView greetingText;
    private final long[] totalStudents = new long[1];
    private final long[] totalTeachers = new long[1];
    private final long[] totalHODs = new long[1];

    private ProgressBar progressBar;

    PieChart pieChart;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        pieChart = view.findViewById(R.id.pieChart);
        progressBar= view.findViewById(R.id.progressBar2);


        greetingText = view.findViewById(R.id.greetingtext);
        greetingText.setText(GreetingLogic.getGreeting());

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewOptions);

        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(LayoutManager);

        List<AdminHomeItemsModel> options = Arrays.asList(
                new AdminHomeItemsModel(R.drawable.course2,"Manage Courses", AddCourseFragment.class),
                new AdminHomeItemsModel(R.drawable.hods,"Manage Hods", AddHodsFragment.class),
                new AdminHomeItemsModel(R.drawable.teacher,"Manage Teachers", LogoutLogic.class),
                new AdminHomeItemsModel(R.drawable.student2,"Mange Students", AddCourseFragment.class),
                new AdminHomeItemsModel(R.drawable.userlist,"Users List", AddHodsFragment.class)
                //... add all other options
        );

        AdminHomeOptionsAdapter adapter = new AdminHomeOptionsAdapter(options, getActivity().getSupportFragmentManager());
        recyclerView.setAdapter(adapter);

        setupPieChartAdminUserCount();

        return view;
    }

    public void fetchvaluesCount() {

        progressBar.setVisibility(View.VISIBLE);

        AtomicInteger completedFetches = new AtomicInteger(0);

        db.collection("Teacher").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                totalTeachers[0] = task.getResult().size();
            }
            if (completedFetches.incrementAndGet() == 3) {
                setupPieChartAdminUserCount();
            }
        });

        db.collection("Student").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                totalStudents[0] = task.getResult().size();
            }
            if (completedFetches.incrementAndGet() == 3) {
                setupPieChartAdminUserCount();
            }
        });

        db.collection("HODS").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                totalHODs[0] = task.getResult().size();
            }
            if (completedFetches.incrementAndGet() == 3) {
                setupPieChartAdminUserCount();
            }
        });
    }


    public void setupPieChartAdminUserCount(){
        progressBar.setVisibility(View.VISIBLE);


        fetchvaluesCount();

        int studentValue = (int) totalStudents[0];
        int teacherValue = (int) totalTeachers[0];
        int HODValue = (int) totalHODs[0];

        Log.i("Teacher Valyue", String.valueOf(teacherValue));
        Log.i("Student Valyue", String.valueOf(studentValue));
        Log.i("HODS Valyue", String.valueOf(HODValue));

        int[] chartColors = {
                Color.parseColor("#0074a3"),         // Students
                Color.parseColor("#008fc9"),        // Teachers
                Color.parseColor("#8cd5ff")  // HODs - Light Purple
        };


        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(studentValue, "Students"));
        entries.add(new PieEntry(teacherValue, "Teachers"));
        entries.add(new PieEntry(HODValue, "HODs"));

        PieDataSet dataSet = new PieDataSet(entries, "USER STATS");
        dataSet.setColors(chartColors); // You can customize colors as per your requirements

        dataSet.setValueTextSize(16f);
        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);

        pieChart.setDescription(null); // Hide the description label if you don't need it
        pieChart.setDrawHoleEnabled(false); // To remove the center hole, set to false
        pieChart.invalidate(); // Refresh the chart


        pieChart.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        progressBar.setVisibility(View.GONE);
    }
}