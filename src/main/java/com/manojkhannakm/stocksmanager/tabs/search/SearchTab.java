package com.manojkhannakm.stocksmanager.tabs.search;

import com.manojkhannakm.stocksmanager.StocksManager;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Manoj Khanna
 */

public class SearchTab extends JPanel {

    private ArrayList<JComponent> searchComponents = new ArrayList<>(),
            resultComponents = new ArrayList<>();

    public SearchTab() {
        setLayout(null);
        setBackground(Color.WHITE);
    }

    public void addComponents() {
        JLabel title = new JLabel("Search using: ");
        title.setSize(title.getPreferredSize());
        title.setLocation(10, 10);
        add(title);
        searchComponents.add(title);

        final JCheckBox idCheckBox = new JCheckBox("ID: ");
        idCheckBox.setBackground(Color.WHITE);
        idCheckBox.setSize(idCheckBox.getPreferredSize());
        idCheckBox.setLocation(131, 50);
        add(idCheckBox);
        searchComponents.add(idCheckBox);

        final JTextField idTextField = new JTextField();
        idTextField.setBackground(Color.WHITE);
        idTextField.setSize(250, 25);
        idTextField.setLocation(387, 50);
        add(idTextField);
        searchComponents.add(idTextField);

        final JCheckBox priceCheckBox = new JCheckBox("Price range: ");
        priceCheckBox.setBackground(Color.WHITE);
        priceCheckBox.setSize(priceCheckBox.getPreferredSize());
        priceCheckBox.setLocation(131, 100);
        add(priceCheckBox);
        searchComponents.add(priceCheckBox);

        final JLabel priceFromLabel = new JLabel("From: ");
        priceFromLabel.setSize(priceFromLabel.getPreferredSize());
        priceFromLabel.setLocation(387, 105);
        add(priceFromLabel);
        searchComponents.add(priceFromLabel);

        final JTextField priceFromTextField = new JTextField();
        priceFromTextField.setBackground(Color.WHITE);
        priceFromTextField.setSize(86, 25);
        priceFromTextField.setLocation(429, 100);
        add(priceFromTextField);
        searchComponents.add(priceFromTextField);

        final JLabel priceToLabel = new JLabel("To: ");
        priceToLabel.setSize(priceToLabel.getPreferredSize());
        priceToLabel.setLocation(523, 105);
        add(priceToLabel);
        searchComponents.add(priceToLabel);

        final JTextField priceToTextField = new JTextField();
        priceToTextField.setBackground(Color.WHITE);
        priceToTextField.setSize(87, 25);
        priceToTextField.setLocation(550, 100);
        add(priceToTextField);
        searchComponents.add(priceToTextField);

        final JCheckBox catCheckBox = new JCheckBox("Category: ");
        catCheckBox.setBackground(Color.WHITE);
        catCheckBox.setSize(catCheckBox.getPreferredSize());
        catCheckBox.setLocation(131, 150);
        add(catCheckBox);
        searchComponents.add(catCheckBox);

        final JComboBox<String> catComboBox = new JComboBox<>(StocksManager.categories);
        catComboBox.setBackground(Color.WHITE);
        catComboBox.setSize(110, 25);
        catComboBox.setLocation(387, 150);
        add(catComboBox);
        searchComponents.add(catComboBox);

        final JLabel catArrowLabel = new JLabel(" > ");
        catArrowLabel.setSize(catArrowLabel.getPreferredSize());
        catArrowLabel.setLocation(507, 155);
        add(catArrowLabel);
        searchComponents.add(catArrowLabel);

        final JComboBox<String> subCatComboBox = new JComboBox<>(StocksManager.subCategories[0]);
        subCatComboBox.setBackground(Color.WHITE);
        subCatComboBox.setSize(110, 25);
        subCatComboBox.setLocation(527, 150);
        add(subCatComboBox);
        searchComponents.add(subCatComboBox);

        catComboBox.addActionListener(e -> subCatComboBox.setModel(new JComboBox<>(StocksManager.subCategories[catComboBox.getSelectedIndex()]).getModel()));

        final JCheckBox placeCheckBox = new JCheckBox("Place of purchase: ");
        placeCheckBox.setBackground(Color.WHITE);
        placeCheckBox.setSize(placeCheckBox.getPreferredSize());
        placeCheckBox.setLocation(131, 200);
        add(placeCheckBox);
        searchComponents.add(placeCheckBox);

        final JTextField placeTextField = new JTextField();
        placeTextField.setBackground(Color.WHITE);
        placeTextField.setSize(250, 25);
        placeTextField.setLocation(387, 200);
        add(placeTextField);
        searchComponents.add(placeTextField);

        final JCheckBox dateCheckBox = new JCheckBox("Date of purchase (D/M/YYYY): ");
        dateCheckBox.setBackground(Color.WHITE);
        dateCheckBox.setSize(dateCheckBox.getPreferredSize());
        dateCheckBox.setLocation(131, 250);
        add(dateCheckBox);
        searchComponents.add(dateCheckBox);

        final JLabel dateFromLabel = new JLabel("From: ");
        dateFromLabel.setSize(dateFromLabel.getPreferredSize());
        dateFromLabel.setLocation(387, 255);
        add(dateFromLabel);
        searchComponents.add(dateFromLabel);

        final JTextField dateFromTextField = new JTextField();
        dateFromTextField.setBackground(Color.WHITE);
        dateFromTextField.setSize(86, 25);
        dateFromTextField.setLocation(429, 250);
        add(dateFromTextField);
        searchComponents.add(dateFromTextField);

        final JLabel dateToLabel = new JLabel("To: ");
        dateToLabel.setSize(dateToLabel.getPreferredSize());
        dateToLabel.setLocation(523, 255);
        add(dateToLabel);
        searchComponents.add(dateToLabel);

        final JTextField dateToTextField = new JTextField();
        dateToTextField.setBackground(Color.WHITE);
        dateToTextField.setSize(87, 25);
        dateToTextField.setLocation(550, 250);
        add(dateToTextField);
        searchComponents.add(dateToTextField);

        final JCheckBox sizeCheckBox = new JCheckBox("Size: ");
        sizeCheckBox.setBackground(Color.WHITE);
        sizeCheckBox.setSize(sizeCheckBox.getPreferredSize());
        sizeCheckBox.setLocation(131, 300);
        add(sizeCheckBox);
        searchComponents.add(sizeCheckBox);

        final JTextField sizeTextField = new JTextField();
        sizeTextField.setBackground(Color.WHITE);
        sizeTextField.setSize(250, 25);
        sizeTextField.setLocation(387, 300);
        add(sizeTextField);
        searchComponents.add(sizeTextField);

        final JCheckBox colorCheckBox = new JCheckBox("Color: ");
        colorCheckBox.setBackground(Color.WHITE);
        colorCheckBox.setSize(colorCheckBox.getPreferredSize());
        colorCheckBox.setLocation(131, 350);
        add(colorCheckBox);
        searchComponents.add(colorCheckBox);

        final JTextField colorTextField = new JTextField();
        colorTextField.setBackground(Color.WHITE);
        colorTextField.setSize(250, 25);
        colorTextField.setLocation(387, 350);
        add(colorTextField);
        searchComponents.add(colorTextField);

        JLabel resultLabel = new JLabel("Results: ");
        resultLabel.setSize(resultLabel.getPreferredSize());
        resultLabel.setLocation(785, 10);
        add(resultLabel);
        resultComponents.add(resultLabel);

        JButton backButton = new JButton("Back");
        backButton.setSize(backButton.getPreferredSize());
        backButton.setLocation(1480, 7);
        add(backButton);
        resultComponents.add(backButton);

        backButton.addActionListener(e -> new Thread(() -> {
            int i = 1000, j = 0;
            while (j < 775) {
                for (Component component : searchComponents) {
                    Point point = component.getLocation();
                    component.setLocation(point.x + i / 25, point.y);
                }
                for (Component component : resultComponents) {
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

        final DefaultTableModel resultTableModel = new DefaultTableModel(new Object[]{"ID", "Category", "Subcategory", "Price (Rs.)", "Stock", "Size", "Color", "Place of purchase", "Date of purchase"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        TableRowSorter<DefaultTableModel> resultTableSorter = new TableRowSorter<>(resultTableModel);
        resultTableSorter.setSortsOnUpdates(true);
        @SuppressWarnings("unchecked")
        final Comparator<String> defaultComparator = (Comparator<String>) resultTableSorter.getComparator(0);
        for (int i = 0, n = resultTableModel.getColumnCount(); i < n; i++) {
            resultTableSorter.setComparator(i, (object1, object2) -> {
                String string1 = object1.toString(), string2 = object2.toString();
                try {
                    return (int) ((Float.parseFloat(string1) - Float.parseFloat(string2)) * 100);
                } catch (NumberFormatException ex) {
                    return defaultComparator.compare(string1, string2);
                }
            });
        }

        final JTable resultTable = new JTable(resultTableModel);
        resultTable.setRowSorter(resultTableSorter);
        resultTable.setRowHeight(25);
        resultTable.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        JScrollPane resultScrollPane = new JScrollPane(resultTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultScrollPane.getViewport().setBackground(Color.WHITE);
        resultScrollPane.setSize(755, 450);
        resultScrollPane.setLocation(785, 40);
        add(resultScrollPane);
        resultComponents.add(resultScrollPane);

        final JButton searchButton = new JButton("Search");
        searchButton.setSize(searchButton.getPreferredSize());
        searchButton.setLocation(StocksManager.getCenterAlignX(this, searchButton), 425);
        add(searchButton);
        searchComponents.add(searchButton);

        searchButton.addActionListener(e -> {
            int noOfConditions = 0;
            if (idCheckBox.isSelected()) {
                noOfConditions++;
            }
            float fromPrice = 0, toPrice = 0;
            if (priceCheckBox.isSelected()) {
                try {
                    fromPrice = Float.parseFloat(priceFromTextField.getText());
                    toPrice = Float.parseFloat(priceToTextField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid PRICE input.", "Stocks Manager", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                noOfConditions++;
            }
            if (catCheckBox.isSelected()) {
                noOfConditions++;
            }
            if (placeCheckBox.isSelected()) {
                noOfConditions++;
            }
            LocalDate fromDate = null, toDate = null;
            if (dateCheckBox.isSelected()) {
                try {
                    fromDate = LocalDate.parse(dateFromTextField.getText(), DateTimeFormat.forPattern("d/M/y"));
                    toDate = LocalDate.parse(dateToTextField.getText(), DateTimeFormat.forPattern("d/M/y"));
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid DATE input.", "Stocks Manager", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                noOfConditions++;
            }
            if (sizeCheckBox.isSelected()) {
                noOfConditions++;
            }
            if (colorCheckBox.isSelected()) {
                noOfConditions++;
            }

            String id = idTextField.getText().toLowerCase(), place = placeTextField.getText().toLowerCase(), size = sizeTextField.getText().toLowerCase(), color = colorTextField.getText().toLowerCase();
            resultTableModel.setRowCount(0);
            for (int i = 1, n = StocksManager.databaseSheet.getLastRowNum(); i <= n; i++) {
                HSSFRow row = StocksManager.databaseSheet.getRow(i);
                int noOfMatchedConditions = 0;
                if (idCheckBox.isSelected()) {
                    if (!row.getCell(0).getStringCellValue().toLowerCase().contains(id)) {
                        continue;
                    }
                    noOfMatchedConditions++;
                }
                float price;
                try {
                    price = Float.parseFloat(row.getCell(3).getStringCellValue());
                } catch (NullPointerException | NumberFormatException ex) {
                    price = -1.0f;
                }
                if (priceCheckBox.isSelected()) {
                    if (price == -1.0f || !(fromPrice <= price && price <= toPrice)) {
                        continue;
                    }
                    noOfMatchedConditions++;
                }
                if (catCheckBox.isSelected()) {
                    if (!(row.getCell(1).getStringCellValue().equals(catComboBox.getSelectedItem()) && row.getCell(2).getStringCellValue().equals(subCatComboBox.getSelectedItem()))) {
                        continue;
                    }
                    noOfMatchedConditions++;
                }
                if (placeCheckBox.isSelected()) {
                    if (!row.getCell(7).getStringCellValue().toLowerCase().contains(place)) {
                        continue;
                    }
                    noOfMatchedConditions++;
                }
                if (dateCheckBox.isSelected()) {
                    LocalDate itemDate = LocalDate.parse(row.getCell(8).getStringCellValue(), DateTimeFormat.forPattern("d/M/y"));
                    //noinspection ConstantConditions
                    if (!(itemDate.isEqual(fromDate) || itemDate.isEqual(toDate) || (itemDate.isAfter(fromDate) && itemDate.isBefore(toDate)))) {
                        continue;
                    }
                    noOfMatchedConditions++;
                }
                if (sizeCheckBox.isSelected()) {
                    if (!row.getCell(5).getStringCellValue().toLowerCase().contains(size)) {
                        continue;
                    }
                    noOfMatchedConditions++;
                }
                if (colorCheckBox.isSelected()) {
                    if (!row.getCell(6).getStringCellValue().toLowerCase().contains(color)) {
                        continue;
                    }
                    noOfMatchedConditions++;
                }
                if (noOfConditions == noOfMatchedConditions) {
                    resultTableModel.addRow(new Object[]{row.getCell(0), row.getCell(1), row.getCell(2), price == -1.0f ? "" : StocksManager.rsFormat.format(price), row.getCell(4), row.getCell(5), row.getCell(6), row.getCell(7), row.getCell(8)});
                }
            }
            if (resultTable.getRowCount() == 0) {
                resultTableModel.addRow(new Object[]{"No results found !"});
            }
            new Thread(() -> {
                int i = 1000, j = 0;
                while (j < 775) {
                    for (Component component : searchComponents) {
                        Point point = component.getLocation();
                        component.setLocation(point.x - i / 25, point.y);
                    }
                    for (Component component : resultComponents) {
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
        });
    }

}
