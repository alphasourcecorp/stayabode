package stayabode.foodyHive.adapters.consumers;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyView> {

    private List<String> list;

    public class MyView extends RecyclerView.ViewHolder {

       // public TextView textView;
        public de.hdodenhof.circleimageview.CircleImageView  imageViewc;

        public MyView(View view) {
            super(view);

            imageViewc = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.profile_image);

        }
    }


    public RecyclerViewAdapter(List<String> horizontalList) {
        this.list = horizontalList;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expand_list_img_item, parent, false);

        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {

        //holder.textView.setText(list.get(position));

        Glide.with(holder.itemView.getContext()).load(list.get(position)).placeholder(R.drawable.foodi_logo_left_image).into(holder.imageViewc);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}