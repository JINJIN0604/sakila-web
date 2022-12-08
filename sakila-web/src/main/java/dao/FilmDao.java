package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.Film;

public class FilmDao {
	
	// release_year의 최소값
	public int selectMinReleaseYear() {
		int minYear = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila", "root", "java1234");
			String sql = "SELECT MIN(release_year) y FROM film";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()) {
				minYear = rs.getInt("y"); // rs.getInt(1)
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return minYear;
	}
	
	// rating : String[] 여러개의 등급
	public ArrayList<Film> selectFilmList2(String releaseYear, String searchTitle, String[] rating, int fromMinute, int toMinute){
		ArrayList<Film> list = new ArrayList<Film>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila", "root", "java1234");
			
			String sql = "";
			
			if(toMinute > fromMinute) { // between ... and ... 필요
				if(rating == null || rating.length == 5) {
					sql = "SELECT * FROM film WHERE title LIKE ?"
							+ " AND release_year LIKE ?"
							+ " AND length BETWEEN ? AND ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1,"%"+searchTitle+"%");
					stmt.setString(2,"%"+releaseYear+"%");
					stmt.setInt(3, fromMinute);
					stmt.setInt(4, toMinute);
				} else if(rating.length == 4) {
					sql = "SELECT * FROM film WHERE title LIKE ? AND rating IN(?, ?, ?, ?)"
							+ " AND release_year LIKE ?"
							+ " AND length BETWEEN ? AND ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1,"%"+searchTitle+"%");
					stmt.setString(2, rating[0]);
					stmt.setString(3, rating[1]);
					stmt.setString(4, rating[2]);
					stmt.setString(5, rating[3]);
					stmt.setString(6,"%"+releaseYear+"%");
					stmt.setInt(7, fromMinute);
					stmt.setInt(8, toMinute);
				} else if(rating.length == 3) {
					sql = "SELECT * FROM film WHERE title LIKE ? AND rating IN(?, ?, ?)"
							+ " AND release_year LIKE ?"
							+ " AND length BETWEEN ? AND ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1,"%"+searchTitle+"%");
					stmt.setString(2, rating[0]);
					stmt.setString(3, rating[1]);
					stmt.setString(4, rating[2]);
					stmt.setString(5,"%"+releaseYear+"%");
					stmt.setInt(6, fromMinute);
					stmt.setInt(7, toMinute);
				} else if(rating.length == 2) {
					sql = "SELECT * FROM film WHERE title LIKE ? AND rating IN(?, ?)"
						+ " AND release_year LIKE ?"
						+ " AND length BETWEEN ? AND ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1,"%"+searchTitle+"%");
					stmt.setString(2, rating[0]);
					stmt.setString(3, rating[1]);
					stmt.setString(4,"%"+releaseYear+"%");
					stmt.setInt(5, fromMinute);
					stmt.setInt(6, toMinute);
				} else if(rating.length == 1) {
					sql = "SELECT * FROM film WHERE title LIKE ? AND rating IN(?)"
						+ " AND release_year LIKE ?"
						+ " AND length BETWEEN ? AND ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1,"%"+searchTitle+"%");
					stmt.setString(2, rating[0]);
					stmt.setString(3,"%"+releaseYear+"%");
					stmt.setInt(4, fromMinute);
					stmt.setInt(5, toMinute);
				} 
			} else { // between ... and ... 불필요
				if(rating == null || rating.length == 5) {
					sql = "SELECT * FROM film WHERE title LIKE ?"
						+ " AND release_year LIKE ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1,"%"+searchTitle+"%");
					stmt.setString(2,"%"+releaseYear+"%");
				} else if(rating.length == 4) {
					sql = "SELECT * FROM film WHERE title LIKE ?"
						+ " AND release_year LIKE ?"
						+ " AND rating IN(?, ?, ?, ?)";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1,"%"+searchTitle+"%");
					stmt.setString(2,"%"+releaseYear+"%");
					stmt.setString(3, rating[0]);
					stmt.setString(4, rating[1]);
					stmt.setString(5, rating[2]);
					stmt.setString(6, rating[3]);
				} else if(rating.length == 3) {
					sql = "SELECT * FROM film WHERE title LIKE ?"
						+ " AND release_year LIKE ?"
						+ " AND rating IN(?, ?, ?)";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1,"%"+searchTitle+"%");
					stmt.setString(2,"%"+releaseYear+"%");
					stmt.setString(3, rating[0]);
					stmt.setString(4, rating[1]);
					stmt.setString(5, rating[2]);
				} else if(rating.length == 2) {
					sql = "SELECT * FROM film WHERE title LIKE ?"
						+ " AND release_year LIKE ?"
						+ " AND rating IN(?, ?)";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1,"%"+searchTitle+"%");
					stmt.setString(2,"%"+releaseYear+"%");
					stmt.setString(3, rating[0]);
					stmt.setString(4, rating[1]);
				} else if(rating.length == 1) {
					sql = "SELECT * FROM film WHERE title LIKE ?"
						+ " AND release_year LIKE ?"
						+ " AND rating IN(?)";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1,"%"+searchTitle+"%");
					stmt.setString(2,"%"+releaseYear+"%");
					stmt.setString(3, rating[0]);
				} 
			}
			rs = stmt.executeQuery();
			while(rs.next()) {
				Film f = new Film();
				f.setFilmId(rs.getInt("film_id"));
				f.setReleaseYear(rs.getString("release_year"));
				f.setTitle(rs.getString("title"));
				f.setRating(rs.getString("rating"));
				f.setLength(rs.getInt("length"));
				list.add(f);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	// col : 컬럼명
	// sort : asc / desc
	public ArrayList<Film> selectFilmListBySearch(
			String col, String sort, String searchCol, String searchWord){
		ArrayList<Film> list = new ArrayList<Film>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila", "root", "java1234");
			String sql = "";
			
			if(searchCol == null || searchWord == null) { // 검색버튼 조회가 아닌경우
				System.out.println("검색버튼 X");
				sql = "SELECT film_id filmId, title, description, release_year releaseYear, language_id languageId, original_language_id OriginalLanguageId, rental_duration rentalDuration, rental_rate rentalRate, length, replacement_cost replacementCost, rating, special_features specialFeatures, last_update lastUpdate"
					+ "	FROM film"
					+ "	ORDER BY "+col+" "+sort;
				stmt = conn.prepareStatement(sql);
			} else {
				System.out.println("검색버튼 O");
				String whereCol = "";
				if(searchCol.equals("titleAndDescription")) {
					whereCol = "CONCAT(title,' ',description)";
				} else {
					whereCol = searchCol;
				}
				sql = "SELECT film_id filmId, title, description, release_year releaseYear, language_id languageId, original_language_id OriginalLanguageId, rental_duration rentalDuration, rental_rate rentalRate, length, replacement_cost replacementCost, rating, special_features specialFeatures, last_update lastUpdate"
						+ "	FROM film"
						+ " WHERE "+whereCol+" LIKE ?"
						+ "	ORDER BY "+col+" "+sort;
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+searchWord+"%");
			}

			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Film f = new Film();
				f.setFilmId(rs.getInt("filmId"));
				f.setTitle(rs.getString("title"));
				f.setDescription(rs.getString("description"));
				f.setReleaseYear(rs.getString("releaseYear"));
				f.setLanguageId(rs.getInt("languageId"));
				f.setOriginalLanguageId(rs.getInt("originalLanguageId"));
				f.setRentalDuration(rs.getInt("rentalDuration"));
				f.setRentalRate(rs.getDouble("rentalRate"));
				f.setLength(rs.getInt("length"));
				f.setReplacementCost(rs.getDouble("replacementCost"));
				f.setRating(rs.getString("rating"));
				f.setSpecialFeatures(rs.getString("specialFeatures"));
				f.setLastUpdate(rs.getString("lastUpdate"));
				list.add(f);
			}
		
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}