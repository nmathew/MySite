import play.*;
import play.jobs.*;
import play.test.*;
import models.*;

@OnApplicationStart
public class BootStrap extends Job {
	public void doJob() {
		if(User.count() == 0)
			Fixtures.loadModels("data.yml");
	}
}
