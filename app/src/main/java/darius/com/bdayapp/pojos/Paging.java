package darius.com.bdayapp.pojos;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Paging
{

    @SerializedName("previous")
    @Expose
    private String previous;
    @SerializedName("next")
    @Expose
    private String next;
    public final static Parcelable.Creator<Paging> CREATOR = new Creator<Paging>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Paging createFromParcel(Parcel in) {
            return new Paging(in);
        }

        public Paging[] newArray(int size) {
            return (new Paging[size]);
        }

    }
            ;

    protected Paging(Parcel in) {
        this.previous = ((String) in.readValue((String.class.getClassLoader())));
        this.next = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Paging() {
    }

    /**
     *
     * @param previous
     * @param next
     */
    public Paging(String previous, String next) {
        super();
        this.previous = previous;
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public Paging withPrevious(String previous) {
        this.previous = previous;
        return this;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Paging withNext(String next) {
        this.next = next;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(previous);
        dest.writeValue(next);
    }

    public int describeContents() {
        return 0;
    }

}