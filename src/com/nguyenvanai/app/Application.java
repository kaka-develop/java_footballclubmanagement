package com.nguyenvanai.app;

public class Application {

	private ApplicationMenu menus;
			
	public static void main(String[] args) {
		Application myApplication = new Application();
		myApplication.start();
		
	}
	
	public Application() {
		menus = new ApplicationMenu();
	}

	public void start(){
		menus.start();
	}
}
