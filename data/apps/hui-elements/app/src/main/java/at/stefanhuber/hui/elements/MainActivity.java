package at.stefanhuber.hui.elements;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity {

    protected BottomSheetDialog sheetDialog;
    protected WebView sheet;
    protected WebView drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.nav_view);
        drawer.loadUrl("file:///android_asset/drawer.html");

        sheetDialog = new BottomSheetDialog(this);
        sheet = new WebView(this);
        sheet.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 750));
        sheet.loadUrl("file:///android_asset/sheet.html");
        sheetDialog.setContentView(sheet);


    }

    public void openSheet(View view) {
        sheetDialog.show();
    }

    public void openDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        WebView dialog = new WebView(this);
        dialog.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        dialog.loadUrl("file:///android_asset/dialog.html");
        builder.setView(dialog);
        builder.create().show();
    }

}