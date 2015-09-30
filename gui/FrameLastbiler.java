package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Font;

import model.Besoeg;
import model.DateUtil;
import model.Fejl;
import model.Lastbil;

import service.ServiceDAO;

public class FrameLastbiler extends JFrame {

	private JPanel panel;
	private JList<Lastbil> listLastbil;
	private JList<Besoeg> listBesoeg;
	private JButton btnLuk;
	private JLabel lblAnkomst, lblLastbil, lblBesg, lblAfgang, lblHviletid,
			lblFejl, lblLastbilnummer, lblChauffr, lblMobilnummer,
			lblTrailerStatus, lblMaxLastVgt, lblTrailerVgt;
	private JTextField txtLastbilnr, txtChauffoer, txtMobilnr,
			txtTrailerstatus, txtMaxlastvaegt, txtTrailervaegt, txtAnkomst,
			txtAfgang, txtHviletid;
	private JList<Fejl> listFejl;
	private ServiceDAO serviceDao = ServiceDAO.getInstance();
	private JScrollPane scrollPane, scrollPane_1, scrollPane_2;
	private Font stdFont = new Font("Tahoma", Font.PLAIN, 11);

	public FrameLastbiler() {
		setResizable(false);
		setTitle("Lastbiler");
		setBounds(653, 100, 525, 438);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		lblLastbil = new JLabel("Lastbil:");
		lblLastbil.setFont(stdFont);
		lblLastbil.setBounds(28, 13, 46, 14);
		panel.add(lblLastbil);

		lblBesg = new JLabel("Besøg:");
		lblBesg.setFont(stdFont);
		lblBesg.setBounds(28, 215, 46, 14);
		panel.add(lblBesg);

		btnLuk = new JButton("Luk");
		btnLuk.setFont(stdFont);
		btnLuk.addActionListener(new BtnLukActionListener());
		btnLuk.setFocusable(false);
		btnLuk.setBounds(243, 367, 89, 23);
		panel.add(btnLuk);

		lblLastbilnummer = new JLabel("Lastbilnummer:");
		lblLastbilnummer.setFont(stdFont);
		lblLastbilnummer.setBounds(243, 38, 122, 14);
		panel.add(lblLastbilnummer);

		lblChauffr = new JLabel("Chauffør:");
		lblChauffr.setFont(stdFont);
		lblChauffr.setBounds(243, 63, 122, 14);
		panel.add(lblChauffr);

		lblMobilnummer = new JLabel("Mobilnummer:");
		lblMobilnummer.setFont(stdFont);
		lblMobilnummer.setBounds(243, 88, 122, 14);
		panel.add(lblMobilnummer);

		lblTrailerStatus = new JLabel("Trailer status:");
		lblTrailerStatus.setFont(stdFont);
		lblTrailerStatus.setBounds(243, 163, 122, 14);
		panel.add(lblTrailerStatus);

		lblMaxLastVgt = new JLabel("Max last vægt:");
		lblMaxLastVgt.setFont(stdFont);
		lblMaxLastVgt.setBounds(243, 113, 122, 14);
		panel.add(lblMaxLastVgt);

		lblTrailerVgt = new JLabel("Trailer vægt:");
		lblTrailerVgt.setFont(stdFont);
		lblTrailerVgt.setBounds(243, 138, 122, 14);
		panel.add(lblTrailerVgt);

		txtLastbilnr = new JTextField();
		txtLastbilnr.setEditable(false);
		txtLastbilnr.setBounds(346, 38, 138, 20);
		panel.add(txtLastbilnr);
		txtLastbilnr.setColumns(10);

		txtChauffoer = new JTextField();
		txtChauffoer.setEditable(false);
		txtChauffoer.setBounds(346, 63, 138, 20);
		panel.add(txtChauffoer);
		txtChauffoer.setColumns(10);

		txtMobilnr = new JTextField();
		txtMobilnr.setEditable(false);
		txtMobilnr.setBounds(346, 88, 138, 20);
		panel.add(txtMobilnr);
		txtMobilnr.setColumns(10);

		txtTrailerstatus = new JTextField();
		txtTrailerstatus.setEditable(false);
		txtTrailerstatus.setBounds(346, 163, 138, 20);
		panel.add(txtTrailerstatus);
		txtTrailerstatus.setColumns(10);

		txtMaxlastvaegt = new JTextField();
		txtMaxlastvaegt.setEditable(false);
		txtMaxlastvaegt.setBounds(346, 113, 138, 20);
		panel.add(txtMaxlastvaegt);
		txtMaxlastvaegt.setColumns(10);

		txtTrailervaegt = new JTextField();
		txtTrailervaegt.setEditable(false);
		txtTrailervaegt.setBounds(346, 138, 138, 20);
		panel.add(txtTrailervaegt);
		txtTrailervaegt.setColumns(10);

		lblAnkomst = new JLabel("Ankomst:");
		lblAnkomst.setFont(stdFont);
		lblAnkomst.setBounds(243, 240, 122, 14);
		panel.add(lblAnkomst);

		lblAfgang = new JLabel("Afgang:");
		lblAfgang.setFont(stdFont);
		lblAfgang.setBounds(243, 265, 122, 14);
		panel.add(lblAfgang);

		lblHviletid = new JLabel("Hviletid:");
		lblHviletid.setFont(stdFont);
		lblHviletid.setBounds(243, 290, 122, 14);
		panel.add(lblHviletid);

		lblFejl = new JLabel("Fejl:");
		lblFejl.setFont(stdFont);
		lblFejl.setBounds(243, 315, 122, 14);
		panel.add(lblFejl);

		txtAnkomst = new JTextField();
		txtAnkomst.setEditable(false);
		txtAnkomst.setBounds(346, 240, 138, 20);
		panel.add(txtAnkomst);
		txtAnkomst.setColumns(10);

		txtAfgang = new JTextField();
		txtAfgang.setEditable(false);
		txtAfgang.setBounds(346, 265, 138, 20);
		panel.add(txtAfgang);
		txtAfgang.setColumns(10);

		txtHviletid = new JTextField();
		txtHviletid.setEditable(false);
		txtHviletid.setBounds(346, 290, 138, 20);
		panel.add(txtHviletid);
		txtHviletid.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 38, 205, 150);
		panel.add(scrollPane);

