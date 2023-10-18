import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // crearing k from args and list from file
        int k = Integer.parseInt(args[0]);
        List<double[]> vectors = fileToList(args[1]);

        // run k-means clustering with k=3 and maxIterations=100
        KMeans kMeans = new KMeans(k, 10, vectors);
        kMeans.cluster();

        // print clusters
        List<List<double[]>> clusters = kMeans.getClusters();

        // for each cluster
        for (int i = 0; i < clusters.size(); i++) {
            System.out.println("Cluster " + (i+1) + ":");
            List<double[]> cluster = clusters.get(i);
            // for each vector in cluster
            for (double[] vector : cluster) {
                System.out.print("(");
                // for each num in vector
                for (double num : vector) {
                    System.out.print(num + ", ");
                }
                System.out.print(")\n");
            }
            System.out.println();
        }
    }

    public static List<double[]> fileToList(String path){
        List<String> rawData = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                rawData.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<double[]> vectors = new ArrayList<>();

        for (String datum : rawData) {
            String[] tmpLine = datum.split(",");
            double[] tmp = new double[tmpLine.length];

            for (int i = 0; i < tmpLine.length; i++) {
                tmp[i] = Double.parseDouble(tmpLine[i]);
            }

            vectors.add(tmp);
        }

        return vectors;
    }
}