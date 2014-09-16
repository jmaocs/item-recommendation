import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;
import java.util.Arrays;


public class Transform {

    private static final int R = 20;

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
        // transform from csv to arff
        in = new BufferedReader(
                new FileReader(new File("/data/like_items.csv")));
        out = new PrintWriter(new File("/data/samples_like_items.arff"));
        out.println("@relation likes");
        for (int i = 0; i < freqItems.size(); ++i) {
            out.println("@attribute 'A" + i + "' { t}");
        }
        out.println("@data");
        Random rand = new Random();
        while ((line = in.readLine()) != null) {
            if (rand.nextInt(R) != 0) {
                continue;
            }
            String[] parts = line.split("\",\"", -1);
            parts = Arrays.copyOfRange(parts, 1, parts.length);
            for (int i = 0; i < parts.length; ++i) {
                if (i == parts.length - 1) {
                    parts[i] = parts[i].substring(0, parts[i].length() - 1);
                }
                parts[i] = parts[i].replaceAll("\t", " ").replaceAll(",", " ");
            }
            Arrays.sort(parts);
            int i = 0, j = 0;
            StringBuffer buff = new StringBuffer();
            while (i < parts.length && j < freqItems.size()) {
                if (parts[i].compareTo(freqItems.get(j)) < 0) {
                    ++i;
                } else if (parts[i].compareTo(freqItems.get(j)) > 0) {
                    if (j > 0) {
                        buff.append(',');
                    }
                    buff.append('?');
                    ++j;
                } else {
                    if (j > 0) {
                        buff.append(',');
                    }
                    buff.append('t');
                    ++i;
                    ++j;
                }
            }
            for (; j < freqItems.size(); ++j) {
                if (j > 0) {
                    buff.append(',');
                }
                buff.append('?');
            }
            out.println(buff.toString());
        }
        in.close();
        out.close();
        // end
        System.err.println("Done");
    }

}
