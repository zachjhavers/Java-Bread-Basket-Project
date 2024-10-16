package application;
	
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	
	//Scene
		Scene scene1, scene2, scene3, scene4, scene5, scene6;
	
	//Variables 
		static final String DATABASE_URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
		static final String USER_NAME = "COMP214_F22_er_70";

		static final String PASS = "password";

		ListView<String> basketItemData = new ListView<String>();
		ListView<String> prodDescList = new ListView<String>();
		ListView<String> prodList = new ListView<String>();
		ListView<String> addToBask = new ListView<String>();
		ListView<String> shipList = new ListView<String>();
		ListView<String> shopperTotalList = new ListView<String>();
		TextField searchBasketItemId = new TextField();
		TextField prodId = new TextField();
		TextField prodDesc = new TextField();
		TextField addProdDesc = new TextField();
		TextField prodName = new TextField();
		TextField prodImg = new TextField();
		TextField prodPrice = new TextField();
		TextField prodAct = new TextField();
		TextField prodIdBox = new TextField();
		TextField prodPriceBox = new TextField();
		TextField prodQauntBox = new TextField();
		TextField baskIdBox = new TextField();
		TextField sizeCodBox = new TextField();
		TextField formCodBox = new TextField();
		TextField prodIdSaleBox = new TextField();
		TextField salDatePick = new TextField();
		TextField orderLocation = new TextField();
		TextField orderSubtotal = new TextField();
		TextField datePick = new TextField();
		TextField shipperComp = new TextField();
		TextField shipNum = new TextField();
		TextField idBasket = new TextField();
		TextField prodIdStock = new TextField();
		Button checkStockBut = new Button("Check Stock");
		Button backButton1 = new Button("Back");
		Button backButton2 = new Button("Back");
		Button backButton3 = new Button("Back");
		Button backButton4 = new Button("Back");
		Button backButton5 = new Button("Back");
		Button searchBaskItemId = new Button("Search");
		Button changeProdDescBut = new Button("Edit");
		Button addProduct = new Button("Add");
		Button goToProdPage = new Button("Products");
		Button goToOrderPage = new Button("Orders");
		Button goToBasketPage = new Button("Basket");
		Button addToBasket = new Button("Add To Basket");
		Button salePage = new Button("Check Sales");
		Button searchSale = new Button("Search Sales");
		Button calcTax = new Button("Calculate");
		Button shipStatus = new Button("Add Shipping");
		Button shopperTotalBut = new Button("Shopper Total Spending");
		Button popShopList = new Button("Shopper Totals");
		String pattern = "YY/MM/DD";
		DateFormat df = new SimpleDateFormat(pattern);
	
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
			//Button For Order Page
				root.add(goToOrderPage, 0, 1);
				goToOrderPage.setOnAction(e-> primaryStage.setScene(scene3));
				goToOrderPage.setPrefHeight(100);
				goToOrderPage.setPrefWidth(450);
			//Button For Basket Page
				root.add(goToBasketPage, 0, 2);
				goToBasketPage.setOnAction(e-> primaryStage.setScene(scene4));
				goToBasketPage.setPrefHeight(100);
				goToBasketPage.setPrefWidth(450);
			//Button For Sale Page
				root.add(salePage, 0, 3);
				salePage.setOnAction(e-> primaryStage.setScene(scene5));
				salePage.setPrefHeight(100);
				salePage.setPrefWidth(450);
			//Button For Sale Page
				root.add(shopperTotalBut, 0, 4);
				shopperTotalBut.setOnAction(e-> primaryStage.setScene(scene6));
				shopperTotalBut.setPrefHeight(100);
				shopperTotalBut.setPrefWidth(450);
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
				root2.add(backButton1, 2, 1);
				backButton1.setOnAction(e->{
					primaryStage.setScene(scene1);
				});
			//Orders page 
				//Scene3
					GridPane root3 = new GridPane();
					scene3 = new Scene(root3,400, 700);
					root3.setPadding(new Insets(25,25,25,25));
					root3.setHgap(20);
					root3.setVgap(30);
			//Sales tax calculation
					Label labelState = new Label("State: ");
					root3.add(labelState, 0, 0);
					root3.add(orderLocation, 1, 0);
					Label LabelSubtotal = new Label("Subtotal: ");
					root3.add(LabelSubtotal,0,1);
					root3.add(orderSubtotal, 1,1);
					root3.add(calcTax, 1,2);
					calcTax.setPrefWidth(200);
					calcTax.setOnAction(e -> {
						taxCalculation();
				});
			//Order Status
					Label IdBasket = new Label("Basket ID: ");
					root3.add(IdBasket, 0, 3);
					root3.add(idBasket, 1, 3);
					Label date = new Label("Date: ");
					root3.add(date, 0, 4);
					root3.add(datePick, 1, 4);
					Label shipper = new Label("Shipper: ");
					root3.add(shipper, 0, 5);
					root3.add(shipperComp, 1, 5);
					Label shipNumber = new Label("Shipping Number:");
					root3.add(shipNumber, 0, 6);
					root3.add(shipNum, 1, 6);
					root3.add(shipStatus, 1,7);
					shipStatus.setPrefWidth(200);
					shipStatus.setOnAction(e->{
						updateOrderStat();
					});
					root3.add(shipList, 0, 8);
					GridPane.setColumnSpan(shipList, GridPane.REMAINING);
					shipList.setPrefHeight(100);
				//Add Back Button
					root3.add(backButton3, 1, 9);
					backButton3.setPrefWidth(200);
					backButton3.setOnAction(e->{
						primaryStage.setScene(scene1);
					});
			//Basket Page
				//Scene 4
					GridPane root4 = new GridPane();
					scene4 = new Scene(root4,450, 800);
					root4.setPadding(new Insets(25,25,25,25));
					root4.setHgap(20);
					root4.setVgap(20);	
				//Basket Item Search
					Label basketItemSearchLabel = new Label("Basket Item Search");
					root4.add(basketItemSearchLabel, 0, 0);
					Label basketIdBox = new Label("Basket Item ID:");
					root4.add(basketIdBox, 0, 1);
					root4.add(searchBasketItemId, 1, 1);
					root4.add(searchBaskItemId, 2, 1);
					searchBaskItemId.setOnAction(e -> {
						getBasketItemData();
					});
					root4.add(basketItemData, 0, 2);
					GridPane.setColumnSpan(basketItemData, GridPane.REMAINING);
					basketItemData.setPrefHeight(100);
				//Add Item To Basket
					Label addBasketLabel = new Label("Add Items To Basket");
					root4.add(addBasketLabel, 0, 3);
					Label productIdLabel = new Label("Product ID: ");
					root4.add(productIdLabel, 0, 4);
					root4.add(prodIdBox, 1, 4);
					Label productPriceLabel = new Label("Product Price: ");
					root4.add(productPriceLabel, 0, 5);
					root4.add(prodPriceBox, 1, 5);
					Label productQuantLabel = new Label("Product Quantity: ");
					root4.add(productQuantLabel, 0, 6);
					root4.add(prodQauntBox, 1, 6);
					Label baskIdLabel = new Label("Basket ID: ");
					root4.add(baskIdLabel, 0, 7);
					root4.add(baskIdBox, 1, 7);
					Label sizCodeLabel = new Label("Size Code: ");
					root4.add(sizCodeLabel, 0, 8);
					root4.add(sizeCodBox, 1, 8);
					Label formCodeLabel = new Label("Form Code: ");
					root4.add(formCodeLabel, 0, 9);
					root4.add(formCodBox, 1, 9);
					root4.add(addToBasket, 0, 10);
					addToBasket.setOnAction(e->{
						addNewBasketItem();
					});
					root4.add(addToBask, 0, 11);
					GridPane.setColumnSpan(addToBask, GridPane.REMAINING);
					addToBask.setPrefHeight(100);
				//Check Stock
					Label checkStockLabel = new Label("Check Stock");
					root4.add(checkStockLabel, 0, 12);
					Label prodIdStocLab = new Label("Product ID: ");
					root4.add(prodIdStocLab, 0, 13);
					root4.add(prodIdStock, 1, 13);
					root4.add(checkStockBut, 2, 13);
					checkStockBut.setOnAction(e->{
						checkStock();
					});
				//Add Back Button
					root4.add(backButton4, 0, 14);
					backButton4.setOnAction(e->{
						primaryStage.setScene(scene1);
					});
			//Sale Page
				//Scene 5
					GridPane root5 = new GridPane();
					scene5 = new Scene(root5,450,300);
					root5.setPadding(new Insets(25,25,25,25));
					root5.setHgap(20);
					root5.setVgap(20);
					Label prodIdSaleLabel = new Label("Enter Product ID: ");
					root5.add(prodIdSaleLabel, 0, 0);
					root5.add(prodIdSaleBox, 1, 0);
					Label prodSaleDateLab = new Label("Enter Date: ");
					root5.add(prodSaleDateLab, 0, 1);
					root5.add(salDatePick, 1, 1);
					root5.add(searchSale, 2, 1);
					searchSale.setOnAction(e->{
						calculateSalesProduct();
					});
				//Add Back Button
					root5.add(backButton5, 2, 2);
					backButton5.setOnAction(e->{
						primaryStage.setScene(scene1);
					});
			//Scene 6
				//Shopper Total Spending
					GridPane root6 = new GridPane();
					scene6 = new Scene(root6,450,300);
					root6.setPadding(new Insets(25,25,25,25));
					root6.setHgap(20);
					root6.setVgap(20);
					root6.add(shopperTotalList, 0, 0);
					GridPane.setColumnSpan(shopperTotalList, GridPane.REMAINING);
					shopperTotalList.setPrefHeight(100);
					root6.add(popShopList, 0, 1);
					popShopList.setOnAction(e->{
						getCustomerTotalPurchase();
					});
			primaryStage.setScene(scene1);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void taxCalculation()
	
	{
		String call =  "DECLARE "
					+ " v_totalamt DECIMAL(4,2); "
					+ " num integer := 1000; "
					+ " BEGIN "
					+ " TAX_COST_SP(?,?, v_totalamt); "
					+ " DBMS_OUTPUT.PUT_LINE('The tax amount is $'|| to_char(v_totalamt, '0.00'));"
					+ " DBMS_OUTPUT.GET_LINES(?, num); "
					+ " END; ";

		try
		{
			Class.forName("oracle.jdbc.OracleDriver");

			Connection con = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASS);

			Statement s = con.createStatement();
			s.executeUpdate("BEGIN DBMS_OUTPUT.ENABLE(); END;");

			String location = orderLocation.getText();
			int subtotal = Integer.parseInt(orderSubtotal.getText());


			CallableStatement cstmt = con.prepareCall(call);

			cstmt.setString(1, location);
			cstmt.setInt(2, subtotal);

			cstmt.registerOutParameter(3, Types.ARRAY, "DBMSOUTPUT_LINESARRAY");
			cstmt.execute();

			Array array = null;

			try
			{
				array = cstmt.getArray(3);
				JOptionPane.showInputDialog(Arrays.asList((Object[]) array.getArray()));
			}
			finally
			{
				if (array != null)
					array.free();
			}
			
			
			cstmt.close();

			s.executeUpdate("begin dbms_output.disable(); end;");

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
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
		
		String call = ("{CALL prod_desc_change(?, ?)}");
		
		try 
		
		{
			
			Class.forName("oracle.jdbc.OracleDriver");

			Connection con = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASS);

			int userProdId = Integer.parseInt(prodId.getText());
			String userProdDesc = prodDesc.getText();
			
			
			CallableStatement cstmt = con.prepareCall(call);
			
			cstmt.setInt(1, userProdId);
			cstmt.setString(2, userProdDesc);
			
			cstmt.execute();
			
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
		
		String call = ("{CALL PROD_ADD_SP (?, ?, ?, ?, ?)}");
		
		try 
		
		{
			
			Class.forName("oracle.jdbc.OracleDriver");

			Connection con = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASS);
			
			String userProdName = prodName.getText();
			String userProdDesc = prodDesc.getText();
			String userProdImg = prodImg.getText();
			double userProdPric = Double.parseDouble(prodPrice.getText());
			int userProdAct = Integer.parseInt(prodAct.getText());
			
			CallableStatement cstmt = con.prepareCall(call);

			
			cstmt.setString(1, userProdName);
			cstmt.setString(2, userProdDesc);
			cstmt.setString(3, userProdImg);
			cstmt.setDouble(4, userProdPric);
			cstmt.setInt(5, userProdAct);
			
			cstmt.execute();
			
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
	
	private void addNewBasketItem() {
		
		String call = ("{CALL BASKET_ADD_SP (?, ?, ?, ?, ?, ?)}");
		
		try 
		
		{
			
			Class.forName("oracle.jdbc.OracleDriver");

			Connection con = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASS);
			
			double productId = Double.parseDouble(prodIdBox.getText());
			double productPrice = Double.parseDouble(prodPriceBox.getText());
			double productQuant = Double.parseDouble(prodQauntBox.getText());
			double baskId = Double.parseDouble(baskIdBox.getText());
			double sizeCod = Double.parseDouble(sizeCodBox.getText());
			double FormCod = Double.parseDouble(formCodBox.getText());
			
			
			CallableStatement cstmt = con.prepareCall(call);
			
			
			cstmt.setDouble(1, productId);
			cstmt.setDouble(2, productPrice);
			cstmt.setDouble(3, productQuant);
			cstmt.setDouble(4, baskId);
			cstmt.setDouble(5, sizeCod);
			cstmt.setDouble(6, FormCod);
			
			cstmt.execute();
			
			getNewBasketItemData();
						
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void getNewBasketItemData() {
		
		try 
		
		{
			
			Class.forName("oracle.jdbc.OracleDriver");

			Connection con = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASS);


			String q = "SELECT IDPRODUCT, PRICE, QUANTITY, IDBASKET FROM BB_BASKETITEM";
			

			Statement st = con.createStatement();

			
			ResultSet rs = st.executeQuery(q);
			
			
	           ObservableList<String> basketList = FXCollections.observableList(new ArrayList<String>());
			   
	           while(rs.next()) {
	        	   

	        	   double idProduct = rs.getDouble(1);
	        	   double price = rs.getDouble(2);
	        	   double quantity = rs.getDouble(3);
	        	   double idBasket = rs.getDouble(4);

	        	   
	        	   basketList.add("Prodcut ID: "+idProduct+"\t"+"Price: "+price+"\t"+"Qauntity: "+quantity+"\t"+"Basket ID: "+idBasket);
	           }
	           
	           addToBask.setItems(basketList);

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void calculateSalesProduct() {
		
		
		String call = "DECLARE "
					+ " sales VARCHAR(20); "
					+ " num integer := 1000; "
					+ " BEGIN "
					+ " sales := CK_SALE_SF(?, ?); "
					+ " DBMS_OUTPUT.PUT_LINE(sales); "
					+ " DBMS_OUTPUT.GET_LINES(?, num);"
					+ " END;";
		
		try 
		
		{
			
			Class.forName("oracle.jdbc.OracleDriver");

			Connection con = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASS);
			
			Statement s = con.createStatement();
			s.executeUpdate("BEGIN DBMS_OUTPUT.ENABLE(); END;");
			
			int userProdId = Integer.parseInt(prodIdSaleBox.getText());
			String userProdDate = salDatePick.getText();
			
			
			CallableStatement cstmt = con.prepareCall(call);

			cstmt.setInt(1, userProdId);
			cstmt.setString(2, userProdDate);

			
			cstmt.registerOutParameter(3, Types.ARRAY, "DBMSOUTPUT_LINESARRAY");
			cstmt.execute();
			
            Array array = null;
            try 
            {
                array = cstmt.getArray(3);
                JOptionPane.showInputDialog(Arrays.asList((Object[]) array.getArray()));
                
            }
            finally 
            {
                if (array != null)
                    array.free();
            }
			
			cstmt.close();
			
		    s.executeUpdate("begin dbms_output.disable(); end;");

						
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void checkStock() {
		
		
		String call = "DECLARE "
				    + " num integer := 1000; "
				    + " BEGIN "
					+ " check_basket(?);"
					+ " DBMS_OUTPUT.GET_LINES(?, num);"
					+ " END;";
		
		try 
		
		{
			
			Class.forName("oracle.jdbc.OracleDriver");

			Connection con = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASS);
			
			Statement s = con.createStatement();
			s.executeUpdate("BEGIN DBMS_OUTPUT.ENABLE(); END;");
			
			int userProdIdStoc = Integer.parseInt(prodIdStock.getText());


			CallableStatement cstmt = con.prepareCall(call);

			cstmt.setInt(1, userProdIdStoc);

			
			cstmt.registerOutParameter(2, Types.ARRAY, "DBMSOUTPUT_LINESARRAY");
			cstmt.execute();
			
            Array array = null;
            try 
            {
                array = cstmt.getArray(2);
                JOptionPane.showInputDialog(Arrays.asList((Object[]) array.getArray()));
                
            }
            finally 
            {
                if (array != null)
                    array.free();
            }
			
			cstmt.close();
			
		    s.executeUpdate("begin dbms_output.disable(); end;");

						
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void updateOrderStat() {
		
		
		String call = ("{CALL STATUS_SHIP_SP (?, ?, ?, ?)}");
		
		try 
		
		{
			
			Class.forName("oracle.jdbc.OracleDriver");

			Connection con = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASS);
			
			
			int basketId = Integer.parseInt(idBasket.getText());
			String date = datePick.getText();
			String company = shipperComp.getText();
			String num = shipNum.getText();


			CallableStatement cstmt = con.prepareCall(call);

			cstmt.setInt(1, basketId);
			cstmt.setString(2, date);
			cstmt.setString(3, company);
			cstmt.setString(4, num);
			
			cstmt.execute();
				
			cstmt.close();
			
			getBasketStatus();


						
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void getBasketStatus() {
		
		try 
		
		{
			
			Class.forName("oracle.jdbc.OracleDriver");

			Connection con = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASS);


			String q = "SELECT * FROM BB_BASKETSTATUS";
			

			Statement st = con.createStatement();

			
			ResultSet rs = st.executeQuery(q);
			
			
	           ObservableList<String> shipStatList = FXCollections.observableList(new ArrayList<String>());
			   
	           while(rs.next()) {
	        	   

	        	   int idStatus = rs.getInt(1);
	        	   int idBasket = rs.getInt(2);
	        	   int idStage = rs.getInt(3);
	        	   Date dtStage =rs.getDate(4);
	        	   String notes = rs.getString(5);
	        	   String shipper = rs.getString(6);
	        	   String shippingNum = rs.getString(7);

	        	   
	        	   shipStatList.add("ID Status: "+idStatus+"\t"+"Basket ID: "+idBasket+"\t"+"ID Stage: "+idStage+"\t"+"Date: "+dtStage+"\t"+"Notes: "+notes+"\t"+"Shipping Company: "+shipper+"\t"+"Shipping Number: "+shippingNum);
	           }
	           
	           shipList.setItems(shipStatList);

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getCustomerTotalPurchase() {
		
		try 
		
		{
			
			Class.forName("oracle.jdbc.OracleDriver");

			Connection con = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASS);


			String q = " SELECT idshopper,tot_purch_sf(idshopper) "
					 + " FROM bb_shopper ";
			

			Statement st = con.createStatement();

			
			ResultSet rs = st.executeQuery(q);
			
			
	           ObservableList<String> shopperTot = FXCollections.observableList(new ArrayList<String>());
			   
	           while(rs.next()) {
	        	   

	        	   int shopperId = rs.getInt(1);
	        	   int shopperTotal = rs.getInt(2);

	        	   
	        	   shopperTot.add("Shopper ID "+shopperId+"\t"+"Total Spending: "+shopperTotal);
	           }
	           
	           shopperTotalList.setItems(shopperTot);

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	
	public static void main(String[] args) {
		launch(args);
	}
}
