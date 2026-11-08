import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class HelpDeskManagement extends JFrame {

    Color navy = new Color(30, 41, 75);
    Color background = new Color(245, 247, 252);
    Color blue = new Color(65, 105, 225);
    Color green = new Color(22, 163, 106);
    Color purple = new Color(125, 85, 200);

    static class Ticket {
        int id;
        String name, problem, status;

        Ticket(int id, String name, String problem) {
            this.id = id;
            this.name = name;
            this.problem = problem;
            this.status = "Open";
        }
    }

    ArrayList<Ticket> tickets = new ArrayList<>();
    int nextId = 1;

    JTextField nameField;
    JTextArea problemArea;
    JTable table;
    DefaultTableModel model;

    JLabel totalLabel, openLabel, resolvedLabel;

    public HelpDeskManagement() {
        setTitle("Help Desk Management System");
        setSize(1000, 700);
        setMinimumSize(new Dimension(750, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(15, 15));
        root.setBackground(background);
        root.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));
        setContentPane(root);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(navy);
        header.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JLabel title = new JLabel("HELP DESK MANAGEMENT SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 23));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Manage your support tickets easily");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(new Color(210, 220, 245));

        JPanel headings = new JPanel(new GridLayout(2, 1, 0, 5));
        headings.setBackground(navy);
        headings.add(title);
        headings.add(subtitle);
        header.add(headings, BorderLayout.CENTER);

        root.add(header, BorderLayout.NORTH);

        // Main content
        JPanel content = new JPanel(new BorderLayout(12, 12));
        content.setBackground(background);

        // Dashboard cards
        JPanel cards = new JPanel(new GridLayout(1, 3, 12, 0));
        cards.setBackground(background);

        totalLabel = new JLabel("0");
        openLabel = new JLabel("0");
        resolvedLabel = new JLabel("0");

        cards.add(createCard("TOTAL TICKETS", totalLabel, blue));
        cards.add(createCard("OPEN TICKETS", openLabel, purple));
        cards.add(createCard("RESOLVED TICKETS", resolvedLabel, green));

        content.add(cards, BorderLayout.NORTH);

        // Ticket entry form
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(225, 230, 240)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 6, 5, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel formTitle = new JLabel("Create Support Ticket");
        formTitle.setFont(new Font("Arial", Font.BOLD, 16));
        formTitle.setForeground(navy);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        form.add(formTitle, c);

        c.gridwidth = 1;
        c.gridy = 1;
        c.gridx = 0;
        form.add(new JLabel("User Name:"), c);

        nameField = new JTextField(22);
        styleField(nameField);
        c.gridx = 1;
        form.add(nameField, c);

        c.gridy = 2;
        c.gridx = 0;
        form.add(new JLabel("Problem:"), c);

        problemArea = new JTextArea(2, 22);
        problemArea.setFont(new Font("Arial", Font.PLAIN, 14));
        problemArea.setLineWrap(true);
        problemArea.setWrapStyleWord(true);
        problemArea.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        JScrollPane problemScroll = new JScrollPane(problemArea);
        problemScroll.setPreferredSize(new Dimension(280, 55));

        c.gridx = 1;
        form.add(problemScroll, c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 3));
        buttons.setBackground(Color.WHITE);

        JButton create = makeButton("Create Ticket", green);
        JButton resolve = makeButton("Resolve Selected", blue);
        JButton clear = makeButton("Clear Fields", new Color(100, 110, 130));

        buttons.add(create);
        buttons.add(resolve);
        buttons.add(clear);

        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 2;
        form.add(buttons, c);

        // Ticket table
        String[] columns = {
            "Ticket ID", "User Name", "Problem", "Status"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(new Color(220, 232, 255));
        table.setSelectionForeground(navy);
        table.setGridColor(new Color(230, 234, 242));
        table.setShowVerticalLines(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getColumnModel().getColumn(0).setPreferredWidth(90);
        table.getColumnModel().getColumn(1).setPreferredWidth(160);
        table.getColumnModel().getColumn(2).setPreferredWidth(450);
        table.getColumnModel().getColumn(3).setPreferredWidth(130);

        JTableHeader th = table.getTableHeader();
        th.setFont(new Font("Arial", Font.BOLD, 13));
        th.setBackground(navy);
        th.setForeground(Color.WHITE);
        th.setPreferredSize(new Dimension(0, 34));

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(700, 280));
        tableScroll.setMinimumSize(new Dimension(300, 180));
        tableScroll.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tableScroll.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tableScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(225, 230, 240)),
            "Support Tickets"
        ));

        // Form stays above the table.
        // Table uses the remaining space and can scroll.
        JPanel lower = new JPanel(new BorderLayout(0, 10));
        lower.setBackground(background);
        lower.add(form, BorderLayout.NORTH);
        lower.add(tableScroll, BorderLayout.CENTER);

        content.add(lower, BorderLayout.CENTER);
        root.add(content, BorderLayout.CENTER);

        // Button actions
        create.addActionListener(e -> createTicket());
        resolve.addActionListener(e -> resolveTicket());
        clear.addActionListener(e -> clearFields());

        setVisible(true);
    }

    JPanel createCard(String text, JLabel number, Color colour) {
        JPanel card = new JPanel(new BorderLayout(5, 6));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 5, 0, 0, colour),
            BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(new Color(100, 110, 130));

        number.setFont(new Font("Arial", Font.BOLD, 26));
        number.setForeground(colour);

        card.add(label, BorderLayout.NORTH);
        card.add(number, BorderLayout.CENTER);
        return card;
    }

    void styleField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 218, 232)),
            BorderFactory.createEmptyBorder(7, 8, 7, 8)
        ));
    }

    JButton makeButton(String text, Color colour) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(colour);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(9, 12, 9, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    void createTicket() {
        String name = nameField.getText().trim();
        String problem = problemArea.getText().trim();

        if (name.isEmpty() || problem.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both user name and problem.",
                "Missing Information",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        tickets.add(new Ticket(nextId++, name, problem));
        refreshTable();
        clearFields();

        JOptionPane.showMessageDialog(this,
            "Ticket created successfully!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
    }

    void resolveTicket() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a ticket from the table.");
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);
        int id = (int) model.getValueAt(modelRow, 0);

        for (Ticket t : tickets) {
            if (t.id == id) {
                if (t.status.equals("Resolved")) {
                    JOptionPane.showMessageDialog(this,
                        "This ticket is already resolved.");
                } else {
                    t.status = "Resolved";
                    refreshTable();
                    JOptionPane.showMessageDialog(this,
                        "Ticket resolved successfully!");
                }
                return;
            }
        }
    }

    void refreshTable() {
        model.setRowCount(0);
        int open = 0;
        int resolved = 0;

        for (Ticket t : tickets) {
            model.addRow(new Object[]{
                t.id, t.name, t.problem, t.status
            });

            if (t.status.equals("Open")) {
                open++;
            } else {
                resolved++;
            }
        }

        totalLabel.setText(String.valueOf(tickets.size()));
        openLabel.setText(String.valueOf(open));
        resolvedLabel.setText(String.valueOf(resolved));
    }

    void clearFields() {
        nameField.setText("");
        problemArea.setText("");
        nameField.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
            new HelpDeskManagement()
        );
    }
}
