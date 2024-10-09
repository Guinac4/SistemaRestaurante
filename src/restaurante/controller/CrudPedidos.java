package restaurante.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import restaurante.jdbc.ConnectionFactory;
import restaurante.model.Pedidos;

public class CrudPedidos {
	//Cadastrar Pedidos
			public static void cadastrarPedidos()throws SQLException{
				Pedidos pedidos = new Pedidos();
				
				Connection conexao = ConnectionFactory.createConnection();
				
				String[] MesaPedidos = {
						"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
						"11", "12", "13", "14", "15", "16", "17", "18", "19","20",
						"21", "22", "23", "24", "25", "26", "27", "28", "29","30"
				}; 
				
				String mesaSelecionado = (String) JOptionPane.showInputDialog(null, 
						"Selecione uma mesa:", "Escolha uma Mesa",
						JOptionPane.QUESTION_MESSAGE, null, MesaPedidos, MesaPedidos[0]);
				
				pedidos.setMesa(Integer.parseInt(mesaSelecionado));
				pedidos.setTotal(0.00);
				pedidos.setStatus(0);
				
				//criar o SQL
				String sql = "INSERT INTO pedidos(mesa, total, status) VALUES (?, ?, ?);";
				
				//criar o comando para passar para o sql
				PreparedStatement cmd = conexao.prepareStatement(sql);
				
				cmd.setInt(1, pedidos.getMesa());
				cmd.setDouble(2, pedidos.getTotal());
				cmd.setInt(3, pedidos.getStatus());
				
				//executar o comando
				cmd.execute();
				JOptionPane.showMessageDialog(null, "Pedido inserido com sucesso.");
				cmd.close();
			}
			
			public static void listartodosPedidos()throws SQLException{
				Pedidos pedidos = new Pedidos();
				
				Connection conexao = ConnectionFactory.createConnection();
				
				String sql = "select * from pedidos;";
				
				PreparedStatement cmd = conexao.prepareStatement(sql);
				
				ResultSet resultado = cmd.executeQuery();
				
				String printpedidos = "--- Pedidos encontrados ---\n\n";
				while(resultado.next()) {
					String printstatus; 
					if(resultado.getInt("status") == 0) {
						printstatus = "Aberto";
					}else {
						printstatus = "Fechada";
					}
					printpedidos += 
					  "ID: " + resultado.getInt("id")
					+ "   Mesa: " + resultado.getInt("mesa")
					+ "   Total: " + resultado.getDouble("total")
					+ "\n"
					+ "Status: " + printstatus
					+ "   Data: " + resultado.getDate("data_hora")
					+ "\n\n";
				}
				JOptionPane.showMessageDialog(null, printpedidos);
				conexao.close();
			}
			
			public static void listarabertosPedidos()throws SQLException{
				Pedidos pedidos = new Pedidos();
				
				Connection conexao = ConnectionFactory.createConnection();
				
				String sql = "select * from pedidos where status = 0;";
				
				PreparedStatement cmd = conexao.prepareStatement(sql);
				
				ResultSet resultado = cmd.executeQuery();
				
				String printpedidos = "--- Pedidos encontrados ---\n\n";
				while(resultado.next()) {
					String printstatus; 
					if(resultado.getInt("status") == 0) {
						printstatus = "Aberto";
					}else {
						printstatus = "Fechada";
					}
					printpedidos += 
					  "ID: " + resultado.getInt("id")
					+ "   Mesa: " + resultado.getInt("mesa")
					+ "   Total: " + resultado.getDouble("total")
					+ "\n"
					+ "Status: " + printstatus
					+ "   Data: " + resultado.getDate("data_hora")
					+ "\n\n";
				}
				JOptionPane.showMessageDialog(null, printpedidos);
				conexao.close();
			}
			
