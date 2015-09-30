package gui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.BorderLayout;
import java.awt.Font;

import model.DateUtil;
import model.Fejl;
import model.Laesning;
import model.Lastbil;
import model.Rampe;
import model.TrailerStatus;

import service.Service;
import service.ServiceDAO;
import service.Validation;

public class FrameTrailerMedarbejder extends JFrame {

	private JPanel contentPane, panel;
	private JComboBox cb_LaesningType;
	private JButton btnVejTrailer, btnTildelRampe, btnRamper, btnBeregnTider,
			btnLuk;
	private JScrollPane scrollPane;
	private JList<Laesning> listLaesning;
	private JLabel lblStartvgt, lblSlutvgt, lblTrailerStatus, lblMaxLastVgt,
			lblTrailerVgt, lblDelordrevgt, lblForventetStart, lblForventetSlut,
			lblRampe;
	private JTextField txtStartvgt, txtDelordrevgt, txtSlutvgt, txtMaxlastvgt,
			txtTrailervgt, txtTrailerstatus, txtForventetstart,
			txtForventetslut, txtRampe;
	private FrameRamper frameRamper;
	private FrameLaesseMedarbejder frameLaesseMedarbejder;
	private ItemChangeListener itemChangeListener = new ItemChangeListener();
	private Controller controller = new Controller();
	private Font stdFont = new Font("Tahoma", Font.PLAIN, 11);
	private Service service = Service.getInstance();
	private ServiceDAO serviceDAO = ServiceDAO.getInstance();

	public FrameTrailerMedarbejder(FrameRamper fr) {
		setResizable(false);
		setTitle("Trailer Medarbejder");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(653, 100, 509, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		frameRamper = fr;

		cb_LaesningType = new JComboBox();
		cb_LaesningType.setModel(new DefaultComboBoxModel(new String[] {
				"Ankommet Læsninger", "Færdige Læsninger" }));
		cb_LaesningType.setBounds(10, 11, 217, 20);
		cb_LaesningType.addItemListener(itemChangeListener);
		panel.add(cb_LaesningType);

		btnVejTrailer = new JButton("Vej trailer");
		btnVejTrailer.setFont(stdFont);
		btnVejTrailer.setBounds(237, 45, 125, 23);
		btnVejTrailer.setFocusable(false);
		btnVejTrailer.addActionListener(controller);
		panel.add(btnVejTrailer);

		btnTildelRampe = new JButton("Tildel rampe");
		btnTildelRampe.setFont(stdFont);
		btnTildelRampe.setBounds(237, 79, 125, 23);
		btnTildelRampe.setFocusable(false);
		btnTildelRampe.addActionListener(controller);
		panel.add(btnTildelRampe);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 217, 295);
		panel.add(scrollPane);
		listLaesning = new JList<Laesning>();
		listLaesning
				.addListSelectionListener(new ListLaesningListSelectionListener());
		listLaesning.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listLaesning);

		btnBeregnTider = new JButton("Beregn tider");
		btnBeregnTider.setFont(stdFont);
		btnBeregnTider.setBounds(237, 113, 125, 23);
		btnBeregnTider.setFocusable(false);
		btnBeregnTider.addActionListener(controller);
		panel.add(btnBeregnTider);

		btnLuk = new JButton("Luk");
		btnLuk.setFont(stdFont);
		btnLuk.setBounds(10, 350, 103, 23);
		btnLuk.setFocusable(false);
		btnLuk.addActionListener(controller);
		panel.add(btnLuk);

		lblStartvgt = new JLabel("StartVægt:");
		lblStartvgt.setFont(stdFont);
		lblStartvgt.setBounds(237, 147, 105, 14);
		panel.add(lblStartvgt);

		lblSlutvgt = new JLabel("Slutvægt:");
		lblSlutvgt.setFont(stdFont);
		lblSlutvgt.setBounds(237, 197, 105, 14);
		panel.add(lblSlutvgt);

		lblTrailerStatus = new JLabel("Trailer status:");
		lblTrailerStatus.setFont(stdFont);
		lblTrailerStatus.setBounds(237, 272, 105, 14);
		panel.add(lblTrailerStatus);

		lblMaxLastVgt = new JLabel("Max last vægt:");
		lblMaxLastVgt.setFont(stdFont);
		lblMaxLastVgt.setBounds(237, 222, 105, 14);
		panel.add(lblMaxLastVgt);

		lblTrailerVgt = new JLabel("Trailer Vægt:");
		lblTrailerVgt.setFont(stdFont);
		lblTrailerVgt.setBounds(237, 247, 105, 14);
		panel.add(lblTrailerVgt);

