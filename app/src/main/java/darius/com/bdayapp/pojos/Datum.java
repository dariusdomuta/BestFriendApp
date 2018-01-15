package darius.com.bdayapp.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("created_time")
    @Expose
    private String createdTime;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("story")
    @Expose
    private String story;
    public final static Parcelable.Creator<Datum> CREATOR = new Parcelable.Creator<Datum>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    }
            ;

    protected Datum(Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.createdTime = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.story = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Datum() {
    }

    /**
     *
     * @param id
     * @param message
     * @param story
     * @param createdTime
     */
    public Datum(String message, String createdTime, String id, String story) {
        super();
        this.message = message;
        this.createdTime = createdTime;
        this.id = id;
        this.story = story;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Datum withMessage(String message) {
        this.message = message;
        return this;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public Datum withCreatedTime(String createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Datum withId(String id) {
        this.id = id;
        return this;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public Datum withStory(String story) {
        this.story = story;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(message);
        dest.writeValue(createdTime);
        dest.writeValue(id);
        dest.writeValue(story);
    }

    public int describeContents() {
        return 0;
    }

}