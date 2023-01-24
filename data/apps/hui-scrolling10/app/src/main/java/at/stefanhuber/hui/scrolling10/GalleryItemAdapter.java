package at.stefanhuber.hui.scrolling10;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GalleryItemAdapter extends RecyclerView.Adapter<GalleryItemAdapter.GalleryItemViewHolder> {

    protected int webViewElements = 10;

    protected List<GalleryItem[]> items;
    protected RecyclerView recyclerView;

    public static class GalleryItemViewHolder extends RecyclerView.ViewHolder {
        WebView webView;
        boolean initialized = false;

        public GalleryItemViewHolder(View view) {
            super(view);

            if (view instanceof WebView) {
                webView = (WebView) view;
                webView.loadUrl("file:///android_asset/gallery_item.html");
                //webView.loadUrl("file:///android_asset/gallery_item_text_only.html");
                webView.getSettings().setJavaScriptEnabled(true);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public GalleryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_list_item, parent, false);
        return new GalleryItemViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull GalleryItemViewHolder holder, int position) {
        final GalleryItem[] item = items.get(position);
        final String renderData = "renderData([" + Arrays.stream(item).map(it -> it.toJson()).collect(Collectors.joining(", ")) + "])";

        if (holder.initialized) {
            holder.webView.evaluateJavascript(renderData, null);
        } else {
            holder.webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.evaluateJavascript(renderData, null);
                    holder.initialized = true;
                }
            });
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

        int collectionSize = webViewElements;
        GalleryItem[] itemCollections = null;
        for (int i = 0; i < data.length(); i++) {
            if (i % collectionSize == 0) {
                itemCollections = new GalleryItem[collectionSize];
            }

            GalleryItem item = new GalleryItem();
            item.setTitle(data.getJSONObject(i).getString("title"));
            item.setSubtitle(data.getJSONObject(i).getInt("count") + " images");
            item.setImage(data.getJSONObject(i).getString("image"));
            itemCollections[i % collectionSize] = item;

            if (i % collectionSize == (collectionSize-1)) {
                items.add(itemCollections);
            }
        }

        notifyDataSetChanged();
    }

}
