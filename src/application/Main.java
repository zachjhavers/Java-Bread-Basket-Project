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
	ListView<String> basketItemData = new ListView<String>();
	TextField searchBasketItemId = new TextField();
	Button searchBaskItemId = new Button("Search"); 
	
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
			root.add(searchBasketItemId, 1, 1);
			root.add(searchBasketItemId, 2, 1);
			searchBasketItemId.setOnAction(e -> {
				getBasketData();
			});
			root.add(basketItemData, 0, 2);
			GridPane.setColumnSpan(basketItemData, GridPane.REMAINING);
			basketItemData.setPrefHeight(100);
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
			
			int userBasketNum = Integer.parseInt(searchBasketItemId.getText());
			
			String q = "SELECT * FROM BB_BASKETITEM WHERE IDBASKETITEM = ?";
			
			PreparedStatement pst = con.prepareStatement(q);
			
			pst.setInt(1, userBasketNum);
			
			ResultSet rs = pst.executeQuery();
			
			
           ObservableList<String> basketItemList = FXCollections.observableList(new ArrayList<String>());
		   
           while(rs.next()) {
        	   
        	   int idBasketItem = rs.getInt(1);
        	   int idProduct = rs.getInt(2);
        	   int price = rs.getInt(3);
        	   int quantity = rs.getInt(4);
        	   int idBasket = rs.getInt(5);
        	   int option1 = rs.getInt(6);
        	   int option2 = rs.getInt(7);
        	   
        	   basketItemList.add("Basket Item ID: "+idBasketItem+"\t"+"Prodcut ID: "+idProduct+"\t"+"Price: "+price+"\t"+"Qauntity: "+quantity+"\t"+"Basket ID: "+idBasket+"\t"+"Option 1: "+option1+"\t"+"Option 2: "+option2);
           }
           
           basketItemData.setItems(basketItemList);

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
