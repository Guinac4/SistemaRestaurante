package restaurante.model;

public class Pedidos {
	
	private Integer id;
	private Integer mesa;
	private Double total;
	private Integer status;
	
	public Integer getMesa() {
		return mesa;
	}
	public void setMesa(Integer mesa) {
		this.mesa = mesa;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
}
