package cseb.tech.smarthublms.Adapters;

import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import java.util.*;

import cseb.tech.smarthublms.Models.AdminHomeItemsModel;

import cseb.tech.smarthublms.R;

public class AdminHomeOptionsAdapter extends RecyclerView.Adapter<AdminHomeOptionsAdapter.ViewHolder> {
    private List<AdminHomeItemsModel> optionsList;
    private FragmentManager fragmentManager;


    public AdminHomeOptionsAdapter(List<AdminHomeItemsModel> optionsList, FragmentManager fragmentManager) {
        this.optionsList = optionsList;
        this.fragmentManager = fragmentManager;
    }


    @NonNull
    @Override
    public AdminHomeOptionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_home_dashboard_options, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AdminHomeItemsModel option = optionsList.get(position);
        holder.optionTitle.setText(option.getTitle());
        holder.optionIcon.setImageResource(option.getIconResId());

        holder.itemView.setOnClickListener(v -> {
            try {
                Fragment fragment = (Fragment) option.getFragmentClass().newInstance();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return optionsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView optionIcon;
        TextView optionTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            optionIcon = itemView.findViewById(R.id.option_icon);
            optionTitle = itemView.findViewById(R.id.option_title);
        }
        }
    }

