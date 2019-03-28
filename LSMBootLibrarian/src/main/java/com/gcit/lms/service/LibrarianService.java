package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.model.BookCopies;
import com.gcit.lms.model.LibraryBranch;

@RestController
@RequestMapping("/librarian")
public class LibrarianService {
	
	@Autowired
	LibraryBranchDAO ldao;
	
	@Autowired
	BookCopiesDAO cdao;
	
	@Autowired
	BookDAO bdao;
	
	@GetMapping(value="/initBranch", produces = "application/json") 
	public LibraryBranch initBranch() throws SQLException {
		return new LibraryBranch();
	}
	
	@GetMapping(value="/readAllBranches", produces = "application/json")
	public List<LibraryBranch> readAllBranches() throws SQLException {
		List<LibraryBranch> branches = new ArrayList<LibraryBranch>();
		try {
			branches = ldao.readAllBranches();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		return branches;
	}
	
	@GetMapping(value="/readAllBranchesByName", produces = "application/json")
	public LibraryBranch readBranchByPK(@RequestParam Integer branchId) throws SQLException {
		LibraryBranch branch = new LibraryBranch();
		try {
			branch = ldao.readBranchsByPK(branchId);
			branch.setBooks(bdao.getAvailableBookFromBranch(branchId));
			branch.setBookCopies(cdao.readAllBookCopiesByBranch(branch));
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		return branch;
	}
	
	@Transactional
	@PostMapping(value="/updateBranch",produces = "application/json", consumes="application/json")
	public void updateBranch(@RequestBody LibraryBranch branch) throws SQLException {
		try {
			ldao.updateBranch(branch);
			System.out.println("Successfull operation!");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			System.out.println("Something went wrong, sorry!");
			e.printStackTrace();
		} 
	}
	
	@PostMapping(value="/listCopies", produces = "application/json", consumes = "application/json" )
	public List<BookCopies> listCopies(@RequestBody LibraryBranch branch) throws SQLException {
		List<BookCopies> list = new ArrayList<>();
		try {
			list=cdao.readAllBookCopiesByBranch(branch);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			System.out.println("Something went wrong, sorry!");
			e.printStackTrace();
		}
		return list;
	}
	
	@Transactional
	@PostMapping(value="/updateCopies",produces = "application/json", consumes="application/json")
	public void updateCopies(@RequestBody BookCopies bookCopies, LibraryBranch branch) throws SQLException {
		try {
			cdao.updateBookCopies(bookCopies, branch);
			System.out.println("Successfull operation!");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			System.out.println("Something went wrong, sorry!");
			e.printStackTrace();
		} 
	}
}
