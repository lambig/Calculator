package calculator.stacker;

import calculator.element.Token;
import calculator.logic.Cases;

/**
 * Stackerのアクセス窓口
 * 
 * @author lambig
 *
 */
public class Stackers {

	/** トークンとStackerの対応表 */
	private static final Cases<Token, Stacker> STACKER_MAP = new Cases<Token, Stacker>()
			.when(Token::isNumeric)
			.then(Stacker.AS_NUMERIC)

			.when(Token::isOperator)
			.then(Stacker.AS_OPERATOR)

			.when(Token::isLeftParen)
			.then(Stacker.AS_LEFT_PAREN)

			.when(Token::isRightParen)
			.then(Stacker.RESOLVE_PARTIALLY)

			.orElse(Stacker.UNSUPPORTED_TOKEN);

	/** 「トークンに応じたStackerを適用する」Stacker */
	public static final Stacker FOR_TOKEN_TYPES = (tno, t) -> STACKER_MAP.caseOf(t).accept(tno, t);
}
