package custom;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class StatusCellRenderer extends DefaultTableCellRenderer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        String status = (String) value;
        if ("Đi làm".equals(status)) {
            rendererComponent.setForeground(Color.GREEN);
        } else if ("Nghỉ phép".equals(status)) {
            rendererComponent.setForeground(Color.ORANGE);
        } else if ("Vắng".equals(status)) {
            rendererComponent.setForeground(Color.RED);
        } else {
            rendererComponent.setForeground(table.getForeground());
        }

        return rendererComponent;
    }
}