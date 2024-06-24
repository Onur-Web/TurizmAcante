package core.mouselistener;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener {
    private JTable table;
    private JPopupMenu jPopupMenu;

    public MouseListener(JTable table, JPopupMenu jPopupMenu) {
        this.table = table;
        this.jPopupMenu = jPopupMenu;
    }

    public void listenToTable() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = table.rowAtPoint(e.getPoint());
                if (r >= 0 && r < table.getRowCount()) {
                    table.setRowSelectionInterval(r, r);
                } else {
                    table.clearSelection();
                }

                int rowIndex = table.getSelectedRow();

                if (rowIndex < 0)
                    return;
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    jPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }
}
