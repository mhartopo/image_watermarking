package gui;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import utils.ImageUtils;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.Color;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import core.*;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;

public class Front {

	private JFrame frame;
	private BufferedImage hostImage;
	private BufferedImage watermark;
	BufferedImage reswatermarked;
	private Imagewm iwm;
	private JTextField textField;
	private JTextField textField_1;
	BufferedImage imgwatermarked_ex;
	private double PSNR = 0;
	private String pathim1;
	private String pathim2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Front window = new Front();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Front() {
		iwm = new Imagewm();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.WHITE);
		tabbedPane.addTab("Add Watermark", null, desktopPane, null);
		
		JButton btnView = new JButton("view");
		JButton btnView2 = new JButton("view");
		
		JLabel lblhost = DefaultComponentFactory.getInstance().createLabel("-");
		lblhost.setBounds(148, 51, 223, 14);
		desktopPane.add(lblhost);
		
		JLabel lblwm = DefaultComponentFactory.getInstance().createLabel("-");
		lblwm.setBounds(148, 100, 223, 14);
		desktopPane.add(lblwm);
		
		JLabel lblsize = DefaultComponentFactory.getInstance().createLabel("-");
		lblsize.setBounds(290, 199, 117, 20);
		desktopPane.add(lblsize);
		
		
		JButton btnSelect = new JButton("Select");
		btnSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String filePath = chooseFile();
				if(filePath != null) {
					hostImage = ImageUtils.loadImage(filePath);
					btnView.setEnabled(true);
					lblhost.setText(filePath);
					int size = hostImage.getWidth() * hostImage.getHeight() / (8 * 1024);
					lblsize.setText(Integer.toString(size) + " KB");
				}
			}
		});
		btnSelect.setBounds(148, 21, 73, 23);
		desktopPane.add(btnSelect);
		
		JButton button = new JButton("Select");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String filePath = chooseFile();
				if(filePath != null) {
					watermark = ImageUtils.loadImage(filePath);
					btnView2.setEnabled(true);
					lblwm.setText(filePath);
				}
			}
		});
		button.setBounds(148, 76, 73, 23);
		desktopPane.add(button);
		
		JLabel lblpsnr = DefaultComponentFactory.getInstance().createLabel("PSNR");
		lblpsnr.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblpsnr.setBounds(42, 202, 120, 23);
		desktopPane.add(lblpsnr);
		
		
		JButton btnAddWatermark = new JButton("Add watermark");
		btnAddWatermark.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(!textField.getText().isEmpty()) {
					try {
						BufferedImage reswatermarked = iwm.addWatermark(hostImage, watermark, textField.getText());
						PSNR = ImageUtils.getPSNR(hostImage, reswatermarked);
						lblpsnr.setText("PSNR = " + Double.toString(PSNR));
						view(reswatermarked, "Image with watermark");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnAddWatermark.setBounds(32, 168, 137, 23);
		desktopPane.add(btnAddWatermark);
		
		JButton btnSaveWatermark = new JButton("full watermark");
		btnSaveWatermark.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				view(iwm.getWatermark_img(), "Full Watermark");
			}
		});
		btnSaveWatermark.setBounds(234, 168, 137, 23);
		desktopPane.add(btnSaveWatermark);
		
		
		
		JLabel lblKey = DefaultComponentFactory.getInstance().createLabel("Key");
		lblKey.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblKey.setBounds(32, 130, 92, 21);
		desktopPane.add(lblKey);
		
		textField = new JTextField();
		textField.setBounds(82, 132, 289, 20);
		desktopPane.add(textField);
		textField.setColumns(10);
		
		btnView.setEnabled(false);
		btnView.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				view(hostImage, "Host Image");
			}
		});
		btnView.setBounds(245, 21, 89, 23);
		desktopPane.add(btnView);
		
		btnView2.setEnabled(false);
		btnView2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				view(watermark, "Watermark Image");
			}
		});
		btnView2.setBounds(245, 76, 89, 23);
		desktopPane.add(btnView2);
		
		JLabel lblHostImage = DefaultComponentFactory.getInstance().createTitle("Host Image");
		lblHostImage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHostImage.setBounds(21, 25, 88, 19);
		desktopPane.add(lblHostImage);
		
		JLabel lblWatermarkImage = DefaultComponentFactory.getInstance().createTitle("Watermark Image");
		lblWatermarkImage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWatermarkImage.setBounds(21, 76, 117, 18);
		desktopPane.add(lblWatermarkImage);
		
		JLabel lblMaxSize = new JLabel("Max size");
		lblMaxSize.setBounds(234, 202, 56, 14);
		desktopPane.add(lblMaxSize);
		
		
		JDesktopPane desktopPane_1 = new JDesktopPane();
		desktopPane_1.setBackground(Color.WHITE);
		tabbedPane.addTab("Extract Watermark", null, desktopPane_1, null);
		
		JLabel lblWatermarkedImage = DefaultComponentFactory.getInstance().createLabel("Watermarked Image");
		lblWatermarkedImage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWatermarkedImage.setBounds(29, 35, 142, 27);
		desktopPane_1.add(lblWatermarkedImage);
		
		JLabel lblwmex = DefaultComponentFactory.getInstance().createLabel("-");
		lblwmex.setBounds(156, 80, 241, 14);
		desktopPane_1.add(lblwmex);
		
		
		
		JButton btnSelect_1 = new JButton("select");
		btnSelect_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String filePath = chooseFile();
				if(filePath != null) {
					imgwatermarked_ex = ImageUtils.loadImage(filePath);
					lblwmex.setText(filePath);
					
				}
			}
		});
		btnSelect_1.setBounds(198, 39, 89, 23);
		desktopPane_1.add(btnSelect_1);
		
		JButton btnView_1 = new JButton("view");
		btnView_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				view(imgwatermarked_ex, "Image with watermark");
			}
		});
		btnView_1.setBounds(308, 39, 89, 23);
		desktopPane_1.add(btnView_1);
		
		JLabel lblKey_1 = DefaultComponentFactory.getInstance().createLabel("Key");
		lblKey_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblKey_1.setBounds(29, 117, 92, 27);
		desktopPane_1.add(lblKey_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(73, 122, 324, 20);
		desktopPane_1.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnExtract = new JButton("Extract");
		btnExtract.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				BufferedImage res = iwm.extractWatermark(imgwatermarked_ex, textField_1.getText());
				view(res, "Extracted watermark");
			}
		});
		btnExtract.setBounds(308, 185, 89, 23);
		desktopPane_1.add(btnExtract);
		
		
		JDesktopPane desktopPane_2 = new JDesktopPane();
		desktopPane_2.setBackground(Color.WHITE);
		tabbedPane.addTab("Image Substract", null, desktopPane_2, null);
		
		JLabel lblim1 = new JLabel("-");
		lblim1.setBounds(53, 79, 331, 14);
		desktopPane_2.add(lblim1);
		
		JLabel lblim2 = new JLabel("-");
		lblim2.setBounds(53, 149, 331, 14);
		desktopPane_2.add(lblim2);
		
		JButton btnSelect_2 = new JButton("select");
		btnSelect_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				String filePath = chooseFile();
				if(filePath != null) {
					pathim1 = filePath;
					lblim1.setText(filePath);
				}
			}
		});
		btnSelect_2.setBounds(236, 45, 89, 23);
		desktopPane_2.add(btnSelect_2);
		
		JButton btnSelect_3 = new JButton("Select");
		btnSelect_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				String filePath = chooseFile();
				if(filePath != null) {
					pathim2 = filePath;
					lblim2.setText(filePath);
				}
			}
		});
		btnSelect_3.setBounds(236, 110, 89, 23);
		desktopPane_2.add(btnSelect_3);
		
		JButton btnSubstract = new JButton("Substract");
		btnSubstract.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				view(ImageUtils.substract(pathim1, pathim2), "Substract Image");
			}
		});
		btnSubstract.setBounds(232, 184, 105, 23);
		desktopPane_2.add(btnSubstract);
		
		JLabel lblImage = new JLabel("Image 1");
		lblImage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblImage.setBounds(53, 49, 77, 19);
		desktopPane_2.add(lblImage);
		
		JLabel lblImage_1 = new JLabel("Image 2");
		lblImage_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblImage_1.setBounds(53, 110, 77, 18);
		desktopPane_2.add(lblImage_1);
		
		
		
		
	}
	
	private String chooseFile() {
		JFileChooser fc = new JFileChooser();
		int retVal = fc.showOpenDialog(null);
		String path = "";
		FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
		if(retVal == JFileChooser.APPROVE_OPTION) {
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.setFileFilter(imageFilter);
			File workingDirectory = new File(System.getProperty("user.dir"));
			fc.setCurrentDirectory(workingDirectory);
			path = fc.getSelectedFile().getAbsolutePath();
		}
		
		return path;
	}
	
	public void view(BufferedImage buffimage, String name) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					SingleImage frame = new SingleImage(buffimage, name);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
