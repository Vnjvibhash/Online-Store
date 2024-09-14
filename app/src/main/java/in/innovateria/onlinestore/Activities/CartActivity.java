package in.innovateria.onlinestore.Activities;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.innovateria.onlinestore.Adapters.CartAdapter;
import in.innovateria.onlinestore.Models.OrderModel;
import in.innovateria.onlinestore.Models.ProductModel;
import in.innovateria.onlinestore.R;
import in.innovateria.onlinestore.Utils.CartManager;
import in.innovateria.onlinestore.Utils.Constant;
import in.innovateria.onlinestore.Utils.DBHelper;

public class CartActivity extends AppCompatActivity {
    private String paymentMode = "Cash";
    private ProgressBar progressBar;
    protected LinearLayout cartLayout, cashMethod,bankMethod;
    private ImageView cashMethod_ic,bankMethod_ic;
    private TextView cashMethod_txt,cashMethod_desc,bankMethod_txt,bankMethod_desc;
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private CartManager cartManager;
    private List<ProductModel> cartItems;
    public TextView emptyText, subTotalText,deliveryFeeText, taxText, grandTotal;
    private AppCompatButton checkOutBtn;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Constant.setStatusBarColor(this, R.color.puerto_rico);

        dbHelper = new DBHelper(this);

        ImageView backBtn = findViewById(R.id.backBtn);
        progressBar = findViewById(R.id.progressBar);
        cartLayout = findViewById(R.id.cartLayout);
        emptyText = findViewById(R.id.emptyText);

        // For Price View
        subTotalText = findViewById(R.id.subTotalText);
        deliveryFeeText = findViewById(R.id.deliveryFeeText);
        taxText = findViewById(R.id.taxText);
        grandTotal = findViewById(R.id.grandTotal);
        checkOutBtn = findViewById(R.id.checkOutBtn);

        // For Payment Method View
        cashMethod = findViewById(R.id.cashMethod);
        cashMethod_ic = findViewById(R.id.cashMethod_ic);
        cashMethod_txt = findViewById(R.id.cashMethod_txt);
        cashMethod_desc = findViewById(R.id.cashMethod_desc);
        bankMethod = findViewById(R.id.bankMethod);
        bankMethod_ic = findViewById(R.id.bankMethod_ic);
        bankMethod_txt = findViewById(R.id.bankMethod_txt);
        bankMethod_desc = findViewById(R.id.bankMethod_desc);

        cashMethod.setOnClickListener(v->{
            paymentMethodSelected("Cash");
        });
        bankMethod.setOnClickListener(v->{
            paymentMethodSelected("Bank");
        });


        cartRecyclerView = findViewById(R.id.cartListRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartManager = new CartManager(this);
        cartItems = cartManager.getCartItems();

        cartAdapter = new CartAdapter(this, cartItems, cartManager,this);
        cartRecyclerView.setAdapter(cartAdapter);

        backBtn.setOnClickListener(v->{
            finish();
        });

        progressBar.setVisibility(View.VISIBLE);

        updateTotals();

        checkOutBtn.setOnClickListener(v -> {
            List<ProductModel> cartItems = cartManager.getCartItems();

            String orderId = "";
            String orderDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String userId = "06121996";
            String orderStatus = "Pending";

            OrderModel orderModel = new OrderModel(orderId, orderDate, userId, paymentMode, orderStatus, cartItems);
            dbHelper.submitOrderToFirebase(orderModel);
        });

    }


    private void paymentMethodSelected(String mode) {
        paymentMode=mode;
        // Reset all methods to default
        resetPaymentMethod(cashMethod, cashMethod_txt, cashMethod_desc, cashMethod_ic);
        resetPaymentMethod(bankMethod, bankMethod_txt, bankMethod_desc, bankMethod_ic);

        // Update selected method
        if (mode.equals("Cash")) {
            updatePaymentMethod(cashMethod, cashMethod_txt, cashMethod_desc, cashMethod_ic, R.color.puerto_rico);
        } else {
            updatePaymentMethod(bankMethod, bankMethod_txt, bankMethod_desc, bankMethod_ic, R.color.puerto_rico);
        }
    }

    private void resetPaymentMethod(View method, TextView txt, TextView desc, ImageView ic) {
        method.setBackgroundResource(R.drawable.grey_bg);
        txt.setTextColor(ContextCompat.getColor(CartActivity.this, R.color.black));
        desc.setTextColor(ContextCompat.getColor(CartActivity.this, R.color.black));
        ImageViewCompat.setImageTintList(ic, ColorStateList.valueOf(ContextCompat.getColor(CartActivity.this, R.color.dark_grey)));
    }

    private void updatePaymentMethod(View method, TextView txt, TextView desc, ImageView ic, int color) {
        method.setBackgroundResource(R.drawable.selected_bg);
        txt.setTextColor(ContextCompat.getColor(CartActivity.this, color));
        desc.setTextColor(ContextCompat.getColor(CartActivity.this, color));
        ImageViewCompat.setImageTintList(ic, ColorStateList.valueOf(ContextCompat.getColor(CartActivity.this, color)));
    }


    public void updateTotals() {
        List<ProductModel> cartItems = cartManager.getCartItems();

        if (cartItems.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
            cartLayout.setVisibility(View.GONE);
            subTotalText.setText("$0.00");
            deliveryFeeText.setText("$0.00");
            taxText.setText("$0.00");
            grandTotal.setText("$0.00");
            return;
        }
        progressBar.setVisibility(View.GONE);
        cartLayout.setVisibility(View.VISIBLE);
        double subTotal = 0;
        double deliveryFee = 5.00;
        double tax = 0.18;

        for (ProductModel item : cartItems) {
            subTotal += (double) item.getPrice() * item.getQuantity();
        }

        double taxAmount = subTotal * tax;

        double grandTotalValue = subTotal + taxAmount + deliveryFee;

        subTotalText.setText(String.format("$%.2f", subTotal));
        deliveryFeeText.setText(String.format("$%.2f", deliveryFee));
        taxText.setText(String.format("$%.2f", taxAmount));
        grandTotal.setText(String.format("$%.2f", grandTotalValue));
    }

}
