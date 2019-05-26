
import java.util.Random;

class simulateDist {
    static long seed = 8;
    public static void main(String[] args) {
        double[] inputArr = new double[args.length-2];
        String distributionName;
        int numberOfSamples;
        numberOfSamples = Integer.parseInt(args[0]);
		double[] result = new double[numberOfSamples];
        distributionName = args[1];
        for(int i = 2; i < args.length; i++)
        {
            inputArr[i-2] = Double.parseDouble(args[i]);
        }

        if(distributionName.equals("bernoulli"))
        {
            result = bernoulli(numberOfSamples, inputArr);
        }
        else if(distributionName.equals("binomial"))
        {
            result = binomial(numberOfSamples, inputArr);
        }
        else if(distributionName.equals("geometric"))
        {
            result = geometric(numberOfSamples, inputArr);
        }
        else if(distributionName.equals("neg-binomial"))
        {
            result = negBinomial(numberOfSamples, inputArr);
        }
        else if(distributionName.equals("poisson"))
        {
            result = poisson(numberOfSamples, inputArr);
        }
        else if(distributionName.equals("arb-discrete"))
        {
            result = arbDiscrete(numberOfSamples, inputArr);
        }
        else if(distributionName.equals("uniform"))
        {
            result = uniform(numberOfSamples, inputArr);
        }
        else if(distributionName.equals("exponential"))
        {
            result = exponential(numberOfSamples, inputArr);
        }
        else if(distributionName.equals("gamma"))
        {
            result = gamma(numberOfSamples, inputArr);
        }
        else if(distributionName.equals("normal"))
        {
            result = normal(numberOfSamples, inputArr);
        }
        else
        {
            System.out.println("\nEnter a valid distribution name \n");
        }

        print(result);
        System.out.println("\n");
    }

