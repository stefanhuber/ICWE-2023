package at.stefanhuber.hui.android.elements;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity {

    protected BottomSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.bottom_sheet);
    }

    public void openSheet(View view) {
        dialog.show();
    }

    public void openDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Entry");
        builder.setMessage("Do you really want to delete the entry?");
        builder.setPositiveButton("Delete", null);
        builder.setNegativeButton("Abort", null);
        builder.create().show();
    }

}