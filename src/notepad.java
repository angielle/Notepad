import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class notepad {
	
	private JFrame frmTextEditor;
	
	int characters = 0;
	int words = 0;
	int sentences = 0;
	boolean textChanges = false;
	String text = "";
	String newText = "";
	String fileName = "";
	JFileChooser fc = new JFileChooser();
	Reader rd;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					notepad window = new notepad();
					window.frmTextEditor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public notepad() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTextEditor = new JFrame();

		frmTextEditor.setTitle("Untitled - Text Editor");
		frmTextEditor.setBounds(100, 100, 548, 389);
		frmTextEditor.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		frmTextEditor.getContentPane().add(scrollPane, BorderLayout.CENTER);

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		// Menus
		JMenuBar menuBar = new JMenuBar();
		scrollPane.setColumnHeaderView(menuBar);
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		JMenuItem mntmLoad = new JMenuItem("Load");
		mnFile.add(mntmLoad);
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mnFile.add(mntmSaveAs);
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		JMenuItem mntmCount = new JMenuItem("Count");
		mnView.add(mntmCount);
		
		// File, Disable Save/Save As?
		mnFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				newText = textArea.getText();
				if (fileName == "" && textArea.getText().trim().length() == 0){
					mntmSave.setEnabled(false);
					textChanges = false;
				}
				else if (fileName == "" && textArea.getText().trim().length() != 0){
					mntmSave.setEnabled(true);
					textChanges = true;
				}
				else if (fileName != "" && !text.equals(newText)){
					mntmSave.setEnabled(true);
					textChanges = true;
				}
				else if (fileName != "" && text.equals(newText)){
					mntmSave.setEnabled(false);
					textChanges = false;
				}
				
			}
		});
		
		// New
		mntmNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (textChanges){
					int confirmSaveNew = JOptionPane.showConfirmDialog(null, "Do you want to save changes?", "Confirm New",JOptionPane.YES_NO_CANCEL_OPTION);
					if (confirmSaveNew == JOptionPane.YES_OPTION){
						int confirmSave  = fc.showSaveDialog(frmTextEditor);
						if (confirmSave  == JFileChooser.APPROVE_OPTION){
							text = textArea.getText();
							File fileToSave = fc.getSelectedFile();
							System.out.println("Save as: " + fileToSave.getAbsolutePath());
							fileName = fileToSave.getName();
							textArea.setText("");
							frmTextEditor.setTitle("Untitled - Text Editor");
						}
					}
					else if (confirmSaveNew == JOptionPane.NO_OPTION){
						textArea.setText("");
						frmTextEditor.setTitle("Untitled - Text Editor");
					}
					
					else if (confirmSaveNew == JOptionPane.CANCEL_OPTION){
						JOptionPane.getRootFrame().dispose();
					}
				}
				else {
					textArea.setText("");
					frmTextEditor.setTitle("Untitled - Text Editor");
				}
			}
		});
		
		// Load
		mntmLoad.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				textChanges = true;
				try {
					int confirmLoad = fc.showOpenDialog(frmTextEditor);
					if (confirmLoad == JFileChooser.APPROVE_OPTION){
						FileReader r = new FileReader(fc.getSelectedFile());
						textArea.read(r, null);
						r.close();
						frmTextEditor.setTitle(fc.getSelectedFile().getName() + " - Text Editor");
						fileName = fc.getSelectedFile().getName();
						text = textArea.getText();
					}
				}
				catch(IOException e1) {
					JOptionPane.showMessageDialog(frmTextEditor, "Error! Text editor could not open the file");
				}
			}
		});
		
		
		// Save
		mntmSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int confirmSave = fc.showSaveDialog(frmTextEditor);
				if (confirmSave == JFileChooser.APPROVE_OPTION){
					text = textArea.getText();
					File fileToSave = fc.getSelectedFile();
					System.out.println("Save as: " + fileToSave.getAbsolutePath());
					fileName = fileToSave.getName();
				}
				frmTextEditor.setTitle(fileName + " - Text Editor");
				text = textArea.getText();
			}
		});
		
		// Save As
		mntmSaveAs.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int confirmSave = fc.showSaveDialog(frmTextEditor);
				if (confirmSave == JFileChooser.APPROVE_OPTION){
					text = textArea.getText();
					File fileToSave = fc.getSelectedFile();
					System.out.println("Save as: " + fileToSave.getAbsolutePath());
					fileName = fileToSave.getName();
				}
				frmTextEditor.setTitle(fileName + " - Text Editor");
				text = textArea.getText();
			}
		});
		
		// Exit
		mntmExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if ((fileName == "" && textArea.getText().trim().length() != 0) || (fileName != "" && textChanges)){
					int confirmSaveExit = JOptionPane.showConfirmDialog(null, "Do you want to save changes before exit?", "Confirm Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
					if (confirmSaveExit == JOptionPane.YES_OPTION){
						int confirmSave = fc.showSaveDialog(frmTextEditor);
						if (confirmSave == JFileChooser.APPROVE_OPTION){
							text = textArea.getText();
							File fileToSave = fc.getSelectedFile();
							System.out.println("Save as: " + fileToSave.getAbsolutePath());;
							System.exit(0);
						}
					}
					else if (confirmSaveExit == JOptionPane.NO_OPTION){
						System.exit(0);
					}
					else if (confirmSaveExit == JOptionPane.CANCEL_OPTION) {
						JOptionPane.getRootFrame().dispose();
					}
				}
				else {
					int comfirmExit = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
					if (comfirmExit == JOptionPane.YES_OPTION){
						System.exit(0);
					}
					else {
						JOptionPane.getRootFrame().dispose();
					}
					
				}
			}
		});
		
		// Count Characters, Words, Sentences
		mntmCount.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				text = textArea.getText();
				helper count = new helper();
				characters = count.charCount(text);
				words = count.wordCount(text);
				sentences = count.sentenceCount(text);
				JOptionPane.showMessageDialog(null, "Characters:  " + characters + "\nWords:  " + words + "\nSentences:  " + sentences);
				
			}
		});
		
		
		// Closing the Window
		frmTextEditor.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if ((fileName == "" && textArea.getText().trim().length() != 0) || (fileName != "" && textChanges)){
					int confirmSaveExit = JOptionPane.showConfirmDialog(null, "Do you want to save changes before exit?", "Confirm Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
					if (confirmSaveExit == JOptionPane.YES_OPTION){
						int confirmSave = fc.showSaveDialog(frmTextEditor);
						if (confirmSave == JFileChooser.APPROVE_OPTION){
							text = textArea.getText();
							File fileToSave = fc.getSelectedFile();
							System.out.println("Save as: " + fileToSave.getAbsolutePath());;
							System.exit(0);
						}
					}
					else if (confirmSaveExit == JOptionPane.NO_OPTION){
						System.exit(0);
					}
					else if (confirmSaveExit == JOptionPane.CANCEL_OPTION) {
						JOptionPane.getRootFrame().dispose();
					}
				}
				else {
					int comfirmExit = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
					if (comfirmExit == JOptionPane.YES_OPTION){
						System.exit(0);
					}
				}
			}
		});
	}
}
