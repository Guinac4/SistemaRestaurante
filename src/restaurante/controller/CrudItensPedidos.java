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
import restaurante.model.ItensPedidos;
import restaurante.model.Pedidos;
import restaurante.model.Pratos;

public class CrudItensPedidos {

	public static void CadastrarItensPedidos() throws SQLException {

		ItensPedidos itensdepedidos = new ItensPedidos();

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
		
		String StringQuantidade = JOptionPane.showInputDialog("Quantidade total de Pratos:");
		String StringPratos_id = JOptionPane.showInputDialog("Entre com o ID do Prato:");
		String StringTotal_item  = JOptionPane.showInputDialog("Preço Total do Pedido: ");
		String StringPedidos_id = JOptionPane.showInputDialog("ID do Pedido: ");
				
		
		if(StringQuantidade.isEmpty() || StringPratos_id.isEmpty() || 
				StringTotal_item.isEmpty() || StringPedidos_id.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Não foi possivel inserir o Pedido (Campo vazio).");
		}else {
			
			itensdepedidos.setQuantidade(Integer.parseInt(StringQuantidade));
			itensdepedidos.setPratos_id(Integer.parseInt(StringPratos_id));
			itensdepedidos.setTotal_item(Double.parseDouble(StringTotal_item));
			itensdepedidos.setPedidos_id(Integer.parseInt(StringPedidos_id));
			
			
			String sqlitem_pedido = "insert into itens_pedido (quantidade , total_item , pratos_id, pedidos_id) values (?,?,?,?);";
			
			//INSERT INTO itens_pedido (quantidade , total_item , pratos_id, pedidos_id) VALUES (2,40,2,1)
			
			PreparedStatement cmditem_pedido = conexao.prepareStatement(sqlitem_pedido);
			cmditem_pedido.setInt(1, itensdepedidos.getQuantidade());
			cmditem_pedido.setInt(2, itensdepedidos.getPratos_id());
			cmditem_pedido.setDouble(3, itensdepedidos.getTotal_item());
			cmditem_pedido.setInt(4, itensdepedidos.getPedidos_id());
			cmditem_pedido.execute();
			
			JOptionPane.showMessageDialog(null, "Itens de Pedido inserido com sucesso.");
			cmditem_pedido.close();
		}
		
		
		if (pedidoSelecionado != null) {
			// Obter o ID do pedido associado à opção selecionada
			int pedidoIdSelecionado = mapaOpcoes.get(pedidoSelecionado);

			if (JOptionPane.showConfirmDialog(null, "Cadastrar Item Pedido", "Cancelar",
					JOptionPane.YES_NO_OPTION)

					== JOptionPane.YES_OPTION) {

				int IdSelecionado = pedidoIdSelecionado;
				
			} else {
				
			}
		}
	}
}

//quantidade total_item pratos_id

