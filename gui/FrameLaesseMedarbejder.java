package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextField;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Font;

import model.DateUtil;
import model.Fejl;
import model.Laesning;
import model.Lastbil;
import model.Rampe;
import model.TrailerStatus;
import model.TransportMateriale;

import service.Service;
import service.ServiceDAO;

public class FrameLaesseMedarbejder extends JFrame {

	private JPanel contentPane;
	private FrameLastbiler frameLastbiler;
	private FrameTrailerMedarbejder frameTrailerMedarbejder;
	private FrameOrdrer frameOrdrer;
	private JLabel lblForventetStart, lblForventetSlut, lblAktuelStart,
			lblRampe, lblDelordrenummer, label_5, lblTransportmateriale,
			lblLssetid, lblOrdre, lblVaegt, lblFejl;
	private JTextField txfForventetStart, txfAktuelStart, txfForventetSlut,
			txfRampe, txfDelordrenummer, txfTransportMateriale, txfLaessetid,
			txfOrdre, txfVaegt;
	private JList<Laesning> listLaesninger;
	private JScrollPane spLaesninger, spFejl;
	private JComboBox cb_laesninger, cb_sorter, cb_tranportmateriale;
	private JList<Fejl> listFejl;
	private JButton btnLuk, btnStartLsning, btnFrdigLsning, btnOrdre,
			btnLastbil, btnFlytTilAnden;
	private Controller controller = new Controller();
	private ItemChangeListener itemChangeListener = new ItemChangeListener();
	private Font stdFont = new Font("Tahoma", Font.PLAIN, 11);
	private boolean tid = false, aktive = true;
	private TransportMateriale tm = null;
	private Service service = Service.getInstance();
	private ServiceDAO serviceDAO = ServiceDAO.getInstance();
	
