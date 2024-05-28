package com.example.exam2;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ReportActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Получение менеджера через Intent или Singleton (например, как статическое поле)
        CarRentalManager manager = MySingleton.getCarRentalManager();
        TextView reportTextView = findViewById(R.id.reportTextView);
        String report = generateReport(manager);
        reportTextView.setText(report);
    }

    private String generateReport(CarRentalManager manager) {
        int availableCount = manager.getCountByStatus("available");
        int rentedCount = manager.getCountByStatus("rented");
        int scrappedCount = manager.getCountByStatus("scrapped");

        return String.format("Available: %d\nRented: %d\nScrapped: %d", availableCount, rentedCount, scrappedCount);
    }
}
