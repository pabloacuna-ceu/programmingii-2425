package edu.ceu.programming.classes.datastructures.visualizers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class GraphFloydUI extends JFrame {
    private static final double INF = Double.POSITIVE_INFINITY;
    private static final int MAX_NODES = 12;
    private JTextArea inputArea, calcArea;
    private JButton loadButton, nextButton;
    private JTable distTable, predTable;
    private DefaultTableModel distModel, predModel;
    private JLabel statusLabel;
    private java.util.List<String> nodes;
    private double[][] dist;
    private double[][] prevDist;
    private int[][] pred;
    private int[][] prevPred;
    private int n;
    private int kStep = 0;

    public GraphFloydUI() {
        setTitle("Floyd–Warshall Educational Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Graph Definition"));
        inputArea = new JTextArea(5, 30);
        inputArea.setText("# One edge per line: Source Target Weight\n# e.g.\nA B 5\nB C 3\nA C 10");
        inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);
        loadButton = new JButton("Load Graph");
        inputPanel.add(loadButton, BorderLayout.SOUTH);

        JTabbedPane matricesPane = new JTabbedPane();
        distModel = new DefaultTableModel();
        distTable = new JTable(distModel);
        distTable.setDefaultRenderer(Object.class, new BoldChangeRenderer(true));
        matricesPane.add("Distance (D)", new JScrollPane(distTable));
        predModel = new DefaultTableModel();
        predTable = new JTable(predModel);
        predTable.setDefaultRenderer(Object.class, new BoldChangeRenderer(false));
        matricesPane.add("Predecessor (P)", new JScrollPane(predTable));

        JPanel controlPanel = new JPanel(new BorderLayout());
        JPanel topControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel(" ");
        nextButton = new JButton("Next Step");
        nextButton.setEnabled(false);
        topControls.add(statusLabel);
        topControls.add(nextButton);
        calcArea = new JTextArea(6, 60);
        calcArea.setEditable(false);
        calcArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane calcScroll = new JScrollPane(calcArea);
        controlPanel.add(topControls, BorderLayout.NORTH);
        controlPanel.add(calcScroll, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(matricesPane, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.SOUTH);

        loadButton.addActionListener(e -> loadGraph());
        nextButton.addActionListener(e -> nextIteration());
    }

    private void loadGraph() {
        parseInput();
        initializeMatrices();
        kStep = 0;
        statusLabel.setText("Graph loaded. Ready to start.");
        updateTableModels();
        nextButton.setEnabled(true);
        calcArea.setText("");
    }

    private void parseInput() {
        String[] lines = inputArea.getText().split("\\R");
        nodes = new ArrayList<>();
        java.util.List<Triple> edges = new ArrayList<>();
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;
            String[] parts = line.split("\\s+");
            if (parts.length < 3) continue;
            String u = parts[0], v = parts[1];
            double w;
            try { w = Double.parseDouble(parts[2]); }
            catch (NumberFormatException ex) { continue; }
            if (!nodes.contains(u)) nodes.add(u);
            if (!nodes.contains(v)) nodes.add(v);
            edges.add(new Triple(u, v, w));
        }
        if (nodes.size() > MAX_NODES) {
            JOptionPane.showMessageDialog(this, "Maximum number of nodes is " + MAX_NODES);
            return;
        }
        n = nodes.size();
        dist = new double[n][n];
        prevDist = new double[n][n];
        pred = new int[n][n];
        prevPred = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
            Arrays.fill(prevDist[i], INF);
            Arrays.fill(pred[i], -1);
            Arrays.fill(prevPred[i], -1);
            dist[i][i] = 0;
            prevDist[i][i] = 0;
            pred[i][i] = i;
            prevPred[i][i] = i;
        }
        for (Triple t : edges) {
            int u = nodes.indexOf(t.u), v = nodes.indexOf(t.v);
            dist[u][v] = t.w;
            prevDist[u][v] = t.w;
            pred[u][v] = u;
            prevPred[u][v] = u;
        }
    }

    private void initializeMatrices() {}

    private void nextIteration() {
        for (int i = 0; i < n; i++) {
            System.arraycopy(dist[i], 0, prevDist[i], 0, n);
            System.arraycopy(pred[i], 0, prevPred[i], 0, n);
        }

        if (kStep >= n) return;
        int k = kStep;
        String kNode = nodes.get(k);
        statusLabel.setText("Using \"" + kNode + "\" as intermediate (step " + (k+1) + " of " + n + ")");
        StringBuilder calcLog = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double old = dist[i][j];
                double newDist = dist[i][k] + dist[k][j];
                if (newDist < dist[i][j]) {
                    calcLog.append("D[" + nodes.get(i) + "][" + nodes.get(j) + "] = min(" +
                            format(old) + ", " + format(dist[i][k]) + " + " + format(dist[k][j]) + ") = " + format(newDist) + "\n");
                    dist[i][j] = newDist;
                    pred[i][j] = pred[k][j];
                }
            }
        }
        kStep++;
        updateTableModels();
        calcArea.setText(calcLog.toString());
        if (kStep == n) {
            nextButton.setEnabled(false);
            statusLabel.setText("Done — all intermediate nodes processed.");
        }
    }

    private String format(double d) {
        return (d == INF ? "∞" : String.format("%.1f", d));
    }

    private void updateTableModels() {
        distModel.setRowCount(0);
        distModel.setColumnCount(0);
        distModel.addColumn(" ");
        for (String col : nodes) distModel.addColumn(col);
        for (int i = 0; i < n; i++) {
            Object[] row = new Object[n + 1];
            row[0] = nodes.get(i);
            for (int j = 0; j < n; j++) {
                row[j + 1] = format(dist[i][j]);
            }
            distModel.addRow(row);
        }

        predModel.setRowCount(0);
        predModel.setColumnCount(0);
        predModel.addColumn(" ");
        for (String col : nodes) predModel.addColumn(col);
        for (int i = 0; i < n; i++) {
            Object[] row = new Object[n + 1];
            row[0] = nodes.get(i);
            for (int j = 0; j < n; j++) {
                int p = pred[i][j];
                row[j + 1] = (p == -1 ? "-" : nodes.get(p));
            }
            predModel.addRow(row);
        }
    }

    class BoldChangeRenderer extends DefaultTableCellRenderer {
        private final boolean isDistance;
        public BoldChangeRenderer(boolean isDistance) { this.isDistance = isDistance; }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int col) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            if (row > -1 && col > 0 && row < n && col - 1 < n) {
                boolean changed;
                if (isDistance) {
                    String valStr = (String) value;
                    changed = !valStr.equals(format(prevDist[row][col - 1]));
                } else {
                    String valStr = (String) value;
                    int prevVal = prevPred[row][col - 1];
                    String prevStr = (prevVal == -1 ? "-" : nodes.get(prevVal));
                    changed = !valStr.equals(prevStr);
                }
                c.setFont(c.getFont().deriveFont(changed ? Font.BOLD : Font.PLAIN));
            } else {
                c.setFont(c.getFont().deriveFont(Font.PLAIN));
            }
            return c;
        }
    }

    static class Triple {
        String u, v;
        double w;
        Triple(String u, String v, double w) { this.u = u; this.v = v; this.w = w; }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GraphFloydUI().setVisible(true));
    }
}