	public FrameLaesseMedarbejder(FrameLastbiler fl, FrameOrdrer fo) {
		setTitle("Læsse medarbejder");
		setResizable(false);
		frameLastbiler = fl;
		frameOrdrer = fo;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(653, 100, 490, 498);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblForventetStart = new JLabel("Forventet start:");
		lblForventetStart.setFont(stdFont);
		lblForventetStart.setBounds(254, 131, 122, 14);
		contentPane.add(lblForventetStart);

		txfForventetStart = new JTextField();
		txfForventetStart.setEditable(false);
		txfForventetStart.setColumns(10);
		txfForventetStart.setBounds(357, 129, 115, 20);
		contentPane.add(txfForventetStart);

		lblForventetSlut = new JLabel("Forventet slut:");
		lblForventetSlut.setFont(stdFont);
		lblForventetSlut.setBounds(254, 190, 122, 14);
		contentPane.add(lblForventetSlut);

		txfAktuelStart = new JTextField();
		txfAktuelStart.setEditable(false);
		txfAktuelStart.setColumns(10);
		txfAktuelStart.setBounds(357, 158, 115, 20);
		contentPane.add(txfAktuelStart);

		lblAktuelStart = new JLabel("Aktuel start:");
		lblAktuelStart.setFont(stdFont);
		lblAktuelStart.setBounds(254, 160, 122, 14);
		contentPane.add(lblAktuelStart);

		txfForventetSlut = new JTextField();
		txfForventetSlut.setEditable(false);
		txfForventetSlut.setColumns(10);
		txfForventetSlut.setBounds(357, 187, 115, 20);
		contentPane.add(txfForventetSlut);

		lblRampe = new JLabel("Rampe:");
		lblRampe.setFont(stdFont);
		lblRampe.setBounds(254, 274, 122, 14);
		contentPane.add(lblRampe);

		txfRampe = new JTextField();
		txfRampe.setEditable(false);
		txfRampe.setColumns(10);
		txfRampe.setBounds(357, 271, 115, 20);
		contentPane.add(txfRampe);

		lblDelordrenummer = new JLabel("Delordrenummer:");
		lblDelordrenummer.setFont(stdFont);
		lblDelordrenummer.setBounds(254, 330, 122, 14);
		contentPane.add(lblDelordrenummer);

		txfDelordrenummer = new JTextField();
		txfDelordrenummer.setEditable(false);
		txfDelordrenummer.setColumns(10);
		txfDelordrenummer.setBounds(357, 327, 115, 20);
		contentPane.add(txfDelordrenummer);

		label_5 = new JLabel("Læsninger:");
		label_5.setFont(stdFont);
		label_5.setBounds(10, 104, 65, 14);
		contentPane.add(label_5);

		spLaesninger = new JScrollPane();
		spLaesninger.setBounds(11, 129, 233, 333);
		contentPane.add(spLaesninger);

		listLaesninger = new JList<Laesning>();
		listLaesninger
				.addListSelectionListener(new ListLaesningerListSelectionListener());
		spLaesninger.setViewportView(listLaesninger);
		listLaesninger.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		cb_laesninger = new JComboBox();
		cb_laesninger.setFocusable(false);
		cb_laesninger.setModel(new DefaultComboBoxModel(new String[] {
				"Aktive læsninger", "Ikke aktive læsninger" }));
		cb_laesninger.setBounds(10, 11, 234, 20);
		cb_laesninger.addItemListener(itemChangeListener);
		contentPane.add(cb_laesninger);

		cb_sorter = new JComboBox();
		cb_sorter.setFocusable(false);
		cb_sorter.setModel(new DefaultComboBoxModel(new String[] {
				"Sorter efter ramper", "Sorter efter tid" }));
		cb_sorter.setBounds(10, 73, 234, 20);
		cb_sorter.addItemListener(itemChangeListener);
		contentPane.add(cb_sorter);

		cb_tranportmateriale = new JComboBox();
		cb_tranportmateriale.setFocusable(false);
		cb_tranportmateriale.setModel(new DefaultComboBoxModel());
		cb_tranportmateriale.setBounds(10, 42, 234, 20);
		cb_tranportmateriale.addItem("ALLE");
		for (TransportMateriale tm : TransportMateriale.values())
			cb_tranportmateriale.addItem(tm.toString());
		cb_tranportmateriale.addItemListener(itemChangeListener);
		contentPane.add(cb_tranportmateriale);

		lblTransportmateriale = new JLabel("TransportMateriale:");
		lblTransportmateriale.setFont(stdFont);
		lblTransportmateriale.setBounds(254, 358, 122, 14);
		contentPane.add(lblTransportmateriale);

		txfTransportMateriale = new JTextField();
		txfTransportMateriale.setEditable(false);
		txfTransportMateriale.setColumns(10);
		txfTransportMateriale.setBounds(357, 355, 115, 20);
		contentPane.add(txfTransportMateriale);

		lblLssetid = new JLabel("Læssetid:");
		lblLssetid.setFont(stdFont);
		lblLssetid.setBounds(254, 218, 122, 14);
		contentPane.add(lblLssetid);

		txfLaessetid = new JTextField();
		txfLaessetid.setEditable(false);
		txfLaessetid.setColumns(10);
		txfLaessetid.setBounds(357, 215, 115, 20);
		contentPane.add(txfLaessetid);

		lblOrdre = new JLabel("Ordre:");
		lblOrdre.setFont(stdFont);
		lblOrdre.setBounds(254, 302, 122, 14);
		contentPane.add(lblOrdre);

		txfOrdre = new JTextField();
		txfOrdre.setEditable(false);
		txfOrdre.setColumns(10);
		txfOrdre.setBounds(357, 299, 115, 20);
		contentPane.add(txfOrdre);

		lblVaegt = new JLabel("Vægt:");
		lblVaegt.setFont(stdFont);
		lblVaegt.setBounds(254, 246, 122, 14);
		contentPane.add(lblVaegt);

		txfVaegt = new JTextField();
		txfVaegt.setEditable(false);
		txfVaegt.setColumns(10);
		txfVaegt.setBounds(357, 243, 115, 20);
		contentPane.add(txfVaegt);

		spFejl = new JScrollPane();
		spFejl.setBounds(357, 383, 115, 79);
		contentPane.add(spFejl);

		listFejl = new JList<Fejl>();
		spFejl.setViewportView(listFejl);

		lblFejl = new JLabel("Fejl:");
		lblFejl.setBounds(254, 385, 46, 14);
		contentPane.add(lblFejl);

		btnLuk = new JButton("Luk");
		btnLuk.setFont(stdFont);
		btnLuk.setFocusable(false);
		btnLuk.setBounds(254, 439, 89, 23);
		btnLuk.addActionListener(controller);
		contentPane.add(btnLuk);

		btnStartLsning = new JButton("Start Læsning");
		btnStartLsning.setFont(stdFont);
		btnStartLsning.setFocusable(false);
		btnStartLsning.setBounds(254, 10, 122, 23);
		btnStartLsning.addActionListener(controller);
		contentPane.add(btnStartLsning);

		btnFrdigLsning = new JButton("Færdig Læsning");
		btnFrdigLsning.setFont(stdFont);
		btnFrdigLsning.setFocusable(false);
		btnFrdigLsning.setBounds(254, 41, 122, 23);
		btnFrdigLsning.addActionListener(controller);
		contentPane.add(btnFrdigLsning);

		btnOrdre = new JButton("Ordre");
		btnOrdre.setFont(stdFont);
		btnOrdre.setFocusable(false);
		btnOrdre.setBounds(386, 10, 89, 23);
		btnOrdre.addActionListener(controller);
		contentPane.add(btnOrdre);

		btnLastbil = new JButton("Lastbil");
		btnLastbil.setFont(stdFont);
		btnLastbil.setFocusable(false);
		btnLastbil.setBounds(386, 41, 89, 23);
		btnLastbil.addActionListener(controller);
		contentPane.add(btnLastbil);

		btnFlytTilAnden = new JButton("Flyt til anden rampe");
		btnFlytTilAnden.setBounds(254, 72, 218, 23);
		btnFlytTilAnden.setFont(stdFont);
		btnFlytTilAnden.setFocusable(false);
		btnFlytTilAnden.addActionListener(controller);
		contentPane.add(btnFlytTilAnden);

		resetView();
	}

