<p align="center">
  <img width="250" height="200" src="https://github.com/rohan-bhautoo/Point-Of-Sales-System/blob/master/pointofsales/src/main/java/com/pointofsales/Image/POS-icon.png">
</p>
<h1 align="center">Point of Sales System</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-1.5.0-brightgreen.svg" />
  <img alt="Java" src="https://img.shields.io/badge/Java-ED8B00?logo=java&logoColor=white" />
  <img alt="JDK" src="https://img.shields.io/badge/JDK->=11.0.14-blue.svg" />
  <img alt="JavaFX-SDK" src="https://img.shields.io/badge/JavaFX_SDK->=17.0.2-blue.svg" />
</p>

## Description
> A point of sale (POS) system is the place where your customer makes a payment for products or services at your store. With this idea, an application was designed and built using Java alongside with JavaFX to create a Graphical User Interface (GUI) for a more user-friendly app. See images of the application in the [Screenshot](/Screenshots) folder.
> 
> The application consists of a buy, add, remove, multi-search and sorting by multiple fields options. 
> 
> JavaFX is a set of graphics and media packages that enables developers to design, create, test, debug and deploy rich client applications that operate consistently across diverse platforms.
> 
> Features of JavaFX:
> - Written in Java – The JavaFX library is written in Java.
> - Built-in UI controls – JavaFX library caters UI controls which can be used to develop a full-featured application.
> - CSS like Styling – JavaFX provides CSS styling to improve the design of the application.
> - Rich set of APIs– JavaFX library provides a rich set of APIs to develop GUI applications, 2D and 3D graphics.

### 🏠 [Homepage](/pointofsales/src/main/java/com/pointofsales/PointOfSales.java)
<p align="center">
  <img height="400" src="https://github.com/rohan-bhautoo/Point-Of-Sales-System/blob/master/Screenshots/Main.png">
</p>

## Prerequisite

### JavaFX
> JavaFX is an open source, next generation client application platform for desktop, mobile and embedded systems built on Java. Download it [here](https://openjfx.io/).
> 
> For Linux:
```sh
export PATH_TO_FX=path/to/javafx-sdk-17/lib
```
> For Windows:
```sh
set PATH_TO_FX="path\to\javafx-sdk-17\lib"
```

### Java Development Kit (JDK) 
> JDK version 11 is used for this project as it includes the JavaFX library. Download it [here](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html).
> 
> For Windows:
```sh
set JAVA_HOME="C:\[Path to folder]\Java\jdk-11.0.14
```
> Enter the Environment Vairables in System Properties.
> 
> Add **%JAVA_HOME%\bin** into Path.
```sh
%JAVA_HOME%\bin
```
<p align="center">
  <img height="400" src="https://github.com/rohan-bhautoo/Point-Of-Sales-System/blob/master/Screenshots/Env%20Variable.png">
</p>

## GUI Design

### Stage
> JavaFX Stage is the top-level JavaFX container. The Primary Stage is constructed by the platform. For this project, the Primary Stage is assigned to a stage called *window* in the start() method.
```java
@Override
    public void start(Stage primaryStage) {
        window = primaryStage;
          .
          .
          .
          .
        window.show();
}
```

> Additional Stages are also used for each feature:
```java
Stage window, buyWindow, cartWindow, continueBuy, receiptWindow, addWindow, removeWindow, searchWindow, sortWindow;
```

> The window is set to maximize by default when the user starts the application.
```java
window.setMaximized(true);
```

### Scene
> The JavaFX Scene is the container for all content in a scene graph. A layout like BorderPane, VBox, HBox, StackPane or GridPane must be specified in the scene to display all the contents to a Stage.
```java
BorderPane layout = new BorderPane();
layout.setTop(menuBar);
layout.setCenter(table);
layout.setLeft(middleMenu);
layout.setBottom(Bottom);
Scene scene = new Scene(layout);
window.setMaximized(true);
window.setScene(scene);
window.show();
```

> In the code above, a BorderPane is declared as layout and is set as the Scene.

### MenuBar
> A MenuBar control traditionally is placed at the very top of the user interface and some menu items are added to it.
```java
Menu fileMenu = new Menu("_File");
Menu editMenu = new Menu("_Edit");
Menu optionsMenu = new Menu("_Options");
Menu helpMenu = new Menu("_Help");
MenuItems(fileMenu, editMenu, optionsMenu, helpMenu);
MenuBar menuBar = new MenuBar();
menuBar.getMenus().addAll(fileMenu, editMenu, optionsMenu, helpMenu);
```

> The MenuBar is placed at the top of the layout by using BorderPane.
```java
BorderPane layout = new BorderPane();
layout.setTop(menuBar);
```

### Buttons
> A JavaFX Button control enables a JavaFX application to have some action events executed when the application user clicks the button. The button control can contain text and/or a graphic.
>
> Action Events are implemented using the .setOnAction() function.
```java
Button buyProducts = new Button("Buy Products");
ImageView buyimg = new ImageView(buyImg);
buyProducts.setGraphic(buyimg);
buyimg.setFitHeight(35);
buyimg.setFitWidth(35);
buyProducts.setStyle("-fx-background-size: cover; -fx-background-repeat: no-repeat;");
buyProducts.setFont(new Font(20.0));
buyProducts.setPrefSize(230, 50);
buyProducts.setOnAction(e -> buyProduct());
```

> In the code above, the setOnAction() contains a lambda expression ‘e -> buyProduct()’. A Java lambda expression is a function which can be created without belonging to any class. It can be passed around as if it was an object and executed on demand. 
> 
> Image is also added to the buy button using .setGraphic() and ImageView.
> 
> Below is how the image is imported from the Image folder.
```java
Image buyImg = new Image(new FileInputStream("/Image/buy.png")); 
```

> This size of the button can be adjusted using the .setPrefSize(230, 50) where 230 is the width and 50 is the height.

### TableView
> The JavaFX TableView enables data in file to be displayed inside a table.
```java
TableView<Products> table;
```

> The implementation of the TableView is added inside the tableList() method.
> 
> The data of each column’s cell is set to equal the value of the Constructor instance variables.
```java
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
```
> The *table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY)* function is used to set all the columns to the same width. This helps to remove unused spaces or columns in the table.

