package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import tda.TDASistemaCine;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class VentanaInicial extends JFrame {

	private JPanel contentPane;
	private TDASistemaCine sisCin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaInicial frame = new VentanaInicial();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public VentanaInicial()
	{
		if(getStub())
		{
		//sisCin = SistemaCine.getInstance();
		iniciarVentanaInicial();
		}
	}

    public boolean getStub() {
    	
    	try {
			sisCin = (TDASistemaCine)Naming.lookup ("//192.168.157.101/SistemaCine");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
    }
	
	
	/**
	 * Create the frame.
	 */
	public void iniciarVentanaInicial() {
		setTitle("Inicio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 385, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnRegistrarVenta = new JButton("Registrar Venta");
		btnRegistrarVenta.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if(sisCin != null)
				{
					/*
					RegistrarVenta reg;
					try {
						reg = new RegistrarVenta(sisCin);
						reg.setVisible(true);
					} catch (RemoteException e) 
					{
						e.printStackTrace();
					}	
		//			dispose();
		 */

					try {
						System.out.println(sisCin.getEntradas().elementAt(0).getNombre());
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Sistema sin instanciar");
			}
		});
		
		JButton btnVentasAnteriores = new JButton("Ventas Anteriores");
		btnVentasAnteriores.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				//VentasAnteriores va = new VentasAnteriores(sisCin);
				//va.setVisible(true);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnVentasAnteriores, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
						.addComponent(btnRegistrarVenta, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(27)
					.addComponent(btnRegistrarVenta, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnVentasAnteriores, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(105, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