	public void setFrameTrailerMedarbejder(
			FrameTrailerMedarbejder frameTrailerMedarbejder) {
		if (frameTrailerMedarbejder != this.frameTrailerMedarbejder) {
			if (this.frameTrailerMedarbejder != null) {
				this.frameTrailerMedarbejder.unsetFrameTrailerMedarbejder();
			}
			this.frameTrailerMedarbejder = frameTrailerMedarbejder;
			if (frameTrailerMedarbejder != null) {
				frameTrailerMedarbejder.setFrameLaesseMedarbejder(this);
			}
		}
	}

	public void unsetFrameTrailerMedarbejder() {
		this.frameTrailerMedarbejder = null;
	}

	public void resetView() {
		if (aktive) {
			listLaesninger.setListData(serviceDAO.hentLaesninger(tm, tid,
					TrailerStatus.VED_RAMPE).toArray(new Laesning[0]));
		} else {
			listLaesninger.setListData(serviceDAO.hentLaesninger(tm, tid,
					TrailerStatus.KLAR_TIL_LAESNING).toArray(new Laesning[0]));
		}
		listLaesninger.clearSelection();
		txfForventetStart.setText("");
		txfAktuelStart.setText("");
		txfForventetSlut.setText("");
		txfLaessetid.setText("");
		txfVaegt.setText("");
		txfRampe.setText("");
		txfOrdre.setText("");
		txfDelordrenummer.setText("");
		txfTransportMateriale.setText("");
		btnOrdre.setEnabled(false);
		btnLastbil.setEnabled(false);
		btnFlytTilAnden.setEnabled(false);
		btnStartLsning.setEnabled(false);
		btnFrdigLsning.setEnabled(false);
	}

	private class ListLaesningerListSelectionListener implements
			ListSelectionListener {
		public void valueChanged(ListSelectionEvent lse) {
			if (!lse.getValueIsAdjusting()) {
				if (lse.getSource().equals(listLaesninger)) {
					Laesning laesning = listLaesninger.getSelectedValue();
					if (laesning != null) {
						txfForventetStart.setText(""
								+ DateUtil.getSmartDate(laesning
										.getForventetStart()));
						if (aktive)
							txfAktuelStart.setText(""
									+ DateUtil.getSmartDate(laesning
											.getAktuelStart()));
						txfForventetSlut.setText(""
								+ DateUtil.getSmartDate(laesning
										.beregnForventetSlut()));
						txfLaessetid.setText(""
								+ laesning.getDelordre().getLaessetid());
						txfVaegt.setText("" + laesning.getDelordre().getVaegt());
						txfRampe.setText(""
								+ laesning.getRampe().getRampenummer());
						txfOrdre.setText(""
								+ laesning.getDelordre().getOrdre()
										.getOrdrenummer());
						txfDelordrenummer.setText(""
								+ laesning.getDelordre().getDelordrenummer());
						txfTransportMateriale.setText(""
								+ laesning.getDelordre().getOrdre()
										.getTransportMateriale());
						listFejl.setListData(laesning.getBesoeg().getFejl()
								.toArray(new Fejl[0]));
						btnOrdre.setEnabled(true);
						btnLastbil.setEnabled(true);
						if (!aktive) {
							btnStartLsning.setEnabled(true);
							btnFlytTilAnden.setEnabled(true);
						}
						else {
							btnFrdigLsning.setEnabled(true);
						}
					}
				}
			}
		}
	}

