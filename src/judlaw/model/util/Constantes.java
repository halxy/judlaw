/* 
 * Universidade Federal de Campina Grande
 * Centro de Engenharia Eletrica e Informatica
 * Programa de Pos-Graduacao em Ciencia da Computacao
 * Sistema JudLaw
 * Mestrando: Halley Freitas	
 */
package judlaw.model.util;

/**
 * Classe Constantes. Contem as contantes utilizadas pelo sistema
 * @author Halley Freitas
 *
 */
public class Constantes {

	// Estrutura dos dispositivos legais
	public static final String PARTE = "Parte";
	public static final String LIVRO = "Livro";
	public static final String TITULO = "Titulo";
	public static final String CAPITULO = "Capitulo";
	public static final String SECAO = "Secao";
	public static final String SUBSECAO = "Subsecao";
	public static final String ARTIGO = "Artigo";
	public static final String PARAGRAFO = "Paragrafo";
	public static final String INCISO = "Inciso";
	public static final String ALINEA = "Alinea";
	public static final String ITEM = "Item";
	
	// Tipos de Alteracao (Referencia)
	public static final String INCLUSAO = "Inclusão";
	public static final String MODIFICACAO = "Modificação";
	public static final String REVOGACAO_PARCIAL = "Ab-rogação";
	public static final String REVOGACAO_TOTAL = "Derrogação";
	
	// Caracteristicas de Alteracao (Referencia)
	public static final String MAIS_RESTRITIVA = "Mais-restritiva";
	public static final String MENOS_RESTRITIVA = "Menos-restritiva";
	public static final String NEUTRA = "Neutra";
	
	// Delimitadores
	public static final String DELIMITADOR_DATA = "/";
	public static final String DELIMITADOR_VIGENCIA = "-";
	
	// Tipos de arquivos Download
	public static final String DOWNLOAD_ARQUIVO = "Arquivo";
	public static final String DOWNLOAD_HTML = "Html";
}
