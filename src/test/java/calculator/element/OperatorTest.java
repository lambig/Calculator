package calculator.element;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class OperatorTest {
	public static class PlusTest {
		@Test
		public void test() {
			String actual = Operator.Plus.apply(new BigDecimal("2"), new BigDecimal("1")).toString();
			assertThat(actual, is("3"));
		}
	}

	public static class MinusTest {
		@Test
		public void test() {
			String actual = Operator.Minus.apply(new BigDecimal("2"), new BigDecimal("1")).toString();
			assertThat(actual, is("-1"));
		}
	}

	public static class TimesTest {
		@Test
		public void test() {
			String actual = Operator.Times.apply(new BigDecimal("2"), new BigDecimal("1")).toString();
			assertThat(actual, is("2"));
		}
	}

	public static class SlashTest {
		@Test
		public void test() {
			String actual = Operator.Slash.apply(new BigDecimal("2"), new BigDecimal("1")).toString();
			assertThat(actual, is("0.5"));
		}
	}

}
