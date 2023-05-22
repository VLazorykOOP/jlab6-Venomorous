
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MatrixVectorInterface {
    private JFrame frame;
    private JPanel panel;
    private JButton generateButton;
    private JLabel sizeLabel;
    private JTextField sizeField;
    private JLabel minValueLabel;
    private JTextField minValueField;
    private JLabel maxValueLabel;
    private JTextField maxValueField;
    private JButton readButton;
    private JTable matrixTable;
    private JTable vectorTable;
    private DefaultTableModel matrixModel;
    private DefaultTableModel vectorModel;
    private JScrollPane matrixScrollPane;
    private JScrollPane vectorScrollPane;
    public MatrixVectorInterface() {
        frame = new JFrame("Matrix Vector Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1280, 720));

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        generateButton = new JButton("Generate");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateMatrixVector();
            }
        });

        sizeLabel = new JLabel("Size:");
        sizeField = new JTextField(10);

        minValueLabel = new JLabel("Min Value:");
        minValueField = new JTextField(10);

        maxValueLabel = new JLabel("Max Value:");
        maxValueField = new JTextField(10);

        readButton = new JButton("Read from File");
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readFromFile();
            }
        });

        matrixTable = new JTable();
        vectorTable = new JTable();

        // Initialize matrix table model
        matrixModel = new DefaultTableModel();
        matrixTable.setModel(matrixModel);

        // Initialize vector table model
        vectorModel = new DefaultTableModel();
        vectorTable.setModel(vectorModel);

        matrixScrollPane = new JScrollPane(matrixTable);
        vectorScrollPane = new JScrollPane(vectorTable);

        panel.add(generateButton, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.add(sizeLabel);
        inputPanel.add(sizeField);
        inputPanel.add(minValueLabel);
        inputPanel.add(minValueField);
        inputPanel.add(maxValueLabel);
        inputPanel.add(maxValueField);

        panel.add(inputPanel, BorderLayout.CENTER);

        panel.add(readButton, BorderLayout.SOUTH);

        panel.add(matrixScrollPane, BorderLayout.WEST);
        panel.add(vectorScrollPane, BorderLayout.EAST);

// Revalidate and repaint the panel
        panel.revalidate();
        panel.repaint();
        frame.add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    private void generateMatrixVector() {
        try {
            int n = Integer.parseInt(sizeField.getText());
            int min = Integer.parseInt(minValueField.getText());
            int max = Integer.parseInt(maxValueField.getText());

            if (n <= 0) {
                throw new IllegalArgumentException("Size must be greater than 0.");
            }

            double A[][] = new double[n][n];
            double B[] = new double[n];

            for (int i = 0; i < n; i++) {
                double summ = 0;
                for (int j = 0; j < n; j++) {
                    A[i][j] = Math.floor(Math.random() * (max - min + 1) + min);
                    summ += A[i][j];
                }
                double aver = summ / n;
                B[i] = aver;
            }

            // Check if matrix table and vector table already exist
            if (matrixModel.getDataVector().size() == 0 || vectorModel.getDataVector().size() == 0) {
                // Create new table models
                System.out.println("They're null");
                matrixModel = new DefaultTableModel();
                vectorModel = new DefaultTableModel();


                matrixModel.setColumnCount(n);
                matrixModel.setRowCount(n);
                System.out.println("This is a part before for loop");
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        matrixModel.setValueAt(A[i][j], i, j);
                        System.out.println(A[i][j]);
                    }
                }

                vectorModel.setColumnCount(1);
                vectorModel.setRowCount(n);
                for (int i = 0; i < n; i++) {
                    vectorModel.setValueAt(B[i], i, 0);
                }

                // Set the models to the tables
                matrixTable.setModel(matrixModel);
                vectorTable.setModel(vectorModel);

                // Set the tables to the scroll panes
                matrixScrollPane.setViewportView(matrixTable);
                vectorScrollPane.setViewportView(vectorTable);

                // Add the scroll panes to the panel
                panel.add(matrixScrollPane, BorderLayout.WEST);
                panel.add(vectorScrollPane, BorderLayout.EAST);
            }

//            System.out.println("They're not null");
            // Update matrix table
            matrixModel.setColumnCount(n);
            matrixModel.setRowCount(n);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrixModel.setValueAt(A[i][j], i, j);
                }
            }

            // Update vector table
            vectorModel.setColumnCount(1);
            vectorModel.setRowCount(n);
            for (int i = 0; i < n; i++) {
                vectorModel.setValueAt(B[i], i, 0);
            }

            JOptionPane.showMessageDialog(frame, "Matrix and vector generated successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid numeric values.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }
    }



    private void readFromFile() {
        try {
            String filePath = JOptionPane.showInputDialog(frame, "Enter file path:");
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

//             Read the size of the matrix/vector
                int n = Integer.parseInt(scanner.nextLine());
                System.out.println("n: " + n);

                double A[][] = new double[n][n];
                double B[] = new double[n];

                // Read the matrix values
                for (int i = 0; i < n; i++) {
                    String[] values = scanner.nextLine().split(",");
                    for (int j = 0; j < n; j++) {
                        A[i][j] = Double.parseDouble(values[j]);
                        System.out.println(A[i][j]);
                    }
                    // Calculate the average of row i in matrix A
                    double sum = 0.0;
                    for (int j = 0; j < n; j++) {
                        sum += A[i][j];
                    }
                    B[i] = sum / n; // Store the average in vector B
                    System.out.println("B: " + B[i]);
                }

            // Update matrix table
            matrixModel.setColumnCount(n);
            matrixModel.setRowCount(n);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrixModel.setValueAt(A[i][j], i, j);
                }
            }

            // Update vector table
            vectorModel.setColumnCount(1);
            vectorModel.setRowCount(n);
            for (int i = 0; i < n; i++) {
                vectorModel.setValueAt(B[i], i, 0);
            }

            // Refresh scroll panes
            matrixScrollPane.setViewportView(matrixTable);
            vectorScrollPane.setViewportView(vectorTable);

            scanner.close();
            JOptionPane.showMessageDialog(frame, "Matrix and vector read successfully.");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "File not found.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid file format.");
        }
    }

    public static void main(String[] args) {
        new MatrixVectorInterface();
    }
}


