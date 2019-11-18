package calculator;

import java.util.Scanner;
import java.util.function.BiConsumer;

import org.apache.commons.lang3.StringUtils;

import calculator.element.CalcStacks;
import calculator.element.Token;
import calculator.stacker.Stackers;

/**
 * コンソールから受け付けた入力値を数式とみなして計算し、
 * 標準出力に結果を表示する
 * @author lambig
 *
 */
public class Calculator {

	/** 実際には呼び出されないBiConsumer実装(呼び出されないことを知っているのはCalculatorクラスのみ) */
	private static final BiConsumer<CalcStacks, CalcStacks> UNSUPPORTED_OPERATION = (t1, t2) -> {
		throw new UnsupportedOperationException();
	};
	private static final String INPUT_ENCOURAGER = "数式を入力してください(何も入力せずEnter押下で終了): ";

	public static void main(String[] args) {
		System.out.print(INPUT_ENCOURAGER);
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		while (StringUtils.isNotEmpty(input)) {
			try {
				System.out.println(calc(input));
			} catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
				System.out.println();
			}
			System.out.print(INPUT_ENCOURAGER);
			input = scanner.nextLine();
		}
		scanner.close();
	}

	/**
	 * 入力値を数式として解釈し、計算結果を文字列で返す
	 * テストのためパッケージに公開
	 * 
	 * @param literal 文字列
	 * @return 計算結果文字列
	 */
	static String calc(String literal) {
		return StringUtils.remove(literal, ' ')
				.codePoints()
				.mapToObj(c -> (char) c)
				.map(Token.TO_TOKEN)
				.collect(
						CalcStacks::new,
						Stackers.FOR_TOKEN_TYPES,
						UNSUPPORTED_OPERATION)
				.resolve()
				.toPlainString();
	}

}
