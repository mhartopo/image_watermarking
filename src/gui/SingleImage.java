package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import utils.ImageUtils;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;

public class SingleImage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private BufferedImage imagecontent;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					SingleImage frame = new SingleImage(ImageUtils.loadImage("C:/Users/muhtarh/Desktop/wm.png"), "halo");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SingleImage(BufferedImage buffimage, String name) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		imagecontent = buffimage;
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.WHITE);
		contentPane.add(desktopPane, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 474, 21);
		desktopPane.add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSave = new JMenuItem("save");
		mntmSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String fileName = chooseDir();
				try {
					ImageUtils.writeImage(imagecontent, fileName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		mnFile.add(mntmSave);
		
		JLabel lblImage = DefaultComponentFactory.getInstance().createTitle(name);
		lblImage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblImage.setBounds(10, 21, 454, 32);
		desktopPane.add(lblImage);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 57, 474, 294);
		Image image;
		image = buffimage;
		double ratio = ((double) buffimage.getWidth())/buffimage.getHeight();
		
		int w,h;
		if(ratio < 1) {
			w = (int) (294 * ratio);
			h = 294;
		}else{
			w = 474;
			h = (int) (474/ratio);
		}
		image = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		JLabel lblimage = new JLabel(new ImageIcon(image));
		panel.add(lblimage);
		desktopPane.add(panel);
	}
	
	private String chooseDir() {
		JFileChooser fc = new JFileChooser();
		int retVal = fc.showOpenDialog(null);
		String path = "";
		if(retVal == JFileChooser.APPROVE_OPTION) {
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			File workingDirectory = new File(System.getProperty("user.dir"));
			fc.setCurrentDirectory(workingDirectory);
			path = fc.getSelectedFile().getAbsolutePath();
		}
		
		return path;
	}
}
