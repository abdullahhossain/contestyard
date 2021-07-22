package Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yaga909.contestyard.Pdf_Show_Activity;
import com.yaga909.contestyard.R;

import java.util.List;

import Organizer.Participant_List;
import Organizer.View_Participant_Info_Activity;
import USER.Show_Result_List;
import de.hdodenhof.circleimageview.CircleImageView;

public class Show_Result_Adapter extends RecyclerView.Adapter<Show_Result_Adapter.Show_Result_CustomViewHolder> {
    Context context;
    List<Show_Result_List> result_list;

    public Show_Result_Adapter(Context context, List<Show_Result_List> result_list) {
        this.context = context;
        this.result_list = result_list;
    }

    @Override
    public Show_Result_CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_view_show_result, parent, false);
        return new Show_Result_CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Show_Result_Adapter.Show_Result_CustomViewHolder holder, int position) {

        String url = "http://projecttech.xyz/contest_yard/comp_banners/";
        Show_Result_List list = result_list.get(position);
        holder.name.setText(list.getComp_name());
        Glide.with(context).load(url + list.getComp_image()).into(holder.image);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Pdf_Show_Activity.class);
                intent.putExtra("id", list.getId());
                intent.putExtra("comp_code", list.getComp_code());
                intent.putExtra("image_name", list.getComp_image());
                intent.putExtra("comp_name", list.getComp_name());
                intent.putExtra("username", list.getUsername());
                intent.putExtra("institution", list.getInstitution());
                intent.putExtra("email", list.getEmail());
                intent.putExtra("un_code", list.getUn_number());
                intent.putExtra("comp_id",list.getComp_id());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return result_list.size();
    }

    class Show_Result_CustomViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView name, view;


        public Show_Result_CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.comp_image);
            name = itemView.findViewById(R.id.comp_name);
            view = itemView.findViewById(R.id.viewBTN);

        }
    }

}