			public static void updatePedidos() throws SQLException {
			    Pedidos pedidos = new Pedidos();

			    Connection conexao = ConnectionFactory.createConnection();

			    String sql = "select id, mesa from pedidos where status = 0 order by id asc;";

			    PreparedStatement cmd = conexao.prepareStatement(sql);

			    ResultSet resultado = cmd.executeQuery();

			    // Mapa de pedidos: chave = id do pedido, valor = mesa
			    Map<Integer, Integer> mapaPedidos = new HashMap<>();
			    ArrayList<String> listaPedidosFormatada = new ArrayList<>();
			    Map<String, Integer> mapaOpcoes = new HashMap<>(); 
			    // Mapa para associar a string formatada ao id do pedido

			    while (resultado.next()) {
			        int pedidoId = resultado.getInt("id");
			        int mesa = resultado.getInt("mesa");

			        // Criar string formatada com ID e Mesa
			        String opcao = "Pedido: " + pedidoId + " - Mesa: " + mesa;

			        // Adicionar a string formatada à lista
			        listaPedidosFormatada.add(opcao);

			        // Mapear a string formatada ao ID do pedido
			        mapaOpcoes.put(opcao, pedidoId);
			    }

			    // Converter a lista de opções formatadas para um array de strings
			    String[] pedidosArray = listaPedidosFormatada.toArray(new String[0]);

			    // Mostrar o diálogo com as opções formatadas (ID e Mesa)
			    String pedidoSelecionado = (String) JOptionPane.showInputDialog(
			            null,
			            "Selecione o número do pedido: ",
			            "Seleção de Pedido",
			            JOptionPane.QUESTION_MESSAGE,
			            null,
			            pedidosArray,
			            pedidosArray[0]
			    );

			    if (pedidoSelecionado != null) {
			        // Obter o ID do pedido associado à opção selecionada
			        int pedidoIdSelecionado = mapaOpcoes.get(pedidoSelecionado);

			        String itensPedidoSql = "SELECT pratos.id, itens_pedido.id, itens_pedido.quantidade, "
			        		+ "pratos.nome, pratos.preco ,itens_pedido.total_item FROM itens_pedido "
			        		+ "LEFT JOIN pratos ON itens_pedido.pratos_id = pratos.id WHERE itens_pedido.pedidos_id = ?;;"; 
			        
			        PreparedStatement itensPedidocmd = conexao.prepareStatement(itensPedidoSql);
			        itensPedidocmd.setInt(1, pedidoIdSelecionado);
			        
			        ResultSet itensPedidoresultado = itensPedidocmd.executeQuery();
					
					String printitensPedido = "--- Itens encontrados ---\n\n";
					Double total = 0.00;
					while(itensPedidoresultado.next()) {
						
						Integer quantidade = itensPedidoresultado.getInt("itens_pedido.quantidade");
						Double preco = itensPedidoresultado.getDouble("pratos.preco");
						Double subtotal = quantidade * preco;
						
						
						printitensPedido += 
						  "Qtd: " + itensPedidoresultado.getInt("itens_pedido.id") +
						  "   Sub-Total" + itensPedidoresultado.getString("pratos.nome") +
						  "	  Qtd.: " + itensPedidoresultado.getInt("itens_pedido.quantidade") +
						  "   Preco: " + itensPedidoresultado.getDouble("pratos.preco") + 
						  "   Total: " + subtotal
						  
						  
						  +"\n";
						total = total + subtotal;
						
					}
					
					Double taxa = total*0.10;
					Double val_total = total + taxa;
					
					String ftotal = String.format("%.2f", total);
					String ftaxa = String.format("%.2f", taxa);
					String fvaltotal = String.format("%.2f", val_total);
					
					
					printitensPedido += "\nValor Total: R$ " + ftotal + 
										"\nTaxa de 10%: R$ " + ftaxa +
										"\nValor a Pagar: " + fvaltotal;
							;
					
					JOptionPane.showMessageDialog(null, printitensPedido);
					//conexao.close();
					
					
					
					if(printitensPedido != null) {
						

						if(
								JOptionPane.showConfirmDialog(
										null, 
										"Finalizar Pedido",
										"Deseja Finalizar Pedido?",
										JOptionPane.YES_NO_OPTION) 
										
									== JOptionPane.YES_OPTION) {
							
							
							
							//Total
							Integer status = 1;
							
							String sqlUpdate = "UPDATE pedidos SET total =?, status =? where id =?;";
							
							PreparedStatement cmdUpdate = conexao.prepareStatement(sqlUpdate);
							
							
							cmdUpdate.setDouble(1, total);
							cmdUpdate.setInt(2, status);
							cmdUpdate.setInt(3, pedidoIdSelecionado);
							
							cmdUpdate.execute();
							
							JOptionPane.showConfirmDialog(null, "Pedido Finalizado com Sucesso");
							
							cmd.close();
							
							
							
							
							//System.out.print("Clicou !");
						} else {
							
						}
													
					}   
					
					conexao.close();
			    }			
			}
			
