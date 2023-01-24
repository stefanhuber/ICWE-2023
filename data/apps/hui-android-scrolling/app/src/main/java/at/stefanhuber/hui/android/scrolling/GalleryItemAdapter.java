package at.stefanhuber.hui.android.scrolling;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GalleryItemAdapter extends RecyclerView.Adapter<GalleryItemAdapter.GalleryItemViewHolder> {

    protected List<GalleryItem> items;

    public static class GalleryItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView subtitle;
        ShapeableImageView image;

        public GalleryItemViewHolder(ViewGroup item) {
            super(item);
            title = item.findViewById(R.id.item_title);
            subtitle = item.findViewById(R.id.item_subtitle);
            image = item.findViewById(R.id.item_image);
        }
    }

    @NonNull
    @Override
    public GalleryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FrameLayout item = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_list_item, parent, false);
        return new GalleryItemViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryItemViewHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.subtitle.setText(items.get(position).getSubtitle());

        try {
            Context context = holder.image.getContext();
            InputStream stream = context.getAssets().open("data/" + items.get(position).getImage());
            Drawable image = Drawable.createFromStream(stream, null);
            holder.image.setImageDrawable(image);
            stream.close();
        } catch (Exception e) {
            Log.e("GALLERY", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void init(InputStream stream) throws Exception {
        items = new ArrayList<>();
        String text = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining(""));
        JSONArray data = new JSONArray(text);

        for (int i = 0; i < data.length(); i++) {
            GalleryItem item = new GalleryItem();
            item.setTitle(data.getJSONObject(i).getString("title"));
            item.setSubtitle(data.getJSONObject(i).getInt("count") + " images");
            item.setImage(data.getJSONObject(i).getString("image"));
            items.add(item);
        }

        notifyDataSetChanged();
    }

}
