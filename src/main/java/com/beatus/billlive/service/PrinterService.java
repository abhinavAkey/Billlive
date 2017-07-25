package com.beatus.billlive.service;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.beatus.billlive.domain.model.Receipt;
import com.beatus.billlive.utils.PrinterOptions;

@Service
@Component("printerService")
public class PrinterService implements Printable {

    public List<String> getPrinters(){

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printServices[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);

        List<String> printerList = new ArrayList<String>();
        for(PrintService printerService: printServices){
            printerList.add( printerService.getName());
        }

        return printerList;
    }


    public void printString(String printerName, String text) {

        // find the printService of name printerName
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);
        PrintService service = findPrintService(printerName, printService);

        DocPrintJob job = service.createPrintJob();

        try {

            byte[] bytes;

            // important for umlaut chars
            bytes = text.getBytes("CP437");

            Doc doc = new SimpleDoc(bytes, flavor, null);


            job.print(doc, null);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void printBytes(String printerName, byte[] bytes) {

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);
        PrintService service = findPrintService(printerName, printService);

        DocPrintJob job = service.createPrintJob();

        try {

            Doc doc = new SimpleDoc(bytes, flavor, null);

            job.print(doc, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PrintService findPrintService(String printerName,
            PrintService[] services) {
        for (PrintService service : services) {
            if (service.getName().equalsIgnoreCase(printerName)) {
                return service;
            }
        }

        return null;
    }


	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {	
		if (pageIndex > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }
		
		Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        /* Now we perform our rendering */

        graphics.setFont(new Font("Roman", 0, 8));
        graphics.drawString("Hello world !", 0, 10);

        return PAGE_EXISTS;
	}


	public void printDocument(Receipt receipt) {
		PrinterOptions p=new PrinterOptions();

        p.resetAll();
        p.initialize();
        p.feedBack((byte)2);
        p.color(0);
        p.alignCenter();
        p.setText("The Dum Dum Name");
        p.newLine();
        p.setText("Restaurant Dining");
        p.newLine();
        p.addLineSeperator();
        p.setText("Bling Bling");
        p.newLine();
        p.addLineSeperator();
        p.newLine();

        p.alignLeft();
        p.setText("POD No \t: 2001 \tTable \t: E511");
        p.newLine();              

        p.setText("Res Date \t: " +  "01/01/1801 22:59");

        p.newLine();
        p.setText("Session \t: Evening Session");
        p.newLine();
        p.setText("Staff \t: Bum Dale");
        p.newLine();
        p.addLineSeperator();
        p.newLine();
        p.alignCenter();
        p.setText(" - Some Items - ");
        p.newLine();
        p.alignLeft();
        p.addLineSeperator();

        p.newLine();

        p.setText("No \tItem\t\tUnit\tQty");
        p.newLine();
        p.addLineSeperator();
        p.setText("1" + "\t" + "Aliens Everywhere" + "\t" +  "Rats" + "\t" + "500");
        p.setText("1" + "\t" + "Aliens Everywhere" + "\t" +  "Rats" + "\t" + "500");
        p.setText("1" + "\t" + "Aliens Everywhere" + "\t" +  "Rats" + "\t" + "500");
        p.setText("1" + "\t" + "Aliens Everywhere" + "\t" +  "Rats" + "\t" + "500");
        p.setText("1" + "\t" + "Aliens Everywhere" + "\t" +  "Rats" + "\t" + "500");
        p.setText("1" + "\t" + "Aliens Everywhere" + "\t" +  "Rats" + "\t" + "500");

        p.addLineSeperator();
        p.feed((byte)3);
        p.finit();
        
        PrinterService printerService = new PrinterService();

        System.out.println(printerService.getPrinters());

        //print some stuff. Change the printer name to your thermal printer name.
        printerService.printString("CC1-12CCR-KC", "\n\n testing testing 1 2 3eeeee \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        // cut that paper!
        byte[] cutP = new byte[] { 0x1d, 'V', 1 };

        printerService.printBytes("CC1-12CCR-KC", p.finalCommandSet().getBytes());
		
	}
	
	
}