		listLastbil = new JList<Lastbil>();
		scrollPane.setViewportView(listLastbil);
		listLastbil.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listLastbil
				.addListSelectionListener(new listLastbilListSelectionListener());
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(28, 240, 205, 150);
		panel.add(scrollPane_1);

		listBesoeg = new JList<Besoeg>();
		scrollPane_1.setViewportView(listBesoeg);
		listBesoeg.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listBesoeg
				.addListSelectionListener(new ListBesoegListSelectionListener());
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(346, 315, 138, 77);
		panel.add(scrollPane_2);

		listFejl = new JList<Fejl>();
		scrollPane_2.setViewportView(listFejl);
		listFejl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		resetBesoeg();
		resetLastbil();
	}

	public void resetLastbil() {
		txtLastbilnr.setText("");
		txtChauffoer.setText("");
		txtMaxlastvaegt.setText("");
		txtMobilnr.setText("");
		txtTrailerstatus.setText("");
		txtTrailervaegt.setText("");
		listLastbil.setListData(serviceDao.getAllLastbiler().toArray(
				new Lastbil[0]));
		listLastbil.clearSelection();
	}

	public void resetBesoeg() {
		txtAfgang.setText("");
		txtAnkomst.setText("");
		txtHviletid.setText("");
		listFejl.clearSelection();
		listBesoeg.clearSelection();
		listFejl.setListData(new Fejl[0]);
	}

	public void setBesoegView(Besoeg besoeg) {
		if (besoeg != null) {
			Lastbil lastbil = besoeg.getLastbil();
			listLastbil.setSelectedValue(lastbil, true);
			listBesoeg.setSelectedValue(besoeg, true);
			setBesoegTxf(besoeg);
		}
	}

	private void setBesoegTxf(Besoeg besoeg) {
		listFejl.setListData(besoeg.getFejl().toArray(new Fejl[0]));
		txtAfgang.setText(DateUtil.getSmartDate(besoeg.getAfgang()));
		txtAnkomst.setText(DateUtil.getSmartDate(besoeg.getAnkomst()));
		txtHviletid.setText(besoeg.getHviletid() + " min");
	}

	private class listLastbilListSelectionListener implements
			ListSelectionListener {
		public void valueChanged(ListSelectionEvent arg0) {
			if (!listLastbil.isSelectionEmpty()) {
				resetBesoeg();
				Lastbil lastbil = listLastbil.getSelectedValue();
				txtLastbilnr.setText("" + lastbil.getLastbilnummer());
				txtChauffoer.setText("" + lastbil.getChauffoer());
				txtMobilnr.setText("" + lastbil.getMobilnummer());
				txtMaxlastvaegt.setText("" + lastbil.getMaxLastVaegt());
				txtTrailervaegt.setText("" + lastbil.getTrailerVaegt());
				txtTrailerstatus.setText("" + lastbil.getTrailerStatus());
				listBesoeg.setListData(lastbil.getBesoeg().toArray(
						new Besoeg[0]));
			}
		}
	}

	private class ListBesoegListSelectionListener implements
			ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if (!listBesoeg.isSelectionEmpty()) {
				Besoeg besoeg = listBesoeg.getSelectedValue();
				setBesoegTxf(besoeg);
			}
		}
	}

	private class BtnLukActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			resetBesoeg();
			resetLastbil();
			FrameLastbiler.this.setVisible(false);
		}
	}
}