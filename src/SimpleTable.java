import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleTable {
    private JPanel MainPanel;
    private JPanel topPanel;
    private JScrollPane tableScrollPane;
    private JTable scoreTable;
    private JPanel buttonPanel;
    private JButton deleteButton;
    private JLabel headerLabel;

    public SimpleTable() {
        String[] columnName = {"学号","姓名","成绩"};
        String[][]tableData={{"001","Lily","78"},{"002","Jane","89"},{"003","Alex","67"},
                {"004","Macy","83"},{"005","Nancy","66"},{"006","John","99"}};

        //表格模型
        DefaultTableModel model = new DefaultTableModel(tableData, columnName){
            @Override
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };
        scoreTable.setModel(model);
        tableScrollPane.setViewportView(scoreTable);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = scoreTable.getSelectedRow();
                System.out.println(row);
                if(row != -1){
                    model.removeRow(row);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SimpleTable");
        frame.setContentPane(new SimpleTable().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
