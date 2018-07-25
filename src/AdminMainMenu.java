/*C1213578H, CHIHWAYI IGNATIOUS
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
C1213578H, CHIHWAYI IGNATIOUS
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.util.Date;
import java.beans.PropertyVetoException;
import javax.swing.plaf.metal.*;
import java.awt.image.*;
/**
 *
 * @author kuda
 */
public class AdminMainMenu extends JFrame implements ActionListener, ItemListener {

 
JDesktopPane desktop = new JDesktopPane();
String sMSGBOX_TITLE	= "M.R.D.C Payroll System V. 1.0";

JMenuBar menubar = new JMenuBar();

JMenu menuFile = new JMenu("File");
JMenu menuEmployee = new JMenu("Employee");
JMenu menuTools = new JMenu("Tools");
JMenu menuReports = new JMenu("Reports");
JMenu menuOptions = new JMenu("Options");
JMenu menuAbout = new JMenu("About M.R.D.C");

JMenuItem itemExit = new JMenuItem();

JMenuItem itemAdd = new JMenuItem();
JMenuItem itemEdit = new JMenuItem();
JMenuItem itemDelete = new JMenuItem();

JMenuItem itemSettings = new JMenuItem();
JMenuItem itemCalculator = new JMenuItem();
JMenuItem itemNotePad = new JMenuItem();

JMenuItem itemEmprpt = new JMenuItem();

JMenuItem itemHistory = new JMenuItem();
JMenuItem itemMissionStatement = new JMenuItem();

private	JMenuItem change, style, theme;

JPanel panel_Bottom = new JPanel();
JPanel panel_Top = new JPanel();


JLabel lblUsername = new JLabel("User Name:");
JLabel lblLogDetails = new JLabel("Time Login :");
JLabel lblTimeNow = new JLabel();

JTextField username = new JTextField();
JTextField logtime = new JTextField();


EmployeeAdd FormAddwindow;
EmployeeEdit FormEditwindow;
EmployeeDelete FormDeletewindow;

        private String strings[] = {"1. Metal", "2. Motif", "3. Windows"};
	private UIManager.LookAndFeelInfo looks[] = UIManager.getInstalledLookAndFeels ();
	private ButtonGroup group = new ButtonGroup ();
	private JRadioButtonMenuItem radio[] = new JRadioButtonMenuItem[strings.length];

EmployeePayslip FormEmprptwindow;
MRDC_History FormTextMRDC;
EmployeesSettings FormSettingswindow;
MRDC_MissionStatement FormMRDC_MissionStatement;

Connection conn;

static Date td  = new Date();

static Statement stmtLogin;

PayrollSettings settings 	= new PayrollSettings();

	static String sUser = "";
	static String sLogin = DateFormat.getDateTimeInstance().format(td);
	
        private ActionListener listener;
        private javax.swing.Timer t1;
	
