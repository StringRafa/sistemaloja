package com.fedex.lojasistema.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fedex.lojasistema.domain.Categoria;
import com.fedex.lojasistema.domain.Cliente;
import com.fedex.lojasistema.domain.ItemPedido;
import com.fedex.lojasistema.domain.Pagamento;
import com.fedex.lojasistema.domain.PagamentoComBoleto;
import com.fedex.lojasistema.domain.PagamentoComCartao;
import com.fedex.lojasistema.domain.Pedido;
import com.fedex.lojasistema.domain.Produto;
import com.fedex.lojasistema.enums.EstadoPedido;
import com.fedex.lojasistema.enums.PedidoStatus;
import com.fedex.lojasistema.enums.Perfil;
import com.fedex.lojasistema.enums.TipoCliente;
import com.fedex.lojasistema.repositories.CategoriaRepository;
import com.fedex.lojasistema.repositories.ClienteRepository;
import com.fedex.lojasistema.repositories.ItemPedidoRepository;
import com.fedex.lojasistema.repositories.PagamentoRepository;
import com.fedex.lojasistema.repositories.PedidoRepository;
import com.fedex.lojasistema.repositories.ProdutoRepository;



@Service
public class DBService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public void instantiateTestDatabase() throws ParseException {

		Categoria cat1 = new Categoria(null, "Eletrônicos");
		Categoria cat2 = new Categoria(null, "Books");
		Categoria cat3 = new Categoria(null, "Computadores");

		Produto p1 = new Produto(null, "The Lord of the Rings", "In a fantastic and unique land, a hobbit receives a magic ring as a gift from his uncle.", 90.5);
		Produto p2 = new Produto(null, "Smart TV", "A smart TV is a digital television that is, essentially, an Internet-connected, storage-aware computer specialized for entertainment", 2190.0);
		Produto p3 = new Produto(null, "McBook Pro", "Apple MacBook Pro is a macOS laptop with a 13.30-inch display that has a resolution of 2560x1600 pixels.", 1250.0);
		Produto p4 = new Produto(null, "Pc Gamer", "A high-end Windows PC that is suited for gaming.", 1200.0);
		Produto p5 = new Produto(null, "Rails For Dummies", "Quickly create Web sites with this poweful tool Use this free and easy programming language for e-commerce sites and blogs If you need to build ...", 100.99);

		cat1.getProdutos().addAll(Arrays.asList(p2));
		cat2.getProdutos().addAll(Arrays.asList(p1, p5));
		cat3.getProdutos().addAll(Arrays.asList(p2, p3, p4));


		p1.getCategorias().addAll(Arrays.asList(cat2));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat3));
		p3.getCategorias().addAll(Arrays.asList(cat3));
		p4.getCategorias().addAll(Arrays.asList(cat3));
		p5.getCategorias().addAll(Arrays.asList(cat2));
		

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

		

		Cliente cli1 = new Cliente(null, "Maria Brown", "maria@gmail.com", "24689207089",
				TipoCliente.PESSOAFISICA, "123");
		cli1.addPerfil(Perfil.ADMIN);
		cli1.getTelefones().addAll(Arrays.asList("01127363323", "01193838393"));
		
		Cliente cli2 = new Cliente(null, "Alex Green", "alex@gmail.com", "41462651003",
				TipoCliente.PESSOAFISICA, "123");
		cli2.getTelefones().addAll(Arrays.asList("01129256894", "011989632514"));
		
		clienteRepository.saveAll(Arrays.asList(cli1, cli2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, PedidoStatus.PAID);
		Pedido ped2 = new Pedido(null, sdf.parse("11/10/2017 19:35"), cli2, PedidoStatus.WAINTING_PAYMENT);
		Pedido ped3 = new Pedido(null, sdf.parse("15/10/2017 18:55"), cli1, PedidoStatus.WAINTING_PAYMENT);
		Pedido ped4 = new Pedido(null, sdf.parse("20/10/2017 13:15"), cli1, PedidoStatus.PAID);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPedido.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPedido.QUITADO, ped4, null, sdf.parse("21/10/2017 10:15"));
		ped1.setPagamento(pagto1);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		cli2.getPedidos().addAll(Arrays.asList(ped3));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2, ped3));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 2, 90.50);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 1, 1250.00);
		ItemPedido ip3 = new ItemPedido(ped2, p3, 0.00, 2, 1250.00);
		ItemPedido ip4 = new ItemPedido(ped3, p5, 0.00, 2, 100.99);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		ped3.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip4));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3, ip4));
	}
}
