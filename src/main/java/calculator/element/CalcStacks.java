package calculator.element;

import java.math.BigDecimal;
import java.util.Stack;
import java.util.function.Predicate;

/**
 * 演算に使用する演算子と項のスタック
 * 
 * @author lambig
 *
 */
public class CalcStacks {
	private Stack<Token> marks = new Stack<>();
	private Operands operands = new Operands();

	/**
	 * 現在の演算子スタックが空または最上位演算子が左括弧で、かつ項バッファが空=項の先頭文字位置であるかを返す
	 * 
	 * @return 現在の位置が項の先頭に当たる場合true
	 */
	public boolean isHeadOfOperand() {
		return (this.marks.empty() || this.marks.peek().isLeftParen()) && this.operands.hasEmptyBuffer();
	}

	/**
	 * 項バッファにトークンを積む
	 * 
	 * @param token トークン
	 * @return
	 */
	public void buf(Token token) {
		this.operands.buf(token);
	}

	/**
	 * 記号スタックが空でないかを返す
	 * 
	 * @return 記号スタックのサイズが1以上のときtrue
	 */
	public boolean hasMarks() {
		return !this.marks.empty();
	}

	/**
	 * 記号スタックに1トークン追加する
	 * 
	 * @param token トークン
	 */
	public void pushMark(Token token) {
		this.pushBuffer(token);
		this.marks.push(token);
	}

	/**
	 * 記号をpopする
	 * 
	 * @return 記号トークン
	 */
	public Token popMark() {
		if (!this.hasMarks()) {
			return Token.NULL;
		}
		return this.marks.pop();
	}

	/**
	 * 記号をpeekする
	 * 
	 * @return 記号トークン
	 */
	public Token peekMark() {
		return this.marks.peek();
	}

	/**
	 * 現在の項バッファを項にプッシュする
	 * 
	 */
	public void pushBuffer(Token next) {
		if (this.operands.isMinusMark()) {
			this.buf(Token.ONE);
			this.pushMark(Token.TIMES);
		} else if (!this.operands.hasEmptyBuffer() && next.isLeftParen()) {
			this.pushMark(Token.TIMES);
		}
		this.operands.pushCurrent();
	}

	/**
	 * 項をpopする
	 * 
	 * @return 項
	 */
	public BigDecimal popOperand() {
		return this.operands.pop();
	}

	/**
	 * 演算子と項をすべて計算する
	 * 
	 * @return 計算結果
	 */
	public BigDecimal resolve() {
		this.resolveWhile(self -> true);
		return this.popOperand();
	}

	/**
	 * 演算子と項を継続条件を満たす間すべて計算する
	 * 
	 * @param continueCondition 継続条件
	 * @return 最後に処理した演算子
	 */
	public void resolveWhile(Predicate<CalcStacks> continueCondition) {
		this.operands.pushCurrent();
		while (this.hasMarks() && continueCondition.test(this)) {
			Token token = this.popMark();
			if (!token.isOperator()) {
				throw new IllegalArgumentException("閉じていない括弧があります");
			}
			this.operands.calc(token.toOperator());
		}
	}
}
