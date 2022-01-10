

public class DecisionTreeMetrics {

    public static void calculateMetrics(VirtualDataSet dataset) {
		System.out.println("Attribute " + InformationGainCalculator.calculateAndSortInformationGains(dataset)[0].getAttributeName());
        System.out.println();

        int attributeIndex = dataset.getAttributeIndex(InformationGainCalculator.calculateAndSortInformationGains(dataset)[0].getAttributeName());
        int classIndex = dataset.getNumberOfAttributes()-1;
        String targetClassValue = dataset.getUniqueAttributeValues(dataset.getNumberOfAttributes()-1)[1];
        String[] uniqueAttributeValues = dataset.getUniqueAttributeValues(InformationGainCalculator.calculateAndSortInformationGains(dataset)[0].getAttributeName());
        

        double[] support = new double[uniqueAttributeValues.length];
        double[] confidence = new double[uniqueAttributeValues.length];
        double[] interest = new double[uniqueAttributeValues.length];
        double[] lift = new double[uniqueAttributeValues.length];


        System.out.println("#of AttValues " + uniqueAttributeValues.length);


        for (int i = 0; i<uniqueAttributeValues.length; i++){
           
            System.out.println("Value " + uniqueAttributeValues[i]);
            String currentAttributeValue = uniqueAttributeValues[i];
            double freqXY = 0.0;
            double n = (double)dataset.getNumberOfDatapoints();
            double freqX = 0.0;
            double freqY = 0.0;
            boolean premise;
            boolean conclusion;
            System.out.println("n " + n);
                for (int j = 0; j < n; j++) {
                    premise = false;
                    conclusion = false;
                    if ((dataset.getValueAt(j,attributeIndex)).equals(currentAttributeValue)) {
                        premise = true;
                        
                    }
                    if ((dataset.getValueAt(j,classIndex)).equals(targetClassValue)) {
                        conclusion = true;
                        freqY++;
                    }
                    if (premise){
                        freqX++;
                    }
                    if (conclusion){
                        freqY++;
                    }
                    if ((premise) && (conclusion)){
                        freqXY++;  
                    }
                }
           
            support[i] = freqXY/n;
            confidence[i] = freqXY/freqX;
            interest[i] = confidence[i]-(freqY/n);
            lift[i] = (Math.pow(n, 2)*support[i])/((freqX)*(freqY));

            System.out.println("Support: " + support[i]);
            System.out.println("Confidence: " + confidence[i]);
            System.out.println("Interest: " + interest[i]);
            System.out.println("Lift: " + lift[i]);
            System.out.println();
            } 

      

       
	}    



    public static void main(String[] args) throws Exception{
        String strFilename=args[0];
		ActualDataSet actual=new ActualDataSet(new CSVReader(strFilename));
		VirtualDataSet virtual=actual.toVirtual();
        calculateMetrics(virtual);
    }

    

}