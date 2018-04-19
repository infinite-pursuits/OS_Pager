import java.io.*;
import java.util.*;

public class Paging {
	static int M;
	static int pageSize;//P
	static int processSize;//S
	static int J;
	static int N;
	static String algo;
	static int np; //no_of_processes
	static ArrayList<Process> processes;
	static FrameTable ft;
	static Scanner sc;
	public static void main(String[] args)
	{	int tF=0,tR=0,tE=0;
		double avg_res=0.0;
		
		algo=args[5];
		M=Integer.parseInt(args[0]);
		pageSize=Integer.parseInt(args[1]);
		processSize=Integer.parseInt(args[2]);
		J=Integer.parseInt(args[3]);
		N=Integer.parseInt(args[4]);
		
		try {
			File f=new File("random-numbers.txt");
			sc=new Scanner(f);
		}
		catch(FileNotFoundException e) 
		{ 
			e.printStackTrace(); 
		}
		System.out.println("The machine size is "+M+".");
		System.out.println("The page size is "+pageSize+".");
		System.out.println("The process size is "+processSize+".");
		System.out.println("The job mix number is "+J+".");
		System.out.println("The number of references per process is "+N+".");
		System.out.println("The replacement algorithm is "+algo+".");
		System.out.println("The level of debugging output is 0");
		System.out.println();
		ft=new FrameTable();
		
		if(J==1)
			np=1;
		else
			np=4;
		
		processes = new ArrayList<>();
		for(int i=0;i<np;i++)
			processes.add(new Process(i+1));
		
		run();
		
		for(int i=0;i<np;i++)
		{
			tF+=processes.get(i).nf;
			tE+=processes.get(i).ne;
			tR+=processes.get(i).total_res_t;
			display(processes.get(i));
		}
		
		if(tE==0)
		{	System.out.println("The total number of faults is "+tF+".");
			System.out.println("With no evictions, the overall average residency is undefined.");
		}
		else
		{
			avg_res = ((double)tR)/((double)tE);
			System.out.println("The total number of faults is "+tF+" and the overall average residency is "+avg_res+".");
		}
	}
	
	public static void run()
	{
		Process current_process;
		int cycle = 0;
		Frame frame_for_replacing = null;

		while (true) {
				for (int i = 0; i < processes.size(); i++) {
					
					current_process = processes.get(i);
					
					for (int j = 0; j < 3; j++) {
						if (current_process.ref > 0) {
							cycle++;
							
							int hit = ft.hit(current_process,cycle);
							if (hit != -1 ) 
								{
								
								
								}
							else 
								{
								current_process.nf ++;
								
								if (ft.free_frame() != null)
								{	frame_for_replacing = ft.free_frame();
									frame_for_replacing.insertpage(current_process, cycle);
									
								}
								else
									{if (algo.equalsIgnoreCase("FIFO")) {
										frame_for_replacing = ft.fifo_frame();
									}
									else
										if (algo.equalsIgnoreCase("LRU")) {
										frame_for_replacing = ft.find_lru_frame();
										
									} else if (algo.equalsIgnoreCase("RANDOM")) {
										frame_for_replacing = ft.random_Frame(sc);
									}
									
									frame_for_replacing.replacepage(current_process, cycle);
								}
								
							}
							
							
							current_process.ref--;
							
							current_process.get_next_word(sc,cycle+1);
							
						}
					}
				}
				
				
				boolean done = true;
				for (int i = 0; i < processes.size(); i++) {
					done &= processes.get(i).ref == 0;
				}
				
				if (done) {
					break;
				}
			}
	}
	public static void display(Process p)
	{
		if(p.ne==0)
		{	System.out.println("Process "+p.id+" had "+p.nf+" faults.");
			System.out.println("With no evictions, the average residency is undefined.");
		}
		else
		{
			p.avg_res=((double)p.total_res_t)/((double)p.ne);
			System.out.println("Process "+p.id+" had "+p.nf+" faults and "+p.avg_res+" average residency.");
		}
	
	}
}
