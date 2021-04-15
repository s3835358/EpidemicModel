package dataGen;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DataGen {
	
	
	
	public static void main(String[] args) throws IOException
    {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("(If located within this directory)\nEnter GRAPH name with extension ie. erdosRenyi30.net");
		System.out.println("Otherwise include the full path and filename:");
		
        String filepath = br.readLine();
        File graph = new File(filepath);
        filepath = filepath.substring(0, filepath.lastIndexOf("\\") + 1);
        
        System.out.println("Enter operation: KN, AV, DV, AE or DE");
        
        String operation = br.readLine();
        
        int degree = 0;
        if(operation.equals("KN")) {
        	System.out.println("Enter the degree ie. KN <degree>");
        	degree = Integer.parseInt(br.readLine());
        }
        
        System.out.println("How many times do you want to run said operation?");
        
        int iterations = getIterations(br);
        
        System.out.println("Do you want to print vertices (y/n)?");
        
        String pv = br.readLine();
        
        System.out.println("Do you want to print edges (y/n)?");
        
        String pe = br.readLine();
        
        System.out.println("Name your file (no need to include extension)");
        
        String filename = br.readLine();
        
        
        
              
        Scanner scanner = new Scanner(graph);
        
        ArrayList<String> verts = new ArrayList<String>();
        ArrayList<String> edges = new ArrayList<String>();
        boolean cont = true;
        String line = "",
        vert = "",
        edge = "";
        
        // Find verts
        
        while(scanner.hasNext() && cont) {
        	line = scanner.nextLine();
        	vert = scanner.findInLine("v[0-9]+"); 
	    	if(vert != null) {
	    		verts.add(vert.replace("v",""));
        	} else if (line.contains("*Edges")) {
	    		cont = false;
        	}
        }
        
        cont = true;
        
        // Find edges
        
        while(scanner.hasNext()) {
        	edge = scanner.findInLine("-?\\d+") + " ";
        	edge = edge + scanner.findInLine("-?\\d+");
        	edges.add(edge);
        	edge = scanner.nextLine();
        }
        scanner.close();
        
        // Write operations
        
        Random random = new Random();
        String toWrite = "";
        
        if(operation.equals("KN")) {
        	toWrite = kHop(iterations, degree, verts, random);
        } else if (operation.equals("DE")) {
        	toWrite = delEdge(edges, random, iterations);
        } else if (operation.equals("AE")) {
        	toWrite = addEdge(edges, iterations, verts, random);
        } else if (operation.equals("AV")) {
        	toWrite = addVert(iterations, verts);
        } else if (operation.equals("DV")) {
        	toWrite = delVert(iterations, verts, random);
        }
        
        // Add print vertices and/or print edges
        
        if(pv.equalsIgnoreCase("y")) {
        	toWrite += "PV\n";
        }
        if(pe.equalsIgnoreCase("y")) {
        	toWrite += "PE\n";
        }

        // Write to file in same directory as graph
        Path path = Paths.get(filepath+filename+".in");
        try {
            Files.write(path, toWrite.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("No reported errors, check directory of graph for your file.");
    }
	
	public static String kHop(int iterations, int degree, 
			ArrayList<String> verts, Random random) {
		
		ArrayList<Integer> selected = new ArrayList<Integer>();
		String toWrite = "";
        
		for(int i = 0; i < iterations; i++) {
        	selected.add(getRand(random, selected, verts.size()));	
        }
				
		for(int i = 0; i < selected.size(); i ++) {
			toWrite += "KN" + " " + degree + " " + verts.get(selected.get(i)) + "\n";
		}
		

	    return toWrite;
	}
	
	public static String delEdge(ArrayList<String> edges, Random random, int iterations) {
		
		ArrayList<Integer> selected = new ArrayList<Integer>();
		int bound = edges.size();
        String toWrite = "";
		for(int i = 0; i < iterations; i++) {
        	selected.add(getRand(random, selected, bound));
        	toWrite += "DE " + edges.get(selected.get(i)) + "\n";
        }
		return toWrite;
	}
	
	public static String addEdge(ArrayList<String> edges, int iterations,
			ArrayList<String> verts, Random random) {
		
		ArrayList<Integer> selected = new ArrayList<Integer>();
        String toWrite = "";
        ArrayList<String> notExist = new ArrayList<String>();
		String edge = "",
		inverse = "";
		
        for(String vertOne: verts) {
			for(String vertTwo : verts) {
				edge = vertOne + " " + vertTwo;
				inverse = vertTwo + " " + vertOne;
				if(!edges.contains(edge) && !edges.contains(inverse)) {
					notExist.add(edge);
				}
			}
		}
        int bound = notExist.size();
        for(int i = 0; i < iterations; i++) {
        	selected.add(getRand(random, selected, bound));
        	toWrite += "AE " + notExist.get(selected.get(i)) + "\n";
        }
        
        
		return toWrite;
	}
	
	public static String addVert(int iterations, ArrayList<String> verts) {
		String toWrite = "";
		int vert = verts.size();
		for(int i = 0; i < iterations; i++) {
			vert++;
			toWrite += "AV " + vert + "\n";
		}
		return toWrite;
	}
	
	public static String delVert(int iterations, ArrayList<String> verts, Random random) {
		ArrayList<Integer> selected = new ArrayList<Integer>();
		int bound = verts.size();
        String toWrite = "";
        
		for(int i = 0; i < iterations; i++) {
        	selected.add(getRand(random, selected, bound));
        	toWrite += "DV " + verts.get(selected.get(i)) + "\n";
        }
		return toWrite;
	}
	
	public static int getIterations(BufferedReader br) throws IOException {
		boolean invalid = true;
		String iterations = "";
        while(invalid) {
        	iterations = br.readLine();
        	if(iterations.matches("-?\\d+")) {
        		invalid = false;
        	} else {
        		System.out.println("Invalid int, try again");
        	}
        }
        return Integer.parseInt(iterations);
	}
	
	public static int getRand(Random random, ArrayList<Integer> selected, int bound) {
		int r = -1;
		while(r < 0) {
			int i = random.nextInt(bound);
			if(!selected.contains(i)) {
				r = i; 
			}
		}		
		return r;
	}
}
