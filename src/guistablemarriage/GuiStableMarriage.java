package guistablemarriage;
//Stable Marriage Program for EECS 2500 Linear Data Structures
//Written By Allen Williams and Dustin Fitzpatrick
//Turned in October 12th, 2017
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;

public class GuiStableMarriage extends Application {
   public static Person[] men;
   public static Person[] women;
   public static int n;
   public static TextArea Output;
   
    static void propose(Person[] proposers, Person[] proposees) {
       boolean finished;
       finished = false;
       while (!finished) {
          finished = true;
          for (int i = 0; i < n; i++) {
              if(!proposers[i].isEngaged) {
                 proposers[i].propose();
              }
          }
          for (int i = 0; i < n; i++) {
              if (!proposees[i].isEngaged) finished = false;
          }
          
       }
   }
   static void addByName(String name, int i, Person[] proposers, 
           Person[] proposees) {
       for (int k = 0; k < n; k++) {
           if (proposers[k].getName().equals(name))
               proposees[i].addPreference(proposers[k]);
       }
   }

   static void readFile(String fileName) throws FileNotFoundException{
    String pref;
    File F = new File(fileName);
    Scanner fileIn = new Scanner(F);
    n = Integer.parseInt(fileIn.nextLine());
    men = new Person[n];
    women = new Person[n];
    for (int i = 0; i < n; i++) 
        men[i]=new Person(fileIn.nextLine());
    for (int i = 0; i < n; i++) 
        women[i]=new Person(fileIn.nextLine());
    
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            pref = fileIn.next();
            addByName(pref, i, women, men);          
        }
        fileIn.nextLine();
    }
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            pref = fileIn.next();
            addByName(pref, i, men, women);           
        }
        fileIn.nextLine();
    }
  }

   static void listInfo() {
      Output.setText("");
      Output.appendText("There are " + n + " men and women\n");
      Output.appendText("The men are : ");
      for(int i=0; i < n; i++)
         Output.appendText(men[i].getName()+ " ");
      Output.appendText("\nThe women are : ");
      for(int i=0; i< n; i++)
         Output.appendText(women[i].getName() + " ");
      Output.appendText("\n");
      for(int i = 0; i < 60; i++)
          Output.appendText("\u203E");
      
      for(int i = 0; i < n; i++) {
          Output.appendText("\n" + men[i].getName() + "'s Preferences are \n"); 
          men[i].listPreferences();
      }
      for(int i = 0; i < n; i++) {
          Output.appendText("\n" + women[i].getName() + 
                  "'s Preferences are \n"); 
          women[i].listPreferences();
      }
   }
   static void listMatches(String proposers) {
       Output.appendText("\nThe matches when the " + proposers + 
               " propose are\n");
      for (int i = 0; i < n; i++) {
          Output.appendText("(" + men[i].getName() + ", " + 
                  men[i].getMatchName() + ")\n");
      }
      for (int i = 0; i < n; i++) {
          men[i].voidMatch();
          women[i].voidMatch();
      }
   }
    @Override
    public void start(Stage primaryStage) {
        TextField txtFileName;
        Label lblInput, lblMen, lblWomen;
        HBox hbInput, hbRdio;
        RadioButton rdioMen, rdioWomen;
        Button btnRun, btnExit, btnInput;
        final ToggleGroup rdioButtons = new ToggleGroup();
        
        rdioMen = new RadioButton();
        rdioMen.setSelected(true);
        rdioMen.setToggleGroup(rdioButtons);
        rdioWomen = new RadioButton();
        rdioWomen.setToggleGroup(rdioButtons);
        lblMen = new Label("Men Propose :");
        lblWomen = new Label("Women Propose :");
        hbRdio = new HBox(10);
        hbRdio.getChildren().addAll(lblMen, rdioMen, lblWomen, rdioWomen);
        hbRdio.setAlignment(Pos.CENTER);
        txtFileName = new TextField();
        lblInput = new Label("Input File Name : ");
        hbInput = new HBox(10);
        hbInput.getChildren().addAll(lblInput, txtFileName);
        hbInput.setAlignment(Pos.CENTER);
        btnRun = new Button();
        btnRun.setText("Run Gale-Shapley");
        btnRun.setPrefWidth(140);
        btnExit = new Button();
        btnExit.setPrefWidth(140);
        btnExit.setText("Exit Program");
        btnInput = new Button();
        btnInput.setText("Load Input File");
        btnInput.setPrefWidth(140);
        btnExit.setOnAction(e -> {
           System.exit(0);
        });
        btnInput.setOnAction(e->{
            final String inputFile;
            inputFile = txtFileName.getText();
            try {
                readFile(inputFile);
                listInfo();
            } catch (FileNotFoundException ex) {
                Output.appendText("ERROR: FILE NOT FOUND");
            }
        });
        btnRun.setOnAction(e->{
            final boolean isConfirmed;
            isConfirmed = AlertBox.display("The data as read from the input" +
                    " file is displayed. \n Go ahead and apply the " + ""
                    + "Gale-Shapley algorithm\n to find a stable "+ ""
                    + "matching for these people?");
            if (rdioMen.isSelected() && (isConfirmed)) {
                propose(men, women);
                listMatches("men");
            }
            else if (rdioWomen.isSelected() && (isConfirmed)) {
                propose(women, men);
                listMatches("women");
            }
        });
        Output = new TextArea();
        Output.setMaxWidth(450);
        Output.setPrefHeight(200);
        Output.setEditable(false);
        VBox root = new VBox(20);
        root.getChildren().addAll(hbRdio, hbInput,btnInput, btnRun, btnExit,
                Output);
        root.setAlignment(Pos.CENTER);        
        Scene scene = new Scene(root, 500, 500);
        scene.getStylesheets().add("guistablemarriage/Style.css");        
        primaryStage.setTitle("Stable Marriage");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