		lblDelordrevgt = new JLabel("Delordrevægt:");
		lblDelordrevgt.setFont(stdFont);
		lblDelordrevgt.setBounds(237, 172, 105, 14);
		panel.add(lblDelordrevgt);

		lblForventetStart = new JLabel("Forventet start:");
		lblForventetStart.setFont(stdFont);
		lblForventetStart.setBounds(237, 297, 105, 14);
		panel.add(lblForventetStart);

		lblForventetSlut = new JLabel("Forventet slut:");
		lblForventetSlut.setFont(stdFont);
		lblForventetSlut.setBounds(237, 322, 105, 14);
		panel.add(lblForventetSlut);

		lblRampe = new JLabel("Rampe:");
		lblRampe.setFont(stdFont);
		lblRampe.setBounds(237, 347, 105, 14);
		panel.add(lblRampe);

		txtStartvgt = new JTextField();
		txtStartvgt.setEditable(false);
		txtStartvgt.setBounds(332, 147, 141, 20);
		txtStartvgt.setColumns(10);
		panel.add(txtStartvgt);

		txtDelordrevgt = new JTextField();
		txtDelordrevgt.setEditable(false);
		txtDelordrevgt.setBounds(332, 172, 141, 20);
		txtDelordrevgt.setColumns(10);
		panel.add(txtDelordrevgt);

		txtSlutvgt = new JTextField();
		txtSlutvgt.setEditable(false);
		txtSlutvgt.setBounds(332, 197, 141, 20);
		txtSlutvgt.setColumns(10);
		panel.add(txtSlutvgt);

		txtMaxlastvgt = new JTextField();
		txtMaxlastvgt.setEditable(false);
		txtMaxlastvgt.setBounds(332, 222, 141, 20);
		txtMaxlastvgt.setColumns(10);
		panel.add(txtMaxlastvgt);

		txtTrailervgt = new JTextField();
		txtTrailervgt.setEditable(false);
		txtTrailervgt.setBounds(332, 247, 141, 20);
		txtTrailervgt.setColumns(10);
		panel.add(txtTrailervgt);

		txtTrailerstatus = new JTextField();
		txtTrailerstatus.setEditable(false);
		txtTrailerstatus.setBounds(332, 272, 141, 20);
		txtTrailerstatus.setColumns(10);
		panel.add(txtTrailerstatus);

		txtForventetstart = new JTextField();
		txtForventetstart.setEditable(false);
		txtForventetstart.setBounds(332, 297, 141, 20);
		txtForventetstart.setColumns(10);
		panel.add(txtForventetstart);

		txtForventetslut = new JTextField();
		txtForventetslut.setEditable(false);
		txtForventetslut.setBounds(332, 323, 141, 20);
		txtForventetslut.setColumns(10);
		panel.add(txtForventetslut);

		txtRampe = new JTextField();
		txtRampe.setEditable(false);
		txtRampe.setBounds(332, 348, 141, 20);
		txtRampe.setColumns(10);
		panel.add(txtRampe);

		btnRamper = new JButton("Ramper");
		btnRamper.setFont(stdFont);
		btnRamper.setBounds(237, 11, 125, 23);
		btnRamper.setFocusable(false);
		btnRamper.addActionListener(controller);
		panel.add(btnRamper);

