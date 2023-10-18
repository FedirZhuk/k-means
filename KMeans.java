import java.util.ArrayList;
import java.util.List;
public class KMeans {
    private int k; // number of clusters
    private int maxIterations; // maximum number of iterations
    private List<double[]> vectors; // input vectors
    private List<double[]> centroids; // centroids of clusters

    public KMeans(int k, int maxIterations, List<double[]> vectors) {
        this.k = k;
        this.maxIterations = maxIterations;
        this.vectors = vectors;
        this.centroids = new ArrayList<>();

        // initialize centroids randomly
        List<Integer> alreadyTakenIndexes = new ArrayList<>();
        int x = (int)(Math.random() * vectors.size());
        centroids.add(vectors.get(x));
        alreadyTakenIndexes.add(x);

        for (int i = 1; i < k; i++) {
            while (alreadyTakenIndexes.contains(x))
                x = (int)(Math.random() * vectors.size());

            centroids.add(vectors.get(x));
            alreadyTakenIndexes.add(x);
        }

        System.out.println("Centroid list size is: " + centroids.size());
    }

    public void cluster() {
        List<Double> euclideanSums;
        for (int iteration = 0; iteration < maxIterations; iteration++) {

            euclideanSums = new ArrayList<>();
            // initialize clusters and k 0's for sum at iteration start
            List<List<double[]>> clusters = new ArrayList<>();
            for (int i = 0; i < k; i++) {
                clusters.add(new ArrayList<>());
                euclideanSums.add(0.0);
            }

            // assign vectors to nearest centroid
            for (double[] vector : vectors) {
                int nearestCentroid = getNearestCentroid(vector);

                double tmp = euclideanSums.get(nearestCentroid);
                tmp += euclideanDistance(vector, centroids.get(nearestCentroid));
                euclideanSums.set(nearestCentroid, tmp);

                clusters.get(nearestCentroid).add(vector);
            }

            // update centroids
            for (int i = 0; i < k; i++) {
                double[] newCentroid = new double[centroids.get(0).length];
                List<double[]> cluster = clusters.get(i);
                if (!cluster.isEmpty()) {
                    for (int j = 0; j < newCentroid.length; j++) {
                        double sum = 0.0;
                        for (double[] vector : cluster) {
                            sum += vector[j];
                        }
                        newCentroid[j] = sum / cluster.size();
                    }
                } else {
                    newCentroid = centroids.get(i);
                }
                centroids.set(i, newCentroid);
            }

            System.out.println("Iteration number: " + iteration + " with sums: " + euclideanSums);
        }
    }

    public int getNearestCentroid(double[] vector) {
        int nearestCentroid = 0;
        double nearestDistance = Double.MAX_VALUE;
        for (int i = 0; i < k; i++) {
            double[] centroid = centroids.get(i);
            double distance = euclideanDistance(vector, centroid);
            if (distance < nearestDistance) {
                nearestCentroid = i;
                nearestDistance = distance;
            }
        }
        return nearestCentroid;
    }

    public double euclideanDistance(double[] a, double[] b) {
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            double diff = a[i] - b[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }

    public List<List<double[]>> getClusters() {
        List<List<double[]>> clusters = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            clusters.add(new ArrayList<>());
        }
        for (double[] vector : vectors) {
            int nearestCentroid = getNearestCentroid(vector);
            clusters.get(nearestCentroid).add(vector);
        }
        return clusters;
    }
}
