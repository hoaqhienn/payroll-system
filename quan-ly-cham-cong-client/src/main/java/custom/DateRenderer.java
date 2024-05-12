package custom;

import java.text.DateFormat;
import java.util.Date;

import javax.swing.table.DefaultTableCellRenderer;

public class DateRenderer extends DefaultTableCellRenderer {

    private DateFormat dateFormat;

    public DateRenderer(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setValue(Object value) {
        if (value instanceof Date) {
            value = dateFormat.format(value);
        }
        super.setValue(value);
    }

}