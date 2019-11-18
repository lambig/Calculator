package calculator.element;

import java.util.function.Function;

import org.apache.commons.lang3.CharUtils;

/**
 * 文字列の各文字をトークンとして扱うためのクラス
 * 
 * @author lambig
 *
 */
public class Token {
	/** 文字⇒トークン変換 */
	public static final Function<Character, Token> TO_TOKEN = Token::new;

	/** 空トークン */
	public static final Token NULL = new Token();
	public static final Token ONE = new Token('1');
	public static final Token TIMES = new Token('*');

	private char value;

	private Token() {
	}

	private Token(char value) {
		this.value = value;
	}

	/**
	 * トークンが数値文字であるかを返却
	 * 
	 * @return 数値のときtrue
	 */
	public boolean isNumeric() {
		return CharUtils.isAsciiNumeric(this.value);
	}

	/**
	 * トークンが演算子であるかを返却
	 * 
	 * @return 演算子のときtrue
	 */
	public boolean isOperator() {
		return Operator.isOperator(this.value);
	}

	/**
	 * トークンを演算子として返却
	 * 
	 * @return 演算子インスタンス
	 */
	public Operator toOperator() {
		return Operator.of(this.value);
	}

	/**
	 * トークンが左括弧であるかを返却
	 * 
	 * @return 左括弧のときtrue
	 */
	public boolean isLeftParen() {
		return '(' == this.value;
	}

	/**
	 * トークンが右括弧であるかを返却
	 * 
	 * @return 右括弧のときtrue
	 */
	public boolean isRightParen() {
		return ')' == this.value;
	}

	public char value() {
		return this.value;
	}

}
