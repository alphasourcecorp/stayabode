package stayabode.foodyHive.adapters.consumers;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.consumers.ConsumerMainActivity;
import stayabode.foodyHive.models.SortHeader;

import java.util.ArrayList;
import java.util.List;

public class ConsumerSortHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<SortHeader> sortHeaderList = new ArrayList<>();
    int selectedposition ;
    Boolean scrolled = false;
    RecyclerView recyclerView;
    Typeface poppinsMedium;


    public ConsumerSortHeaderAdapter(Context context, List<SortHeader> sortHeaderList,RecyclerView recyclerView,Boolean scrolled,int selectedposition,Typeface poppinsMedium)
    {
        this.context = context;
        this.sortHeaderList = sortHeaderList;
        this.recyclerView = recyclerView;
        this.scrolled = scrolled;
        this.selectedposition = selectedposition;
        this.poppinsMedium = poppinsMedium;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_header,parent,false);
        return new HeadersItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HeadersItemViewHolder headersItemViewHolder = (HeadersItemViewHolder)holder;
        headersItemViewHolder.menuName.setText(sortHeaderList.get(position).getTitle());
        headersItemViewHolder.menuName.setTypeface(poppinsMedium);

        if(selectedposition == 0)
        {
            headersItemViewHolder.menuName.setTextColor(context.getResources().getColor(R.color.colorConsumerPrimary));
            headersItemViewHolder.view.setVisibility(View.VISIBLE);
        }
        else
        {
            //#1E3854
            headersItemViewHolder.menuName.setTextColor(context.getResources().getColor(R.color.colorBlack));
            headersItemViewHolder.view.setVisibility(View.GONE);
        }

        if(selectedposition == position)
        {
            headersItemViewHolder.menuName.setTextColor(context.getResources().getColor(R.color.colorConsumerPrimary));
            headersItemViewHolder.view.setVisibility(View.VISIBLE);


        }
        else
        {
            headersItemViewHolder.menuName.setTextColor(context.getResources().getColor(R.color.colorBlack));
            headersItemViewHolder.view.setVisibility(View.GONE);
        }

        headersItemViewHolder.menuName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ConsumerMainActivity)context).enableScroll = false;
                selectedposition = holder.getAdapterPosition();
                ((LinearLayoutManager)recyclerView.getLayoutManager()).scrollToPositionWithOffset(selectedposition,0);
                notifyDataSetChanged();
                recyclerView.scrollToPosition(selectedposition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sortHeaderList.size();
    }

    public class HeadersItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView menuName;
        View view;

        public HeadersItemViewHolder(@NonNull View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.menuName);
            view = itemView.findViewById(R.id.selectedPositionView);
        }
    }

}