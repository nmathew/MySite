package controllers;

import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Admin extends Controller {
	@Before
	static void setConnecteduser() {
		if(Security.isConnected()) {
			User user = User.all().filter("email", Security.connected()).get();
			renderArgs.put("user", user.fullname);
		}
	}	
	
	public static void index() {		
		render();
	}

}
