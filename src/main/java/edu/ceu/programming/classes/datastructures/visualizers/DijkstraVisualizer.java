package edu.ceu.programming.classes.datastructures.visualizers;

// DijkstraVisualizer.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class DijkstraVisualizer extends JFrame {
    private static final double INF = Double.POSITIVE_INFINITY;
    private static final int MAX_NODES = 12;

    private JTextArea inputArea, calcArea;
    private JComboBox<String> sourceSelector;
    private JButton loadButton, runButton, nextButton;
    private JTable distTable, predTable;
    private DefaultTableModel distModel, predModel;
    private JLabel statusLabel;

    private java.util.List<String> nodes;
    private double[][] adjMatrix;
    private double[] dist;
    private int[] pred;
    private boolean[] visited;
    private PriorityQueue<Node> queue;
    private int step = 0;
    private String sourceNode;

    public DijkstraVisualizer() {
        setTitle("Dijkstra Educational Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Graph Definition"));
        inputArea = new JTextArea(5, 30);
        inputArea.setText("# Source Target Weight\nA B 4\nA C 1\nC B 2\nB D 1\nC D 5");
        inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

        JPanel controlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        loadButton = new JButton("Load Graph");
        sourceSelector = new JComboBox<>();
        runButton = new JButton("Start Dijkstra");
        nextButton = new JButton("Next Step");
        nextButton.setEnabled(false);
        controlTop.add(loadButton);
        controlTop.add(new JLabel("Source: "));
        controlTop.add(sourceSelector);
        controlTop.add(runButton);
        controlTop.add(nextButton);

        inputPanel.add(controlTop, BorderLayout.SOUTH);

        JTabbedPane matricesPane = new JTabbedPane();
        distModel = new DefaultTableModel();
        distTable = new JTable(distModel);
        matricesPane.add("Distance (D)", new JScrollPane(distTable));
        predModel = new DefaultTableModel();
        predTable = new JTable(predModel);
        matricesPane.add("Predecessor (P)", new JScrollPane(predTable));

        JPanel logPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel(" ");
        calcArea = new JTextArea(8, 60);
        calcArea.setEditable(false);
        calcArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane calcScroll = new JScrollPane(calcArea);
        logPanel.add(statusLabel, BorderLayout.NORTH);
        logPanel.add(calcScroll, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(matricesPane, BorderLayout.CENTER);
        getContentPane().add(logPanel, BorderLayout.SOUTH);

        loadButton.addActionListener(e -> loadGraph());
        runButton.addActionListener(e -> startDijkstra());
        nextButton.addActionListener(e -> nextStep());
    }

    private void loadGraph() {
        nodes = new ArrayList<>();
        java.util.List<Edge> edges = new ArrayList<>();
        String[] lines = inputArea.getText().split("\\R");

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;
            String[] parts = line.split("\\s+");
            if (parts.length < 3) continue;
            String u = parts[0];
            String v = parts[1];
            double w = Double.parseDouble(parts[2]);
            if (!nodes.contains(u)) nodes.add(u);
            if (!nodes.contains(v)) nodes.add(v);
            edges.add(new Edge(u, v, w));
        }

        if (nodes.size() > MAX_NODES) {
            JOptionPane.showMessageDialog(this, "Maximum node limit is " + MAX_NODES);
            return;
        }

        int n = nodes.size();
        adjMatrix = new double[n][n];
        for (double[] row : adjMatrix) Arrays.fill(row, INF);

        for (Edge e : edges) {
            int u = nodes.indexOf(e.u);
            int v = nodes.indexOf(e.v);
            adjMatrix[u][v] = e.w; // Directed
        }

        sourceSelector.removeAllItems();
        for (String node : nodes) sourceSelector.addItem(node);
        statusLabel.setText("Graph loaded. Choose source and press Start.");
    }

    private void startDijkstra() {
        if (nodes == null || nodes.isEmpty()) return;
        int n = nodes.size();
        sourceNode = (String) sourceSelector.getSelectedItem();
        int src = nodes.indexOf(sourceNode);

        dist = new double[n];
        pred = new int[n];
        visited = new boolean[n];
        Arrays.fill(dist, INF);
        Arrays.fill(pred, -1);
        dist[src] = 0;

        queue = new PriorityQueue<>(Comparator.comparingDouble(a -> a.dist));
        queue.add(new Node(src, 0));
        step = 0;

        updateTables();
        calcArea.setText("");
        statusLabel.setText("Running Dijkstra from " + sourceNode);
        nextButton.setEnabled(true);
    }

    private void nextStep() {
        if (queue.isEmpty()) {
            nextButton.setEnabled(false);
            statusLabel.setText("Finished Dijkstra from " + sourceNode);
            return;
        }

        Node current = queue.poll();
        int u = current.id;
        if (visited[u]) return;
        visited[u] = true;

        StringBuilder log = new StringBuilder();
        log.append("Step " + (++step) + ": Visiting node " + nodes.get(u) + "\n");

        for (int v = 0; v < nodes.size(); v++) {
            if (adjMatrix[u][v] != INF && !visited[v]) {
                double alt = dist[u] + adjMatrix[u][v];
                if (alt < dist[v]) {
                    log.append(String.format("D[%s] = min(%.1f, %.1f + %.1f) = %.1f\n",
                            nodes.get(v), dist[v], dist[u], adjMatrix[u][v], alt));
                    dist[v] = alt;
                    pred[v] = u;
                    queue.add(new Node(v, alt));
                }
            }
        }

        updateTables();
        calcArea.setText(log.toString());
    }

    private void updateTables() {
        distModel.setRowCount(0);
        distModel.setColumnCount(0);
        distModel.addColumn("Node");
        distModel.addColumn("Distance");
        for (int i = 0; i < nodes.size(); i++) {
            String d = dist[i] == INF ? "∞" : String.format("%.1f", dist[i]);
            distModel.addRow(new Object[]{nodes.get(i), d});
        }

        predModel.setRowCount(0);
        predModel.setColumnCount(0);
        predModel.addColumn("Node");
        predModel.addColumn("Predecessor");
        for (int i = 0; i < nodes.size(); i++) {
            String p = (pred[i] == -1) ? "-" : nodes.get(pred[i]);
            predModel.addRow(new Object[]{nodes.get(i), p});
        }
    }

    static class Edge {
        String u, v;
        double w;
        Edge(String u, String v, double w) { this.u = u; this.v = v; this.w = w; }
    }

    static class Node {
        int id;
        double dist;
        Node(int id, double dist) { this.id = id; this.dist = dist; }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DijkstraVisualizer().setVisible(true));
    }
}
