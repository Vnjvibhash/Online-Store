package in.innovateria.onlinestore.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import in.innovateria.onlinestore.Activities.ProductActivity;
import in.innovateria.onlinestore.Models.CategoryModel;
import in.innovateria.onlinestore.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<CategoryModel> categoryList;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public CategoryAdapter(Context context, List<CategoryModel> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_view, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModel category = categoryList.get(position);
        holder.categoryName.setText(category.getTitle());

        Glide.with(context)
                .load(category.getPicUrl())
                .apply(new RequestOptions().transform(new CenterInside()))
                .into(holder.categoryImage);

        // Set background based on whether the item is selected
        if (selectedPosition == position) {
            holder.categoryImage.setBackgroundResource(0);
            holder.mainLayout.setBackgroundResource(R.drawable.button_bg);
            holder.categoryName.setVisibility(View.VISIBLE);
            holder.categoryName.setTextColor(
                    ContextCompat.getColor(
                            holder.categoryName.getContext(),
                            R.color.white
                    )
            );
            ImageViewCompat.setImageTintList(
                    holder.categoryImage,
                    ColorStateList.valueOf(ContextCompat.getColor(
                            holder.categoryImage.getContext(),
                            R.color.white
                    ))
            );
        } else {
            holder.categoryImage.setBackgroundResource(R.drawable.grey_bg);
            holder.mainLayout.setBackgroundResource(0);
            holder.categoryName.setVisibility(View.GONE);
            holder.categoryName.setTextColor(
                    ContextCompat.getColor(
                            holder.categoryName.getContext(),
                            R.color.black
                    )
            );
            ImageViewCompat.setImageTintList(
                    holder.categoryImage,
                    ColorStateList.valueOf(ContextCompat.getColor(
                            holder.categoryImage.getContext(),
                            R.color.black
                    ))
            );
        }

        // Handle item click
        holder.categoryImage.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, ProductActivity.class);
                    intent.putExtra("categoryID",category.getId());
                    intent.putExtra("categoryTitle",category.getTitle());
                    context.startActivity(intent);
                }
            }, 500);
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryName;
        LinearLayout mainLayout;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.picImage);
            categoryName = itemView.findViewById(R.id.titleText);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}

