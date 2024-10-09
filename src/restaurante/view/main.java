package restaurante.view;

import javax.swing.JOptionPane;

import restaurante.controller.CrudItensPedidos;
import restaurante.controller.CrudPedidos;
import restaurante.controller.CrudPratos;
import restaurante.controller.Usuarios;


public class main {
	public static void main(String[] args) {
		try {
			CrudPratos crudpratos = new CrudPratos();
			CrudPedidos crudpedidos = new CrudPedidos();
			CrudItensPedidos cruditenspedidos = new CrudItensPedidos();
			
			//boolean loginSucesso = Usuarios.loginUsuarios();
			
			//if(loginSucesso) {
				//Exibe o menu Principal
				while(true) {
					//Menu Principal
					String[] options = {"Pratos", "Pedidos", "Itens Pedidos", "Usuários", "Sair"};
					
					int choice = JOptionPane.showOptionDialog(null, "Selecione uma opção",
					"Sistema de Restaurante", JOptionPane.DEFAULT_OPTION, 
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
					
					if(choice == 0) {
						//Menu de Pratos
						String[] oppratos = {"Cadastrar", "Todos", 
								"Por Categoria", "Editar", "Deletar", "Voltar"};
						
						int pratos = JOptionPane.showOptionDialog(null, "Selecione uma opção",
								"Sistema de Restaurante", JOptionPane.DEFAULT_OPTION, 
								JOptionPane.INFORMATION_MESSAGE, null, oppratos, oppratos[0]);
			
						if(pratos == 0) {
							crudpratos.cadastrarPrato();
						}else if(pratos == 1) {
							crudpratos.listarPratos();
						}else if(pratos == 2) {
							crudpratos.ListarPorCategoriaPratos();
						}else if(pratos == 3) {
							crudpratos.UpdatePratos();
						}else if(pratos == 4) {
							crudpratos.DeletePratos();
						}else if(pratos == 5) {
							JOptionPane.showMessageDialog(null, "Voltar");
						}else {
							JOptionPane.showMessageDialog(null, "Opção Inválida."); 
						}
					}else if(choice == 1) {
						
						//Menu de Pratos
						String[] oppedidos = {"Cadastrar", "Todos", 
								"Aberto", "Finalizar Pedido", "Deletar", "Voltar"};
						
						int pedidos = JOptionPane.showOptionDialog(null, "Selecione uma opção",
								"Sistema de Restaurante", JOptionPane.DEFAULT_OPTION, 
								JOptionPane.INFORMATION_MESSAGE, null, oppedidos, oppedidos[0]);
			
						if(pedidos == 0) {
							crudpedidos.cadastrarPedidos();
						}else if(pedidos == 1) {
							crudpedidos.listartodosPedidos();
						}else if(pedidos == 2) {
							crudpedidos.listarabertosPedidos();
						}else if(pedidos == 3) {
							crudpedidos.updatePedidos();
						}else if(pedidos == 4) {
							crudpedidos.deletePedidos();
						}
					}else if(choice == 2) {
						//System.out.print("Itens Pedido");
						
						String[] opitenspedidos = {"Cadastrar", "Atualizar", "Finalizar", "Voltar"};
						
						int itenspedidos = JOptionPane.showOptionDialog(null, "Selecione uma opção",
								"Sistema de Restaurante", JOptionPane.DEFAULT_OPTION, 
								JOptionPane.INFORMATION_MESSAGE, null, opitenspedidos, opitenspedidos[0]);
						
						if(itenspedidos == 0) {
							cruditenspedidos.CadastrarItensPedidos();
							
							
						}
						
						
						
						
						
						
						
						
						
						
						
						
						
						
					}else if(choice == 3) {
						System.out.print("Usuários");
						
						
		
					}else if(choice == 4) {
						//JOptionPane.showMessageDialog(null, "Saindo do Sistema.");
						System.exit(0);
						break;
					}else {
						JOptionPane.showMessageDialog(null, "Opção Inválida.");
					}
					
				}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
