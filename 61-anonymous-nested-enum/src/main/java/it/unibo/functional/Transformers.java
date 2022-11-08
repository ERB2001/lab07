package it.unibo.functional;

import it.unibo.functional.api.Function;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A special utility class with methods that transform collections using
 * {@link Function}s provided as parameters.
 */
public final class Transformers {

    private Transformers() {
    }

    /**
     * A function that applies a transformation to each element of an
     * {@link Iterable}, obtaining multiple elements,
     * and then builds a "flat" list of these transformed elements.
     * For instance, {@code [1, 2, 3, 4, 5]} could use {@code flattenTransform} to
     * transform only the odd numbers into
     * squares, passing a function that returns an empty {@link List} for even
     * numbers and a List with the square of
     * the input otherwise, obtaining as output {@code [1, 9, 25]}.
     * -->
     * Questa è una funzione che applica una trasformazione a ciascun elemento di un
     * {@link Iterable}, ottenendo più elementi,
     * e quindi costruisce un elenco "piatto" di questi elementi trasformati.
     * Ad esempio, {@code [1, 2, 3, 4, 5]} potrebbe usare {@code flattenTransform}
     * per trasformare
     * solo i numeri dispari in quadrati, passando una funzione che restituisce un
     * {@link List} vuoto per i numeri pari
     * e un elenco con il quadrato dil'input altrimenti, ottenendo come output
     * {@code [1, 9, 25]}.
     *
     * @param base        the elements on which to operate --> gli elementi su cui
     *                    operare
     * @param transformer the {@link Function} to apply to each element. It must
     *                    transform the elements into a
     *                    (possibly empty) collection of other elements.
     *                    --> {@link Function} da applicare a ciascun elemento. Deve
     *                    trasformare gli elementi in una
     *                    (possibilmente vuota) raccolta di altri elementi.
     * @return A "flattened" list of the produced elements --> Un elenco
     *         "appiattito" degli elementi prodotti
     * @param <I> input elements type --> <I> Tipo di elementi di input
     * @param <O> output elements type --> <O> Tipo di elementi di output
     */
    public static <I, O> List<O> flattenTransform(
            final Iterable<? extends I> base,
            final Function<I, ? extends Collection<? extends O>> transformer) {
        final var result = new ArrayList<O>();
        for (final I input : Objects.requireNonNull(base, "The base iterable cannot be null")) {
            result.addAll(transformer.call(input));
        }
        return result;
    }

    /**
     * A function that applies a transformation to each element of an
     * {@link Iterable},
     * returning a list of these transformed elements.
     * For instance, {@code [1, 2, 3, 4, 5]} could use {@code transform} to produce
     * a list of squares by passing
     * a function that computes the square of the input, thus obtaining
     * {@code [1, 4, 9, 16, 25]}.
     * <b>NOTE:</b> this function is a special flattenTransform whose function
     * always return a list with a single
     * element
     * -->
     * Funzione che applica una trasformazione a ogni elemento di un
     * {@link Iterable}
     * restituendo un elenco di questi elementi trasformati.
     * Ad esempio, {@code [1, 2, 3, 4, 5]} potrebbe usare {@code trasforma} per
     * produrre
     * un elenco di quadrati passando
     * una funzione che calcola il quadrato dell'ingresso, ottenendo così
     * {@code [1, 4, 9, 16, 25]}.
     * <b>NOTA:</b> questa funzione è una speciale flattenTransform la cui funzione
     * restituisce sempre una lista con un singolo
     * elemento
     *
     * @param base        the elements on which to operate
     * @param transformer the {@link Function} to apply to each element.
     * @return A transformed list where each input element is replaced with the
     *         produced elements
     *         -->
     *         Un elenco trasformato in cui ogni elemento di input viene rimpiazzato
     *         con gli
     *         elementi prodotti
     * @param <I> input elements type
     * @param <O> output elements type
     */
    // transformer si occupa di trasformare l'Inpuit in Output
    public static <I, O> List<O> transform(final Iterable<I> base, final Function<I, O> transformer) {
        return flattenTransform(base, new Function<I, Collection<? extends O>>() {

            @Override
            public Collection<? extends O> call(I input) {
                // TODO Auto-generated method stub
                return List.of(transformer.call(input));
            }

        });
    }

