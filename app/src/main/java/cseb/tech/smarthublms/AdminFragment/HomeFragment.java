package cseb.tech.smarthublms.AdminFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.protobuf.Option;

import cseb.tech.smarthublms.Adapters.AdminHomeOptionsAdapter;
import cseb.tech.smarthublms.LogoutLogic;
import cseb.tech.smarthublms.Models.AdminHomeItemsModel;





import java.util.Arrays;
import java.util.List;

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

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        greetingText = view.findViewById(R.id.greetingtext);
        greetingText.setText(GreetingLogic.getGreeting());

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewOptions);

        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(LayoutManager);

        List<AdminHomeItemsModel> options = Arrays.asList(
                new AdminHomeItemsModel(R.drawable.course,"View Courses", AddCourseFragment.class),
                new AdminHomeItemsModel(R.drawable.hods,"Manage Hod", AddHodsFragment.class),
                new AdminHomeItemsModel(R.drawable.hods,"Manage Hod", LogoutLogic.class),
                new AdminHomeItemsModel(R.drawable.course,"View Courses", AddCourseFragment.class),
                new AdminHomeItemsModel(R.drawable.hods,"Manage Hod", AddHodsFragment.class)
                //... add all other options
        );

        AdminHomeOptionsAdapter adapter = new AdminHomeOptionsAdapter(options, getActivity().getSupportFragmentManager());
        recyclerView.setAdapter(adapter);







        return view;
    }
}