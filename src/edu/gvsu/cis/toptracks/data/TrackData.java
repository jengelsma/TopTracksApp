package edu.gvsu.cis.toptracks.data;

public class TrackData {
	
	private String name;
	private String artist;
	private String imageUrl;
	private String artistUrl;
	private String trackUrl;
	
	public TrackData(String name, String artist, String imageUrl, String artistUrl, String trackUrl) {
		super();
		this.name = name;
		this.artist = artist;
		this.imageUrl = imageUrl;
		this.artistUrl = artistUrl;
		this.trackUrl = trackUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getArtistUrl() {
		return artistUrl;
	}

	public void setArtistUrl(String artistUrl) {
		this.artistUrl = artistUrl;
	}

	public String getTrackUrl() {
		return trackUrl;
	}

	public void setTrackUrl(String trackUrl) {
		this.trackUrl = trackUrl;
	}
	
	

}
