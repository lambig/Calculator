package calculator.stacker;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import calculator.element.CalcStacks;
import calculator.element.Operator;
import calculator.element.Token;

/**
 * 計算用スタックにトークンを積んでいくためのインタフェースとその実装
 * 
 * @author lambig
 *
 */
interface Stacker extends BiConsumer<CalcStacks, Token> {
	/** 項の文字列としてトークンを項バッファに積む */
	static final Stacker AS_NUMERIC = (calcStacks, token) -> calcStacks.buf(token);

	/** 負数符号として項バッファに積むか演算子としてトークンスタックに積む(より優先度の高い演算子がスタックにあれば、先に計算してしまう) */
	static final Stacker AS_OPERATOR = (calcStacks, token) -> {
		if (calcStacks.isHeadOfOperand() && token.toOperator() == Operator.Minus) {
			calcStacks.buf(token);
			return;
		}
		calcStacks.resolveWhile(havingPriorOperatorThan(token));
		calcStacks.pushMark(token);
	};
	/** 左括弧としてトークンスタックに積む */
	static final Stacker AS_LEFT_PAREN = (calcStacks, token) -> calcStacks.pushMark(token);

	/** 右括弧として、左括弧がトークンスタックから出てくるまで計算を実施する 出てこなければ入力不正 */
	static final Stacker RESOLVE_PARTIALLY = (calcStacks, token) -> {
		calcStacks.resolveWhile(it -> !it.peekMark().isLeftParen());

		if (!calcStacks.popMark().isLeftParen()) {// 左括弧を捨てている
			throw new IllegalArgumentException("開き括弧に対して閉じ括弧が多すぎます");
		}
	};

	/** 想定外のトークンを検出したときの処理 */
	static final Stacker UNSUPPORTED_TOKEN = (tna, t) -> {
		throw new IllegalArgumentException("計算できない文字\"" + t.value() + "\"が含まれています");
	};

	/**
	 * CalcStacksの述語 「引数の演算子トークンより優先度の高い演算子がスタックにある」を返す
	 * 
	 * @param current 演算子トークン
	 * @return 述語
	 */
	static Predicate<CalcStacks> havingPriorOperatorThan(Token current) {
		return cs -> cs.peekMark().isOperator() && cs.peekMark().toOperator().priorTo(current.toOperator());
	}
}
