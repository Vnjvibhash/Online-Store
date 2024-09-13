package in.innovateria.onlinestore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;

import in.innovateria.onlinestore.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context context;
    private List<String> imageUrls;
    private int selectedPosition = 0;
    private ImageView productImageView;

    public ImageAdapter(Context context, List<String> imageUrls, ImageView productImageView) {
        this.context = context;
        this.imageUrls = imageUrls;
        this.productImageView = productImageView;

        if (!imageUrls.isEmpty()) {
            Glide.with(context)
                    .load(imageUrls.get(0))
                    .apply(new RequestOptions().centerInside())
                    .into(productImageView);
        }
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_view, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);

        Glide.with(context)
                .load(imageUrl)
                .apply(new RequestOptions().centerInside())
                .into(holder.productImage);

        if(selectedPosition==position){
            holder.productImage.setBackgroundResource(R.drawable.selected_bg);
        }else{
            holder.productImage.setBackgroundResource(R.drawable.grey_bg);
        }

        holder.productImage.setOnClickListener(v -> {
            Glide.with(context)
                    .load(imageUrl)
                    .apply(new RequestOptions().centerInside())
                    .into(productImageView);
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);
        });
    }

    @Override
    public int getItemCount() {
        return !imageUrls.isEmpty() ?imageUrls.size():8;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }
}


