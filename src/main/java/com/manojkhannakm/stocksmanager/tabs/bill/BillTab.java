package com.manojkhannakm.stocksmanager.tabs.bill;

import com.manojkhannakm.stocksmanager.StocksManager;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * @author Manoj Khanna
 */

public class BillTab extends JPanel {

    private JTableComboBox idTableComboBox;
    private JTextComponent idTextComponent;
    private DefaultTableModel billTableModel;
    private JTable billTable;
    private JLabel totalQuantityLabel, totalPriceLabel, totalDiscountLabel, grandTotalLabel;

    public BillTab() {
        setLayout(null);
        setBackground(Color.WHITE);
    }

    public void addComponents() {
        JLabel title = new JLabel("Billing: ");
        title.setSize(title.getPreferredSize());
        title.setLocation(10, 10);
        add(title);

        JLabel paymentLabel = new JLabel("Payment using: ");
        paymentLabel.setSize(paymentLabel.getPreferredSize());
        paymentLabel.setLocation(10, 45);
        add(paymentLabel);

        final JComboBox<String> paymentComboBox = new JComboBox<>(new String[]{"Cash", "Card"});
        paymentComboBox.setBackground(Color.WHITE);
        paymentComboBox.setSize(55, 25);
        paymentComboBox.setLocation(105, 40);
        add(paymentComboBox);

        JLabel idLabel = new JLabel("ID: ");
        idLabel.setSize(idLabel.getPreferredSize());
        idLabel.setLocation(180, 45);
        add(idLabel);

        idTableComboBox = new JTableComboBox();
        idTableComboBox.setBackground(Color.WHITE);
        idTableComboBox.setSize(150, 25);
        idTableComboBox.setLocation(200, 40);
        idTableComboBox.setEditable(true);
        add(idTableComboBox);
        idTableComboBox.requestFocusInWindow();

        idTextComponent = (JTextComponent) idTableComboBox.getEditor().getEditorComponent();

        JLabel quantityLabel = new JLabel("Quantity: ");
        quantityLabel.setSize(quantityLabel.getPreferredSize());
        quantityLabel.setLocation(370, 45);
        add(quantityLabel);

        final JTextField quantityTextField = new JTextField("1");
        quantityTextField.setBackground(Color.WHITE);
        quantityTextField.setSize(50, 25);
        quantityTextField.setLocation(428, 40);
        add(quantityTextField);

        quantityTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                quantityTextField.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
            }


        });

        JLabel discountLabel = new JLabel("Discount per piece(%): ");
        discountLabel.setSize(discountLabel.getPreferredSize());
        discountLabel.setLocation(500, 45);
        add(discountLabel);

        final JTextField discountTextField = new JTextField("0");
        discountTextField.setBackground(Color.WHITE);
        discountTextField.setSize(50, 25);
        discountTextField.setLocation(635, 40);
        add(discountTextField);

        discountTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
            }

            @Override
            public void focusGained(FocusEvent e) {
                discountTextField.selectAll();
            }

        });

        JButton addButton = new JButton("Add");
        addButton.setSize(addButton.getPreferredSize());
        addButton.setLocation(705, 41);
        add(addButton);

        JLabel cartLabel = new JLabel("Cart: ");
        cartLabel.setSize(cartLabel.getPreferredSize());
        cartLabel.setLocation(10, 80);
        add(cartLabel);

        billTableModel = new DefaultTableModel(new Object[]{" X ", "ID", "Price (Rs.)", "Quantitiy", "Discount (%)", "Total (Rs.)"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        TableRowSorter<DefaultTableModel> billTableSorter = new TableRowSorter<>(billTableModel);
        billTableSorter.setSortsOnUpdates(true);
        @SuppressWarnings("unchecked")
        final Comparator<String> defaultComparator = (Comparator<String>) billTableSorter.getComparator(0);
        for (int i = 0, n = billTableModel.getColumnCount(); i < n; i++) {
            billTableSorter.setComparator(i, (object1, object2) -> {
                String string1 = object1.toString(), string2 = object2.toString();
                try {
                    return (int) ((Float.parseFloat(string1) - Float.parseFloat(string2)) * 100);
                } catch (NumberFormatException ex) {
                    return defaultComparator.compare(string1, string2);
                }
            });
        }

        billTable = new JTable(billTableModel);
        billTable.setRowSorter(billTableSorter);
        billTable.setRowHeight(25);
        billTable.getColumnModel().getColumn(0).setMinWidth(25);
        billTable.getColumnModel().getColumn(0).setMaxWidth(25);
        billTable.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        final JScrollPane billScrollPane = new JScrollPane(billTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        billScrollPane.getViewport().setBackground(Color.WHITE);
        billScrollPane.setSize(755, 310);
        billScrollPane.setLocation(10, 110);
        add(billScrollPane);

        totalQuantityLabel = new JLabel("Total Quantity:  0");
        totalQuantityLabel.setSize(totalQuantityLabel.getPreferredSize());
        totalQuantityLabel.setLocation(10, 438);
        add(totalQuantityLabel);

        totalPriceLabel = new JLabel("Total price:  Rs.0.00");
        totalPriceLabel.setSize(totalPriceLabel.getPreferredSize());
        totalPriceLabel.setLocation(200, 438);
        add(totalPriceLabel);

        totalDiscountLabel = new JLabel("Total discount:  Rs.0.00");
        totalDiscountLabel.setSize(totalDiscountLabel.getPreferredSize());
        totalDiscountLabel.setLocation(390, 438);
        add(totalDiscountLabel);

        grandTotalLabel = new JLabel("Grand total:  Rs.0.00");
        grandTotalLabel.setSize(grandTotalLabel.getPreferredSize());
        grandTotalLabel.setLocation(598, 438);
        add(grandTotalLabel);

        final JButton printButton = new JButton("Print");
        printButton.setSize(printButton.getPreferredSize());
        printButton.setLocation(StocksManager.getCenterAlignX(this, printButton), 468);
        add(printButton);

        idTextComponent.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_HOME || e.getKeyCode() == KeyEvent.VK_END || e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_CONTROL || e.getKeyCode() == KeyEvent.VK_PAGE_UP || e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                    return;
                }
                String idString = idTextComponent.getText();
                int caretPosition = idTextComponent.getCaretPosition();
                idTableComboBox.setPopupVisible(false);
                idTableComboBox.removeAllItems();
                if (idString.isEmpty()) {
                    return;
                }
                String idStringLower = idString.toLowerCase();
                for (int i = 1, n = StocksManager.databaseSheet.getLastRowNum(); i <= n; i++) {
                    HSSFRow row = StocksManager.databaseSheet.getRow(i);
                    if (row.getCell(0).getStringCellValue().toLowerCase().contains(idStringLower)) {
                        float price;
                        int stock;
                        try {
                            price = Float.parseFloat(row.getCell(3).getStringCellValue());
                            stock = Integer.parseInt(row.getCell(4).getStringCellValue());
                        } catch (NullPointerException | NumberFormatException ex) {
                            continue;
                        }
                        idTableComboBox.addItem(new Object[]{row.getCell(0), StocksManager.rsFormat.format(price), stock - getCartQuantity(row.getCell(0).getStringCellValue())});
                    }
                }
                if (idTableComboBox.getItemCount() == 0) {
                    idTableComboBox.addItem(new Object[]{"No results found!"});
                }
                idTableComboBox.setSelectedIndex(-1);
                idTextComponent.setText(idString);
                idTextComponent.setCaretPosition(caretPosition);
                idTableComboBox.setPopupVisible(true);
            }

        });

        addButton.addActionListener(e -> {
            if (billTable.getRowCount() == 14) {
                JOptionPane.showMessageDialog(null, "No more items can be added.", "Stocks Manager", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String idString = idTextComponent.getText();
            int quantity, discount;
            try {
                quantity = Integer.parseInt(quantityTextField.getText());
            } catch (NumberFormatException ex) {
                quantityTextField.requestFocusInWindow();
                JOptionPane.showMessageDialog(null, "Invalid QUANTITY input.", "Stocks Manager", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                discount = Integer.parseInt(discountTextField.getText());
                if (discount > 100) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                discountTextField.requestFocusInWindow();
                JOptionPane.showMessageDialog(null, "Invalid DISCOUNT input.", "Stocks Manager", JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (int i = 1, n = StocksManager.databaseSheet.getLastRowNum(); i <= n; i++) {
                HSSFRow row = StocksManager.databaseSheet.getRow(i);
                if (row.getCell(0).getStringCellValue().equals(idString)) {
                    float price;
                    try {
                        price = Float.parseFloat(row.getCell(3).getStringCellValue());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid PRICE value in database.", "Stocks Manager", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    int stock;
                    try {
                        stock = Integer.parseInt(row.getCell(4).getStringCellValue());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid QUANTITY value in database.", "Stocks Manager", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    int cartQuantity = getCartQuantity(idString);
                    if (stock < cartQuantity + quantity) {
                        JOptionPane.showMessageDialog(null, "Only " + Integer.toString(stock - cartQuantity) + " more stocks available.", "Stocks Manager", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    float totalPrice = price * quantity, totalDiscount = price * discount / 100 * quantity, grandTotal = totalPrice - totalDiscount;
                    billTableModel.addRow(new Object[]{"  X", row.getCell(0), StocksManager.rsFormat.format(price), quantity, discount, StocksManager.rsFormat.format(grandTotal)});
                    totalQuantityLabel.setText("Total Quantity:  " + (Integer.parseInt(totalQuantityLabel.getText().substring(17)) + quantity));
                    totalQuantityLabel.setSize(totalQuantityLabel.getPreferredSize());
                    totalPriceLabel.setText("Total price:  Rs." + StocksManager.rsFormat.format(Float.parseFloat(totalPriceLabel.getText().substring(17)) + totalPrice));
                    totalPriceLabel.setSize(totalPriceLabel.getPreferredSize());
                    totalDiscountLabel.setText("Total discount:  Rs." + StocksManager.rsFormat.format(Float.parseFloat(totalDiscountLabel.getText().substring(20)) + totalDiscount));
                    totalDiscountLabel.setSize(totalDiscountLabel.getPreferredSize());
                    grandTotalLabel.setText("Grand total:  Rs." + StocksManager.rsFormat.format(Float.parseFloat(grandTotalLabel.getText().substring(17)) + grandTotal));
                    grandTotalLabel.setSize(grandTotalLabel.getPreferredSize());
                    idTextComponent.dispatchEvent(new KeyEvent(idTextComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));
                    idTextComponent.requestFocusInWindow();
                    break;
                }
            }
        });

        billTable.getTableHeader().addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (billTable.getTableHeader().getHeaderRect(0).contains(e.getPoint())) {
                    clearCart();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }


        });

        billTable.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getX() <= 25) {
                    int rowIndex = billTable.rowAtPoint(e.getPoint()), quantity = (int) billTable.getValueAt(rowIndex, 3);
                    float price = Float.parseFloat(billTable.getValueAt(rowIndex, 2).toString()), totalPrice = price * quantity, totalDiscount = price * (int) billTable.getValueAt(rowIndex, 4) / 100 * quantity;
                    totalQuantityLabel.setText("Total Quantity:  " + (Integer.parseInt(totalQuantityLabel.getText().substring(17)) - quantity));
                    totalQuantityLabel.setSize(totalQuantityLabel.getPreferredSize());
                    totalPriceLabel.setText("Total price:  Rs." + StocksManager.rsFormat.format(Float.parseFloat(totalPriceLabel.getText().substring(17)) - totalPrice));
                    totalPriceLabel.setSize(totalPriceLabel.getPreferredSize());
                    totalDiscountLabel.setText("Total discount:  Rs." + StocksManager.rsFormat.format(Float.parseFloat(totalDiscountLabel.getText().substring(20)) - totalDiscount));
                    totalDiscountLabel.setSize(totalDiscountLabel.getPreferredSize());
                    grandTotalLabel.setText("Grand total:  Rs." + StocksManager.rsFormat.format(Float.parseFloat(grandTotalLabel.getText().substring(17)) - (totalPrice - totalDiscount)));
                    grandTotalLabel.setSize(grandTotalLabel.getPreferredSize());
                    billTableModel.removeRow(rowIndex);
                    idTextComponent.dispatchEvent(new KeyEvent(idTextComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));
                    idTableComboBox.setPopupVisible(false);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
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

        printButton.addActionListener(e -> {
            printButton.setEnabled(false);
            if (billTable.getRowCount() == 0) {
                printButton.setEnabled(true);
                return;
            }
            FileOutputStream databaseFileOutputStream, billsFileOutputStream;
            try {
                databaseFileOutputStream = new FileOutputStream("database.xls");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Close the DATABASE file before printing.", "Stocks Manager", JOptionPane.ERROR_MESSAGE);
                printButton.setEnabled(true);
                return;
            }
            try {
                billsFileOutputStream = new FileOutputStream("bills.xls");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Close the BILLS file before printing.", "Stocks Manager", JOptionPane.ERROR_MESSAGE);
                try {
                    StocksManager.databaseWorkbook.write(databaseFileOutputStream);
                    databaseFileOutputStream.close();
                } catch (IOException ignored) {
                }
                printButton.setEnabled(true);
                return;
            }

            int[][] values = new int[billTable.getRowCount()][2];
            for (int i = 0, n = billTable.getRowCount(); i < n; i++) {
                for (int j = 1, n1 = StocksManager.databaseSheet.getLastRowNum(); j <= n1; j++) {
                    HSSFRow row = StocksManager.databaseSheet.getRow(j);
                    if (row.getCell(0).getStringCellValue().equals(billTable.getValueAt(i, 1).toString())) {
                        int reducedValue = Integer.parseInt(row.getCell(4).getStringCellValue());
                        for (int k[] : values) {
                            if (k[0] == j) {
                                reducedValue = k[1];
                            }
                        }
                        reducedValue -= (int) billTable.getValueAt(i, 3);
                        if (reducedValue < 0) {
                            JOptionPane.showMessageDialog(null, "Only " + row.getCell(4).getStringCellValue() + " more stocks available in " + row.getCell(0).getStringCellValue() + ".", "Stocks Manager", JOptionPane.ERROR_MESSAGE);
                            try {
                                StocksManager.databaseWorkbook.write(databaseFileOutputStream);
                                databaseFileOutputStream.close();
                                StocksManager.billsWorkbook.write(billsFileOutputStream);
                                billsFileOutputStream.close();
                            } catch (IOException ignored) {
                            }
                            printButton.setEnabled(true);
                            return;
                        }
                        values[i][0] = j;
                        values[i][1] = reducedValue;
                    }
                }
            }
            for (int[] i : values) {
                StocksManager.databaseSheet.getRow(i[0]).getCell(4).setCellValue(Integer.toString(i[1]));
            }
            try {
                StocksManager.databaseWorkbook.write(databaseFileOutputStream);
                databaseFileOutputStream.close();
            } catch (IOException ignored) {
            }

            HSSFCell cell = StocksManager.billsWorkbook.getSheetAt(0).getRow(0).getCell(0);
            int billNo = (int) (cell.getNumericCellValue() + 1);
            cell.setCellValue(billNo);
            HSSFSheet billsSheet = StocksManager.billsWorkbook.getSheetAt(StocksManager.billsWorkbook.getNumberOfSheets() - 1);
            if (billNo % 4001 == 0) {
                billsSheet = StocksManager.billsWorkbook.createSheet();
                billsSheet.protectSheet("stocks_manager");
            }
            HSSFRow row = billsSheet.createRow(billsSheet.getLastRowNum() + 1);
            row.createCell(0).setCellValue("#" + billNo);
            String time = new SimpleDateFormat("d MMM y h:m a").format(new Date());
            row.createCell(1).setCellValue(time);
            row.createCell(2).setCellValue(totalQuantityLabel.getText().substring(17));
            row.createCell(3).setCellValue(totalPriceLabel.getText().substring(17));
            row.createCell(4).setCellValue(totalDiscountLabel.getText().substring(20));
            row.createCell(5).setCellValue(grandTotalLabel.getText().substring(17));
            row.createCell(6).setCellValue(paymentComboBox.getSelectedItem().toString());
            for (int i = 0, n = billTable.getRowCount(), j = billsSheet.getLastRowNum() + 1; i < n; i++) {
                row = billsSheet.createRow(j + i);
                row.createCell(0).setCellValue(billTable.getValueAt(i, 1).toString());
                row.createCell(1).setCellValue(billTable.getValueAt(i, 2).toString());
                row.createCell(2).setCellValue(billTable.getValueAt(i, 3).toString());
                row.createCell(3).setCellValue(billTable.getValueAt(i, 4).toString());
                row.createCell(4).setCellValue(billTable.getValueAt(i, 5).toString());
            }
            try {
                StocksManager.billsWorkbook.write(billsFileOutputStream);
                billsFileOutputStream.close();
            } catch (IOException ignored) {
            }

            String bill = StocksManager.billFormat, itemformat = bill.substring(bill.indexOf("<#itemStart#>") + 13, bill.indexOf("<#itemEnd#>"));
            bill = bill.replace("<#billNo#>", Integer.toString(billNo));
            bill = bill.replace("<#time#>", time);
            for (int i = 0, n = billTable.getRowCount(); i < n; i++) {
                String item = itemformat;
                item = item.replace("<#id#>", billTable.getValueAt(i, 1).toString());
                item = item.replace("<#price#>", billTable.getValueAt(i, 2).toString());
                item = item.replace("<#quantity#>", billTable.getValueAt(i, 3).toString());
                item = item.replace("<#discount#>", billTable.getValueAt(i, 4).toString());
                item = item.replace("<#total#>", billTable.getValueAt(i, 5).toString());
                bill = bill.substring(0, bill.indexOf("<#itemStart#>")) + item + bill.substring(bill.indexOf("<#itemStart#>"));
            }
            bill = bill.replace("<#itemStart#>", "");
            bill = bill.replace(itemformat, "");
            bill = bill.replace("<#itemEnd#>", "");
            bill = bill.replace("<#totalPrice#>", totalPriceLabel.getText().substring(17));
            bill = bill.replace("<#totalDiscount#>", totalDiscountLabel.getText().substring(20));
            bill = bill.replace("<#grandTotal#>", grandTotalLabel.getText().substring(17));
            bill = bill.replace("<#totalQuantity#>", totalQuantityLabel.getText().substring(17));
            bill = bill.replace("<#paymentType#>", paymentComboBox.getSelectedItem().toString());
            try {
                File tempBillFile = new File("tempbill.html");
                FileWriter fileWriter = new FileWriter(tempBillFile);
                fileWriter.write(bill);
                fileWriter.close();
                Desktop.getDesktop().print(tempBillFile);
                tempBillFile.deleteOnExit();
            } catch (IOException ignored) {
            }

            JOptionPane.showMessageDialog(null, "Bill has been printed.", "Stocks Manager", JOptionPane.WARNING_MESSAGE);
            clearCart();

            printButton.setEnabled(true);
        });

    }

    private int getCartQuantity(String id) {
        int quantity = 0;
        for (int i = 0, n = billTable.getRowCount(); i < n; i++) {
            if (billTable.getValueAt(i, 1).toString().equals(id)) {
                quantity += (int) billTable.getValueAt(i, 3);
            }
        }
        return quantity;
    }

    private void clearCart() {
        billTableModel.setRowCount(0);
        totalQuantityLabel.setText("Total Quantity:  0");
        totalQuantityLabel.setSize(totalQuantityLabel.getPreferredSize());
        totalPriceLabel.setText("Total price:  Rs.0.00");
        totalPriceLabel.setSize(totalPriceLabel.getPreferredSize());
        totalDiscountLabel.setText("Total discount:  Rs.0.00");
        totalDiscountLabel.setSize(totalDiscountLabel.getPreferredSize());
        grandTotalLabel.setText("Grand total:  Rs.0.00");
        grandTotalLabel.setSize(grandTotalLabel.getPreferredSize());
        idTextComponent.dispatchEvent(new KeyEvent(idTextComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));
        idTableComboBox.setPopupVisible(false);
    }

}
