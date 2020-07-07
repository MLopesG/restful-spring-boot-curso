	package com.api.v1.model;

	import javax.persistence.*;
	import javax.validation.constraints.*;

@Entity
@Table(name="veiculos")
public class VeiculoModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
	private Integer id;

	@NotEmpty(message = "Preencha o campo corretamente!")
	@Size(min = 5, max = 255)
    @Column(name="nome", length = 255)
	private String nome;

	@NotEmpty(message = "Preencha o campo corretamente!")
	@Size(min = 10, max = 255)
    @Column(name="categoria", length = 255)
	private String categoria;

	@NotEmpty(message = "Preencha o campo corretamente!")
	@Size(min = 5, max = 255)
    @Column(name="marca", length = 255)
	private String marca;

	@Min(1990)
    @Column(name="ano")
	private Integer ano;

	@NotEmpty(message = "Preencha o campo corretamente!")
	@Size(min = 10, max = 255)
    @Column(name="imagem", length = 255)
	private String imagem;

	@NotEmpty(message = "Preencha o campo corretamente!")
	@Size(min = 10, max = 255)
    @Column(name="nome_comercial", length = 255)
	private String nome_comercial;

	@NotEmpty(message = "Preencha o campo corretamente!")
	@Size(min = 2, max = 255)
    @Column(name="eixo", length = 255)
	private String eixo;

	@NotEmpty(message = "Preencha o campo corretamente!")
	@Size(min = 2, max = 255)
    @Column(name="geracao", length = 255)
	private String geracao;

	@NotEmpty(message = "Preencha o campo corretamente!")
	@Size(min = 10, max = 255)
    @Column(name="descricao", length = 255)
    private String descricao;

    @Column(name="visualizar")
    private Integer visualizar;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	public String getNome_comercial() {
		return nome_comercial;
	}
	public void setNome_comercial(String nome_comercial) {
		this.nome_comercial = nome_comercial;
	}
	public String getEixo() {
		return eixo;
	}
	public void setEixo(String eixo) {
		this.eixo = eixo;
	}
	public String getGeracao() {
		return geracao;
	}
	public void setGeracao(String geracao) {
		this.geracao = geracao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getVisualizar() {
		return visualizar;
	}
	public void setVisualizar(Integer visualizar) {
		this.visualizar = visualizar;
	}
}