## Features
> The Point of Sales application consists of different functionality as seen below.

### Buy Products
> The Buy Products feature is used to select a product from the table, by double-clicking on the product chosen, then entering the amount bought. The price will be automatically calculated in the calculatePrice() method.
<p align="center">
  <img height="400" src="https://github.com/rohan-bhautoo/Point-Of-Sales-System/blob/master/Screenshots/Buy-products.png">
</p>

```java
double productPrice = price * Double.valueOf(buyAmountField.getText());
```     
        
> Then, the price is formatted to 2 decimal places using DecimalFormat.
```java
DecimalFormat df = new DecimalFormat("#.##");
String formatted = df.format(productPrice);
buyPriceField.setText(formatted);
```

> The price calculated is then stored in a text file called ‘totalPrice.txt’. New prices will be appended to the file.
>
> The total price of all products bought is then calculated.
```java
File file = new File("totalPrice.txt");
scanPrice = new Scanner(file);
double sum = 0.0;
while (scanPrice.hasNext()) {
  sum += scanPrice.nextDouble();
}
scanPrice.close();
```

> The products names are also stored in a text file and it is appended to a TextArea. Every time a user selects a product, it is automatically stored in the file and showed in the TextArea.
> 
> However, LinkedHashSet is used to prevent duplicate names to be stored in the file and to keep the insertion order.
```java
LinkedHashSet<String> lines = new LinkedHashSet<>();
```

> After all products are selected and the user is ready to pay by pressing continue button, a TextInputDialog() is used to enter the money given by the user. Then the Change is calculated by:
```java
double Money_change = Double.valueOf(moneyField.getText()) - totalSum;
DecimalFormat df = new DecimalFormat("#.##");
String formatted = df.format(Money_change);
changeField.setText(formatted);
```

> Finally, after everything is complete, the program will print a receipt for the user.
<p align="center">
  <img height="300" src="https://github.com/rohan-bhautoo/Point-Of-Sales-System/blob/master/Screenshots/Receipt.png">
</p>

### Add Products
> The Add Products feature enables the user to add new products by writing to file.
<p align="center">
  <img height="400" src="https://github.com/rohan-bhautoo/Point-Of-Sales-System/blob/master/Screenshots/Add-products.png">
</p>

```java
bw.write(nameInput.getText() + " " + Integer.parseInt(productIDInput.getText()) + " " + Double.parseDouble(priceInput.getText())+ " " + weightInput.getText() + " " + Integer.parseInt(caloriesInput.getText()) + " " + Integer.parseInt(quantityInput.getText()) +   " " + categoryChoice.getValue());
```

> The information about the new product is written in a TextField, then written to file. The name, ID, price, weight, etc.… are split using a space in between them “ “.
> 
> Some verifications are also added to check for valid input of information. And if the user entered an existing name or ID, an error message will be displayed and it will not be added to the file.

### Remove Products
> Like the Add Products feature, the program has another feature called Remove Products to remove unwanted products from the file. The products are removed by specifying its ID.
> 
> In the remove() method, the integer searchResult variable is equal to -1, because the array starts at 0. Then the index of the ID entered by the user is equal to the searchResult. Another Products array called anotherProducts is created but with length – 1.
<p align="center">
  <img height="400" src="https://github.com/rohan-bhautoo/Point-Of-Sales-System/blob/master/Screenshots/Remove-products.png">
