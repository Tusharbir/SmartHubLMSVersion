package cseb.tech.smarthublms.Adapters;




import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import cseb.tech.smarthublms.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Objects;

public class Time_Table_Adapter extends RecyclerView.Adapter<Time_Table_Adapter.viewholder>
{

    public Time_Table_Adapter(ArrayList datalist ,ArrayList datalist2,ArrayList datalist3,ArrayList datalist4,String g) {
        this.datalist = datalist;
        this.q = datalist2;
        this.time = datalist3;
        this.Group = datalist4;
        this.group = g;
    }

    ArrayList datalist ,q,time,Group;
    String group;





    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(cseb.tech.smarthublms.R.layout.time_row,parent,false);

        viewholder vh = new viewholder(v,new MyCustomEditTextListener(), new Timetext(),new Grouptext());

        return (viewholder) vh;

        // return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position)
    {


        //
        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
        holder.timetext.updatePosition(holder.getAdapterPosition());
        holder.t1.setText((String) datalist.get(position));
        holder.grouptext.updatePosition(holder.getAdapterPosition());
        q.add(position, holder.editText.getText().toString());
        time.add(position, holder.editText2.getText().toString().trim());
        Group.add(position, holder.editText3.getText().toString());


        if (Objects.equals(group, "Lecture"))
        {
            holder.editText3.setVisibility(View.INVISIBLE);
            holder.editText3.setText("0");
        }







    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class viewholder extends  RecyclerView.ViewHolder

    {


        TextView t1;
        EditText editText,editText2,editText3;
        MyCustomEditTextListener myCustomEditTextListener;
        Timetext timetext;
        Grouptext grouptext;




        public viewholder(@NonNull View itemView, MyCustomEditTextListener myCustomEditTextListener,Timetext timetext,Grouptext  grouptext ) {
            super(itemView);

            t1 = itemView.findViewById(R.id.time_day);
            editText =itemView.findViewById(R.id.Roomnotime);
            editText2 =itemView.findViewById(R.id.timeclassaaa);
            editText3 =itemView.findViewById(R.id.selectgoup);



            this.timetext = timetext;
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.grouptext = grouptext;


            this.editText.addTextChangedListener(myCustomEditTextListener);
            this.editText2.addTextChangedListener(timetext);
            this.editText3.addTextChangedListener(grouptext);

        }
    }

    private  class Timetext implements TextWatcher {



        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        public Timetext() {

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            time.set(position,charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private  class Grouptext implements TextWatcher {



        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        public Grouptext() {

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            Group.set(position,charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {



            q.set(position,charSequence.toString());


        }

        @Override
        public void afterTextChanged(Editable editable)
        {
//hello
        }
    }











}