    /**
     * A function that takes an iterable of collections, and returns a flatten list
     * of the elements of the inner
     * collections.
     * For instance, {@code [[1], [2, 3], [4, 5], []]} could use {@code flatten} to
     * produce a flat list, thus obtaining
     * {@code [1, 2, 3, 4, 5]}.
     * <b>NOTE:</b> this function is a special flattenTransform whose input is an
     * iterable of collections,
     * and whose function simply returns each collection (identity).
     * -->
     * Una funzione che accetta un iterabile di collections e restituisce un elenco
     * appiattito
     * degli elementi delle collezioni
     * innestate.
     * Ad esempio, {@code [[1], [2, 3], [4, 5], []]} potrebbe usare {@code flatten}
     * per
     * produrre una lista piatta, ottenendo così
     * {@code [1, 2, 3, 4, 5]}.
     * <b>NOTA:</b> questa funzione è una speciale flattenTransform il cui input è
     * un
     * iterabilità delle collezioni,
     * e la cui funzione restituisce semplicemente ogni raccolta (identità).
     *
     * @param base the collections on which to operate
     * @return A flattened list with the elements of each collection in the input
     * @param <I> type of the collection elements
     */
    public static <I> List<? extends I> flatten(final Iterable<? extends Collection<? extends I>> base) {
        return flattenTransform(base, Function.identity());
    }

    /**
     * A function that applies a test to each element of an {@link Iterable},
     * returning a list containing only the
     * elements that pass the test.
     * --> <I>Una funzione che applica un test a ciascun elemento di un
     * {@link Iterable},
     * restituendo un elenco contenente solo il
     * elementi che superano il test.
     * 
     * For instance, {@code [1, 2, 3, 4, 5]} could use {@code select} to filter only
     * the odd numbers, thus obtaining
     * {@code [1, 3, 5]}.
     * -->
     * Ad esempio, {@code [1, 2, 3, 4, 5]} potrebbe usare {@code select} per
     * filtrare solo
     * i numeri dispari, ottenendo così
     * {@code [1, 3, 5]}.
     * 
     * <b>NOTE:</b> this function is a special flattenTransform whose function
     * returns a list with a single element if
     * the element passes the test, and an empty list otherwise.
     * -->
     * <b>NOTA:</b> questa funzione è una speciale flattenTransform la cui funzione
     * restituisce un elenco con un singolo elemento se
     * L'elemento supera il test e un elenco vuoto in caso contrario.
     *
     * @param base the elements on which to operate
     * @param test the {@link Function} to use to test whether the elements should
     *             be selected.
     * @return A list containing only the elements that passed the test
     * @param <I> elements type
     */
    public static <I> List<I> select(final Iterable<I> base, final Function<I, Boolean> test) {
        return flattenTransform(base, new Function<I, Collection<? extends I>>() {

            @Override
            public Collection<? extends I> call(I input) {
                // TODO Auto-generated method stub
                if (test.call(input)) {
                    return List.of(input);
                } else {
                    return Collections.emptyList();
                }

            }

        });
    }

    /**
     * A function that applies a test to each element of an {@link Iterable},
     * returning a list containing only the
     * elements that do not pass the test.
     * -->
     * Una funzione che applica un test a ciascun elemento di un {@link Iterable},
     * restituendo un elenco contenente solo gli
     * elementi che non superano il test.
     * 
     * For instance, {@code [1, 2, 3, 4, 5]} could use {@code select} to reject all
     * the even numbers, thus obtaining
     * {@code [1, 3, 5]}.
     * -->
     * Ad esempio, {@code [1, 2, 3, 4, 5]} potrebbe usare {@code select} per
     * rifiutare tutti
     * i numeri pari, ottenendo così
     * {@code [1, 3, 5]}.
     * 
     * <b>NOTE:</b> this function is a special select whose test function return
     * value is negated.
     * -->
     * <b>NOTA:</b> questa funzione è una selezione speciale la cui funzione di test
     * restituisce
     * che il valore è negato.
     *
     * @param base the elements on which to operate
     * @param test the {@link Function} to use to test whether the elements should
     *             be discarded.
     * @return A list containing only the elements that passed the test
     * @param <I> elements type
     */
    public static <I> List<I> reject(final Iterable<I> base, final Function<I, Boolean> test) {
        return flattenTransform(base, new Function<I, Collection<? extends I>>() {

            @Override
            public Collection<? extends I> call(I input) {
                if (test.call(input)) {
                    return Collections.emptyList();
                } else {
                    return List.of(input);
                }
                // TODO Auto-generated method stub

            }
        });
    }
}
