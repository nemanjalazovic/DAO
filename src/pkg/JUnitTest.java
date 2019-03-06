package pkg;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

public class JUnitTest {
	AuthorDAO authorDao;
	Author author;
	


	@Before
	 public void setup() {
		authorDao = new AuthorDAO();
	
	}
	
	
	@Test
	public void testGetAuthor() {
		author =authorDao.getAuthor(4);
		Assert.assertEquals("Successfully fetched a single author from the table author","Emile Zola", author.getName());
	}
	
	
	@Test
	public void TestGetAllAuthors() {
	
		Assert.assertEquals("Successfully fetched a multiple authors from the table author",7, authorDao.getAllAuthors().size());
	}	

	@Test
	public void TestUpdateAuthor() {
		author =authorDao.getAuthor(3);
		author.setName("Lav Tolstoj");
		System.out.println(author.getName());
		Assert.assertEquals("Successfully update a single author from the table author",true, authorDao.updateAuthor(author));
	}
	
	
	@Test
	public void TestDeleteAuthor() {
		author =authorDao.getAuthor(3);
		System.out.println(author.getName());
		Assert.assertEquals("Successfully deleted a single author from the table author",true, authorDao.deleteAuthor(author.getId()));
	}	
	
	@Test
	public void TestInsertAuthor() {
		author=new Author();
		author.setId(8);
		author.setName("Jovan Ducic");
		Assert.assertEquals("Successfully insert a single author from the table author",true, authorDao.insertAuthor(author));
	}
	
	
	@Test
	public void TestCRUDAuthor() {
		//inserting the author 
		author = new Author();
		author.setId(11);
		author.setName("Fjodor Dostojevski");
		Assert.assertEquals("Successfully insert a single author from the table author",true, authorDao.insertAuthor(author));

		author = new Author();
		author.setId(10);
		author.setName("Herman Melvil");
		Assert.assertEquals("Successfully insert a single author from the table author",true, authorDao.insertAuthor(author));

		//fetching and updating the author
		author =authorDao.getAuthor(9);
		author.setName("Stiv Dostojevski");
		Assert.assertEquals("Successfully update a single author from the table author",true, authorDao.updateAuthor(author));
	
		//delete the author
		author =authorDao.getAuthor(11);
		Assert.assertEquals("Successfully deleted a single author from the table author",true, authorDao.deleteAuthor(author.getId()));
	
	}	
	
	
	

}