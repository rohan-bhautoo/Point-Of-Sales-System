
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.stage.Modality;

/**
 * PointOfSales.java - The main class containing the GUI and all its features.
 *
 * @author Rohan
 * @version 1.0
 * @see Application
 */
public class PointOfSales extends Application {

    Stage window, buyWindow, cartWindow, continueBuy, receiptWindow, addWindow, removeWindow, searchWindow, sortWindow;
    TableView<Products> table;
    TextField nameInput, productIDInput, priceInput, weightInput, caloriesInput, quantityInput, removeIDField,
            searchNameField, searchIDField, buyNameField, buyIDField, buyAmountField, buyPriceField;
    TextInputDialog amountDialog, moneyDialog;
    TextArea textArea, aboutTextArea;
    ComboBox<String> categoryChoice, searchProducts;
    BufferedReader br;
    BufferedWriter bw;
    Scanner scanPrice;

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     *
     * The main() serves only as a fallback in case the application can not be launched through deployment artifacts, e.g., in IDEs with limited FX
     *
     * @param args A variable of type String[].
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start() method is called when the JavaFX application is started.
     *
     * The start() method takes a single parameter primaryStage,
     *
     * where all the visual parts of the JavaFX application are displayed.
     *
     * @param primaryStage A variable of type Stage.
     */
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Point Of Sales System");
        window.getIcons().add(new Image(getClass().getResourceAsStream("/Image/POS-icon.png")));

        window.setOnCloseRequest(e -> {
            /**
             * Consumes the setOnCloseRequest and goes to closeProgram method.
             *
             * Closing the program with x will work properly.
             */
            e.consume();
            closeProgram();
        });

        /* '_' is a shortcut for keyboard. ALT + F will open File Menu. */
        Menu fileMenu = new Menu("_File");
        Menu editMenu = new Menu("_Edit");
        Menu optionsMenu = new Menu("_Options");
        Menu helpMenu = new Menu("_Help");

        MenuItems(fileMenu, editMenu, optionsMenu, helpMenu);

