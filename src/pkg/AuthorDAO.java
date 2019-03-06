package pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.postgresql.Driver;

public class AuthorDAO implements IAuthorDAO{
	
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public static final String URL = "jdbc:postgresql://127.0.0.1:5432/authors_books_postgresql";
   
    /**
     * Get a connection to database
     * @return Connection object
     */
    public static Connection getConnection()
    {
      try {
    	  
//firstly you have to make an class in which you save data about user_name and password  to make a connection to DB  	  
    	  
 // first way to make connection
    	  
    	  USER_PASS user_pass =  new USER_PASS();
    	  
// Another way to make a connection
	  
  		/*Scanner scanner = new Scanner(System.in);
  		
  		
  		System.out.println("Please enter user_name to connect DB: ");
  		String USER= scanner.nextLine();
  		
  		System.out.println("Please enter password to connect DB: ");
  		String PASS= scanner.nextLine();*/
  		     	  
          DriverManager.registerDriver(new Driver());
          return DriverManager.getConnection(URL, user_pass.getUSER(), user_pass.getPASS());
      } catch (SQLException ex) {
          throw new RuntimeException("Error connecting to the database", ex);
      }
    }
    
    @Override
	public Author getAuthor(int id) {
		Connection connection = AuthorDAO.getConnection();
        try {
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM authors WHERE id=" + id);

            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                Author author = new Author();
                author.setId( rs.getInt("id") );
                author.setName( rs.getString("name") );
                return author;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    return null;
	}

	@Override
	public ArrayList<Author> getAllAuthors() {
		AuthorDAO connector = new AuthorDAO();
        Connection connection = connector.getConnection();
        ArrayList<Author> list = new ArrayList<Author>();
        
        try {
        	
          
            
        	PreparedStatement stmt = connection.prepareStatement("SELECT * FROM authors");

            ResultSet rs = stmt.executeQuery();
           
            while(rs.next())
            {
            	 Author author = new Author();
                 author.setId( rs.getInt("id") );
                 author.setName( rs.getString("name") );
                 list.add(author);
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
	}

	@Override
	public boolean insertAuthor(Author author) {
		AuthorDAO connector = new AuthorDAO();
        Connection connection = connector.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO authors VALUES (?, ?)");
            ps.setInt(1, author.getId());
            ps.setString(2, author.getName());
         
            int i = ps.executeUpdate();
          if(i == 1) {
            return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
	}

	@Override
	public boolean updateAuthor(Author author) {
		AuthorDAO connector = new AuthorDAO();
        Connection connection = connector.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE authors SET name=? WHERE id=?");
            ps.setString(1, author.getName());
            ps.setInt(2, author.getId());
           
            int i = ps.executeUpdate();
          if(i == 1) {
        return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
	}

	@Override
	public boolean deleteAuthor(int id) {
		AuthorDAO connector = new AuthorDAO();
        Connection connection = connector.getConnection();
        try {
        	PreparedStatement stmt = connection.prepareStatement("DELETE FROM authors WHERE id=" + id);
            int i = stmt.executeUpdate();
          if(i == 1) {
        return true;
          }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
	}
	
	@Override
	public String GetVersion() throws SQLException {
		Connection connection = AuthorDAO.getConnection();
		Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT VERSION()");
        try{

        	if (rs.next()) {
        System.out.println(rs.getString(1));
        	}

        }catch (SQLException ex) {

        	Logger lgr = Logger.getLogger(AuthorDAO.class.getName());
        	lgr.log(Level.SEVERE, ex.getMessage(), ex);
        	System.out.println("error");
        	}	
        return null;
	}

	@Override
	public void writeMultipleRows(String query) throws SQLException {
		AuthorDAO connector = new AuthorDAO();
        Connection connection = connector.getConnection();
        PreparedStatement pst = connection.prepareStatement(query); 

            boolean isResult = pst.execute();

            do {
                try (ResultSet rs = pst.getResultSet()) {

                    while (rs.next()) {
                    
                        System.out.print(rs.getInt(1));
                        System.out.print(": ");
                        System.out.println(rs.getString(2));
                    }

                    isResult = pst.getMoreResults();
                
                
            } catch (SQLException ex) {

                Logger lgr = Logger.getLogger(
                		AuthorDAO.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
            } while (isResult);

        
	}
	
	@Override
	public void writeMetaData(String query) throws SQLException {
		AuthorDAO connector = new AuthorDAO();
        Connection connection = connector.getConnection();
        PreparedStatement pst = connection.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        try{

        ResultSetMetaData meta = rs.getMetaData();

        String colname1 = meta.getColumnName(1);
        String colname2 = meta.getColumnName(2);

        Formatter fmt1 = new Formatter();
        fmt1.format("%-21s%s", colname1, colname2);
        System.out.println(fmt1);

        while (rs.next()) {
            
            Formatter fmt2 = new Formatter();
            fmt2.format("%-21s", rs.getString(1));
            System.out.print(fmt2);
            System.out.println(rs.getString(2));
        }

    } catch (SQLException ex) {
        
        Logger lgr = Logger.getLogger(
        		AuthorDAO.class.getName());
        lgr.log(Level.SEVERE, ex.getMessage(), ex);
    }
        
	}
	
	

	
	
    
    public static void main(String[] args) throws SQLException {
        AuthorDAO dao = new AuthorDAO();
        
       // System.out.println(dao.getAuthor(3));
        
        //System.out.println(dao.getAllAuthors());
        
        Author a1 = new Author(8,"Mark Tven");
       // dao.insertAuthor(a1);
                
        
        Author a2 = new Author(8,"Lav Tolstoj");
       // dao.updateAuthor(a2);
        
        
       // dao.deleteAuthor(8);

        
        //dao.GetVersion();
        
        String query1= "SELECT id, name FROM authors where id=1;"+
                "SELECT id, name FROM authors Where id=2;"+
                "SELECT id, name FROM authors WHERE id=3";
        
        //dao.writeMultipleRows(query1);
        
        String query2 = "SELECT name, title FROM authors, books WHERE authors.id= books.id";
        //dao.writeMetaData(query2);
        
        
        //System.out.println(dao.getAllAuthors());

        
        
        
        
        
        
    }








	

}
