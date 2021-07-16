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
import com.yaga909.contestyard.R;
import Organizer.View_Participant_Info_Activity;

import java.util.List;

import Organizer.Participant_List;
import de.hdodenhof.circleimageview.CircleImageView;

public class ParticipantsRequest_Adapter extends RecyclerView.Adapter<ParticipantsRequest_Adapter.ParticipantRequest_CustomViewHolder> {

    Context context;
    List<Participant_List> participant_list;

    public ParticipantsRequest_Adapter(Context context, List<Participant_List> participant_list) {
        this.context = context;
        this.participant_list = participant_list;
    }

    @Override
    public ParticipantRequest_CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_org_accept, parent, false);
        return new ParticipantRequest_CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantsRequest_Adapter.ParticipantRequest_CustomViewHolder holder, int position) {
        String url = "http://projecttech.xyz/contest_yard/comp_banners/";
        Participant_List list = participant_list.get(position);
        holder.name.setText(list.getComp_name());
        Glide.with(context).load(url + list.getComp_image()).into(holder.image);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(context, View_Participant_Info_Activity.class);
               intent.putExtra("image_name", list.getComp_image());
               intent.putExtra("comp_name", list.getComp_name());
               intent.putExtra("username", list.getUsername());
               intent.putExtra("institution", list.getInstitution());
               intent.putExtra("email", list.getEmail());
               intent.putExtra("un_code", list.getUn_number());
               context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return participant_list.size();
    }

    class ParticipantRequest_CustomViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView name, view;

        public ParticipantRequest_CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.comp_image);
            name = itemView.findViewById(R.id.comp_name);
            view = itemView.findViewById(R.id.viewBTN);


        }
    }

}
