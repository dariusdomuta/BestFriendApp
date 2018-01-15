package darius.com.bdayapp.pojos;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserFeed
{

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("paging")
    @Expose
    private Paging paging;
    public final static Parcelable.Creator<UserFeed> CREATOR = new Creator<UserFeed>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserFeed createFromParcel(Parcel in) {
            return new UserFeed(in);
        }

        public UserFeed[] newArray(int size) {
            return (new UserFeed[size]);
        }

    }
            ;

    protected UserFeed(Parcel in) {
        in.readList(this.data, (darius.com.bdayapp.pojos.Datum.class.getClassLoader()));
        this.paging = ((Paging) in.readValue((Paging.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public UserFeed() {
    }

    /**
     *
     * @param data
     * @param paging
     */
    public UserFeed(List<Datum> data, Paging paging) {
        super();
        this.data = data;
        this.paging = paging;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public UserFeed withData(List<Datum> data) {
        this.data = data;
        return this;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public UserFeed withPaging(Paging paging) {
        this.paging = paging;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
        dest.writeValue(paging);
    }

    public int describeContents() {
        return 0;
    }

}