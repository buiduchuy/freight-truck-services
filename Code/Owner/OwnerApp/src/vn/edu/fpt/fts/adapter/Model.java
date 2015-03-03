package vn.edu.fpt.fts.adapter;

public class Model {
	
    private String title;
    private String counter;
    
    public Model(String title, String counter) {
		// TODO Auto-generated constructor stub
    	super();
        
        this.title = title;
        this.counter = counter;
	}

	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCounter() {
		return counter;
	}

	public void setCounter(String counter) {
		this.counter = counter;
	}
    
    
}
