module BBBreweryApplication 

{
	requires javafx.graphics; 
	requires javafx.controls;
	requires java.sql;
	requires javafx.swt;
    requires java.sql.rowset;

    opens application to javafx.graphics, javafx.fxml;
}
