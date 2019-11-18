package calculator.element;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * 項スタッククラス スタックに積む前のバッファも持つ
 * 
 * @author lambig
 *
 */
public class Operands {
	private Stack<BigDecimal> operands = new Stack<>();
	private StringBuilder sb = new StringBuilder();

	/**
	 * バッファにトークンの文字を積む
	 * 
	 * @param c
	 */
	public void buf(Token c) {
		sb.append(c.value());
	}

	/**
	 * バッファを項としてスタックに積む
	 */
	public void pushCurrent() {
		if (this.sb.length() > 0) {
			this.operands.push(new BigDecimal(this.sb.toString()));
		}
		this.sb.setLength(0);
	}

	/**
	 * バッファが空であるかを返す
	 * 
	 * @return 空のときtrue
	 */
	public boolean hasEmptyBuffer() {
		return this.sb.length() == 0;
	}

	/**
	 * バッファの中身が「負数符号」のみであるかを判定する
	 * 
	 * @return バッファの中身が"-"のときtrue
	 */
	public boolean isMinusMark() {
		return this.sb.length() == 1 && this.sb.indexOf("-") == 0;
	}

	/**
	 * スタックの上位2項で引数の演算を行う
	 * 
	 * @param op 演算子
	 */
	public void calc(Operator op) {
		if (operands.size() < 2) {
			throw new IllegalArgumentException("被演算子が足りません");
		}
		this.operands.push(op.apply(this.pop(), this.pop()));
	}

	/**
	 * スタック最上位の項をpopする
	 * 
	 * @return 項
	 */
	public BigDecimal pop() {
		return this.operands.pop();
	}
}
