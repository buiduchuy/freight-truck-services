package vn.edu.fpt.fts.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class GoodsCategory implements Parcelable {
	private String count;
	private String id;
	private String name;

	public GoodsCategory() {
		// TODO Auto-generated constructor stub
	}

	public GoodsCategory(String count, String id, String name) {
		super();
		this.count = count;
		this.id = id;
		this.name = name;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GoodsCategory(Parcel in) {
		String[] data = new String[3];
		in.readStringArray(data);
		this.count = data[0];
		this.id = data[1];
		this.name = data[2];
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeStringArray(new String[] { this.count, this.id, this.name });
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public GoodsCategory createFromParcel(Parcel in) {
			return new GoodsCategory(in);
		}

		@Override
		public Object[] newArray(int size) {
			// TODO Auto-generated method stub
			return new GoodsCategory[size];
		}

	};
}