		resetView();
	}

	public void setFrameLaesseMedarbejder(
			FrameLaesseMedarbejder frameLaesseMedarbejder) {
		if (frameLaesseMedarbejder != this.frameLaesseMedarbejder) {
			if (this.frameLaesseMedarbejder != null) {
				this.frameLaesseMedarbejder.unsetFrameTrailerMedarbejder();
			}
			this.frameLaesseMedarbejder = frameLaesseMedarbejder;
			if (frameLaesseMedarbejder != null) {
				frameLaesseMedarbejder.setFrameTrailerMedarbejder(this);
			}
		}
	}

	public void unsetFrameTrailerMedarbejder() {
		this.frameLaesseMedarbejder = null;
	}

	public void resetView() {
		setList(cb_LaesningType.getSelectedIndex());
		listLaesning.clearSelection();
		txtForventetslut.setText("");
		txtForventetstart.setText("");
		txtMaxlastvgt.setText("");
		txtRampe.setText("");
		txtSlutvgt.setText("");
		txtStartvgt.setText("");
		txtTrailerstatus.setText("");
		txtTrailervgt.setText("");
		txtDelordrevgt.setText("");
		btnTildelRampe.setEnabled(false);
		btnVejTrailer.setEnabled(false);
	}

	public void setView(Laesning laesning) {
		txtForventetslut.setText(DateUtil.getSmartDate(laesning
				.beregnForventetSlut()));
		txtForventetstart.setText(DateUtil.getSmartDate(laesning
				.getForventetStart()));
		Lastbil lastbil = laesning.getDelordre().getLastbil();
		txtMaxlastvgt.setText("" + lastbil.getMaxLastVaegt());
		Rampe rampe = laesning.getRampe();
		if (rampe != null)
			txtRampe.setText("" + rampe.getRampenummer());
		else
			txtRampe.setText("" + Fejl.RAMPE_IKKE_FUNDET);
		txtSlutvgt.setText("" + laesning.getAktuelSlutVaegt());
		txtStartvgt.setText("" + laesning.getAktuelStartVaegt());
		txtTrailerstatus.setText("" + lastbil.getTrailerStatus());
		txtTrailervgt.setText("" + lastbil.getTrailerVaegt());
		txtDelordrevgt.setText("" + laesning.getDelordre().getVaegt());
		if (cb_LaesningType.getSelectedIndex() == 0)
			btnTildelRampe.setEnabled(true);
		btnVejTrailer.setEnabled(true);
	}

	public void setList(int type) {
		if (type == 0) {
			listLaesning.setListData(serviceDAO.hentLaesningerViaTrailerStatus(
					TrailerStatus.ANKOMMET).toArray(new Laesning[0]));
		} else if (type == 1) {
			listLaesning.setListData(serviceDAO.hentLaesningerViaTrailerStatus(
					TrailerStatus.FAERDIG_LAESSET).toArray(new Laesning[0]));
		} else
			listLaesning.setListData(serviceDAO.hentLaesningerUdenRampe(null)
					.toArray(new Laesning[0]));
	}

	private class ListLaesningListSelectionListener implements
			ListSelectionListener {
		public void valueChanged(ListSelectionEvent lse) {
			if (!lse.getValueIsAdjusting()) {
				if (lse.getSource().equals(listLaesning)) {
					Laesning laesning = listLaesning.getSelectedValue();
					if (laesning != null) {
						setView(laesning);
					}
				}
			}
		}
	}

	private class Controller implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			JButton jbutton = (JButton) ae.getSource();
			if (jbutton.equals(btnBeregnTider)) {
				for (Rampe r : serviceDAO.getAllRamper())
					r.beregnStartTidspunkter();
				resetView();
				frameLaesseMedarbejder.resetView();
			} else if (jbutton.equals(btnLuk)) {
				FrameTrailerMedarbejder.this.setVisible(false);
			} else if (jbutton.equals(btnRamper)) {
				frameRamper.setVisible(true);
			} else if (jbutton.equals(btnTildelRampe)) {
				Laesning laesning = listLaesning.getSelectedValue();
				if (laesning != null) {
					service.findOgTildelRampe(laesning);
				}
				resetView();
				frameLaesseMedarbejder.resetView();
			} else if (jbutton.equals(btnVejTrailer)) {
				Laesning laesning = listLaesning.getSelectedValue();
				if (laesning != null) {
					Lastbil lastbil = laesning.getDelordre().getLastbil();
					if (lastbil.getTrailerStatus() == TrailerStatus.ANKOMMET) {
						Object temp = service.showInputBox("Vejning", "Indsæt vægt på trailer", "");
						if (temp!=null) {
							if (Validation.checkDouble((String) temp, false, "vægt input")) {
								Double input = Double.parseDouble((String) temp);
								if (input<lastbil.getTrailerVaegt()){
									service.showWarningBox("Vægtfejl", "Startvægt kan ikke være mindre end trailers vægt");	
								} else {
									service.registrerAnkomstvaegt(
											lastbil.getLastbilnummer(), input);
								}
							}
						}
					} else {
						Object temp = service.showInputBox("Vejning",
										"Indsæt vægt på trailer. (Forventet: "
												+ (laesning.getAktuelStartVaegt() + laesning.getDelordre()
														.getVaegt()) + ")", "");
						if (temp != null
								&& Validation.checkDouble((String) temp, false, "vægt input")) {
							Double input = Double.parseDouble((String) temp);
							service.registrerAfgangsvaegt(
									lastbil.getLastbilnummer(), input);
						}
					}
				}
				resetView();
				frameLaesseMedarbejder.resetView();
			}
		}
	}

	private class ItemChangeListener implements ItemListener {
		public void itemStateChanged(ItemEvent ie) {
			if (ie.getStateChange() == ItemEvent.SELECTED) {
				setList(cb_LaesningType.getSelectedIndex());
				resetView();
			}
		}
	}
}