package edu.ceu.programming.classes.datastructures.visualizers;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.Timer;

/*
PROMPT (DeepSeek):
Create an educational Java app that lets students create and visualize a
graph data structure (unweighted and not-directional),
displaying the adjacency matrix while the user creates the graph.
The input of the user can be text-based, e.g. A->B, and then B->C.
Finally, it allows a step-by-step visualization of traversals
Depth-First Search (DFS) and Breadth-First Search (BFS).
The app must include a visual representation of the graph, with nodes and archs.
The app is just for educational purposes, so the graphs won't be very big
 */

public class GraphVisualizer extends JFrame {
    private Map<Character, List<Character>> graph;
    private JTextArea inputArea, traversalArea;
    private JButton buildButton, dfsButton, bfsButton, resetButton;
    private JPanel matrixPanel;
    private Set<Character> visited;
    private Queue<Character> bfsQueue;
    private Stack<Character> dfsStack;
    private Timer traversalTimer;
    private boolean isBFS;
    private GraphPanel graphPanel;
    private char currentTraversalNode;

    public GraphVisualizer() {
        graph = new HashMap<>();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Graph Visualizer");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputArea = new JTextArea(5, 20);
        inputArea.setLineWrap(true);
        inputPanel.add(new JLabel("Enter edges (format: A->B, B->C):"), BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

        buildButton = new JButton("Build Graph");
        buildButton.addActionListener(e -> buildGraph());
        inputPanel.add(buildButton, BorderLayout.SOUTH);

        // Graph visualization panel
        graphPanel = new GraphPanel();
        graphPanel.setPreferredSize(new Dimension(400, 400));
        graphPanel.setBackground(Color.WHITE);

        // Traversal panel
        traversalArea = new JTextArea(10, 20);
        traversalArea.setEditable(false);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        dfsButton = new JButton("DFS Traversal");
        dfsButton.addActionListener(e -> startDFS());
        bfsButton = new JButton("BFS Traversal");
        bfsButton.addActionListener(e -> startBFS());
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetTraversal());
        buttonPanel.add(dfsButton);
        buttonPanel.add(bfsButton);
        buttonPanel.add(resetButton);

        // Matrix panel
        matrixPanel = new JPanel();
        matrixPanel.setBorder(BorderFactory.createTitledBorder("Adjacency Matrix"));

        // Main layout
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(inputPanel, BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(traversalArea), BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(matrixPanel, BorderLayout.NORTH);
        rightPanel.add(graphPanel, BorderLayout.CENTER);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void buildGraph() {
        graph.clear();
        String input = inputArea.getText().trim();
        String[] edges = input.split(",");

        for (String edge : edges) {
            edge = edge.trim();
            if (edge.contains("->")) {
                String[] nodes = edge.split("->");
                if (nodes.length == 2) {
                    char from = nodes[0].trim().charAt(0);
                    char to = nodes[1].trim().charAt(0);

                    // Add edge in both directions (undirected graph)
                    graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
                    graph.computeIfAbsent(to, k -> new ArrayList<>()).add(from);
                }
            }
        }

        displayAdjacencyMatrix();
        graphPanel.setGraph(graph);
        graphPanel.repaint();
    }

    private void displayAdjacencyMatrix() {
        matrixPanel.removeAll();
        
        if (graph.isEmpty()) {
            matrixPanel.add(new JLabel("No graph to display"));
            matrixPanel.revalidate();
            matrixPanel.repaint();
            return;
        }

        // Get sorted list of nodes
        List<Character> nodes = new ArrayList<>(graph.keySet());
        Collections.sort(nodes);
        int size = nodes.size();

        // Create matrix layout
        matrixPanel.setLayout(new GridLayout(size + 1, size + 1));

        // Add header row
        matrixPanel.add(new JLabel(""));
        for (char node : nodes) {
            matrixPanel.add(new JLabel(String.valueOf(node), SwingConstants.CENTER));
        }

        // Add matrix rows
        for (char rowNode : nodes) {
            matrixPanel.add(new JLabel(String.valueOf(rowNode), SwingConstants.CENTER));
            for (char colNode : nodes) {
                boolean connected = graph.get(rowNode).contains(colNode);
                JLabel cell = new JLabel(connected ? "1" : "0", SwingConstants.CENTER);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                matrixPanel.add(cell);
            }
        }

        matrixPanel.revalidate();
        matrixPanel.repaint();
    }

    private void startDFS() {
        if (graph.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please build a graph first");
            return;
        }

        traversalArea.setText("Starting DFS Traversal:\n");
        visited = new HashSet<>();
        dfsStack = new Stack<>();
        isBFS = false;

        // Start with the first node in the graph
        char startNode = graph.keySet().iterator().next();
        dfsStack.push(startNode);
        visited.add(startNode);
        currentTraversalNode = startNode;
        traversalArea.append("Visited: " + startNode + "\n");
        graphPanel.setCurrentNode(startNode);
        graphPanel.repaint();

        // Start timer for step-by-step visualization
        if (traversalTimer != null) {
            traversalTimer.stop();
        }
        traversalTimer = new Timer(1000, e -> performTraversalStep());
        traversalTimer.start();
    }

    private void startBFS() {
        if (graph.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please build a graph first");
            return;
        }

        traversalArea.setText("Starting BFS Traversal:\n");
        visited = new HashSet<>();
        bfsQueue = new LinkedList<>();
        isBFS = true;

        // Start with the first node in the graph
        char startNode = graph.keySet().iterator().next();
        bfsQueue.add(startNode);
        visited.add(startNode);
        currentTraversalNode = startNode;
        traversalArea.append("Visited: " + startNode + "\n");
        graphPanel.setCurrentNode(startNode);
        graphPanel.repaint();

        // Start timer for step-by-step visualization
        if (traversalTimer != null) {
            traversalTimer.stop();
        }
        traversalTimer = new Timer(1000, e -> performTraversalStep());
        traversalTimer.start();
    }

    private void resetTraversal() {
        if (traversalTimer != null) {
            traversalTimer.stop();
        }
        traversalArea.setText("");
        graphPanel.setCurrentNode('\0');
        graphPanel.repaint();
    }

    private void performTraversalStep() {
        if (isBFS) {
            if (bfsQueue.isEmpty()) {
                traversalTimer.stop();
                traversalArea.append("BFS Traversal complete!\n");
                currentTraversalNode = '\0';
                graphPanel.setCurrentNode('\0');
                graphPanel.repaint();
                return;
            }

            char current = bfsQueue.poll();
            currentTraversalNode = current;
            graphPanel.setCurrentNode(current);
            
            for (char neighbor : graph.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    bfsQueue.add(neighbor);
                    traversalArea.append("Visited: " + neighbor + "\n");
                }
            }
        } else {
            if (dfsStack.isEmpty()) {
                traversalTimer.stop();
                traversalArea.append("DFS Traversal complete!\n");
                currentTraversalNode = '\0';
                graphPanel.setCurrentNode('\0');
                graphPanel.repaint();
                return;
            }

            char current = dfsStack.peek();
            currentTraversalNode = current;
            graphPanel.setCurrentNode(current);
            boolean hasUnvisitedNeighbor = false;

            for (char neighbor : graph.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    dfsStack.push(neighbor);
                    traversalArea.append("Visited: " + neighbor + "\n");
                    hasUnvisitedNeighbor = true;
                    break;
                }
            }

            if (!hasUnvisitedNeighbor) {
                dfsStack.pop();
            }
        }
        
        graphPanel.repaint();
    }

