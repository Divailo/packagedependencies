import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class MyProgram {
	
	public static Map<String, String[]> readAndMap(String filename) throws IOException{
		Map<String, String[]> dependenciesMap = new HashMap<String, String[]>();
		    FileReader fileReader = new FileReader(filename);
		    BufferedReader bufferedReader = new BufferedReader(fileReader);
		    String line = bufferedReader.readLine();
		    while(line != null){
		    	String[] elements = line.split("->");
		    	dependenciesMap.put(elements[0].replace(" ", ""), elements[1].split(" "));
		    	line = bufferedReader.readLine();
		    }
		bufferedReader.close();
		return dependenciesMap;
	}
	
	public static ArrayList<String> getPackageDependencies(Map<String, String[]> map, String packageName){
		ArrayList<String> toReturn = new ArrayList<String>();
		if(map.containsKey(packageName)){
			String[] deps = map.get(packageName);
			for(String s : deps){
				if(map.containsKey(s)){
					toReturn.addAll(getPackageDependencies(map, s));
				}
				toReturn.add(s);
			}
		}
		else{
			toReturn.add(" ");
		}
		
		return toReturn;
	}

	public static void main(String[] args) {
		
		String filename = null;
		if(args.length != 0){
			filename = args[0];
		}
		else{
			System.out.println("NO ARGUMENTS SPECIFIED!");
			System.exit(0);
		}
		
		Map<String, String[]> dependenciesMap = null;
		try {
			dependenciesMap = readAndMap(filename);
		} catch (IOException e) {
			System.out.println("FILE NOT FOUND");
			System.exit(0);
		}
		
		for(int i = 1; i < args.length; i++){
			String packageName = args[i];
			Set<String> dependencies = new LinkedHashSet<String>(getPackageDependencies(dependenciesMap, packageName));
			StringBuilder sb = new StringBuilder();
			for(String s : dependencies){
				if(!s.equals(" ")){
					sb.append(s + " ");
				}
			}
			System.out.println(packageName + " -> " + sb.toString());
		}
	}

}