package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import model.Lastbil;
import model.TrailerStatus;
import service.*;

public class FrameAdministrerLastbil extends JFrame {

	private JLabel lblNewLabel, lblChauffr, lblChauffrsMobilnummer,
			lblMaxLastvgt, lblTrailervgtkg, lblOversigtOverLastbiler;
	private JTextField textField_chauffrsnavn, textField_trailervgt,
			textField_lastbilsnummer, textField_chauffrsmobilnummer,
			textField_maxlastvgt;
	private JScrollPane scrollPane;
	private JList<Lastbil> jlist_oversigtoverlastbiler;
	private ServiceDAO serviceDao = ServiceDAO.getInstance();
	private Service service = Service.getInstance();
	private Lastbil lastbil;
	private Controller controller = new Controller();
	private JButton btnSlet, btnRediger, btnOpret, btnNulstil, btnLuk;
	private Font stdFont = new Font("Tahoma", Font.PLAIN, 11);
	private FrameLastbiler frameLastbiler;

	public FrameAdministrerLastbil(FrameLastbiler fl) {
		frameLastbiler = fl;
		getContentPane().setFont(stdFont);
		setTitle("Administrer lastbil");
		setResizable(false);
		setBounds(653, 100, 339, 475);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getContentPane().setLayout(null);

		lblNewLabel = new JLabel("Lastbils nummer:");
		lblNewLabel.setFont(stdFont);
		lblNewLabel.setBounds(24, 14, 81, 14);
		getContentPane().add(lblNewLabel);

		lblChauffr = new JLabel("Chauff\u00F8rs navn:");
		lblChauffr.setFont(stdFont);
		lblChauffr.setBounds(24, 39, 106, 14);
		getContentPane().add(lblChauffr);

		lblChauffrsMobilnummer = new JLabel("Chauff\u00F8rs mobilnummer:");
		lblChauffrsMobilnummer.setFont(stdFont);
		lblChauffrsMobilnummer.setBounds(24, 64, 131, 14);
		getContentPane().add(lblChauffrsMobilnummer);

		textField_chauffrsnavn = new JTextField();
		textField_chauffrsnavn.setBounds(151, 39, 160, 20);
		getContentPane().add(textField_chauffrsnavn);
		textField_chauffrsnavn.setColumns(10);

		textField_lastbilsnummer = new JTextField();
		textField_lastbilsnummer.setColumns(10);
		textField_lastbilsnummer.setBounds(151, 14, 63, 20);
		getContentPane().add(textField_lastbilsnummer);

		textField_chauffrsmobilnummer = new JTextField();
		textField_chauffrsmobilnummer.setColumns(10);
		textField_chauffrsmobilnummer.setBounds(151, 64, 160, 20);
		getContentPane().add(textField_chauffrsmobilnummer);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 204, 287, 223);
		getContentPane().add(scrollPane);

		jlist_oversigtoverlastbiler = new JList<Lastbil>();
		jlist_oversigtoverlastbiler
				.addListSelectionListener(new SelectionListener());
		jlist_oversigtoverlastbiler
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(jlist_oversigtoverlastbiler);

		lblMaxLastvgt = new JLabel("Max Lastvægt (kg):");
		lblMaxLastvgt.setFont(stdFont);
		lblMaxLastvgt.setBounds(24, 89, 131, 14);
		getContentPane().add(lblMaxLastvgt);

		textField_maxlastvgt = new JTextField();
		textField_maxlastvgt.setColumns(10);
		textField_maxlastvgt.setBounds(151, 89, 63, 20);
		getContentPane().add(textField_maxlastvgt);

		lblTrailervgtkg = new JLabel("Trailervægt (kg):");
		lblTrailervgtkg.setFont(stdFont);
		lblTrailervgtkg.setBounds(24, 114, 131, 14);
		getContentPane().add(lblTrailervgtkg);

		lblOversigtOverLastbiler = new JLabel("Oversigt over lastbiler:");
		lblOversigtOverLastbiler.setFont(stdFont);
		lblOversigtOverLastbiler.setBounds(24, 179, 131, 14);
		getContentPane().add(lblOversigtOverLastbiler);

		textField_trailervgt = new JTextField();
		textField_trailervgt.setColumns(10);
		textField_trailervgt.setBounds(151, 114, 63, 20);
		getContentPane().add(textField_trailervgt);

		btnSlet = new JButton("Slet");
		btnSlet.addActionListener(controller);
		btnSlet.setFont(stdFont);
		btnSlet.setFocusable(false);
		btnSlet.setBounds(222, 145, 89, 23);
		getContentPane().add(btnSlet);

		btnRediger = new JButton("Rediger");
		btnRediger.addActionListener(controller);
		btnRediger.setFont(stdFont);
		btnRediger.setFocusable(false);
		btnRediger.setBounds(123, 145, 89, 23);
		getContentPane().add(btnRediger);

