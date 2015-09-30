package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import model.DateUtil;
import model.Laesning;
import model.Rampe;
import model.RampeStatus;
import service.Service;
import service.ServiceDAO;

public class FrameRamper extends JFrame {

	private JPanel contentPane;
	private JLabel lblRamper, lblAktuelStart, lblTrailerStartvgt, lblDelordrenummer, lblLaesninger, lblRampenummer, lblTransportmateriale, lblRampestatus, lblForventetStart, lblForventetSlut;
	private JScrollPane scrollPane, scrollPane_1;
	private JList<Rampe> listRamper;
	private JList<Laesning> listLaesninger;
	private JTextField txfRampenummer, txfTransportmateriale, txfRampestatus, txfForventetStart, txfForventetSlut, txfAktuelStart, txfTrailerStartvaegt, txfDelordrenummer;
	private JButton btnLuk, btnToggleRampestop;
	private Font stdFont = new Font("Tahoma", Font.PLAIN, 11);
	private Controller controller = new Controller();
	private String rampeStopText = "Rampestop", rampeAktiverText = "Aktiver rampe";
	private FrameLaesseMedarbejder frameLaesseMedarbejder;
	private ServiceDAO serviceDAO = ServiceDAO.getInstance();
	
	public FrameRamper(FrameLaesseMedarbejder fl) {
		frameLaesseMedarbejder = fl;
		setTitle("Ramper");
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(653, 100, 485, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblRamper = new JLabel("Ramper:");
		lblRamper.setFont(stdFont);
		lblRamper.setBounds(10, 11, 46, 14);
		contentPane.add(lblRamper);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 37, 230, 149);
		contentPane.add(scrollPane);
		
		listRamper = new JList<Rampe>();
		listRamper.addListSelectionListener(new ListRamperListSelectionListener());
		listRamper.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listRamper);
		
