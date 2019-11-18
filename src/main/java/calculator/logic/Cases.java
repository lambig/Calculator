package calculator.logic;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * キーの述語が最初に真となるエントリの値を返すマップ エントリの設定順は保持される
 * 
 * @author lambig
 *
 * @param <C> 述語の引数
 * @param <R> 返却する値
 */
public class Cases<C, R> {
	private Map<Predicate<C>, R> entries = new LinkedHashMap<>();
	private R defaultValue = null;

	/**
	 * 返却条件と返却値を対応させたエントリ追加処理を遅延させるためのカリーイング用クラス
	 */
	public abstract class WhenCurry {
		/**
		 * このエントリで返却すべき値を与える
		 * 
		 * @param value 返却値
		 * @return 述語と返却値の設定済みマップインスタンス
		 */
		public abstract Cases<C, R> then(R value);
	}

	/**
	 * このエントリの値が返却される条件を与える
	 * 
	 * @param predicate 述語
	 * @return 返却する値を受け取るオブジェクト
	 */
	public WhenCurry when(Predicate<C> predicate) {
		return new WhenCurry() {
			@Override
			public Cases<C, R> then(R value) {
				entries.put(predicate, value);
				return Cases.this;
			}
		};
	}

	/**
	 * デフォルト値を設定する
	 * 
	 * @param value デフォルト値
	 * @return デフォルト値設定済みインスタンス
	 */
	public Cases<C, R> orElse(R value) {
		this.defaultValue = value;
		return this;
	}

	/**
	 * キーから該当するケースの値を取得する
	 * 
	 * @param key キー
	 * @return キーに対して真となる述語があればそのエントリの値 なければデフォルト値
	 */
	public R caseOf(C key) {
		for (Entry<Predicate<C>, R> entry : this.entries.entrySet()) {
			if (entry.getKey().test(key)) {
				return entry.getValue();
			}
		}
		return defaultValue;
	}

	/**
	 * キーから該当する全ケースのキーをリストで取得する
	 * 
	 * @param key
	 * @return
	 */
	public List<R> casesOf(C key) {
		return this.entries.entrySet()
				.stream()
				.filter(e -> e.getKey().test(key)).map(e -> e.getValue())
				.collect(Collectors.toList());
	}
}
