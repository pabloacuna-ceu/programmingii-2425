package edu.ceu.programming.classes.datastructures.heap;

import java.util.PriorityQueue;

public class MinHeapPQ {
    private PriorityQueue<Integer> pq;

    public MinHeapPQ() {
        pq = new PriorityQueue<>();
    }

    // Add a value to the heap
    public void add(int value) {
        pq.add(value);
    }

    // Remove the smallest element (root)
    public int remove() {
        if (pq.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        return pq.poll(); // Removes and returns the smallest
    }

    // Remove a specific value (first occurrence only)
    public boolean removeValue(int value) {
        return pq.remove(value); // Removes first matching element if exists
    }

    // Traverse the heap (note: no guaranteed order!)
    public void printHeap() {
        System.out.print("Heap contents (no guaranteed order): ");
        for (int val : pq) {
            System.out.print(val + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MinHeapPQ heap = new MinHeapPQ();

        // Add elements
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

        // Print the heap
        System.out.println("After adding elements:");
        heap.printHeap();

        // Remove the smallest element
        System.out.println("Removed: " + heap.remove());
        heap.printHeap();

        System.out.println("Removed: " + heap.remove());
        heap.printHeap();

        System.out.println("Removed: " + heap.remove());
        heap.printHeap();


        // Remove a specific value
        /*boolean removed = heap.removeValue(15);
        System.out.println("Removed 15? " + removed);
        heap.printHeap();

        // Try to remove a non-existent value
        removed = heap.removeValue(99);
        System.out.println("Removed 99? " + removed);
        heap.printHeap();*/
    }
}