	private class Controller implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			JButton jbutton = (JButton) ae.getSource();
			if (jbutton.equals(btnOrdre)) {
				Laesning laesning = listLaesninger.getSelectedValue();
				if (laesning != null) {
					frameOrdrer.setDelordreView(laesning.getDelordre());
					frameOrdrer.setVisible(true);
				}
			} else if (jbutton.equals(btnStartLsning)) {
				Laesning laesning = listLaesninger.getSelectedValue();
				if (laesning != null) {
					Lastbil lastbil = laesning.getBesoeg().getLastbil();
					if (lastbil.getTrailerStatus() == TrailerStatus.KLAR_TIL_LAESNING) {
						if (laesning.getRampe().getLaesninger().get(0)
								.equals(laesning)) {
							service.registrerStartlaesning(laesning.getBesoeg()
									.getLastbil().getLastbilnummer());
							resetView();
							frameTrailerMedarbejder.resetView();
						} else
							service.showWarningBox("Læsning ikke først i køen",
									"Første læsning i køen skal komme først og afsluttes.");
					} else if (lastbil.getTrailerStatus() == TrailerStatus.VED_RAMPE)
						service.showWarningBox("Læsning allerede i gang",
								"Læsningen er allerede i gang!");
					else
						service.showWarningBox("Fejl",
								"Læsningen er ikke klar til at starte!");
				}
			} else if (jbutton.equals(btnFrdigLsning)) {
				Laesning laesning = listLaesninger.getSelectedValue();
				if (laesning != null) {
					Lastbil lastbil = laesning.getBesoeg().getLastbil();
					if (laesning.getRampe().getLaesninger().get(0)
							.equals(laesning)
							&& lastbil.getTrailerStatus() == TrailerStatus.VED_RAMPE) {
						service.registrerFaerdigLaesning(lastbil
								.getLastbilnummer());
						resetView();
						frameTrailerMedarbejder.resetView();
					} else
						service.showWarningBox("Læsning ikke klar",
								"Læsningen er ikke klar til afslutning.");
				}
			} else if (jbutton.equals(btnLastbil)) {
				Laesning laesning = listLaesninger.getSelectedValue();
				if (laesning != null) {
					frameLastbiler.setBesoegView(laesning.getBesoeg());
					frameLastbiler.setVisible(true);
				}
			} else if (jbutton.equals(btnLuk)) {
				FrameLaesseMedarbejder.this.setVisible(false);
			} else if (jbutton.equals(btnFlytTilAnden)) {
				Laesning laesning = listLaesninger.getSelectedValue();
				if (laesning != null) {
					Object[] possibleValues = serviceDAO.getRampeUdenStopTM(
							laesning.getTransportMateriale()).toArray(
							new Object[0]);
					Rampe temp = (Rampe) service
							.showInputDialog("Flyt Læsning til anden rampe",
									"Hvilken rampe vil du flytte " + laesning
											+ " til?", possibleValues);
					if (temp != null) {
						service.flytTilAndenRampe(laesning,
								temp.getRampenummer());
						resetView();
						frameTrailerMedarbejder.resetView();
					}
				}
			} else if (jbutton.equals(cb_sorter)) {
				System.out.println(cb_sorter.getSelectedItem());
			}
		}
	}

	private class ItemChangeListener implements ItemListener {
		public void itemStateChanged(ItemEvent ie) {
			if (ie.getStateChange() == ItemEvent.SELECTED) {
				JComboBox box = (JComboBox) ie.getSource();
				if (box.equals(cb_sorter)) {
					if (cb_sorter.getSelectedIndex() == 0)
						tid = false;
					else
						tid = true;
				} else if (box.equals(cb_laesninger)) {
					if (cb_laesninger.getSelectedIndex() == 0)
						aktive = true;
					else
						aktive = false;
				} else if (box.equals(cb_tranportmateriale)) {
					if (cb_tranportmateriale.getSelectedIndex() > 0)
						tm = TransportMateriale.values()[cb_tranportmateriale
								.getSelectedIndex() - 1];
					else
						tm = null;
				}
			}
				resetView();
		}
	}
}