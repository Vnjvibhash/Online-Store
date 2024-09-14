package in.innovateria.onlinestore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import in.innovateria.onlinestore.Activities.CartActivity;
import in.innovateria.onlinestore.Models.ProductModel;
import in.innovateria.onlinestore.R;
import in.innovateria.onlinestore.Utils.CartManager;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<ProductModel> cartItems;
    private CartManager cartManager;
    private CartActivity cartActivity;

    public CartAdapter(Context context, List<ProductModel> cartItems, CartManager cartManager, CartActivity cartActivity) {
        this.context = context;
        this.cartItems = cartItems;
        this.cartManager = cartManager;
        this.cartActivity = cartActivity;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_view, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        ProductModel product = cartItems.get(position);

        // Load product image
        Glide.with(context)
                .load(product.getPicUrl().get(0))
                .into(holder.productImg);

        // Set the title and price
        holder.titleText.setText(product.getTitle());
        holder.itemPrice.setText(String.format("$%d", product.getPrice()));

        // Set initial number of items and total price
        final int[] quantity = {product.getQuantity()};
        holder.numberItemText.setText(String.valueOf(quantity[0]));
        holder.totalPrice.setText(String.format("$%d", product.getPrice() * quantity[0]));

        holder.minusCartBtn.setOnClickListener(v -> {
            if (quantity[0] > 1) {
                quantity[0]--;
                product.setQuantity(quantity[0]);
                holder.numberItemText.setText(String.valueOf(quantity[0]));
                holder.totalPrice.setText(String.format("$%d", product.getPrice() * quantity[0]));

                // Update cart manager
                cartManager.updateCart(product);
                cartActivity.updateTotals();
            }else {
                // Remove the item from the cart
                cartItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItems.size());
                // Update cart manager
                cartManager.removeFromCart(product);
                cartActivity.updateTotals();
            }
        });

        // Handle plus button click
        holder.plusCartBtn.setOnClickListener(v -> {
            quantity[0]++;
            product.setQuantity(quantity[0]);
            holder.numberItemText.setText(String.valueOf(quantity[0]));
            holder.totalPrice.setText(String.format("$%d", product.getPrice() * quantity[0]));

            // Update cart manager
            cartManager.updateCart(product);
            cartActivity.updateTotals();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    // ViewHolder class for Cart
    static class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImg;
        private TextView titleText, itemPrice, totalPrice;
        private TextView numberItemText, minusCartBtn, plusCartBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImg = itemView.findViewById(R.id.productImg);
            titleText = itemView.findViewById(R.id.titleText);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            numberItemText = itemView.findViewById(R.id.numberItemText);
            minusCartBtn = itemView.findViewById(R.id.minusCartBtn);
            plusCartBtn = itemView.findViewById(R.id.plusCartBtn);
        }
    }
}


