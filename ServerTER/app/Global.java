//https://github.com/natoine/Argumentea/blob/master/ArgumenteaServer/app/Global.java

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

		
		//MorphiaLoggerFactory.get(SLF4JLogrImplFactory.class);
		//MorphiaLoggerFactory.registerLogger(SLF4JLogrImplFactory.class);

		Properties prop = new Properties();
		String url = "";
		String port = "";
		String db = "";

		try {

			prop.load(new FileInputStream("conf/prod.properties"));
			url = prop.getProperty("url");
			port = prop.getProperty("port");
			db = prop.getProperty("db");

			System.out.println(url+" "+port+" "+db);

			/*
				prop.load(new FileInputStream("dev.properties"));
				url = prop.getProperty("url");
				port = prop.getProperty("port");
				db = prop.getProperty("db");
				id = prop.getProperty("id");
				pw = prop.getProperty("pw");
			 */

			MorphiaObject.mongo = new Mongo(url, Integer.parseInt(port));
			MorphiaObject.morphia = new Morphia();
			MorphiaObject.morphia.map(Annotation.class);
			MorphiaObject.morphia.map(Video.class);
			MorphiaObject.morphia.map(Quadrant.class);

			MorphiaObject.datastore = MorphiaObject.morphia.createDatastore(MorphiaObject.mongo, db);


			//MorphiaObject.datastore = MorphiaObject.morphia.createDatastore(MorphiaObject.mongo, db, id, pw.toCharArray());


			MorphiaObject.datastore.ensureIndexes();
			//MorphiaObject.datastore.ensureCaps();
		}
		catch (FileNotFoundException e) {
			System.err.println("Configuration files not found.");
		} catch (IOException e) {
			System.err.println("Configuration files not found.");
		}

	}
}

