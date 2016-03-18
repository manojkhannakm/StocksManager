package com.manojkhannakm.stocksmanager.tabs.salereport;

import com.manojkhannakm.stocksmanager.StocksManager;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Manoj Khanna
 */

public class SaleReportTab extends JPanel {

    private ArrayList<Component> saleReportComponents = new ArrayList<>(),
            billComponents = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> billsRowIndexes;

    public SaleReportTab() {
        setLayout(null);
        setBackground(Color.WHITE);
    }

    public void addComponents() {
        JLabel title = new JLabel("Sale report: ");
        title.setSize(title.getPreferredSize());
        title.setLocation(10, 10);
        add(title);
        saleReportComponents.add(title);

        JLabel dateLabel = new JLabel("Date of purchase (D/M/YYYY): ");
        dateLabel.setSize(dateLabel.getPreferredSize());
        dateLabel.setLocation(100, 40);
        add(dateLabel);
        saleReportComponents.add(dateLabel);

        JLabel dateFromLabel = new JLabel("From: ");
        dateFromLabel.setSize(dateFromLabel.getPreferredSize());
        dateFromLabel.setLocation(300, 40);
        add(dateFromLabel);
        saleReportComponents.add(dateFromLabel);

        final JTextField dateFromTextField = new JTextField();
        dateFromTextField.setBackground(Color.WHITE);
        dateFromTextField.setSize(86, 25);
        dateFromTextField.setLocation(342, 35);
        add(dateFromTextField);
        saleReportComponents.add(dateFromTextField);

        JLabel dateToLabel = new JLabel("To: ");
        dateToLabel.setSize(dateToLabel.getPreferredSize());
        dateToLabel.setLocation(438, 40);
        add(dateToLabel);
        saleReportComponents.add(dateToLabel);

        final JTextField dateToTextField = new JTextField();
        dateToTextField.setBackground(Color.WHITE);
        dateToTextField.setSize(87, 25);
        dateToTextField.setLocation(465, 35);
        add(dateToTextField);
        saleReportComponents.add(dateToTextField);

        JButton searchButton = new JButton("Search");
        searchButton.setSize(searchButton.getPreferredSize());
        searchButton.setLocation(590, 36);
        add(searchButton);
        saleReportComponents.add(searchButton);

        final DefaultTableModel saleReportTableModel = new DefaultTableModel(new Object[]{"Bill no.", "Time", "Total quantity", "Grand total (Rs.)", "Payment type"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        TableRowSorter<DefaultTableModel> saleReportTableSorter = new TableRowSorter<>(saleReportTableModel);
        saleReportTableSorter.setSortsOnUpdates(true);
        @SuppressWarnings("unchecked")
        final Comparator<String> defaultComparator = (Comparator<String>) saleReportTableSorter.getComparator(0);
        for (int i = 0, n = saleReportTableModel.getColumnCount(); i < n; i++) {
            saleReportTableSorter.setComparator(i, (object1, object2) -> {
                String string1 = object1.toString(), string2 = object2.toString();
                try {
                    return (int) ((Float.parseFloat(string1) - Float.parseFloat(string2)) * 100);
                } catch (NumberFormatException ex) {
                    return defaultComparator.compare(string1, string2);
                }
            });
        }

        final JTable saleReportTable = new JTable(saleReportTableModel);
        saleReportTable.setRowSorter(saleReportTableSorter);
        saleReportTable.setRowHeight(25);
        saleReportTable.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        JScrollPane saleReportScrollPane = new JScrollPane(saleReportTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        saleReportScrollPane.getViewport().setBackground(Color.WHITE);
        saleReportScrollPane.setSize(755, 375);
        saleReportScrollPane.setLocation(10, 75);
        add(saleReportScrollPane);
        saleReportComponents.add(saleReportScrollPane);

        final JLabel netTotalQuantityLabel = new JLabel("Total quantity:  0");
        netTotalQuantityLabel.setSize(netTotalQuantityLabel.getPreferredSize());
        netTotalQuantityLabel.setLocation(170, 470);
        add(netTotalQuantityLabel);
        saleReportComponents.add(netTotalQuantityLabel);

        final JLabel netTotalPriceLabel = new JLabel("Total price:  Rs.0.00");
        netTotalPriceLabel.setSize(netTotalPriceLabel.getPreferredSize());
        netTotalPriceLabel.setLocation(430, 470);
        add(netTotalPriceLabel);
        saleReportComponents.add(netTotalPriceLabel);

        final JLabel billNoLabel = new JLabel("Bill no:  ");
        billNoLabel.setSize(billNoLabel.getPreferredSize());
        billNoLabel.setLocation(785, 10);
        add(billNoLabel);
        billComponents.add(billNoLabel);

        final JLabel paymentLabel = new JLabel("Payment type:  ");
        paymentLabel.setSize(paymentLabel.getPreferredSize());
        paymentLabel.setLocation(985, 10);
        add(paymentLabel);
        billComponents.add(paymentLabel);

        final JLabel timeLabel = new JLabel("Time:  ");
        timeLabel.setSize(timeLabel.getPreferredSize());
        timeLabel.setLocation(1225, 10);
        add(timeLabel);
        billComponents.add(timeLabel);

        JButton backButton = new JButton("Back");
        backButton.setSize(backButton.getPreferredSize());
        backButton.setLocation(1480, 7);
        add(backButton);
        billComponents.add(backButton);

        JLabel cartLabel = new JLabel("Cart: ");
        cartLabel.setSize(cartLabel.getPreferredSize());
        cartLabel.setLocation(785, 45);
        add(cartLabel);
        billComponents.add(cartLabel);

        final DefaultTableModel billTableModel = new DefaultTableModel(new Object[]{"ID", "Price (Rs.)", "Quantitiy", "Discount (%)", "Total (Rs.)"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        TableRowSorter<DefaultTableModel> billTableSorter = new TableRowSorter<>(billTableModel);
        billTableSorter.setSortsOnUpdates(true);
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

        final JTable billTable = new JTable(billTableModel);
        billTable.setRowSorter(billTableSorter);
        billTable.setRowHeight(25);
        billTable.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        final JScrollPane billScrollPane = new JScrollPane(billTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        billScrollPane.getViewport().setBackground(Color.WHITE);
        billScrollPane.setSize(755, 345);
        billScrollPane.setLocation(785, 75);
        add(billScrollPane);
        billComponents.add(billScrollPane);

        final JLabel totalQuantityLabel = new JLabel("Total Quantity:  0");
        totalQuantityLabel.setSize(totalQuantityLabel.getPreferredSize());
        totalQuantityLabel.setLocation(785, 438);
        add(totalQuantityLabel);
        billComponents.add(totalQuantityLabel);

        final JLabel totalPriceLabel = new JLabel("Total price:  Rs.0.00");
        totalPriceLabel.setSize(totalPriceLabel.getPreferredSize());
        totalPriceLabel.setLocation(985, 438);
        add(totalPriceLabel);
        billComponents.add(totalPriceLabel);

        final JLabel totalDiscountLabel = new JLabel("Total discount:  Rs.0.00");
        totalDiscountLabel.setSize(totalDiscountLabel.getPreferredSize());
        totalDiscountLabel.setLocation(1165, 438);
        add(totalDiscountLabel);
        billComponents.add(totalDiscountLabel);

        final JLabel grandTotalLabel = new JLabel("Grand total:  Rs.0.00");
        grandTotalLabel.setSize(grandTotalLabel.getPreferredSize());
        grandTotalLabel.setLocation(1373, 438);
        add(grandTotalLabel);
        billComponents.add(grandTotalLabel);

        final JButton printButton = new JButton("Print again");
        printButton.setSize(printButton.getPreferredSize());
        printButton.setLocation(StocksManager.getCenterAlignX(this, printButton) + 775, 468);
        add(printButton);
        billComponents.add(printButton);

        searchButton.addActionListener(e -> {
            LocalDate fromDate = null, toDate = null;
            if (!dateFromTextField.getText().isEmpty() || !dateToTextField.getText().isEmpty()) {
                try {
                    fromDate = LocalDate.parse(dateFromTextField.getText(), DateTimeFormat.forPattern("d/M/y"));
                    toDate = LocalDate.parse(dateToTextField.getText(), DateTimeFormat.forPattern("d/M/y"));
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid DATE input.", "Stocks Manager", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            saleReportTableModel.setRowCount(0);
            int totalQuantity = 0;
            float totalPrice = 0;
            billsRowIndexes = new ArrayList<>();
            if (fromDate == null) {
                for (int i = 0, n = StocksManager.billsWorkbook.getNumberOfSheets(); i < n; i++) {
                    HSSFSheet billsSheet = StocksManager.billsWorkbook.getSheetAt(i);
                    for (int j = 1, n1 = billsSheet.getLastRowNum(); j <= n1; j++) {
                        HSSFRow row = billsSheet.getRow(j);
                        if (row.getCell(0).getStringCellValue().startsWith("#")) {
                            saleReportTableModel.addRow(new Object[]{row.getCell(0).getStringCellValue().substring(1), row.getCell(1), row.getCell(2), row.getCell(5), row.getCell(6)});
                            totalQuantity += Integer.parseInt(row.getCell(2).getStringCellValue());
                            totalPrice += Float.parseFloat(row.getCell(5).getStringCellValue());
                            ArrayList<Integer> billsRowIndex = new ArrayList<>();
                            billsRowIndex.add(i);
                            billsRowIndex.add(j);
                            billsRowIndexes.add(billsRowIndex);
                        }
                    }
                }
            } else {
                for (int i = 0, n = StocksManager.billsWorkbook.getNumberOfSheets(); i < n; i++) {
                    HSSFSheet billsSheet = StocksManager.billsWorkbook.getSheetAt(i);
                    for (int j = 1, n1 = billsSheet.getLastRowNum(); j <= n1; j++) {
                        HSSFRow row = billsSheet.getRow(j);
                        if (row.getCell(0).getStringCellValue().startsWith("#")) {
                            LocalDate billDate = LocalDate.parse(row.getCell(1).getStringCellValue().replaceFirst(" \\d+:\\d+ \\w+", ""), DateTimeFormat.forPattern("d MMM y"));
                            if (billDate.isEqual(fromDate) || billDate.isEqual(toDate) || (billDate.isAfter(fromDate) && billDate.isBefore(toDate))) {
                                saleReportTableModel.addRow(new Object[]{row.getCell(0).getStringCellValue().substring(1), row.getCell(1), row.getCell(2), row.getCell(5), row.getCell(6)});
                                totalQuantity += Integer.parseInt(row.getCell(2).getStringCellValue());
                                totalPrice += Float.parseFloat(row.getCell(5).getStringCellValue());
                                ArrayList<Integer> billsRowIndex = new ArrayList<>();
                                billsRowIndex.add(i);
                                billsRowIndex.add(j);
                                billsRowIndexes.add(billsRowIndex);
                            }
                        }
                    }
                }
            }
            netTotalQuantityLabel.setText("Total quantity:  " + totalQuantity);
            netTotalQuantityLabel.setSize(netTotalQuantityLabel.getPreferredSize());
            netTotalPriceLabel.setText("Total price:  Rs." + StocksManager.rsFormat.format(totalPrice));
            netTotalPriceLabel.setSize(netTotalPriceLabel.getPreferredSize());
            if (saleReportTable.getRowCount() == 0) {
                saleReportTableModel.addRow(new Object[]{"No results found !"});
            }
        });

        saleReportTable.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    if (billsRowIndexes.isEmpty()) {
                        return;
                    }
                    int rowIndex = saleReportTable.rowAtPoint(e.getPoint());
                    String billNo = saleReportTable.getValueAt(rowIndex, 0).toString();
                    billNoLabel.setText("Bill no:  " + billNo);
                    billNoLabel.setSize(billNoLabel.getPreferredSize());
                    paymentLabel.setText("Payment type:  " + saleReportTable.getValueAt(rowIndex, 4));
                    paymentLabel.setSize(paymentLabel.getPreferredSize());
                    timeLabel.setText("Time:  " + saleReportTable.getValueAt(rowIndex, 1));
                    timeLabel.setSize(timeLabel.getPreferredSize());
                    HSSFSheet billsSheet = StocksManager.billsWorkbook.getSheetAt(billsRowIndexes.get(rowIndex).get(0));
                    HSSFRow row = billsSheet.getRow(billsRowIndexes.get(rowIndex).get(1));
                    if (row.getCell(0).getStringCellValue().equals("#" + billNo)) {
                        totalQuantityLabel.setText("Total Quantity:  " + row.getCell(2).getStringCellValue());
                        totalQuantityLabel.setSize(totalQuantityLabel.getPreferredSize());
                        totalPriceLabel.setText("Total price:  Rs." + row.getCell(3).getStringCellValue());
                        totalPriceLabel.setSize(totalPriceLabel.getPreferredSize());
                        totalDiscountLabel.setText("Total discount:  Rs." + row.getCell(4).getStringCellValue());
                        totalDiscountLabel.setSize(totalDiscountLabel.getPreferredSize());
                        grandTotalLabel.setText("Grand total:  Rs." + row.getCell(5).getStringCellValue());
                        grandTotalLabel.setSize(grandTotalLabel.getPreferredSize());
                        billTableModel.setRowCount(0);
                        for (int k = billsRowIndexes.get(rowIndex).get(1) + 1, n2 = billsSheet.getLastRowNum(); k <= n2; k++) {
                            row = billsSheet.getRow(k);
                            if (row.getCell(0).getStringCellValue().startsWith("#")) {
                                break;
                            }
                            billTableModel.addRow(new Object[]{row.getCell(0), row.getCell(1), row.getCell(2), row.getCell(3), row.getCell(4)});
                        }
                    }
                    new Thread(() -> {
                        int i = 1000, j = 0;
                        while (j < 775) {
                            for (Component component : saleReportComponents) {
                                Point point = component.getLocation();
                                component.setLocation(point.x - i / 25, point.y);
                            }
                            for (Component component : billComponents) {
                                Point point = component.getLocation();
                                component.setLocation(point.x - i / 25, point.y);
                            }
                            repaint();
                            j += i / 25;
                            i -= 25;
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException ignored) {
                            }
                        }
                    }).start();
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

        backButton.addActionListener(e -> new Thread(() -> {
            int i = 1000, j = 0;
            while (j < 775) {
                for (Component component : saleReportComponents) {
                    Point point = component.getLocation();
                    component.setLocation(point.x + i / 25, point.y);
                }
                for (Component component : billComponents) {
                    Point point = component.getLocation();
                    component.setLocation(point.x + i / 25, point.y);
                }
                repaint();
                j += i / 25;
                i -= 25;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {
                }
            }
        }).start());

        printButton.addActionListener(e -> {
            printButton.setEnabled(false);
            String bill = StocksManager.billFormat, itemformat = bill.substring(bill.indexOf("<#itemStart#>") + 13, bill.indexOf("<#itemEnd#>"));
            bill = bill.replace("<#billNo#>", billNoLabel.getText().substring(10));
            bill = bill.replace("<#time#>", timeLabel.getText().substring(7));
            for (int i = 0, n = billTable.getRowCount(); i < n; i++) {
                String item = itemformat;
                item = item.replace("<#id#>", billTable.getValueAt(i, 0).toString());
                item = item.replace("<#price#>", billTable.getValueAt(i, 1).toString());
                item = item.replace("<#quantity#>", billTable.getValueAt(i, 2).toString());
                item = item.replace("<#discount#>", billTable.getValueAt(i, 3).toString());
                item = item.replace("<#total#>", billTable.getValueAt(i, 4).toString());
                bill = bill.substring(0, bill.indexOf("<#itemStart#>")) + item + bill.substring(bill.indexOf("<#itemStart#>"));
            }
            bill = bill.replace("<#itemStart#>", "");
            bill = bill.replace(itemformat, "");
            bill = bill.replace("<#itemEnd#>", "");
            bill = bill.replace("<#totalPrice#>", totalPriceLabel.getText().substring(17));
            bill = bill.replace("<#totalDiscount#>", totalDiscountLabel.getText().substring(20));
            bill = bill.replace("<#grandTotal#>", grandTotalLabel.getText().substring(17));
            bill = bill.replace("<#totalQuantity#>", totalQuantityLabel.getText().substring(17));
            bill = bill.replace("<#paymentType#>", paymentLabel.getText().substring(15));
            try {
                File tempBillFile = new File("tempbill.html");
                FileWriter fileWriter = new FileWriter(tempBillFile);
                fileWriter.write(bill);
                fileWriter.close();
                Desktop.getDesktop().print(tempBillFile);
                tempBillFile.deleteOnExit();
            } catch (IOException ignored) {
            }
            printButton.setEnabled(true);
        });
    }

}
