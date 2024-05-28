package com.example.exam2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<Car> cars;
    private OnItemClickListener listener;
    public CarAdapter(List<Car> cars) {
        this.cars = cars;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = cars.get(position);
        holder.carIdTextView.setText(car.getId());
        holder.carStatusTextView.setText(car.getStatus());
        holder.clientNameTextView.setText(car.getClientName());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(car);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public void updateCars(List<Car> cars) {
        this.cars = cars;
        notifyDataSetChanged();
    }

    static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView carIdTextView;
        TextView carStatusTextView;
        TextView clientNameTextView;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            carIdTextView = itemView.findViewById(R.id.carIdTextView);
            carStatusTextView = itemView.findViewById(R.id.carStatusTextView);
            clientNameTextView = itemView.findViewById(R.id.clientNameTextView);
        }
    }
}
