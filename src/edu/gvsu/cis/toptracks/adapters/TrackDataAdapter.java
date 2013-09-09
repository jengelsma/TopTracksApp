package edu.gvsu.cis.toptracks.adapters;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import edu.gvsu.cis.toptracks.R;
import edu.gvsu.cis.toptracks.TopTrackListActivity;
import edu.gvsu.cis.toptracks.TopTrackListActivity.MyViewHolder;
import edu.gvsu.cis.toptracks.data.TrackData;
import edu.gvsu.cis.toptracks.tasks.LastFMIconTask;

/**
 * A custom adapter to fill the Track list. 
 * 
 * @author Jonathan Engelsma
 *
 */
public class TrackDataAdapter extends BaseAdapter implements OnClickListener {
	
	private static final String debugTag = "TrackDataAdapter";
	private TopTrackListActivity activity;
	private LastFMIconTask imgFetcher;
	private LayoutInflater layoutInflater;
	private ArrayList<TrackData> tracks;
	
	
    public TrackDataAdapter (TopTrackListActivity a, LastFMIconTask i, LayoutInflater l, ArrayList<TrackData> data)
    {
    	this.activity = a;
    	this.imgFetcher = i;
    	this.layoutInflater = l;
    	this.tracks = data;
    }
    
    @Override
    public int getCount() {
        return this.tracks.size();
    }

    @Override
    public boolean areAllItemsEnabled () 
    {
    	return true;
    }
    
    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate (R.layout.trackrow, parent, false);
            holder = new MyViewHolder();
            holder.trackName = (TextView) convertView.findViewById(R.id.track_name);
            holder.artistName = (TextView) convertView.findViewById(R.id.artist_name);
            holder.icon = (ImageView) convertView.findViewById(R.id.album_icon);
            holder.trackButton = (Button) convertView.findViewById(R.id.track_button);
            holder.trackButton.setTag(holder);
            convertView.setTag(holder);
        }
        else {
            holder = (MyViewHolder) convertView.getTag();
        }
        
   		convertView.setOnClickListener(this);
   		
   		TrackData track = tracks.get(pos);
   		holder.track = track;
   		holder.trackName.setText(track.getName());
   		holder.artistName.setText(track.getArtist());
   		holder.trackButton.setOnClickListener(this);
   		if(track.getImageUrl() != null) {
   			holder.icon.setTag(track.getImageUrl());
   			Drawable dr = imgFetcher.loadImage(this, holder.icon);
   			if(dr != null) {
   				holder.icon.setImageDrawable(dr);
   			}
   		} else {
   			holder.icon.setImageResource(R.drawable.filler_icon);
   		}
   		
        return convertView;
    }
    
    @Override
	public void onClick(View v) {
		MyViewHolder holder = (MyViewHolder) v.getTag();
		if (v instanceof Button) {
			
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
						Uri.parse(holder.track.getArtistUrl()));
				this.activity.startActivity(intent);

		} else if (v instanceof View) {
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
					Uri.parse(holder.track.getTrackUrl()));
			this.activity.startActivity(intent);
		}
   		Log.d(debugTag,"OnClick pressed.");

	}
    
}