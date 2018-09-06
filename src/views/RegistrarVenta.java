package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import tda.TDASistemaCine;
import bean.ProductoView;
import bean.VentaView;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;

public class RegistrarVenta extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private TDASistemaCine sisCin;
	private JPanel contentPane;
	private JTable tproductos;
	private DefaultTableModel modeloTabla;
	private Vector<Vector<String>> datosTabla;
	private RegistrarVenta autoRef;
	private VentaView venta;
	private Vector<String> columnNames;
	private JTextField txtTotal;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrarVenta frame = new RegistrarVenta();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
	
	public RegistrarVenta()
	{
		iniciarRegistrarVenta();
	}
	
	public RegistrarVenta(TDASistemaCine sc) throws RemoteException
	{
		sisCin = sc;
		autoRef = this;
		venta = sisCin.iniciarVenta();
		columnNames = new Vector<String>();
	    columnNames.addElement("Codigo");
	    columnNames.addElement("Descripcion");
	    columnNames.addElement("Cantidad");
	    columnNames.addElement("Subtotal");
	    datosTabla = new Vector<Vector<String>>();
		iniciarRegistrarVenta();
	}
	
	public void agregarItemVenta(ProductoView prod, int cantidad) throws RemoteException
	{
		sisCin.agregarProducto(prod,cantidad);
		actualizarTabla();
		txtTotal.setText(String.valueOf(sisCin.totalVenta()));
	}
	
	public void actualizarTabla() throws RemoteException
	{
		venta = sisCin.actualizarVistaVenta();
		datosTabla = venta.getItems();
		modeloTabla.setDataVector(datosTabla, columnNames);
	}

	public void iniciarRegistrarVenta() {
		setTitle("Registrar Venta");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 543, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnAgregarProducto = new JButton("Agregar Producto");
		btnAgregarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if(sisCin != null)
				{
					AgregarProducto agPr = new AgregarProducto(sisCin,autoRef);
					agPr.setVisible(true);
				}
			}
		});
		
		JButton btnRegistrarVenta = new JButton("Registrar Venta");
		btnRegistrarVenta.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				float total = 0;
				try {
					total = sisCin.registrarVenta();
				} catch (RemoteException e) 
				{
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, total);
				VentanaInicial vi = new VentanaInicial();
				vi.setVisible(true);
				dispose();
			}
		});
		
		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				dispose();
			}
		});
		
		JLabel lblTotal = new JLabel("Total:");
		
		txtTotal = new JTextField();
		txtTotal.setForeground(Color.BLUE);
		txtTotal.setEditable(false);
		txtTotal.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
							.addGap(12))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnAgregarProducto)
									.addPreferredGap(ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
									.addComponent(btnRegistrarVenta)
									.addGap(18))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblTotal, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(txtTotal, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNewButton)))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtTotal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTotal, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAgregarProducto)
						.addComponent(btnNewButton)
						.addComponent(btnRegistrarVenta)))
		);
		
		tproductos = new JTable();
		scrollPane.setViewportView(tproductos);
		contentPane.setLayout(gl_contentPane);
		tproductos.setModel(new DefaultTableModel(datosTabla, columnNames));
		modeloTabla = (DefaultTableModel) tproductos.getModel();
	}
}
