package Sanashopping;

import java.sql.Date;
import java.sql.*;
import java.util.*;


/***      NOTE  THIS BEFORE SCROLLING DOWN!!     FIRST CREATE YOUR CLASS CONSTRUCTORS FOR GOODS, SUPPLIERS, SALES OR ANY OTHER FEATURES YOU WANT ADD */

/*** NOTE IN MY CASE WAS ProductS,SALEDETAILS,Vendor, */


public class collections {
    public Stack<Product> stackProduct;
    public Queue<Product> queueProduct;
    public List<Product> listgood;

    public HashMap<String, Vendor> venderdetails;
    public List<Product> Productlist;
    public List<String> cat14;
    public List<String> cat57;
    public List<String> cat811;
    public Map<Integer, Salesproduct> sales;
    /*******************************************************************************************/


    String gh = "d";
    private int rand;
    private String displayType;
    private Connection con;


    public collections() {

        stackProduct = new Stack<>();
        queueProduct = new LinkedList<>();
        venderdetails = new HashMap<>();
        listgood = new ArrayList<>();
        Productlist = new ArrayList<>();

        sales = new HashMap<>();


        cat14 = new ArrayList<>();
        cat57 = new ArrayList<>();
        cat811 = new ArrayList<>();


        cat14.add("Beverages");
        cat14.add("Bread/Bakery");
        cat14.add("Canned/Jarred Goods");
        cat14.add("Dairy");

        cat57.add("Dry/Baking Goods");
        cat57.add("Frozen Foods");
        cat57.add("Meat");
        cat811.add("Produce");
        cat811.add("Cleaners");
        cat811.add("Paper Goods");
        cat811.add("Personal Care");


    }

    public String getDisplayType() {
        return displayType;
    }
    /** queue****************************************************************************************/

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    /**
     * Add ap product to queue---------------------------------------------------------------------
     */
    public void addQueuegood(Product g) {

        queueProduct.add(g);
    }

    /**
     * Remove from QUEUE
     ********************************************************************************/
    public void removeQueue_good() {
        queueProduct.remove();

    }
    /** **********************QUEUE LINE ENDS*******************************************************************************************/


    /*** ********************STACK BEGINS******************************************************************************************/

    public Queue<Product> getQlist() {
        return queueProduct;
    }

    /**
     * stack   ADD
     */
    public void addgood(Product g) {

        stackProduct.push(g);


    }

    /**
     * ******************REMOVE FROM  STACK
     ***********************/
    public void removegood() throws SQLException {
        Product nw = stackProduct.pop();
        String deletestate = "delete from good where id=" + nw.getName();
        PreparedStatement stm = con.prepareStatement(deletestate);
        stm.executeUpdate();
        stm.close();


    }

    public void removegood_list(int a) throws SQLException {
        Product nw = listgood.remove(a);
        String deletestate = "delete from good where id=" + nw.getName();
        PreparedStatement stm = con.prepareStatement(deletestate);
        stm.executeUpdate();
        stm.close();


    }

    public void removegood_queue() throws SQLException {
        Product nw = queueProduct.remove();
        String deletestate = "delete from good where id=" + nw.getName();
        PreparedStatement stm = con.prepareStatement(deletestate);
        stm.executeUpdate();
        stm.close();


    }

    public int c_s() {
        return stackProduct.size();
    }

    public int l_s() {
        return listgood.size();
    }

    public int q_s() {
        return queueProduct.size();
    }

    public int a_l() {
        return Productlist.size();
    }
/*****



 / **************LIST FOR ADD CATEGORY 8 TO 11 ****************************************/

    /*** GET GOOD FROM stack***********************************/


    public Stack<Product> getgood() {
        return stackProduct;
    }

    /**
     * add
     */
    public void add_good_to_list(Product g) {
        listgood.add(g);
    }

    /**
     * remove
     */
    public void remove_good_list(int a) {
        listgood.remove(a);
    }

/**********END ************************************ **/


/********************** ADD******************/

    public List<Product> getgood_list() {
        return listgood;
    }

    /****************************        STORE VENDOR DETAILS USING HASHMAP***/
    public void Addvendor(String name, Vendor detail) {
        venderdetails.put(name, detail);
    }

    /****      sales record                   ******************     ***/
    public Map<Integer, Salesproduct> getSales() {
        return sales;
    }
/*************  GET SALE RECORDS ******/

    public void storesale(int id, Salesproduct s) {
        sales.put(id, s);
    }

    /********** Get vendors*********************/
    public HashMap<String, Vendor> Getvendors() {
        return venderdetails;
    }
/*************** end of hashmap****/

    public void removevendor(String n) {
        venderdetails.remove(n);
    }

