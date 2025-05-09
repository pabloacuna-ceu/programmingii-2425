package edu.ceu.programming.classes.datastructures.heap;

import java.util.ArrayList;

public class MinHeap {
    private ArrayList<Integer> heap;

    public MinHeap() {
        heap = new ArrayList<>();
    }

    private int parent(int i) { return (i - 1) / 2; }
    private int leftChild(int i) { return 2 * i + 1; }
    private int rightChild(int i) { return 2 * i + 2; }

    public void add(int value) {
        heap.add(value);
        int current = heap.size() - 1;

        // Bubble up
        while (current > 0 && heap.get(current) < heap.get(parent(current))) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public int remove() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }

        int root = heap.get(0);
        int last = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }

        return root;
    }

    private void heapifyDown(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < heap.size() && heap.get(left) < heap.get(smallest)) {
            smallest = left;
        }

        if (right < heap.size() && heap.get(right) < heap.get(smallest)) {
            smallest = right;
        }

        if (smallest != i) {
            swap(i, smallest);
            heapifyDown(smallest);
        }
    }

    public boolean removeValue(int value) {
        int index = heap.indexOf(value);
        if (index == -1) return false; // Value not found

        int lastIndex = heap.size() - 1;
        if (index != lastIndex) {
            heap.set(index, heap.get(lastIndex)); // Replace with last element
        }
        heap.remove(lastIndex); // Remove last element

        // Restore heap property
        if (index < heap.size()) {
            heapifyDown(index);
            bubbleUp(index); // In case the new value is smaller
        }

        return true;
    }

    private void bubbleUp(int i) {
        while (i > 0 && heap.get(i) < heap.get(parent(i))) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private void swap(int i, int j) {
        int tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
    }

    public void printHeap() {
        System.out.println("Heap: " + heap);
    }

    public static void main(String[] args) {
        MinHeap heap = new MinHeap();


        heap.add(5);
        heap.printHeap();
        heap.add(7);
        heap.printHeap();
        heap.add(8);
        heap.printHeap();
        heap.add(1);
        heap.printHeap();
        heap.add(4);
        heap.printHeap();
        heap.add(3);
        heap.printHeap();

        // Insert the following: 1 3 6 5 9 8 10 13 15 17 11 14 12 18 20

        System.out.println("Removed: " + heap.remove());
        heap.printHeap();
        System.out.println("Removed: " + heap.remove());
        heap.printHeap();
        System.out.println("Removed: " + heap.remove());
        heap.printHeap();
    }
}