        /*Menu Bar */
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu, optionsMenu, helpMenu);

        /* Welcome Image */
        Image welcomeImg = new Image(getClass().getResourceAsStream("/Image/welcomeLabel.png"));

        /* Label */
        Label Welcome = new Label();
        ImageView WelcomeImg = new ImageView(welcomeImg);
        WelcomeImg.setFitHeight(45);
        WelcomeImg.setFitWidth(45);
        Welcome.setGraphic(WelcomeImg);
        Welcome.setStyle("-fx-background-position: center; -fx-background-repeat: no-repeat; -fx-background-size: cover, auto;");
        Welcome.setPadding(new Insets(15, 10, 0, 10));
        Welcome.setFont(new Font(20.0));
        Welcome.setAlignment(Pos.TOP_CENTER);

        Label header = new Label("PICK 'N' BUY LTD");
        header.setPadding(new Insets(0, 10, 0, 10));
        header.setFont(new Font(20.0));
        header.setAlignment(Pos.TOP_CENTER);

        /* Middle Menu */
        VBox middleMenu = new VBox();
        middleMenu.setPadding(new Insets(0, 10, 0, 10));
        middleMenu.setSpacing(27);
        middleMenu.setStyle("-fx-background-color: #336699;");
        middleMenu.setAlignment(Pos.CENTER);
        middleMenu.prefHeightProperty().bind(window.heightProperty());

        /* Images */
        Image buyImg = new Image(getClass().getResourceAsStream("/Image/buy.png"));
        Image addImg = new Image(getClass().getResourceAsStream("/Image/add.png"));
        Image removeImg = new Image(getClass().getResourceAsStream("/Image/removeProd.png"));
        Image sortImg = new Image(getClass().getResourceAsStream("/Image/sortProd.png"));

        /* Buttons */
        Button buyProducts = new Button("Buy Products");
        ImageView buyimg = new ImageView(buyImg);
        buyProducts.setGraphic(buyimg);
        buyimg.setFitHeight(35);
        buyimg.setFitWidth(35);
        buyProducts.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        buyProducts.setFont(new Font(20.0));
        buyProducts.setPrefSize(230, 50);
        buyProducts.setOnAction(e -> buyProduct());

        Button addProducts = new Button("Add Products");
        ImageView addimg = new ImageView(addImg);
        addProducts.setGraphic(addimg);
        addimg.setFitHeight(35);
        addimg.setFitWidth(35);
        addProducts.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        addProducts.setFont(new Font(20.0));
        addProducts.setPrefSize(230, 50);
        addProducts.setOnAction(e -> addProduct());

        Button removeProducts = new Button("Remove Products");
        ImageView removeimg = new ImageView(removeImg);
        removeProducts.setGraphic(removeimg);
        removeimg.setFitHeight(30);
        removeimg.setFitWidth(35);
        removeProducts.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        removeProducts.setFont(new Font(20.0));
        removeProducts.setPrefSize(230, 50);
        removeProducts.setOnAction(e -> removeProduct());

        searchProducts = new ComboBox<>();
        searchProducts.getItems().addAll("Product Name", "ProductID");
        searchProducts.setPromptText("Search by:");
        searchProducts.setPrefSize(230, 50);
        searchProducts.setStyle("-fx-font: 19px \"sans-Serif\";");
        searchProducts.setPadding(new Insets(10, 5, 10, 15));
        searchProducts.setEditable(false);
        searchProducts.setOnAction(e -> {
            if (searchProducts.getValue() != null) {
                String tempString = searchProducts.getSelectionModel().getSelectedItem();
                switch (tempString) {
                    case "Product Name":
                        searchProductName();
                        break;
                    case "ProductID":
                        searchProductID();
                        break;
                    default:
                        AlertBox.display("ERROR", "Restart Program!");
                }
            }
        });

        Button sortProducts = new Button("Sort Products");
        ImageView sortimg = new ImageView(sortImg);
        sortProducts.setGraphic(sortimg);
        sortimg.setFitHeight(35);
        sortimg.setFitWidth(35);
        sortProducts.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        sortProducts.setFont(new Font(20.0));
        sortProducts.setPrefSize(230, 50);
        sortProducts.setOnAction(e -> sortProduct());

        /* Exit Image */
        Image exitImg = new Image(getClass().getResourceAsStream("/Image/exit.png"));

        HBox hbox = new HBox();
        Button exit = new Button("Exit");
        ImageView img = new ImageView(exitImg);
        exit.setGraphic(img);
        img.setFitHeight(25);
        img.setFitWidth(25);
        exit.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        exit.setFont(new Font(15.0));
        exit.setPrefSize(100, 30);
        exit.setOnAction(e -> closeProgram());

        hbox.setPadding(new Insets(60, 0, 10, 0));
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        hbox.getChildren().add(exit);

        middleMenu.getChildren().addAll(Welcome, header, buyProducts, addProducts, removeProducts, searchProducts, sortProducts, hbox);

        /* Bottom Label */
        Label Bottom = new Label("Copyright © 2019 Bhautoo Rohan");
        Bottom.setMaxWidth(Double.MAX_VALUE);
        Bottom.setAlignment(Pos.CENTER);

        tableList();
        BorderPane layout = new BorderPane();
        layout.setTop(menuBar);
        layout.setCenter(table);
        layout.setLeft(middleMenu);
        layout.setBottom(Bottom);
        Scene scene = new Scene(layout);
        window.setMaximized(true);
        window.setScene(scene);
        window.show();
    }

    /**
     * tableList() method contains the tableColumns and the cells values.
     *
     * The cells values of each columns must be equal to the instance variables of the Products.java constructor.
     */
    private void tableList() {
        /* Name column */
        TableColumn<Products, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        /* ProductID column */
        TableColumn<Products, Integer> productIDColumn = new TableColumn<>("ProductID");
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));

        /* Price column */
        TableColumn<Products, Double> priceColumn = new TableColumn<>("Price(RS)");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        /* Weight column */
        TableColumn<Products, String> weightColumn = new TableColumn<>("Weight(kg/g/L/ml)");
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        /* Calories column */
        TableColumn<Products, Integer> caloriesColumn = new TableColumn<>("Calories per serving");
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));

        /* Quantity column */
        TableColumn<Products, Integer> quantityColumn = new TableColumn<>("Quantity Available");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        /* Category column */
        TableColumn<Products, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        /* Creating tableView */
        table = new TableView<>();

        /**
         * The .bind() is used to make the height and width of the table
         *
         * to be exactly the same as the Stage(window) size.
         */
        table.prefHeightProperty().bind(window.heightProperty());
        table.prefWidthProperty().bind(window.widthProperty());

        /**
         * The CONSTRAINED_RESIZED_POLICY forces all the columns to be the same width.
         *
         * This helps to remove extra spaces or unused columns.
         */
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        getProducts();
        table.getColumns().addAll(nameColumn, productIDColumn, priceColumn, weightColumn, caloriesColumn, quantityColumn, categoryColumn);
    }

    /**
     * CloseProgram() is used with with ConfirmBox.java to give confirmation when close the window.
     *
     * Platform.exit is used to exit the whole application since window is the main stage.
     *
     * Sub-stages cannot remain opened.
     */
    private void closeProgram() {
        Boolean answer = ConfirmBox.display("Exit", "Are you sure you want to exit?");
        if (answer) {
            Platform.exit();
        }
    }

    /**
     * Closes the buyWindow.
     */
    private void closeBuyWindow() {
        Boolean answer = ConfirmBox.display("Exit", "Are you sure you want to exit?");
        if (answer) {
            /**
             * Deletes the Cart.txt & totalPrice.txt which is used to save customer's orders.
             *
             * This enables new customers to enter new orders.
             */
            File Cart = new File("Cart.txt");
            File price = new File("totalPrice.txt");

            Cart.delete();
            price.delete();

            buyWindow.close();
            window.close();
            /**
             * Platform.runLater() is used to restart the application so that the table on the main window gets updated.
             *
             * The Stage is restarted after the ConfirmBox is closed.
             */
            Platform.runLater(() -> new PointOfSales().start(new Stage()));
        }
    }

    /**
     * Closes the ReceiptWindow to go back to the primaryStage.
     */
    private void closeReceiptWindow() {
        Boolean answer = ConfirmBox.display("Exit", "Are you sure you want to exit?");
        if (answer) {
            File Cart = new File("Cart.txt");
            File price = new File("totalPrice.txt");

            Cart.delete();
            price.delete();

            receiptWindow.close();
            buyWindow.close();
        }
    }

    /**
     * Closes the addWindow.
     */
    private void closeAddWindow() {
        Boolean answer = ConfirmBox.display("Exit", "Are you sure you want to exit?");
        if (answer) {
            addWindow.close();
            window.close();
            Platform.runLater(() -> new PointOfSales().start(new Stage()));
        }
    }

    /**
     * Closes the removeWindow.
     */
    private void closeRemoveWindow() {
        Boolean answer = ConfirmBox.display("Exit", "Are you sure you want to exit?");
        if (answer) {
            removeWindow.close();
            window.close();
            Platform.runLater(() -> new PointOfSales().start(new Stage()));
        }
    }

    /**
     * Closes the searchWindow.
     */
    private void closeSearchWindow() {
        Boolean answer = ConfirmBox.display("Exit", "Are you sure you want to exit?");
        if (answer) {
            searchWindow.close();
            window.close();
            Platform.runLater(() -> new PointOfSales().start(new Stage()));
        }
    }

    /**
     * Closes the sortWindow.
     */
    private void closeSortWindow() {
        Boolean answer = ConfirmBox.display("Exit", "Are you sure you want to exit?");
        if (answer) {
            sortWindow.close();
        }
    }

    /**
     * MenuItems are options used in the MenuBar.
     *
     * @param fileMenu A variable of type Menu.
     * @param editMenu A variable of type Menu.
     * @param optionsMenu A variable of type Menu.
     * @param helpMenu A variable of type Menu.
     */
    private void MenuItems(Menu fileMenu, Menu editMenu, Menu optionsMenu, Menu helpMenu) {
        /* File Menu items */
        fileMenu.getItems().add(new MenuItem("New File... \tCtrl+N"));
        fileMenu.getItems().add(new MenuItem("Open File...\tCtrl+Shift+O"));
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(new MenuItem("Save \t\tCtrl+S"));
        fileMenu.getItems().add(new MenuItem("Save As..."));

        /* Separator creates a line between 2 MenuItems. */
        fileMenu.getItems().add(new SeparatorMenuItem());
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> closeProgram());
        fileMenu.getItems().add(exit);

        /* Edit Menu items */
        editMenu.getItems().add(new MenuItem("Undo \tCtrl+Z"));
        MenuItem redo = new MenuItem("Redo \tCtrl+Y");

        /* Grayed the redo menu item to make it unusable. */
        redo.setDisable(true);
        editMenu.getItems().add(redo);
        editMenu.getItems().add(new SeparatorMenuItem());
        editMenu.getItems().add(new MenuItem("Cut \t\tCtrl+X"));
        editMenu.getItems().add(new MenuItem("Copy \tCtrl+c"));
        MenuItem paste = new MenuItem("Paste \tCtrl+v");
        paste.setDisable(true);
        editMenu.getItems().add(paste);
        editMenu.getItems().add(new MenuItem("Delete \tDelete"));
        editMenu.getItems().add(new SeparatorMenuItem());
        editMenu.getItems().add(new MenuItem("Find... \tCtrl+F"));
        editMenu.getItems().add(new MenuItem("Replace \tCtrl+H"));

        /* Options Menu items */
        optionsMenu.getItems().add(new MenuItem("General"));
        optionsMenu.getItems().add(new SeparatorMenuItem());
        optionsMenu.getItems().add(new MenuItem("Fonts & Colors"));
        optionsMenu.getItems().add(new MenuItem("Keymap"));
        optionsMenu.getItems().add(new SeparatorMenuItem());
        optionsMenu.getItems().add(new MenuItem("Miscellaneous"));

        /* helpMenu items */
        helpMenu.getItems().add(new MenuItem("Help Contents"));
        helpMenu.getItems().add(new SeparatorMenuItem());
        helpMenu.getItems().add(new MenuItem("Check for Updates"));
        MenuItem About = new MenuItem("About");
        About.setOnAction(e -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("About");
            Image POSimage = new Image(getClass().getResourceAsStream("/Image/POS-icon.png"));
            ImageView PosImg = new ImageView(POSimage);
            PosImg.setFitHeight(150);
            PosImg.setFitWidth(150);
            alert.setGraphic(PosImg);
            alert.setHeaderText("This Point Of Sale System(POS) is the time and place where a retail transaction is completed. "
                    + "\nIt simplifies key day-to-day business operations. \nThis application was created by Bhautoo Rohan.\n");
            aboutTextArea = new TextArea();
            aboutTextArea.setText("Product Version: Point Of Sale System 1.12 (Build 201904012233)"
                    + "\nUpdates: Point Of Sale System is updatedd to version Point Of Sale System 1.12 Patch 2"
                    + "\nJava: 1.8.0_181; Java HotSpot(TM) 64-Bit Server VM 25.181-b13"
                    + "\nRuntime: Java(TM) SE Runtime Environment 1.8.0_181-b13"
                    + "\nSystem: Windows 10 version 10.0 running on amd64; Cp1252; en_US"
                    + "\nUser directory: C:\\Users\\Rohan\\Documents\\NetBeansProjects\\PointOfSales");
            aboutTextArea.setEditable(false);
            aboutTextArea.setFont(new Font(15));
            alert.getDialogPane().setContent(aboutTextArea);
            alert.showAndWait();
        });
        helpMenu.getItems().add(About);
    }

    /**
     * Retrieve the value of count which is the number of lines in the text file.
     *
     * @return An Integer data type.
     */
    private int count() {
        String line;
        int count = 0;

        try {
            /* Reads file. */
            File inputFile = new File("Products.txt");
            br = new BufferedReader(new FileReader(inputFile));

            while ((line = br.readLine()) != null) {
                /* Counts the lines in the File until it reaches End-Of-File or null. */
                count++;
            }
            /* Closes file. */
            br.close();
        } catch (IOException e) {
            AlertBox.display("ERROR", e + "");
        }
        return count;
    }

    /**
     * The product array size will be equal to the value of count.
     *
     * Each value in the text file will be stored in an array which is split using " ".
     *
     * @return A Product[] data type.
     */
    public Products[] productArray() {
        Products[] product = new Products[count()];

        try {
            File inputFile = new File("Products.txt");
            br = new BufferedReader(new FileReader(inputFile));

            String line;
            String[] array;

            int i = 0;

            while ((line = br.readLine()) != null) {

                array = line.split(" ");
                /**
                 * Names are stored in array[0],
                 *
                 * IDs are stored in array[1],
                 *
                 * Prices are stored in array[2],
                 *
                 * Weights are stored in array[3],
                 *
                 * Calories are stored in array[4],
                 *
                 * Quantity are stored in array[5],
                 *
                 * Category are stored in array[6].
                 */
                product[i] = new Products();
                product[i].setName(array[0]);
                product[i].setProductID(Integer.parseInt(array[1]));
                product[i].setPrice(Double.parseDouble(array[2]));
                product[i].setWeight(array[3]);
                product[i].setCalories(Integer.parseInt(array[4]));
                product[i].setQuantity(Integer.parseInt(array[5]));
                product[i].setCategory(array[6]);
                i++;
            }

            br.close();
        } catch (IOException e) {
            AlertBox.display("ERROR", e + "");
        }

        return product;
    }

    /**
     * getProducts() is used to add the products stored in the tableView.
     */
    private void getProducts() {
        Products[] product = new Products[count()];
        product = productArray();

        for (int i = 0; i < count(); i++) {
            table.getItems().add((new Products(product[i].getName(), product[i].getProductID(),
                    product[i].getPrice(), product[i].getWeight(), product[i].getCalories(),
                    product[i].getQuantity(), product[i].getCategory())));

        }
    }

    /**
     * Used to get the sorted name stored in the array.
     */
    private void getProductSortName() {
        Products[] product = new Products[count()];
        product = sortName();

        table.getItems().clear();
        for (int i = 0; i < count(); i++) {
            table.getItems().add((new Products(product[i].getName(), product[i].getProductID(),
                    product[i].getPrice(), product[i].getWeight(), product[i].getCalories(),
                    product[i].getQuantity(), product[i].getCategory())));

        }
    }

    /**
     * Used to get the sorted ID stored in the array.
     */
    private void getProductSortID() {
        Products[] product = new Products[count()];
        product = sortID();

        table.getItems().clear();
        for (int i = 0; i < count(); i++) {
            table.getItems().add((new Products(product[i].getName(), product[i].getProductID(),
                    product[i].getPrice(), product[i].getWeight(), product[i].getCalories(),
                    product[i].getQuantity(), product[i].getCategory())));

        }
    }

    /**
     * Used to get the sorted smallest price stored in the array.
     */
    private void getProductSortSmallestPrice() {
        Products[] product = new Products[count()];
        product = sortSmallestPrice();

        table.getItems().clear();
        for (int i = 0; i < count(); i++) {
            table.getItems().add((new Products(product[i].getName(), product[i].getProductID(),
                    product[i].getPrice(), product[i].getWeight(), product[i].getCalories(),
                    product[i].getQuantity(), product[i].getCategory())));

        }
    }

    /**
     * Used to get the sorted largest price stored in the array.
     */
    private void getProductsSortLargestPrice() {
        Products[] product = new Products[count()];
        product = sortLargestPrice();

        table.getItems().clear();
        for (int i = 0; i < count(); i++) {
            table.getItems().add((new Products(product[i].getName(), product[i].getProductID(),
                    product[i].getPrice(), product[i].getWeight(), product[i].getCalories(),
                    product[i].getQuantity(), product[i].getCategory())));

        }
    }

    /**
     * buyProduct() method is used to buy products by selecting the products from the table.
     *
     * The amount of the product bought is added and finally the total price is calculated.
     */
    private void buyProduct() {
        buyWindow = new Stage();
        buyWindow.setTitle("Buy Products");
        buyWindow.getIcons().add(new Image(getClass().getResourceAsStream("/Image/buy.png")));

        buyWindow.setOnCloseRequest(e -> {
            e.consume();
            closeBuyWindow();
        });

        tableList();

        /* Double-click on the table will cause the event below to occur. */
        table.setOnMouseClicked(e -> {
            if (e.getClickCount() > 1) {
                if (table.getSelectionModel().getSelectedItem() != null) {
                    try {
                        /* Fills the text field with the users selected product. */
                        Products product = table.getSelectionModel().getSelectedItem();
                        buyNameField.setText(product.getName());
                        buyNameField.setFocusTraversable(false);

                        buyIDField.setText(Integer.toString(product.getProductID()));
                        buyIDField.setFocusTraversable(false);

                        /**
                         * The amount field must be filled before to prevent any errors
                         *
                         * like nullPointer and to be able to calculate price.
                         *
                         * A TextInputDialog is used for this to get the amountField value.
                         */
                        amountDialog = new TextInputDialog("Amount");

                        /**
                         * .initModality(Modality.APPLICATION_MODAL) blocks actions on the other windows from the same application.
                         *
                         * It will continue to block until an input is entered in the field.
                         */
                        amountDialog.initModality(Modality.APPLICATION_MODAL);
                        amountDialog.setTitle("Amount for " + buyNameField.getText());
                        amountDialog.setHeaderText("Enter Amount:");
                        amountDialog.setContentText("Amount:");
                        Optional<String> result = amountDialog.showAndWait();

                        /**
                         * Checks for error is the result is null or less than 1.
                         */
                        if (!result.isPresent() || Integer.valueOf(result.get()) < 1) {
                            AlertBox.display("ERROR", "Invalid Input!\nEnter values greater than 0 only!");
                            buyAmountField.clear();
                        } else if (Integer.valueOf(result.get()) > product.getQuantity()) {
                            /* Checks if the amount entered is more than the amount available in store */
                            AlertBox.display("ERROR", "Quantity required not available in store!");
                            buyAmountField.clear();
                        } else {
                            try {
                                /**
                                 * Write product selected to file
                                 *
                                 * and append new product by adding '("Cart.txt", true)'.
                                 *
                                 * Append is used to prevent over-writing of the file.
                                 */
                                bw = new BufferedWriter(new FileWriter("Cart.txt", true));
                                bw.write(buyNameField.getText() + System.getProperty("line.separator"));
                                bw.close();
                            } catch (IOException io) {
                                AlertBox.display("ERROR", io + "");
                            }
                            buyAmountField.setText(amountDialog.getResult());

                            double price = product.getPrice();
                            calculatePrice(price);
                        }
                    } catch (NumberFormatException ex) {
                        AlertBox.display("ERROR", "Invalid Input!\nEnter 0-9 values only!");
                        buyAmountField.clear();
                    } catch (NullPointerException n) {
                        AlertBox.display("ERROR", "Field Input cannot be null!\nEnter value again!");
                    }
                }
            }
        });

        /* Label */
        Label buy = new Label("Please select the product(s)\nyou want to buy from \nthe table.");
        buy.setPadding(new Insets(10, 10, 10, 10));
        buy.setFont(new Font(16.5));

        HBox topLayout = new HBox();
        topLayout.getChildren().add(buy);

        /* Name Input */
        buyNameField = new TextField();
        buyNameField.setPromptText("Name");
        buyNameField.setMinWidth(100);
        buyNameField.setFont(new Font(15.0));
        buyNameField.setEditable(false);

        /* ID Input */
        buyIDField = new TextField();
        buyIDField.setPromptText("ID");
        buyIDField.setMinWidth(100);
        buyIDField.setFont(new Font(15.0));
        buyIDField.setEditable(false);

        /* Amount Input */
        buyAmountField = new TextField();
        buyAmountField.setPromptText("Amount");
        buyAmountField.setMinWidth(100);
        buyAmountField.setFont(new Font(15.0));
        buyAmountField.setEditable(false);

        /* Price Input */
        buyPriceField = new TextField();
        buyPriceField.setPromptText("Price(RS)");
        buyPriceField.setMinWidth(100);
        buyPriceField.setFont(new Font(15.0));
        buyPriceField.setEditable(false);

        /* Image */
        Image cart = new Image(getClass().getResourceAsStream("/Image/cartButton.png"));
        Image backImg = new Image(getClass().getResourceAsStream("/Image/back.png"));

        /* Buttons */
        Button cartButton = new Button();
        ImageView img = new ImageView(cart);
        img.setFitHeight(65);
        img.setFitWidth(75);
        cartButton.setGraphic(img);
        cartButton.setOnAction(e -> viewCart());
        cartButton.setPrefSize(10, 10);
        cartButton.setStyle("-fx-background-position: center; -fx-background-repeat: no-repeat;");

        Button backButton = new Button("Back");
        ImageView backimg = new ImageView(backImg);
        backButton.setGraphic(backimg);
        backimg.setFitHeight(25);
        backimg.setFitWidth(25);
        backButton.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        backButton.setPrefSize(90, 30);
        backButton.setOnAction(e -> back());

        VBox leftLayout = new VBox();
        leftLayout.setStyle("-fx-background-color: #336699;");
        leftLayout.setSpacing(30);
        leftLayout.setPadding(new Insets(10, 10, 10, 10));
        leftLayout.setAlignment(Pos.TOP_CENTER);
        leftLayout.getChildren().addAll(topLayout, buyNameField, buyIDField, buyAmountField, buyPriceField, cartButton, backButton);

        /* Bottom Label */
        Label Bottom = new Label("Copyright © 2019 Bhautoo Rohan");
        Bottom.setMaxWidth(Double.MAX_VALUE);
        Bottom.setAlignment(Pos.CENTER);

        /* Whole layout */
        BorderPane layout = new BorderPane();
        layout.setCenter(table);
        layout.setLeft(leftLayout);
        layout.setBottom(Bottom);

        Scene scene = new Scene(layout);
        buyWindow.setMaximized(true);
        buyWindow.setScene(scene);
        buyWindow.show();
    }

    /**
     * calculatePrice() method calculates the total price * the amount bought by user.
     *
     * The price is formatted to 2 decimal place and then stored in a text file.
     *
     * @param price A variable of type Double.
     */
    public void calculatePrice(double price) {

        double productPrice = price * Double.valueOf(buyAmountField.getText());

        DecimalFormat df = new DecimalFormat("#.##");
        String formatted = df.format(productPrice);
        buyPriceField.setText(formatted);

        try {
            bw = new BufferedWriter(new FileWriter("totalPrice.txt", true));
            bw.write(formatted + System.getProperty("line.separator"));
            bw.close();
        } catch (IOException io) {
            AlertBox.display("ERROR", io + "");
        }
    }

    /**
     * viewCart() enables the user to view the products bought, the total price and to finish buying.
     */
    private void viewCart() {
        cartWindow = new Stage();
        cartWindow.setTitle("View Cart");

        /* Disable maximize and minimize of Stage. */
        cartWindow.resizableProperty().setValue(Boolean.FALSE);
        cartWindow.initModality(Modality.APPLICATION_MODAL);

        /* TextArea to display contents of "Cart.txt". */
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font(18.0));
        try {
            File inputFile = new File("Cart.txt");
            br = new BufferedReader(new FileReader(inputFile));

            /**
             * Duplicate values are not allowed in LinkedHashSet.
             *
             * LinkedHashSet is used instead of HashSet to keep the insertion order.
             */
            LinkedHashSet<String> lines = new LinkedHashSet<>();
            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            br.close();

            bw = new BufferedWriter(new FileWriter(inputFile));
            for (String word : lines) {
                /* write word to file and diplay word in textArea. */
                bw.write(word);
                bw.newLine();
                textArea.appendText(word + "\n");
            }

            bw.close();
        } catch (IOException e) {
            AlertBox.display("ERROR", e + "");
        }

        try {
            File file = new File("totalPrice.txt");
            scanPrice = new Scanner(file);
            double sum = 0.0;

            /**
             * hasNext() will return true if the File has a String.
             *
             * It will loop through the file until no more String is found and returns false.
             */
            while (scanPrice.hasNext()) {
                /* Adds all the prices in the text file. */
                sum += scanPrice.nextDouble();
            }
            scanPrice.close();

            DecimalFormat df = new DecimalFormat("#.##");
            String formattedSum = df.format(sum);

            HBox Labelhbox = new HBox();
            Label label = new Label("Product(s) in Cart:");
            label.setFont(new Font(17.0));
            Labelhbox.setPadding(new Insets(10, 10, 10, 10));
            Labelhbox.getChildren().add(label);

            VBox vbox = new VBox();
            vbox.getChildren().add(textArea);

            /* Sets the vertical grow priority for the child when contained by a vbox. */
            VBox.setVgrow(textArea, Priority.ALWAYS);

            /* Image */
            Image continueImg = new Image(getClass().getResourceAsStream("/Image/continue.png"));

            /**
             * Making the variables a constant, the value cannot be changed.
             *
             * Then the constant variables is passed as parameters.
             *
             * Non-final variables cannot be referenced in lambda expressions.
             */
            final double totalSum = sum;
            final String formatSum = formattedSum;

            /* Button */
            Button continueButton = new Button("Continue");
            ImageView img = new ImageView(continueImg);
            continueButton.setGraphic(img);
            img.setFitHeight(20);
            img.setFitWidth(20);
            continueButton.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
            continueButton.setOnAction(e -> continueBuy(totalSum, formatSum));
            continueButton.setPrefSize(95, 30);

            HBox hbox = new HBox();
            Label priceLabel = new Label("Total Price: RS " + formattedSum);
            priceLabel.setFont(new Font(15.0));
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(156.0);
            hbox.setPadding(new Insets(8, 2, 8, 2));
            hbox.getChildren().addAll(priceLabel, continueButton);

            /* Whole layout */
            BorderPane layout = new BorderPane();
            layout.setCenter(vbox);
            layout.setTop(Labelhbox);
            layout.setBottom(hbox);

            Scene scene = new Scene(layout, 400, 400);
            cartWindow.setScene(scene);
            cartWindow.show();
        } catch (IOException e) {
            AlertBox.display("ERROR", e + "");
        }
    }

    /**
     * continueBuy() will show the total amount of money given by the user.
     *
     * The total price is also displayed.
     *
     * Then the change is calculated and displayed in the window.
     *
     * @param totalSum A variable of type Double.
     * @param formatSum A variable of type String.
     */
    private void continueBuy(final double totalSum, final String formatSum) {
        try {
            continueBuy = new Stage();
            continueBuy.setTitle("Confirm Payment");

            /* Disable maximize and minimize of Stage. */
            continueBuy.resizableProperty().setValue(Boolean.FALSE);
            continueBuy.initModality(Modality.APPLICATION_MODAL);

            /* Label */
            Label header = new Label("Money given by customer :");
            header.setFont(new Font(18.0));
            header.setPadding(new Insets(10, 10, 10, 10));

            /**
             * The money field must be filled before to prevent any errors like nullPointer
             *
             * and to be able to calculate change.
             *
             * A TextInputDialog is used for this to get the moneyField value.
             */
            moneyDialog = new TextInputDialog("Money");
            moneyDialog.initModality(Modality.APPLICATION_MODAL);
            moneyDialog.setTitle("Money ");
            moneyDialog.setHeaderText("Enter Money:");
            moneyDialog.setContentText("Money:");
            Optional<String> result = moneyDialog.showAndWait();

            /* Checks for errors. */
            if (!result.isPresent() || !result.get().matches("^[0-9.]+$")) {
                AlertBox.display("ERROR", "Invalid Input!\nEnter 0-9 values only!");
            } else if (Double.valueOf(result.get()) < totalSum) {
                /* Checks if the money entered is greater than the total price. */
                AlertBox.display("ERROR", "Not ENOUGH Money!");
            } else {
                cartWindow.close();
                TextField moneyField = new TextField();
                moneyField.setPromptText("Money");
                moneyField.setMinWidth(50);
                moneyField.setFont(new Font(15.0));
                moneyField.setEditable(false);
                moneyField.setText(moneyDialog.getResult());
                moneyField.setFocusTraversable(false);

                /* Label */
                Label money = new Label("Money: RS ");
                money.setFont(new Font(15.0));

                Label TotalPrice = new Label("Total Price: RS " + formatSum);
                TotalPrice.setFont(new Font(15.0));

                HBox hbox = new HBox();
                hbox.getChildren().addAll(money, moneyField);
                hbox.setSpacing(9.0);
                hbox.setAlignment(Pos.CENTER_LEFT);

                TextField changeField = new TextField();
                changeField.setPromptText("Change");
                changeField.setMinWidth(50);
                changeField.setFont(new Font(15.0));
                changeField.setEditable(false);
                changeField.setFocusTraversable(false);

                Label change = new Label("Change: RS ");
                change.setFont(new Font(15.0));

                /* Calculate change and format to 2 decimal places. */
                double Money_change = Double.valueOf(moneyField.getText()) - totalSum;
                DecimalFormat df = new DecimalFormat("#.##");
                String formatted = df.format(Money_change);
                changeField.setText(formatted);

                HBox hbox2 = new HBox();
                hbox2.getChildren().addAll(change, changeField);
                hbox2.setSpacing(9.0);
                hbox2.setAlignment(Pos.CENTER_LEFT);

                /**
                 * Setting constant variables.
                 *
                 * Non-final variables cannot be referenced in lambda expressions.
                 */
                final String change_money = formatted;
                final String total_Price = formatSum;
                final String money_Of_Customer = moneyField.getText();
                final String prod = textArea.getText();

                /* Image */
                Image okImg = new Image(getClass().getResourceAsStream("/Image/ok.png"));

                /* Button */
                ToggleButton okButton = new ToggleButton("OK");
                ImageView ok = new ImageView(okImg);
                okButton.setGraphic(ok);
                ok.setFitHeight(25);
                ok.setFitWidth(25);
                okButton.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
                okButton.setOnAction(e -> {
                    continueBuy.close();
                    viewReceipt(change_money, total_Price, money_Of_Customer, prod);
                });
                okButton.setPrefSize(90, 30);

                HBox hbox3 = new HBox();
                hbox3.getChildren().addAll(okButton);
                hbox3.setPadding(new Insets(15, 0, 0, 0));
                hbox3.setAlignment(Pos.CENTER);

                VBox vbox = new VBox();
                vbox.getChildren().addAll(hbox, TotalPrice, hbox2, hbox3);
                vbox.setSpacing(20.0);
                vbox.setPadding(new Insets(10, 10, 10, 10));

                /* Whole Layout */
                BorderPane layout = new BorderPane();
                layout.setTop(header);
                layout.setCenter(vbox);

                Scene scene = new Scene(layout, 350, 250);
                continueBuy.setScene(scene);
                continueBuy.show();
            }
        } catch (NumberFormatException e) {
            AlertBox.display("ERROR", "Invalid Input!");
        }
    }

    /**
     * viewReceipt() displays a receipt to the user and finishing a purchase.
     *
     * The Receipt contains information about the products bought,
     *
     * total price, money given by user, the change, the order number
     *
     * and the date.
     *
     * @param change_money A variable of type String.
     * @param total_Price A variable of type String.
     * @param money_Of_Customer A variable of type String.
     * @param prod A variable of type String.
     */
    private void viewReceipt(final String change_money, final String total_Price, final String money_Of_Customer, final String prod) {
        receiptWindow = new Stage();
        receiptWindow.setTitle("Receipt");
        receiptWindow.resizableProperty().setValue(Boolean.FALSE);
        receiptWindow.initModality(Modality.APPLICATION_MODAL);

        receiptWindow.setOnCloseRequest(e -> {
            e.consume();
            closeReceiptWindow();
        });

        /* Random numbers from 0 to 10000 */
        Random rand = new Random();
        int Order_Number = rand.nextInt(10001);

        /**
         * Method to get calender using time zone and locale.
         *
         * getTime() returns the Data that represents Calendar's time value.
         */
        java.util.Date currentDate = Calendar.getInstance().getTime();

        /* Label */
        Label receipt = new Label(
                "-----------------------------------------------"
                + "\n                       Thanks You."
                + "\n                 Please Come Again."
                + "\n    <Please Keep Receipt For Any Claim>"
                + "\n-----------------------------------------------"
                + "\n                    PICK 'N' BUY LTD"
                + "\n                   Tel: (+230)2120000"
                + "\n-----------------------------------------------"
                + "\n                *** Order Number *** \n"
                + "\t\t             " + Order_Number
                + "\n-----------------------------------------------\n"
                + "\t  " + currentDate
                + "\n-----------------------------------------------\n"
                + "                     *** Products ***\n\n"
                + prod
                + "\nCost: RS " + total_Price
                + "\nCash: RS " + money_Of_Customer
                + "\nChange: RS " + change_money
                + "\n-----------------------------------------------"
        );
        receipt.setFont(new Font(15.0));
        receipt.setPadding(new Insets(10, 10, 10, 10));
        receipt.setAlignment(Pos.CENTER);

        /* Whole Layout */
        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #ffffff");
        layout.getChildren().add(receipt);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        receiptWindow.setScene(scene);
        receiptWindow.show();
    }

    /**
     * The back() method goes back to the primaryStage and
     *
     * deletes the texts files for new users to enter new informations.
     */
    private void back() {
        File Cart = new File("Cart.txt");
        File price = new File("totalPrice.txt");

        Cart.delete();
        price.delete();

        closeBuyWindow();
    }

    /**
     * The addProduct() contains the addWindow and its visual parts.
     */
    private void addProduct() {
        addWindow = new Stage();
        addWindow.setTitle("Add Products");
        addWindow.getIcons().add(new Image(getClass().getResourceAsStream("/Image/add.png")));

        addWindow.setOnCloseRequest(e -> {
            e.consume();
            closeAddWindow();
        });

        tableList();

        /* Name Input */
        nameInput = new TextField();
        nameInput.setPromptText("Name");
        nameInput.setMinWidth(100);

        /* ProductID Input */
        productIDInput = new TextField();
        productIDInput.setPromptText("ProductID");
        productIDInput.setMinWidth(100);

        /* Price Input */
        priceInput = new TextField();
        priceInput.setPromptText("Price(RS)");
        priceInput.setMinWidth(100);

        /* Weight Input */
        weightInput = new TextField();
        weightInput.setPromptText("Weight");
        weightInput.setMinWidth(100);

        /* Calories Input */
        caloriesInput = new TextField();
        caloriesInput.setPromptText("Calories");
        caloriesInput.setMinWidth(100);

        /* Quantity Input */
        quantityInput = new TextField();
        quantityInput.setPromptText("Quantity");
        quantityInput.setMinWidth(100);

        /* Category Input */
        categoryChoice = new ComboBox<>();
        categoryChoice.getItems().addAll("Dairy", "Egg", "Poultry", "Meat", "Fish", "Vegetables", "Snacks", "Grain", "Cereal", "Sweets",
                "Beverages", "Care", "Goods", "Cleaners", "Canned-Foods", "Frozen-Foods");
        categoryChoice.setPromptText("Select Category");
        categoryChoice.setEditable(true);
        categoryChoice.setMinWidth(100);

        /* Image */
        Image addImg = new Image(getClass().getResourceAsStream("/Image/add.png"));
        Image backImg = new Image(getClass().getResourceAsStream("/Image/back.png"));

        /* Button */
        Button addButton = new Button("Add");
        ImageView addimg = new ImageView(addImg);
        addButton.setGraphic(addimg);
        addimg.setFitHeight(20);
        addimg.setFitWidth(20);
        addButton.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        addButton.setOnAction(e -> add());
        addButton.setPrefSize(110, 100);

        Button backButton = new Button("Back");
        ImageView backimg = new ImageView(backImg);
        backButton.setGraphic(backimg);
        backimg.setFitHeight(20);
        backimg.setFitWidth(20);
        backButton.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        backButton.setPrefSize(110, 100);
        backButton.setOnAction(e -> closeAddWindow());

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setStyle("-fx-background-color: #336699;");
        hbox.setSpacing(25);
        hbox.getChildren().addAll(nameInput, productIDInput, priceInput, weightInput, caloriesInput, quantityInput, categoryChoice, addButton, backButton);
        hbox.prefWidthProperty().bind(addWindow.widthProperty());

        /* Whole Layout */
        VBox layout = new VBox();
        layout.getChildren().addAll(table, hbox);

        Scene scene = new Scene(layout);
        addWindow.setMaximized(true);
        addWindow.setScene(scene);
        addWindow.show();
    }

    /**
     * The add() method is used to write, informations of new products
     *
     * entered in the textFields, in a text file.
     *
     * Verifications has also been implemented using regex.
     */
    public void add() {
        Products[] product = new Products[count()];
        product = productArray();
        try {
            File inputFile = new File("Products.txt");
            bw = new BufferedWriter(new FileWriter(inputFile, true));

            /* Change line when adding new products. */
            bw.newLine();

            bw.write(nameInput.getText() + " " + Integer.parseInt(productIDInput.getText()) + " " + Double.parseDouble(priceInput.getText())
                    + " " + weightInput.getText() + " " + Integer.parseInt(caloriesInput.getText()) + " "
                    + Integer.parseInt(quantityInput.getText()) + " " + categoryChoice.getValue());

            int countName = 0;
            int countID = 0;

            /**
             * Checks if the product name or productID already exists.
             *
             * If countName/countID > 1, then the product already exists and cannot be added.
             */
            for (int i = 0; i < count(); i++) {
                if (nameInput.getText().equalsIgnoreCase(product[i].getName())) {
                    countName++;
                }
                if (Integer.valueOf(productIDInput.getText()) == (product[i].getProductID())) {
                    countID++;
                }
            }

            /* Checking for errors. */
            if (!nameInput.getText().matches("^[a-zA-Z-]+$")
                    || !weightInput.getText().matches("^[a-zA-Z0-9]+$")
                    || !categoryChoice.getValue().matches("^[a-zA-Z-]+$")) {
                AlertBox.display("ERROR", "Invalid Input!\nEnter 'a-Z | A-Z | -' values!");
            } else if (countName != 0) {
                AlertBox.display("ERROR", "Product Name ALREADY Exists!");

                /* Clears all the fields */
                nameInput.clear();
                productIDInput.clear();
                priceInput.clear();
                weightInput.clear();
                caloriesInput.clear();
                quantityInput.clear();
                categoryChoice.getSelectionModel().clearSelection();
            } else if (countID != 0) {
                AlertBox.display("ERROR", "ProductID ALREADY Exists!");

                nameInput.clear();
                productIDInput.clear();
                priceInput.clear();
                weightInput.clear();
                caloriesInput.clear();
                quantityInput.clear();
                categoryChoice.getSelectionModel().clearSelection();
            } else {
                AlertBox.display("Success", "Product SUCCESSFULLY added.");

                nameInput.clear();
                productIDInput.clear();
                priceInput.clear();
                weightInput.clear();
                caloriesInput.clear();
                quantityInput.clear();
                categoryChoice.getSelectionModel().clearSelection();

                bw.close();

                /**
                 * Clears products from table.
                 *
                 * Re-reads the "Products.txt" so that the table gets updated.
                 */
                table.getItems().clear();
                getProducts();
            }
        } catch (FileNotFoundException e) {
            AlertBox.display("ERROR", e + "");
        } catch (IOException e) {
            AlertBox.display("ERROR", e + "");
        } catch (NumberFormatException e) {
            AlertBox.display("ERROR", "Invalid Input!\n" + e);
        }
    }

    /**
     * The removeProduct() contains the removeWindow and its visual parts.
     *
     * The ID can be entered by user or by double-clicking the selected
     *
     * product from the table.
     */
    private void removeProduct() {
        removeWindow = new Stage();
        removeWindow.setTitle("Remove Products");
        removeWindow.getIcons().add(new Image(getClass().getResourceAsStream("/Image/removeProd.png")));

        removeWindow.setOnCloseRequest(e -> {
            e.consume();
            closeRemoveWindow();
        });

        tableList();

        table.setOnMouseClicked(e -> {
            if (e.getClickCount() > 1) {
                if (table.getSelectionModel().getSelectedItem() != null) {
                    Products product = table.getSelectionModel().getSelectedItem();
                    removeIDField.setText(Integer.toString(product.getProductID()));
                }
            }
        });

        Label remove = new Label("Remove Product(s).\nEnter ProductID in the field:");
        remove.setPadding(new Insets(0, 0, 0, 5));
        remove.setFont(new Font(16.5));

        HBox topLayout = new HBox();
        topLayout.getChildren().add(remove);

        /* ID input */
        removeIDField = new TextField();
        removeIDField.setPromptText("Enter ProductID");
        removeIDField.setMinWidth(100);
        removeIDField.setFont(new Font(15.0));

        /* Image */
        Image removeImg = new Image(getClass().getResourceAsStream("/Image/remove.png"));
        Image backImg = new Image(getClass().getResourceAsStream("/Image/back.png"));

        /* Button */
        Button removeButton = new Button("Remove");
        ImageView removeimg = new ImageView(removeImg);
        removeButton.setGraphic(removeimg);
        removeimg.setFitHeight(30);
        removeimg.setFitWidth(30);
        removeButton.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        removeButton.setOnAction(e -> {
            Boolean answer = ConfirmBox.display("Confirmation", "Are you sure you want to remove this product?");
            if (answer) {
                remove();
            } else {
                window.close();
            }
        });
        removeButton.setPrefSize(130, 35);

        Button backButton = new Button("Back");
        ImageView backimg = new ImageView(backImg);
        backButton.setGraphic(backimg);
        backimg.setFitHeight(30);
        backimg.setFitWidth(30);
        backButton.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        backButton.setPrefSize(130, 35);
        backButton.setOnAction(e -> closeRemoveWindow());

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setStyle("-fx-background-color: #336699;");
        vbox.setSpacing(30);
        vbox.getChildren().addAll(topLayout, removeIDField, removeButton, backButton);

        VBox layout = new VBox();
        layout.getChildren().addAll(table, vbox);

        /* Bottom Label */
        Label Bottom = new Label("Copyright © 2019 Bhautoo Rohan");
        Bottom.setMaxWidth(Double.MAX_VALUE);
        Bottom.setAlignment(Pos.CENTER);

        BorderPane bp = new BorderPane();
        bp.setCenter(table);
        bp.setLeft(vbox);
        bp.setBottom(Bottom);

        Scene scene = new Scene(bp);
        removeWindow.setMaximized(true);
        removeWindow.setScene(scene);
        removeWindow.show();
    }

    /**
     * The remove() method is used to get the index of the entered ID from the file.
     *
     * Then the index is passed as parameter in the removeElement() method.
     *
     * The File is then re written without the ID entered and all its informations.(Name, weight, calories, category, etc...)
     */
    public void remove() {
        Products[] products = new Products[count()];
        products = productArray();
        try {
            int searchResult = -1;
            for (int x = 0; x < count(); x++) {
                if (products[x].getProductID() == Integer.parseInt(removeIDField.getText())) {
                    searchResult = x;
                }
            }

            products = removeElement(products, searchResult);

            /* Rewrite second array to file. */
            File inputFile = new File("Products.txt");
            PrintStream writer = new PrintStream(inputFile);

            for (int i = 0; i < products.length; i++) {
                if (!(products[i].getProductID() == 0)) {
                    writer.print(products[i].getName());
                    writer.print(" ");
                    writer.print(products[i].getProductID());
                    writer.print(" ");
                    writer.print(products[i].getPrice());
                    writer.print(" ");
                    writer.print(products[i].getWeight());
                    writer.print(" ");
                    writer.print(products[i].getCalories());
                    writer.print(" ");
                    writer.print(products[i].getQuantity());
                    writer.print(" ");
                    if (i == products.length - 1) {
                        writer.print(products[i].getCategory());
                    } else {
                        writer.println(products[i].getCategory());
                    }
                }
            }
            AlertBox.display("Success", "Product SUCCESSFULLY removed.");
            removeIDField.clear();

            /* Update table. */
            table.getItems().clear();
            getProducts();
        } catch (IOException e) {
            AlertBox.display("ERROR", e + "");
        } catch (NumberFormatException ex) {
            AlertBox.display("ERROR", "Invalid Input!");
        }
    }

    /**
     * The removeElement() method creates a second array with length - 1.
     *
     * Then if x is equal to searchResult, the iteration will increase by 1 in anotherProducts[k++].
     *
     * This will skip the index of the searchResult to be written to file.
     *
     * Hence, the product will be removed.
     *
     * @param products A variable of type Product.
     * @param searchResult A variable of type Integer.
     * @return A Product[] data type.
     */
    public Products[] removeElement(Products[] products, int searchResult) {
        Products[] anotherProducts = new Products[products.length - 1];

        for (int x = 0, k = 0; x < products.length; x++) {
            if (x == searchResult) {
                continue;
            }
            anotherProducts[k++] = products[x];
        }

        return anotherProducts;
    }

    /**
     * The searchProductName() contains the searchWindow and its visual parts.
     */
    private void searchProductName() {
        searchWindow = new Stage();
        searchWindow.setTitle("Search Product Name");
        searchWindow.getIcons().add(new Image(getClass().getResourceAsStream("/Image/searchProd.png")));

        searchWindow.setOnCloseRequest(e -> {
            e.consume();
            closeSearchWindow();
        });

        tableList();

        Label chooseSearchLabel = new Label("Enter Product Name:");
        chooseSearchLabel.setPadding(new Insets(0, 10, 0, 10));
        chooseSearchLabel.setFont(new Font(20.0));

        Label noteLabel = new Label("NOTE: If the name entered is found \nin the database, it will display its results. \nElse, nothing"
                + " will be displayed.");
        noteLabel.setPadding(new Insets(0, 10, 0, 10));
        noteLabel.setFont(new Font(15.0));

        /* Search Name input */
        searchNameField = new TextField();
        searchNameField.setPromptText("Enter Product Name");
        searchNameField.setMinWidth(100);
        searchNameField.setFont(new Font(15.0));

        /* Images */
        Image searchImg = new Image(getClass().getResourceAsStream("/Image/search.png"));
        Image backImg = new Image(getClass().getResourceAsStream("/Image/back.png"));

        /* Buttons */
        Button searchButton = new Button("Search");
        ImageView searchimg = new ImageView(searchImg);
        searchButton.setGraphic(searchimg);
        searchimg.setFitHeight(25);
        searchimg.setFitWidth(25);
        searchButton.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        searchButton.setOnAction(e -> searchName());
        searchButton.setPrefSize(130, 35);

        Button backButton = new Button("Back");
        ImageView backimg = new ImageView(backImg);
        backButton.setGraphic(backimg);
        backimg.setFitHeight(25);
        backimg.setFitWidth(25);
        backButton.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        backButton.setPrefSize(130, 35);
        backButton.setOnAction(e -> closeSearchWindow());

        VBox vboxMenu = new VBox();
        vboxMenu.setAlignment(Pos.TOP_CENTER);
        vboxMenu.setPadding(new Insets(10, 10, 10, 10));
        vboxMenu.setStyle("-fx-background-color: #336699;");
        vboxMenu.setSpacing(30);
        vboxMenu.getChildren().addAll(chooseSearchLabel, noteLabel, searchNameField, searchButton, backButton);

        VBox vboxTable = new VBox();
        vboxTable.getChildren().addAll(table, vboxMenu);

        Label Bottom = new Label("Copyright © 2019 Bhautoo Rohan");
        Bottom.setMaxWidth(Double.MAX_VALUE);
        Bottom.setAlignment(Pos.CENTER);

        /* Whole Layout */
        BorderPane layout = new BorderPane();
        layout.setCenter(table);
        layout.setLeft(vboxMenu);
        layout.setBottom(Bottom);

        Scene scene = new Scene(layout);
        searchWindow.setMaximized(true);
        searchWindow.setScene(scene);
        searchWindow.show();
    }

    /**
     * The searchName() method will get the name entered in the searchNameField.
     *
     * Then if the name is found in the array, it will display the results.
     *
     * Validation is implemented using regex.
     *
     * @return A Product[] data type.
     */
    private Products[] searchName() {

        Products[] product = new Products[count()];
        product = productArray();
        String inputSearch = searchNameField.getText();

        if (!inputSearch.matches("^[a-zA-Z-]+$")) {
            AlertBox.display("ERROR", "Invalid Input!\nEnter 'a-Z | A-Z | -' values!");
            searchNameField.clear();
        } else {
            for (int i = 0; i < count(); i++) {
                if (inputSearch.equalsIgnoreCase((product[i].getName()))) {
                    AlertBox.display("FOUND", "FOUND!\nName: " + product[i].getName() + "\nID: " + product[i].getProductID() + "\nPrice: RS "
                            + product[i].getPrice() + "\nWeight: " + product[i].getWeight() + "\nCalories: " + product[i].getCalories()
                            + "\nQuantity: " + product[i].getQuantity() + "\nCategory: " + product[i].getCategory());
                }
            }
            searchNameField.clear();
        }

        return product;
    }

    /**
     * The searchProductID() contains the searchWindow and its visual parts.
     */
    private void searchProductID() {
        searchWindow = new Stage();
        searchWindow.setTitle("Search ProductID");
        searchWindow.getIcons().add(new Image(getClass().getResourceAsStream("/Image/searchProd.png")));

        searchWindow.setOnCloseRequest(e -> {
            e.consume();
            closeSearchWindow();
        });

        tableList();

        Label chooseSearchLabel = new Label("Enter ProductID:");
        chooseSearchLabel.setPadding(new Insets(0, 10, 0, 10));
        chooseSearchLabel.setFont(new Font(20.0));

        Label noteLabel = new Label("NOTE: If the ID entered is found \nin the database, it will display the result. \nElse, nothing"
                + " will be displayed.");
        noteLabel.setPadding(new Insets(0, 10, 0, 10));
        noteLabel.setFont(new Font(15.0));

        /* Search ID input */
        searchIDField = new TextField();
        searchIDField.setPromptText("Enter ProductID");
        searchIDField.setMinWidth(100);
        searchIDField.setFont(new Font(15.0));

        /* Images */
        Image searchImg = new Image(getClass().getResourceAsStream("/Image/search.png"));
        Image backImg = new Image(getClass().getResourceAsStream("/Image/back.png"));

        /* Button */
        Button searchButton = new Button("Search");
        ImageView searchimg = new ImageView(searchImg);
        searchButton.setGraphic(searchimg);
        searchimg.setFitHeight(25);
        searchimg.setFitWidth(25);
        searchButton.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        searchButton.setOnAction(e -> searchID());
        searchButton.setPrefSize(130, 35);

        Button backButton = new Button("Back");
        ImageView backimg = new ImageView(backImg);
        backButton.setGraphic(backimg);
        backimg.setFitHeight(25);
        backimg.setFitWidth(25);
        backButton.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        backButton.setPrefSize(130, 35);
        backButton.setOnAction(e -> closeSearchWindow());

        /* VBox layout */
        VBox vboxMenu = new VBox();
        vboxMenu.setAlignment(Pos.TOP_CENTER);
        vboxMenu.setPadding(new Insets(10, 10, 10, 10));
        vboxMenu.setStyle("-fx-background-color: #336699;");
        vboxMenu.setSpacing(30);
        vboxMenu.getChildren().addAll(chooseSearchLabel, noteLabel, searchIDField, searchButton, backButton);

        VBox vboxTable = new VBox();
        vboxTable.getChildren().addAll(table, vboxMenu);

        /* Bottom Label */
        Label Bottom = new Label("Copyright © 2019 Bhautoo Rohan");
        Bottom.setMaxWidth(Double.MAX_VALUE);
        Bottom.setAlignment(Pos.CENTER);

        /* Whole Layout */
        BorderPane layout = new BorderPane();
        layout.setCenter(table);
        layout.setLeft(vboxMenu);
        layout.setBottom(Bottom);

        Scene scene = new Scene(layout);
        searchWindow.setMaximized(true);
        searchWindow.setScene(scene);
        searchWindow.show();
    }

    /**
     * The searchID() method will get the ID entered in the searchIDField.
     *
     * Then if the ID is found in the array, it will display the results.
     *
     * Validation is implemented using regex.
     *
     * Number entered by user should also be greater then 0.
     *
     * @return A Product[] data type.
     */
    public Products[] searchID() {
        Products[] product = new Products[count()];
        product = productArray();

        //Get texts from searchIDField for searching
        String inputSearch = searchIDField.getText();

        if (!searchIDField.getText().matches("^[0-9]+$")) {
            AlertBox.display("ERROR", "Invalid Input!\nEnter '0-9' values!");
            searchIDField.clear();
        } else if (Integer.valueOf(searchIDField.getText()) < 1) {
            AlertBox.display("ERROR", "ProductID should be greater than 0");
            searchIDField.clear();
        } else {
            for (int i = 0; i < count(); i++) {
                if (Integer.valueOf(inputSearch) == (product[i].getProductID())) {
                    AlertBox.display("FOUND", "FOUND!\nID: " + product[i].getProductID() + "\nName: " + product[i].getName()
                            + "\nPrice: RS " + product[i].getPrice() + "\nWeight: " + product[i].getWeight() + "\nCalories: "
                            + product[i].getCalories() + "\nQuantity: " + product[i].getQuantity() + "\nCategory: "
                            + product[i].getCategory());
                }
            }
            searchIDField.clear();
        }
        return product;
    }

    /**
     * The sortProduct() contains the sortWindow and its visual parts.
     */
    public void sortProduct() {
        sortWindow = new Stage();
        sortWindow.setTitle("Sort Products");
        sortWindow.getIcons().add(new Image(getClass().getResourceAsStream("/Image/sortProd.png")));

        sortWindow.setOnCloseRequest(e -> {
            e.consume();
            closeSortWindow();
        });

        tableList();

        Label sortLabel = new Label("Sort Products");
        sortLabel.setPadding(new Insets(0, 10, 0, 10));
        sortLabel.setFont(new Font(20.0));

        /* Images */
        Image sortImg = new Image(getClass().getResourceAsStream("/Image/sortName.png"));
        Image sortIDImg = new Image(getClass().getResourceAsStream("/Image/sortID.png"));
        Image sortSmallPriceImg = new Image(getClass().getResourceAsStream("/Image/sortSmallestPrice.png"));
        Image sortLargePriceImg = new Image(getClass().getResourceAsStream("/Image/sortLargestPrice.png"));
        Image resetImg = new Image(getClass().getResourceAsStream("/Image/reset.png"));
        Image backImg = new Image(getClass().getResourceAsStream("/Image/back.png"));

        /* Button */
        Button sortNameButton = new Button("Sort Name");
        ImageView sortNameimg = new ImageView(sortImg);
        sortNameButton.setGraphic(sortNameimg);
        sortNameimg.setFitHeight(25);
        sortNameimg.setFitWidth(25);
        sortNameButton.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        sortNameButton.setOnAction(e -> getProductSortName());
        sortNameButton.setPrefSize(130, 35);

        Button sortIDButton = new Button("Sort ID");
        ImageView sortIDimg = new ImageView(sortIDImg);
        sortIDButton.setGraphic(sortIDimg);
        sortIDimg.setFitHeight(25);
        sortIDimg.setFitWidth(25);
        sortIDButton.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        sortIDButton.setOnAction(e -> getProductSortID());
        sortIDButton.setPrefSize(130, 35);

        Button sortSmallestPriceButton = new Button("Sort Price");
        ImageView sortSmallPriceimg = new ImageView(sortSmallPriceImg);
        sortSmallestPriceButton.setGraphic(sortSmallPriceimg);
        sortSmallPriceimg.setFitHeight(25);
        sortSmallPriceimg.setFitWidth(25);
        sortSmallestPriceButton.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        sortSmallestPriceButton.setOnAction(e -> getProductSortSmallestPrice());
        sortSmallestPriceButton.setPrefSize(130, 35);

        Button sortLargestPriceButton = new Button("Sort Price");
        ImageView sortLargePriceimg = new ImageView(sortLargePriceImg);
        sortLargestPriceButton.setGraphic(sortLargePriceimg);
        sortLargePriceimg.setFitHeight(25);
        sortLargePriceimg.setFitWidth(25);
        sortLargestPriceButton.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        sortLargestPriceButton.setOnAction(e -> getProductsSortLargestPrice());
        sortLargestPriceButton.setPrefSize(130, 35);

        Button resetButton = new Button("Reset");
        ImageView resetimg = new ImageView(resetImg);
        resetButton.setGraphic(resetimg);
        resetimg.setFitHeight(25);
        resetimg.setFitWidth(25);
        resetButton.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        resetButton.setOnAction(e -> reset());
        resetButton.setPrefSize(130, 35);

        Button backButton = new Button("Back");
        ImageView backimg = new ImageView(backImg);
        backButton.setGraphic(backimg);
        backimg.setFitHeight(25);
        backimg.setFitWidth(25);
        backButton.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
        backButton.setPrefSize(130, 35);
        backButton.setOnAction(e -> closeSortWindow());

        /* VBox layout */
        VBox vboxMenu = new VBox();
        vboxMenu.setAlignment(Pos.TOP_CENTER);
        vboxMenu.setPadding(new Insets(10, 10, 10, 10));
        vboxMenu.setStyle("-fx-background-color: #336699;");
        vboxMenu.setSpacing(30);
        vboxMenu.getChildren().addAll(sortLabel, sortNameButton, sortIDButton,
                sortSmallestPriceButton, sortLargestPriceButton, resetButton, backButton);

        VBox vboxTable = new VBox();
        vboxTable.getChildren().addAll(table, vboxMenu);

        /* Bottom Label */
        Label Bottom = new Label("Copyright © 2019 Bhautoo Rohan");
        Bottom.setMaxWidth(Double.MAX_VALUE);
        Bottom.setAlignment(Pos.CENTER);

        /* Whole Layout */
        BorderPane layout = new BorderPane();
        layout.setCenter(table);
        layout.setLeft(vboxMenu);
        layout.setBottom(Bottom);

        Scene scene = new Scene(layout);
        sortWindow.setMaximized(true);
        sortWindow.setScene(scene);
        sortWindow.show();
    }

    /**
     * Using bubble sort algorithm to compare name in product array.
     *
     * compareTo will return:
     *
     * 0 - Strings are identical.
     *
     * +ve number- 1st String is bigger than second string.
     *
     * -ve number- 1st String is smaller than second string.
     *
     * The names are swapped if it is less than 0.
     *
     * @return A Product[] data type.
     */
    public Products[] sortName() {

        Products[] product = new Products[count()];
        product = productArray();

        String tempName, tempWeight, tempCategory;
        int tempProductID, tempCalories, tempQuantity;
        double tempPrice;

        for (int x = 1; x < count(); x++) {
            for (int y = x; y > 0; y--) {
                if (product[y].getName().compareToIgnoreCase(product[y - 1].getName()) < 0) {
                    tempName = product[y].getName();
                    tempProductID = product[y].getProductID();
                    tempPrice = product[y].getPrice();
                    tempWeight = product[y].getWeight();
                    tempCalories = product[y].getCalories();
                    tempQuantity = product[y].getQuantity();
                    tempCategory = product[y].getCategory();

                    product[y].setName(product[y - 1].getName());
                    product[y].setProductID(product[y - 1].getProductID());
                    product[y].setPrice(product[y - 1].getPrice());
                    product[y].setWeight(product[y - 1].getWeight());
                    product[y].setCalories(product[y - 1].getCalories());
                    product[y].setQuantity(product[y - 1].getQuantity());
                    product[y].setCategory(product[y - 1].getCategory());

                    product[y - 1].setName(tempName);
                    product[y - 1].setProductID(tempProductID);
                    product[y - 1].setPrice(tempPrice);
                    product[y - 1].setWeight(tempWeight);
                    product[y - 1].setCalories(tempCalories);
                    product[y - 1].setQuantity(tempQuantity);
                    product[y - 1].setCategory(tempCategory);
                }
            }
        }

        return product;
    }

    /**
     * Using bubble sort algorithm to compare IDs in product array.
     *
     * The current productID is compared with the next productID.
     *
     * If the current ID is less than the next ID, it is swapped.
     *
     * The process is repeated again from 0th to y-1 index.
     *
     * @return A Product[] data type.
     */
    public Products[] sortID() {

        Products[] product = new Products[count()];
        product = productArray();

        String tempName, tempWeight, tempCategory;
        int tempProductID, tempCalories, tempQuantity;
        double tempPrice;

        for (int x = 1; x < count(); x++) {
            for (int y = x; y > 0; y--) {
                if (product[y].getProductID() < ((product[y - 1].getProductID()))) {
                    tempName = product[y].getName();
                    tempProductID = product[y].getProductID();
                    tempPrice = product[y].getPrice();
                    tempWeight = product[y].getWeight();
                    tempCalories = product[y].getCalories();
                    tempQuantity = product[y].getQuantity();
                    tempCategory = product[y].getCategory();

                    product[y].setName(product[y - 1].getName());
                    product[y].setProductID(product[y - 1].getProductID());
                    product[y].setPrice(product[y - 1].getPrice());
                    product[y].setWeight(product[y - 1].getWeight());
                    product[y].setCalories(product[y - 1].getCalories());
                    product[y].setQuantity(product[y - 1].getQuantity());
                    product[y].setCategory(product[y - 1].getCategory());

                    product[y - 1].setName(tempName);
                    product[y - 1].setProductID(tempProductID);
                    product[y - 1].setPrice(tempPrice);
                    product[y - 1].setWeight(tempWeight);
                    product[y - 1].setCalories(tempCalories);
                    product[y - 1].setQuantity(tempQuantity);
                    product[y - 1].setCategory(tempCategory);
                }
            }
        }

        return product;
    }

    /**
     * Using bubble sort algorithm to compare price in product array.
     *
     * The current price is compared with the next price.
     *
     * If the current price is less than the next price, it is swapped.
     *
     * The process is repeated again from 0th to y-1 index.
     *
     * @return A Product[] data type.
     */
    public Products[] sortSmallestPrice() {

        Products[] product = new Products[count()];
        product = productArray();

        String tempName, tempWeight, tempCategory;
        int tempProductID, tempCalories, tempQuantity;
        double tempPrice;

        for (int x = 1; x < count(); x++) {
            for (int y = x; y > 0; y--) {
                if (product[y].getPrice() < ((product[y - 1].getPrice()))) {
                    tempName = product[y].getName();
                    tempProductID = product[y].getProductID();
                    tempPrice = product[y].getPrice();
                    tempWeight = product[y].getWeight();
                    tempCalories = product[y].getCalories();
                    tempQuantity = product[y].getQuantity();
                    tempCategory = product[y].getCategory();

                    product[y].setName(product[y - 1].getName());
                    product[y].setProductID(product[y - 1].getProductID());
                    product[y].setPrice(product[y - 1].getPrice());
                    product[y].setWeight(product[y - 1].getWeight());
                    product[y].setCalories(product[y - 1].getCalories());
                    product[y].setQuantity(product[y - 1].getQuantity());
                    product[y].setCategory(product[y - 1].getCategory());

                    product[y - 1].setName(tempName);
                    product[y - 1].setProductID(tempProductID);
                    product[y - 1].setPrice(tempPrice);
                    product[y - 1].setWeight(tempWeight);
                    product[y - 1].setCalories(tempCalories);
                    product[y - 1].setQuantity(tempQuantity);
                    product[y - 1].setCategory(tempCategory);
                }
            }
        }

        return product;
    }

    /**
     * Using bubble sort algorithm to compare price in product array.
     *
     * The current price is compared with the next price.
     *
     * If the current price is greater than the next price, it is swapped.
     *
     * The process is repeated again from 0th to y-1 index.
     *
     * @return A Product[] data type.
     */
    public Products[] sortLargestPrice() {

        Products[] product = new Products[count()];
        product = productArray();

        String tempName, tempWeight, tempCategory;
        int tempProductID, tempCalories, tempQuantity;
        double tempPrice;

        for (int x = 1; x < count(); x++) {
            for (int y = x; y > 0; y--) {
                if (product[y].getPrice() > ((product[y - 1].getPrice()))) {
                    tempName = product[y].getName();
                    tempProductID = product[y].getProductID();
                    tempPrice = product[y].getPrice();
                    tempWeight = product[y].getWeight();
                    tempCalories = product[y].getCalories();
                    tempQuantity = product[y].getQuantity();
                    tempCategory = product[y].getCategory();

                    product[y].setName(product[y - 1].getName());
                    product[y].setProductID(product[y - 1].getProductID());
                    product[y].setPrice(product[y - 1].getPrice());
                    product[y].setWeight(product[y - 1].getWeight());
                    product[y].setCalories(product[y - 1].getCalories());
                    product[y].setQuantity(product[y - 1].getQuantity());
                    product[y].setCategory(product[y - 1].getCategory());

                    product[y - 1].setName(tempName);
                    product[y - 1].setProductID(tempProductID);
                    product[y - 1].setPrice(tempPrice);
                    product[y - 1].setWeight(tempWeight);
                    product[y - 1].setCalories(tempCalories);
                    product[y - 1].setQuantity(tempQuantity);
                    product[y - 1].setCategory(tempCategory);
                }
            }
        }

        return product;
    }

    /**
     * Resets the table data if the table has been sorted.
     */
    public void reset() {
        table.getItems().clear();
        getProducts();
    }
}
