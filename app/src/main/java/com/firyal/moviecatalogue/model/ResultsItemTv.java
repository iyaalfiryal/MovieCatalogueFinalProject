package com.firyal.moviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_tv")
public class ResultsItemTv implements Parcelable {

	public ResultsItemTv() {
	}

	@ColumnInfo(name = "first_air_date")
	@SerializedName("first_air_date")
	private String firstAirDate;

	@ColumnInfo(name = "overview")
	@SerializedName("overview")
	private String overview;

	@ColumnInfo(name = "original_language")
	@SerializedName("original_language")
	private String originalLanguage;
//
//	@ColumnInfo(name = "genre_ids")
//	@SerializedName("genre_ids")
//	private List<Integer> genreIds;

	@ColumnInfo(name = "poster_path")
	@SerializedName("poster_path")
	private String posterPath;

//	@ColumnInfo(name = "origin_country")
//	@SerializedName("origin_country")
//	private List<String> originCountry;

	@ColumnInfo(name = "backdrop_path")
	@SerializedName("backdrop_path")
	private String backdropPath;

	@ColumnInfo(name = "original_name")
	@SerializedName("original_name")
	private String originalName;

	@ColumnInfo(name = "popularity")
	@SerializedName("popularity")
	private double popularity;

	@ColumnInfo(name = "vote_average")
	@SerializedName("vote_average")
	private double voteAverage;

	@ColumnInfo(name = "name")
	@SerializedName("name")
	private String name;

	@PrimaryKey
	@ColumnInfo(name = "id")
	@SerializedName("id")
	private int id;

	@ColumnInfo(name = "vote_count")
	@SerializedName("vote_count")
	private int voteCount;

	public ResultsItemTv(Parcel in) {
		firstAirDate = in.readString();
		overview = in.readString();
		originalLanguage = in.readString();
		posterPath = in.readString();
//		originCountry = in.createStringArrayList();
		backdropPath = in.readString();
		originalName = in.readString();
		popularity = in.readDouble();
		voteAverage = in.readDouble();
		name = in.readString();
		id = in.readInt();
		voteCount = in.readInt();
	}

	public static final Creator<ResultsItemTv> CREATOR = new Creator<ResultsItemTv>() {
		@Override
		public ResultsItemTv createFromParcel(Parcel in) {
			return new ResultsItemTv(in);
		}

		@Override
		public ResultsItemTv[] newArray(int size) {
			return new ResultsItemTv[size];
		}
	};

	public void setFirstAirDate(String firstAirDate){
		this.firstAirDate = firstAirDate;
	}

	public String getFirstAirDate(){
		return firstAirDate;
	}

	public void setOverview(String overview){
		this.overview = overview;
	}

	public String getOverview(){
		return overview;
	}

	public void setOriginalLanguage(String originalLanguage){
		this.originalLanguage = originalLanguage;
	}

	public String getOriginalLanguage(){
		return originalLanguage;
	}

//	public void setGenreIds(List<Integer> genreIds){
//		this.genreIds = genreIds;
//	}
//
//	public List<Integer> getGenreIds(){
//		return genreIds;
//	}

	public void setPosterPath(String posterPath){
		this.posterPath = posterPath;
	}

	public String getPosterPath(){
		return posterPath;
	}

//	public void setOriginCountry(List<String> originCountry){
//		this.originCountry = originCountry;
//	}
//
//	public List<String> getOriginCountry(){
//		return originCountry;
//	}

	public void setBackdropPath(String backdropPath){
		this.backdropPath = backdropPath;
	}

	public String getBackdropPath(){
		return backdropPath;
	}

	public void setOriginalName(String originalName){
		this.originalName = originalName;
	}

	public String getOriginalName(){
		return originalName;
	}

	public void setPopularity(double popularity){
		this.popularity = popularity;
	}

	public double getPopularity(){
		return popularity;
	}

	public void setVoteAverage(double voteAverage){
		this.voteAverage = voteAverage;
	}

	public double getVoteAverage(){
		return voteAverage;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setVoteCount(int voteCount){
		this.voteCount = voteCount;
	}

	public int getVoteCount(){
		return voteCount;
	}

	@Override
 	public String toString(){
		return 
			"ResultsItemTv{" +
			"first_air_date = '" + firstAirDate + '\'' + 
			",overview = '" + overview + '\'' + 
			",original_language = '" + originalLanguage + '\'' + 

			",poster_path = '" + posterPath + '\'' + 
//			",origin_country = '" + originCountry + '\'' +
			",backdrop_path = '" + backdropPath + '\'' + 
			",original_name = '" + originalName + '\'' + 
			",popularity = '" + popularity + '\'' + 
			",vote_average = '" + voteAverage + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",vote_count = '" + voteCount + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(firstAirDate);
		parcel.writeString(overview);
		parcel.writeString(originalLanguage);
		parcel.writeString(posterPath);
//		parcel.writeStringList(originCountry);
		parcel.writeString(backdropPath);
		parcel.writeString(originalName);
		parcel.writeDouble(popularity);
		parcel.writeDouble(voteAverage);
		parcel.writeString(name);
		parcel.writeInt(id);
		parcel.writeInt(voteCount);
	}
}