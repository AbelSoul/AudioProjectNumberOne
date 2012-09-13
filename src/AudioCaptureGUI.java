import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AudioCaptureGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private AudioCapture audCap = new AudioCapture();
	private JMenuItem openUserGuide;

	public AudioCaptureGUI() {

		// constructor
		layoutMenu();
		layoutTransporButtons();

		getContentPane().setLayout(new FlowLayout());
		setTitle("Record/Playback Demo");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(350, 170);
		setVisible(true);
	}// end constructor

	public void layoutMenu() {

		// Menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Menu bar headings and sub-menus
		JMenu fileMenu = new JMenu("File");
		// menuBar.add(fileMenu);
		JMenu helpMenu = new JMenu("Hauners");
		menuBar.add(helpMenu);
		JMenu recentMenu = new JMenu("Recently opened files");
		fileMenu.add(recentMenu);

		// Menu items
		JMenuItem openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		openMenuItem.addActionListener(this);
		openUserGuide = new JMenuItem("User Guide");
		helpMenu.add(openUserGuide);
		openUserGuide.addActionListener(this);
	}

	public void layoutTransporButtons() {

		final JPanel guiButtonPanel = new JPanel();
		final JButton captureBtn = new JButton("Record");
		final JButton stopBtn = new JButton("Stop");
		final JButton playBtn = new JButton("Playback");

		guiButtonPanel.setBackground(Color.blue);
		this.add(guiButtonPanel);

		captureBtn.setEnabled(true);
		stopBtn.setEnabled(false);
		playBtn.setEnabled(false);

		// Register anonymous listeners
		captureBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				captureBtn.setEnabled(false);
				stopBtn.setEnabled(true);
				playBtn.setEnabled(false);
				// Capture input data from the
				// microphone until the Stop button is
				// clicked.
				audCap.captureAudio();
			}// end actionPerformed
		}// end ActionListener
				);// end addActionListener()
		guiButtonPanel.add(captureBtn);

		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				captureBtn.setEnabled(true);
				stopBtn.setEnabled(false);
				playBtn.setEnabled(true);
			}// end actionPerformed
		}// end ActionListener
		);// end addActionListener()
		guiButtonPanel.add(stopBtn);

		playBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Play back all of the data that was
				// saved during capture.
				audCap.playAudio();
			}// end actionPerformed
		}// end ActionListener
		);// end addActionListener()
		guiButtonPanel.add(playBtn);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == openUserGuide) {
			System.out.println("nae help yit");
			JOptionPane.showMessageDialog(null, "nae help yit, sorry");
		}

	}
}
