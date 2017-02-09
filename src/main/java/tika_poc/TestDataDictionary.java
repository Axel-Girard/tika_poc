package tika_poc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TestDataDictionary {

	private static Connection connection;

	public static void connection() {
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/dataDictionnary", "postgres","admin");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> getDataDictionnary(){
		Statement statement = null;
		String selectSQL = "SELECT * FROM words";
		ArrayList<String> resultat = new ArrayList<String>();

		try {
			statement = connection.createStatement();

			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectSQL);

			while (rs.next()) {
				String word = rs.getString("word");
				resultat.add(word);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return resultat;
	}
	
	public static void deconnection(){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	public static void main(String[] args) {
//		connection();
//		for(String str: getDataDictionnary()){
//			System.out.println("word : " + str);
//		}
//		deconnection();
//	}
}