		btnOpret = new JButton("Opret");
		btnOpret.addActionListener(controller);
		btnOpret.setFont(stdFont);
		btnOpret.setFocusable(false);
		btnOpret.setBounds(24, 145, 89, 23);
		getContentPane().add(btnOpret);

		btnNulstil = new JButton("Nulstil");
		btnNulstil.addActionListener(controller);
		btnNulstil.setFont(stdFont);
		btnNulstil.setFocusable(false);
		btnNulstil.setBounds(222, 114, 89, 23);
		getContentPane().add(btnNulstil);

		btnLuk = new JButton("Luk");
		btnLuk.addActionListener(controller);
		btnLuk.setFont(stdFont);
		btnLuk.setEnabled(true);
		btnLuk.setFocusable(false);
		btnLuk.setBounds(222, 175, 89, 23);
		getContentPane().add(btnLuk);

		resetView();
	}

	private void resetView() {
		textField_lastbilsnummer.setText("");
		textField_chauffrsnavn.setText("");
		textField_chauffrsmobilnummer.setText("");
		textField_maxlastvgt.setText("");
		textField_trailervgt.setText("");
		btnSlet.setEnabled(false);
		btnRediger.setEnabled(false);
		btnOpret.setEnabled(true);
		btnNulstil.setEnabled(true);
		jlist_oversigtoverlastbiler.setListData(serviceDao.getAllLastbiler()
				.toArray(new Lastbil[0]));
		jlist_oversigtoverlastbiler.clearSelection();
		lastbil = null;
	}

	private class Controller implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(btnLuk)) {
				FrameAdministrerLastbil.this.setVisible(false);
			} else if (ae.getSource().equals(btnSlet)) {
				if (lastbil.getTrailerStatus() != TrailerStatus.IKKE_VED_DC || lastbil.getDelordre() != null) {
					service.showWarningBox("Lastbil slettes",
							"Lastbil er aktiv og kan ikke slettes");
				} else {
					boolean lastbilSlettes = service.showConfirmBox(
							"Lastbil slettes",
							"Skal lastbil " + lastbil.getLastbilnummer()
									+ " slettes?", "Ja", "Nej");
					if (lastbilSlettes) {
						serviceDao.deleteLastbil(lastbil);
						resetView();
						frameLastbiler.resetBesoeg();
						frameLastbiler.resetLastbil();
					}
				}
			} else if (ae.getSource().equals(btnNulstil)) {
				resetView();
			} else {
				boolean noError = true;
				String lastbilnummer = textField_lastbilsnummer.getText();
				String chauffoer = textField_chauffrsnavn.getText();
				String mobilnummer = textField_chauffrsmobilnummer.getText();
				String maxLastVaegt = textField_maxlastvgt.getText();
				String trailerVaegt = textField_trailervgt.getText();
				if (noError)
					noError = Validation.checkInt(lastbilnummer, false,
							"Lastbils nummer");
				if (noError)
					noError = Validation.checkEmpty(chauffoer,
							"Chaufførens navn");
				if (noError)
					noError = Validation.checkEmpty(mobilnummer,
							"Chaufførs mobilnummer");
				if (noError)
					noError = Validation.checkDouble(maxLastVaegt, false,
							"Max Lastvægt");
				if (noError)
					noError = Validation.checkDouble(trailerVaegt, false,
							"Trailervægt");
				if (noError) {
					if (ae.getSource().equals(btnOpret)) {
						if (serviceDao.createLastbil(
								Integer.parseInt(lastbilnummer), chauffoer,
								mobilnummer, Double.parseDouble(maxLastVaegt),
								Double.parseDouble(trailerVaegt)) != null) {
							resetView();
							frameLastbiler.resetBesoeg();
							frameLastbiler.resetLastbil();
						}
					} else if (ae.getSource().equals(btnRediger)) {
						if (serviceDao.updateLastbil(lastbil,
								Integer.parseInt(lastbilnummer), chauffoer,
								mobilnummer, Double.parseDouble(maxLastVaegt),
								Double.parseDouble(trailerVaegt)) != null) {
							resetView();
							frameLastbiler.resetBesoeg();
							frameLastbiler.resetLastbil();
						}
					}
				}
			}
		}
	}

	private class SelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent arg0) {
			if (!jlist_oversigtoverlastbiler.isSelectionEmpty()) {
				btnSlet.setEnabled(true);
				btnRediger.setEnabled(true);
				lastbil = jlist_oversigtoverlastbiler.getSelectedValue();
				textField_lastbilsnummer.setText(""
						+ lastbil.getLastbilnummer());
				textField_chauffrsnavn.setText("" + lastbil.getChauffoer());
				textField_chauffrsmobilnummer.setText(""
						+ lastbil.getMobilnummer());
				textField_maxlastvgt.setText("" + lastbil.getMaxLastVaegt());
				textField_trailervgt.setText("" + lastbil.getTrailerVaegt());
			}
		}
	}
}