package stayabode.foodyHive.adapters.chefs;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.models.Reviews;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Object> objectList = new ArrayList<>();
    Typeface fontBold;
    Typeface fontRegular;

    public ReviewsAdapter(Context context, List<Object> objectList, Typeface fontBold, Typeface fontRegular) {
        this.context = context;
        this.objectList = objectList;
        this.fontBold = fontBold;
        this.fontRegular = fontRegular;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_bottom_list_item, parent, false);
        return new ReviewItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReviewItemViewHolder reviewItemViewHolder = (ReviewItemViewHolder) holder;
        reviewItemViewHolder.nameHeader.setTypeface(fontBold);
        reviewItemViewHolder.description.setTypeface(fontRegular);
        Reviews reviews = (Reviews)objectList.get(position);
        reviewItemViewHolder.nameHeader.setText(reviews.getUserName());
        reviewItemViewHolder.description.setText(reviews.getReviewsDescription());
        Glide.with(context).load(reviews.getImage()).placeholder(R.drawable.foodi_logo_left_image).into(reviewItemViewHolder.imageView);
        reviewItemViewHolder.ratingBar.setRating(Float.parseFloat(reviews.getRatingCount()));
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }


    public class ReviewItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameHeader;
        TextView description;
        RatingBar ratingBar;

        public ReviewItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nameHeader = itemView.findViewById(R.id.nameHeader);
            description = itemView.findViewById(R.id.contentHeader);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
