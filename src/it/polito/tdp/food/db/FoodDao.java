package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.food.model.CondimentCounter;
import it.polito.tdp.food.model.CondimentIdMap;


public class FoodDao {

	public List<Food> listAllFood(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getInt("portion_default"), 
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"),
							res.getDouble("calories")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiment(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getString("condiment_portion_size"), 
							res.getDouble("condiment_calories")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listCondimentUnderCalories(double cal, CondimentIdMap condimentIdMap){
		String sql = "SELECT * " + 
				"FROM condiment " + 
				"WHERE condiment_calories < ?" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setDouble(1, cal);
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					/*list.add(new Condiment(res.getInt("condiment_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getString("condiment_portion_size"), 
							res.getDouble("condiment_calories")
							));*/
					list.add(condimentIdMap.get(new Condiment(res.getInt("condiment_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getString("condiment_portion_size"), 
							res.getDouble("condiment_calories")
							)));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<CondimentCounter> getCondimentCounter (double cal, CondimentIdMap condimentIdMap){
		String sql = "SELECT c1.condiment_id AS c1id, c2.condiment_id AS c2id, COUNT(*) AS cnt " + 
				"FROM food_condiment AS f1, food_condiment AS f2, condiment AS c1, condiment AS c2 " + 
				"WHERE c1.food_code = f1.condiment_food_code " + 
				"AND c2.food_code = f2.condiment_food_code " + 
				"AND f1.condiment_food_code != f2.condiment_food_code " + 
				"AND f1.food_code = f2.food_code " + 
				"AND c1.condiment_calories < ? " + 
				"AND c2.condiment_calories < ? " + 
				"GROUP BY c1id, c2id" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setDouble(1, cal);
			st.setDouble(2, cal);
			
			List<CondimentCounter> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					Condiment c1 = condimentIdMap.get(res.getInt("c1id"));
					Condiment c2 = condimentIdMap.get(res.getInt("c2id"));
					int cnt = res.getInt("cnt");
					
					if (c1 == null || c2 == null) {
						System.out.println("Errore");
					} else {
						list.add(new CondimentCounter(c1, c2, cnt));
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}
	
}
