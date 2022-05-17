package com.fedex.lojasistema.enums;

public enum EstadoPedido {

	PENDENTE(1, "Pagamento pendente"),
	QUITADO(2, "Pagamento quitado"), 
	CANCELADO(5, "Pagamento cancelado");
	
	private int cod;
	private String descricao;

	private EstadoPedido(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static EstadoPedido toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (EstadoPedido x : EstadoPedido.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
