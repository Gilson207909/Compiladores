// Generated from br\u005Cufscar\dc\compiladores\expr\parser3\Expressoes.g4 by ANTLR 4.7.2
package br.ufscar.dc.compiladores.expr.parser3;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExpressoesParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExpressoesVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ExpressoesParser#programa}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrograma(ExpressoesParser.ProgramaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressoesParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(ExpressoesParser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressoesParser#termo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermo(ExpressoesParser.TermoContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressoesParser#listaDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListaDecl(ExpressoesParser.ListaDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressoesParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl(ExpressoesParser.DeclContext ctx);
}