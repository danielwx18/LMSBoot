package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.gcit.lms.model.Genre;

@Component
public class GenreDAO extends BaseDAO<Genre> implements ResultSetExtractor<List<Genre>> {

	public void createGenre(Genre genre)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		template.update("insert into tbl_genre (genre_name) values(?)", new Object[] { genre.getGenre_name() });
	}

	public void createBookgenres(Integer bookId, Integer genre_id)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		template.update("insert into tbl_book_genres values(?, ?)", new Object[] { bookId, genre_id });
	}

	public void updateGenre(Genre genre)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		template.update("update tbl_genre set genre_name = ? where genre_id= ?",
				new Object[] { genre.getGenre_name(), genre.getGenre_id() });
	}

	public void deleteGenre(Genre genre)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		template.update("delete from tbl_genre where genre_id= ?", new Object[] { genre.getGenre_id() });
	}

	public List<Genre> readAllgenres()
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return template.query("select * from tbl_genre", this);
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<Genre>();
		while (rs.next()) {
			Genre genre = new Genre();
			genre.setGenre_name(rs.getString("genre_name"));
			genre.setGenre_id(rs.getInt("genre_id"));
			genres.add(genre);
		}
		return genres;
	}

}
