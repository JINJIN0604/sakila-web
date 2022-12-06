package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.Film;

public class FilmDao {
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