    class GraphPanel extends JPanel {
        private Map<Character, Point> nodePositions;
        private char currentNode;

        public GraphPanel() {
            nodePositions = new HashMap<>();
            currentNode = '\0';
        }

        public void setGraph(Map<Character, List<Character>> graph) {
            nodePositions.clear();
            if (graph.isEmpty()) return;

            // Calculate positions in a circle
            int centerX = 200;
            int centerY = 200;
            int radius = 150;
            int nodeCount = graph.size();
            double angleStep = 2 * Math.PI / nodeCount;
            double angle = 0;

            for (char node : graph.keySet()) {
                int x = centerX + (int)(radius * Math.cos(angle));
                int y = centerY + (int)(radius * Math.sin(angle));
                nodePositions.put(node, new Point(x, y));
                angle += angleStep;
            }
        }

        public void setCurrentNode(char node) {
            this.currentNode = node;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw edges
            g2d.setColor(Color.LIGHT_GRAY);
            for (Map.Entry<Character, Point> entry : nodePositions.entrySet()) {
                char from = entry.getKey();
                Point fromPoint = entry.getValue();
                for (char to : graph.get(from)) {
                    Point toPoint = nodePositions.get(to);
                    g2d.drawLine(fromPoint.x, fromPoint.y, toPoint.x, toPoint.y);
                }
            }

            // Draw nodes
            for (Map.Entry<Character, Point> entry : nodePositions.entrySet()) {
                char node = entry.getKey();
                Point point = entry.getValue();
                int radius = 20;

                // Draw node
                if (node == currentNode) {
                    g2d.setColor(Color.RED); // Highlight current node
                } else if (visited != null && visited.contains(node)) {
                    g2d.setColor(Color.GREEN); // Visited nodes
                } else {
                    g2d.setColor(Color.BLUE); // Unvisited nodes
                }

                g2d.fillOval(point.x - radius, point.y - radius, radius * 2, radius * 2);

                // Draw node label
                g2d.setColor(Color.WHITE);
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(String.valueOf(node));
                int textHeight = fm.getAscent();
                g2d.drawString(String.valueOf(node), point.x - textWidth/2, point.y + textHeight/4);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GraphVisualizer());
    }
}