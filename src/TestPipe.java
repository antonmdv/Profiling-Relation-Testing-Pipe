/*
 * Author:  Anton Medvedev, amedvedev2013@my.fit.edu
 * Course:  CSE 4415, Spring 2017
 * Project: Proj 1A
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

//
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
//

public class TestPipe {

	public static void main(String[] args) throws IOException{
		        
         
        
		//high resolution timer (nanoseconds) and runtime stats
        start = System.nanoTime();
        Runtime runtime = Runtime.getRuntime();
        
		//__________________________________________________
		//Input
		//__________________________________________________
		
		//piped input from the server
		
		Scanner sc = new Scanner(System.in);
		
		int numberOfObjects = sc.nextInt();
		
		HashMap<Integer,ArrayList<Integer>> HM = new HashMap<Integer,ArrayList<Integer>>();
		
		//read data into arrayList and put it in hashmap 
		while(sc.hasNext()){	
			
			int x = sc.nextInt();
			int y = sc.nextInt();
			
			ArrayList<Integer> map = new ArrayList<Integer>();
			
			if(HM.containsKey(x)){
				
				map = HM.get(x);
				map.add(y);
				HM.put(x, map);
				
			}else{
				map.add(y);
				HM.put(x, map);
			}

		}
		
		boolean OTO,OT,Ref,Symm,Tran,isFunc;
		
		//Argument for which test to run from the script task.sh (assignment 1B)
		String param = args[0];
		
		System.out.println("");
		System.out.println("Testing for: " + param);
		System.out.println("");
		
		String testChoice;
		
		
		switch(param) {
		
			case "onetoone":	testChoice = "onetoone";
			
								OTO = OneToOne(HM);
								System.out.println("Is Relation One to One?: " + OTO);
								
								break;
							
            case "onto":		testChoice = "onto";
            
           						OT = OnTo(HM, numberOfObjects);
								System.out.println("Is Relation On To?: " + OT);

            					break;
            					
            case "reflex":		testChoice = "reflex";
            
            					Ref = Reflexive(HM,numberOfObjects);
								System.out.println("Is Relation Reflexive?: " + Ref);
            
            					break;
            				
            case "sym":			testChoice = "sym";
            
            					Symm = Symmetric(HM,numberOfObjects);
								System.out.println("Is Relation Symmetric?: " + Symm);
								
            					break;
            
            					
            case "trans":		testChoice = "trans";
            
            					Tran = Transative(HM);
								System.out.println("Is Relation Transative?: " + Tran);
																
            					break;
            					
            case "func":		testChoice = "func";
            					
            					isFunc = isFunction(HM,numberOfObjects);
								System.out.println("Is Relation a function?: " + isFunc);
								
            					break;
          			
            case "eq":			testChoice = "eq";
            
            					Ref = Reflexive(HM,numberOfObjects);
								Symm = Symmetric(HM,numberOfObjects);
								Tran = Transative(HM);
								
            					if((Ref==true)&&(Symm==true)&&(Tran==true)){
									int numberOfPartitions = displayPartitions(HM,numberOfObjects);
									System.out.println("Number of equivalence classes: "+numberOfPartitions);
									System.out.println("");
			
								}else{
									System.out.println("Relationship is not reflexive,symmetric and transative thus N/A");
									System.out.println("");
								}
								
            					break;
            					
        	case "ref.trans":	testChoice = "ref.trans";
        	
								Ref = Reflexive(HM,numberOfObjects);
								Tran = Transative(HM);
								System.out.println("Is Relation Reflexive?: " + Ref);
								System.out.println("Is Relation Transative?: " + Tran);
								System.out.println("");
								
								break;
								
        	case "ref.sym":	testChoice = "ref.sym";
        	
								Ref = Reflexive(HM,numberOfObjects);
								Symm = Symmetric(HM,numberOfObjects);
								System.out.println("Is Relation Reflexive?: " + Ref);
								System.out.println("Is Relation Symmetric?: " + Symm);
								
								break;
								
        	case "sym.trans":	testChoice = "sym.trans";
        	
        						Symm = Symmetric(HM,numberOfObjects);
								Tran = Transative(HM);
								System.out.println("Is Relation Symmetric?: " + Symm);
								System.out.println("Is Relation Transative?: " + Tran);
								System.out.println("");
								
								break;
           
			
		}
		
		System.out.println("");
		// converting nanoseconds to seconds: nanosecond = 1 billion seconds
        duration = ((double) System.nanoTime() - start) / 1000000000.0;
        
        System.out.println("Program ran for " + duration + " seconds");
        //Print used memory
        System.out.println("Used Memory:"
                           + (runtime.totalMemory() - runtime.freeMemory()) / mb);
        
        //Print free memory
        System.out.println("Free Memory:"
                           + runtime.freeMemory() / mb);
        
        //Print total available memory
        System.out.println("Total Memory:" + runtime.totalMemory() / mb);
        
        //Print Maximum available memory
        System.out.println("Max Memory:" + runtime.maxMemory() / mb);
        
        printUsage();
        
        System.out.println("");
		
		
}
	
	
	public static boolean OneToOne(HashMap<Integer,ArrayList<Integer>> HM){
		
		for(Integer key : HM.keySet()){
			if(HM.get(key).size() > 1){
				return false;
			}
		}
		return true;
	}
	
	
	public static boolean OnTo(HashMap<Integer,ArrayList<Integer>> HM,int range){
	
		HashMap checkY = new HashMap(range);
		for(int i=1; i<=range;i++){
			checkY.put(i, false);
		}
		
		for(Integer key : HM.keySet()){
			for(int i = 0; i<HM.get(key).size(); i++){
				checkY.put(HM.get(key).get(i), true);
			}
		}
		
		if(checkY.containsValue(false)){
			return false;
		}
		return true;
	}
	
	public static boolean Reflexive(HashMap<Integer,ArrayList<Integer>> HM,int range){
		
		HashMap checkUniverse = new HashMap(range);
		for(int i=1; i<=range;i++){
			checkUniverse.put(i, false);
		}
		/*
		for(int i = 0; i < HM.get(key).size(); i++){
			
			if((Integer)key != HM.get(key).get(i)){
				return false;
			}
		}
		*/
		for(Integer key : HM.keySet()){
			if(HM.get(key).contains(key)){
				checkUniverse.put(key, true);
			}
		}
		
		if(checkUniverse.containsValue(false)){
			return false;
		}
		return true;
	
	}
	
	
	public static boolean Symmetric(HashMap<Integer,ArrayList<Integer>> HM, int range){
		
		for(Integer key : HM.keySet()){

			for(int i = 0; i<HM.get(key).size();i++){
				
				if((!HM.containsKey(HM.get(key).get(i)))||(!HM.get(HM.get(key).get(i)).contains(key))){
					return false;
				}
			}
		}
		return true;
		
	}
	
	public static boolean Transative(HashMap<Integer,ArrayList<Integer>> HM){
		
		for(Integer key : HM.keySet()){
			for(int i = 0; i<HM.get(key).size(); i++){
				
				int X = (Integer) key;
				int Y = HM.get(key).get(i);
				
				if(HM.containsKey(Y)){
					
					for(int k = 0; k<HM.get(Y).size(); k++){
						 int Z = HM.get(Y).get(k);
						 if(!HM.get(key).contains(Z)){
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	public static boolean isFunction(HashMap<Integer,ArrayList<Integer>> HM, int range){
		
		for(Integer key : HM.keySet()){
			if((HM.get(key).size()>1)||(HM.get(key).size() == 0)){
				return false;
			}
		}
		return true;
	}
	
	public static int displayPartitions(HashMap<Integer,ArrayList<Integer>> HM, int range){
		
		int numberOfClasses = 0;
		LinkedList<ArrayList<Integer>> eqClasses = new LinkedList<ArrayList<Integer>>();
		
		for(Integer key : HM.keySet()){
			ArrayList<Integer> eq = HM.get(key);
			Collections.sort(eq);
			
			if(!eqClasses.contains(eq)){
				eqClasses.add(eq);
			}
			
		}
		numberOfClasses = eqClasses.size();
		
		if(numberOfClasses <= 15){
			System.out.println("Equivalence classes are: ");
			for(int i = 0; i<numberOfClasses; i++){
				for(int j = 0; j < eqClasses.get(i).size(); j++){
					System.out.print(eqClasses.get(i).get(j)+" ");
				}
				System.out.println("");
			}
		}
		
		return numberOfClasses;
	}
	private static void printUsage() {
  		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
  			for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
    			method.setAccessible(true);
    			if (method.getName().startsWith("get") 
        			&& Modifier.isPublic(method.getModifiers())) {
            			Object value;
        				try {
            				value = method.invoke(operatingSystemMXBean);
        				} catch (Exception e) {
            			value = e;
        				} // try
        			System.out.println(method.getName() + " = " + value);
    			} // if
  			} // for
	}
	
}