    private static void print(double[] result) {
        for(int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
    }

    private static double[] bernoulli(int n,double[] p)
    {
        Random rand = new Random();
        double[] result = new double[n];
        if(p.length != 1)
        {
            System.out.println("\nIncorrect amount of parameters\n");
            return result;
        }

        double probability = p[0];
        if(probability < 0.0 || probability > 1.0)
        {
            System.out.println("\nIncorrect probability\n");
            return result;
        }

        rand.setSeed(seed);
        for(int i = 0; i < n; i++)
        {

            double rn = rand.nextDouble();
//            System.out.println("Random number is " +rn);
            if(rn <= probability) {
                result[i] = 1.0;
            } else {
                result[i] = 0.0;
            }
        }

        return result;
    }

    static double[] binomial(int n, double[] p)
    {
        Random rand = new Random();
        double[] result = new double[n];
        if(p.length != 2)
        {
            System.out.println("\nIncorrect number of parameters\n");
            return result;
        }

        int k = (int) p[0];
        double probability = p[1];
        if(probability < 0.0 || probability > 1.0) {
            System.out.println("\nIncorrect probability\n");
            return result;
        }

        rand.setSeed(seed);
        for (int i = 0; i < n; i++)
        {
            int ns = 0;
            for(int j = 0; j < k; j++)
            {
                double rn = rand.nextDouble();
//                System.out.println("Random numeber is "+rn);
                if(rn <= probability)
                {
                    ns = ns + 1;
                }
            }
            result[i] = (double)ns;

        }

        return result;
    }
    static double[] geometric(int n,double[] p)
    {
        Random rand = new Random();
        double[] result = new double[n];
        if(p.length != 1)
        {
            System.out.println("\nIncorrect amount of parameters\n");
            return result;
        }

        double probability = p[0];
//        System.out.println("prob:" + probability);

        if(probability < 0.0 || probability > 1.0)
        {
            System.out.println("\nIncorrect probability\n");
            return result;
        }
        rand.setSeed(seed);
        for(int i = 0; i < n; i++)
        {
            int t = 1;
            double rn = rand.nextDouble();

            while (rn > probability)
            {
                t = t + 1;
                rn = rand.nextDouble();
            }
            result[i] = (double) t;
        }

        return result;
    }
    static double[] negBinomial(int n, double[] p)
    {
        double[] result = new double[n];
        if(p.length != 2)
        {
            System.out.println("\nIncorrect number of parameters\n");
            return result;
        }

        int k = (int) p[0];
        double[] temp = new double[p.length-1];
        temp[0] = p[1];
        double sum = 0.0;
        for (int i = 0; i < n; i++)
        {
            double[] sumGeometric = geometric(k,temp);
            for(int j = 0; j < sumGeometric.length; j++)
            {
                sum += sumGeometric[j];
            }
            result[i] = sum;
        }

        return result;
    }
    static double[] poisson(int n, double[] p)
    {
        double[] result = new double[n];
        if(p.length != 1) {
            System.out.println("\nIncorrect number of parameters\n");
            return result;
        }
        Random rand = new Random();
        rand.setSeed(seed);
        double lambda = p[0];

        for(int i = 0; i < n; i++)
        {
            int k = 0;
            double u = rand.nextDouble();
            while (u >= Math.exp(0.0-lambda))
            {
                k += 1;
                u = u * rand.nextDouble();
            }
            result[i] = k;
        }

        return result;
    }
    static double[] cdfDiscrete(double[] p)
    {
        double sumProbability = 0;
        double[] result = new double[p.length];
        for(int i = 0; i < p.length; i++)
        {
            for(int j = 0; j <= i; j++)
            {
                sumProbability += p[j];
            }
            result[i] = sumProbability;
        }
        return result;
    }
    static double[] arbDiscrete(int n, double[] p)
    {
        double[] result = new double[n];
        double[] cdf = cdfDiscrete(p);
        Random rand = new Random();
        rand.setSeed(seed);
        for (int i = 0; i < n; i++) {
            int t = 0;
            double u = rand.nextDouble();
            while (cdf[t] <= u) {
                t = t + 1;
//                u = rand.nextDouble();
            }
            result[i] = t;
        }

        return result;
    }
    static double[] uniform(int n, double[] p)
    {
        double[] result = new double[n];
        if(p.length != 2)
        {
            System.out.println("\nIncorrect number of parameters \n");
            return result;
        }
        double a = p[0];
        double b = p[1];
        if(a > b)
        {
            double temp = a;
            a = b;
            b = temp;
        }
        Random rand = new Random();
        rand.setSeed(seed);
        for (int i = 0; i < n; i++)
        {
            double u = rand.nextDouble();
            double uni = (a + ((b-a)*u));
            result[i] = uni;
        }

        return result;
    }
    static double[] exponential(int n, double[] p)
    {
        double[] result = new double[n];
        if(p.length != 1)
        {
            System.out.println("\nIncorrect number of parameters \n");
            return result;
        }
        double lambda = p[0];
        Random rand = new Random();
        rand.setSeed(seed);
        for (int i = 0; i < n; i++) {
            double u = rand.nextDouble();
            double e = ((0-(1/lambda))*Math.log(1-u));
            result[i] = e;
        }

        return result;
    }
    static double[] gamma(int n, double[] p)
    {
        double[] result = new double[n];
        if(p.length != 2) {
            System.out.println("\nIncorrect number of parameters \n");
            return result;
        }
        int alpha = (int) p[0];
        double[] lambda = new double[p.length-1];
        lambda[0] = p[1];
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            double[] sumExponential = exponential(alpha,lambda);
            for (int j = 0; j < sumExponential.length; j++) {
                sum += sumExponential[j];
            }
            result[i] = sum;
        }

        return result;
    }
    static double[] normal(int n, double[] p)
    {
        double[] result = new double[n];
        if(p.length != 2) {
            System.out.println("\nIncorrect number of parameters \n");
            return result;
        }
        int n2 = (int)Math.ceil(n/2);
        double mu = p[0];
        double sigma = p[1];
        Random rand = new Random();
        rand.setSeed(seed);
        int arrayCursor = 0;
        for (int i = 0; i < n2; i++) {
            double u1 = rand.nextDouble();
            double u2 = rand.nextDouble();
            double z1 = (Math.sqrt((0-2)*Math.log(u1))*Math.cos(2*Math.PI*u2));
            double z2 = (Math.sqrt((0-2)*Math.log(u1))*Math.sin(2*Math.PI*u2));
            double x1 = (mu + (z1*sigma));
            double x2 = (mu + (z2*sigma));
            result[arrayCursor++] = x1;
            result[arrayCursor++] = x2;
        }

        if (n % 2 == 0) {

            return result;
        }
        else {
            double[] result2 = new double[result.length-1];
            for(int k = 0; k < result2.length; k++)
            {
                result2[k] = result[k];
            }
            
            return result2;
        }
    }
}
