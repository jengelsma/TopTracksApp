package edu.gvsu.cis.toptracks.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Image cache and async fetcher task for Last.FM images.
 * 
 * @author Jonathan Engelsma
 */
public class LastFMIconTask {
	
	private static final String debugTag = "ImageWorker";

    private HashMap<String, Drawable> imageCache;
    private static Drawable DEFAULT_ICON = null;
    private BaseAdapter adapt;
    
    
    public LastFMIconTask (Context ctx)
    {
        imageCache = new HashMap<String, Drawable>();
    }
    
    public Drawable loadImage (BaseAdapter adapt, ImageView view)
    {
        this.adapt = adapt;
        String url = (String) view.getTag();
        if (imageCache.containsKey(url))
        {
            return imageCache.get(url);
        }
        else {
            new ImageTask().execute(url);
            return DEFAULT_ICON;
        }
    }
    
    private class ImageTask extends AsyncTask<String, Void, Drawable>
    {
        private String s_url;

        @Override
        protected Drawable doInBackground(String... params) {
            s_url = params[0];
            InputStream istr;
            try {
                Log.d(debugTag, "Fetching: " + s_url);
                URL url = new URL(s_url);
                istr = url.openStream();
            } catch (MalformedURLException e) {
                Log.d(debugTag, "Malformed: " + e.getMessage());
                throw new RuntimeException(e);
            } catch (IOException e)
            {
                Log.d(debugTag, "I/O : " + e.getMessage());
                throw new RuntimeException(e);
                
            }
            return Drawable.createFromStream(istr, "src");
        }

        @Override
        protected void onPostExecute(Drawable result) {
            super.onPostExecute(result);
            synchronized (this) {
                imageCache.put(s_url, result);
            }
            adapt.notifyDataSetChanged();
        }
        
    }
}