    public AdminMainMenu(String user, Date date) throws IOException{
     		super("PayRoll and Database System [Version 1.0]");
                sUser = user;
                td = date; 
                
                change = new JMenuItem ("Change Background Color");
		change.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK));
		change.setMnemonic ((int)'B');
		change.addActionListener (this);
                
                style = new JMenu ("Change Layout Style");
		style.setMnemonic ((int)'L');
		for( int i = 0; i < radio.length ; i++ ) {			
			radio[i] = new JRadioButtonMenuItem (strings[i]);	
			radio[i].addItemListener (this);			
			group.add (radio[i]);					
			style.add (radio[i]);					
		}
		
		MetalTheme[] themes = { new DefaultMetalTheme(), new ThemeGreen(), new ThemeAqua(), 
					new ThemeSand(), new ThemeSolid(), new ThemeMilky(), new ThemeGray() };
		theme = new ThemeMetalMenu ("Apply Theme", themes);		
		theme.setMnemonic ((int)'M');
              
             
    JTextField username = new JTextField();
    username.setEditable(false);
	JTextField logtime = new JTextField();
	logtime.setEditable(false);
	username.setText(sUser);
	logtime.setText(sLogin);
      
     panel_Bottom.setLayout(new FlowLayout());
     panel_Bottom.setPreferredSize(new Dimension(10,25));
    
     panel_Bottom.add(lblUsername);
     panel_Bottom.add(username);
     panel_Bottom.add(lblLogDetails);
     panel_Bottom.add(logtime);
     panel_Bottom.setBackground(Color.BLUE);
     
     panel_Top.setLayout(new BorderLayout());
     panel_Top.setPreferredSize(new Dimension(10,65));
     panel_Top.add(createJToolBar(),BorderLayout.PAGE_START);
     panel_Top.setBackground(Color.BLUE); 
     
     
     BufferedImage image = ImageIO.read(new File("images/mrdc.png"));
     desktop.setBorder(new DesktopCentredBackGroundBorder(image));
     desktop.setAutoscrolls(true);
     desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
     getContentPane().add(panel_Top,BorderLayout.PAGE_START);
     getContentPane().add(desktop,BorderLayout.CENTER);
     getContentPane().add(panel_Bottom,BorderLayout.PAGE_END);
     
     
     addWindowListener(new WindowAdapter(){
         
     public void windowClosing(WindowEvent e)
     {
         UnloadWindow();
     }
     });
     
     setJMenuBar(CreateJMenuBar());
     setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
     setIconImage(new ImageIcon("images/first2.gif").getImage());
     setSize(1000,1000);
     setLocation(2,2);
     show();
        
    }
    
    protected JMenuBar CreateJMenuBar(){
       
        menuFile.add(settings.setJMenuItem(itemExit,"Quit","images/exitt.png"));
        
        itemExit.addActionListener(this);
        
        menuEmployee.add(settings.setJMenuItem(itemAdd,"Add Employee","images/addemp.png"));
        menuEmployee.add(settings.setJMenuItem(itemEdit,"Edit Employee","images/edit-icon.png"));
        menuEmployee.addSeparator();
        menuEmployee.add(settings.setJMenuItem(itemDelete,"Delete Employee","images/deletee.png"));
        
        itemAdd.addActionListener(this);
        itemEdit.addActionListener(this);
        itemDelete.addActionListener(this);

      //  menuTools.add(settings.setJMenuItem(itemSettings,"Settings","images/rep.png"));
        menuTools.add(settings.setJMenuItem(itemCalculator,"Calculator","images/calculator.png"));
        menuTools.addSeparator();
        menuTools.add(settings.setJMenuItem(itemNotePad,"NotePad","images/notepadee.png"));
        
        itemSettings.addActionListener(this);
        itemCalculator.addActionListener(this);
        itemNotePad.addActionListener(this);
        
        menuReports.add(settings.setJMenuItem(itemEmprpt,"Employee Report","images/report.png"));
         menuTools.addSeparator();
          menuTools.addSeparator();
        itemEmprpt.addActionListener(this);

        menuAbout.add(settings.setJMenuItem( itemHistory,"M.R.D.C History",""));
        menuAbout.add(settings.setJMenuItem(itemMissionStatement,"M.R.D.C Vision and Mission",""));

        menuAbout.addSeparator();
        
        itemHistory.addActionListener(this);
        itemMissionStatement.addActionListener(this);
    
  
        menuOptions.add (change);
	menuOptions.addSeparator ();
	menuOptions.add (style);
	menuOptions.addSeparator ();
	menuOptions.add (theme);
        
        menubar.add(settings.setJMenu(menuFile));
        menubar.add(settings.setJMenu(menuEmployee));
        menubar.add(settings.setJMenu(menuTools));
        menubar.add(settings.setJMenu(menuReports));
        menubar.add(settings.setJMenu(menuOptions));
        menubar.add(settings.setJMenu(menuAbout));
        menubar.setBackground(Color.gray);
       return menubar;

    }
    
    protected JToolBar createJToolBar(){
        JToolBar toolbar = new JToolBar("Toolbar");
        
        toolbar.add(settings.CreateJToolbarButton("Exit", "images/exitt1.png", "File_Exit",
                JToolBarActionListener));
			toolbar.addSeparator();
			toolbar.addSeparator();

        toolbar.add(settings.CreateJToolbarButton("Add - Employee", "images/add-user-icon.png", "Emp_Add",
                JToolBarActionListener));
        
        toolbar.add(settings.CreateJToolbarButton("Edit - Employee", "images/edgraphic.png", "Emp_Edit",
                JToolBarActionListener));
        		toolbar.addSeparator();

        toolbar.add(settings.CreateJToolbarButton("Delete - Employee", "images/deleteee.png","Emp_Delete",
                JToolBarActionListener));
		toolbar.addSeparator();
		toolbar.addSeparator();
	
      //  toolbar.add(settings.CreateJToolbarButton("Employee Position Settings", "images/imagesreport.jpeg","Settings",
           //     JToolBarActionListener));
        toolbar.add(settings.CreateJToolbarButton("Calculator", "images/calculator-icon.png","Tools_Calculator",
                JToolBarActionListener));
        toolbar.add(settings.CreateJToolbarButton("NotePad", "images/notepade.png","Tools_NotePad",
                JToolBarActionListener));
				toolbar.addSeparator();
				toolbar.addSeparator();

        toolbar.add(settings.CreateJToolbarButton("Employee - Report", "images/imagesrep.jpeg","Reports_Employee",
                JToolBarActionListener));
        toolbar.addSeparator();
	toolbar.addSeparator();
        
        toolbar.add(settings.CreateJToolbarButton("About - M.R.D.C", "images/first3.png","M.R.D.C_About",
                JToolBarActionListener));
        toolbar.setBackground(Color.magenta);

        return toolbar;
        
    }
    
    ActionListener JToolBarActionListener = new ActionListener(){
        public void actionPerformed(ActionEvent e)
        {
            String source = e.getActionCommand();
            
            if (source == "File_Exit")
            {
                loadJInternalFrame(2);
            }
            else if (source == "Emp_Add")
            {
                loadJInternalFrame(3);                
            }
            else if (source == "Emp_Edit")
            {
                loadJInternalFrame(4);                
            }
            else if (source == "Emp_Delete")
            {
                loadJInternalFrame(5);                
            }
            else if (source == "Settings")
            {
                loadJInternalFrame(6);                
            }
            else if (source == "Tools_Calculator")
            {
                loadJInternalFrame(7);                
            }
            else if (source == "Tools_NotePad")
            {
                loadJInternalFrame(8);
            }
            else if (source == "Reports_Employee")
            {
                loadJInternalFrame(9);
            }
           
            else if (source == "M.R.D.C_About")
            {
                loadJInternalFrame(10);
            }
           
        }
    
    };
    
    public void actionPerformed(ActionEvent event){
        Object object = event.getSource();
        
        if (object ==  itemExit)
        {
            loadJInternalFrame(2);
        }
        else if (object == itemAdd)
        {
            loadJInternalFrame(3);
        }
        else if ( object == itemEdit)
        {
            loadJInternalFrame(4);
        }
        else if (object == itemDelete)
        {
            loadJInternalFrame(5);
        }
        else if (object == itemSettings)
        {
            loadJInternalFrame(6);
        }
        else if (object == itemCalculator)
        {
            loadJInternalFrame(7);
            
        }
        else if (object == itemNotePad)
        {
            loadJInternalFrame(8);
        }
        else if (object == itemEmprpt)
        {
            loadJInternalFrame(9);
        }
         else if (object == itemHistory)
        {
            loadJInternalFrame(10);
        }
         else if (object == itemMissionStatement)
        {
            loadJInternalFrame(11);
        }
        
        else if (object == change) {

			Color cl = new Color (153, 153, 204);
			cl = JColorChooser.showDialog (this, "Choose Background Color", cl);
			if (cl == null) { }
			else {
				desktop.setBackground (cl);
				desktop.repaint ();
			}
       
    }
    }
    
    public void itemStateChanged (ItemEvent e) {

		for( int i = 0; i < radio.length; i++ )
			if(radio[i].isSelected()) {
				changeLookAndFeel (i);
			}

	}
    
    public void changeLookAndFeel (int val) {

		try {
			UIManager.setLookAndFeel (looks[val].getClassName());
			SwingUtilities.updateComponentTreeUI (this);
		}
		catch (Exception e) { }

	}
    
    private void loadJInternalFrame(int intWhich)
    {
        switch(intWhich)
        {
            
            case 2:
                System.exit(0);
                break;
            
            case 3:
                try {
                	FormAddwindow = new EmployeeAdd(this);
               loadForm("Add Employee", FormAddwindow);
                }
                catch(Exception e)
                {
                	System.out.println("\nError");
                }
                break;
            
            case 4:
                try {
                	FormEditwindow = new EmployeeEdit(this);
               loadForm("Edit Employee", FormEditwindow);
                }
                catch(Exception e)
                {
                	System.out.println("\nError");
                }
                break;
            
            case 5:
                try {
                	FormDeletewindow = new EmployeeDelete(this);
               loadForm("Delete Employee", FormDeletewindow);
                }
                catch(Exception e)
                {
                	System.out.println("\nError");
                }
                break;
            
            case 6:
                try {
                	FormSettingswindow = new EmployeesSettings(this);
               loadForm("Settings of Employee", FormSettingswindow);
                }
                catch(Exception e)
                {
                	System.out.println("\nError");
                }
                break;
            
            case 7:
                runComponents("Calc.exe");
                break;
            
            case 8:
                runComponents("Notepad.exe");
                break;
            
            case 9:
            	try{
            		FormEmprptwindow = new EmployeePayslip(this);
               		loadForm("Employee PaySlip", FormEmprptwindow);
            	
                }
                catch(Exception e)
                {
                	System.out.println("\nError" + e );
                }
                break;
                
               
            case 10:
               try{
            	        FormTextMRDC = new MRDC_History(this);
               		loadForm("MRDC History", FormTextMRDC);	
            	
                }
                catch(Exception e)
                {
                	System.out.println("\nError" + e );
                }
                break;
            
            case 11:
                try{
            	        FormMRDC_MissionStatement = new MRDC_MissionStatement(this);
               		loadForm("MRDC History", FormMRDC_MissionStatement);	
            	
                }
                catch(Exception e)
                {
                	System.out.println("\nError" + e );
                }
                break;
                
                
        }
        
    }
    	protected void runComponents(String sComponents){
		Runtime rt = Runtime.getRuntime();
		try{rt.exec(sComponents);}
		catch(IOException evt){JOptionPane.showMessageDialog(null,evt.getMessage(),"Error Found",JOptionPane.ERROR_MESSAGE);}
	}

