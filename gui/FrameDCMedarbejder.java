package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class FrameDCMedarbejder extends JFrame {

	private JPanel contentPane;
	private JButton btnLsseMedarbejder, btnTrailerMedarbejder, btnAdministrerLastbil, btnLastbiler, btnRamper, btnOrdrer;
	private FrameAdministrerLastbil frameAdministrerLastbil;
	private FrameLastbiler frameLastbiler;
	private FrameOrdrer frameOrdrer;
	private FrameRamper frameRamper;
	private FrameTrailerMedarbejder frameTrailerMedarbejder;
	private FrameLaesseMedarbejder frameLaesseMedarbejder;
	private Controller controller = new Controller();
	private Font stdFont = new Font("Tahoma", Font.PLAIN, 11);

	public FrameDCMedarbejder() {
		setResizable(false);
		setTitle("DC medarbejder");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(327, 100, 316, 138);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		frameLastbiler = new FrameLastbiler();
		frameOrdrer = new FrameOrdrer();
		frameLaesseMedarbejder = new FrameLaesseMedarbejder(frameLastbiler, frameOrdrer);
		frameRamper = new FrameRamper(frameLaesseMedarbejder);
		frameTrailerMedarbejder = new FrameTrailerMedarbejder(frameRamper);
		frameTrailerMedarbejder.setFrameLaesseMedarbejder(frameLaesseMedarbejder);
		frameAdministrerLastbil = new FrameAdministrerLastbil(frameLastbiler);
		
		
		btnLsseMedarbejder = new JButton("Læsse medarbejder");
		btnLsseMedarbejder.addActionListener(controller);
		btnLsseMedarbejder.setFont(stdFont);
		btnLsseMedarbejder.setFocusable(false);
		btnLsseMedarbejder.setBounds(10, 11, 140, 23);
		contentPane.add(btnLsseMedarbejder);

		btnTrailerMedarbejder = new JButton("Trailer medarbejder");
		btnTrailerMedarbejder.addActionListener(controller);
		btnTrailerMedarbejder.setFont(stdFont);
		btnTrailerMedarbejder.setFocusable(false);
		btnTrailerMedarbejder.setBounds(10, 45, 140, 23);
		contentPane.add(btnTrailerMedarbejder);

		btnAdministrerLastbil = new JButton("Administrer lastbil");
		btnAdministrerLastbil.addActionListener(controller);
		btnAdministrerLastbil.setFont(stdFont);
		btnAdministrerLastbil.setFocusable(false);
		btnAdministrerLastbil.setBounds(10, 79, 140, 23);
		contentPane.add(btnAdministrerLastbil);

		btnLastbiler = new JButton("Lastbiler");
		btnLastbiler.addActionListener(controller);
		btnLastbiler.setFont(stdFont);
		btnLastbiler.setFocusable(false);
		btnLastbiler.setBounds(160, 11, 140, 23);
		contentPane.add(btnLastbiler);

		btnRamper = new JButton("Ramper");
		btnRamper.addActionListener(controller);
		btnRamper.setFont(stdFont);
		btnRamper.setFocusable(false);
		btnRamper.setBounds(160, 45, 140, 23);
		contentPane.add(btnRamper);

		btnOrdrer = new JButton("Ordrer");
		btnOrdrer.addActionListener(controller);
		btnOrdrer.setFocusable(false);
		btnOrdrer.setFont(stdFont);
		btnOrdrer.setBounds(160, 79, 140, 23);
		contentPane.add(btnOrdrer);
	}

	private class Controller implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			JButton jButton = (JButton) ae.getSource();
			if (jButton.equals(btnAdministrerLastbil)) {
				frameAdministrerLastbil.setVisible(true);
			} else if (jButton.equals(btnLastbiler)){
				frameLastbiler.setVisible(true);
			} else if (jButton.equals(btnOrdrer)){
				frameOrdrer.setVisible(true);
			} else if (jButton.equals(btnRamper)){
				frameRamper.setVisible(true);
			} else if (jButton.equals(btnLsseMedarbejder)){
				frameLaesseMedarbejder.setVisible(true);
			} else if (jButton.equals(btnTrailerMedarbejder)){
				frameTrailerMedarbejder.setVisible(true);
			}
		}
	}
}