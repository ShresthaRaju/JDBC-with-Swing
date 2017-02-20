package edu.lfa.view;

/**
 *
 * @author RAJU
 */
import edu.lfa.dao.PersonDao;
import edu.lfa.daoimpl.PersonDaoImpl;
import edu.lfa.dbutil.Database;
import edu.lfa.entity.Person;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class UI extends JFrame implements ActionListener {
    
    private JPanel contentPane;
    private JTextField txt_pid;
    private JTextField txt_fullname;
    private JTextField txt_address;
    private JTextField txt_contact;
    private JTextField txt_email;
    private JTable table;
    private JLabel lbl_pid, lbl_fullname, lbl_address, lbl_contact, lbl_email, lbl_message;
    private JButton btn_Save, btn_Edit, btn_Search, btn_Delete, btn_viewAll;
    
    private Database database;
    private String sql;
    private PreparedStatement statement;
    private ResultSet resultset;
    private DefaultTableModel model;
    private Vector row;
    private List<Person> personlist;
    
    private PersonDao persondao = new PersonDaoImpl();
    
    public UI() {
        
        setTitle("RECORD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 609, 526);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        lbl_pid = new JLabel("Person_Id");
        lbl_pid.setBounds(28, 37, 83, 14);
        contentPane.add(lbl_pid);
        
        txt_pid = new JTextField();
        txt_pid.setBounds(166, 31, 236, 20);
        contentPane.add(txt_pid);
        txt_pid.setColumns(10);
        
        lbl_fullname = new JLabel("Full Name");
        lbl_fullname.setBounds(28, 65, 83, 14);
        contentPane.add(lbl_fullname);
        
        txt_fullname = new JTextField();
        txt_fullname.setColumns(10);
        txt_fullname.setBounds(166, 59, 236, 20);
        contentPane.add(txt_fullname);
        
        lbl_address = new JLabel("Address");
        lbl_address.setBounds(28, 95, 83, 14);
        contentPane.add(lbl_address);
        
        txt_address = new JTextField();
        txt_address.setColumns(10);
        txt_address.setBounds(166, 89, 236, 20);
        contentPane.add(txt_address);
        
        lbl_contact = new JLabel("Contact Number");
        lbl_contact.setBounds(28, 124, 93, 14);
        contentPane.add(lbl_contact);
        
        txt_contact = new JTextField();
        txt_contact.setColumns(10);
        txt_contact.setBounds(166, 118, 236, 20);
        contentPane.add(txt_contact);
        
        lbl_email = new JLabel("Email");
        lbl_email.setBounds(28, 154, 83, 14);
        contentPane.add(lbl_email);
        
        txt_email = new JTextField();
        txt_email.setColumns(10);
        txt_email.setBounds(166, 148, 236, 20);
        contentPane.add(txt_email);
        
        btn_Save = new JButton("Save");
        btn_Save.setBounds(28, 209, 67, 23);
        contentPane.add(btn_Save);
        btn_Save.addActionListener(this);
        
        btn_Search = new JButton("Search");
        btn_Search.setBounds(122, 209, 83, 23);
        contentPane.add(btn_Search);
        btn_Search.addActionListener(this);
        
        btn_Edit = new JButton("Edit");
        btn_Edit.setBounds(232, 209, 67, 23);
        contentPane.add(btn_Edit);
        btn_Edit.addActionListener(this);
        btn_Edit.setEnabled(false);
        
        btn_Delete = new JButton("Delete");
        btn_Delete.setBounds(328, 209, 74, 23);
        contentPane.add(btn_Delete);
        btn_Delete.addActionListener(this);
        btn_Delete.setEnabled(false);
        
        btn_viewAll = new JButton("View All");
        btn_viewAll.setBounds(424, 209, 74, 23);
        contentPane.add(btn_viewAll);
        btn_viewAll.addActionListener(this);
        
        lbl_message = new JLabel();
        lbl_message.setBounds(28, 449, 535, 14);
        contentPane.add(lbl_message);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(28, 279, 517, 136);
        contentPane.add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
        
        model = new DefaultTableModel();
        this.table.setModel(model);
        
        model.addColumn("Person_id");
        model.addColumn("Full_name");
        model.addColumn("Address");
        model.addColumn("Contact Number");
        model.addColumn("Email");
        
        this.txt_fullname.requestFocus();
        
        try {
            database = new Database();
            database.connectToDatabase();
            sql = "select * from tbl_person";
            statement = database.initStatement(sql);
            
            resultset = database.executeQuery();
            
            while (resultset.next()) {
                
                this.txt_pid.setText(resultset.getInt("person_id") + 1 + "");
            }
            
            database.disconnectDatabase();
            
        } catch (ClassNotFoundException ex) {
            this.lbl_message.setText("Error in class :" + ex.getMessage());
            this.lbl_message.setForeground(Color.red);
        } catch (SQLException ex) {
            this.lbl_message.setText("Error in sql :" + ex.getMessage());
            this.lbl_message.setForeground(Color.red);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        
        if (button == btn_Save) {
            if (this.txt_pid.getText().equals("")) {
                this.lbl_message.setText("Id cannot be null!");
                this.lbl_message.setForeground(Color.red);
                this.txt_pid.requestFocus();
                
            } else {
                try {
                    Person person = new Person();
                    
                    person.setFullName(this.txt_fullname.getText());
                    person.setAddress(this.txt_address.getText());
                    person.setContactNumber(this.txt_contact.getText());
                    person.setEmail(this.txt_email.getText());
                    
                    this.lbl_message.setText(persondao.insert(person));
                    this.lbl_message.setForeground(Color.GREEN);
                    
                    this.txt_pid.setText(Integer.parseInt(this.txt_pid.getText()) + 1 + "");
                    this.txt_fullname.setText("");
                    this.txt_address.setText("");
                    this.txt_contact.setText("");
                    this.txt_email.setText("");
                    this.txt_fullname.requestFocus();
                    
                } catch (ClassNotFoundException ex) {
                    this.lbl_message.setText("Error in class :" + ex.getMessage());
                    this.lbl_message.setForeground(Color.red);
                } catch (SQLException ex) {
                    this.lbl_message.setText("Error in sql :" + ex.getMessage());
                    this.lbl_message.setForeground(Color.red);
                }
            }
        } else if (button == btn_Search) {
            if (this.txt_pid.getText().equals("")) {
                this.lbl_message.setText("Enter Id you want to search!");
                this.lbl_message.setForeground(Color.red);
                this.txt_pid.requestFocus();
                
            } else {
                try {
                    
                    Person person = persondao.search(Integer.parseInt(this.txt_pid.getText()));
                    if (person != null) {
                        this.txt_fullname.setText(person.getFullName());
                        this.txt_address.setText(person.getAddress());
                        this.txt_contact.setText(person.getContactNumber());
                        this.txt_email.setText(person.getEmail());
                        
                        this.txt_pid.requestFocus();
                        btn_Edit.setEnabled(true);
                        btn_Delete.setEnabled(true);
                        this.lbl_message.setText("");
                    } else {
                        this.lbl_message.setText("No any record found!");
                        this.lbl_message.setForeground(Color.red);
                        this.txt_pid.setText("");
                        this.txt_fullname.setText("");
                        this.txt_address.setText("");
                        this.txt_contact.setText("");
                        this.txt_email.setText("");
                        this.txt_pid.requestFocus();
                        btn_Edit.setEnabled(false);
                        btn_Delete.setEnabled(false);
                    }
                    
                } catch (ClassNotFoundException ex) {
                    this.lbl_message.setText("Error in class :" + ex.getMessage());
                    this.lbl_message.setForeground(Color.red);
                } catch (SQLException ex) {
                    this.lbl_message.setText("Error in sql :" + ex.getMessage());
                    this.lbl_message.setForeground(Color.red);
                }
            }
        } else if (button == btn_Edit) {
            try {
                Person person = new Person();
                
                person.setFullName(this.txt_fullname.getText());
                person.setAddress(this.txt_address.getText());
                person.setContactNumber(this.txt_contact.getText());
                person.setEmail(this.txt_email.getText());
                
                this.lbl_message.setText(persondao.update(person, Integer.parseInt(this.txt_pid.getText())));
                this.lbl_message.setForeground(Color.green);
                
                this.txt_fullname.setText("");
                this.txt_address.setText("");
                this.txt_contact.setText("");
                this.txt_email.setText("");
                this.txt_fullname.requestFocus();
                
            } catch (ClassNotFoundException ex) {
                this.lbl_message.setText("Error in class :" + ex.getMessage());
                this.lbl_message.setForeground(Color.red);
            } catch (SQLException ex) {
                this.lbl_message.setText("Error in sql :" + ex.getMessage());
                this.lbl_message.setForeground(Color.red);
            }
            
        } else if (button == btn_Delete) {
            try {
                
                this.lbl_message.setText(persondao.delete(Integer.parseInt(this.txt_pid.getText())));
                this.txt_pid.setText("");
                this.txt_fullname.setText("");
                this.txt_address.setText("");
                this.txt_contact.setText("");
                this.txt_email.setText("");
                this.txt_pid.requestFocus();
                
            } catch (ClassNotFoundException ex) {
                this.lbl_message.setText("Error in class :" + ex.getMessage());
                this.lbl_message.setForeground(Color.red);
            } catch (SQLException ex) {
                this.lbl_message.setText("Error in sql :" + ex.getMessage());
                this.lbl_message.setForeground(Color.red);
            }
            
        } else if (button == btn_viewAll) {
            
            try {
                
                personlist = persondao.viewAll();
                
                if (personlist != null) {
                    for (Person p : personlist) {
                        row = new Vector();
                        row.add(p.getId());
                        row.add(p.getFullName());
                        row.add(p.getAddress());
                        row.add(p.getContactNumber());
                        row.add(p.getEmail());
                        
                        model.addRow(row);
                        
                    }
                    this.lbl_message.setText("");
                } else {
                    this.lbl_message.setText("There is no any record in database!");
                    this.lbl_message.setForeground(Color.red);
                }
                
                this.txt_pid.requestFocus();
                
            } catch (ClassNotFoundException ex) {
                this.lbl_message.setText("Error in class :" + ex.getMessage());
                this.lbl_message.setForeground(Color.red);
            } catch (SQLException ex) {
                this.lbl_message.setText("Error in sql :" + ex.getMessage());
                this.lbl_message.setForeground(Color.red);
            }
            
            {
                
            }
            
        }
        
    }
}
