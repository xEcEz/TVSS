
import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

public class LdaReducer {
	
	
	
	public static class Reduce extends MapReduceBase implements Reducer<Text, DoubleWritable, Text, DoubleWritable> {
		
		//Input : "A,B" iterator of doubles
		//Output : "A,B" sum over the iterator.
		public void reduce(Text key, Iterator<DoubleWritable> values, OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {
			DoubleWritable outputValue = new DoubleWritable();
			double tempSum = 0.0;
			
			while(values.hasNext()){
				tempSum += values.next().get();
				
			}
			outputValue.set(tempSum);
			output.collect(key, outputValue);
			
			
		}
	}
	
	

}
