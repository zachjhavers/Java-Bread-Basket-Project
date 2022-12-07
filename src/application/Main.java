package application;
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	
	//Variables 
	static final String DATABASE_URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
	static final String USER_NAME = "COMP214_F22_er_70";
	static final String PASS = "password";
	ListView<String> basketData = new ListView<String>();
	TextField searchBasketId = new TextField();
	Button searchBaskId = new Button("Search"); 
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("BB Brewery Application");
			GridPane root = new GridPane();
			Scene scene = new Scene(root,550,1100);
			root.setPadding(new Insets(25,25,25,25));
			root.setHgap(20);
			root.setVgap(20);
			Label basketIdBox = new Label("Basket ID:");
			root.add(basketIdBox, 0, 1);
			root.add(searchBasketId, 1, 1);
			root.add(searchBaskId, 2, 1);
			searchBaskId.setOnAction(e -> {
				getBasketData();
			});
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Method To Search Basket 
	private void getBasketData() {
		
		try 
		
		{
			
			Class.forName("oracle.jdbc.OracleDriver");

			Connection con = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASS);

			Statement st = con.createStatement();
			
			int userBasketNum = Integer.parseInt(searchBasketId.getText());
			
			String q = "SELECT * FROM BB_BASKETITEM WHERE IDBASKETITEM = ?";
			
			PreparedStatement pst = con.prepareStatement(q);
			
			pst.setInt(1, userBasketNum);
			
			ResultSet rs = st.executeQuery(q);
			
			
           ObservableList<String> basketList = FXCollections.observableList(new ArrayList<String>());
		   
           while(rs.next()) {
        	   
        	   int idBasketItem = rs.getInt(1);
        	   int idProduct = rs.getInt(2);
        	   int price = rs.getInt(3);
        	   int quantity = rs.getInt(4);
        	   int idBasket = rs.getInt(5);
        	   int option1 = rs.getInt(6);
        	   int option2 = rs.getInt(7);
        	   
        	   basketList.add(idBasketItem+"\t"+idProduct+"\t"+price+"\t"+quantity+"\t"+idBasket+"\t"+option1+"\t"+option2);
           }
           
           basketData.setItems(basketList);

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
