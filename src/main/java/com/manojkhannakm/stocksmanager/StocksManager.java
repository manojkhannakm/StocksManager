package com.manojkhannakm.stocksmanager;

import com.manojkhannakm.stocksmanager.tabs.about.AboutTab;
import com.manojkhannakm.stocksmanager.tabs.bill.BillTab;
import com.manojkhannakm.stocksmanager.tabs.salereport.SaleReportTab;
import com.manojkhannakm.stocksmanager.tabs.search.SearchTab;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;

/**
 * @author Manoj Khanna
 */

public class StocksManager extends JFrame {

    public static HSSFWorkbook databaseWorkbook, billsWorkbook;
    public static HSSFSheet databaseSheet;
    public static String[] categories = new String[]{"Baby wear", "Boys wear", "Fashion accessories", "Girls wear", "Ladies Pant", "Ladies tops", "Lingerie", "Night wear", "Salwar materials", "Sarees"};
    public static String[][] subCategories = new String[][]{
            {"Baby set"},
            {"Boys T-shirt", "Boys Pant"},
            {"Bags", "Hair accessories", "Ear-ring", "Finger ring", "Saree pin", "Bindi", "Chain set", "Bangles", "Gift article"},
            {"Girls Frock", "Girls tops", "Girls Pant", "Girls panties"},
            {"Cotton pant", "Leggings-cotton", "Leggings-knitted"},
            {"Ladies tops", "Kurtis-cotton", "Kurtis-designer"},
            {"Bra-cotton", "Bra-branded", "Ladies Panties"},
            {"Nightwear set"},
            {"Salwar materials-cotton", "Salwar materials-synthetic", "Salwar materials-designer"},
            {"Sarees-cotton", "Sarees-silk cotton", "Sarees-Fancy", "Sarees-Designer"}
    };
    public static DecimalFormat rsFormat = new DecimalFormat("0.00");
    public static String billFormat;

    public StocksManager() {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(StocksManager.class.getResourceAsStream("/billformat.html"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStreamReader.close();
            billFormat = stringBuilder.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setTitle("Stocks Manager");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = (JPanel) getContentPane();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(800, 600));
        panel.setSize(panel.getPreferredSize());
        panel.setBackground(new Color(0, 185, 255));

        JLabel title = new JLabel("Stocks Manager");
        title.setForeground(Color.WHITE);
        try {
            title.setFont(Font.createFont(Font.TRUETYPE_FONT, StocksManager.class.getResourceAsStream("/pristina.ttf")).deriveFont(Font.BOLD, 27));
        } catch (FontFormatException | IOException ex) {
            ex.printStackTrace();
        }
        title.setSize(title.getPreferredSize());
        title.setSize(title.getWidth(), title.getHeight() + 15);
        title.setLocation(getCenterAlignX(panel, title), 10);
        panel.add(title);

        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(10, 60, panel.getWidth() - 20, panel.getHeight() - 70);

        final BillTab billTab = new BillTab();
        tabbedPane.add("Billing", billTab);

        SearchTab searchTab = new SearchTab();
        tabbedPane.add("Search", searchTab);

        SaleReportTab saleReportTab = new SaleReportTab();
        tabbedPane.add("Sale report", saleReportTab);

        AboutTab aboutTab = new AboutTab();
        tabbedPane.add("About", aboutTab);

        panel.add(tabbedPane);

        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);

        billTab.addComponents();

        setVisible(true);

        tabbedPane.repaint();
        searchTab.addComponents();
        saleReportTab.addComponents();
        aboutTab.addComponents();
    }

    public static int getCenterAlignX(Component parent, Component child) {
        return (parent.getWidth() - child.getWidth()) / 2;
    }

    public static void setCenterAlign(Component parent, Component child, int xOffset, int yOffset) {
        child.setLocation((parent.getWidth() - child.getWidth()) / 2 + xOffset, (parent.getHeight() - child.getHeight()) / 2 + yOffset);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
        }
        String[] componentKeys = new String[]{"Button.font", "CheckBox.font", "ComboBox.font", "Label.font", "List.font", "TabbedPane.font", "Table.font", "TextField.font"};
        Font font = new Font("Arial", Font.BOLD, 12);
        for (String string : componentKeys) {
            UIManager.put(string, font);
        }
        try {
            databaseWorkbook = new HSSFWorkbook(new FileInputStream("database.xls"));
            databaseSheet = databaseWorkbook.getSheetAt(0);
            for (Row row : databaseSheet) {
                for (Cell cell : row) {
                    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    }
                }
            }
            File billsFile = new File("bills.xls");
            if (billsFile.exists()) {
                try {
                    billsWorkbook = new HSSFWorkbook(new FileInputStream(billsFile));
                } catch (IOException ignored) {
                }
            } else {
                billsWorkbook = new HSSFWorkbook();
                HSSFSheet billsSheet = billsWorkbook.createSheet();
                billsSheet.protectSheet("stocks_manager");
                billsSheet.createRow(0).createCell(0).setCellValue(0);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "DATABASE file does not exist.", "Stocks Manager", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new StocksManager();
    }

}
