package custom;

import java.awt.Toolkit;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

public class Custom {
	static Calendar calendar = Calendar.getInstance();
	static java.util.Date currentDate = calendar.getTime();
	public static Date sqlDate = new Date(currentDate.getTime());
	
	public static final double DEFAULT_SALARY = 4680000;
	public static final double LUNCH_ALLOWANCE = 30000;
	
	
	public static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	public static final DecimalFormat df = new DecimalFormat("#,###");
	
    public static void transferData(JTable sourceTable, DefaultTableModel sourceModel, JTable targetTable, DefaultTableModel targetModel) {
        int[] selectedRows = sourceTable.getSelectedRows();
        Arrays.sort(selectedRows);

        for (int i = selectedRows.length - 1; i >= 0; i--) {
            int selectedRow = selectedRows[i];

            Object[] rowData = new Object[sourceModel.getColumnCount()];
            for (int j = 0; j < sourceModel.getColumnCount(); j++) {
                rowData[j] = sourceModel.getValueAt(selectedRow, j);
            }

            sourceModel.removeRow(selectedRow);
            int insertIndex = findInsertIndex(targetModel, rowData[3]);
            targetModel.insertRow(insertIndex, rowData);
        }
    }

    public static int findInsertIndex(DefaultTableModel model, Object key) {
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            @SuppressWarnings("unchecked")
			Comparable<Object> value = (Comparable<Object>) model.getValueAt(i, 3);
            if (key.toString().compareTo(value.toString()) < 0) {
                return i;
            }
        }
        return rowCount;
    }
	
	public static void restrictToNumbers(Document doc) {
		AbstractDocument abstractDoc = (AbstractDocument) doc;
		abstractDoc.setDocumentFilter(new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
					throws BadLocationException {
				if (string == null)
					return;
				replace(fb, offset, 0, string, attr);
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				Document doc = fb.getDocument();
				StringBuilder currentText = new StringBuilder(doc.getText(0, doc.getLength()));
				currentText.replace(offset, offset + length, text);

				if (isNumeric(currentText.toString())) {
					super.replace(fb, offset, length, text, attrs);
				} else {
					Toolkit.getDefaultToolkit().beep(); // Produces a beep sound
					JOptionPane.showMessageDialog(null, "Vui lòng nhập vào ký tự số.");
				}
			}

			private boolean isNumeric(String str) {
				return str.matches("\\d*");
			}
		});
	}
}
