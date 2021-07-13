package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yaga909.contestyard.R;

import java.util.List;

import USER.Status_List;
import de.hdodenhof.circleimageview.CircleImageView;

public class Status_Adapter extends RecyclerView.Adapter<Status_Adapter.Status_CustomViewHolder> {
    Context context;
    List<Status_List> status_list;

    public Status_Adapter(Context context, List<Status_List> status_list) {
        this.context = context;
        this.status_list = status_list;
    }

    @NonNull
    @Override
    public Status_CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_view_requests, parent, false);
        return new  Status_CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Status_Adapter.Status_CustomViewHolder holder, int position) {
        String url = "http://projecttech.xyz/contest_yard/comp_banners/";
        Status_List list = status_list.get(position);
        holder.name.setText(list.getComp_name());
        holder.status.setText(list.getComp_status());
        Glide.with(context).load(url+list.getComp_image()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return status_list.size();
    }

    class Status_CustomViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView name, status;

        public Status_CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.comp_image);
            name = itemView.findViewById(R.id.comp_name);
            status = itemView.findViewById(R.id.comp_status);

        }
    }


}