</p>

```java
Products[] anotherProducts = new Products[products.length - 1];
for (int x = 0, k = 0; x < products.length; x++) {
  if (x == searchResult) {
    continue;
  }
  anotherProducts[k++] = products[x];
}
```

> Here, if x is equal to searchResult which is the index of the ProductID, the iteration will increase by 1 in anotherProducts[k++]. This will skip the index of the searchResult to be written to file. Hence, the product will be removed.

### Search by
> The Search feature provides the user with 2 different options to search a product, by the product name or by the product ID.

#### Product Name
> When a product name is entered in the TextField, the searchName() method will see if the name exists in the product file. If the later does exists, all its information will be displayed, else nothing will be shown to the user.
<p align="center">
  <img height="400" src="https://github.com/rohan-bhautoo/Point-Of-Sales-System/blob/master/Screenshots/Search-by-name.png">
</p>

```java
if (inputSearch.equalsIgnoreCase((product[i].getName()))) {
  AlertBox.display("FOUND", "FOUND!\nName: " + product[i].getName() + "\nID: " + product[i].getProductID() + "\nPrice: RS " + product[i].getPrice() + "\nWeight: " + product[i].getWeight() + "\nCalories: " + product[i].getCalories() + "\nQuantity: " + product[i].getQuantity() + "\nCategory: " + product[i].getCategory());
}
```       
> In the code above, the .equalsIgnoreCase() enables the search result to display the information even if the case is not equal to the Product in the file. Ex: User input: yoP; In file: YOP. 
> Verification has also been implemented to check if user input is valid!

#### ProductID
> When a product ID is entered in the TextField, the searchID() method will see if the ID exists in the product file. If the later does exists, all its information will be displayed, else nothing will be shown to the user.
<p align="center">
  <img height="400" src="https://github.com/rohan-bhautoo/Point-Of-Sales-System/blob/master/Screenshots/Search-by-id.png">
</p>

```java
if (Integer.valueOf(inputSearch) == (product[i].getProductID())) {
  AlertBox.display("FOUND", "FOUND!\nID: " + product[i].getProductID() + "\nName: " + product[i].getName() + "\nPrice: RS " + product[i].getPrice() + "\nWeight: " + product[i].getWeight() + "\nCalories: " + product[i].getCalories() + "\nQuantity: " + product[i].getQuantity() + "\nCategory: " + product[i].getCategory());
}
```

### Sort Products
> The Sort Products feature uses 4 bubble sort algorithms. Therefore, the user has 4 different options to sort the file. It also contains a Reset function which clears the sorted algorithms and sets it back to the initial order.
<p align="center">
  <img height="400" src="https://github.com/rohan-bhautoo/Point-Of-Sales-System/blob/master/Screenshots/Sort-products.png">
</p>

#### Sort by Name
> The bubble sort algorithm is used to compare the name stored in the product array.
> 
> The compareTo will return:
> 
> -	0 – If the Strings are identical.
> - Positive Number – If the 1st String is bigger than the 2nd String.
> - Negative Number – If the 1st String is smaller than the 2nd String.
> 
> Hence, if the number is less than 0, it is swapped. This process is repeated again from 0th to y-1 index.

```java
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
```

#### Sort by ProductID
> Using bubble sort algorithm to compare IDs in product array. The current productID is compared with the next productID. If the current ID is less than the next ID, it is swapped.
> The process is repeated from 0th to y-1 index.
>
> The same process is applied to the sorting of smallest price.
```java
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
```

#### Sort by Largest Price   
> The same algorithm is implemented to sort the prices by largest. But the prices will swap only if the 2nd  Price is greater than the 1st Price.
```java
if (product[y].getPrice() > ((product[y - 1].getPrice()))) {       
  .
  .
  .
}
```

## Usage
> Compile the [PointOfSales.java](/pointofsales/src/main/java/com/pointofsales/PointOfSales.java) file.
> 
> For Linux:
```sh
javac --module-path $PATH_TO_FX --add-modules javafx.controls PointOfSales.java
```

> For Windows:
```sh
javac --module-path %PATH_TO_FX% --add-modules javafx.controls PointOfSales.java
```

> ** Make sure to add the required modules **
> 
> Run the application
> 
> For Linux:
```sh
java --module-path $PATH_TO_FX --add-modules javafx.controls PointOfSales.java
```

> For Windows:
```sh
java --module-path %PATH_TO_FX% --add-modules javafx.controls PointOfSales.java
```

## Author

👤 **Rohan Bhautoo**

* Github: [@rohan-bhautoo](https://github.com/rohan-bhautoo)
* LinkedIn: [@rohan-bhautoo](https://linkedin.com/in/rohan-bhautoo)

## Show your support

Give a ⭐️ if this project helped you!
