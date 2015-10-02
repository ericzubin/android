package com.embeddedlapps.subastas_cliente;

public class Model{
	 
    private int icon;
    private String title;
    private String counter;
 
    private boolean isGroupHeader = false;
 
    public Model(String title) {
        this(-1,title,null);
        isGroupHeader = true;
    }
    public Model(int icon, String title, String counter) {
        super();
        this.icon = icon;
        this.title = title;
        this.counter = counter;
    }
	
    public boolean isGroupHeader() {
		// TODO Auto-generated method stub
		return false;
	}
 
//gettters & setters...
}