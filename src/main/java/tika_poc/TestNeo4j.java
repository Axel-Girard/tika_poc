package tika_poc;

import java.util.ArrayList;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

public class TestNeo4j {

	private static Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "root" ) );;
	private static Session session = driver.session();

	public static void createNode(String type, String name, ArrayList<String> params){
		String parameter = "name: \"" + name+"\", ";
		for(int i = 0; i < params.size(); i++){
			parameter += "link"+i+": \"" + params.get(i) + "\", ";
		}
		if(parameter.length() >= 2){
			parameter = parameter.substring(0, parameter.length()-2);
		}

		session.run( "CREATE (a:" + type + " {" + parameter + "})");

//		StatementResult result = session.run("MATCH (a:Person) WHERE a.name = {name} RETURN a.name AS name, a.title AS title", parameters("name", name));
//		while(result.hasNext()) {
//		    Record record = result.next();
//		    System.out.println(record.get("title").asString() + " " + record.get("name").asString());
//		}
	}

	public static void disconnect(){
		session.close();
		driver.close();
	}
}
