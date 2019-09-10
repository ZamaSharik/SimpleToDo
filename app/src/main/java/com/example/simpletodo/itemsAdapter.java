package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Adapter is Responsible for displaying data from the model into a row in the recycler view.
// Adapter is used to handle the data collection and bind it to the view.
public class itemsAdapter extends RecyclerView.Adapter<itemsAdapter.ViewHolder> {

    public interface OnLongClickListener {
        // The class that implements this inteface needs to know at which position we need to delete.
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;

    public itemsAdapter(List <String> items, OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    //Responsible for creating each view.
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use layout inflator to inflate a view.
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        // Wrap it inside a view holder and return it.
        return new ViewHolder(todoView);
    }

    @Override
    // Responsible for taking data at a particular position and putting it into the view holder.

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Grab the item at the position.
        String item = items.get(position);
        // Bind the item into the specified view holder.
        holder.bind(item);
    }

    @Override
    // No. of items in the list.
    public int getItemCount() {
        return items.size();
    }

    // Container to provide access to views that represent each row of the list.
    // ViewHolder Helps in positioning the view.
    class ViewHolder extends RecyclerView.ViewHolder {

        // This is a reference to access the TextView in simple_list_item_1 through id which is text1.
        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // This is the view inside the viewholder.
            tvItem = itemView.findViewById(android.R.id.text1);
        }
        // update the view inside of the view holder with this data.
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Notify the listener which position was long pressed.
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return false;
                }
            });
        }
    }
}
