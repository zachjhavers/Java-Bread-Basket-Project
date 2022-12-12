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
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	
	//Scene
	Scene scene1, scene2, scene3, scene4;

	//Variables
	static final String DATABASE_URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
	//	static final String USER_NAME = "COMP214_F22_er_70";
	static final String USER_NAME = "COMP214_F22_er_41";
	static final String PASS = "password";
	ListView<String> basketItemData = new ListView<String>();
	ListView<String> prodDescList = new ListView<String>();
	ListView<String> prodList = new ListView<String>();
	ListView<String> orderTaxList = new ListView<String>();
	TextField searchBasketItemId = new TextField();
	TextField prodId = new TextField();
	TextField prodDesc = new TextField();
	TextField addProdDesc = new TextField();
	TextField prodName = new TextField();
	TextField prodImg = new TextField();
	TextField prodPrice = new TextField();
	TextField prodAct = new TextField();
	TextField orderLocation = new TextField();
	TextField orderSubtotal = new TextField();

	TextField idBasket = new TextField();
	DatePicker datePick = new DatePicker();
	TextField shipperComp = new TextField();
	TextField shipNum = new TextField();
	Button backButton = new Button("Back");
	Button searchBaskItemId = new Button("Search");
	Button changeProdDescBut = new Button("Edit");
	Button addProduct = new Button("Add");
	Button goToProdPage = new Button("Products");
	Button goToOrderPage = new Button("Orders");
	Button goToBasketPage = new Button("Basket");
	Button calcTax = new Button("Calculate");
	Button shipStatus = new Button("Add Shipping");
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//Scene1
			primaryStage.setTitle("BB Brewery Application");
			GridPane root = new GridPane();
			scene1 = new Scene(root,450,700);
			root.setPadding(new Insets(25,25,25,25));
			root.setHgap(20);
			root.setVgap(20);
			//Button For Products Page
			root.add(goToProdPage, 0, 0);
			goToProdPage.setOnAction(e -> primaryStage.setScene(scene2));
			goToProdPage.setPrefWidth(450);
			goToProdPage.setPrefHeight(100);
			root.add(goToOrderPage, 0, 1);
			goToOrderPage.setOnAction(e-> primaryStage.setScene(scene3));
			goToOrderPage.setPrefHeight(100);
			goToOrderPage.setPrefWidth(450);
			root.add(goToBasketPage, 0, 2);
			goToBasketPage.setOnAction(e-> primaryStage.setScene(scene4));
			goToBasketPage.setPrefHeight(100);
			goToBasketPage.setPrefWidth(450);
			//Basket Item Search
//				Label basketItemSearchLabel = new Label("Basket Item Search");
//				root.add(basketItemSearchLabel, 0, 0);
//				Label basketIdBox = new Label("Basket Item ID:");
//				root.add(basketIdBox, 0, 1);
//				root.add(searchBasketItemId, 1, 1);
//				root.add(searchBaskItemId, 2, 1);
//				searchBaskItemId.setOnAction(e -> {
//					getBasketItemData();
//				});
//				root.add(basketItemData, 0, 2);
//				GridPane.setColumnSpan(basketItemData, GridPane.REMAINING);
//				basketItemData.setPrefHeight(100);
			//Scene 2
			GridPane root2 = new GridPane();
			scene2 = new Scene(root2,450, 700);
			root2.setPadding(new Insets(25,25,25,25));
			root2.setHgap(20);
			root2.setVgap(20);	
			//Change Product Description
				Label labelChangeProd = new Label("Change Prodcut Description");
				root2.add(labelChangeProd, 0, 0);
				Label labelProdId = new Label("Product ID: ");
				root2.add(labelProdId, 0, 1);
				root2.add(prodId, 1, 1);
				Label labelProdDesc = new Label("Product Description: ");
				root2.add(labelProdDesc, 0, 2);
				root2.add(prodDesc, 1, 2);
				root2.add(changeProdDescBut, 2, 2);
				changeProdDescBut.setOnAction(e -> {
					changeProdDescData();
				});
				root2.add(prodDescList, 0, 3);
				GridPane.setColumnSpan(prodDescList, GridPane.REMAINING);
				prodDescList.setPrefHeight(100);
			//Add New Product
				Label labelAddProd = new Label("Add Product");
				root2.add(labelAddProd, 0, 7);
				Label labelProdName = new Label("Product Name: ");
				root2.add(labelProdName, 0, 8);
				root2.add(prodName, 1, 8);
				Label labelAddProdDesc = new Label("Product Description: ");
				root2.add(labelAddProdDesc, 0, 9);
				root2.add(addProdDesc, 1, 9);
				Label labelProdImg = new Label("Product Image: ");
				root2.add(labelProdImg, 0, 10);
				root2.add(prodImg, 1, 10);
				Label labelProdPric = new Label("Product Price: ");
				root2.add(labelProdPric, 0, 11);
				root2.add(prodPrice, 1, 11);
				Label labelProdAct = new Label("Product Active: ");
				root2.add(labelProdAct, 0, 12);
				root2.add(prodAct, 1, 12);
				root2.add(addProduct, 2, 12);
				addProduct.setOnAction(e -> {
					addNewProduct();
				});
				root2.add(prodList, 0, 13);
				GridPane.setColumnSpan(prodList, GridPane.REMAINING);
				prodList.setPrefHeight(100);
				root2.add(backButton, 0, 0);
			//Orders page scene3
			GridPane root3 = new GridPane();
			scene3 = new Scene(root3,650, 700);
			root3.setPadding(new Insets(25,25,25,25));
			root3.setHgap(20);
			root3.setVgap(30);

			//Sales tax calculation
			Label labelState = new Label("State: ");
			root3.add(labelState, 0, 0);
			root3.add(orderLocation, 8, 0);
			Label LabelSubtotal = new Label("Subtotal: ");
			root3.add(LabelSubtotal,0,1);
			root3.add(orderSubtotal, 8,1);
			root3.add(calcTax, 9,3);

			root3.add(backButton, 0, 13);
			root3.add(orderTaxList, 0, 2);
			GridPane.setColumnSpan(orderTaxList, GridPane.REMAINING);
			orderTaxList.setPrefHeight(100);
			backButton.setOnAction(e -> primaryStage.setScene(scene1));
			calcTax.setOnAction(e -> {
//				taxCalculation();
			});

			//Order Status
			Label IdBasket = new Label("ID BASKET: ");
			root3.add(IdBasket, 0, 5);
			root3.add(idBasket, 8, 5);
			Label date = new Label("Date: ");
			root3.add(date, 0, 6);
			root3.add(datePick, 8, 6);
			Label shipper = new Label("Shipper: ");
			root3.add(shipper, 0, 7);
			root3.add(shipperComp, 8, 7);
			Label shipNumber = new Label("Shipping Num:");
			root3.add(shipNumber, 0, 8);
			root3.add(shipNum, 8, 8);
			root3.add(shipStatus, 7,9);
			primaryStage.setScene(scene1);
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
