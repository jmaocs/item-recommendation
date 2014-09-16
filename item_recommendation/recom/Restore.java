import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;


public class Restore {

    public static void main(String[] args) throws IOException {
        BufferedReader in;
        PrintWriter out;
        String line;
        List<String> freqItems = new LinkedList<String>();
        // read in frequent items
        in = new BufferedReader(
                new FileReader(new File("/data/high_freq_items.csv")));
        while ((line = in.readLine()) != null) {
            freqItems.add(line.split(",")[0]);
        }
        in.close();
        // restore
        in = new BufferedReader(
                new FileReader(new File("/weka_results/apriori_result.txt")));
        out = new PrintWriter(new File("/weka_results/association_rules.txt"));
        while ((line = in.readLine()) != null) {
            if (!line.matches("^\\s*[0-9]+\\..*")) {
                continue;
            }
            System.err.println(line);
            String[] parts = line.split("==>");
            String[] left = parts[0].split(" ", -1);
            String[] right = parts[1].split(" ", -1);
            StringBuffer buff = new StringBuffer();
            for (String part : left) {
                if (part.indexOf('=') >= 0) {
                    buff.append(freqItems.get(
                                Integer.parseInt(part.substring(1, part.indexOf('=')))));
                    buff.append(',');
                }
            }
            buff.append("==>");
            for (String part : right) {
                if (part.indexOf('=') >= 0) {
                    buff.append(freqItems.get(
                                Integer.parseInt(part.substring(1, part.indexOf('=')))));
                    buff.append(',');
                }
            }
            out.println(buff.toString());
        }
        in.close();
        out.close();
        // end
        System.err.println("Done");
    }

}