		lblLaesninger = new JLabel("Læsninger:");
		lblLaesninger.setFont(stdFont);
		lblLaesninger.setBounds(10, 197, 65, 14);
		contentPane.add(lblLaesninger);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 222, 230, 149);
		contentPane.add(scrollPane_1);
		listLaesninger = new JList<Laesning>();
		listLaesninger.addListSelectionListener(new ListLaesningerListSelectionListener());
		scrollPane_1.setViewportView(listLaesninger);
		listLaesninger.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		txfRampenummer = new JTextField();
		txfRampenummer.setEditable(false);
		txfRampenummer.setColumns(10);
		txfRampenummer.setBounds(353, 37, 115, 20);
		contentPane.add(txfRampenummer);
		
		lblRampenummer = new JLabel("Rampenummer:");
		lblRampenummer.setFont(stdFont);
		lblRampenummer.setBounds(250, 39, 122, 14);
		contentPane.add(lblRampenummer);
		
		lblTransportmateriale = new JLabel("Transportmateriale:");
		lblTransportmateriale.setFont(stdFont);
		lblTransportmateriale.setBounds(250, 65, 122, 14);
		contentPane.add(lblTransportmateriale);
		
		txfTransportmateriale = new JTextField();
		txfTransportmateriale.setEditable(false);
		txfTransportmateriale.setColumns(10);
		txfTransportmateriale.setBounds(353, 62, 115, 20);
		contentPane.add(txfTransportmateriale);
		
		lblRampestatus = new JLabel("Rampestatus:");
		lblRampestatus.setFont(stdFont);
		lblRampestatus.setBounds(250, 90, 122, 14);
		contentPane.add(lblRampestatus);
		
		txfRampestatus = new JTextField();
		txfRampestatus.setEditable(false);
		txfRampestatus.setColumns(10);
		txfRampestatus.setBounds(353, 87, 115, 20);
		contentPane.add(txfRampestatus);
		
		txfForventetStart = new JTextField();
		txfForventetStart.setEditable(false);
		txfForventetStart.setColumns(10);
		txfForventetStart.setBounds(353, 222, 115, 20);
		contentPane.add(txfForventetStart);
		
		lblForventetStart = new JLabel("Forventet start:");
		lblForventetStart.setFont(stdFont);
		lblForventetStart.setBounds(250, 224, 122, 14);
		contentPane.add(lblForventetStart);
		
		lblForventetSlut = new JLabel("Forventet slut:");
		lblForventetSlut.setFont(stdFont);
		lblForventetSlut.setBounds(250, 250, 122, 14);
		contentPane.add(lblForventetSlut);
		
		txfForventetSlut = new JTextField();
		txfForventetSlut.setEditable(false);
		txfForventetSlut.setColumns(10);
		txfForventetSlut.setBounds(353, 247, 115, 20);
		contentPane.add(txfForventetSlut);
		
		lblAktuelStart = new JLabel("Aktuel start:");
		lblAktuelStart.setFont(stdFont);
		lblAktuelStart.setBounds(250, 275, 122, 14);
		contentPane.add(lblAktuelStart);
		
		txfAktuelStart = new JTextField();
		txfAktuelStart.setEditable(false);
		txfAktuelStart.setColumns(10);
		txfAktuelStart.setBounds(353, 272, 115, 20);
		contentPane.add(txfAktuelStart);
		
		txfTrailerStartvaegt = new JTextField();
		txfTrailerStartvaegt.setEditable(false);
		txfTrailerStartvaegt.setColumns(10);
		txfTrailerStartvaegt.setBounds(353, 297, 115, 20);
		contentPane.add(txfTrailerStartvaegt);
		
		lblTrailerStartvgt = new JLabel("Trailer startvægt:");
		lblTrailerStartvgt.setFont(stdFont);
		lblTrailerStartvgt.setBounds(250, 300, 122, 14);
		contentPane.add(lblTrailerStartvgt);
		
		lblDelordrenummer = new JLabel("Delordrenummer:");
		lblDelordrenummer.setFont(stdFont);
		lblDelordrenummer.setBounds(250, 325, 122, 14);
		contentPane.add(lblDelordrenummer);
		
		txfDelordrenummer = new JTextField();
		txfDelordrenummer.setEditable(false);
		txfDelordrenummer.setColumns(10);
		txfDelordrenummer.setBounds(353, 322, 115, 20);
		contentPane.add(txfDelordrenummer);
		
		btnLuk = new JButton("Luk");
		btnLuk.setFont(stdFont);
		btnLuk.setFocusable(false);
		btnLuk.setBounds(10, 388, 89, 23);
		btnLuk.addActionListener(controller);
		contentPane.add(btnLuk);
		
		btnToggleRampestop = new JButton("Rampestop");
		btnToggleRampestop.setFont(stdFont);
		btnToggleRampestop.setFocusable(false);
		btnToggleRampestop.setEnabled(false);
		btnToggleRampestop.setBounds(353, 163, 115, 23);
		btnToggleRampestop.addActionListener(controller);
		contentPane.add(btnToggleRampestop);
		
		listRamper.setListData(serviceDAO.getAllRamper().toArray(new Rampe[0]));
	}
	
	private void resetViewLaesning() {
		txfForventetStart.setText("");
		txfForventetSlut.setText("");
		txfAktuelStart.setText("");
		txfTrailerStartvaegt.setText("");
		txfDelordrenummer.setText("");
	}
	
	private void resetView() {
		Rampe r = listRamper.getSelectedValue();
		listRamper.clearSelection();
		listRamper.setSelectedValue(r, false);
	}
	
	private class ListRamperListSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if(!listRamper.getValueIsAdjusting()) {
				if(!listRamper.isSelectionEmpty()) {
					Rampe rampe = listRamper.getSelectedValue();
					listLaesninger.setListData(rampe.getLaesninger().toArray(new Laesning[0]));
					txfRampenummer.setText("" + rampe.getRampenummer());
					txfTransportmateriale.setText("" + rampe.getTransportMateriale());
					RampeStatus rs = rampe.getRampeStatus();
					txfRampestatus.setText("" + rs);
					if(rs == RampeStatus.STOPPET)
						btnToggleRampestop.setText(rampeAktiverText);
					else 
						btnToggleRampestop.setText(rampeStopText);
					btnToggleRampestop.setEnabled(true);
				}
			}
			resetViewLaesning();
		}
	}
	
	private class ListLaesningerListSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if(!listLaesninger.getValueIsAdjusting()) {
				if(!listLaesninger.isSelectionEmpty()) {
					Laesning laesning = listLaesninger.getSelectedValue();
					txfForventetStart.setText(DateUtil.formatTimestamp(laesning.getForventetStart()));
					txfForventetSlut.setText(DateUtil.formatTimestamp(laesning.beregnForventetSlut()));
					txfAktuelStart.setText("" + laesning.getTransportMateriale());
					double startVaegt = laesning.getAktuelStartVaegt();
					txfTrailerStartvaegt.setText("" + (startVaegt == 0.0 ? "Endnu ikke vejet" : startVaegt));
					txfDelordrenummer.setText("" + laesning.getDelordre().getDelordrenummer());
				}
			}
		}
	}
	
	private class Controller implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			JButton knap = (JButton) ae.getSource();
			if(knap.equals(btnToggleRampestop)) {
				Rampe rampe = listRamper.getSelectedValue();
				Service service = Service.getInstance();
				if(btnToggleRampestop.getText().equals(rampeStopText)) {
					service.setRampestop(rampe, true);
				
				} else {
					service.setRampestop(rampe, false);
				}
				frameLaesseMedarbejder.resetView();
				resetView();
			} else if (knap.equals(btnLuk)){
				FrameRamper.this.setVisible(false);
			}
		}
	}
}