//https://github.com/natoine/Argumentea/blob/master/ArgumenteaServer/app/Global.java

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;

import models.Annotation;
import models.Quadrant;
import models.Video;
import play.GlobalSettings;
import play.Logger;

import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

import controllers.MorphiaObject;

public class Global extends GlobalSettings 
{

	@Override
	public void onStart(play.Application arg0) {
		super.beforeStart(arg0);
		Logger.debug("** onStart **");
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream("conf/mongo.properties"));


			MorphiaObject.mongo = new Mongo(prop.getProperty("url"), Integer.parseInt(prop.getProperty("port")));			
			MorphiaObject.morphia = new Morphia();

			MorphiaObject.morphia.map(Annotation.class);
			MorphiaObject.morphia.map(Video.class);
			MorphiaObject.morphia.map(Quadrant.class);

			MorphiaObject.datastore = MorphiaObject.morphia.createDatastore(
					MorphiaObject.mongo, 
					prop.getProperty("db"), 
					prop.getProperty("id"), 
					prop.getProperty("pw").toCharArray());

			//MorphiaObject.datastore.ensureCaps();
			MorphiaObject.datastore.ensureIndexes();


		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}





	}
}

