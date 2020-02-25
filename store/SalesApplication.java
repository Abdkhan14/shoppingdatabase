package com.b07.store;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.exceptions.ConnectionFailedException;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.inventory.ItemImplement;
import com.b07.users.Admin;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.b07.users.User;


public class SalesApplication {
  /**
   * This is the main method to run your entire program! Follow the "Pulling it together"
   * instructions to finish this off.
   * 
   * @param argv unused.
   * @throws SQLException
   */
  public static void main(String[] argv) {
    Scanner scanner = new Scanner(System.in);
    String input = "";

    while (!input.equals("exit")) {
      System.out.println(
          "ENTER -1 to CREATE FIRST ACCOUNT, ENTER 1 TO GO INTO ADMIN MODE AND exit TO LEAVE APPLICATION OR ANYTHING ELSE TO MAKE CONTEXT MENU");
      input = scanner.nextLine();
      Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
      if (connection == null) {
        System.out.print("NOOO");
      }
      try {
        // -------------------INPUT IS -1-------
        if (input.equals(Integer.toString(-1))) {
          DatabaseDriverExtender.initialize(connection);
          System.out.println("PLEASE ENTER ADMIN'S NAME");
          String nameAdmin = scanner.nextLine();
          System.out.println("PLEASE ENTER ADMIN'S AGE, MUST BE A POSITIVE INTEGER");
          String adminage = scanner.nextLine();
          int Adminage = Integer.parseInt(adminage);
          System.out.println("PLEASE ENTER ADMIN'S ADDRESS");
          String Adminaddress = scanner.nextLine();
          System.out.println("PLEASE ENTER ADMIN'S PASSWORD");
          String Adminpass = scanner.nextLine();
          int userId =
              DatabaseInsertHelper.insertNewUser(nameAdmin, Adminage, Adminaddress, Adminpass);
          int roleId = DatabaseInsertHelper.insertRole("ADMIN");
          DatabaseInsertHelper.insertUserRole(userId, roleId);
          System.out.println(DatabaseSelectHelper.getRoleName(roleId) + " *DONE*");
          System.out.println("ADMIN ACCOUNT MADE AND USERID IS :" + userId + " MUST REMEMBER THIS");
          System.out.println("ADMIN ROLEID IS :" + DatabaseSelectHelper.getUserRoleId(userId));
          System.out.println("PLEASE ENTER EMPLOYEES NAME");
          String nameEmployee = scanner.nextLine();
          System.out.println("PLEASE ENTER ADMIN'S AGE, MUST BE A POSITIVE INTEGER");
          String employeeage = scanner.nextLine();
          int Employeeage = Integer.parseInt(employeeage);
          System.out.println("PLEASE ENTER ADMIN'S ADDRESS");
          String Employeeaddress = scanner.nextLine();
          System.out.println("PLEASE ENTER ADMIN'S PASSWORD");
          String Employeepass = scanner.nextLine();
          int userIdemp = DatabaseInsertHelper.insertNewUser(nameEmployee, Employeeage,
              Employeeaddress, Employeepass);
          int roleIdemp = DatabaseInsertHelper.insertRole("EMPLOYEE");
          DatabaseInsertHelper.insertUserRole(userIdemp, roleIdemp);
          System.out.println(DatabaseSelectHelper.getRoleName(roleIdemp) + " *DONE*");
          System.out.println("EMPLOYEE USERID IS :" + userIdemp + " MUST REMEMBER!");
          List<User> list = DatabaseSelectHelper.getUsersDetails();
          List<User> list2 = new ArrayList<>();
          for (User user : list) {
            int id = user.getId();
            int RoleID = DatabaseSelectHelper.getUserRoleId(id);
            String Rolename = DatabaseSelectHelper.getRoleName(RoleID);
            if (Rolename.equalsIgnoreCase("EMPLOYEE")) {
              list2.add(user);
            }
          }
          for (User user : list2) {
            System.out.println(user.getName());
          }
        } // -------------------INPUT IS 1--------------
        else if (input.equals(Integer.toString(1))) {
          List<User> UserList = DatabaseSelectHelper.getUsersDetails();
          List<Admin> adminList = new ArrayList<>();
          int i = 0;
          for (User user : UserList) {
            int id = user.getId();
            int roleId = DatabaseSelectHelper.getUserRoleId(id);
            i = roleId;
            if (roleId == 1) {
              adminList.add((Admin) user);
            }
          }
          System.out.println("NOW YOU HAVE TO LOGIN TO ACCES FEATURES!");
          System.out.println("TYPE IN YOUR ADMIN NAME: ");
          String name = scanner.nextLine();
          Admin admin = new Admin(1, "", 1, "");
          for (Admin user : adminList) {
            if (user.getName().equalsIgnoreCase(name)) {
              admin = user;
            }
          }
          System.out.println("TYPE IN YOU PASSWORD: ");
          String password = scanner.nextLine();
          if (admin.authenticate(password)) {
            System.out.println("THANK YOU FOR LOGING IN");
            System.out.println("DO YOU WANT TO PROMOTE EMPLOYEE OR ACCESS VIEWBOOK?");
            System.out.println("TYPE VIEWBOOK OR ANYTHING ELSE FOR PROMOTION");
            String inspv = scanner.nextLine();
            if (inspv.equalsIgnoreCase("VIEWBOOK")) {
              admin.viewbook();
            } else {
              Employee employee1 = null;
              System.out.println("ENTER THE NAME OF THE EMPLOYEE YOU WANT TO PROMOTE");
              String employeeName = scanner.nextLine();
              for (User user : UserList) {
                int id2 = user.getId();
                int roleId2 = DatabaseSelectHelper.getUserRoleId(id2);
                String roleName = DatabaseSelectHelper.getRoleName(roleId2);
                if (roleName.equals("EMPLOYEE") && user.getName().equalsIgnoreCase(employeeName)) {
                  employee1 = (Employee) user;
                  System.out.println("FOUND EMPLOYEE: " + user.getName());
                }
              }
              admin.setId(i);
              admin.promoteEmployee(employee1);
              System.out.println("The EMPLOYEE HAS BEEN PROMOTED TO AN ADMIN");
            }
          } else {
            System.out
                .println("INCORRECT PASSWORD OR NAME!PLEASE TRY AGAIN TO ACCESS ADMIN FEATURES");
            input = "exit";
          }
        } // ----------INPUT IS ANYTHING BUT 1, -1 AND EXIT -----------------------
        else if (!input.equals("exit")) {
          System.out.println(
              "1-EMPLOYEE LOGIN\n2-CUSTOMER LOGIN\n0-EXIT\nSELECTION HAS TO BE ONE OF THE ABOVE:");
          String ExitCondition = "";
          String selectionS = scanner.nextLine();
          int selection = Integer.parseInt(selectionS);
          while (!(ExitCondition.equalsIgnoreCase("Stop"))) {
            if (selection == 1) {
              System.out.println("ENTER THE USERID OF EMPLOYEE: ");
              String idSemployee = scanner.nextLine();
              int idemployee = Integer.parseInt(idSemployee);
              System.out.println("ENTER THE PASSWORD OF THE EMPLOYEE: ");
              String password = scanner.nextLine();
              int roleId = DatabaseSelectHelper.getUserRoleId(idemployee);
              String roleName = DatabaseSelectHelper.getRoleName(roleId);
              Employee employee = (Employee) DatabaseSelectHelper.getUserDetails(idemployee);

              if (roleName.equals("EMPLOYEE") && employee.authenticate(password)) {
                String intialend = "";
                while (!intialend.equals("Stop")) {
                  System.out.println("EMPLOYEE LOGGED IN!");
                  Inventory inventory = DatabaseSelectHelper.getInventory();
                  EmployeeInterface empInterface = new EmployeeInterface(employee, inventory);
                  System.out.println("1-AUTHENTICATE NEW EMPLOYEE\n2-MAKE NEW USER\n"
                      + "3-MAKE NEW ACCOUNT\n4-MAKE NEW EMPLOYEE\n5-RESTOCK INVENTORY\n6-EXIT\nSELECTION MUST BE ONE OF THE ABOVE:");
                  String choiceS = scanner.nextLine();
                  int choice = Integer.parseInt(choiceS);
                  if (choice == 1) {
                    System.out.println("ENTER THE USERID OF NEW EMPLOYEE: ");
                    String id2S = scanner.nextLine();
                    int id2 = Integer.parseInt(id2S);
                    System.out.println("ENTER THE PASSWORD OF THE EMPLOYEE");
                    String password2 = scanner.nextLine();
                    int roleId2 = DatabaseSelectHelper.getUserRoleId(id2);
                    String roleName2 = DatabaseSelectHelper.getRoleName(roleId2);
                    Employee employee2 = (Employee) DatabaseSelectHelper.getUserDetails(id2);
                    if (roleName2.equals("EMPLOYEE") && employee2.authenticate(password2)) {
                      System.out.println("NEW EMPLOYEE HAS BEEN AUTHENTICATED");
                      empInterface.seCurrenttEmployee(employee2);
                    } else {
                      System.out.println("INVALID LOGIN INFORMATION, PLEASE TRY AGAIN");
                    }
                  } else if (choice == 2) {
                    System.out.println("ENTER THE NEW USER'S NAME");
                    String UserName1 = scanner.nextLine();
                    System.out.println("ENTER THE NEW CUSTOMER'S AGE, MUST BE A POISTIVE INTEGER");
                    String userageS1 = scanner.nextLine();
                    int userage1 = Integer.parseInt(userageS1);
                    System.out.println("ENTER THE NEW USER'S ADDRESS");
                    String UserAddress1 = scanner.nextLine();
                    System.out.println("ENTER THE NEW USER'S PASSWORD");
                    String UserPassword1 = scanner.nextLine();
                    int newId1 = DatabaseInsertHelper.insertNewUser(UserName1, userage1,
                        UserAddress1, UserPassword1);
                    int roleIdcus = DatabaseInsertHelper.insertRole("CUSTOMER");
                    DatabaseInsertHelper.insertUserRole(newId1, roleIdcus);
                    System.out
                        .println("SUCCESFULLY MADE A NEW CUSTOMER ACCOUNT WITH USERID TO REMEMBER: "
                            + newId1);
                  } else if (choice == 3) {
                    System.out.println("Enter the existing Customer's Id");
                    String userageS2 = scanner.nextLine();
                    int userage2 = Integer.parseInt(userageS2);
                    int accountID = DatabaseInsertHelper.insertAccount(userage2);
                    System.out.println(
                        "SUCCESFULLY MADE A NEW USER WITH ACCOUNTID TO REMEMBER: " + accountID);
                  } else if (choice == 4) {
                    System.out.println("PLEASE ENTER THE NEW EMPLOYEE'S NAME:");
                    String nameEmployee4 = scanner.nextLine();
                    System.out.println("PLEASE ENTER EMPLOYEES'S AGE MUST BE AN INT");
                    String employeeageS4 = scanner.nextLine();
                    int Employeeage4 = Integer.parseInt(employeeageS4);
                    System.out.println("PLEASE ENTER EMPLOYEE'S ADDRESS");
                    String Employeeaddress4 = scanner.nextLine();
                    System.out.println("PLEASE ENTER EMPLOYEE'S PASSWORD");
                    String Employeepass4 = scanner.nextLine();
                    int userIdemp4 = DatabaseInsertHelper.insertNewUser(nameEmployee4, Employeeage4,
                        Employeeaddress4, Employeepass4);
                    int roleIdemp4 = DatabaseInsertHelper.insertRole("EMPLOYEE");
                    DatabaseInsertHelper.insertUserRole(userIdemp4, roleIdemp4);
                    System.out.println("SUCCESFULLY MADE A NEW EMPLOYEE");
                  } else if (choice == 5) {
                    System.out
                        .println("ENTER  THE ITEM NAME THAT YOU WANT TO RESTOCK IN INVENTORY ");
                    String nameofItem = scanner.nextLine();
                    System.out.println("ENTER AMOUNT TO BE RESTOCKED BY IN INVENTORY: ");
                    String amountS = scanner.nextLine();
                    int amount = Integer.parseInt(amountS);
                    HashMap<Item, Integer> itemMap1 = new HashMap<>();
                    try {
                      itemMap1 = inventory.getItemMap();
                    } catch (NullPointerException e) {
                      System.out.println("SORRY BUT THE STORE HAS AN EMPTY INVENTORY");
                    }
                    List<String> itemList = new ArrayList<>();
                    itemList.add("FISHING_ROD");
                    itemList.add("HOCKEY_STICK");
                    itemList.add("SKATESRUNNING_SHOES");
                    itemList.add("PROTEIN_BAR");
                    Item item = new ItemImplement(10, "");
                    try {
                      for (Item itemS : itemMap1.keySet()) {
                        if (itemS.getName().equalsIgnoreCase(nameofItem)) {
                          item.setId(itemS.getId());
                          item.setName(itemS.getName());
                          item.setPrice(itemS.getPrice());
                          break;
                        }
                      }
                    } catch (NullPointerException e) {
                      System.out.println("EMPTY IVENTORY SO ITEMS NEED TO BE ADDED");
                    }
                    if (item.getName().equals("")) {
                      System.out.println("NO SUCH ITEM EXISTS,SO ADDING THE ITEM TO THE INVENTORY");
                      System.out.println("ENTER  THE ITEMNAME AGAIN IF YOU WANT TO RESTOCK ");
                      System.out.println(
                          "MUST BE ONE OF 'FISHING_ROD', 'HOCKEY_STICK', 'SKATESRUNNING_SHOES' ,'PROTEIN_BAR'");
                      String nameofItem1 = scanner.nextLine();
                      System.out
                          .println("TYPE NEW PRICE AND MUST BE PRECISE TO TWO DECIMAL PLACES:");
                      String input5 = scanner.nextLine();
                      BigDecimal price1 = new BigDecimal(input5);
                      int newitemId = DatabaseInsertHelper.insertItem(nameofItem1, price1);
                      DatabaseInsertHelper.insertInventory(newitemId, amount);
                      System.out
                          .println("SUCCESSFULLY ADDED THE NEW ITEM WITH ITEMID: " + newitemId);
                      ExitCondition = "Stop";
                    } else {
                      int itemId = item.getId();
                      int quantity = amount;
                      DatabaseUpdateHelper.updateInventoryQuantity(quantity, itemId);
                      System.out.println("INVENTORY HAS BEEN RESTOCKED with item and quantity: "
                          + item.getName() + " " + amount);
                      ExitCondition = "Stop";
                    }
                  } else if (choice == 6) {
                    intialend = "Stop";
                    ExitCondition = "Stop";
                  }
                }
              } else {
                System.out.println(
                    "Id inputted is either not of the employee or is invalid or doesn't match with password");
                ExitCondition = "Stop";
              }
            } else if (selection == 2) {
              String secondexit = "";
              while (!secondexit.equalsIgnoreCase("Stop")) {
                System.out.println("ENTER THE ID OF THE CUSTOMER: ");
                String idScustomer = scanner.nextLine();
                int idcustomer = Integer.parseInt(idScustomer);
                System.out.println("ENTER THE PASSWORD OF THE CUSTOMER: ");
                String password6 = scanner.nextLine();
                int roleIdcus = DatabaseSelectHelper.getUserRoleId(idcustomer);
                String roleNamecus1 = DatabaseSelectHelper.getRoleName(roleIdcus);
                Customer customer = (Customer) DatabaseSelectHelper.getUserDetails(idcustomer);
                if (!(roleNamecus1.equalsIgnoreCase("CUSTOMER")
                    && customer.authenticate(password6))) {
                  System.out.println("SORRY, NON EXISTING CUSTOMER INFORMATION!");
                } else {
                  ShoppingCart shoppingCart = new ShoppingCart(customer);
                  List<Integer> accounts = DatabaseSelectHelper.getUserAccounts(customer.getId());
                  if (accounts.size() > 0) {
                    System.out.println("DEAR CUSTOMER, YOU ALREADY HAVE " + accounts.size()
                        + " EXISTING SHOPPING CARTS");
                    System.out
                        .println("DO YOU WANT TO USE ONE OF THE EXISTING ONES? IF SO TYPE YES");
                    String ins = scanner.nextLine();
                    if (ins.equalsIgnoreCase("YES")) {
                      System.out.println("ENTER WHICH ACCOUNT YOU WANT TO CHOOSE");
                      System.out.println("ACCOUNT NUMBER STARTS WITH 0 AND THUS MUST BE LESS THAN "
                          + accounts.size());
                      System.out.println("SO WHICH ACCOUNT DO YOU CHOOSE?");
                      String ins1S = scanner.nextLine();
                      int ins1 = Integer.parseInt(ins1S);
                      int accountId = accounts.get(ins1);
                      HashMap<Integer, Integer> a =
                          DatabaseSelectHelper.getAccountDetails(accountId);
                      for (Integer i : a.keySet()) {
                        shoppingCart.additem(DatabaseSelectHelper.getItem(i), a.get(i));
                      }
                      System.out.println("ENJOY USING YOUR EXISTING SHOPPING CART ITEMS!");
                    }
                  } else {
                    System.out.println("ENJOY SHOPPING WITH NEW!");
                  }
                  String end = "";
                  while (!end.equalsIgnoreCase("Stop")) {
                    if (roleNamecus1.equalsIgnoreCase("CUSTOMER")
                        && customer.authenticate(password6)) {
                      System.out.println(
                          "1.LIST THE CURRENT ITEMS IN CART\n2.ADD A QUANTITY OF AN ITEM TO THE CART\n"
                              + "3.CHECK THE TOTAL PRICE OF ITEMS IN THE CART\n4.REMOVE A QUANTITY OF AN ITEM FROM THE CART\n"
                              + "5.CHECK OUT\n6.EXIT");
                      String choiceS11 = scanner.nextLine();
                      int choiceS1 = Integer.parseInt(choiceS11);
                      if (choiceS1 == 1) {
                        System.out.println("THE LIST OF ITEMS AND QUATITY OF THEM IN CART ARE: ");
                        HashMap<Item, Integer> itemList11 = shoppingCart.getItemMap();
                        for (Item item : itemList11.keySet()) {
                          System.out.println(item.getName() + " ------------ "
                              + shoppingCart.getQuantitybyItem(item));
                        }
                      } else if (choiceS1 == 2) {
                        System.out.println("ENTER THE NAME OF THE ITEM YOU WANT TO BE ADDED: ");
                        String itemName20 = scanner.nextLine();
                        System.out.println("ENTER AMOUNT OF THE ITEM THAT YOU WANT TO BE ADDED: ");
                        String ItemAmount20S = scanner.nextLine();
                        int ItemAmount20 = Integer.parseInt(ItemAmount20S);
                        List<Item> items = DatabaseSelectHelper.getAllItems();
                        int count = 0;
                        for (Item item : items) {
                          if (item.getName().equals(itemName20)) {
                            count++;
                          }
                        }
                        if (count > 0) {
                          for (Item item : items) {
                            if (item.getName().equals(itemName20)) {
                              shoppingCart.additem(item, ItemAmount20);
                              System.out.println("ITEM SUCCESFULLY ADDED TO CART");
                              break;
                            }
                          }
                          List<Item> i = shoppingCart.getItems();
                          for (Item item : i) {
                            System.out.println("ITEMS ALREADY IN CART ARE: " + item.getName());
                          }
                        } else {
                          System.out.println("SORRY BUT NO SUCH ITEM EXISTS IN STORE TO BE ADDED");
                        }
                      } else if (choiceS1 == 3) {
                        System.out.println(
                            "THE TOTAL PRICE OF ALL ITEMS IN CART IS: " + shoppingCart.getTotal());
                      } else if (choiceS1 == 4) {
                        System.out.println("ENTER ITEM YOU WANT TO BE REMOVED: ");
                        String itemName40 = scanner.nextLine();
                        System.out.println("ENTER AMOUNT OF THE ITEM YOU WANT TO BE REMOVED: ");
                        String itemAmtS = scanner.nextLine();
                        int itemAmt = Integer.parseInt(itemAmtS);
                        List<Item> items = DatabaseSelectHelper.getAllItems();
                        int count = 0;
                        for (Item item : items) {
                          if (item.getName().equals(itemName40)) {
                            count++;
                          }
                        }
                        if (count > 0) {
                          for (Item item : items) {
                            if (item.getName().equals(itemName40)) {
                              shoppingCart.removeItem(item, itemAmt);
                              System.out.println("ITEM:" + item.getName()
                                  + " QUANTITY SUCCESFULLY REMOVED FROM CART");
                              break;
                            }
                          }
                        } else {
                          System.out
                              .println("Sorry but no such item exists in store to be REMOVED");
                          ExitCondition = "Stop";
                        }
                      } else if (choiceS1 == 5) {
                        // *******************ONLY METHOD LEFT TO DO********************
                        System.out.println(
                            "YOUR TOTAL PROCE FOR THE ITEMS IS: " + shoppingCart.getTotal());
                        System.out.println("DO YOU WISH TO CONTINUE WITH ANOTHER ORDER?");
                        System.out.println("TYPE YES OR NO");
                        String in = scanner.nextLine();
                        if (in.equalsIgnoreCase("NO")) {
                          if (shoppingCart.checkout()) {
                            System.out.println(
                                "REQUESTED QUANTITY HAS BEEN CHECKED OUT!THANK YOU FOR SHOPPING ");
                            secondexit = "Stop";
                          } else {
                            System.out.println("REQUESTED QUANTITY EXCEEDS WHAT THE STORE HAS");
                            System.out.println(
                                "DO YOU WANT TO CONTINUE WITH CHANGED QUANTITY?\n TYPE YES OR NO");
                            String what = scanner.nextLine();
                            if (what.equalsIgnoreCase("NO")) {
                              System.out.println("Sorry for the inconvenience, please come again");
                              end = "Stop";
                            }
                          }
                        } else {
                          System.out.println("PLEASE CONTINUE WITH NEW ORDER");
                        }
                      } else if (choiceS1 == 6) {
                        List<Integer> acc = DatabaseSelectHelper.getUserAccounts(customer.getId());
                        List<HashMap<Integer, Integer>> h1 = new ArrayList<>();
                        if (acc.size() > 0) {
                          for (Integer accid : acc) {
                            HashMap<Integer, Integer> h = new HashMap<>();
                            h = DatabaseSelectHelper.getAccountDetails(accid);
                            if (h.isEmpty()) {
                              h1.add(h);
                            }
                          }
                          if (h1.isEmpty()) {
                            System.out.println(" YOU ALREADY SAVED A SHOPPING CART");
                            System.out.println("EXITING, PLEASE COME SHOP AGAIN");
                            ExitCondition = "Stop";
                            end = "Stop";
                          } else {
                            System.out.println(
                                "BEFORE EXITING DO YOU WANT TO SAVE THE ACCOUNT YOU MADE?");
                            System.out.println("TYPE YES OR NO");
                            String nn = scanner.nextLine();
                            if (nn.equalsIgnoreCase("YES")) {
                              System.out.println(
                                  "DEAR CUSTOMER, YOU MADE " + accounts.size() + "  ACOOUNTS");
                              System.out.println("ENTER WHICH ACCOUNT YOU WANT TO CHOOSE");
                              System.out.println(
                                  "ACCOUNT NUMBER STARTS WITH 0 AND THUS MUST BE LESS THAN "
                                      + accounts.size());
                              System.out.println("SO WHICH ACCOUNT DO YOU CHOOSE?");
                              String ins1S = scanner.nextLine();
                              int ins1 = Integer.parseInt(ins1S);
                              int accountId = acc.get(ins1);
                              for (Item item : shoppingCart.getItems()) {
                                int itemId = item.getId();
                                int quantity = shoppingCart.getQuantitybyItem(item);
                                int a = DatabaseInsertHelper.insertAccountLine(accountId, itemId,
                                    quantity);
                                System.out.println("RECORD ID IS " + a);
                              }
                            }
                            System.out.println("EXITING, PLEASE COME SHOP AGAIN");
                            ExitCondition = "Stop";
                            end = "Stop";
                          }
                        }
                      }
                    } else {
                      System.out.println(
                          "The customer doesn't belong in the database or incorrect password is put in");
                      ExitCondition = "Stop";
                    }
                  }
                }
              }
            } else if (selection == 0) {
              ExitCondition = "Stop";
            }
          }
        } else {
          System.out.println("Exited the program");
        }
      } catch (ConnectionFailedException e) {
        // TODO Improve this!
        System.out.println("Something went wrong :(");
      } finally {
        try {
          connection.close();
        } catch (Exception e) {
          System.out.println("Looks like it was closed already :)");
        }
      }
    }
  }
}
