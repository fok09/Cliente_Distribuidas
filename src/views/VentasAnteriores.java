package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import tda.TDASistemaCine;
import bean.VentaView;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class VentasAnteriores extends JFrame 
{
	private JPanel contentPane;
	private JTable table;
	private TDASistemaCine sisCin;
	private DefaultTableModel modeloTabla;
	private Vector<Vector<String>> datosTabla;
	private Vector<String> columnNames;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentasAnteriores frame = new VentasAnteriores();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public VentasAnteriores()
	{
		iniciarVentasAnteriores();
	}
	
	public VentasAnteriores(TDASistemaCine sc) throws RemoteException
	{
		this.sisCin = sc;
		columnNames = new Vector<String>();
	    columnNames.addElement("Numero");
	    columnNames.addElement("Fecha");
	    columnNames.addElement("Total");
	    datosTabla = sisCin.getVentasAnteriores();
		iniciarVentasAnteriores();
	}

	private void iniciarVentasAnteriores() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 557, 347);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				dispose();
			}
		});
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(datosTabla, columnNames));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane)
						.addComponent(btnVolver, Alignment.TRAILING)))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
					.addComponent(btnVolver))
		);
		contentPane.setLayout(gl_contentPane);
		modeloTabla = (DefaultTableModel) table.getModel();
	    modeloTabla.setDataVector(datosTabla, columnNames);
	}
}
