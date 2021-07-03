package Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yaga909.contestyard.R;

import java.util.List;

import Organizer.OrgProfile_List;
import Organizer.Organizer_Newsfeed;
import Organizer.Result_ORG;
import Organizer.Update_Post_ORG;

public class Org_Profile_Adapter extends RecyclerView.Adapter<Org_Profile_Adapter.Org_Profile_CustomViewHolder> {
    Context context;
    List<OrgProfile_List> org_list;
    Organizer_Newsfeed organizer_newsfeed = new Organizer_Newsfeed();

    public Org_Profile_Adapter(Context context, List<OrgProfile_List> org_list) {
        this.context = context;
        this.org_list = org_list;
    }

    @NonNull
    @Override
    public Org_Profile_CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_view_org_newsfeed, parent, false);
        return new Org_Profile_CustomViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull Org_Profile_CustomViewHolder holder, int position) {
        String url = "http://projecttech.xyz/contest_yard/comp_banners/";
        OrgProfile_List orgProfile_list = org_list.get(position);
        holder.Post_Name.setText(orgProfile_list.getPost_name());
        holder.Post_Last_date.setText(orgProfile_list.getPost_last_date());
        holder.Post_Fees.setText(orgProfile_list.getPost_fees());
        Glide.with(context).load(url + orgProfile_list.getPost_banner()).into(holder.Post_Banner);

        holder.Post_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                organizer_newsfeed.deletePost(context);

            }
        });

        holder.Post_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             context.startActivity(new Intent(context, Update_Post_ORG.class));
            }
        });

        holder.Result_Publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Result_ORG.class));
            }
        });

    }


    @Override
    public int getItemCount() {
        return org_list.size();
    }

    class Org_Profile_CustomViewHolder extends RecyclerView.ViewHolder {
        TextView Post_Name, Post_Last_date, Post_Fees;
        ImageButton Post_Delete, Post_Update, Result_Publish;
        ImageView Post_Banner;

        public Org_Profile_CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            Post_Name = itemView.findViewById(R.id.orgPostNameTV);
            Post_Last_date = itemView.findViewById(R.id.orgLastDateTV);
            Post_Fees = itemView.findViewById(R.id.orgFeesTV);
            Post_Banner = itemView.findViewById(R.id.contestBannerIV);
            Post_Delete = itemView.findViewById(R.id.dltPostORGBTN);
            Post_Update = itemView.findViewById(R.id.updateBTN);
            Result_Publish = itemView.findViewById(R.id.publishBTN);
        }
    }

}
