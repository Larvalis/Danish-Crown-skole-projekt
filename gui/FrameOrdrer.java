package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import model.DateUtil;
import model.Delordre;
import model.Ordre;

import service.ServiceDAO;

public class FrameOrdrer extends JFrame {

	private JPanel contentPane;
	private JScrollPane sp_ordre, sp_delordre;
	private JList<Ordre> jlist_ordre;
	private JList<Delordre> jlist_delordre;
	private SelectionListener listSelectionListener = new SelectionListener();
	private JLabel lblOrdre, lblDelordre, lblOrdrenummer, lblDato,
			lblDelordrenummer, lblLssedato, lblLssetid, lblVgt, lblLssedatp,
			lblTransportmateriale, lblBruttovgt, lblFejlmargenVedVgt,
			lblChauffr;
	private JTextField txf_ordrenummer, txf_dato, txf_transportmateriale,
			txf_bruttovaegt, txf_margenvedvejning, txf_delordrenummer,
			txf_lastbil, txf_laessetid, txf_vaegt, txf_laessedato,
			txf_chauffoer;
	private Font stdFont = new Font("Tahoma", Font.PLAIN, 11);
	private JButton btnLuk;
	private ServiceDAO serviceDao = ServiceDAO.getInstance();

	public FrameOrdrer() {
		setResizable(false);
		setTitle("Odrer");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(653, 100, 470, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblOrdre = new JLabel("Ordre:");
		lblOrdre.setFont(stdFont);
		lblOrdre.setBounds(10, 11, 46, 14);
		contentPane.add(lblOrdre);

		sp_ordre = new JScrollPane();
		sp_ordre.setBounds(10, 30, 220, 223);
		contentPane.add(sp_ordre);
		jlist_ordre = new JList<Ordre>();
		jlist_ordre.addListSelectionListener(listSelectionListener);
		sp_ordre.setViewportView(jlist_ordre);

		lblDelordre = new JLabel("Delordre:");
		lblDelordre.setFont(stdFont);
		lblDelordre.setBounds(10, 264, 72, 14);
		contentPane.add(lblDelordre);

		sp_delordre = new JScrollPane();
		sp_delordre.setBounds(10, 289, 220, 153);
		contentPane.add(sp_delordre);
		jlist_delordre = new JList<Delordre>();
		jlist_delordre.addListSelectionListener(listSelectionListener);
		sp_delordre.setViewportView(jlist_delordre);

		lblOrdrenummer = new JLabel("Ordrenummer:");
		lblOrdrenummer.setFont(stdFont);
		lblOrdrenummer.setBounds(241, 32, 94, 14);
		contentPane.add(lblOrdrenummer);

		lblDato = new JLabel("Dato:");
		lblDato.setFont(stdFont);
		lblDato.setBounds(241, 57, 94, 14);
		contentPane.add(lblDato);

		txf_ordrenummer = new JTextField();
		txf_ordrenummer.setEditable(false);
		txf_ordrenummer.setBounds(345, 30, 106, 20);
		contentPane.add(txf_ordrenummer);
		txf_ordrenummer.setColumns(10);

		lblTransportmateriale = new JLabel("TransportMateriale:");
		lblTransportmateriale.setFont(stdFont);
		lblTransportmateriale.setBounds(241, 82, 105, 14);
		contentPane.add(lblTransportmateriale);

		lblBruttovgt = new JLabel("Bruttovægt:");
		lblBruttovgt.setFont(stdFont);
		lblBruttovgt.setBounds(241, 107, 94, 14);
		contentPane.add(lblBruttovgt);

		lblFejlmargenVedVgt = new JLabel("Margen ved vejning:");
		lblFejlmargenVedVgt.setFont(stdFont);
		lblFejlmargenVedVgt.setBounds(241, 132, 105, 14);
		contentPane.add(lblFejlmargenVedVgt);

		txf_dato = new JTextField();
		txf_dato.setEditable(false);
		txf_dato.setColumns(10);
		txf_dato.setBounds(345, 54, 106, 20);
		contentPane.add(txf_dato);

		txf_transportmateriale = new JTextField();
		txf_transportmateriale.setEditable(false);
		txf_transportmateriale.setColumns(10);
		txf_transportmateriale.setBounds(345, 79, 106, 20);
		contentPane.add(txf_transportmateriale);

		txf_bruttovaegt = new JTextField();
		txf_bruttovaegt.setEditable(false);
		txf_bruttovaegt.setColumns(10);
		txf_bruttovaegt.setBounds(345, 104, 106, 20);
		contentPane.add(txf_bruttovaegt);

		txf_margenvedvejning = new JTextField();
		txf_margenvedvejning.setEditable(false);
		txf_margenvedvejning.setColumns(10);
		txf_margenvedvejning.setBounds(345, 129, 106, 20);
		contentPane.add(txf_margenvedvejning);

		lblDelordrenummer = new JLabel("Delordrenummer:");
		lblDelordrenummer.setFont(stdFont);
		lblDelordrenummer.setBounds(241, 291, 94, 14);
		contentPane.add(lblDelordrenummer);

		lblLssedato = new JLabel("Lastbil:");
		lblLssedato.setFont(stdFont);
		lblLssedato.setBounds(241, 316, 94, 14);
		contentPane.add(lblLssedato);

		lblLssetid = new JLabel("Læssetid:");
		lblLssetid.setFont(stdFont);
		lblLssetid.setBounds(241, 366, 105, 14);
		contentPane.add(lblLssetid);

		lblVgt = new JLabel("Vægt:");
		lblVgt.setFont(stdFont);
		lblVgt.setBounds(241, 391, 94, 14);
		contentPane.add(lblVgt);

		lblLssedatp = new JLabel("Læssedato:");
		lblLssedatp.setFont(stdFont);
		lblLssedatp.setBounds(241, 416, 105, 14);
		contentPane.add(lblLssedatp);

		txf_delordrenummer = new JTextField();
		txf_delordrenummer.setEditable(false);
		txf_delordrenummer.setColumns(10);
		txf_delordrenummer.setBounds(345, 289, 106, 20);
		contentPane.add(txf_delordrenummer);

		txf_lastbil = new JTextField();
		txf_lastbil.setEditable(false);
		txf_lastbil.setColumns(10);
		txf_lastbil.setBounds(345, 313, 106, 20);
		contentPane.add(txf_lastbil);

		txf_laessetid = new JTextField();
		txf_laessetid.setEditable(false);
		txf_laessetid.setColumns(10);
		txf_laessetid.setBounds(345, 363, 106, 20);
		contentPane.add(txf_laessetid);

		txf_vaegt = new JTextField();
		txf_vaegt.setEditable(false);
		txf_vaegt.setColumns(10);
		txf_vaegt.setBounds(345, 388, 106, 20);
		contentPane.add(txf_vaegt);

		txf_laessedato = new JTextField();
		txf_laessedato.setEditable(false);
		txf_laessedato.setColumns(10);
		txf_laessedato.setBounds(345, 413, 106, 20);
		contentPane.add(txf_laessedato);

		lblChauffr = new JLabel("Chauffør:");
		lblChauffr.setFont(stdFont);
		lblChauffr.setBounds(241, 341, 94, 14);
		contentPane.add(lblChauffr);

		txf_chauffoer = new JTextField();
		txf_chauffoer.setEditable(false);
		txf_chauffoer.setColumns(10);
		txf_chauffoer.setBounds(345, 338, 106, 20);
		contentPane.add(txf_chauffoer);

		btnLuk = new JButton("Luk");
		btnLuk.setFont(stdFont);
		btnLuk.setFocusable(false);
		btnLuk.setBounds(10, 453, 89, 23);
		btnLuk.addActionListener(new Controller());
		contentPane.add(btnLuk);

		resetView();
	}

	public void resetView() {
		jlist_ordre.setListData(serviceDao.getAllOrdrerFraDagsDato().toArray(
				new Ordre[0]));
		jlist_ordre.clearSelection();
		txf_ordrenummer.setText("");
		txf_dato.setText("");
		txf_transportmateriale.setText("");
		txf_bruttovaegt.setText("");
		txf_margenvedvejning.setText("");
		resetDelordreTxf();
		jlist_delordre.setListData(new Delordre[0]);
	}

	public void setDelordreView(Delordre delordre) {
		if (delordre != null) {
			Ordre ordre = delordre.getOrdre();
			jlist_ordre.setSelectedValue(ordre, true);
			jlist_delordre.setSelectedValue(delordre, true);
			setDelordeTxf(delordre);
		}
	}

	private void resetDelordreTxf() {
		txf_delordrenummer.setText("");
		txf_lastbil.setText("");
		txf_chauffoer.setText("");
		txf_laessetid.setText("");
		txf_vaegt.setText("");
		txf_laessedato.setText("");
	}

	private void setDelordeTxf(Delordre delordre) {
		txf_delordrenummer.setText("" + delordre.getDelordrenummer());
		txf_lastbil.setText("" + delordre.getLastbil().getLastbilnummer());
		txf_chauffoer.setText("" + delordre.getLastbil().getChauffoer());
		txf_laessetid.setText("" + delordre.getLaessetid());
		txf_vaegt.setText("" + delordre.getVaegt());
		txf_laessedato.setText(""
				+ DateUtil.formatDate(delordre.getLaessedato()));
	}

	private class SelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent lse) {
			if (!lse.getValueIsAdjusting()) {
				if (lse.getSource().equals(jlist_ordre)) {
					Ordre ordre = jlist_ordre.getSelectedValue();
					if (ordre != null) {
						txf_ordrenummer.setText("" + ordre.getOrdrenummer());
						txf_dato.setText(""
								+ DateUtil.formatDate(ordre.getDato()));
						txf_transportmateriale.setText(""
								+ ordre.getTransportMateriale());
						txf_bruttovaegt.setText("" + ordre.getBruttovaegt());
						txf_margenvedvejning.setText(""
								+ ordre.getMargenVedVejning() * 100);
						jlist_delordre.setListData(ordre.getDelordre().toArray(
								new Delordre[0]));
						resetDelordreTxf();
					}
				}
			} else if (lse.getSource().equals(jlist_delordre)) {
				Delordre delordre = jlist_delordre.getSelectedValue();
				if (delordre != null) {
					setDelordeTxf(delordre);
				}
			}
		}
	}

	private class Controller implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			FrameOrdrer.this.setVisible(false);
		}
	}
}