package com.firyal.moviecatalogue.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_movie")
public class ResultsItemMovie implements Parcelable {

	@ColumnInfo(name = "overview")
	@SerializedName("overview")
	private String overview;

	@ColumnInfo(name = "original_language")
	@SerializedName("original_language")
	private String originalLanguage;

	@ColumnInfo(name = "original_title")
	@SerializedName("original_title")
	private String originalTitle;

	@ColumnInfo(name = "video")
	@SerializedName("video")
	private boolean video;

	@ColumnInfo(name = "title")
	@SerializedName("title")
	private String title;

	@ColumnInfo(name = "poster_path")
	@SerializedName("poster_path")
	private String posterPath;

	@ColumnInfo(name = "backdrop_path")
	@SerializedName("backdrop_path")
	private String backdropPath;

	@ColumnInfo(name = "release_date")
	@SerializedName("release_date")
	private String releaseDate;

	@ColumnInfo(name = "vote_average")
	@SerializedName("vote_average")
	private double voteAverage;

	@ColumnInfo(name = "popularity")
	@SerializedName("popularity")
	private double popularity;

	@PrimaryKey
	@ColumnInfo(name = "id")
	@SerializedName("id")
	private int id;

	@ColumnInfo(name = "adult")
	@SerializedName("adult")
	private boolean adult;

	@ColumnInfo(name = "vote_count")
	@SerializedName("vote_count")
	private int voteCount;

	@SuppressLint("NewsApi")
	public static ResultsItemMovie fromContentValues(ContentValues values){
		final ResultsItemMovie movie = new ResultsItemMovie();
		if (values.containsKey("id")){
			movie.id = Math.toIntExact(values.getAsLong("id"));
		}
		if (values.containsKey("original_title")){
			movie.originalTitle = values.getAsString("original_title");
		}
		return movie;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getOriginalLanguage() {
		return originalLanguage;
	}

	public void setOriginalLanguage(String originalLanguage) {
		this.originalLanguage = originalLanguage;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public boolean isVideo() {
		return video;
	}

	public void setVideo(boolean video) {
		this.video = video;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public String getBackdropPath() {
		return backdropPath;
	}

	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public double getVoteAverage() {
		return voteAverage;
	}

	public void setVoteAverage(double voteAverage) {
		this.voteAverage = voteAverage;
	}

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	public static Creator<ResultsItemMovie> getCREATOR() {
		return CREATOR;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.overview);
		dest.writeString(this.originalLanguage);
		dest.writeString(this.originalTitle);
		dest.writeByte(this.video ? (byte) 1 : (byte) 0);
		dest.writeString(this.title);
		dest.writeString(this.posterPath);
		dest.writeString(this.backdropPath);
		dest.writeString(this.releaseDate);
		dest.writeDouble(this.voteAverage);
		dest.writeDouble(this.popularity);
		dest.writeInt(this.id);
		dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
		dest.writeInt(this.voteCount);
	}

	public ResultsItemMovie() {
	}

	protected ResultsItemMovie(Parcel in) {
		this.overview = in.readString();
		this.originalLanguage = in.readString();
		this.originalTitle = in.readString();
		this.video = in.readByte() != 0;
		this.title = in.readString();
		this.posterPath = in.readString();
		this.backdropPath = in.readString();
		this.releaseDate = in.readString();
		this.voteAverage = in.readDouble();
		this.popularity = in.readDouble();
		this.id = in.readInt();
		this.adult = in.readByte() != 0;
		this.voteCount = in.readInt();
	}

	public static final Creator<ResultsItemMovie> CREATOR = new Creator<ResultsItemMovie>() {
		@Override
		public ResultsItemMovie createFromParcel(Parcel source) {
			return new ResultsItemMovie(source);
		}

		@Override
		public ResultsItemMovie[] newArray(int size) {
			return new ResultsItemMovie[size];
		}
	};
}