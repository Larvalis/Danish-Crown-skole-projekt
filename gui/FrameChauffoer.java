package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import model.Lastbil;
import model.TrailerStatus;

import service.Service;
import service.ServiceDAO;
import service.Validation;

public class FrameChauffoer extends JFrame {

	private JPanel contentPane;
	private JTextField txfLastbilsNummer;
	private JLabel lblLastbilsNummer;
	private JButton btnAnkomst, btnAfgang;
	private Controller controller = new Controller();
	private Service service = Service.getInstance();
	private ServiceDAO serviceDAO = ServiceDAO.getInstance();
	private Font stdFont = new Font("Tahoma", Font.PLAIN, 11);

	public FrameChauffoer() {
		setResizable(false);
		setTitle("Chauffør");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 217, 103);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txfLastbilsNummer = new JTextField();
		txfLastbilsNummer.setColumns(10);
		txfLastbilsNummer.setBounds(137, 11, 63, 20);
		contentPane.add(txfLastbilsNummer);

		lblLastbilsNummer = new JLabel("Lastbils nummer:");
		lblLastbilsNummer.setFont(stdFont);
		lblLastbilsNummer.setBounds(10, 14, 81, 14);
		contentPane.add(lblLastbilsNummer);

		btnAnkomst = new JButton("Ankomst");
		btnAnkomst.addActionListener(controller);
		btnAnkomst.setFont(stdFont);
		btnAnkomst.setFocusable(false);
		btnAnkomst.setBounds(10, 42, 89, 23);
		contentPane.add(btnAnkomst);

		btnAfgang = new JButton("Afgang");
		btnAfgang.addActionListener(controller);
		btnAfgang.setFocusable(false);
		btnAfgang.setFont(stdFont);
		btnAfgang.setBounds(111, 42, 89, 23);
		contentPane.add(btnAfgang);
	}

	private void resetView() {
		txfLastbilsNummer.setText("");
	}

	private class Controller implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			JButton source = (JButton) ae.getSource();
			if (source.equals(btnAnkomst)) {
				boolean noError = true;
				noError = Validation.checkInt(txfLastbilsNummer.getText(),
						false, "Lastbils nummer");
				if (noError) {
					int lastbilsNummer = Integer.parseInt(txfLastbilsNummer
							.getText());
					Lastbil lastbil = serviceDAO.getLastbil(lastbilsNummer);
					if (lastbil != null) {
						if (lastbil.getTrailerStatus() == TrailerStatus.IKKE_VED_DC) {
							boolean rigtigeLastbil = service.showConfirmBox(
									"Lastbils ankomst",
									"Lastbils nummer: "
											+ lastbil.getLastbilnummer()
											+ "\nChauffør: "
											+ lastbil.getChauffoer(),
									"Bekræft", "Annuller");
							if (rigtigeLastbil) {
								if (lastbil.getDelordre() != null) {
									Object objInput = null;
									noError = false;
									while (!noError || objInput == null) {
										objInput = service.showInputBox(
												"Hviletid",
												"Indtast hviletid:", "");
										if (objInput != null)
											noError = Validation.checkInt(
													objInput.toString(), false,
													"Hviletid");
										else
											break;
									}
									if (noError) {
										service.registrerLastbilsAnkomst(
												lastbilsNummer, Integer
														.parseInt(objInput
																.toString()));
										service.showInfoBox("Ankomst registreret");
									}
								} else {
									service.showWarningBox(
											"Ankomst ikke registreret",
											"Lastbilen har ingen ordre");
								}
							}
						} else
							service.showWarningBox("Ankomst ikke registreret",
									"Lastbilens ankomst er allerede registreret");
					} else {
						service.showWarningBox("Ankomst ikke registreret",
								"Der findes ikke en lastbil med dette nummer");
					}
				}
			} else if (source.equals(btnAfgang)) {
				boolean noError = true;
				noError = Validation.checkInt(txfLastbilsNummer.getText(),
						false, "Lastbils nummer");

				if (noError) {
					int lastbilsNummer = Integer.parseInt(txfLastbilsNummer
							.getText());
					Lastbil lastbil = serviceDAO.getLastbil(lastbilsNummer);
					if (lastbil != null) {
						if (lastbil.getTrailerStatus() == TrailerStatus.TIL_AFHENTNING) {
							service.registrerLastbilsAfgang(lastbilsNummer);
							service.showInfoBox("Afgang registreret");
						} else {
							if (lastbil.getTrailerStatus() == TrailerStatus.IKKE_VED_DC)
								service.showWarningBox(
										"Afgang ikke registreret",
										"Lastbilen er ikke ved DC");
							else
								service.showWarningBox(
										"Afgang ikke registreret",
										"Traileren er ikke klar til afhentning");
						}
					} else {
						service.showWarningBox("Afgang ikke registreret",
								"Der findes ikke en lastbil med dette nummer");
					}
				}
			}
			resetView();
		}
	}
}