package ext.test4j.hamcrest.collection;

import static ext.test4j.hamcrest.core.DescribedAs.describedAs;
import static ext.test4j.hamcrest.core.IsEqual.equalTo;

import ext.test4j.hamcrest.Factory;
import ext.test4j.hamcrest.FeatureMatcher;
import ext.test4j.hamcrest.Matcher;

/**
 * Matches if array size satisfies a nested matcher.
 */
public class IsArrayWithSize<E> extends FeatureMatcher<E[], Integer> {
    public IsArrayWithSize(Matcher<? super Integer> sizeMatcher) {
        super(sizeMatcher, "an array with size","array size");
    }

    @Override
    protected Integer featureValueOf(E[] actual) {
      return actual.length;
    };

    /**
     * Does array size satisfy a given matcher?
     */
    @Factory
    public static <E> Matcher<E[]> arrayWithSize(Matcher<? super Integer> sizeMatcher) {
        return new IsArrayWithSize<E>(sizeMatcher);
    }

    /**
     * This is a shortcut to the frequently used arrayWithSize(equalTo(x)).
     *
     * For example,  assertThat(arrayWithSize(equal_to(x)))
     *          vs.  assertThat(arrayWithSize(x))
     */
    @Factory
    public static <E> Matcher<E[]> arrayWithSize(int size) {
        return arrayWithSize(equalTo(size));
    }

    /**
     * Matches an empty array.
     */
    @Factory
    public static <E> Matcher<E[]> emptyArray() {
        Matcher<E[]> isEmpty = arrayWithSize(0);
        return describedAs("an empty array", isEmpty);
    }
}
