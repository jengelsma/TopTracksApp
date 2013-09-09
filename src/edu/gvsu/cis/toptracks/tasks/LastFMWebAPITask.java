package edu.gvsu.cis.toptracks.tasks;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.gvsu.cis.toptracks.R;
import edu.gvsu.cis.toptracks.TopTrackListActivity;
import edu.gvsu.cis.toptracks.data.TrackData;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * AsyncTask for fetching from Last.FM Geo Metro Track Chart API. 
 *
 * @author Jonathan Engelsma
 */
public class LastFMWebAPITask extends AsyncTask<String, Integer, String>
{
	private ProgressDialog progDialog;
	private Context context;
	private TopTrackListActivity activity;
	private static final String debugTag = "LastFMWebAPITask";
	
	/**
	 * Construct a task
	 * @param activity
	 */
    public LastFMWebAPITask(TopTrackListActivity activity) {
		super();
		this.activity = activity;
		this.context = this.activity.getApplicationContext();
	}

	@Override
    protected void onPreExecute() {
        super.onPreExecute(); 
    	progDialog = ProgressDialog.show(this.activity, "Search", this.context.getResources().getString(R.string.looking_for_tracks) , true, false);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
        	Log.d(debugTag,"Background:" + Thread.currentThread().getName());
            String result = LastFMHelper.downloadFromServer(params);
            return result;
        } catch (Exception e) {
            return new String();
        }
    }
    
    @Override
    protected void onPostExecute(String result) 
    {
    	
    	ArrayList<TrackData> trackdata = new ArrayList<TrackData>();
    	
    	progDialog.dismiss();
        if (result.length() == 0) {
            this.activity.alert ("Unable to find track data. Try again later.");
            return;
        }
        
        try {
			JSONObject respObj = new JSONObject(result);
			JSONObject topTracksObj = respObj.getJSONObject("toptracks");
			JSONArray tracks = topTracksObj.getJSONArray("track");
			for(int i=0; i<tracks.length(); i++) {
				JSONObject track = tracks.getJSONObject(i);	
				String trackName = track.getString("name");
				String trackUrl = track.getString("url");
				JSONObject artistObj = track.getJSONObject("artist");
				String artistName = artistObj.getString("name");
				String artistUrl = artistObj.getString("url");
				String imageUrl;
				try {
					JSONArray imageUrls = track.getJSONArray("image");
					imageUrl = null;
					for(int j=0; j<imageUrls.length(); j++) {
						JSONObject imageObj = imageUrls.getJSONObject(j);
						imageUrl = imageObj.getString("#text");
						if(imageObj.getString("size").equals("medium")) {
							break;
						}
					}
				} catch (Exception e) {
					imageUrl = null;
				}
				
				trackdata.add(new TrackData(trackName,artistName,imageUrl, artistUrl, trackUrl));				
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.activity.setTracks(trackdata);
             
    }
}