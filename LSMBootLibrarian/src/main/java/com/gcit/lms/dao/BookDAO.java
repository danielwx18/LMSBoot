package com.gcit.lms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.gcit.lms.model.Book;


@Component
public class BookDAO extends BaseDAO<Book> implements ResultSetExtractor<List<Book>> {

	public Book getBookByPK(Integer bookId)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<Book> books = template.query("select * from tbl_book where bookId = ?", new Object[] { bookId }, this);
		if (books != null && books.size() > 0) {
			return books.get(0);
		}
		return null;
	}

	public Integer createBookToGetPK(Book book)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(connection -> {
			PreparedStatement ps = connection.prepareStatement("insert into tbl_book (title) values(?) ", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, book.getTitle());
			return ps;
		}, keyHolder);
		return keyHolder.getKey().intValue();

	}

	// Used by BorrowerService.java
	public List<Book> getAvailableBookFromBranch(Integer branchId)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return template.query(
				"Select * from tbl_book where bookId in"
						+ " (Select bookId from (Select c.bookId, count(cardNo) as bookborrow, noOfCopies"
						+ " From tbl_book_copies as c" + " Left Join tbl_book_loans as l"
						+ " On l.bookId = c.bookId and l.branchId = c.branchId"
						+ " Where returnDate is Null and c.branchId = ?" + " Group by c.bookId, noOfCopies"
						+ " Having bookborrow < noOfCopies) as borrow1)" + " Order by bookId;",
				new Object[] { branchId }, this);
	}

	public void createBook(Book book)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		template.update("Insert Into tbl_book (title, pubid) value (?,?)",
				new Object[] { book.getTitle(), book.getPublisher().getPublisherId() });
	}

	public void updateBook(Book book)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		template.update("Update tbl_book set title = ?, pubId = ? where bookId = ?",
				new Object[] { book.getTitle(), book.getPublisher().getPublisherId(), book.getBookId() });
	}

	public void deleteBook(Book book)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		System.out.println("book:" + book.getBookId());
		template.update("Delete from tbl_book where bookId= ?", new Object[] { book.getBookId() });
	}

	public List<Book> readAllBooks()
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return template.query("select * from tbl_book", this);
	}

	public List<Book> readBooksWithCopies()
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return template.query(
				"select title, sum(noOfCopies) copies from tbl_book b join tbl_book_copies c on b.bookId=c.bookId\n"
						+ "group by title\n" + "order by b.bookId",
				this);
	}

	public List<Book> readBooksWithPublisher()
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return template.query(
				"select title, publisherName from tbl_book b join tbl_publisher p on b.pubId=p.publisherId", this);
	}

	public List<Book> readBooksWithGenres()
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return template.query(
				"select title, genre_name from tbl_book b join tbl_book_genres bg on b.bookId=bg.bookId join tbl_genre g on g.genre_id=bg.genre_id order by b.bookId",
				this);
	}

	public List<Book> readBooksWithAuthors()
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return template.query(
				"select title, authorName from tbl_book b join tbl_book_authors bg on b.bookId=bg.bookId join tbl_author g on g.authorId=bg.authorId order by b.bookId",
				this);
	}

	public List<Book> readBooksByName(String title)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return template.query("select * from tbl_book where title = ? order by bookId DESC", new Object[] { title },
				this);
	}

	public List<Book> readBooksByAuthorId(Integer authorId)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return template.query(
				"select * from tbl_book_authors ba, tbl_book b, tbl_author a\n"
						+ "where ba.authorId = a.authorId and a.authorId = ?\n" + "and b.bookId = ba.bookId",
				new Object[] { authorId }, this);
	}

	public List<Book> readBooksByGenreId(Integer genreId)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return template.query(
				"select * from tbl_book_genres ba, tbl_book b, tbl_genre a\n"
						+ "where ba.genreId = a.genreId and a.genreId = ?\n" + "and b.bookId = ba.bookId",
				new Object[] { genreId }, this);
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		while (rs.next()) {
			Book book = new Book();
			book.setTitle(rs.getString("title"));
			book.setBookId(rs.getInt("bookId"));
			books.add(book);
		}
		return books;
	}

}
