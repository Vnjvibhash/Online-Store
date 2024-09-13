package in.innovateria.onlinestore.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;

import in.innovateria.onlinestore.Activities.ProductDetailActivity;
import in.innovateria.onlinestore.Models.ProductModel;
import in.innovateria.onlinestore.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<ProductModel> productList;

    public ProductAdapter(Context context, List<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModel product = productList.get(position);

        Glide.with(context)
                .load(product.getPicUrl().get(0))
                .apply(new RequestOptions().placeholder(R.drawable.grey_bg))
                .into(holder.productImage);

        holder.titleText.setText(product.getTitle());
        holder.rating.setText(String.valueOf(product.getRating()));
        holder.priceText.setText(String.format("$%d", product.getPrice()));

        holder.favBtn.setOnClickListener(v -> {
            // TODO :Implement favorite button click logic here
        });

        holder.productImage.setOnClickListener(v->{
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("productObject", product);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        ImageView favBtn;
        TextView titleText;
        ImageView star;
        TextView rating;
        TextView priceText;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            favBtn = itemView.findViewById(R.id.favBtn);
            titleText = itemView.findViewById(R.id.titleText);
            star = itemView.findViewById(R.id.star);
            rating = itemView.findViewById(R.id.rating);
            priceText = itemView.findViewById(R.id.textView3);
        }
    }
}

