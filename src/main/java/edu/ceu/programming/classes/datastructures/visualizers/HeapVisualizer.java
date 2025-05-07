package edu.ceu.programming.classes.datastructures.visualizers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class HeapVisualizer extends JFrame {
    private ArrayList<Integer> heap;
    private JPanel treePanel;
    private JTextArea arrayTextArea;
    private JTextField inputField;
    private JButton insertButton;
    private JButton removeButton;
    private JButton removeRootButton;
    private JButton buildHeapButton;
    private JButton stepButton;
    private JButton nextStepButton;
    private JTextArea logArea;
    
    private boolean stepMode = false;
    private ArrayList<String> steps;
    private int currentStep = 0;
    private boolean operationInProgress = false;
    private String currentOperation = "";
    
    public HeapVisualizer() {
        heap = new ArrayList<>();
        
        // Set up the main frame
        setTitle("Heap Data Structure Visualizer (Max-Heap)");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create components
        treePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTree(g, getWidth(), getHeight());
            }
        };
        treePanel.setBackground(Color.WHITE);
        
        arrayTextArea = new JTextArea();
        arrayTextArea.setEditable(false);
        arrayTextArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        
        JPanel controlPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        inputField = new JTextField();
        insertButton = new JButton("Insert");
        removeButton = new JButton("Remove Value");
        removeRootButton = new JButton("Remove Root");
        buildHeapButton = new JButton("Build Heap from Array");
        stepButton = new JButton("Step Mode: OFF");
        nextStepButton = new JButton("Next Step");
        nextStepButton.setEnabled(false);
        
        controlPanel.add(inputField);
        controlPanel.add(insertButton);
        controlPanel.add(removeButton);
        controlPanel.add(removeRootButton);
        controlPanel.add(buildHeapButton);
        controlPanel.add(stepButton);
        controlPanel.add(nextStepButton);
        
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        // Add components to frame
        add(controlPanel, BorderLayout.NORTH);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(new JScrollPane(treePanel));
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JLabel("Array Representation:"), BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(arrayTextArea), BorderLayout.CENTER);
        rightPanel.add(new JLabel("Operations Log:"), BorderLayout.SOUTH);
        rightPanel.add(new JScrollPane(logArea), BorderLayout.SOUTH);
        
        splitPane.setRightComponent(rightPanel);
        add(splitPane, BorderLayout.CENTER);
        
        // Add event listeners
        insertButton.addActionListener(e -> {
            if (operationInProgress) {
                log("Finish current operation first");
                return;
            }
            try {
                int value = Integer.parseInt(inputField.getText());
                insert(value);
                inputField.setText("");
                updateDisplay();
                log("=== Inserted: " + value + " ===");
            } catch (NumberFormatException ex) {
                log("Error: Please enter a valid integer");
            }
        });
        
        removeButton.addActionListener(e -> {
            if (operationInProgress) {
                log("Finish current operation first");
                return;
            }
            try {
                int value = Integer.parseInt(inputField.getText());
                int index = heap.indexOf(value);
                if (index != -1) {
                    remove(value);
                    inputField.setText("");
                    updateDisplay();
                    log("=== Removed: " + value + " ===");
                } else {
                    log("Value not found in heap: " + value);
                }
            } catch (NumberFormatException ex) {
                log("Error: Please enter a valid integer");
            }
        });
        
        removeRootButton.addActionListener(e -> {
            if (operationInProgress) {
                log("Finish current operation first");
                return;
            }
            if (!heap.isEmpty()) {
                removeRoot();
            } else {
                log("Heap is empty");
            }
        });
        
        buildHeapButton.addActionListener(e -> {
            if (operationInProgress) {
                log("Finish current operation first");
                return;
            }
            String input = JOptionPane.showInputDialog("Enter numbers separated by spaces:");
            if (input != null && !input.trim().isEmpty()) {
                try {
                    String[] parts = input.trim().split("\\s+");
                    ArrayList<Integer> numbers = new ArrayList<>();
                    for (String part : parts) {
                        numbers.add(Integer.parseInt(part));
                    }
                    buildHeap(numbers);
                    updateDisplay();
                    log("=== Built heap from array: " + input + " ===");
                } catch (NumberFormatException ex) {
                    log("Invalid input. Please enter numbers separated by spaces.");
                }
            }
        });
        
        stepButton.addActionListener(e -> {
            stepMode = !stepMode;
            stepButton.setText(stepMode ? "Step Mode: ON" : "Step Mode: OFF");
            nextStepButton.setEnabled(stepMode);
            if (stepMode) {
                log("=== Step mode activated ===");
                log("Operations will be broken into steps. Use Next Step button.");
            } else {
                log("=== Step mode deactivated ===");
            }
        });
        
        nextStepButton.addActionListener(e -> {
            if (steps != null && currentStep < steps.size()) {
                logArea.append(steps.get(currentStep) + "\n");
                currentStep++;
                treePanel.repaint();
                
                if (currentStep == steps.size()) {
                    operationInProgress = false;
                    log("=== " + currentOperation + " COMPLETED ===");
                }
            }
        });
    }
    
    private void insert(int value) {
        heap.add(value);
        if (stepMode) {
            prepareStepMode("Insert " + value);
            siftUpSteps(heap.size() - 1);
        } else {
            siftUp(heap.size() - 1);
        }
    }
    
    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index) > heap.get(parentIndex)) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }
    
    private void siftUpSteps(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            addStep("Comparing " + heap.get(index) + " (index " + index + 
                   ") with parent " + heap.get(parentIndex) + " (index " + parentIndex + ")");
            
            if (heap.get(index) > heap.get(parentIndex)) {
                addStep("Swapping " + heap.get(index) + " with parent " + heap.get(parentIndex));
                swap(index, parentIndex);
                addStep("Heap after swap: " + heap.toString());
                index = parentIndex;
            } else {
                addStep(heap.get(index) + " is in correct position");
                break;
            }
        }
    }
    
    public void remove(int value) {
        int index = heap.indexOf(value);
        if (index == -1) {
            log("Value not found in heap: " + value);
            return;
        }
        
        if (stepMode) {
            prepareStepMode("Remove " + value);
            addStep("Replacing " + heap.get(index) + " with last element " + heap.get(heap.size()-1));
        }
        
        heap.set(index, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        
        if (stepMode) {
            addStep("Heap after replacement: " + heap.toString());
            siftDownSteps(index);
        } else {
            siftDown(index);
        }
    }
    
    private void removeRoot() {
        if (heap.isEmpty()) return;
        
        int rootValue = heap.get(0);
        if (stepMode) {
            prepareStepMode("Remove Root (" + rootValue + ")");
            addStep("Replacing root " + rootValue + " with last element " + heap.get(heap.size()-1));
        }
        
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        
        if (stepMode) {
            addStep("Heap after replacement: " + heap.toString());
            siftDownSteps(0);
        } else {
            siftDown(0);
        }
    }
    
    private void siftDown(int index) {
        int size = heap.size();
        while (index < size) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int largest = index;
            
            if (leftChild < size && heap.get(leftChild) > heap.get(largest)) {
                largest = leftChild;
            }
            
            if (rightChild < size && heap.get(rightChild) > heap.get(largest)) {
                largest = rightChild;
            }
            
            if (largest != index) {
                swap(index, largest);
                index = largest;
            } else {
                break;
            }
        }
    }
    
    private void siftDownSteps(int index) {
        int size = heap.size();
        while (index < size) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int largest = index;
            
            addStep("Current node: " + heap.get(index) + " (index " + index + ")");
            
            if (leftChild < size) {
                addStep("Left child: " + heap.get(leftChild) + " (index " + leftChild + ")");
                if (heap.get(leftChild) > heap.get(largest)) {
                    largest = leftChild;
                }
            }
            
            if (rightChild < size) {
                addStep("Right child: " + heap.get(rightChild) + " (index " + rightChild + ")");
                if (heap.get(rightChild) > heap.get(largest)) {
                    largest = rightChild;
                }
            }
            
            if (largest != index) {
                addStep("Swapping " + heap.get(index) + " with " + heap.get(largest));
                swap(index, largest);
                addStep("Heap after swap: " + heap.toString());
                index = largest;
            } else {
                addStep("Node is in correct position");
                break;
            }
        }
    }
    
    private void buildHeap(ArrayList<Integer> numbers) {
        heap = new ArrayList<>(numbers);
        if (stepMode) {
            prepareStepMode("Build Heap");
            addStep("Initial array: " + heap.toString());
            for (int i = heap.size() / 2 - 1; i >= 0; i--) {
                siftDownSteps(i);
            }
        } else {
            for (int i = heap.size() / 2 - 1; i >= 0; i--) {
                siftDown(i);
            }
        }
    }
    
    private void prepareStepMode(String operation) {
        operationInProgress = true;
        currentOperation = operation;
        steps = new ArrayList<>();
        currentStep = 0;
        addStep("=== " + operation + " STARTED ===");
    }
    
    private void addStep(String message) {
        steps.add(message);
    }
    
    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    
    private void updateDisplay() {
        arrayTextArea.setText(heap.toString());
        treePanel.repaint();
        
        if (!stepMode || steps == null || steps.isEmpty()) {
            logArea.setCaretPosition(logArea.getDocument().getLength());
        }
    }
    
    private void log(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void drawTree(Graphics g, int width, int height) {
        if (heap.isEmpty()) {
            g.drawString("Heap is empty", width / 2 - 50, height / 2);
            return;
        }
        
        int nodeRadius = 25;
        int levelHeight = 80;
        
        // Draw array representation
        g.setColor(Color.BLACK);
        g.drawString("Array: " + heap.toString(), 20, 30);
        
        // Highlight the current node being processed in step mode
        int highlightIndex = -1;
        if (stepMode && operationInProgress && currentStep < steps.size()) {
            String currentStepText = steps.get(currentStep);
            if (currentStepText.contains("index ")) {
                String indexPart = currentStepText.substring(currentStepText.indexOf("index ") + 6);
                if (indexPart.contains(")")) {
                    indexPart = indexPart.substring(0, indexPart.indexOf(")"));
                    try {
                        highlightIndex = Integer.parseInt(indexPart.trim());
                    } catch (NumberFormatException e) {
                        // Ignore if we can't parse the index
                    }
                }
            }
        }
        
        // Draw tree
        Queue<NodePosition> queue = new LinkedList<>();
        int startX = width / 2;
        int startY = 60;
        
        queue.add(new NodePosition(0, startX, startY));
        
        while (!queue.isEmpty()) {
            NodePosition current = queue.poll();
            int index = current.index;
            int x = current.x;
            int y = current.y;
            
            // Draw node
            if (index == highlightIndex) {
                g.setColor(Color.ORANGE);
            } else {
                g.setColor(Color.CYAN);
            }
            g.fillOval(x - nodeRadius, y - nodeRadius, 2 * nodeRadius, 2 * nodeRadius);
            g.setColor(Color.BLACK);
            g.drawOval(x - nodeRadius, y - nodeRadius, 2 * nodeRadius, 2 * nodeRadius);
            
            // Draw value
            String value = String.valueOf(heap.get(index));
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(value);
            int textHeight = fm.getHeight();
            g.drawString(value, x - textWidth / 2, y + textHeight / 4);
            
            // Draw index
            g.setColor(Color.RED);
            g.drawString("["+index+"]", x - 15, y + nodeRadius + 15);
            
            // Calculate child positions
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            
            int nextLevelY = y + levelHeight;
            int horizontalOffset = (int) (width / Math.pow(2, (y - startY) / levelHeight + 2));
            
            if (leftChild < heap.size()) {
                int leftX = x - horizontalOffset / 2;
                queue.add(new NodePosition(leftChild, leftX, nextLevelY));
                
                // Draw line to left child
                g.setColor(Color.BLACK);
                g.drawLine(x, y + nodeRadius, leftX, nextLevelY - nodeRadius);
            }
            
            if (rightChild < heap.size()) {
                int rightX = x + horizontalOffset / 2;
                queue.add(new NodePosition(rightChild, rightX, nextLevelY));
                
                // Draw line to right child
                g.setColor(Color.BLACK);
                g.drawLine(x, y + nodeRadius, rightX, nextLevelY - nodeRadius);
            }
        }
    }
    
    private static class NodePosition {
        int index;
        int x;
        int y;
        
        NodePosition(int index, int x, int y) {
            this.index = index;
            this.x = x;
            this.y = y;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HeapVisualizer().setVisible(true));
    }
}