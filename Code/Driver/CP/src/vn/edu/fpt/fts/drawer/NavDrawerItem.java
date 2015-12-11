package vn.edu.fpt.fts.drawer;
public class NavDrawerItem {
	
	private String count = "0";
	private int icon;
	// boolean to set visiblity of the counter
	private boolean isCounterVisible = false;
	private String title;
	
	public NavDrawerItem(){}

	public NavDrawerItem(String title, int icon){
		this.title = title;
		this.icon = icon;
	}
	
	public NavDrawerItem(String title, int icon, boolean isCounterVisible, String count){
		this.title = title;
		this.icon = icon;
		this.isCounterVisible = isCounterVisible;
		this.count = count;
	}
	
	public String getCount(){
		return this.count;
	}
	
	public boolean getCounterVisibility(){
		return this.isCounterVisible;
	}
	
	public int getIcon(){
		return this.icon;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public void setCount(String count){
		this.count = count;
	}
	
	public void setCounterVisibility(boolean isCounterVisible){
		this.isCounterVisible = isCounterVisible;
	}
	
	public void setIcon(int icon){
		this.icon = icon;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
}
