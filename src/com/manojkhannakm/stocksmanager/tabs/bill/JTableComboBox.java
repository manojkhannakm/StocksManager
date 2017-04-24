package com.manojkhannakm.stocksmanager.tabs.bill;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.Comparator;

/**
 * @author Manoj Khanna
 */

public class JTableComboBox extends JComboBox<String> {

    private DefaultTableModel defaultTableModel;
    private JTable table;
    private JComboBox<String> comboBox;

    public JTableComboBox() {
        comboBox = this;
        setUI(new TableComboBoxUI(getJButton(this)));
    }

    @Override
    public void removeAllItems() {
        defaultTableModel.setRowCount(0);
        super.removeAllItems();
    }

    @Override
    public void setSelectedIndex(int index) {
        super.setSelectedIndex(index);
        table.getSelectionModel().setSelectionInterval(index, index);
        table.scrollRectToVisible(table.getCellRect(index, 0, true));
    }

    public void addItem(Object[] objects) {
        defaultTableModel.addRow(objects);
        super.addItem(objects[0].toString());
    }

    private JButton getJButton(Container container) {
        if (container instanceof JButton) {
            return (JButton) container;
        }
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof Container) {
                return getJButton((Container) component);
            }
        }
        return null;
    }

    private class TableComboBoxUI extends BasicComboBoxUI {

        public TableComboBoxUI(JButton button) {
            arrowButton = button;
        }

        @Override
        protected JButton createArrowButton() {
            squareButton = false;
            return arrowButton;
        }

        @Override
        protected ComboPopup createPopup() {
            return new TableComboBoxPopup(JTableComboBox.this.comboBox);
        }

    }

    private class TableComboBoxPopup extends BasicComboPopup {

        private boolean isMousePressed;

        public TableComboBoxPopup(final JComboBox<String> comboBox) {
            super(comboBox);
            defaultTableModel = new DefaultTableModel(new Object[]{"ID", "Price (Rs.)", "Stock"}, 0) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }

            };

            TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<>(defaultTableModel);
            tableRowSorter.setSortsOnUpdates(true);
            //noinspection unchecked
            final Comparator<String> defaultComparator = (Comparator<String>) tableRowSorter.getComparator(0);
            for (int i = 0, n = defaultTableModel.getColumnCount(); i < n; i++) {
                tableRowSorter.setComparator(i, (object1, object2) -> {
                    String string1 = object1.toString(), string2 = object2.toString();
                    try {
                        return (int) ((Float.parseFloat(string1) - Float.parseFloat(string2)) * 100);
                    } catch (NumberFormatException ex) {
                        return defaultComparator.compare(string1, string2);
                    }
                });
            }

            table = new JTable(defaultTableModel);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.setRowSorter(tableRowSorter);
            table.setRowHeight(25);
            table.getColumnModel().getColumn(0).setPreferredWidth(150);
            table.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

            final JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.getViewport().setBackground(Color.WHITE);

            final JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
            final int scrollBarWidth = scrollBar.getPreferredSize().width + 2;
            scrollPane.setPreferredSize(new Dimension(table.getPreferredSize().width + (scrollBar.isVisible() ? scrollBarWidth : 0), 250));

            table.addComponentListener(new ComponentListener() {

                @Override
                public void componentShown(ComponentEvent e) {
                }

                @Override
                public void componentResized(ComponentEvent e) {
                    scrollPane.setPreferredSize(new Dimension(table.getPreferredSize().width + (scrollBar.isVisible() ? scrollBarWidth : 0), 250));
                    hide();
                    show();
                }

                @Override
                public void componentMoved(ComponentEvent e) {
                }

                @Override
                public void componentHidden(ComponentEvent e) {
                }

            });

            table.addMouseListener(new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    comboBox.setSelectedIndex(table.getSelectedRow());
                    isMousePressed = false;
                    hide();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    isMousePressed = true;
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                }

            });

            table.addMouseMotionListener(new MouseMotionListener() {

                @Override
                public void mouseMoved(MouseEvent e) {
                    int rowIndex = table.rowAtPoint(e.getPoint());
                    table.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                }

            });

            removeAll();
            add(scrollPane);

        }

        @Override
        public void hide() {
            if (!isMousePressed) {
                super.hide();
            }
        }

    }

}
