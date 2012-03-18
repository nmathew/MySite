package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.User;

@With(Secure.class)
public class Admin extends Controller {
	@Before
	static void setConnecteduser() {
		if(Security.isConnected()) {
			User user = User.find("byEmail", Security.connected()).first();
			renderArgs.put("user", user.fullname);
		}
	}
	
	public static void index() {
		render();
	}

}
