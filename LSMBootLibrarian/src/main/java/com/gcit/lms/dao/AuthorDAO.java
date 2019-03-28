package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.gcit.lms.model.Author;

@Component
public class AuthorDAO extends BaseDAO<Author> implements ResultSetExtractor<List<Author>>{

	public void createAuthor(Author author)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		template.update("insert into tbl_author (authorName) values(?)", new Object[] { author.getAuthorName() });
	}
	
	public void createBookAuthors(Integer bookId,Integer authorId)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		template.update("insert into tbl_book_authors values(?, ?)", new Object[] { bookId, authorId});
	}

	public void updateAuthor(Author author)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		template.update("update tbl_author set authorName = ? where authorId= ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void deleteAuthor(Author author)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		template.update("delete from tbl_author where authorId= ?", new Object[] { author.getAuthorId() });
	}

	public List<Author> readAllAuthors()
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return template.query("select * from tbl_author", this);
	}
	
	public List<Author> readAuthorsByName(String authorName)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return template.query("select * from tbl_author where authorName = ?", new Object[] {authorName}, this);
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException  {
		List<Author> authors = new ArrayList<Author>();
		while (rs.next()) {
			Author author = new Author();
			author.setAuthorName(rs.getString("authorName"));
			author.setAuthorId(rs.getInt("authorId"));
			authors.add(author);
		}
		return authors;
	}

}