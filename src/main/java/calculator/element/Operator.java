package calculator.element;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * 演算子定義
 * 
 * @author lambig
 *
 */
public enum Operator {
	/** 加算 */
	Plus('+', 2, (a, b) -> b.add(a)),
	/** 減算 */
	Minus('-', 2, (a, b) -> b.subtract(a)),
	/** 乗算 */
	Times('*', 3, (a, b) -> b.multiply(a)),
	/** 除算 */
	Slash('/', 3, (a, b) -> {
		if (a.doubleValue() == 0d) {
			throw new IllegalArgumentException("ゼロ除算が発生します");
		}
		return b.divide(a, 10, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
	});

	private char token;
	private int priority;
	private Operation operation;

	public static Operator of(char literal) {
		return Stream.of(values()).filter(e -> e.token == literal).findFirst().orElse(null);
	}

	private Operator(char token, int priority, Operation operation) {
		this.token = token;
		this.priority = priority;
		this.operation = operation;
	}

	public boolean priorTo(Operator op) {
		return this.priority > op.priority;
	}

	public BigDecimal apply(BigDecimal arg1, BigDecimal arg2) {
		return this.operation.apply(arg1, arg2);
	}

	public static boolean isOperator(char c) {
		return of(c) != null;
	}

	private static interface Operation extends BiFunction<BigDecimal, BigDecimal, BigDecimal> {

	}

}