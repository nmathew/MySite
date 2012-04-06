package controllers;

import models.Post;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;
import java.util.*;

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
		String user = Security.connected();
		User loginuser = User.all().filter("email", user).get();
		List <Post> posts = Post.all().filter("author", loginuser).fetch();		
		render(posts);
	}
	
	public static void form() {
		render();
	}
	
	public static void save() {
		
	}

}
