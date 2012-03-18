import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {
	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}	
	
    @Test
    public void createAndRetrieveUser() {
    	new User("nobin.mathew@gmail.com", "asdfgh", "Nobin" ).save();
    	
    	User bob = User.find("byEmail", "nobin.mathew@gmail.com").first();
    	
    	assertNotNull(bob);
    	assertEquals("Nobin", bob.fullname);
    }
    
    @Test
    public void tryConnectAsUser (){
    	new User("nobin.mathew@gmail.com", "asdfgh", "Nobin").save();
    	assertNotNull(User.connect("nobin.mathew@gmail.com", "asdfgh"));
    	assertNull(User.connect("nobin.mathew@gmail.com", "abc"));
    	assertNull(User.connect("abc@gmail.com", "zxcvbn"));
    }
    
    @Test
    public void testPost () {
    	User bob = new User("nobin.mathew@gmail.com", "asdfgh", "Nobin").save();
    	
    	new Post("First Post", bob, "Hello World!").save();
    	assertEquals(1, Post.count());
    	List<Post> bobsPosts = Post.find("byAuthor", bob).fetch();
    	
    	assertEquals(1, bobsPosts.size());
    	Post firstPost = bobsPosts.get(0);
    	assertNotNull(firstPost);
    	assertEquals(bob, firstPost.author);
    	assertEquals("First Post", firstPost.title);
    	assertEquals("Hello World!", firstPost.content);
    	assertNotNull(firstPost.postedAt);
    	
    }
    
    @Test
    public void postComments() {
    	User bob = new User("nobin.mathew@gmail.com", "asdfgh", "Nobin").save();
    	
    	Post bobPost = new Post("First Post", bob, "Hello World!").save();
    	
    	new Comment(bobPost, "mary", "Nice Post").save();
    	new Comment(bobPost, "Nobin", "I knew that !").save();
    	List<Comment> bobpostComments = Comment.find("byPost", bobPost).fetch();
    	
    	assertEquals(2, bobpostComments.size());
    }
    
    @Test
    public void useTheCommentsRelation() {
    	User bob = new User("nobin.mathew@gmail.com", "asdfgh", "Nobin").save();
    	
    	Post bobPost = new Post("First Post", bob, "Hello World!").save();
    	
    	bobPost.addComment("mary", "Nice Post!");
    	bobPost.addComment("rachel", "You copied Dad!");
    	
    	assertEquals(1, User.count());
    	assertEquals(1, Post.count());
    	assertEquals(2, Comment.count());
    	bobPost = Post.find("byAuthor", bob).first();
    	assertNotNull(bobPost);
    	assertEquals(2, bobPost.comments.size());
    	assertEquals("mary", bobPost.comments.get(0).author);
    	bobPost.delete();
    	
    	assertEquals(1, User.count());
    	assertEquals(0, Post.count());
    	assertEquals(0, Comment.count());    	   	
    }
    
    
    @Test
    public void fullTest() {
    	Fixtures.loadModels("data.yml");
    	
    	assertEquals(2, User.count());
    }
    
    
    @Test
    public void testTags() {
    	User bob = new User("nobin.mathew@gmail.com", "asdfgh", "Nobin").save();
    	
    	Post bobPost = new Post("My First Post", bob, "Hello World! First Time").save();
    	Post AnotherbobPost = new Post("My Second Post", bob, "Hello World! Second Time").save();
    	
    	assertEquals(0, Post.findTaggedWith("Red").size());
    	bobPost.tagItWith("Red").tagItWith("Blue").save();
    	AnotherbobPost.tagItWith("Red").tagItWith("Green").save();
    	
    	assertEquals(2, Post.findTaggedWith("Red").size());
    	assertEquals(1, Post.findTaggedWith("Green").size());
    	assertEquals(1, Post.findTaggedWith("Blue").size());    	
    }
}
