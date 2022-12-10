package application;
	
import java.sql.CallableStatement;
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
	ListView<String> prodDescList = new ListView<String>();
	ListView<String> prodList = new ListView<String>();
	TextField searchBasketItemId = new TextField();
	TextField prodId = new TextField();
	TextField prodDesc = new TextField();
	TextField addProdDesc = new TextField();
	TextField prodName = new TextField();
	TextField prodImg = new TextField();
	TextField prodPrice = new TextField();
	TextField prodAct = new TextField();
	Button searchBaskItemId = new Button("Search");
	Button changeProdDescBut = new Button("Edit");
	Button addProduct = new Button("Add");
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("BB Brewery Application");
			GridPane root = new GridPane();
			Scene scene = new Scene(root,550,1100);
			root.setPadding(new Insets(25,25,25,25));
			root.setHgap(20);
			root.setVgap(20);
			//Basket Item Search
				Label basketItemSearchLabel = new Label("Basket Item Search");
				root.add(basketItemSearchLabel, 0, 0);
				Label basketIdBox = new Label("Basket Item ID:");
				root.add(basketIdBox, 0, 1);
				root.add(searchBasketItemId, 1, 1);
				root.add(searchBaskItemId, 2, 1);
				searchBaskItemId.setOnAction(e -> {
					getBasketItemData();
				});
				root.add(basketItemData, 0, 2);
				GridPane.setColumnSpan(basketItemData, GridPane.REMAINING);
				basketItemData.setPrefHeight(100);
			//Change Product Description
				Label labelChangeProd = new Label("Change Prodcut Description");
				root.add(labelChangeProd, 0, 3);
				Label labelProdId = new Label("Product ID: ");
				root.add(labelProdId, 0, 4);
				root.add(prodId, 1, 4);
				Label labelProdDesc = new Label("Product Description: ");
				root.add(labelProdDesc, 0, 5);
				root.add(prodDesc, 1, 5);
				root.add(changeProdDescBut, 2, 5);
				changeProdDescBut.setOnAction(e -> {
					changeProdDescData();
				});
				root.add(prodDescList, 0, 6);
				GridPane.setColumnSpan(prodDescList, GridPane.REMAINING);
				prodDescList.setPrefHeight(100);
			//Add New Product
				Label labelAddProd = new Label("Add Product");
				root.add(labelAddProd, 0, 7);
				Label labelProdName = new Label("Product Name: ");
				root.add(labelProdName, 0, 8);
				root.add(prodName, 1, 8);
				Label labelAddProdDesc = new Label("Product Description: ");
				root.add(labelAddProdDesc, 0, 9);
				root.add(addProdDesc, 1, 9);
				Label labelProdImg = new Label("Product Image: ");
				root.add(labelProdImg, 0, 10);
				root.add(prodImg, 1, 10);
				Label labelProdPric = new Label("Product Price: ");
				root.add(labelProdPric, 0, 11);
				root.add(prodPrice, 1, 11);
				Label labelProdAct = new Label("Product Active: ");
				root.add(labelProdAct, 0, 12);
				root.add(prodAct, 1, 12);
				root.add(addProduct, 2, 12);
				addProduct.setOnAction(e -> {
					addNewProduct();
				});
				root.add(prodList, 0, 13);
				GridPane.setColumnSpan(prodList, GridPane.REMAINING);
				prodList.setPrefHeight(100);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Method To Search Basket 
	private void getBasketItemData() {
		
		try 
		
		{
			
			Class.forName("oracle.jdbc.OracleDriver");

			Connection con = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASS);
			
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
	
	private void changeProdDescData() {
		
		CallableStatement cstmt = null;
		
		try 
		
		{
			
			Class.forName("oracle.jdbc.OracleDriver");

			Connection con = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASS);

			int userProdId = Integer.parseInt(prodId.getText());
			String userProdDesc = prodDesc.getText();
			
			String q = "{EXECUTE prod_desc_change(?, ?)}";
			
			cstmt = con.prepareCall(q);
			
			cstmt.setInt(1, userProdId);
			cstmt.setString(2, userProdDesc);
			
			cstmt.close();
			
			getProductData();
			

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void getProductData() {
		
		try 
		
		{
			
			Class.forName("oracle.jdbc.OracleDriver");

			Connection con = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASS);


			String q = "SELECT IDPRODUCT, DESCRIPTION FROM BB_PRODUCT";
			

			Statement st = con.createStatement();

			
			ResultSet rs = st.executeQuery(q);
			
			
	           ObservableList<String> productList = FXCollections.observableList(new ArrayList<String>());
			   
	           while(rs.next()) {
	        	   
	        	   int idProduct = rs.getInt(1);
	        	   String prodDesc = rs.getString(2);
	        	   
	        	   productList.add("Product ID: "+idProduct+"\t"+"Prodcut Description: "+prodDesc);
	           }
	           
	           prodDescList.setItems(productList);

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void addNewProduct() {
		
		CallableStatement cstmt = null;
		
		try 
		
		{
			
			Class.forName("oracle.jdbc.OracleDriver");

			Connection con = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASS);
			
			String userProdName = prodName.getText();
			String userProdDesc = prodDesc.getText();
			String userProdImg = prodImg.getText();
			int userProdPric = Integer.parseInt(prodPrice.getText());
			int userProdAct = Integer.parseInt(prodAct.getText());
			
			
			String q = "{EXECUTE PROD_ADD_SP (?, ?, ?, ?, ?)}";
			
			cstmt = con.prepareCall(q);
			
			cstmt.setString(1, userProdName);
			cstmt.setString(2, userProdDesc);
			cstmt.setString(3, userProdImg);
			cstmt.setInt(4, userProdPric);
			cstmt.setInt(5, userProdAct);
			
			getNewProductData();
						
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void getNewProductData() {
		
		try 
		
		{
			
			Class.forName("oracle.jdbc.OracleDriver");

			Connection con = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASS);


			String q = "SELECT PRODUCTNAME, DESCRIPTION, PRODUCTIMAGE, PRICE, ACTIVE FROM BB_PRODUCT";
			

			Statement st = con.createStatement();

			
			ResultSet rs = st.executeQuery(q);
			
			
	           ObservableList<String> newProductList = FXCollections.observableList(new ArrayList<String>());
			   
	           while(rs.next()) {
	        	   
	        	   String prodName = rs.getString(1);
	        	   String prodDesc = rs.getString(2);
	        	   String prodImg = rs.getString(3);
	        	   int prodPrice = rs.getInt(4);
	        	   int prodAct = rs.getInt(5);
	        	   
	        	   newProductList.add("Product Name: "+prodName+"\t"+"Prodcut Description: "+prodDesc+"\t"+"Product Image: "+prodImg+"\t"+"Product Price: "+prodPrice+"\t"+"Product Active: "+prodAct);
	           }
	           
	           prodList.setItems(newProductList);

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	
	
	public static void main(String[] args) {
		launch(args);
	}
}
