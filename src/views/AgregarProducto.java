package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import app.SistemaCine;
import bean.ProductoView;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class AgregarProducto extends JFrame {

	private JPanel contentPane;
	private JTextField txtCantidad;
	private JTextField txtPrecio;
	private SistemaCine sisCin;
	private RegistrarVenta ref;
	private ProductoView prod;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgregarProducto frame = new AgregarProducto();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public AgregarProducto() 
	{
		iniciarAgregarProducto();
	}
	
	public AgregarProducto(SistemaCine sc, RegistrarVenta r) 
	{
		sisCin = sc;
		ref = r;
		iniciarAgregarProducto();
	}
	
	

	/**
	 * Create the frame.
	 */
	public void iniciarAgregarProducto() {
		setTitle("Agregar Producto");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblTipoProducto = new JLabel("Tipo Producto:");
		
		JComboBox cbTipoProducto = new JComboBox();
		JComboBox cbProducto = new JComboBox();


		cbTipoProducto.addItem("Entradas");
		cbTipoProducto.addItem("Adicionales");
		cbTipoProducto.addItem("Combos Promocionales");
		
		cbTipoProducto.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Vector<ProductoView> vec = new Vector<ProductoView>();
				if(cbTipoProducto.getSelectedItem().toString().equals("Entradas"))
				{
					cbProducto.removeAllItems();
					txtPrecio.setText("");
					vec = sisCin.getEntradas();
					for(int i = 0; i < vec.size(); i++)
						cbProducto.addItem(vec.elementAt(i).getNombre());
				}
				if(cbTipoProducto.getSelectedItem().toString().equals("Adicionales"))
				{
					cbProducto.removeAllItems();
					txtPrecio.setText("");
					vec = sisCin.getAdicionales();
					for(int i = 0; i < vec.size(); i++)
						cbProducto.addItem(vec.elementAt(i).getNombre());	
				}
				if(cbTipoProducto.getSelectedItem().toString().equals("Combos Promocionales"))
				{
					cbProducto.removeAllItems();
					txtPrecio.setText("");
					vec = sisCin.getCombos();
					for(int i = 0; i < vec.size(); i++)
						cbProducto.addItem(vec.elementAt(i).getNombre());
				}
			}
		});

		cbProducto.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if(cbProducto.getSelectedItem() != null && cbTipoProducto.getSelectedItem() != null)
				{
					String seleccion = cbProducto.getSelectedItem().toString();
					//System.out.println(seleccion.substring(0,seleccion.indexOf(" ")));
					int cod = Integer.parseInt(seleccion.substring(0,seleccion.indexOf(" ")));
					prod = sisCin.getProducto(cod);
					txtPrecio.setText(prod.getPrecio().toString());
					
				}		
				//else
				//	System.out.println("no hay seleccion");
			}
		});
		
		
		JLabel lblProducto = new JLabel("Producto:");
		
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)
			{
				dispose();
			}
		});
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				int cant = 0;
				try
					{
			        	cant = Integer.parseInt(txtCantidad.getText());
						ref.agregarItemVenta(prod, cant);
						dispose();
			        }
			        catch(Exception f)
			        {
			        	JOptionPane.showMessageDialog(null,"Ingrese cantidad");
			        }

	//			float total = sisCin.registrarVenta();
	//			JOptionPane.showMessageDialog(null,total);
			}
		});
		
		JLabel lblCantidad = new JLabel("Cantidad:");
		
		txtCantidad = new JTextField();
		txtCantidad.setColumns(10);
		
		JLabel lblPrecio = new JLabel("Precio:");
		
		txtPrecio = new JTextField();
		txtPrecio.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtPrecio.setEditable(false);
		txtPrecio.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(12)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblPrecio)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblCantidad))
							.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addComponent(lblProducto)
									.addComponent(lblTipoProducto)))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(txtPrecio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
								.addComponent(txtCantidad, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
								.addComponent(btnAgregar)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnCancelar))
							.addComponent(cbTipoProducto, 0, 314, Short.MAX_VALUE)
							.addComponent(cbProducto, 0, 314, Short.MAX_VALUE))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTipoProducto)
						.addComponent(cbTipoProducto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblProducto)
						.addComponent(cbProducto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPrecio)
						.addComponent(txtPrecio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnCancelar)
							.addComponent(btnAgregar))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCantidad)
								.addComponent(txtCantidad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
