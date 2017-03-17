
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class MutualFriends {

public static void main(String [] args) throws Exception

{

	Configuration c=new Configuration();
	
	String[] files=new GenericOptionsParser(c,args).getRemainingArgs();
	
	Path input=new Path(files[0]);
	
	Path output=new Path(files[1]);
	
	Job j=new Job(c,"mutualfriends");
	
	j.getConfiguration().set("mapreduce.job.queuename", "ohcbddev_q1");
	
	j.setJarByClass(MutualFriends.class);
	
	j.setMapperClass(MapForMutualFriends.class);
	
	j.setReducerClass(ReduceForMutualFriends.class);
	
	j.setOutputKeyClass(Text.class);
	
	j.setOutputValueClass(Text.class);
	
	FileInputFormat.addInputPath(j, input);
	
	FileOutputFormat.setOutputPath(j, output);
	
	System.exit(j.waitForCompletion(true)?0:1);

}

public static class MapForMutualFriends extends Mapper<LongWritable, Text, Text, Text>{

public void map(LongWritable key, Text value, Context con) throws IOException, InterruptedException

{

	String line = value.toString();
	
	String[] input=line.split("->");
	
	String[] words=input[1].split(" ");
	
	for(int i=0;i<words.length;i++ )
	
	{
		for(int j=i+1;j<words.length;j++)
		{
			String pair = "("+words[i].trim()+","+words[j].trim()+")";
			Text outputKey = new Text(pair.trim());
	
			Text outputValue = new Text(input[0]);
	
			con.write(outputKey, outputValue);
		}
	
	}
	
}

}

public static class ReduceForMutualFriends extends Reducer<Text, Text, Text, Text>
{
	public void reduce(Text word, Iterable<Text> values, Context con) throws IOException, InterruptedException
	{
		String mutualFriends = "";
		for(Text value : values){
			mutualFriends = mutualFriends+value.toString() +",";
		}
		Text reducerOut = new Text(mutualFriends);
		con.write(word, reducerOut);
	
	}
	
}

}