			public static void deletePedidos() throws SQLException {
				Pedidos pedidos = new Pedidos();

				Connection conexao = ConnectionFactory.createConnection();

				String sql = "select id, mesa from pedidos where status = 0 order by id asc;";

				PreparedStatement cmd = conexao.prepareStatement(sql);

				ResultSet resultado = cmd.executeQuery();

				// Mapa de pedidos: chave = id do pedido, valor = mesa
				Map<Integer, Integer> mapaPedidos = new HashMap<>();
				ArrayList<String> listaPedidosFormatada = new ArrayList<>();
				Map<String, Integer> mapaOpcoes = new HashMap<>();
				// Mapa para associar a string formatada ao id do pedido

				String pedidoSelecionado = null;

				while (resultado.next()) {
					int pedidoId = resultado.getInt("id");
					int mesa = resultado.getInt("mesa");

					// Criar string formatada com ID e Mesa
					String opcao = "Pedido: " + pedidoId + " - Mesa: " + mesa;

					// Adicionar a string formatada à lista
					listaPedidosFormatada.add(opcao);

					// Mapear a string formatada ao ID do pedido
					mapaOpcoes.put(opcao, pedidoId);

				}

				// Converter a lista de opções formatadas para um array de strings
				String[] pedidosArray = listaPedidosFormatada.toArray(new String[0]);

				// Mostrar o diálogo com as opções formatadas (ID e Mesa)
				pedidoSelecionado = (String) JOptionPane.showInputDialog(null, "Selecione o número do pedido: ",
						"Seleção de Pedido", JOptionPane.QUESTION_MESSAGE, null, pedidosArray, pedidosArray[0]);

				if (pedidoSelecionado != null) {
					// Obter o ID do pedido associado à opção selecionada
					int pedidoIdSelecionado = mapaOpcoes.get(pedidoSelecionado);

					if (JOptionPane.showConfirmDialog(null, "Finalizar Pedido", "Deseja finalizar este pedido?",
							JOptionPane.YES_NO_OPTION)

							== JOptionPane.YES_OPTION) {

						int excluirpedidoIdSelecionado = pedidoIdSelecionado;

						String itensPedidoSql = "SELECT pratos.id, itens_pedido.id, itens_pedido.quantidade, "
								+ "pratos.nome, pratos.preco ,itens_pedido.total_item FROM itens_pedido "
								+ "LEFT JOIN pratos ON itens_pedido.pratos_id = pratos.id WHERE "
								+ "itens_pedido.pedidos_id = ?;";

						PreparedStatement itensPedidocmd = conexao.prepareStatement(itensPedidoSql);
						itensPedidocmd.setInt(1, excluirpedidoIdSelecionado);

						ResultSet itensPedidoresultado = itensPedidocmd.executeQuery();

						while (itensPedidoresultado.next()) {
							Integer idItemPedido = itensPedidoresultado.getInt("itens_pedido.id");
							String SQLexcluir = "DELETE FROM itens_pedido WHERE id = ?;";

							PreparedStatement cmddeleteItensPedido = conexao.prepareStatement(SQLexcluir);
							cmddeleteItensPedido.setInt(1, idItemPedido);

							cmddeleteItensPedido.execute();

							JOptionPane.showMessageDialog(null, "Itens Excluidos.");
						}

						int excluirpedidoId = pedidoIdSelecionado;

						String SQLexcluirPedido = "DELETE FROM pedidos WHERE id = ?;";

						PreparedStatement cmddeletePedido = conexao.prepareStatement(SQLexcluirPedido);
						cmddeletePedido.setInt(1, excluirpedidoId);

						if (excluirpedidoId > 0) {
							cmddeletePedido.execute();
							JOptionPane.showMessageDialog(null, "Pedido Exluido.");
						} else {
							JOptionPane.showMessageDialog(null, "Não Existe Pedido.");
						}

					} else {

					}
				}

			}

	  }		    


//INSERT INTO itens_pedido(id, quantidade, total_item, pratos_id, pedidos_id) VALUES (1,2,3,4,5)
		    