    /********   DATABASE CONNECTION *******************************************/
    public void connect() throws Exception {
        if (con != null) return;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("driver connected");
        } catch (ClassNotFoundException e) {
            throw new Exception("driver not found");


        }
        String mysqlurl = "jdbc:mysql://localhost:3306/elite_inventory";
        con = DriverManager.getConnection(mysqlurl, "root", "manifest");

        System.out.println("conected to " + con);


    }

    /*** DISCONNECT***************************/
    public void disconnected() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("not close");
            }

        }
    }

    /****************************sAVE VENDORS**********************************/
    public void save_To_database_vendor() throws SQLException {


        String Doc = "insert into vendors (name,address,firm,contact_number,country,city) values(?,?,?,?,?,?)";

        PreparedStatement insertDoc = con.prepareStatement(Doc);
        Iterator hash = venderdetails.entrySet().iterator();
        for (HashMap.Entry<String, Vendor> e : venderdetails.entrySet()) {
            String name = e.getKey();
            String add = e.getValue().getName();
            int cont = e.getValue().getExtendedState();
            String comp = e.getValue().getTitle();
            String count = e.getValue().toString();
            String town = e.getValue().getWarningString();
            int size = venderdetails.size() + 1;
            insertDoc.setString(1, name);
            insertDoc.setString(2, add);

            insertDoc.setString(3, comp);
            insertDoc.setInt(4, cont);
            insertDoc.setString(5, count);
            insertDoc.setString(6, town);

            insertDoc.executeUpdate();
            System.out.print("vendor saved");

        }
        stackProduct.clear();
        insertDoc.close();
    }

    /********    adding to database  using *****************/
    public void save_To_database_stack() throws SQLException {

        int size = Productlist.size() + 1;
        String Doc = "insert into good(id,category,product,date,quantity,unit_price,sale_price,gross_price,vendorId) values(?,?,?,?,?,?,?,?,?)";

        PreparedStatement insertDoc = con.prepareStatement(Doc);

        for (Product g : stackProduct) {

            int id = g.getState();


            String cat = g.getName();
            String p = g.getTitle();

            String dat = String.valueOf(g.getState());
            int quan = g.getComponentCount();
            int up = g.getComponentCount();
            int sp = g.getState();
            int gp = g.getState();

            int ven = g.getExtendedState();

            insertDoc.setInt(1, size);
            System.out.println("inserted");
            insertDoc.setString(2, cat);
            insertDoc.setString(3, p);
            insertDoc.setDate(4, Date.valueOf(dat));
            insertDoc.setInt(5, quan);
            insertDoc.setDouble(6, up);
            insertDoc.setDouble(7, sp);
            insertDoc.setDouble(8, gp);

            insertDoc.setInt(9, ven);
            insertDoc.executeUpdate();
            System.out.println("inserted");


        }
        Productlist.clear();

        stackProduct.clear();
        insertDoc.close();

    }

    /**************** queues *********************************************************************/

    public void save_To_database_queue() throws SQLException {


        String Doc = "insert into good(id,category,product,date,quantity,unit_price,sale_price,gross_price,vendorId) values(?,?,?,?,?,?,?,?,?)";

        PreparedStatement insertDoc = con.prepareStatement(Doc);

        int size = Productlist.size() + 1;
        for (Product g : queueProduct) {

//            int id=g.getState();
//
//
//            String  cat=g.getCategory();
//            String p=g.getGood();
//            Date dat= (Date) g.getDate();
//            int quan=g.getQuant();
//            Double up=g.getUnit_price();
//            Double sp=g.getSaleprice();
//            Double gp=g.getGrossprice();
//
//            int ven=g.getVendor();


//                insertDoc.setInt(1,size);
//                insertDoc.setString(2,cat);
//
//                insertDoc.setString(3,p);
//                insertDoc.setDate(4,dat);
//                insertDoc.setInt(5,quan);
//                insertDoc.setDouble(6,up);
//                insertDoc.setDouble(7,sp);
//                insertDoc.setDouble(8,gp);
//
//                insertDoc.setInt(9,ven);
//                insertDoc.executeUpdate();


        }


        Productlist.clear();
        queueProduct.clear();
        insertDoc.close();

    }

    /********           Storing product with list ************/
    public void save_To_database_list() throws SQLException {


        String Doc = "insert into good(id,category,product,date,quantity,unit_price,sale_price,gross_price,vendorId) values(?,?,?,?,?,?,?,?,?)";
        int size = Productlist.size() + 1;

        PreparedStatement insertDoc = con.prepareStatement(Doc);
        System.out.println("list head");
        for (Product g : listgood) {
            System.out.println("dg" + Productlist.size());
            int id = g.getState();


            String cat = g.getName();
            String p = g.getTitle();

            int dat = g.getDefaultCloseOperation();
            int quan = g.getComponentCount();
            Double up = Double.valueOf(g.getExtendedState());
            Double sp = Double.valueOf(g.getComponentCount());
            Double gp = Double.valueOf(g.getExtendedState());

            int ven = g.getState();


            System.out.println("list");
            insertDoc.setInt(1, size);
            insertDoc.setString(2, cat);

            insertDoc.setString(3, p);
//                insertDoc.setDate(4,dat);
            insertDoc.setInt(5, quan);
            insertDoc.setDouble(6, up);
            insertDoc.setDouble(7, sp);
            insertDoc.setDouble(8, gp);

            insertDoc.setInt(9, ven);
            insertDoc.executeUpdate();
            System.out.println("list");
        }


        Productlist.clear();
        listgood.clear();
        insertDoc.close();

    }

    public void Load_data() throws SQLException {
        stackProduct.clear();
        Productlist.clear();
        queueProduct.clear();
        listgood.clear();
        String loader = "select * from good";
        Statement state = con.createStatement();
        ResultSet result = state.executeQuery(loader);
        while (result.next()) {
            int id = result.getInt("id");
            String cat = result.getString("category");
            String good = result.getString("product");
            Date date = result.getDate("date");
            int quan = result.getInt("quantity");
            Double unitprice = result.getDouble("unit_price");
            Double salep = result.getDouble("sale_price");
            Double grossp = result.getDouble("gross_price");

            int vendor = result.getInt("vendorId");

            gh = cat;
            if (cat14.contains(cat)) {
                stackProduct.clear();
                addgood(new Product());


            }
            if (cat57.contains(cat)) {
                queueProduct.clear();
                addQueuegood(new Product());


            }
            if (cat811.contains(cat)) {
                listgood.clear();
                add_good_to_list(new Product());

            }

            Productlist.add(new Product());


        }


        result.close();
        state.close();


    }

    public void Load_data_v() throws SQLException {
        venderdetails.clear();
        Getvendors().clear();
        String loader = "select * from vendors";
        Statement state = con.createStatement();
        ResultSet result = state.executeQuery(loader);
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            String address = result.getString("address");
            String company = result.getString("firm");
            int contact = result.getInt("contact_number");
            String country = result.getString("country");
            String city = result.getString("city");
            Addvendor(name, new Vendor());


        }


        for (Map.Entry<String, Vendor> e : Getvendors().entrySet()) {
            System.out.println("loaded to hash" + Getvendors().size());
        }
        result.close();
        state.close();


    }

    public void save_To_vsaleDate() throws SQLException {


        String Doc = "insert into  sales (product ,category ,quantity ,u_price ,price,customer,prodid) values(?,?,?,?,?,?,?)";

        PreparedStatement insertDoc = con.prepareStatement(Doc);

        for (Map.Entry<Integer, Salesproduct> e : sales.entrySet()) {
            int id = e.getKey();
            String cat = e.getValue().getName();
            String prod = e.getValue().getTitle();
            String cus = e.getValue().getName();
            int qty = e.getValue().getX();
            double u_p = e.getValue().getDefaultCloseOperation();
            double total = e.getValue().getExtendedState();
            int size = sales.size() + 1;
            insertDoc.setString(1, prod);
            insertDoc.setString(2, cat);

            insertDoc.setInt(3, qty);
            insertDoc.setDouble(4, u_p);
            insertDoc.setDouble(5, total);
            insertDoc.setString(6, cus);
            insertDoc.setInt(7, id);
            System.out.println(prod);

            insertDoc.executeUpdate();
            System.out.print("sales saved");

        }

        insertDoc.close();

    }


    public void update_vsaleD(int a, int b) throws SQLException {


        String Doc = "update good set quantity='" + a + "' where id='" + b + "'";

        PreparedStatement insertDoc = con.prepareStatement(Doc);

        insertDoc.executeUpdate();

        insertDoc.close();


    }


    public void Load_data_SALE() throws SQLException {


        String loader = "select * from sales";
        Statement state = con.createStatement();
        ResultSet result = state.executeQuery(loader);
        while (result.next()) {
            int id = result.getInt("id");
            String product = result.getString("product");
            String category = result.getString("category");
            int quan = result.getInt("quantity");
            Double up = result.getDouble("u_price");
            Double tp = result.getDouble("price");
            String cus = result.getString("customer");
            Date date = result.getDate("date");
            int fid = result.getInt("prodid");
            storesale(fid, new Salesproduct());

        }


        result.close();
        state.close();


    }


}