protected void loadForm(String Title, JInternalFrame clsForm){

boolean xForm = isLoaded(Title);
if (xForm == false)
{
desktop.add(clsForm);
clsForm.setVisible(true);
clsForm.show();
}
else
{
try {
clsForm.setIcon(false);
clsForm.setSelected(true);

}
catch(PropertyVetoException e)
{}
 }
} 
protected boolean isLoaded(String FormTitle)
{
 	JInternalFrame Form[] = desktop.getAllFrames();
	for ( int i = 0; i < Form.length; i++)
	{
	if (Form[i].getTitle().equalsIgnoreCase(FormTitle))
		{
			Form[i].show();
			try
			{
			Form[i].setIcon(false);
			Form[i].setSelected(true);
			
			}
			catch(PropertyVetoException e)
			{
				
				}
			return true;
		}	
	}
	return false;
} 

protected void UnloadWindow(){
try
   {
	int reply = JOptionPane.showConfirmDialog(this,"Are you sure to exit?",sMSGBOX_TITLE,JOptionPane.YES_NO_OPTION,
			JOptionPane.WARNING_MESSAGE);
		if (reply == JOptionPane.YES_OPTION)
			{
			
			setVisible(false);
			System.exit(0);
			}
   }
	catch(Exception e)
	{}

}
	public static void setlogin(String sUsername, Date sDate){
		sUser  = sUsername;
		td	   = sDate;
		
		
	}   
	
}
