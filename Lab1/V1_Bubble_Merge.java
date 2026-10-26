package Algos.Lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

public class V1_Bubble_Merge {

    // 1. Сортировка слиянием (Merge Sort) - O(N log N)
    public static void mergeSort(int[] a) {
        mergeSort(a, 0, a.length, new int[a.length]);
    }

    private static void mergeSort(int[] a, int left, int right, int[] buffer) {
        if (right - left <= 1) return;
        int middle = left + (right - left) / 2;
        mergeSort(a, left, middle, buffer);
        mergeSort(a, middle, right, buffer);
        int i = left, j = middle, k = left;
        while (i < middle && j < right) {
            if (a[i] <= a[j]) buffer[k++] = a[i++];
            else buffer[k++] = a[j++];
        }
        while (i < middle) buffer[k++] = a[i++];
        while (j < right) buffer[k++] = a[j++];
        System.arraycopy(buffer, left, a, left, right - left);
    }

    // 2. Сортировка пузырьком (Bubble Sort) - O(N^2)
    public static void bubbleSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    @ParameterizedTest
    @MethodSource("testData")
    void testSort(int N) {
        int[] arr = new int[N];
        Random random = new Random(1000);

        // for (int i = 0; i < N; i++) arr[i] = random.nextInt(100); // Средний случай
        // for (int i = 0; i < N; i++) arr[i] = i;                  // Лучший случай
        for (int i = 0; i < N; i++) arr[i] = N - i + 1;             // Худший случай

        long start = System.nanoTime();

        mergeSort(arr);
        // bubbleSort(arr);

        long diff = System.nanoTime() - start;
        System.out.println(diff);

        for (int i = 0; i < arr.length - 1; i++) {
            Assertions.assertTrue(arr[i] <= arr[i + 1]);
        }
    }

    static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(10),
                Arguments.of(100),
                Arguments.of(200),
                Arguments.of(400),
                Arguments.of(800),
                Arguments.of(1600),
                Arguments.of(3200),
                Arguments.of(6400),
                Arguments.of(12800),
                Arguments.of(25600),
                Arguments.of(51200),
                Arguments.of(102400)
        );
    }

    public static void main(String[] args) {
        V1_Bubble_Merge test = new V1_Bubble_Merge();
        testData().forEach(arg -> {
            int n = (int) arg.get()[0];
            test.testSort(n);
        });
    }
}
