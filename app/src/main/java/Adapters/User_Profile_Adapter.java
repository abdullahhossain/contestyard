package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yaga909.contestyard.R;
import USER.User_Profile_List;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Profile_Adapter extends RecyclerView.Adapter<User_Profile_Adapter.User_Profile_CustomAdapter> {
    Context context;
    List<User_Profile_List> user_list;
  //  RecyclerViewClickListener listener;



    public User_Profile_Adapter(Context context, List<User_Profile_List> user_list ) {
        this.context = context;
        this.user_list = user_list;

    }


    @NonNull
    @Override
    public User_Profile_CustomAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_view_newsfeed,parent, false);
        return new User_Profile_CustomAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull User_Profile_CustomAdapter holder, int position) {
        String url = "http://projecttech.xyz/contest_yard/org_upload/";
        String url2 = "http://projecttech.xyz/contest_yard/comp_banners/";
        User_Profile_List user_profile_list = user_list.get(position);
        holder.org_name.setText(user_profile_list.getOrg_name());
        holder.location.setText(user_profile_list.getLocation());
        Glide.with(context).load(url+user_profile_list.getOrg_logo_name()).into(holder.org_logo);
        holder.comp_name.setText(user_profile_list.getComp_name());
        holder.comp_date.setText(user_profile_list.getComp_date());
        holder.comp_fees.setText(user_profile_list.getComp_fees());
        Glide.with(context).load(url2+user_profile_list.getComp_banner_name()).into(holder.comp_banner);
        holder.applyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Applied", Toast.LENGTH_SHORT).show();
            }
        });
        
        holder.detailsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Details!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return user_list.size();
    }



    class User_Profile_CustomAdapter extends RecyclerView.ViewHolder{
        TextView org_name, location, comp_name, comp_date, comp_fees;
        ImageView comp_banner;
        CircleImageView org_logo;
        ImageButton detailsBTN, applyBTN;


        public User_Profile_CustomAdapter(@NonNull View itemView) {
            super(itemView);

            org_name = itemView.findViewById(R.id.organizerNameTV);
            location = itemView.findViewById(R.id.locationTV);
            org_logo = itemView.findViewById(R.id.organizerLogoCV);
            comp_name = itemView.findViewById(R.id.orgPostNameTV);
            comp_date = itemView.findViewById(R.id.orgLastDateTV);
            comp_fees = itemView.findViewById(R.id.orgFeesTV);
            comp_banner = itemView.findViewById(R.id.contestBannerIV);
            detailsBTN = itemView. findViewById(R.id.detailsBTN);
            applyBTN = itemView.findViewById(R.id.applyBTN);





        }

    }


}
