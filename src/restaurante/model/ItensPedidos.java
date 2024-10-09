package restaurante.model;

public class ItensPedidos {
	
	private Integer id;
	private Integer quantidade;
	private Double total_item;
	private Integer pratos_id;
	private Integer pedidos_id;
	
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Double getTotal_item() {
		return total_item;
	}
	public void setTotal_item(Double total_item) {
		this.total_item = total_item;
	}
	public Integer getPratos_id() {
		return pratos_id;
	}
	public void setPratos_id(Integer pratos_id) {
		this.pratos_id = pratos_id;
	}
	public Integer getPedidos_id() {
		return pedidos_id;
	}
	public void setPedidos_id(Integer pedidos_id) {
		this.pedidos_id = pedidos_id;
	}
	public Integer getId() {
		return id;
	}

		
}
