/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Project_LendMe;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Anja, Katharina
 */
public class GUI extends javax.swing.JFrame implements Runnable {
    
    private final DatabaseHelper hp = new DatabaseHelper();
    private final Rental_Helper rentalHelper = new Rental_Helper();
    private final ArchivHelper archHelper = new ArchivHelper();
    
    
     /**
     * Creates new form GUI
     */
    public GUI() {
        
        initComponents();
        
        fillDropdowns();
        
        listenForSelectionPN();
        listenForSelectionM();
        listenForSelectionIN();
        listenForSelectionUID();
        listenForSelectionAID();
        
        
    }
    
    
    public final void fillDropdowns() {
        
        rentalHelper.fillComboBox_Category(productname_newrental, "productName");
        rentalHelper.fillComboBox_Category(inventorynumber_newrental, "inventoryNumber");
        rentalHelper.fillComboBox_Category(manufacturer_newrental, "manufacturer");
        rentalHelper.fillUser_Year_Admin(userID_newrental,
                                            year_newrental, 
                                            administrator_newrental);
    }
    
    public final void listenForSelectionPN () {
            productname_newrental.addItemListener(new ItemListener () {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    
                    String selected = e.getItem().toString();
               
                    if (productname_newrental.getItemCount() == 0 ){
                        selected = ""; 
                        List <Devices> list = hp.getDevices();    
                        List <Object> nameList = hp.makeListForCategory(list, "productName");
                        productname_newrental.setModel(new DefaultComboBoxModel<>(nameList.toArray((new String[0]))));
                    }
                
                    if (!selected.isBlank()){
                        List <Devices> list = hp.getItemByProductName(selected);
                        String [] categories = new String [] {"manufacturer", "inventoryNumber"};

                        for (int i = 0; i < categories.length; i++) {
                            List <Object> oList = hp.makeListForCategory(list, categories[i]);
                            if (categories[i].equalsIgnoreCase("manufacturer")){
                                manufacturer_newrental.setModel
                            (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                            }
                            if (categories[i].equalsIgnoreCase("inventoryNumber")){
                                inventorynumber_newrental.setModel
                            (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                            }
                        }
                    }
                }
            }
        });
    }
    
