import java.awt.Dimension;
	import java.awt.Graphics;
	import java.awt.event.ActionEvent; 
	import java.awt.event.ActionListener; 
	import java.awt.print.PageFormat; 
	import java.awt.print.Printable;
	import java.awt.print.PrinterException; 
	import java.awt.print.PrinterJob; 
	import java.io.File;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.io.PrintWriter;
import java.util.Stack;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JFileChooser;
	import javax.swing.JFrame;
	import javax.swing.JMenu;
	import javax.swing.JMenuBar;
	import javax.swing.JMenuItem;
	import javax.swing.JOptionPane;
	import javax.swing.JScrollPane;
	import javax.swing.JTextPane;
	import javax.swing.KeyStroke;
import javax.swing.text.Caret;
import javax.swing.text.Position; 
	import javax.swing.text.StyledDocument;

	public class NotePad3 extends JFrame implements ActionListener{
	String myPage = " ";  // Declare a string variable 
	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenu editMenu = new JMenu("Edit");
	JTextPane textPane = new JTextPane();
	JMenuItem newFile = new JMenuItem("New File"); 
	JMenuItem saveFile = new JMenuItem("Save File"); 
	JMenuItem printFile = new JMenuItem("Print File");
	JMenuItem undo = new JMenuItem("Undo"); 
	JMenuItem copy = new JMenuItem("Copy"); 
	JMenuItem paste = new JMenuItem("Paste");
	Stack commandStack = new Stack(); //Declare a stack that will contain the "myPage" string
	public NotePad3() {
		
	setTitle("A Simple Notepad Tool"); 
	fileMenu.add(newFile);
	fileMenu.addSeparator();
	fileMenu.add(saveFile);
	fileMenu.addSeparator();
	fileMenu.add(printFile);
	editMenu.add(undo);
	editMenu.add(copy);
	editMenu.add(paste); 
	newFile.addActionListener(this); 
	newFile.setActionCommand("new"); 
	saveFile.addActionListener(this); 
	saveFile.setActionCommand("save"); 
	printFile.addActionListener(this); 
	printFile.setActionCommand("print"); 
	copy.addActionListener(this); 
	copy.setActionCommand("copy"); 
	paste.addActionListener(this); 
	paste.setActionCommand("paste"); 
	undo.addActionListener(this); 
	undo.setActionCommand("undo"); 
	KeyStroke.getKeyStroke("action");  //Declare a event "action" that will handle the keyStroke
	//action.addActionListener(this);
	//action.setActionCommand("action");
	menuBar.add(fileMenu);
	menuBar.add(editMenu); 
	setJMenuBar(menuBar);
	add(new JScrollPane(textPane));
	setPreferredSize(new Dimension(600,600)); 
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	setVisible(true);
	pack(); 
	
	
	
	}
	
	public static void main(String[] args){ 
		
		NotePad3 app = new NotePad3();
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent event) 
	{ 
		// The action event will push in the stack each keyStroke in order to undo the actions
		if(event.getActionCommand().equals("action")) { 
			textPane.setCaret((Caret) getInputContext()); 
			myPage = textPane.getText( );  
			commandStack.push(myPage);   	//push each action to the stack 
		}
		
		if(event.getActionCommand().equals("new")) {
	textPane.setText(" ");
	myPage = textPane.getText( );  //assign the "textPane" to "myPage"
	commandStack.push(myPage);   //push each "textPane" action to the stack 
	}
		else if(event.getActionCommand().equals("save")) {
	File fileToWrite = null;
	JFileChooser fileChoser = new JFileChooser(); 
	int returnVal = fileChoser.showSaveDialog(null);
	if (returnVal == JFileChooser.APPROVE_OPTION) 
		fileToWrite = fileChoser.getSelectedFile();
	try {
	PrintWriter out = new PrintWriter(new FileWriter(fileToWrite)); 
	out.println(textPane.getText());
	JOptionPane.showMessageDialog(null, "File is saved successfully..."); 
	out.close();
	} 
	catch (IOException ex) {
	}
	}
		else if(event.getActionCommand().equals("print")) {
	try{
	PrinterJob pjob = PrinterJob.getPrinterJob(); 
	pjob.setJobName("Sample Command Pattern"); 
	pjob.setCopies(1);
	pjob.setPrintable(new Printable() {
	public int print(Graphics pg, PageFormat pf, int pageNum) { 
		if (pageNum>0)
	return Printable.NO_SUCH_PAGE; pg.drawString(textPane.getText(), 500, 500);
	paint(pg);
	return Printable.PAGE_EXISTS;
	 } 
	});
	if (pjob.printDialog() == false) 
		return;
		pjob.print();
	} 
	catch (PrinterException pe) {
	JOptionPane.showMessageDialog(null, "Printer error" + pe, "Printing error", JOptionPane.ERROR_MESSAGE);
	}
	}
		else if(event.getActionCommand().equals("copy")) {
			textPane.copy();  
			myPage = textPane.getText( );   //assign the "textPane" to "myPage"
			commandStack.push(myPage);   //push each "textPane" action to the stack !
	}
		else if(event.getActionCommand().equals("paste")) {
			StyledDocument doc = textPane.getStyledDocument(); 
			Position position = doc.getEndPosition(); 
			textPane.paste();
			myPage = textPane.getText( );   //assign the "textPane" to "myPage"
			commandStack.push(myPage);   //push each "textPane" action to the stack 
	}
		else if(event.getActionCommand().equals("undo")) {
	//TODO: implement undo operation
			//this function is undoing the copy, paste and new file !!
			commandStack.pop();  //the stack will throw last "textPane" action 
			myPage = (String) commandStack.peek();   //convert the stack to string
			textPane.setText(myPage);  //change the textPane to myPage
			} 
		}
	}

