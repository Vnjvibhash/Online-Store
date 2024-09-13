package in.innovateria.onlinestore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import in.innovateria.onlinestore.Activities.ProductDetailActivity;
import in.innovateria.onlinestore.R;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ModelViewHolder> {
    private List<String> modelList;
    private Context context;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private ProductDetailActivity activity;

    public ModelAdapter(Context context, List<String> modelList, ProductDetailActivity activity) {
        this.context = context;
        this.modelList = modelList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_view, parent, false);
        return new ModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelViewHolder holder, int position) {
        holder.modelText.setText(modelList.get(position));
        if(selectedPosition==position){
            holder.modelLayout.setBackgroundResource(R.drawable.selected_bg);
            holder.modelText.setTextColor(
                    ContextCompat.getColor(
                            holder.modelText.getContext(),
                            R.color.white
                    )
            );
        }else{
            holder.modelLayout.setBackgroundResource(R.drawable.grey_bg);
            holder.modelText.setTextColor(
                    ContextCompat.getColor(
                            holder.modelText.getContext(),
                            R.color.black
                    )
            );
        }
        holder.modelLayout.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            activity.setModel(modelList.get(selectedPosition));
            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class ModelViewHolder extends RecyclerView.ViewHolder {
        TextView modelText;
        ConstraintLayout modelLayout;

        public ModelViewHolder(@NonNull View itemView) {
            super(itemView);
            modelText = itemView.findViewById(R.id.modelText);
            modelLayout = itemView.findViewById(R.id.modelLayout);
        }
    }
}