    public final void listenForSelectionM () {
        manufacturer_newrental.addItemListener(new ItemListener () {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                     String selected = e.getItem().toString();
                
                    if (manufacturer_newrental.getItemCount() == 0 ){
                        selected = ""; 
                        List <Devices> list = hp.getDevices();    
                        List <Object> manuList = hp.makeListForCategory(list, "manufacturer");
                        manufacturer_newrental.setModel(new DefaultComboBoxModel<>(manuList.toArray((new String[0]))));
                    }
                    if (!selected.isBlank()){
                        List <Devices> list = hp.getItemByManufacturer(selected);
                        String [] categories = new String [] {"productName", "inventoryNumber"};

                        for (int i = 0; i < categories.length; i++) {
                            List <Object> oList = hp.makeListForCategory(list, categories[i]);
                            if (categories[i].equalsIgnoreCase("productName")){
                                productname_newrental.setModel
                            (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                            }
                        }
                    }
                }
            }
        }); 
    }
    
    public final void listenForSelectionIN () {
        inventorynumber_newrental.addItemListener(new ItemListener () {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    String selected = e.getItem().toString();
                
                    if (inventorynumber_newrental.getItemCount() == 0 ){
                        selected = ""; 
                        List <Devices> list = hp.getDevices();
                        List <Object> invNumbList = hp.makeListForCategory(list, "inventoryNumber");
                        inventorynumber_newrental.setModel(new DefaultComboBoxModel<>
                                            (invNumbList.toArray((new String[0]))));
                    }
                
                    if (!selected.isBlank() && hp.isNumeric(selected)){
                        List <Devices> list = hp.getItemByInvNumber(selected);
                        String [] categories = new String [] {"productName", "manufacturer"};

                        for (int i = 0; i < categories.length; i++) {
                            List <Object> oList = hp.makeListForCategory(list, categories[i]);
                            if (categories[i].equalsIgnoreCase("productName")){
                                productname_newrental.setModel
                                (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                            }
                            if (categories[i].equalsIgnoreCase("manufacturer")){
                                manufacturer_newrental.setModel
                                (new DefaultComboBoxModel<>(oList.toArray((new String [0]))));
                            }
                        }
                    }
                }  
            }
        }); 
    }
    
    public final void listenForSelectionUID () {
        userID_newrental.addItemListener(new ItemListener () {
            public void itemStateChanged(ItemEvent e) {
                String selected = e.getItem().toString();
                if ((!selected.isBlank() && hp.isNumeric(selected))){
                    Users userToCheck = hp.checkUserID(selected);
                    if (userToCheck != null && (!hp.isUserNew(selected))) {
                        userFirstName.setText(userToCheck.getUserFirstName());
                        userLastName.setText(userToCheck.getUserLastName());
                        userPhone.setText(userToCheck.getUserPhone());
                        userEmail.setText(userToCheck.getUserEmail());
                        year_newrental.setSelectedItem(userToCheck.getYear());
                        year_newrental.setEnabled(false);
                    } else if (hp.isUserNew(selected)) {
                        userFirstName.setText("");
                        userLastName.setText("");
                        userPhone.setText("");
                        userEmail.setText("");
                        year_newrental.setSelectedItem("");
                        year_newrental.setEnabled(true);
                    } 
                }
            }
        });
    }
    
    /*
        
    public Users createUser (){
        
        Users user = null;
        
        String id = userID_newrental.getEditor().getItem().toString();
        String firstname = userFirstName.getText();
        String lastname = userLastName.getText(); 
        String phone = userPhone.getText();
        String email = userEmail.getText();
        String year = year_newrental.getSelectedItem().toString();
        
        if (firstname.isBlank() || lastname.isBlank() || phone.isBlank() ||
                email.isBlank()){
            JOptionPane.showMessageDialog(null, 
                    "Achtung! Ein oder mehrere Textfelder sind leer!"
                            + " Bitte alles ausfüllen!");
        
        }else {
            user = new Users(Integer.parseInt(id), firstname, lastname, phone, 
                                email, year);
        }
        return user;
    }
    */
    
    public final void listenForSelectionAID() {
        administrator_newrental.addItemListener(new ItemListener () {
            public void itemStateChanged(ItemEvent e) {
                String selected = e.getItem().toString();
                if ((!selected.isBlank() && hp.isNumeric(selected))) {
                    adminFullName.setText(hp.getAdminNameByID(selected));
                }
            }
        });
    }
      
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toppenal = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        parentpanel = new javax.swing.JPanel();
        sidepanel = new javax.swing.JPanel();
        newrental = new javax.swing.JButton();
        rentallist = new javax.swing.JButton();
        archive = new javax.swing.JButton();
        inventory = new javax.swing.JButton();
        startpage = new javax.swing.JPanel();
        layerpane = new javax.swing.JLayeredPane();
        home_panel = new javax.swing.JPanel();
        newrental_panel = new javax.swing.JPanel();
        newrentaltitle = new javax.swing.JLabel();
        devicename = new javax.swing.JLabel();
        manufacturername = new javax.swing.JLabel();
        inventory_number = new javax.swing.JLabel();
        rentedby = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        productname_newrental = new javax.swing.JComboBox<>();
        manufacturer_newrental = new javax.swing.JComboBox<>();
        inventorynumber_newrental = new javax.swing.JComboBox<>();
        userID_newrental = new javax.swing.JComboBox<>();
        userFirstName = new javax.swing.JTextField();
        userLastName = new javax.swing.JTextField();
        userEmail = new javax.swing.JTextField();
        userPhone = new javax.swing.JTextField();
        year_newrental = new javax.swing.JComboBox<>();
        administrator_newrental = new javax.swing.JComboBox<>();
        date = new javax.swing.JLabel();
        rentalDate_newrental = new com.raven.datechooser.DateChooser();
        save_newrental = new javax.swing.JToggleButton();
        cancel_newrental = new javax.swing.JToggleButton();
        adminFullName = new javax.swing.JTextField();
        rentallist_panel = new javax.swing.JPanel();
        rentallist_title = new javax.swing.JLabel();
        searchfilter_rentallist = new javax.swing.JComboBox<>();
        filter_options_rentallist = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        rentallist_table = new javax.swing.JTable();
        archive_panel = new javax.swing.JPanel();
        archive_title = new javax.swing.JLabel();
        searchfilter_archive = new javax.swing.JComboBox<>();
        filter_options_archive = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        archive_table = new javax.swing.JTable();
        inventory_panel = new javax.swing.JPanel();
        newdevice_button = new javax.swing.JButton();
        inventory_title = new javax.swing.JLabel();
        searchfilter_inventory = new javax.swing.JComboBox<>();
        filter_options_inventory = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        inventory_table = new javax.swing.JTable();
        return_panel = new javax.swing.JPanel();
        productname_return = new javax.swing.JLabel();
        manufacturer_return = new javax.swing.JLabel();
        return_title = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        information_return = new javax.swing.JTextArea();
        imei_return = new javax.swing.JLabel();
        rentedby_return = new javax.swing.JLabel();
        date_return = new javax.swing.JLabel();
        administrator_return = new javax.swing.JLabel();
        returndate = new com.raven.datechooser.DateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        withdrawnby = new javax.swing.JComboBox<>();
        notestitle_return = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        notes_return = new javax.swing.JTextArea();
        defaultsettings = new javax.swing.JLabel();
        yes = new javax.swing.JRadioButton();
        no = new javax.swing.JRadioButton();
        save_return = new javax.swing.JToggleButton();
        cancel_return = new javax.swing.JToggleButton();
        newdevice_panel = new javax.swing.JPanel();
        newdevice_title = new javax.swing.JLabel();
        devicename_newdevice = new javax.swing.JLabel();
        manufacturer_newdevice = new javax.swing.JLabel();
        inventorynumber_newdevice = new javax.swing.JLabel();
        IMEInumber_newdevice = new javax.swing.JLabel();
        acquisitiondate_newdevice = new javax.swing.JLabel();
        acquisitionDate = new com.raven.datechooser.DateChooser();
        save_newdevice = new javax.swing.JToggleButton();
        cancel_newdevice = new javax.swing.JToggleButton();
        manufacturer = new javax.swing.JTextField();
        productname = new javax.swing.JTextField();
        inventoryNumber = new javax.swing.JTextField();
        imei = new javax.swing.JTextField();
        room_newdevice = new javax.swing.JLabel();
        location = new javax.swing.JTextField();
        notes_newdevice = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        notes = new javax.swing.JTextArea();
        administrator_newdevice = new javax.swing.JLabel();
        administrator = new javax.swing.JComboBox<>();
        acquisitionvalue_newdevice = new javax.swing.JLabel();
        acquisitionValue = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(0, 0));

        toppenal.setBackground(new java.awt.Color(87, 121, 50));
        toppenal.setPreferredSize(new java.awt.Dimension(802, 110));

        jTextField1.setBackground(new java.awt.Color(220, 229, 211));
        jTextField1.setText("Suche");
        jTextField1.setToolTipText("Suche");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout toppenalLayout = new javax.swing.GroupLayout(toppenal);
        toppenal.setLayout(toppenalLayout);
        toppenalLayout.setHorizontalGroup(
            toppenalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, toppenalLayout.createSequentialGroup()
                .addContainerGap(248, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );
        toppenalLayout.setVerticalGroup(
            toppenalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toppenalLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        getContentPane().add(toppenal, java.awt.BorderLayout.PAGE_START);

        parentpanel.setBackground(new java.awt.Color(255, 255, 255));
        parentpanel.setPreferredSize(new java.awt.Dimension(802, 800));
        parentpanel.setLayout(new java.awt.BorderLayout());

        sidepanel.setBackground(new java.awt.Color(87, 121, 50));
        sidepanel.setMinimumSize(new java.awt.Dimension(100, 500));
        sidepanel.setPreferredSize(new java.awt.Dimension(250, 300));
        sidepanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        newrental.setBackground(new java.awt.Color(55, 57, 58));
        newrental.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        newrental.setForeground(new java.awt.Color(255, 255, 255));
        newrental.setText("Neuer Verleih");
        newrental.setBorderPainted(false);
        newrental.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        newrental.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        newrental.setIconTextGap(15);
        newrental.setPreferredSize(new java.awt.Dimension(250, 70));
        newrental.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newrentalActionPerformed(evt);
            }
        });
        sidepanel.add(newrental);
        newrental.getAccessibleContext().setAccessibleDescription("");

        rentallist.setBackground(new java.awt.Color(55, 57, 58));
        rentallist.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rentallist.setForeground(new java.awt.Color(255, 255, 255));
        rentallist.setText("Verleihliste");
        rentallist.setBorderPainted(false);
        rentallist.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        rentallist.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rentallist.setIconTextGap(15);
        rentallist.setPreferredSize(new java.awt.Dimension(250, 70));
        rentallist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentallistActionPerformed(evt);
            }
        });
        sidepanel.add(rentallist);

        archive.setBackground(new java.awt.Color(55, 57, 58));
        archive.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        archive.setForeground(new java.awt.Color(255, 255, 255));
        archive.setText("Archiv");
        archive.setToolTipText("");
        archive.setActionCommand("Verleihliste");
        archive.setBorderPainted(false);
        archive.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        archive.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        archive.setIconTextGap(15);
        archive.setPreferredSize(new java.awt.Dimension(250, 70));
        archive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archiveActionPerformed(evt);
            }
        });
        sidepanel.add(archive);

        inventory.setBackground(new java.awt.Color(55, 57, 58));
        inventory.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        inventory.setForeground(new java.awt.Color(255, 255, 255));
        inventory.setText("Inventar");
        inventory.setToolTipText("");
        inventory.setActionCommand("Verleihliste");
        inventory.setBorderPainted(false);
        inventory.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        inventory.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        inventory.setIconTextGap(15);
        inventory.setPreferredSize(new java.awt.Dimension(250, 70));
        inventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inventoryActionPerformed(evt);
            }
        });
        sidepanel.add(inventory);

        parentpanel.add(sidepanel, java.awt.BorderLayout.LINE_START);
        sidepanel.getAccessibleContext().setAccessibleName("");

        startpage.setBackground(new java.awt.Color(220, 229, 211));

        layerpane.setLayout(new java.awt.CardLayout());

        home_panel.setBackground(new java.awt.Color(220, 229, 211));

        javax.swing.GroupLayout home_panelLayout = new javax.swing.GroupLayout(home_panel);
        home_panel.setLayout(home_panelLayout);
        home_panelLayout.setHorizontalGroup(
            home_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 665, Short.MAX_VALUE)
        );
        home_panelLayout.setVerticalGroup(
            home_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 957, Short.MAX_VALUE)
        );

        layerpane.add(home_panel, "card7");

        newrental_panel.setBackground(new java.awt.Color(220, 229, 211));

        newrentaltitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        newrentaltitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newrentaltitle.setText("Neuer Verleih");

        devicename.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        devicename.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        devicename.setText("Gerätename");

        manufacturername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manufacturername.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        manufacturername.setText("Herstellerbezeichnung");

        inventory_number.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        inventory_number.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        inventory_number.setText("Inventarnummer");

        rentedby.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rentedby.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rentedby.setText("Verliehen von");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel38.setText("Verleihen an");

        productname_newrental.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        productname_newrental.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4", " " }));

        manufacturer_newrental.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manufacturer_newrental.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        inventorynumber_newrental.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        inventorynumber_newrental.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        userID_newrental.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userID_newrental.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        userID_newrental.setToolTipText("Matrikelnummer");

        userFirstName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userFirstName.setText("Vorname");
        userFirstName.setToolTipText("Vorname");

        userLastName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userLastName.setText("Nachname");
        userLastName.setToolTipText("Nachname");

        userEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userEmail.setText("E-Mail");
        userEmail.setToolTipText("E-Mail");
        userEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userEmailActionPerformed(evt);
            }
        });

        userPhone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userPhone.setText("Telefonnummer");
        userPhone.setToolTipText("Telefonnummer");
        userPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userPhoneActionPerformed(evt);
            }
        });

        year_newrental.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        year_newrental.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Jahrgang" }));

        administrator_newrental.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        administrator_newrental.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        administrator_newrental.setToolTipText("Admin-ID");

        date.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        date.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        date.setText("Datum");

        rentalDate_newrental.setForeground(new java.awt.Color(87, 121, 50));
        rentalDate_newrental.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        save_newrental.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        save_newrental.setText("Speichern");
        save_newrental.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_newrentalActionPerformed(evt);
            }
        });

        cancel_newrental.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cancel_newrental.setText("Abbrechen");
        cancel_newrental.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_newrentalActionPerformed(evt);
            }
        });

        adminFullName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        adminFullName.setText("Vor- und Nachname");
        adminFullName.setToolTipText("");

        javax.swing.GroupLayout newrental_panelLayout = new javax.swing.GroupLayout(newrental_panel);
        newrental_panel.setLayout(newrental_panelLayout);
        newrental_panelLayout.setHorizontalGroup(
            newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newrental_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newrental_panelLayout.createSequentialGroup()
                        .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rentedby, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(newrental_panelLayout.createSequentialGroup()
                                .addComponent(save_newrental, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(cancel_newrental, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(administrator_newrental, 0, 307, Short.MAX_VALUE)
                            .addComponent(rentalDate_newrental, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(adminFullName)))
                    .addGroup(newrental_panelLayout.createSequentialGroup()
                        .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(manufacturername, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(devicename, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inventory_number, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(newrental_panelLayout.createSequentialGroup()
                                .addComponent(userPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(year_newrental, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(userEmail)
                            .addComponent(userID_newrental, 0, 307, Short.MAX_VALUE)
                            .addGroup(newrental_panelLayout.createSequentialGroup()
                                .addComponent(userFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(userLastName))
                            .addComponent(productname_newrental, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(manufacturer_newrental, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inventorynumber_newrental, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(newrentaltitle, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(111, 141, Short.MAX_VALUE))
        );
        newrental_panelLayout.setVerticalGroup(
            newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newrental_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(newrentaltitle, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(devicename)
                    .addComponent(productname_newrental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manufacturername)
                    .addComponent(manufacturer_newrental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inventory_number)
                    .addComponent(inventorynumber_newrental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(userID_newrental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(userEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(year_newrental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rentedby)
                    .addComponent(administrator_newrental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adminFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(date)
                    .addComponent(rentalDate_newrental, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(newrental_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(save_newrental)
                    .addComponent(cancel_newrental))
                .addContainerGap(246, Short.MAX_VALUE))
        );

        layerpane.add(newrental_panel, "card2");

        rentallist_panel.setBackground(new java.awt.Color(220, 229, 211));

        rentallist_title.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rentallist_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rentallist_title.setText("Aktuell verliehen");

        searchfilter_rentallist.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        searchfilter_rentallist.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        searchfilter_rentallist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfilter_rentallistActionPerformed(evt);
            }
        });

        filter_options_rentallist.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filter_options_rentallist.setText("Filterungs- und Sortiermöglichkeiten:");

        jScrollPane6.setBackground(new java.awt.Color(220, 229, 211));

        rentallist_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Aktuell verliehene Geräte"
            }
        ));
        jScrollPane6.setViewportView(rentallist_table);

        javax.swing.GroupLayout rentallist_panelLayout = new javax.swing.GroupLayout(rentallist_panel);
        rentallist_panel.setLayout(rentallist_panelLayout);
        rentallist_panelLayout.setHorizontalGroup(
            rentallist_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rentallist_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(rentallist_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
                    .addComponent(rentallist_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, rentallist_panelLayout.createSequentialGroup()
                        .addComponent(filter_options_rentallist)
                        .addGap(20, 20, 20)
                        .addComponent(searchfilter_rentallist, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(141, Short.MAX_VALUE))
        );
        rentallist_panelLayout.setVerticalGroup(
            rentallist_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rentallist_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(rentallist_title, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(rentallist_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchfilter_rentallist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filter_options_rentallist))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(219, Short.MAX_VALUE))
        );

        layerpane.add(rentallist_panel, "card3");

        archive_panel.setBackground(new java.awt.Color(220, 229, 211));

        archive_title.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        archive_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        archive_title.setText("Archiv");

        searchfilter_archive.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        searchfilter_archive.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        searchfilter_archive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfilter_archiveActionPerformed(evt);
            }
        });

        filter_options_archive.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filter_options_archive.setText("Filterungs- und Sortiermöglichkeiten:");

        jScrollPane3.setBackground(new java.awt.Color(220, 229, 211));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(600, 680));

        archive_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        archive_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(archive_table);

        javax.swing.GroupLayout archive_panelLayout = new javax.swing.GroupLayout(archive_panel);
        archive_panel.setLayout(archive_panelLayout);
        archive_panelLayout.setHorizontalGroup(
            archive_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, archive_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(archive_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
                    .addComponent(archive_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, archive_panelLayout.createSequentialGroup()
                        .addComponent(filter_options_archive)
                        .addGap(20, 20, 20)
                        .addComponent(searchfilter_archive, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(141, Short.MAX_VALUE))
        );
        archive_panelLayout.setVerticalGroup(
            archive_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(archive_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(archive_title, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(archive_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchfilter_archive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filter_options_archive))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(219, Short.MAX_VALUE))
        );

        layerpane.add(archive_panel, "card4");

        inventory_panel.setBackground(new java.awt.Color(220, 229, 211));

        newdevice_button.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        newdevice_button.setText("Neues Device anlegen");
        newdevice_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newdevice_buttonActionPerformed(evt);
            }
        });

        inventory_title.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        inventory_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inventory_title.setText("Inventar");

        searchfilter_inventory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        searchfilter_inventory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        searchfilter_inventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfilter_inventoryActionPerformed(evt);
            }
        });

        filter_options_inventory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filter_options_inventory.setText("Filterungs- und Sortiermöglichkeiten:");

        jScrollPane4.setBackground(new java.awt.Color(220, 229, 211));

        inventory_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Inventar"
            }
        ));
        jScrollPane4.setViewportView(inventory_table);

        javax.swing.GroupLayout inventory_panelLayout = new javax.swing.GroupLayout(inventory_panel);
        inventory_panel.setLayout(inventory_panelLayout);
        inventory_panelLayout.setHorizontalGroup(
            inventory_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inventory_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(inventory_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane4)
                    .addComponent(inventory_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, inventory_panelLayout.createSequentialGroup()
                        .addComponent(newdevice_button, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(filter_options_inventory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchfilter_inventory, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        inventory_panelLayout.setVerticalGroup(
            inventory_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventory_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(inventory_title, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(inventory_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inventory_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchfilter_inventory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(filter_options_inventory))
                    .addComponent(newdevice_button))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(208, Short.MAX_VALUE))
        );

        layerpane.add(inventory_panel, "card5");

        return_panel.setBackground(new java.awt.Color(220, 229, 211));

        productname_return.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        productname_return.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        productname_return.setText("Produktname");

        manufacturer_return.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manufacturer_return.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        manufacturer_return.setText("Herstellerbezeichnung");

        return_title.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        return_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        return_title.setText("Rückgabe");

        information_return.setColumns(20);
        information_return.setRows(5);
        jScrollPane1.setViewportView(information_return);

        imei_return.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        imei_return.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        imei_return.setText("IMEI Nummer");

        rentedby_return.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rentedby_return.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rentedby_return.setText("Verliehen an");

        date_return.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        date_return.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        date_return.setText("Datum");

        administrator_return.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        administrator_return.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        administrator_return.setText("Verliehen von");

        returndate.setForeground(new java.awt.Color(87, 121, 50));
        returndate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Rücknahme durch");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Rückgabedatum");

        withdrawnby.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        withdrawnby.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        withdrawnby.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                withdrawnbyActionPerformed(evt);
            }
        });

        notestitle_return.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        notestitle_return.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        notestitle_return.setText("Notizen");

        notes_return.setColumns(20);
        notes_return.setRows(5);
        jScrollPane5.setViewportView(notes_return);

        defaultsettings.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        defaultsettings.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        defaultsettings.setText("Werkseinstellungen");

        yes.setBackground(new java.awt.Color(220, 229, 211));
        yes.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        yes.setText("Ja");
        yes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yesActionPerformed(evt);
            }
        });

        no.setBackground(new java.awt.Color(220, 229, 211));
        no.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        no.setText("Nein");

        save_return.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        save_return.setText("Speichern");
        save_return.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_returnActionPerformed(evt);
            }
        });

        cancel_return.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cancel_return.setText("Abbrechen");

        javax.swing.GroupLayout return_panelLayout = new javax.swing.GroupLayout(return_panel);
        return_panel.setLayout(return_panelLayout);
        return_panelLayout.setHorizontalGroup(
            return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(return_panelLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(return_title, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, return_panelLayout.createSequentialGroup()
                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(return_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, return_panelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(productname_return, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(manufacturer_return, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(imei_return, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rentedby_return, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(administrator_return, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(date_return, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(return_panelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(notestitle_return, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(defaultsettings, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(48, 48, 48)
                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(return_panelLayout.createSequentialGroup()
                        .addComponent(save_return, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(cancel_return, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(return_panelLayout.createSequentialGroup()
                        .addComponent(yes, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(no, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                        .addComponent(returndate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(withdrawnby, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane5)))
                .addGap(101, 101, 101))
        );
        return_panelLayout.setVerticalGroup(
            return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(return_panelLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(return_title, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(return_panelLayout.createSequentialGroup()
                        .addComponent(productname_return)
                        .addGap(10, 10, 10)
                        .addComponent(manufacturer_return)
                        .addGap(10, 10, 10)
                        .addComponent(imei_return)
                        .addGap(10, 10, 10)
                        .addComponent(rentedby_return)
                        .addGap(10, 10, 10)
                        .addComponent(date_return)
                        .addGap(10, 10, 10)
                        .addComponent(administrator_return))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(returndate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(withdrawnby, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(notestitle_return)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(defaultsettings)
                    .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(yes)
                        .addComponent(no)))
                .addGap(20, 20, 20)
                .addGroup(return_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(save_return)
                    .addComponent(cancel_return))
                .addGap(62, 62, 62))
        );

        layerpane.add(return_panel, "card6");

        newdevice_panel.setBackground(new java.awt.Color(220, 229, 211));

        newdevice_title.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        newdevice_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newdevice_title.setText("Neues Gerät");

        devicename_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        devicename_newdevice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        devicename_newdevice.setText("Gerätename");

        manufacturer_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        manufacturer_newdevice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        manufacturer_newdevice.setText("Herstellerbezeichnung");

        inventorynumber_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        inventorynumber_newdevice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        inventorynumber_newdevice.setText("Inventarnummer");

        IMEInumber_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        IMEInumber_newdevice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        IMEInumber_newdevice.setText("IMEI Nummer");

        acquisitiondate_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        acquisitiondate_newdevice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        acquisitiondate_newdevice.setText("Anschaffungsdatum");

        acquisitionDate.setForeground(new java.awt.Color(87, 121, 50));
        acquisitionDate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        save_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        save_newdevice.setText("Speichern");
        save_newdevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_newdeviceActionPerformed(evt);
            }
        });

        cancel_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cancel_newdevice.setText("Abbrechen");

        manufacturer.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        productname.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        inventoryNumber.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        imei.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        room_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        room_newdevice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        room_newdevice.setText("Raum");

        location.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        notes_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        notes_newdevice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        notes_newdevice.setText("Notizen");

        notes.setColumns(20);
        notes.setRows(5);
        jScrollPane2.setViewportView(notes);

        administrator_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        administrator_newdevice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        administrator_newdevice.setText("Administrator");

        administrator.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        administrator.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        administrator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                administratorActionPerformed(evt);
            }
        });

        acquisitionvalue_newdevice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        acquisitionvalue_newdevice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        acquisitionvalue_newdevice.setText("Anschaffungswert");

        acquisitionValue.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        javax.swing.GroupLayout newdevice_panelLayout = new javax.swing.GroupLayout(newdevice_panel);
        newdevice_panel.setLayout(newdevice_panelLayout);
        newdevice_panelLayout.setHorizontalGroup(
            newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newdevice_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newdevice_panelLayout.createSequentialGroup()
                        .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(manufacturer_newdevice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(devicename_newdevice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inventorynumber_newdevice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(IMEInumber_newdevice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(room_newdevice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(notes_newdevice, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(administrator_newdevice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(acquisitionvalue_newdevice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                        .addGap(40, 40, 40)
                        .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(manufacturer)
                            .addComponent(productname)
                            .addComponent(inventoryNumber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                            .addComponent(imei, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)
                            .addComponent(administrator, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(location)
                            .addComponent(acquisitionValue, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE))
                        .addGap(0, 154, Short.MAX_VALUE))
                    .addGroup(newdevice_panelLayout.createSequentialGroup()
                        .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(newdevice_title, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(newdevice_panelLayout.createSequentialGroup()
                                .addComponent(acquisitiondate_newdevice, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(newdevice_panelLayout.createSequentialGroup()
                                        .addComponent(save_newdevice, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(cancel_newdevice, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(acquisitionDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(19, 19, 19))))
        );
        newdevice_panelLayout.setVerticalGroup(
            newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newdevice_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(newdevice_title, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(devicename_newdevice)
                    .addComponent(productname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manufacturer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(manufacturer_newdevice))
                .addGap(10, 10, 10)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inventorynumber_newdevice)
                    .addComponent(inventoryNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IMEInumber_newdevice)
                    .addComponent(imei, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(room_newdevice)
                    .addComponent(location, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(notes_newdevice)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(administrator, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(administrator_newdevice))
                .addGap(10, 10, 10)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acquisitionvalue_newdevice)
                    .addComponent(acquisitionValue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(acquisitiondate_newdevice)
                    .addComponent(acquisitionDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(newdevice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(save_newdevice)
                    .addComponent(cancel_newdevice))
                .addGap(172, 172, 172))
        );

        layerpane.add(newdevice_panel, "card7");

        javax.swing.GroupLayout startpageLayout = new javax.swing.GroupLayout(startpage);
        startpage.setLayout(startpageLayout);
        startpageLayout.setHorizontalGroup(
            startpageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layerpane)
        );
        startpageLayout.setVerticalGroup(
            startpageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layerpane)
        );

        parentpanel.add(startpage, java.awt.BorderLayout.CENTER);

        getContentPane().add(parentpanel, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleName("jFrame");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void newrentalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newrentalActionPerformed
               
        layerpane.removeAll();
        layerpane.add(newrental_panel);
        layerpane.repaint();
        layerpane.revalidate();
        
    }//GEN-LAST:event_newrentalActionPerformed

    private void rentallistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentallistActionPerformed
                
        layerpane.removeAll();
        layerpane.add(rentallist_panel);
        layerpane.repaint();
        layerpane.revalidate();
    }//GEN-LAST:event_rentallistActionPerformed

    private void archiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiveActionPerformed
        
        layerpane.removeAll();
        layerpane.add(archive_panel);
        layerpane.repaint();
        layerpane.revalidate();
        
        
        archHelper.populateTable(archive_table, jScrollPane3);
        //archive_panel.setSize(630, 680);
    }//GEN-LAST:event_archiveActionPerformed

    private void inventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inventoryActionPerformed
        
        layerpane.removeAll();
        layerpane.add(inventory_panel);
        layerpane.repaint();
        layerpane.revalidate();
    }//GEN-LAST:event_inventoryActionPerformed

    private void searchfilter_archiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfilter_archiveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchfilter_archiveActionPerformed

    private void searchfilter_inventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfilter_inventoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchfilter_inventoryActionPerformed

    private void newdevice_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newdevice_buttonActionPerformed
        // TODO add your handling code here:
        
        layerpane.removeAll();
        layerpane.add(newdevice_panel);
        layerpane.repaint();
        layerpane.revalidate();
    }//GEN-LAST:event_newdevice_buttonActionPerformed

    private void searchfilter_rentallistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfilter_rentallistActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchfilter_rentallistActionPerformed

    private void userEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userEmailActionPerformed

    private void userPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userPhoneActionPerformed

    private void cancel_newrentalActionPerformed(java.awt.event.ActionEvent evt) {
        deleteAll();
    }   
    
    public void deleteAll(){
        
        productname_newrental.removeAllItems();
        manufacturer_newrental.removeAllItems();
        inventorynumber_newrental.removeAllItems();
           
        userID_newrental.setSelectedItem("");
        year_newrental.setSelectedItem("");
        year_newrental.setEnabled(true);
        administrator_newrental.setSelectedItem("");
        adminFullName.setText("Vor- und Nachname");
        userFirstName.setText("Vorname");
        userLastName.setText("Nachname");
        userPhone.setText("Telefon");
        userEmail.setText("E-Mail");
    }
    
    
    
    private void save_newrentalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_newrentalActionPerformed
        
        if (!userID_newrental.getEditor().getItem().toString().isBlank()){
            boolean createNewUser = true;
            if (hp.isUserNew(userID_newrental.getEditor().getItem().toString())){
                Users user = rentalHelper.createUser(userID_newrental, userFirstName, userLastName,
                                                        userPhone, userEmail, year_newrental);
                
                if (user == null){
                    createNewUser = false;
                }
                if (createNewUser){
                    hp.insertNewUser(user);
                    JOptionPane.showMessageDialog(null, "Neuen User hinzugefügt");
                }   
                
            } else {
                Users user = rentalHelper.createUser(userID_newrental, userFirstName, userLastName,
                                                        userPhone, userEmail, year_newrental);
                
                if (user == null){
                    createNewUser = false;
                }
            }
            if (createNewUser){
                rentalHelper.createNewRental(rentalDate_newrental, inventorynumber_newrental,
                                            userID_newrental, administrator_newrental);
            }
        } else {
            JOptionPane.showMessageDialog(null, "UserID/Matrikelnummer angeben!");
        }
        deleteAll();
    }//GEN-LAST:event_save_newrentalActionPerformed

    private void save_newdeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_newdeviceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_save_newdeviceActionPerformed

    private void administratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_administratorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_administratorActionPerformed

    private void withdrawnbyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_withdrawnbyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_withdrawnbyActionPerformed

    private void save_returnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_returnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_save_returnActionPerformed

    private void yesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yesActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IMEInumber_newdevice;
    private com.raven.datechooser.DateChooser acquisitionDate;
    private javax.swing.JTextField acquisitionValue;
    private javax.swing.JLabel acquisitiondate_newdevice;
    private javax.swing.JLabel acquisitionvalue_newdevice;
    private javax.swing.JTextField adminFullName;
    private javax.swing.JComboBox<String> administrator;
    private javax.swing.JLabel administrator_newdevice;
    private javax.swing.JComboBox<String> administrator_newrental;
    private javax.swing.JLabel administrator_return;
    private javax.swing.JButton archive;
    private javax.swing.JPanel archive_panel;
    private javax.swing.JTable archive_table;
    private javax.swing.JLabel archive_title;
    private javax.swing.JToggleButton cancel_newdevice;
    private javax.swing.JToggleButton cancel_newrental;
    private javax.swing.JToggleButton cancel_return;
    private javax.swing.JLabel date;
    private javax.swing.JLabel date_return;
    private javax.swing.JLabel defaultsettings;
    private javax.swing.JLabel devicename;
    private javax.swing.JLabel devicename_newdevice;
    private javax.swing.JLabel filter_options_archive;
    private javax.swing.JLabel filter_options_inventory;
    private javax.swing.JLabel filter_options_rentallist;
    private javax.swing.JPanel home_panel;
    private javax.swing.JTextField imei;
    private javax.swing.JLabel imei_return;
    private javax.swing.JTextArea information_return;
    private javax.swing.JButton inventory;
    private javax.swing.JTextField inventoryNumber;
    private javax.swing.JLabel inventory_number;
    private javax.swing.JPanel inventory_panel;
    private javax.swing.JTable inventory_table;
    private javax.swing.JLabel inventory_title;
    private javax.swing.JLabel inventorynumber_newdevice;
    private javax.swing.JComboBox<String> inventorynumber_newrental;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLayeredPane layerpane;
    private javax.swing.JTextField location;
    private javax.swing.JTextField manufacturer;
    private javax.swing.JLabel manufacturer_newdevice;
    private javax.swing.JComboBox<String> manufacturer_newrental;
    private javax.swing.JLabel manufacturer_return;
    private javax.swing.JLabel manufacturername;
    private javax.swing.JButton newdevice_button;
    private javax.swing.JPanel newdevice_panel;
    private javax.swing.JLabel newdevice_title;
    private javax.swing.JButton newrental;
    private javax.swing.JPanel newrental_panel;
    private javax.swing.JLabel newrentaltitle;
    private javax.swing.JRadioButton no;
    private javax.swing.JTextArea notes;
    private javax.swing.JLabel notes_newdevice;
    private javax.swing.JTextArea notes_return;
    private javax.swing.JLabel notestitle_return;
    private javax.swing.JPanel parentpanel;
    private javax.swing.JTextField productname;
    private javax.swing.JComboBox<String> productname_newrental;
    private javax.swing.JLabel productname_return;
    private com.raven.datechooser.DateChooser rentalDate_newrental;
    private javax.swing.JButton rentallist;
    private javax.swing.JPanel rentallist_panel;
    private javax.swing.JTable rentallist_table;
    private javax.swing.JLabel rentallist_title;
    private javax.swing.JLabel rentedby;
    private javax.swing.JLabel rentedby_return;
    private javax.swing.JPanel return_panel;
    private javax.swing.JLabel return_title;
    private com.raven.datechooser.DateChooser returndate;
    private javax.swing.JLabel room_newdevice;
    private javax.swing.JToggleButton save_newdevice;
    private javax.swing.JToggleButton save_newrental;
    private javax.swing.JToggleButton save_return;
    private javax.swing.JComboBox<String> searchfilter_archive;
    private javax.swing.JComboBox<String> searchfilter_inventory;
    private javax.swing.JComboBox<String> searchfilter_rentallist;
    private javax.swing.JPanel sidepanel;
    private javax.swing.JPanel startpage;
    private javax.swing.JPanel toppenal;
    private javax.swing.JTextField userEmail;
    private javax.swing.JTextField userFirstName;
    private javax.swing.JComboBox<String> userID_newrental;
    private javax.swing.JTextField userLastName;
    private javax.swing.JTextField userPhone;
    private javax.swing.JComboBox<String> withdrawnby;
    private javax.swing.JComboBox<String> year_newrental;
    private javax.swing.JRadioButton yes;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        new GUI().setVisible(true);
    }
}
